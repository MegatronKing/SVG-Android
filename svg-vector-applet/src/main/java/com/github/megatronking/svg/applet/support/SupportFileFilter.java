package com.github.megatronking.svg.applet.support;

import com.github.megatronking.svg.applet.R;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SupportFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return new SvgFileFilter().accept(f) || new VectorFileFilter().accept(f);
    }

    @Override
    public String getDescription() {
        return R.string.support_filter;
    }
}
