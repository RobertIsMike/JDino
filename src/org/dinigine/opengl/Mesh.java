package org.dinigine.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Mesh {

	private final int vertexArray;
	private final int elementCount;

	public Mesh(int vertexArrayID, int elementCount) {
		this.vertexArray = vertexArrayID;
		this.elementCount = elementCount;
	}

	public int getVertexArrayID() {
		return vertexArray;
	}

	public int getElementCount() {
		return elementCount;
	}

	public void bind() {
		GL30.glBindVertexArray(vertexArray);
	}

	public void draw() {
		draw(GL11.GL_TRIANGLES);
	}

	public void draw(int primitive) {
		GL11.glDrawElements(primitive, elementCount, GL11.GL_UNSIGNED_INT, 0);
	}

}
