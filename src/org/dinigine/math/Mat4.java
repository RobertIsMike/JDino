package org.dinigine.math;

import java.nio.FloatBuffer;

public class Mat4 {

	/** Column major */
	public float
			m00, m01, m02, m03,
			m10, m11, m12, m13,
			m20, m21, m22, m23,
			m30, m31, m32, m33;

	public Mat4() {
		identity();
	}

	public Mat4(Mat4 src) {
		set(src);
	}

	public Mat4 identity() {
		m00 = 1;
		m01 = 0;
		m02 = 0;
		m03 = 0;
		m10 = 0;
		m11 = 1;
		m12 = 0;
		m13 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 1;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 1;
		return this;
	}

	public Mat4 zero() {
		m00 = 0;
		m01 = 0;
		m02 = 0;
		m03 = 0;
		m10 = 0;
		m11 = 0;
		m12 = 0;
		m13 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 0;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 0;
		return this;
	}

	public Mat4 negate() {
		m00 = -m00;
		m01 = -m01;
		m02 = -m02;
		m03 = -m03;
		m10 = -m10;
		m11 = -m11;
		m12 = -m12;
		m13 = -m13;
		m20 = -m20;
		m21 = -m21;
		m22 = -m22;
		m23 = -m23;
		m30 = -m30;
		m31 = -m31;
		m32 = -m32;
		m33 = -m33;
		return this;
	}

	public Mat4 set(Mat4 src) {
		m00 = src.m00;
		m01 = src.m01;
		m02 = src.m02;
		m03 = src.m03;
		m10 = src.m10;
		m11 = src.m11;
		m12 = src.m12;
		m13 = src.m13;
		m20 = src.m20;
		m21 = src.m21;
		m22 = src.m22;
		m23 = src.m23;
		m30 = src.m30;
		m31 = src.m31;
		m32 = src.m32;
		m33 = src.m33;
		return this;
	}

	public Mat4 load(FloatBuffer buf) {
		m00 = buf.get();
		m01 = buf.get();
		m02 = buf.get();
		m03 = buf.get();
		m10 = buf.get();
		m11 = buf.get();
		m12 = buf.get();
		m13 = buf.get();
		m20 = buf.get();
		m21 = buf.get();
		m22 = buf.get();
		m23 = buf.get();
		m30 = buf.get();
		m31 = buf.get();
		m32 = buf.get();
		m33 = buf.get();
		return this;
	}

	public Mat4 loadTranspose(FloatBuffer buf) {
		m00 = buf.get();
		m10 = buf.get();
		m20 = buf.get();
		m30 = buf.get();
		m01 = buf.get();
		m11 = buf.get();
		m21 = buf.get();
		m31 = buf.get();
		m02 = buf.get();
		m12 = buf.get();
		m22 = buf.get();
		m32 = buf.get();
		m03 = buf.get();
		m13 = buf.get();
		m23 = buf.get();
		m33 = buf.get();
		return this;
	}

	public Mat4 store(FloatBuffer buf) {
		buf.put(m00).put(m01).put(m02).put(m03);
		buf.put(m10).put(m11).put(m12).put(m13);
		buf.put(m20).put(m21).put(m22).put(m23);
		buf.put(m30).put(m31).put(m32).put(m33);
		return this;
	}

	public Mat4 storeTranspose(FloatBuffer buf) {
		buf.put(m00).put(m10).put(m20).put(m30);
		buf.put(m01).put(m11).put(m21).put(m31);
		buf.put(m02).put(m12).put(m22).put(m32);
		buf.put(m03).put(m13).put(m23).put(m33);
		return this;
	}

	public Mat4 add(Mat4 right) {
		m00 += right.m00;
		m01 += right.m01;
		m02 += right.m02;
		m03 += right.m03;
		m10 += right.m10;
		m11 += right.m11;
		m12 += right.m12;
		m13 += right.m13;
		m20 += right.m20;
		m21 += right.m21;
		m22 += right.m22;
		m23 += right.m23;
		m30 += right.m30;
		m31 += right.m31;
		m32 += right.m32;
		m33 += right.m33;
		return this;
	}

	public Mat4 sub(Mat4 right) {
		m00 -= right.m00;
		m01 -= right.m01;
		m02 -= right.m02;
		m03 -= right.m03;
		m10 -= right.m10;
		m11 -= right.m11;
		m12 -= right.m12;
		m13 -= right.m13;
		m20 -= right.m20;
		m21 -= right.m21;
		m22 -= right.m22;
		m23 -= right.m23;
		m30 -= right.m30;
		m31 -= right.m31;
		m32 -= right.m32;
		m33 -= right.m33;
		return this;
	}

