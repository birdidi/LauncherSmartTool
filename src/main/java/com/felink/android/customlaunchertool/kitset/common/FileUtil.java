package com.felink.android.customlaunchertool.kitset.common;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月17日 14:34.</br>
 * @update: </br>
 */

public class FileUtil {
    public static void copyAssetFile(AssetManager assetManager, String src, String dest) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(src);
            File outFile = new File(dest);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
    }

    /**
     * 指定目录下是否存在指定名称的文件
     *
     * @param dir      目录
     * @param fileName 文件名称
     * @return boolean
     */
    public static boolean isFileExits(String dir, String fileName) {
        fileName = fileName == null ? "" : fileName;
        dir = dir == null ? "" : dir;
        int index = dir.lastIndexOf("/");
        String filePath;
        if (index == dir.length() - 1)
            filePath = dir + fileName;
        else
            filePath = dir + "/" + fileName;
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 指定路么下是否存在文件
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean isFileExits(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists())
                return true;
        } catch (Exception e) {

        }
        return false;
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
