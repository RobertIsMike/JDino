package org.dinigine.opengl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.dinigine.math.Mat4;
import org.dinigine.math.Vec2;
import org.dinigine.math.Vec3;
import org.dinigine.math.Vec4;
import org.dinigine.util.Buffers;
import org.dinigine.util.Log;
import org.lwjgl.opengl.GL20;

/**
 * @author Robert Myers
 */
public class Shader {

	private static final FloatBuffer matrix = Buffers.newFloatBuffer(16);

	private Map<String, Integer> uniforms = new HashMap<>();
	private int program;

	public Shader(int program) {
		this.program = program;
	}

	public int getID() {
		return program;
	}

	public void bind() {
		GL20.glUseProgram(program);
	}

	public void setAttrib(String name, int index) {
		GL20.glBindAttribLocation(program, index, name);
	}

	public void setBoolean(String name, boolean b) {
		setNumber(name, b ? 1 : 0);
	}

	public void setNumber(String name, int i) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		GL20.glUniform1i(loc, i);
	}

	public void setNumber(String name, float f) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		GL20.glUniform1f(loc, f);
	}

	public void setVec2(String name, Vec2 v) {
		setVec2(name, v.x, v.y);
	}

	public void setVec2(String name, float x, float y) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		GL20.glUniform2f(loc, x, y);
	}

	public void setVec3(String name, Vec3 v) {
		setVec3(name, v.x, v.y, v.z);
	}

	public void setVec3(String name, float x, float y, float z) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		GL20.glUniform3f(loc, x, y, z);
	}

	public void setVec4(String name, Vec4 v) {
		setVec4(name, v.x, v.y, v.z, v.w);
	}

	public void setVec4(String name, float x, float y, float z, float w) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		GL20.glUniform4f(loc, x, y, z, w);
	}

	public void setMat4(String name, Mat4 m) {
		int loc = getUniformLocation(name);
		if (loc == -1) return;
		synchronized (matrix) {
			matrix.clear();
			m.store(matrix);
			matrix.flip();
			GL20.glUniformMatrix4fv(loc, false, matrix);
		}
	}

	private int getUniformLocation(String name) {
		Integer loc = uniforms.get(name);
		if (loc == null) {
			loc = GL20.glGetUniformLocation(program, name);
			if (loc == -1) {
				Log.info("[Shader] No uniform named '" + name + "'");
			}
			uniforms.put(name, loc);
		}
		return loc;
	}

}