	public Mat4 mul(Mat4 right) {
		float _m00 = m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03;
		float _m01 = m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03;
		float _m02 = m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03;
		float _m03 = m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03;
		
		float _m10 = m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13;
		float _m11 = m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13;
		float _m12 = m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13;
		float _m13 = m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13;
		
		float _m20 = m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23;
		float _m21 = m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23;
		float _m22 = m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23;
		float _m23 = m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23;
		
		float _m30 = m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33;
		float _m31 = m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33;
		float _m32 = m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33;
		float _m33 = m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33;
		m00 = _m00;
		m01 = _m01;
		m02 = _m02;
		m03 = _m03;
		m10 = _m10;
		m11 = _m11;
		m12 = _m12;
		m13 = _m13;
		m20 = _m20;
		m21 = _m21;
		m22 = _m22;
		m23 = _m23;
		m30 = _m30;
		m31 = _m31;
		m32 = _m32;
		m33 = _m33;
		return this;
	}

	public Mat4 translate(Vec3 v) {
		return translate(v.x, v.y, v.z);
	}

	public Mat4 translate(float x, float y, float z) {
		m30 += m00 * x + m10 * y + m20 * z;
		m31 += m01 * x + m11 * y + m21 * z;
		m32 += m02 * x + m12 * y + m22 * z;
		m33 += m03 * x + m13 * y + m23 * z;
		return this;
	}

	public Mat4 translate(Vec2 v) {
		return translate(v.x, v.y);
	}

	public Mat4 translate(float x, float y) {
		m30 += m00 * x + m10 * y;
		m31 += m01 * x + m11 * y;
		m32 += m02 * x + m12 * y;
		m33 += m03 * x + m13 * y;
		return this;
	}

	public Mat4 scale(float scale) {
		m00 *= scale;
		m01 *= scale;
		m02 *= scale;
		m03 *= scale;
		m10 *= scale;
		m11 *= scale;
		m12 *= scale;
		m13 *= scale;
		m20 *= scale;
		m21 *= scale;
		m22 *= scale;
		m23 *= scale;
		return this;
	}

	public Mat4 scale(Vec3 v) {
		return scale(v.x, v.y, v.z);
	}

	public Mat4 scale(float x, float y, float z) {
		m00 *= x;
		m01 *= x;
		m02 *= x;
		m03 *= x;
		m10 *= y;
		m11 *= y;
		m12 *= y;
		m13 *= y;
		m20 *= z;
		m21 *= z;
		m22 *= z;
		m23 *= z;
		return this;
	}

	public Mat4 rotate(Vec3 v) {
		return rotate(v.x, v.y, v.z);
	}

	public Mat4 rotate(float x, float y, float z) {
		if (x != 0) rotateX(x);
		if (y != 0) rotateY(y);
		if (z != 0) rotateZ(z);
		return this;
	}

	public Mat4 rotateX(float angle) {
		float sin = Maths.sind(angle);
		float cos = Maths.cosd(angle);
		float _m10 = m10 * cos + m20 * sin;
		float _m11 = m11 * cos + m21 * sin;
		float _m12 = m12 * cos + m22 * sin;
		float _m13 = m13 * cos + m23 * sin;
		float _m20 = m10 * -sin + m20 * cos;
		float _m21 = m11 * -sin + m21 * cos;
		float _m22 = m12 * -sin + m22 * cos;
		float _m23 = m13 * -sin + m23 * cos;
		m10 = _m10;
		m11 = _m11;
		m12 = _m12;
		m13 = _m13;
		m20 = _m20;
		m21 = _m21;
		m22 = _m22;
		m23 = _m23;
		return this;
	}

	public Mat4 rotateY(float angle) {
		float sin = Maths.sind(angle);
		float cos = Maths.cosd(angle);
		float _m00 = m00 * cos + m20 * -sin;
		float _m01 = m01 * cos + m21 * -sin;
		float _m02 = m02 * cos + m22 * -sin;
		float _m03 = m03 * cos + m23 * -sin;
		float _m20 = m00 * sin + m20 * cos;
		float _m21 = m01 * sin + m21 * cos;
		float _m22 = m02 * sin + m22 * cos;
		float _m23 = m03 * sin + m23 * cos;
		m00 = _m00;
		m01 = _m01;
		m02 = _m02;
		m03 = _m03;
		m20 = _m20;
		m21 = _m21;
		m22 = _m22;
		m23 = _m23;
		return this;
	}

	public Mat4 rotateZ(float angle) {
		float sin = Maths.sind(angle);
		float cos = Maths.cosd(angle);
		float _m00 = m00 * cos + m10 * sin;
		float _m01 = m01 * cos + m11 * sin;
		float _m02 = m02 * cos + m12 * sin;
		float _m03 = m03 * cos + m13 * sin;
		float _m10 = m00 * -sin + m10 * cos;
		float _m11 = m01 * -sin + m11 * cos;
		float _m12 = m02 * -sin + m12 * cos;
		float _m13 = m03 * -sin + m13 * cos;
		m00 = _m00;
		m01 = _m01;
		m02 = _m02;
		m03 = _m03;
		m10 = _m10;
		m11 = _m11;
		m12 = _m12;
		m13 = _m13;
		return this;
	}

