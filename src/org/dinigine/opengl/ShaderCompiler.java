package org.dinigine.opengl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.lang.StringUtils;
import org.dinigine.math.Maths;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

/**
 * @author Robert Myers
 */
public final class ShaderCompiler {

	private static final String SHADER_VERSION = "400";
	
	private static class ShaderSource {
		final StringBuffer buffer = new StringBuffer("#version " + SHADER_VERSION);
		final String name;
		final int type;

		public ShaderSource(String filename, String typename, int type) {
			this.name = filename + " (" + typename + ")";
			this.type = type;
		}
	}

	public static int createProgram(String name, InputStream in) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		List<ShaderSource> sources = new ArrayList<>();
		ShaderSource current = null;

		for (String ln; (ln = reader.readLine()) != null;) {
			if ((ln = ln.trim()).startsWith("// vertex")) {
				sources.add(current = new ShaderSource(
						name, "vertex", GL20.GL_VERTEX_SHADER));
			} else if (ln.startsWith("// fragment")) {
				sources.add(current = new ShaderSource(
						name, "fragment", GL20.GL_FRAGMENT_SHADER));
			} else if (ln.startsWith("// geometry")) {
				sources.add(current = new ShaderSource(
						name, "geometry", GL32.GL_GEOMETRY_SHADER));
			} else if (current != null) {
				current.buffer.append('\n').append(ln);
			}
		}

		List<Integer> shaders = new ArrayList<>();
		for (ShaderSource src : sources) {
			int shader = GL20.glCreateShader(src.type);
			GL20.glShaderSource(shader, src.buffer);
			GL20.glCompileShader(shader);
			checkShaderError(src.name, shader, false, GL20.GL_COMPILE_STATUS);
			shaders.add(shader);
		}

		int program = VideoResources.createShader();

		for (int shader : shaders)
			GL20.glAttachShader(program, shader);

		GL20.glLinkProgram(program);

		for (int shader : shaders) {
			GL20.glDetachShader(program, shader);
			GL20.glDeleteShader(shader);
		}

		checkShaderError(name, program, true, GL20.GL_LINK_STATUS);

		GL20.glUseProgram(program);
		return program;
	}

	private static void checkShaderError(String name, int id, boolean program, int flag) throws Exception {
		int err = program ? GL20.glGetProgrami(id, flag) : GL20.glGetShaderi(id, flag);
		if (err == 0) {
			StringBuffer buffer = new StringBuffer("Shader '" + name + "' had errors!");
			String log = program ? GL20.glGetProgramInfoLog(id) : GL20.glGetShaderInfoLog(id);
			if (program) GL20.glDeleteProgram(id);
			else GL20.glDeleteShader(id);
			
			if (!StringUtils.isEmpty(log)) {
				StringTokenizer tokens = new StringTokenizer(log, "\n");
				StringBuffer filtered = new StringBuffer();
				int len = 0;
				while (tokens.hasMoreTokens()) {
					String token = tokens.nextToken();
					if (!token.isEmpty()) {
						len = Maths.max(len, token.length());
						filtered.append('\n').append(token);
					}
				}
				
				char[] br = new char[len];
				Arrays.fill(br, '-');
				buffer.append('\n').append(br);
				buffer.append(filtered);
				buffer.append('\n').append(br);
			}

			throw new Exception(buffer.toString());
		}
	}
	
}