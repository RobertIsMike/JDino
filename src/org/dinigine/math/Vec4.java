package org.dinigine.math;

import java.nio.FloatBuffer;

public class Vec4 {
	public float x, y, z, w;

	public Vec4() {}

	public Vec4(Vec4 src) {
		set(src);
	}

	public Vec4(FloatBuffer buf) {
		load(buf);
	}

	public Vec4(float x, float y) {
		set(x, y);
	}

	public Vec4(float x, float y, float z) {
		set(x, y, z);
	}

	public Vec4(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Vec4 set(Vec4 src) {
		x = src.x;
		y = src.y;
		z = src.z;
		w = src.w;
		return this;
	}

	public Vec4 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec4 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec4 set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Vec4 store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		buf.put(z);
		buf.put(w);
		return this;
	}

	public Vec4 load(FloatBuffer buf) {
		x = buf.get();
		y = buf.get();
		z = buf.get();
		w = buf.get();
		return this;
	}

	public float length() {
		return Maths.sqrt(lengthSquared());
	}

	public float lengthSquared() {
		return x * x + y * y + z * z + w * w;
	}

	public Vec4 normalize() {
		float l = length();
		if (l != 0) {
			return mul(1 / l);
		}
		return this;
	}

	public Vec4 negate() {
		return mul(-1);
	}

	public Vec4 zero() {
		return mul(0);
	}

	public float dist(Vec4 other) {
		float xa = other.x - x;
		float ya = other.y - y;
		float za = other.z - z;
		float wa = other.w - w;
		float lenSq = xa * xa + ya * ya + za * za + wa * wa;

		return Maths.sqrt(lenSq);
	}

	public Vec4 transform(Mat4 m, Vec4 out) {
		out.x = m.m00 * x + m.m10 * y + m.m20 * z + m.m30 * w;
		out.y = m.m01 * x + m.m11 * y + m.m21 * z + m.m31 * w;
		out.z = m.m02 * x + m.m12 * y + m.m22 * z + m.m32 * w;
		out.w = m.m03 * x + m.m13 * y + m.m23 * z + m.m33 * w;
		return out;
	} 

	public Vec4 add(Vec4 other) {
		x += other.x;
		y += other.y;
		z += other.z;
		w += other.w;
		return this;
	}

	public Vec4 add(float n) {
		x += n;
		y += n;
		z += n;
		w += n;
		return this;
	}

	public Vec4 sub(Vec4 other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		w -= other.w;
		return this;
	}

	public Vec4 sub(float n) {
		x -= n;
		y -= n;
		z -= n;
		w -= n;
		return this;
	}

	public Vec4 mul(Vec4 other) {
		x *= other.x;
		y *= other.y;
		z *= other.z;
		w *= other.w;
		return this;
	}

	public Vec4 mul(float n) {
		x *= n;
		y *= n;
		z *= n;
		w *= n;
		return this;
	}

	public Vec4 div(Vec4 other) {
		if (other.x != 0) x /= other.x;
		if (other.y != 0) y /= other.y;
		if (other.z != 0) z /= other.z;
		if (other.w != 0) w /= other.w;
		return this;
	}

	public Vec4 div(float n) {
		if (n != 0) {
			x /= n;
			y /= n;
			z /= n;
			w /= n;
		}
		return this;
	}

	public Vec4 copy() {
		return new Vec4(this);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z + " " + w;
	}

	@Override
	public int hashCode() {
		int hash = 31 + Float.floatToIntBits(x);
		hash = hash * 31 + Float.floatToIntBits(y);
		hash = hash * 31 + Float.floatToIntBits(z);
		return hash * 31 + Float.floatToIntBits(w);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o instanceof Vec4) {
			Vec4 v = (Vec4) o;
			return (x == v.x) && (y == v.y) && (z == v.z) && (w == v.w);
		}
		return false;
	}

}
