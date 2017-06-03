package com.heiman.greendao;

import android.content.Context;
import android.text.TextUtils;

import com.heiman.greendao.entity.Setting;
import com.heiman.greendao.gen.DaoMaster;
import com.heiman.greendao.gen.DaoSession;
import com.heiman.greendao.gen.SettingDao;

/**
 * @Author : 张泽晋
 * @Time : 2017/5/26 14:36
 * @Description :
 * @Modify record :
 */

public class SettingTool {

    private static SettingTool instance;
    private static final String SMARTHOME_SETTING_DB_NAME = "smarthome-db";
    private SettingDao settingDao;
    private boolean inited = false;

    public final static String SMARTHOME_SETTING_SHOW_DETAIL_MESSAGE = "setting_show_detail_message"; //通知显示详情设置
    public final static String SMARTHOME_SETTING_VOICE = "setting_voice";   // 消息提示音设置
    public final static String SMARTHOME_SETTING_VIBRATION = "setting_vibration";  //消息提示震动设置

    public final static String SMARTHOME_SETTING_VALUE_OFF = "off"; //关
    public final static String SMARTHOME_SETTING_VALUE_ON = "on";  //开

    private SettingTool() {

    }

    public static SettingTool getInstance(Context context) {
        if (instance == null) {
            instance = new SettingTool();
        }

        if (!instance.inited) {
            instance.init(context);
        }
        return instance;
    }

    private void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, SMARTHOME_SETTING_DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        settingDao = daoSession.getSettingDao();
        inited = true;
    }

    /**
     * 添加设置信息
     * @param key 设置关键字
     * @param value 设置的值
     */
    public void addSetting(String key, String value) {
        Setting setting = settingDao.queryBuilder().where(SettingDao.Properties.Key.eq(key)).build().unique();

        if (setting != null) {
            setting.setValue(value);
            settingDao.update(setting);
        } else {
            setting = new Setting(null, key, value);
            settingDao.insert(setting);
        }
    }

    /**
     * 获取设置信息
     * @param key 设置关键字
     * @param defaultValue 默认设置值
     * @return
     */
    public String getSettingValue(String key, String defaultValue){
        String settingValue = defaultValue;
        Setting setting = settingDao.queryBuilder().where(SettingDao.Properties.Key.eq(key)).build().unique();

        if (setting != null) {
            settingValue = setting.getValue();
            if (TextUtils.isEmpty(settingValue)) {
                settingValue = defaultValue;
            }
        }
        return settingValue;
    }
}
