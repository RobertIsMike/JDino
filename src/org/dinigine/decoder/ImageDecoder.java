package org.dinigine.decoder;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.dinigine.math.Maths;

public class ImageDecoder {

	private static final long MAGIC_NUMBER = 0x89504E470D0A1A0AL;

	private static final int IHDR = 0x49484452;
	private static final int PLTE = 0x504C5445;
	private static final int tRNS = 0x74524E53;
	private static final int IDAT = 0x49444154;
	private static final int TRUECOLOR = 2;
	private static final int INDEXED = 3;
	private static final int ALPHA = 6;

	private final DataInputStream in;
	private final CRC32 crc = new CRC32();
	private final byte[] iobuffer = new byte[4096];

	private int chunk_type;
	private int chunk_length;
	private int chunk_remaining;

	private int width;
	private int height;
	private int bitdepth;
	private int color_type;
	private int bpp;

	private byte[] palette;
	private byte[] palette_alpha;
	private byte[] transPixel;

	public ImageDecoder(InputStream res) throws IOException {
		in = new DataInputStream(res);

		if (in.readLong() != MAGIC_NUMBER) {
			throw new IOException("Not a valid PNG file");
		}

		openChunk(IHDR);
		readIHDR();
		closeChunk();

		searchIDAT: while (true) {
			openChunk();
			switch (chunk_type) {
			case IDAT:
				break searchIDAT;
			case PLTE:
				readPLTE();
				break;
			case tRNS:
				readtRNS();
				break;
			}
			closeChunk();
		}

		if (color_type == INDEXED && palette == null) {
			throw new IOException("Missing PLTE chunk");
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasAlpha() {
		return color_type == ALPHA ||
				palette_alpha != null || transPixel != null;
	}

	public int getSuggestedCapacity() {
		return (width * height) << 2;
	}
	
	public void overwriteTRNS(byte r, byte g, byte b) {
		if (color_type == ALPHA) {
			throw new UnsupportedOperationException("image has an alpha channel");
		}
		byte[] pal = palette;
		if (pal == null) {
			transPixel = new byte[] { 0, r, 0, g, 0, b };
		} else {
			palette_alpha = new byte[pal.length / 3];
			for (int i = 0, j = 0; i < pal.length; i += 3, j++) {
				if (pal[i] != r || pal[i + 1] != g || pal[i + 2] != b) {
					palette_alpha[j] = (byte) 0xFF;
				}
			}
		}
	}

	public void decode(ByteBuffer buffer) throws Exception {
		final int line = width << 2;
		final int lineSize = ((width * bitdepth + 7) / 8) * bpp;
		byte[] curLine = new byte[lineSize + 1];
		byte[] prevLine = new byte[lineSize + 1];
		byte[] palLine = (bitdepth < 8) ? new byte[width + 1] : null;

		final Inflater inflater = new Inflater();
		try {
			for (int y = 0; y < height; y++) {
				readChunkUnzip(inflater, curLine, 0, curLine.length);
				unfilter(curLine, prevLine);

				buffer.position(y * line);

				switch (color_type) {
				case ALPHA:
					buffer.put(curLine, 1, curLine.length - 1);
					break;
				case TRUECOLOR:
					if (transPixel != null) {
						byte tr = transPixel[1];
						byte tg = transPixel[3];
						byte tb = transPixel[5];
						for (int i = 1, n = curLine.length; i < n; i += 3) {
							byte r = curLine[i];
							byte g = curLine[i + 1];
							byte b = curLine[i + 2];
							byte a = (byte) 0xFF;
							if (r == tr && g == tg && b == tb) {
								a = 0;
							}
							buffer.put(r).put(g).put(b).put(a);
						}
					} else {
						for (int i = 1, n = curLine.length; i < n; i += 3) {
							buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]).put((byte) 0xFF);
						}
					}
					break;
				case INDEXED:
					switch (bitdepth) {
					case 8:
						palLine = curLine;
						break;
					case 4:
						expand4(curLine, palLine);
						break;
					case 2:
						expand2(curLine, palLine);
						break;
					case 1:
						expand1(curLine, palLine);
						break;
					default:
						throw new UnsupportedOperationException("Unsupported bitdepth for this image");
					}
					if (palette_alpha != null) {
						for (int i = 1, n = palLine.length; i < n; i++) {
							int idx = palLine[i] & 255;
							byte r = palette[idx * 3 + 0];
							byte g = palette[idx * 3 + 1];
							byte b = palette[idx * 3 + 2];
							byte a = palette_alpha[idx];
							buffer.put(r).put(g).put(b).put(a);
						}
					} else {
						for (int i = 1, n = palLine.length; i < n; i++) {
							int idx = palLine[i] & 255;
							byte r = palette[idx * 3 + 0];
							byte g = palette[idx * 3 + 1];
							byte b = palette[idx * 3 + 2];
							byte a = (byte) 0xFF;
							buffer.put(r).put(g).put(b).put(a);
						}
					}
					break;
				default:
					throw new UnsupportedOperationException();
				}

				byte[] tmp = curLine;
				curLine = prevLine;
				prevLine = tmp;
			}
		} finally {
			inflater.end();
		}
	}

