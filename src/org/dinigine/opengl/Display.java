package org.dinigine.opengl;

import java.nio.IntBuffer;

import org.dinigine.input.Input;
import org.dinigine.util.Buffers;
import org.dinigine.util.Log;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

/**
 * @author Robert Myers
 */
public final class Display {

	private Display() {}

	static {
		GLFWErrorCallback.createThrow().set();
	}

	private static long handle;

	private static final IntBuffer dimensions = Buffers.newIntBuffer(2);
	private static final long dimensionsAddress = MemoryUtil.memAddress(dimensions);

	private static boolean wasResized = true;

	private static double lastFrameTime;
	private static float deltaTime;

	public static void create(String title, int width, int height) throws Exception {
		create(new DisplayHints(), title, width, height);
	}

	public static void create(DisplayHints hints, String title, int width, int height) throws Exception {
		if (!GLFW.glfwInit()) {
			throw new Exception("Could not initialize GLFW");
		}
		
		if (hints != null) hints.apply();
		else {
			GLFW.glfwDefaultWindowHints();
		}

		if ((handle = GLFW.glfwCreateWindow(width, height, title, 0, 0)) == 0) {
			throw new Exception("Unable to create window");
		}

		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(handle, (vidmode.width() - width) >> 1, (vidmode.height() - height) >> 1);

		GLFW.glfwMakeContextCurrent(handle);
		GL.createCapabilities();
		
		show();
		isOpen();

		Log.info("Created window: %sx%s", getWidth(), getHeight());
	}

	public static boolean isOpen() {
		double now = getTime();
		deltaTime = (float) (now - lastFrameTime);
		lastFrameTime = now;

		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(handle);

		int lastWidth = getWidth(), lastHeight = getHeight();
		GLFW.nglfwGetFramebufferSize(handle, dimensionsAddress, dimensionsAddress + 4);
		if (lastWidth != getWidth() || lastHeight != getHeight()) {
			wasResized = true;
		}

		Input.poll();

		return !GLFW.glfwWindowShouldClose(handle);
	}

	public static void show() {
		GLFW.glfwShowWindow(handle);
	}
	
	public static void hide() {
		GLFW.glfwHideWindow(handle);
	}
	
	public static boolean wasResized() {
		if (wasResized) {
			wasResized = false;
			return true;
		}
		return false;
	}

	public static int getWidth() {
		return dimensions.get(0);
	}

	public static int getHeight() {
		return dimensions.get(1);
	}

	public static long getHandle() {
		return handle;
	}

	public static float getDeltaTime() {
		return deltaTime;
	}

	public static double getTime() {
		return System.nanoTime() / 1000000000.0;
	}

	public static void dispose() {
		VideoResources.dispose();
		Callbacks.glfwFreeCallbacks(handle);
		GLFW.glfwDestroyWindow(handle);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

}
