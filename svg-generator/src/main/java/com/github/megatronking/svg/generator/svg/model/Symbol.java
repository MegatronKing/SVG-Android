package com.github.megatronking.svg.generator.svg.model;

/**
 * The symbol element is used to define graphical template objects which can be instantiated by a use element.
 *
 * @author Megatron King
 * @since 2017/1/12 9:53
 */

public class Symbol extends G {

    public boolean isInUse;

    // Not support this now
    public float[] viewBox;

    @Override
    public boolean isValid() {
        return isInUse;
    }

}
