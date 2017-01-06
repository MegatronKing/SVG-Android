package com.github.megatronking.svg.applet;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2016/1/22.
 */
public class R {
    private static final String BUNDLE_NAME = "values.string";

    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }

    public static String getString(String key) {
        String str;
        if (key == null)
            return "";
        key = key.trim();
        try {
            str = RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            str = key.replace("_", " ");
        }
        try {
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            str = key.replace("_", " ");
        }
        return str;
    }

    public final static class string {
        public static final String svg_filter = getString("svg_filter");
        public static final String vector_filter = getString("vector_filter");
        public static final String support_filter = getString("support_filter");

        public static final String exit = getString("exit");
        public static final String exit_short = getString("exit_short");
        public static final String exit_long = getString("exit_long");

        public static final String open = getString("open");
        public static final String open_short = getString("open_short");
        public static final String open_long = getString("open_long");

        public static final String save = getString("save");
        public static final String save_short = getString("save_short");
        public static final String save_long = getString("save_long");

        public static final String menu_open = getString("menu_open");
        public static final String menu_exit = getString("menu_exit");
        public static final String menu_save = getString("menu_save");

        public static final String help = getString("menuitem_help");
        public static final String about = getString("menuitem_about");
        public static final String file = getString("menuitem_file");

        public static final String about_text = getString("about_text");

        public static final String title = getString("title");

        public static final String v_patch = getString("v_patch");
        public static final String v_padding = getString("v_padding");
        public static final String h_patch = getString("h_patch");
        public static final String h_padding = getString("h_padding");
        public static final String px = getString("px");
        public static final String top_tip = getString("top_tip");
        public static final String zoom = getString("zoom");
        public static final String patch_zoom = getString("patch_zoom");
        public static final String show_lock = getString("show_lock");
        public static final String show_patch = getString("show_patch");

        public static final String show_bad_patches = getString("show_bad_patches");
        public static final String show_content = getString("show_content");




    }
}
