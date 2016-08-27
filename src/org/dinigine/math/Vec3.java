package org.dinigine.math;

import java.nio.FloatBuffer;

public class Vec3 {

	public float x, y, z;

	public Vec3() {}

	public Vec3(Vec3 src) {
		set(src);
	}

	public Vec3(FloatBuffer buf) {
		load(buf);
	}

	public Vec3(float x, float y) {
		set(x, y);
	}

	public Vec3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vec3 set(Vec3 src) {
		x = src.x;
		y = src.y;
		z = src.z;
		return this;
	}

	public Vec3 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec3 store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		buf.put(z);
		return this;
	}

	public Vec3 load(FloatBuffer buf) {
		x = buf.get();
		y = buf.get();
		z = buf.get();
		return this;
	}

	public float length() {
		return Maths.sqrt(lengthSquared());
	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public Vec3 normalize() {
		float l = length();
		if (l != 0) {
			mul(1 / l);
		}
		return this;
	}

	public Vec3 negate() {
		return mul(-1);
	}

	public Vec3 zero() {
		return mul(0);
	}

	public float dot(Vec3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	public Vec3 cross(Vec3 right, Vec3 dest) {
		dest.x = y * right.z - z * right.y;
		dest.y = right.x * z - right.z * x;
		dest.z = x * right.y - y * right.x;
		return dest;
	}

	public float dist(Vec3 other) {
		float xa = other.x - x;
		float ya = other.y - y;
		float za = other.z - z;
		float lenSq = xa * xa + ya * ya + za * za;

		return Maths.sqrt(lenSq);
	}

	public Vec3 transform(Mat4 m, Vec3 out) {
		out.x = m.m00 * x + m.m10 * y + m.m20 * z;
		out.y = m.m01 * x + m.m11 * y + m.m21 * z;
		out.z = m.m02 * x + m.m12 * y + m.m22 * z;
		return out;
	}

	public Vec3 add(Vec3 other) {
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}

	public Vec3 add(float n) {
		x += n;
		y += n;
		z += n;
		return this;
	}

	public Vec3 sub(Vec3 other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}

	public Vec3 sub(float n) {
		x -= n;
		y -= n;
		z -= n;
		return this;
	}

	public Vec3 mul(Vec3 other) {
		x *= other.x;
		y *= other.y;
		z *= other.z;
		return this;
	}

	public Vec3 mul(float n) {
		x *= n;
		y *= n;
		z *= n;
		return this;
	}

	public Vec3 div(Vec3 other) {
		if (other.x != 0) x /= other.x;
		if (other.y != 0) y /= other.y;
		if (other.z != 0) z /= other.z;
		return this;
	}

	public Vec3 div(float n) {
		if (n != 0) {
			x /= n;
			y /= n;
			z /= n;
		}
		return this;
	}

	public Vec3 copy() {
		return new Vec3(this);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

	@Override
	public int hashCode() {
		int hash = 31 + Float.floatToIntBits(x);
		hash = hash * 31 + Float.floatToIntBits(y);
		return hash * 31 + Float.floatToIntBits(z);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o instanceof Vec3) {
			Vec3 v = (Vec3) o;
			return (x == v.x) && (y == v.y) && (z == v.z);
		}
		return false;
	}

}
