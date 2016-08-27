package org.dinigine.opengl;

import org.lwjgl.glfw.GLFW;

/**
 * @author Robert Myers
 */
public class DisplayHints {

	public static final int DONT_CARE = GLFW.GLFW_DONT_CARE;

	public static final int NO_API = GLFW.GLFW_NO_API;
	public static final int OPENGL_API = GLFW.GLFW_OPENGL_API;
	public static final int OPENGL_ES_API = GLFW.GLFW_OPENGL_ES_API;

	public static final int NO_ROBUSTNESS = GLFW.GLFW_NO_ROBUSTNESS;
	public static final int NO_RESET_NOTIFICATION = GLFW.GLFW_NO_RESET_NOTIFICATION;
	public static final int LOSE_CONTEXT_ON_RESET = GLFW.GLFW_LOSE_CONTEXT_ON_RESET;

	public static final int ANY_RELEASE_BEHAVIOR = GLFW.GLFW_ANY_RELEASE_BEHAVIOR;
	public static final int RELEASE_BEHAVIOR_FLUSH = GLFW.GLFW_RELEASE_BEHAVIOR_FLUSH;
	public static final int RELEASE_BEHAVIOR_NONE = GLFW.GLFW_RELEASE_BEHAVIOR_NONE;

	public static final int NATIVE_CONTEXT_API = GLFW.GLFW_NATIVE_CONTEXT_API;
	public static final int EGL_CONTEXT_API = GLFW.GLFW_EGL_CONTEXT_API;

	public static final int OPENGL_ANY_PROFILE = GLFW.GLFW_OPENGL_ANY_PROFILE;
	public static final int OPENGL_CORE_PROFILE = GLFW.GLFW_OPENGL_CORE_PROFILE;
	public static final int OPENGL_COMPAT_PROFILE = GLFW.GLFW_OPENGL_COMPAT_PROFILE;

	private boolean resizable = true;
	private boolean visible = true;
	private boolean decorated = true;
	private boolean focused = true;
	private boolean auto_iconify = true;
	private boolean floating = false;
	private boolean maximized = false;
	private int red_bits = 8;
	private int green_bits = 8;
	private int blue_bits = 8;
	private int alpha_bits = 8;
	private int depth_bits = 24;
	private int stencil_bits = 8;
	private int accum_red_bits = 0;
	private int accum_green_bits = 0;
	private int accum_blue_bits = 0;
	private int accum_alpha_bits = 0;
	private int aux_buffers = 0;
	private int samples = 0;
	private int refresh_rate = DONT_CARE;
	private boolean stereo = false;
	private boolean srgb_capable = false;
	private boolean doublebuffer = true;
	private int client_api = OPENGL_API;
	private int context_version_major = 3;
	private int context_version_minor = 3;
	private int context_robustness = NO_ROBUSTNESS;
	private int context_release_behavior = ANY_RELEASE_BEHAVIOR;
	private boolean context_no_error = false;
	private int context_creation_api = NATIVE_CONTEXT_API;
	private boolean opengl_forward_compat = true;
	private boolean opengl_debug_context = false;
	private int opengl_profile = OPENGL_CORE_PROFILE;

