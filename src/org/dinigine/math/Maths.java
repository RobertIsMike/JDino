package org.dinigine.math;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Robert Myers
 */
public final class Maths {

	/** Won't instantiate unless needed */
	private static class RandomHolder {
		static final Random random = new Random();
	}
	/** Won't instantiate unless needed */
	private static class FormatterHolder {
		static final DecimalFormat formatter = new DecimalFormat("#.##");
	}

	/** Static only */
	private Maths() {}

	/** The ratio of the circumference of a circle to its diameter */
	public static final float PI = 3.1415927f;
	/** 2π (360°) */
	public static final float PI2 = 6.2831855f;

	static final int LUT_ACCURACY = 16; // accuracy of the sine table
	static final int LUT_MASK = ~(-1 << LUT_ACCURACY);
	static final int LUT_SIZE = LUT_MASK + 1;

	static final float[] SIN_LUT = new float[LUT_SIZE];

	static {
		for (int i = 1; i < LUT_SIZE; i++) {
			SIN_LUT[i] = (float) StrictMath.sin(i * PI2 / LUT_SIZE);
		}
	}

	/**
	 * @return a random float (0->1)
	 */
	public static float randFloat() {
		return RandomHolder.random.nextFloat();
	}

	/**
	 * @param bound
	 *            - the most the number can be
	 * @return a random float (0->bound)
	 */
	public static float randFloat(float bound) {
		return randFloat() * bound;
	}

	/**
	 * @return a random integer
	 */
	public static int randInt() {
		return RandomHolder.random.nextInt();
	}

	/**
	 * @param bound
	 *            - the most the number can be
	 * @return a random integer (0->bound)
	 */
	public static int randInt(int bound) {
		return RandomHolder.random.nextInt(bound + 1);
	}

	/**
	 * @param n
	 *            - the number
	 * @return true if the number is even
	 */
	public static boolean isEven(int n) {
		return (n & 1) == 0;
	}

	/**
	 * @param n
	 *            - the number
	 * @return true if the number is prime
	 */
	public static boolean isPrime(int n) {
		if (n > 2 && isEven(n)) return false;
		for (int j = 3; j * j <= n; j += 2)
			if (n % j == 0) return false;
		return true;
	}

	/**
	 * @param n
	 *            - a number
	 * @return the power of 2 closest to {@code n}.
	 */
	public static int ceilPoT(int n) {
		int pot = 2;
		while (pot < n)
			pot <<= 1;
		return pot;
	}

	/**
	 * Linear Interpolation
	 *
	 * <p>
	 * Find a value at a specific point between two values.
	 *
	 * @param a
	 *            - the first value
	 * @param b
	 *            - the second value
	 * @param t
	 *            - the point between [0 -> 1]
	 */
	public static float lerp(float a, float b, float t) {
		return (1 - t) * a + t * b;
	}
	
	/**
	 * Barry Centric Triangular Interpolation
	 * 
	 * @param v0 - the 1st vertex
	 * @param v1 - the 2nd vertex
	 * @param v2 - the 3rd vertex
	 * @param p - the point
	 * @return
	 */
	public static float barryCentric(Vec3 v0, Vec3 v1, Vec3 v2, Vec2 p) {
		float d = (v1.z - v2.z) * (v0.x - v2.x) + (v2.x - v1.x) * (v0.z - v2.z);
		float l1 = ((v1.z - v2.z) * (p.x - v2.x) + (v2.x - v1.x) * (p.y - v2.z)) / d;
		float l2 = ((v2.z - v0.z) * (p.x - v2.x) + (v0.x - v2.x) * (p.y - v2.z)) / d;
		float l3 = 1.0f - l1 - l2;
		return l1 * v0.y + l2 * v1.y + l3 * v2.y;
	}

	/**
	 * @param a
	 *            - first number
	 * @param b
	 *            - second number
	 * @return a to the b<sup>th</sup> power
	 */
	public static float pow(float a, float b) {
		return (float) StrictMath.pow(a, b);
	}
	
	/**
	 * @param a
	 *            - the number
	 * @return The natural logarithm of {@code a}.
	 */
	public static float log(float a) {
		return (float) StrictMath.log(a);
	}

	/**
	 * @param a
	 *            - the number
	 * @return The square root of {@code a}.
	 */
	public static float sqrt(float a) {
		return (float) StrictMath.sqrt(a);
	}

	/**
	 * @param a
	 *            - the number
	 * @return The largest (closest to positive infinity) integer value less
	 *         than or equal to {@code a}.
	 */
	public static int floor(float a) {
		int i = (int) a;
		return a < i ? i - 1 : i;
	}

	/**
	 * @param a
	 *            - the number
	 * @return The smallest (closest to negative infinity) integer value greater
	 *         than or equal to {@code a}.
	 */
	public static int ceil(float a) {
		int i = (int) a;
		return a > i ? i + 1 : i;
	}

	/**
	 * @param a
	 *            - the number
	 * @return the absolute value of {@code a}.
	 */
	public static float abs(float a) {
		return a < 0 ? -a : a;
	}

	/**
	 * @param a
	 *            - the number
	 * @return the absolute value of {@code a}.
	 */
	public static int abs(int a) {
		return a < 0 ? -a : a;
	}

	/**
	 * @param a
	 *            - the first number
	 * @param b
	 *            - the second number
	 * @return The smaller (closer to negative infinity) of the two numbers.
	 */
	public static float min(float a, float b) {
		return a < b ? a : b;
	}

