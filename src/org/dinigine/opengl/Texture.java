package org.dinigine.opengl;

import java.nio.ByteBuffer;

import org.dinigine.decoder.ImageDecoder;
import org.dinigine.util.Buffers;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class Texture {

	public static final int MAX_SIZE = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);

	private static final ByteBuffer imageLoadingBuffer = Buffers.newByteBuffer((MAX_SIZE * MAX_SIZE) << 2);

	private int width;
	private int height;
	private int texture;

	public Texture(ImageDecoder decoder) throws Exception {
		this.width = decoder.getWidth();
		this.height = decoder.getHeight();
		
		decoder.decode(imageLoadingBuffer);
		imageLoadingBuffer.position(0);

		this.texture = VideoResources.createTexture();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageLoadingBuffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}

	public void bind(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void dispose() {
		GL11.glDeleteTextures(texture);
	}

}
