package com.heiman.baselibrary;

import android.app.Application;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.jiongbull.jlog.Logger;
import com.jiongbull.jlog.constant.LogLevel;
import com.jiongbull.jlog.constant.LogSegment;
import com.jiongbull.jlog.util.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/5 9:24
 * @Description :基础工程类
 * @Modify record :
 */

public class BaseApplication extends Application {
    private static Logger sLogger;
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBackActivity();
        instance = this;
        initLogSDK();
        getLogger().d("--------------------LogSDKok--------------------");
    }

    /**
     * 打印信息SDK
     */
    private void initLogSDK() {
        List<String> logLevels = new ArrayList<>();
        logLevels.add(LogLevel.ERROR);
        logLevels.add(LogLevel.WTF);

        sLogger = Logger.Builder.newBuilder(getApplicationContext(), "heimanLog")
                /* 下面的属性都是默认值，你可以根据需求决定是否修改它们. */
                .setDebug(true)
                .setWriteToFile(true)
                .setLogDir(getString(R.string.app_name))
                .setLogPrefix(getString(R.string.app_name) + File.separator + "id")
                .setLogSegment(LogSegment.TWELVE_HOURS)
                .setLogLevelsForFile(logLevels)
                .setZoneOffset(TimeUtils.ZoneOffset.P0800)
                .setTimeFormat("yyyy-MM-dd HH:mm:ss")
                .setPackagedLevel(0)
                .setStorage(null)
                .build();
    }

    public static Logger getLogger() {
        return sLogger;
    }

    private void initBackActivity() {
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
    }
}

