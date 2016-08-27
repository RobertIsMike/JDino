/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/**
 * {@link InputStream} implementation that can read from String, StringBuffer,
 * StringBuilder or CharBuffer.
 * <p>
 * <strong>Note:</strong> Supports {@link #mark(int)} and {@link #reset()}.
 *
 * @since 2.2
 */
public class CharSequenceInputStream extends InputStream {

	private final CharsetEncoder encoder;
	private final CharBuffer cbuf;
	private final ByteBuffer bbuf;

	private int mark;

	/**
	 * Constructor.
	 * 
	 * @param s
	 *            the input character sequence
	 * @param charset
	 *            the character set name to use
	 * @param bufferSize
	 *            the buffer size to use.
	 */
	public CharSequenceInputStream(final CharSequence s, final Charset charset, int bufferSize) {
		super();
		encoder = charset.newEncoder()
				.onMalformedInput(CodingErrorAction.REPLACE)
				.onUnmappableCharacter(CodingErrorAction.REPLACE);
		bbuf = ByteBuffer.allocate(bufferSize);
		bbuf.flip();
		cbuf = CharBuffer.wrap(s);
		mark = -1;
	}

	/**
	 * Constructor, calls
	 * {@link #CharSequenceInputStream(CharSequence, Charset, int)}.
	 * 
	 * @param s
	 *            the input character sequence
	 * @param charset
	 *            the character set name to use
	 * @param bufferSize
	 *            the buffer size to use.
	 */
	public CharSequenceInputStream(final CharSequence s, final String charset, int bufferSize) {
		this(s, Charset.forName(charset), bufferSize);
	}

	/**
	 * Constructor, calls
	 * {@link #CharSequenceInputStream(CharSequence, Charset, int)} with a
	 * buffer size of 2048.
	 * 
	 * @param s
	 *            the input character sequence
	 * @param charset
	 *            the character set name to use
	 */
	public CharSequenceInputStream(final CharSequence s, final Charset charset) {
		this(s, charset, 2048);
	}

	/**
	 * Constructor, calls
	 * {@link #CharSequenceInputStream(CharSequence, String, int)} with a buffer
	 * size of 2048.
	 * 
	 * @param s
	 *            the input character sequence
	 * @param charset
	 *            the character set name to use
	 */
	public CharSequenceInputStream(final CharSequence s, final String charset) {
		this(s, charset, 2048);
	}

	/**
	 * Fills the byte output buffer from the input char buffer.
	 * 
	 * @throws CharacterCodingException
	 *             an error encoding data
	 */
	private void fillBuffer() throws CharacterCodingException {
		bbuf.compact();
		CoderResult result = encoder.encode(cbuf, bbuf, true);
		if (result.isError()) {
			result.throwException();
		}
		bbuf.flip();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException("Byte array is null");
		}
		if (len < 0 || (off + len) > b.length) {
			throw new IndexOutOfBoundsException("Array Size=" + b.length +
					", offset=" + off + ", length=" + len);
		}
		if (len == 0) {
			return 0; // must return 0 for zero length read
		}
		if (!bbuf.hasRemaining() && !cbuf.hasRemaining()) {
			return -1;
		}
		int bytesRead = 0;
		while (len > 0) {
			if (bbuf.hasRemaining()) {
				int chunk = Math.min(bbuf.remaining(), len);
				bbuf.get(b, off, chunk);
				off += chunk;
				len -= chunk;
				bytesRead += chunk;
			}
			else {
				fillBuffer();
				if (!bbuf.hasRemaining() && !cbuf.hasRemaining()) {
					break;
				}
			}
		}
		return bytesRead == 0 && !cbuf.hasRemaining() ? -1 : bytesRead;
	}

	@Override
	public int read() throws IOException {
		for (;;) {
			if (bbuf.hasRemaining()) {
				return bbuf.get() & 0xFF;
			}
			else {
				fillBuffer();
				if (!bbuf.hasRemaining() && !cbuf.hasRemaining()) {
					return -1;
				}
			}
		}
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public long skip(long n) throws IOException {
		int skipped = 0;
		while (n > 0 && cbuf.hasRemaining()) {
			cbuf.get();
			n--;
			skipped++;
		}
		return skipped;
	}

	@Override
	public int available() throws IOException {
		return cbuf.remaining();
	}

	@Override
	public void close() throws IOException {}

	/**
	 * {@inheritDoc}
	 * 
	 * @param readlimit
	 *            max read limit (ignored)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		mark = cbuf.position();
	}

	@Override
	public synchronized void reset() throws IOException {
		if (mark != -1) {
			cbuf.position(mark);
			mark = -1;
		}
	}

	@Override
	public boolean markSupported() {
		return true;
	}

}