	public DisplayHints setResizable(boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	public DisplayHints setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public DisplayHints setDecorated(boolean decorated) {
		this.decorated = decorated;
		return this;
	}

	public DisplayHints setFocused(boolean focused) {
		this.focused = focused;
		return this;
	}

	public DisplayHints setAutoIconify(boolean auto_iconify) {
		this.auto_iconify = auto_iconify;
		return this;
	}

	public DisplayHints setFloating(boolean floating) {
		this.floating = floating;
		return this;
	}

	public DisplayHints setMaximized(boolean maximized) {
		this.maximized = maximized;
		return this;
	}

	public DisplayHints setRedBits(int red_bits) {
		this.red_bits = red_bits;
		return this;
	}

	public DisplayHints setGreenBits(int green_bits) {
		this.green_bits = green_bits;
		return this;
	}

	public DisplayHints setBlueBits(int blue_bits) {
		this.blue_bits = blue_bits;
		return this;
	}

	public DisplayHints setAlphaBits(int alpha_bits) {
		this.alpha_bits = alpha_bits;
		return this;
	}

	public DisplayHints setDepthBits(int depth_bits) {
		this.depth_bits = depth_bits;
		return this;
	}

	public DisplayHints setStencilBits(int stencil_bits) {
		this.stencil_bits = stencil_bits;
		return this;
	}

	public DisplayHints setAccumRedBits(int accum_red_bits) {
		this.accum_red_bits = accum_red_bits;
		return this;
	}

	public DisplayHints setAccumGreenBits(int accum_green_bits) {
		this.accum_green_bits = accum_green_bits;
		return this;
	}

	public DisplayHints setAccumBlueBits(int accum_blue_bits) {
		this.accum_blue_bits = accum_blue_bits;
		return this;
	}

	public DisplayHints setAccumAlphaBits(int accum_alpha_bits) {
		this.accum_alpha_bits = accum_alpha_bits;
		return this;
	}

	public DisplayHints setAuxBuffers(int aux_buffers) {
		this.aux_buffers = aux_buffers;
		return this;
	}

	public DisplayHints setSamples(int samples) {
		this.samples = samples;
		return this;
	}

	public DisplayHints setRefreshRate(int refresh_rate) {
		this.refresh_rate = refresh_rate;
		return this;
	}

	public DisplayHints setStereo(boolean stereo) {
		this.stereo = stereo;
		return this;
	}

	public DisplayHints setSRGBCapable(boolean capable) {
		this.srgb_capable = capable;
		return this;
	}

	public DisplayHints setDoublebuffer(boolean doublebuffer) {
		this.doublebuffer = doublebuffer;
		return this;
	}

	public DisplayHints setClientAPI(int client_api) {
		this.client_api = client_api;
		return this;
	}

	public DisplayHints setContextVersionMajor(int context_version_major) {
		this.context_version_major = context_version_major;
		return this;
	}

	public DisplayHints setContextVersionMinor(int context_version_minor) {
		this.context_version_minor = context_version_minor;
		return this;
	}

	public DisplayHints setContextRobustness(int context_robustness) {
		this.context_robustness = context_robustness;
		return this;
	}

	public DisplayHints setContextReleaseBehavior(int context_release_behavior) {
		this.context_release_behavior = context_release_behavior;
		return this;
	}

	public DisplayHints setContextNoError(boolean context_no_error) {
		this.context_no_error = context_no_error;
		return this;
	}

	public DisplayHints setContextCreationAPI(int context_creation_api) {
		this.context_creation_api = context_creation_api;
		return this;
	}

	public DisplayHints setOpenGLForwardCompatible(boolean compat) {
		this.opengl_forward_compat = compat;
		return this;
	}

	public DisplayHints setOpenGLDebugContext(boolean opengl_debug_context) {
		this.opengl_debug_context = opengl_debug_context;
		return this;
	}

	public DisplayHints setOpenGLProfile(int opengl_profile) {
		this.opengl_profile = opengl_profile;
		return this;
	}

	final void apply() {
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, visible ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, decorated ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, focused ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, auto_iconify ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, floating ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, maximized ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, red_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, green_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, blue_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_ALPHA_BITS, alpha_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, depth_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, stencil_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_RED_BITS, accum_red_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_GREEN_BITS, accum_green_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_BLUE_BITS, accum_blue_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_ALPHA_BITS, accum_alpha_bits);
		GLFW.glfwWindowHint(GLFW.GLFW_AUX_BUFFERS, aux_buffers);
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, samples);
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, refresh_rate);
		GLFW.glfwWindowHint(GLFW.GLFW_STEREO, stereo ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_SRGB_CAPABLE, srgb_capable ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, doublebuffer ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, client_api);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, context_version_major);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, context_version_minor);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_ROBUSTNESS, context_robustness);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_RELEASE_BEHAVIOR, context_release_behavior);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_NO_ERROR, context_no_error ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, context_creation_api);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, opengl_forward_compat ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, opengl_debug_context ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, opengl_profile);
	}

}
