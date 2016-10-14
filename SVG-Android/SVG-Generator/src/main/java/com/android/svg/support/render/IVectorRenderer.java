package com.android.svg.support.render;


import com.android.svg.support.writer.JavaClassWriter;

/**
 * Calculate the path value and export to canvas.
 *
 * @author Megatron King
 * @since 2016/9/2 13:49
 */

public interface IVectorRenderer<T> {

    String HEAD_SPACE = JavaClassWriter.HEAD_SPACE;

    void render(T t);

}
