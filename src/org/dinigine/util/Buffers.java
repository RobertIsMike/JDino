package org.dinigine.util;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * @author Robert Myers
 */
public final class Buffers {
	/** The byte order native to the CPU, either Little or Big Endian */
	public static final ByteOrder NATIVE_ORDER = ByteOrder.nativeOrder();

	/** Static only */
	private Buffers() {}

	/**
	 * @param buf
	 *            - the buffer
	 * @return the number of bytes between the current position and the limit.
	 */
	public static int sizeof(Buffer buf) {
		if (buf == null || !buf.hasRemaining()) return 0;
		else return buf.remaining() << getExpOffs(buf);
	}

	/**
	 * For example, the following code is equal the buffer's position in bytes:
	 *
	 * <pre>
	 * Buffer.position() &lt;&lt; getExpOffs(Buffer)
	 * </pre>
	 *
	 * @param buf
	 *            - the buffer
	 * @return the exponent of 2 used to offset a buffer value to equal the
	 *         value in bytes
	 */
	public static int getExpOffs(Buffer buf) {
		if (buf instanceof ByteBuffer) return 0;
		else if (buf instanceof CharBuffer || buf instanceof ShortBuffer) return 1;
		else if (buf instanceof IntBuffer || buf instanceof FloatBuffer) return 2;
		else if (buf instanceof LongBuffer || buf instanceof DoubleBuffer) return 3;
		throw new IllegalArgumentException("Unsupported buffer type: " + buf.getClass());
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code byte} buffer
	 */
	public static ByteBuffer newByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size).order(NATIVE_ORDER);
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code char} buffer
	 */
	public static CharBuffer newCharBuffer(int size) {
		return newByteBuffer(size << 1).asCharBuffer();
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code short} buffer
	 */
	public static ShortBuffer newShortBuffer(int size) {
		return newByteBuffer(size << 1).asShortBuffer();
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code int} buffer
	 */
	public static IntBuffer newIntBuffer(int size) {
		return newByteBuffer(size << 2).asIntBuffer();
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code float} buffer
	 */
	public static FloatBuffer newFloatBuffer(int size) {
		return newByteBuffer(size << 2).asFloatBuffer();
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code long} buffer
	 */
	public static LongBuffer newLongBuffer(int size) {
		return newByteBuffer(size << 3).asLongBuffer();
	}

	/**
	 * @param size
	 *            - the capacity of the buffer
	 * @return A new {@code double} buffer
	 */
	public static DoubleBuffer newDoubleBuffer(int size) {
		return newByteBuffer(size << 3).asDoubleBuffer();
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static ByteBuffer asByteBuffer(byte... vals) {
		ByteBuffer buf = newByteBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static CharBuffer asCharBuffer(char... vals) {
		CharBuffer buf = newCharBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static ShortBuffer asShortBuffer(short... vals) {
		ShortBuffer buf = newShortBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static IntBuffer asIntBuffer(int... vals) {
		IntBuffer buf = newIntBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static FloatBuffer asFloatBuffer(float... vals) {
		FloatBuffer buf = newFloatBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static LongBuffer asLongBuffer(long... vals) {
		LongBuffer buf = newLongBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

	/**
	 * @param vals
	 *            - the values to put in the buffer
	 * @return A new buffer containing {@code vals} with a position of {@code 0}
	 *         and a limit of {@code vals.length}
	 */
	public static DoubleBuffer asDoubleBuffer(double... vals) {
		DoubleBuffer buf = newDoubleBuffer(vals.length);
		buf.put(vals).flip();
		return buf;
	}

}
