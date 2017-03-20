/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.megatronking.svg.generator.utils;

import java.io.PrintWriter;

/**
 * The Matrix class holds a 3x3 matrix for transforming coordinates.
 */
public class Matrix {

    public static final int MSCALE_X = 0;   //!< use with getValues/setValues
    public static final int MSKEW_X  = 1;   //!< use with getValues/setValues
    public static final int MTRANS_X = 2;   //!< use with getValues/setValues
    public static final int MSKEW_Y  = 3;   //!< use with getValues/setValues
    public static final int MSCALE_Y = 4;   //!< use with getValues/setValues
    public static final int MTRANS_Y = 5;   //!< use with getValues/setValues
    public static final int MPERSP_0 = 6;   //!< use with getValues/setValues
    public static final int MPERSP_1 = 7;   //!< use with getValues/setValues
    public static final int MPERSP_2 = 8;   //!< use with getValues/setValues

    private final float[] MATRIX = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};

    /**
     * (deep) copy the src matrix into this matrix. If src is null, reset this
     * matrix to the identity matrix.
     *
     * @param src The source matrix
     */
    public void set(Matrix src) {
        if (src == null) {
            reset();
        } else {
            float[] values = new float[9];
            src.getValues(values);
            setValues(values);
        }
    }

    /** Set the matrix to identity */
    public void reset() {
        MATRIX[MSCALE_X] = 1;
        MATRIX[MSKEW_X] = 0;
        MATRIX[MTRANS_X] = 0;
        MATRIX[MSKEW_Y] = 1;
        MATRIX[MSCALE_Y] = 0;
        MATRIX[MTRANS_Y] = 0;
        MATRIX[MPERSP_0] = 0;
        MATRIX[MPERSP_1] = 0;
        MATRIX[MPERSP_2] = 1;
    }

    /**
     * Preconcats the matrix with the specified matrix.
     * M' = M * other
     *
     * @param other The right matrix
     */
    public void preConcat(Matrix other) {
        float[] otherValue = new float[9];
        other.getValues(otherValue);
        float[] newValue = new float[9];
        newValue[MSCALE_X] = MATRIX[MSCALE_X] * otherValue[MSCALE_X] + MATRIX[MSKEW_X] * otherValue[MSKEW_Y]
                + MATRIX[MTRANS_X] * otherValue[MPERSP_0];
        newValue[MSKEW_X] = MATRIX[MSCALE_X] * otherValue[MSKEW_X] + MATRIX[MSKEW_X] * otherValue[MSCALE_Y]
                + MATRIX[MTRANS_X] * otherValue[MPERSP_1];
        newValue[MTRANS_X] = MATRIX[MSCALE_X] * otherValue[MTRANS_X] + MATRIX[MSKEW_X] * otherValue[MTRANS_Y]
                + MATRIX[MTRANS_X] * otherValue[MPERSP_2];
        newValue[MSKEW_Y] = MATRIX[MSKEW_Y] * otherValue[MSCALE_X] + MATRIX[MSCALE_Y] * otherValue[MSKEW_Y]
                + MATRIX[MTRANS_Y] * otherValue[MPERSP_0];
        newValue[MSCALE_Y] = MATRIX[MSKEW_Y] * otherValue[MSKEW_X] + MATRIX[MSCALE_Y] * otherValue[MSCALE_Y]
                + MATRIX[MTRANS_Y] * otherValue[MPERSP_1];
        newValue[MTRANS_Y] = MATRIX[MSKEW_Y] * otherValue[MTRANS_X] + MATRIX[MSCALE_Y] * otherValue[MTRANS_Y]
                + MATRIX[MTRANS_Y] * otherValue[MPERSP_2];
        newValue[MPERSP_0] = MATRIX[MPERSP_0] * otherValue[MSCALE_X] + MATRIX[MPERSP_1] * otherValue[MSKEW_Y]
                + MATRIX[MPERSP_2] * otherValue[MPERSP_0];
        newValue[MPERSP_1] = MATRIX[MPERSP_0] * otherValue[MSKEW_X] + MATRIX[MPERSP_1] * otherValue[MSCALE_Y]
                + MATRIX[MPERSP_2] * otherValue[MPERSP_1];
        newValue[MPERSP_2] = MATRIX[MPERSP_0] * otherValue[MTRANS_X] + MATRIX[MPERSP_1] * otherValue[MTRANS_Y]
                + MATRIX[MPERSP_2] * otherValue[MPERSP_2];
        setValues(newValue);
    }

    /**
     * Postconcats the matrix with the specified translation.
     * M' = T(dx, dy) * M
     *
     * @param dx translate x
     * @param dy translate y
     */
    public void postTranslate(float dx, float dy) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[] {1, 0, dx, 0, 1, dy, 0, 0, 1});
        Matrix current = new Matrix();
        current.setValues(MATRIX);
        matrix.preConcat(current);
        set(matrix);
    }

    /**
     * Postconcats the matrix with the specified scale.
     * M' = S(sx, sy) * M
     *
     * @param sx scale x
     * @param sy scale y
     */
    public void postScale(float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[] {sx, 0, 0, 0, sy, 0, 0, 0, 1});
        Matrix current = new Matrix();
        current.setValues(MATRIX);
        matrix.preConcat(current);
        set(matrix);
    }

    /**
     * Postconcats the matrix with the specified rotation.
     * M' = R(degrees, px, py) * M
     *
     * @param degrees rotate degree
     * @param px pivot x
     * @param py pivot y
     */
    public void postRotate(float degrees, float px, float py) {
        double radians = Math.toRadians(degrees);
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);
        Matrix matrix = new Matrix();
        matrix.setValues(new float[] {cos, -sin, - px * cos + py * sin + px, sin, cos, - px * sin - py * cos + py, 0, 0, 1});
        Matrix current = new Matrix();
        current.setValues(MATRIX);
        matrix.preConcat(current);
        set(matrix);
    }

    public void setTranslate(float dx, float dy) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[] {1, 0, dx, 0, 1, dy, 0, 0, 1});
        matrix.getValues(MATRIX);
    }

    public void setRotate(float degrees, float px, float py) {
        Matrix matrix = new Matrix();
        double radians = Math.toRadians(degrees);
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);
        matrix.setValues(new float[] {cos, -sin, - px * cos + py * sin + px, sin, cos, - px * sin - py * cos + py, 0, 0, 1});
        matrix.getValues(MATRIX);
    }

    public void setScale(float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[] {sx, 0, 0, 0, sy, 0, 0, 0, 1});
        matrix.getValues(MATRIX);
    }

    /**
     * Apply this matrix to the array of 2D vectors, and write the transformed
     * vectors back into the array.
     *
     * Note: this method does not apply the translation associated with the matrix.
     *
     * @param vecs a coupe of coordinates
     */
    public void mapVectors(float[] vecs) {
        if (vecs == null || vecs.length % 2 != 0) {
            return;
        }
        for (int i = 0; i < vecs.length / 2; i++) {
            float[] result = mapVector(vecs[i * 2], vecs[i * 2 + 1]);
            vecs[i * 2] = result[0];
            vecs[i * 2 + 1] = result[1];
        }
    }

    /**
     * Apply this matrix to the array of 2D vectors, and write the transformed
     * vectors back into the array.
     *
     * Note: this method does not apply the translation associated with the matrix.
     *
     * @param x coordinate x
     * @param y coordinate y
     * @return a coordinate arrays
     */
    public float[] mapVector(float x, float y) {
        float[] result = new float[] {x, y};
        result[0] = MATRIX[MSCALE_X] * x + MATRIX[MSKEW_X] * y;
        result[1] = MATRIX[MSKEW_Y] * x + MATRIX[MSCALE_Y] * y;
        return result;
    }

    /**
     * Copy 9 values from the matrix into the array.
     * @param values A source nine length arrays
     */
    public void getValues(float[] values) {
        if (values.length < 9) {
            throw new ArrayIndexOutOfBoundsException();
        }
        values[MSCALE_X] = MATRIX[MSCALE_X];
        values[MSKEW_X] = MATRIX[MSKEW_X];
        values[MTRANS_X] = MATRIX[MTRANS_X];
        values[MSKEW_Y] = MATRIX[MSKEW_Y];
        values[MSCALE_Y] = MATRIX[MSCALE_Y];
        values[MTRANS_Y] = MATRIX[MTRANS_Y];
        values[MPERSP_0] = MATRIX[MPERSP_0];
        values[MPERSP_1] = MATRIX[MPERSP_1];
        values[MPERSP_2] = MATRIX[MPERSP_2];
    }

    /**
     * Copy 9 values from the array into the matrix.
     * Depending on the implementation of Matrix, these may be
     * transformed into 16.16 integers in the Matrix, such that
     * a subsequent call to getValues() will not yield exactly
     * the same values.
     *
     * @param values nine length float arrays
     */
    public void setValues(float[] values) {
        if (values.length < 9) {
            throw new ArrayIndexOutOfBoundsException();
        }
        MATRIX[MSCALE_X] = values[MSCALE_X];
        MATRIX[MSKEW_X] = values[MSKEW_X];
        MATRIX[MTRANS_X] = values[MTRANS_X];
        MATRIX[MSKEW_Y] = values[MSKEW_Y];
        MATRIX[MSCALE_Y] = values[MSCALE_Y];
        MATRIX[MTRANS_Y] = values[MTRANS_Y];
        MATRIX[MPERSP_0] = values[MPERSP_0];
        MATRIX[MPERSP_1] = values[MPERSP_1];
        MATRIX[MPERSP_2] = values[MPERSP_2];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Matrix{");
        toShortString(sb);
        sb.append('}');
        return sb.toString();

    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder(64);
        toShortString(sb);
        return sb.toString();
    }

    public void toShortString(StringBuilder sb) {
        float[] values = new float[9];
        getValues(values);
        sb.append('[');
        sb.append(values[0]); sb.append(", "); sb.append(values[1]); sb.append(", ");
        sb.append(values[2]); sb.append("][");
        sb.append(values[3]); sb.append(", "); sb.append(values[4]); sb.append(", ");
        sb.append(values[5]); sb.append("][");
        sb.append(values[6]); sb.append(", "); sb.append(values[7]); sb.append(", ");
        sb.append(values[8]); sb.append(']');
    }

    public void printShortString(PrintWriter pw) {
        float[] values = new float[9];
        getValues(values);
        pw.print('[');
        pw.print(values[0]); pw.print(", "); pw.print(values[1]); pw.print(", ");
        pw.print(values[2]); pw.print("][");
        pw.print(values[3]); pw.print(", "); pw.print(values[4]); pw.print(", ");
        pw.print(values[5]); pw.print("][");
        pw.print(values[6]); pw.print(", "); pw.print(values[7]); pw.print(", ");
        pw.print(values[8]); pw.print(']');
    }
}