	/**
	 * @param a
	 *            - the first number
	 * @param b
	 *            - the second number
	 * @return The smaller (closer to negative infinity) of the two numbers.
	 */
	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	/**
	 * @param a
	 *            - the first number
	 * @param b
	 *            - the second number
	 * @return The larger (closer to positive infinity) of the two numbers.
	 */
	public static float max(float a, float b) {
		return a > b ? a : b;
	}

	/**
	 * @param a
	 *            - the first number
	 * @param b
	 *            - the second number
	 * @return The larger (closer to positive infinity) of the two numbers.
	 */
	public static int max(int a, int b) {
		return a > b ? a : b;
	}

	/**
	 * <ul>
	 * <li>If {@code n} < {@code min}, {@code min} is returned
	 * <li>If {@code n} > {@code max}, {@code max} is returned
	 * <li>If {@code min} <u><</u> {@code n} <u>></u> {@code max}, {@code n} is
	 * returned
	 * </ul>
	 *
	 * @param n
	 *            - the number
	 * @param min
	 *            - the smallest acceptable value
	 * @param max
	 *            - the largest acceptable value
	 * @return The clamped value
	 */
	public static float clamp(float n, float min, float max) {
		return min(max(n, min), max);
	}

	/**
	 * <ul>
	 * <li>If {@code n} < {@code min}, {@code min} is returned
	 * <li>If {@code n} > {@code max}, {@code max} is returned
	 * <li>If {@code min} <u><</u> {@code n} <u>></u> {@code max}, {@code n} is
	 * returned
	 * </ul>
	 *
	 * @param n
	 *            - the number
	 * @param min
	 *            - the smallest acceptable value
	 * @param max
	 *            - the largest acceptable value
	 * @return The clamped value
	 */
	public static int clamp(int n, int min, int max) {
		return min(max(n, min), max);
	}

	/**
	 * @param rad
	 *            - theta in radians
	 * @return The trigonomic sine.
	 */
	public static float sinr(float rad) {
		return SIN_LUT[(int) (rad * LUT_SIZE / PI2) & LUT_MASK];
	}

	/**
	 * @param deg
	 *            - theta in degrees
	 * @return The trigonomic sine.
	 */
	public static float sind(float deg) {
		return sinr(deg * .0174532925f);
	}

	/**
	 * @param rad
	 *            - theta in radians
	 * @return The trigonomic cosine.
	 */
	public static float cosr(float rad) {
		// 1.5707964 is PI / 2
		return sinr(1.5707964f - rad);
	}

	/**
	 * @param deg
	 *            - theta in degrees
	 * @return The trigonomic cosine.
	 */
	public static float cosd(float deg) {
		return cosr(deg * .0174532925f);
	}

	/**
	 * @param rad
	 *            - theta in radians
	 * @return The trigonomic tangent.
	 */
	public static float tanr(float rad) {
		return sinr(rad) / cosr(rad);
	}

	/**
	 * @param deg
	 *            - theta in degrees
	 * @return The trigonomic tangent.
	 */
	public static float tand(float deg) {
		return tanr(deg * .0174532925f);
	}

	/**
	 * @param rad
	 *            - theta in radians
	 * @return The trigonomic cotangent.
	 */
	public static float cotr(float rad) {
		return cosr(rad) / sinr(rad);
	}

	/**
	 * @return the cotangent
	 */
	public static float cotd(float deg) {
		return cotr(deg * .0174532925f);
	}

	/**
	 * @return the secant
	 */
	public static float secr(float rad) {
		return 1f / cosr(rad);
	}

	/**
	 * @return the secant
	 */

	public static float secd(float deg) {
		return secr(deg * .0174532925f);
	}

	/**
	 * @return the cosecant
	 */

	public static float cscr(float rad) {
		return 1f / sinr(rad);
	}

	/**
	 * @return the cosecant
	 */

	public static float cscd(float deg) {
		return cscr(deg * .0174532925f);
	}

	/**
	 * @param deg
	 *            - angle in degrees
	 * @return The angle in radians
	 */
	public static float toRad(float deg) {
		return deg * .0174532925f;
	}

	/**
	 * @param rad
	 *            - angle in radians
	 * @return The angle in degrees
	 */
	public static float toDeg(float rad) {
		return rad / .0174532925f;
	}

	/**
	 * @param n
	 *            - a number
	 * @return a string value of the given number with only two leading decimal
	 *         places after the decimal
	 */
	public static String format(double n) {
		return FormatterHolder.formatter.format(n);
	}

	/**
	 * @param i
	 *            - the number
	 * @return The hexadecimal (radix 16) string representation of {@code i}.
	 */
	public static String toHex(int i) {
		return toUnsignedString(i, 4);
	}

	/**
	 * @param i
	 *            - the number
	 * @return The octal (radix 8) string representation of {@code i}.
	 */
	public static String toOct(int i) {
		return toUnsignedString(i, 3);
	}

	/**
	 * @param i
	 *            - the number
	 * @return The binary (radix 2) string representation of {@code i}.
	 */
	public static String toBin(int i) {
		return toUnsignedString(i, 1);
	}

	/** toString with given radix */
	private static String toUnsignedString(int i, int shift) {
		char[] buf = new char[32];
		int mask = ~(-1 << shift);
		int pos = 32;
		do {
			buf[--pos] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i & mask);
			i >>>= shift;
		} while (i != 0);
		return new String(buf, pos, 32 - pos);
	}

}
