package org.dinigine.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.apache.lang.Validate;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class MeshBuilder {

	private final int vao;
	private final int vbo;
	private final int ebo;

	private int elementCount;
	private int type = GL11.GL_FLOAT;
	private boolean built;

	public MeshBuilder() {
		this.vao = VideoResources.createVertexArray();
		this.vbo = GL15.glGenBuffers();
		this.ebo = GL15.glGenBuffers();
		GL30.glBindVertexArray(vao);
	}

	public MeshBuilder setVertexData(FloatBuffer data) {
		Validate.validState(!built, "Mesh already built!");
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		return this;
	}

	public MeshBuilder setVertexData(float... data) {
		Validate.validState(!built, "Mesh already built!");
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		return this;
	}

	public MeshBuilder setElementData(IntBuffer data) {
		Validate.validState(!built, "Mesh already built!");
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		elementCount = data.remaining();
		return this;
	}

	public MeshBuilder setElementData(int... data) {
		Validate.validState(!built, "Mesh already built!");
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		elementCount = data.length;
		return this;
	}

	public MeshBuilder setAttribute(int index, int size, int stride, int offset) {
		Validate.validState(!built, "Mesh already built!");
		GL20.glEnableVertexAttribArray(index);
		GL20.glVertexAttribPointer(index, size, type, false, stride, offset);
		return this;
	}
	
	public MeshBuilder calcAttributes(int... sizes) {
		Validate.validState(!built, "Mesh already built!");

		int stride = 0;
		for (int i = 0; i < sizes.length; i++) {
			stride += sizes[i] << 2;
		}

		int offset = 0;
		for (int i = 0; i < sizes.length; i++) {
			setAttribute(i, sizes[i], stride, offset);
			offset += sizes[i] << 2;
		}

		return this;
	}

	public Mesh build() {
		Validate.validState(!built, "Mesh already built!");
		GL30.glBindVertexArray(0);
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(ebo);
		built = true;
		return new Mesh(vao, elementCount);
	}

}
