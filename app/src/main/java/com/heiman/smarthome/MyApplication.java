package com.heiman.smarthome;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Context;
import android.support.multidex.MultiDex;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.utils.LogUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/17 15:03
 * @Description :
 */
public class MyApplication extends BaseApplication {

    private MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        getLogger().d("--------------------MyApplication--------------------");
        initBuglySDK();
        getLogger().d("--------------------buglaySDKok--------------------");
        LogUtil.setIsLogAble(true);
    }

    /**
     * BUG搜集以及热更新
     */
    private void initBuglySDK() {
        getLogger().d("--------------------buglaySDK1--------------------");
        Context context = getApplicationContext();
        // 获取当前包名136326
        getLogger().d("--------------------buglaySDK2--------------------");
        String packageName = context.getPackageName();
        // 获取当前进程名
        getLogger().d("--------------------buglaySDK3--------------------");
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        getLogger().d("--------------------buglaySDK4--------------------");
        final CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        getLogger().d("--------------------buglaySDK5--------------------");
        strategy.setAppChannel("Heiman");  //设置渠道
        strategy.setAppVersion("V1.0");      //App的版本
        strategy.setAppPackageName("com.heiman.smarthome");  //App的包名
        getLogger().d("--------------------buglaySDK6--------------------");
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "a476f93da4", false, strategy);
        getLogger().d("--------------------buglaySDK7--------------------");
        try {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bugly.init(getApplicationContext(), "a476f93da4", false, strategy);
                }
            });
            thread.start();
            getLogger().d("--------------------buglaySDK8--------------------");
        } catch (
                Exception e)

        {

        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }
}
