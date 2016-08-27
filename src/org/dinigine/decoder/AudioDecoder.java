package org.dinigine.decoder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.openal.AL10;

/**
 * @author Robert Myers
 */
public class AudioDecoder {

	private AudioInputStream in;

	private ByteOrder byteOrder;
	private int channels;
	private int samplesize;
	private int freq;
	private int framelen;
	private int datalen;
	private int openal;
	private boolean is16bit;

	public AudioDecoder(InputStream res) throws IOException {
		try {
			in = AudioSystem.getAudioInputStream(new BufferedInputStream(res));
		} catch (UnsupportedAudioFileException e) {
			throw new IOException("Unsupported audio file type", e);
		}

		readAudioFormat();
		
		datalen = in.available();
		if (datalen <= 0) {
			datalen = framelen * channels * (is16bit ? 2 : 1);
		}
	}
	
	public int getOpenALFormat() {
		return openal;
	}
	
	public int getFrequency() {
		return freq;
	}

	public int getSuggestedCapacity() {
		return datalen;
	}
	
	public void decode(ByteBuffer buffer) throws IOException {
		byte[] data = new byte[datalen];
		int read = 0, total = 0;
		while ((read = in.read(data, total, datalen - total)) != -1 && total < datalen) {
			total += read;
		}
		
		if (buffer.order().equals(byteOrder)) buffer.put(data);
		else {
			ByteBuffer src = ByteBuffer.wrap(data).order(byteOrder);
			if (is16bit) {
				ShortBuffer dest_short = buffer.asShortBuffer();
				ShortBuffer src_short = src.asShortBuffer();
				while (src_short.hasRemaining()) {
					dest_short.put(src_short.get());
				}
			} else {
				while (src.hasRemaining()) {
					buffer.put(src.get());
				}
			}
		}
	}
	
	private void readAudioFormat() throws IOException {
		AudioFormat format = in.getFormat();
		byteOrder = format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
		channels = format.getChannels();
		samplesize = format.getSampleSizeInBits();
		freq = (int) format.getSampleRate();
		framelen = (int) in.getFrameLength();
		is16bit = samplesize == 16;
		
		if (!is16bit && samplesize != 8) {
			throw new IOException("Sample size was " + samplesize + "-bit, must be 8-bit or 16-bit");
		}
		
		if (channels == 1) openal = AL10.AL_FORMAT_MONO8;
		else if (channels == 2) openal = AL10.AL_FORMAT_STEREO8;
		else throw new IOException("Only mono or stereo audio is supported");
		if (is16bit) openal++;
	}

}