package com.android.svg.support;

import java.io.File;

public class Generator {

    public static void main(String[] args) {
        // Deprecated
        // Use the gradle plugin in your projects!
        File file = new File("D:\\git_tc\\SVG-Android\\svg-iconlibs\\toggle\\icon");
        for (File f : file.listFiles()) {
            if (f.getName().contains("_24px")) {
                f.renameTo(new File(f.getPath().replace("_24px", "")));
            }
        }
    }

}