	public Mat4 ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
		identity();

		float rl = right - left;
		float tb = top - bottom;
		float dz = zFar - zNear;

		if (rl * tb * dz == 0) return this;

		m30 = (right + left) / -rl;
		m31 = (top + bottom) / -tb;
		m32 = (zFar + zNear) / -dz;
		m00 = 2 / rl;
		m11 = 2 / tb;
		m22 = -2 / dz;

		return this;
	}

	public Mat4 perspective(float fov, float aspect, float zNear, float zFar) {
		float cotangent = Maths.cotd(fov * .5f);
		float zDelta = zFar - zNear;

		identity();
		m00 = cotangent / aspect;
		m11 = cotangent;
		m22 = -(zFar + zNear) / zDelta;
		m23 = -1;
		m32 = -2 * zNear * zFar / zDelta;
		m33 = 0;

		return this;
	}

	public Mat4 transpose() {
		float _m01 = m10;
		float _m02 = m20;
		float _m03 = m30;
		float _m10 = m01;
		float _m12 = m21;
		float _m13 = m31;
		float _m20 = m02;
		float _m21 = m12;
		float _m23 = m32;
		float _m30 = m03;
		float _m31 = m13;
		float _m32 = m23;
		m01 = _m01;
		m02 = _m02;
		m03 = _m03;
		m10 = _m10;
		m12 = _m12;
		m13 = _m13;
		m20 = _m20;
		m21 = _m21;
		m23 = _m23;
		m30 = _m30;
		m31 = _m31;
		m32 = _m32;
		return this;
	} 

	public float determinant() {
		float d = m00 * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
				- m13 * m22 * m31
				- m11 * m23 * m32
				- m12 * m21 * m33);
		d -= m01 * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
				- m13 * m22 * m30
				- m10 * m23 * m32
				- m12 * m20 * m33);
		d += m02 * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
				- m13 * m21 * m30
				- m10 * m23 * m31
				- m11 * m20 * m33);
		d -= m03 * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
				- m12 * m21 * m30
				- m10 * m22 * m31
				- m11 * m20 * m32);
		return d;
	}

	public Mat4 invert() {
		float d = determinant();
		if (d != 0) {
			float nd = 1 / d;
			Mat4 tmp = new Mat4();
			tmp.m00 = nd * deterMat3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
			tmp.m01 = nd * -deterMat3(m10, m12, m13, m20, m22, m23, m30, m32, m33);
			tmp.m02 = nd * deterMat3(m10, m11, m13, m20, m21, m23, m30, m31, m33);
			tmp.m03 = nd * -deterMat3(m10, m11, m12, m20, m21, m22, m30, m31, m32);
			tmp.m10 = nd * -deterMat3(m01, m02, m03, m21, m22, m23, m31, m32, m33);
			tmp.m11 = nd * deterMat3(m00, m02, m03, m20, m22, m23, m30, m32, m33);
			tmp.m12 = nd * -deterMat3(m00, m01, m03, m20, m21, m23, m30, m31, m33);
			tmp.m13 = nd * deterMat3(m00, m01, m02, m20, m21, m22, m30, m31, m32);
			tmp.m20 = nd * deterMat3(m01, m02, m03, m11, m12, m13, m31, m32, m33);
			tmp.m21 = nd * -deterMat3(m00, m02, m03, m10, m12, m13, m30, m32, m33);
			tmp.m22 = nd * deterMat3(m00, m01, m03, m10, m11, m13, m30, m31, m33);
			tmp.m23 = nd * -deterMat3(m00, m01, m02, m10, m11, m12, m30, m31, m32);
			tmp.m30 = nd * -deterMat3(m01, m02, m03, m11, m12, m13, m21, m22, m23);
			tmp.m31 = nd * deterMat3(m00, m02, m03, m10, m12, m13, m20, m22, m23);
			tmp.m32 = nd * -deterMat3(m00, m01, m03, m10, m11, m13, m20, m21, m23);
			tmp.m33 = nd * deterMat3(m00, m01, m02, m10, m11, m12, m20, m21, m22);
			set(tmp).transpose();
		}
		return this;
	}

	static float deterMat3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21)
				+ t01 * (t12 * t20 - t10 * t22)
				+ t02 * (t10 * t21 - t11 * t20);
	}

	public Mat4 copy() {
		return new Mat4(this);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(m00).append(' ').append(m10).append(' ').append(m20).append(' ').append(m30).append('\n')
				.append(m01).append(' ').append(m11).append(' ').append(m21).append(' ').append(m31).append('\n')
				.append(m02).append(' ').append(m12).append(' ').append(m22).append(' ').append(m32).append('\n')
				.append(m03).append(' ').append(m13).append(' ').append(m23).append(' ').append(m33)
				.toString();
	}

}
