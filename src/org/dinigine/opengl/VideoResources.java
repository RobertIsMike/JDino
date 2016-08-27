package org.dinigine.opengl;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class VideoResources {

	private VideoResources() {}

	private static final List<Integer> vertexArrays = new ArrayList();
	private static final List<Integer> vertexBuffers = new ArrayList();
	private static final List<Integer> frameBuffers = new ArrayList();
	private static final List<Integer> renderBuffers = new ArrayList();
	private static final List<Integer> textures = new ArrayList();
	private static final List<Integer> shaders = new ArrayList();

	public static int createVertexArray() {
		int id = GL30.glGenVertexArrays();
		vertexArrays.add(id);
		return id;
	}

	public static int createVertexBuffer() {
		int id = GL15.glGenBuffers();
		vertexBuffers.add(id);
		return id;
	}

	public static int createFrameBuffer() {
		int id = GL30.glGenFramebuffers();
		frameBuffers.add(id);
		return id;
	}

	public static int createRenderBuffer() {
		int id = GL30.glGenRenderbuffers();
		renderBuffers.add(id);
		return id;
	}

	public static int createTexture() {
		int id = GL11.glGenTextures();
		textures.add(id);
		return id;
	}

	public static int createShader() {
		int id = GL20.glCreateProgram();
		shaders.add(id);
		return id;
	}

	public static void deleteVertexArray(Integer id) {
		GL30.glDeleteVertexArrays(id);
		vertexArrays.remove(id);
	}

	public static void deleteVertexBuffer(Integer id) {
		GL15.glDeleteBuffers(id);
		vertexBuffers.remove(id);
	}

	public static void deleteFrameBuffer(Integer id) {
		GL30.glDeleteFramebuffers(id);
		frameBuffers.remove(id);
	}

	public static void deleteRenderBuffer(Integer id) {
		GL30.glDeleteRenderbuffers(id);
		renderBuffers.remove(id);
	}

	public static void deleteTexture(Integer id) {
		GL11.glDeleteTextures(id);
		textures.remove(id);
	}

	public static void deleteShader(Integer id) {
		GL20.glDeleteProgram(id);
		shaders.remove(id);
	}

	public static void dispose() {
		Object[] ids = vertexArrays.toArray();
		for (Object i : ids) {
			deleteVertexArray((Integer) i);
		}
		ids = vertexBuffers.toArray();
		for (Object i : ids) {
			deleteVertexBuffer((Integer) i);
		}
		ids = frameBuffers.toArray();
		for (Object i : ids) {
			deleteFrameBuffer((Integer) i);
		}
		ids = renderBuffers.toArray();
		for (Object i : ids) {
			deleteRenderBuffer((Integer) i);
		}
		ids = textures.toArray();
		for (Object i : ids) {
			deleteTexture((Integer) i);
		}
		ids = shaders.toArray();
		for (Object i : ids) {
			deleteShader((Integer) i);
		}
	}

}