	private void expand4(byte[] src, byte[] dst) {
		for (int i = 1, n = dst.length; i < n; i += 2) {
			int val = src[1 + (i >> 1)] & 255;
			switch (n - i) {
			default:
				dst[i + 1] = (byte) (val & 15);
			case 1:
				dst[i] = (byte) (val >> 4);
			}
		}
	}

	private void expand2(byte[] src, byte[] dst) {
		for (int i = 1, n = dst.length; i < n; i += 4) {
			int val = src[1 + (i >> 2)] & 255;
			switch (n - i) {
			default:
				dst[i + 3] = (byte) ((val) & 3);
			case 3:
				dst[i + 2] = (byte) ((val >> 2) & 3);
			case 2:
				dst[i + 1] = (byte) ((val >> 4) & 3);
			case 1:
				dst[i] = (byte) ((val >> 6));
			}
		}
	}

	private void expand1(byte[] src, byte[] dst) {
		for (int i = 1, n = dst.length; i < n; i += 8) {
			int val = src[1 + (i >> 3)] & 255;
			switch (n - i) {
			default:
				dst[i + 7] = (byte) ((val) & 1);
			case 7:
				dst[i + 6] = (byte) ((val >> 1) & 1);
			case 6:
				dst[i + 5] = (byte) ((val >> 2) & 1);
			case 5:
				dst[i + 4] = (byte) ((val >> 3) & 1);
			case 4:
				dst[i + 3] = (byte) ((val >> 4) & 1);
			case 3:
				dst[i + 2] = (byte) ((val >> 5) & 1);
			case 2:
				dst[i + 1] = (byte) ((val >> 6) & 1);
			case 1:
				dst[i] = (byte) ((val >> 7));
			}
		}
	}

	private void unfilter(byte[] curLine, byte[] prevLine) throws IOException {
		switch (curLine[0]) {
		case 0: // none
			break;
		case 1:
			unfilterSub(curLine);
			break;
		case 2:
			unfilterUp(curLine, prevLine);
			break;
		case 3:
			unfilterAverage(curLine, prevLine);
			break;
		case 4:
			unfilterPaeth(curLine, prevLine);
			break;
		default:
			throw new IOException("invalide filter type in scanline: " + curLine[0]);
		}
	}

	private void unfilterSub(byte[] curLine) {
		for (int i = bpp + 1, n = curLine.length; i < n; ++i) {
			curLine[i] += curLine[i - bpp];
		}
	}

	private void unfilterUp(byte[] curLine, byte[] prevLine) {
		for (int i = 1, n = curLine.length; i < n; ++i) {
			curLine[i] += prevLine[i];
		}
	}

	private void unfilterAverage(byte[] curLine, byte[] prevLine) {
		int i;
		for (i = 1; i <= bpp; ++i) {
			curLine[i] += (byte) ((prevLine[i] & 0xFF) >>> 1);
		}
		for (int n = curLine.length; i < n; ++i) {
			curLine[i] += (byte) (((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF)) >>> 1);
		}
	}

	private void unfilterPaeth(byte[] curLine, byte[] prevLine) {
		int i;
		for (i = 1; i <= bpp; ++i) {
			curLine[i] += prevLine[i];
		}
		for (int n = curLine.length; i < n; ++i) {
			int a = curLine[i - bpp] & 255;
			int b = prevLine[i] & 255;
			int c = prevLine[i - bpp] & 255;
			int p = a + b - c;
			int pa = p - a;
			if (pa < 0) pa = -pa;
			int pb = p - b;
			if (pb < 0) pb = -pb;
			int pc = p - c;
			if (pc < 0) pc = -pc;
			if (pa <= pb && pa <= pc)
				c = a;
			else if (pb <= pc)
				c = b;
			curLine[i] += (byte) c;
		}
	}

