package org.dinigine.math;

import java.nio.FloatBuffer;

public class Vec2 {
	public float x, y;

	public Vec2() {}

	public Vec2(Vec2 src) {
		set(src);
	}

	public Vec2(FloatBuffer buf) {
		load(buf);
	}

	public Vec2(float x, float y) {
		set(x, y);
	}

	public Vec2 set(Vec2 src) {
		x = src.x;
		y = src.y;
		return this;
	}

	public Vec2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec2 store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	public Vec2 load(FloatBuffer buf) {
		x = buf.get();
		y = buf.get();
		return this;
	}

	public float length() {
		return Maths.sqrt(lengthSquared());
	}

	public float lengthSquared() {
		return x * x + y * y;
	}

	public Vec2 normalize() {
		float l = length();
		if (l != 0) {
			return mul(1 / l);
		}
		return this;
	}

	public Vec2 negate() {
		return mul(-1);
	}

	public Vec2 zero() {
		return mul(0);
	}

	public float dist(Vec2 other) {
		float xa = other.x - x;
		float ya = other.y - y;
		float lenSq = xa * xa + ya * ya;

		return Maths.sqrt(lenSq);
	}

	public float dot(Vec2 other) {
		return x * other.x + y * other.y;
	}
	
	public Vec2 add(Vec2 other) {
		x += other.x;
		y += other.y;
		return this;
	}

	public Vec2 add(float n) {
		x += n;
		y += n;
		return this;
	}

	public Vec2 sub(Vec2 other) {
		x -= other.x;
		y -= other.y;
		return this;
	}

	public Vec2 sub(float n) {
		x -= n;
		y -= n;
		return this;
	}

	public Vec2 mul(Vec2 other) {
		x *= other.x;
		y *= other.y;
		return this;
	}

	public Vec2 mul(float n) {
		x *= n;
		y *= n;
		return this;
	}

	public Vec2 div(Vec2 other) {
		if (other.x != 0) x /= other.x;
		if (other.y != 0) y /= other.y;
		return this;
	}

	public Vec2 div(float n) {
		if (n != 0) {
			x /= n;
			y /= n;
		}
		return this;
	}

	public Vec2 copy() {
		return new Vec2(this);
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

	@Override
	public int hashCode() {
		int hash = 31 + Float.floatToIntBits(x);
		return hash * 31 + Float.floatToIntBits(y);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o instanceof Vec2) {
			Vec2 v = (Vec2) o;
			return (x == v.x) && (y == v.y);
		}
		return false;
	}

}
