package com.heiman.utils;

import android.os.Environment;

import java.io.File;

/**
 * @Author : 张泽晋
 * @Time : 2017/5/25 10:44
 * @Description :文件存储位置
 * @Modify record :
 */

public class SmartHomeSave {
    private final static String SMARTHOME_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "SmartHome";
    private final static String SMARTHOME_PHOTO_PATH = SMARTHOME_SAVE_PATH + File.separator + "photo";
    private final static String SMARTHOME_PHOTO_CACHE_PATH = SMARTHOME_SAVE_PATH + File.separator + "photoCache";

    public static String getSmarthomeSavePath() {
        File file = new File(SMARTHOME_SAVE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return SMARTHOME_SAVE_PATH;
    }

    public static String getSmarthomePhotoPath() {
        File file = new File(SMARTHOME_PHOTO_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return SMARTHOME_PHOTO_PATH;
    }

    public static String getSmarthomePhotoCachePath() {
        File file = new File(SMARTHOME_PHOTO_CACHE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return SMARTHOME_PHOTO_CACHE_PATH;
    }

}