	private void readIHDR() throws IOException {
		checkChunkLength(13);
		readChunk(iobuffer, 0, 13);
		width = toInt(iobuffer, 0);
		height = toInt(iobuffer, 4);
		bitdepth = iobuffer[8] & 255;
		color_type = iobuffer[9] & 255;

		switch (color_type) {
		case TRUECOLOR:
			if (bitdepth != 8) {
				throw new IOException("Unsupported bit depth: " + bitdepth);
			}
			bpp = 3;
			break;
		case ALPHA:
			if (bitdepth != 8) {
				throw new IOException("Unsupported bit depth: " + bitdepth);
			}
			bpp = 4;
			break;
		case INDEXED:
			switch (bitdepth) {
			case 8:
			case 4:
			case 2:
			case 1:
				bpp = 1;
				break;
			default:
				throw new IOException("Unsupported bit depth: " + bitdepth);
			}
			break;
		default:
			throw new IOException("unsupported color format: " + color_type);
		}

		if (iobuffer[10] != 0) {
			throw new IOException("unsupported compression method");
		}
		if (iobuffer[11] != 0) {
			throw new IOException("unsupported filtering method");
		}
		if (iobuffer[12] != 0) {
			throw new IOException("unsupported interlace method");
		}
	}

	private void readPLTE() throws IOException {
		int paletteEntries = chunk_length / 3;
		if (paletteEntries < 1 || paletteEntries > 256 || (chunk_length % 3) != 0) {
			throw new IOException("PLTE chunk has wrong length");
		}
		palette = new byte[paletteEntries * 3];
		readChunk(palette, 0, palette.length);
	}

	private void readtRNS() throws IOException {
		switch (color_type) {
		case TRUECOLOR:
			checkChunkLength(6);
			transPixel = new byte[6];
			readChunk(transPixel, 0, 6);
			break;
		case INDEXED:
			if (palette == null) {
				throw new IOException("tRNS chunk without PLTE chunk");
			}
			palette_alpha = new byte[palette.length / 3];
			Arrays.fill(palette_alpha, (byte) 0xFF);
			readChunk(palette_alpha, 0, palette_alpha.length);
			break;
		default:
			break;
		}
	}

	private void closeChunk() throws IOException {
		if (chunk_remaining > 0) {
			skip(chunk_remaining + 4);
		} else {
			readFully(iobuffer, 0, 4);
			int expectedCrc = toInt(iobuffer, 0);
			int computedCrc = (int) crc.getValue();
			if (computedCrc != expectedCrc) {
				throw new IOException("Invalid CRC");
			}
		}
		chunk_remaining = 0;
		chunk_length = 0;
		chunk_type = 0;
	}

	private void openChunk() throws IOException {
		readFully(iobuffer, 0, 8);
		chunk_length = toInt(iobuffer, 0);
		chunk_type = toInt(iobuffer, 4);
		chunk_remaining = chunk_length;
		crc.reset();
		crc.update(iobuffer, 4, 4);
	}

	private void openChunk(int expected) throws IOException {
		openChunk();
		if (chunk_type != expected) {
			throw new IOException("Expected chunk: 0x" + Maths.toHex(expected));
		}
	}

	private void checkChunkLength(int expected) throws IOException {
		if (chunk_length != expected) {
			throw new IOException("Chunk has wrong size");
		}
	}

	private int readChunk(byte[] buffer, int offset, int length) throws IOException {
		if (length > chunk_remaining) {
			length = chunk_remaining;
		}
		readFully(buffer, offset, length);
		crc.update(buffer, offset, length);
		chunk_remaining -= length;
		return length;
	}

	private void refillInflater(Inflater inflater) throws IOException {
		while (chunk_remaining == 0) {
			closeChunk();
			openChunk(IDAT);
		}
		int read = readChunk(iobuffer, 0, iobuffer.length);
		inflater.setInput(iobuffer, 0, read);
	}

	private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length) throws IOException {
		assert (buffer != this.iobuffer);
		try {
			do {
				int read = inflater.inflate(buffer, offset, length);
				if (read <= 0) {
					if (inflater.finished()) {
						throw new EOFException();
					}
					if (inflater.needsInput()) {
						refillInflater(inflater);
					} else {
						throw new IOException("Can't inflate " + length + " bytes");
					}
				} else {
					offset += read;
					length -= read;
				}
			} while (length > 0);
		} catch (DataFormatException ex) {
			throw new IOException("inflate error", ex);
		}
	}

	private void readFully(byte[] buffer, int offset, int length) throws IOException {
		do {
			int read = in.read(buffer, offset, length);
			if (read < 0) {
				throw new EOFException();
			}
			offset += read;
			length -= read;
		} while (length > 0);
	}

	private int toInt(byte[] buffer, int offset) {
		return ((buffer[offset]) << 24) |
				((buffer[offset + 1] & 0xFF) << 16) |
				((buffer[offset + 2] & 0xFF) << 8) |
				((buffer[offset + 3] & 0xFF));
	}

	private void skip(long amount) throws IOException {
		while (amount > 0) {
			long skipped = in.skip(amount);
			if (skipped < 0) {
				throw new EOFException();
			}
			amount -= skipped;
		}
	}

}
