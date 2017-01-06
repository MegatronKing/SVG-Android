package com.github.megatronking.svg.generator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class FileUtils {

    public static boolean saveFile(String dir, String fileName, String data) {
        return saveFile(dir, fileName, data, false, false);
    }

    private static boolean saveFile(String dir, String fileName, String data, boolean mkdir, boolean append) {
        File dirFile = new File(dir);
        if(!dirFile.exists()) {
            if(!mkdir) {
                return false;
            }
            createFolders(dirFile);
        }

        File dest = new File(dir, fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(dest, append);
            fos.write(data.getBytes());
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean createFolders(File folder) {
        return folder.mkdirs();
    }

    public static String noExtensionName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, dotIndex);
    }

    public static String noExtensionLastName(File file) {
        return noExtensionName(file.getName());
    }

    public static String noExtensionFullName(File file) {
        return noExtensionName(file.getPath());
    }

    public static void unZipGzipFile(File source, File destination) throws IOException {
        if (destination.getParentFile().exists() || destination.getParentFile().mkdirs()) {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(destination);
            GZIPInputStream gis = new GZIPInputStream(fis);
            int count;
            byte[] data = new byte[1024];
            while ((count = gis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
            }
            gis.close();
            fis.close();
            fos.flush();
            fos.close();
        }
    }

}
