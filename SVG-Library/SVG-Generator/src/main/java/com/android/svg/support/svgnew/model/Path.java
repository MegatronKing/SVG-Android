package com.android.svg.support.svgnew.model;

/**
 * The path element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/23 11:10
 */

public class Path extends SvgNode {

    public String d;

    @Override
    public void toPath() {
        pathData =  d == null ? null : d.replaceAll("(\\d)-", "$1,-");
    }

}
