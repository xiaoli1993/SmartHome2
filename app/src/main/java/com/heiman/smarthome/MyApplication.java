package com.heiman.smarthome;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Context;
import android.support.multidex.MultiDex;

import com.heiman.baselibrary.BaseApplication;
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
        initBuglySDK();
        getLogger().d("--------------------buglaySDKok--------------------");
    }

    /**
     * BUG搜集以及热更新
     */
    private void initBuglySDK() {
        Context context = getApplicationContext();
        // 获取当前包名136326
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);

        strategy.setAppChannel("Heiman");  //设置渠道
        strategy.setAppVersion("V1.0");      //App的版本
        strategy.setAppPackageName("com.heiman.smarthome");  //App的包名

        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "a476f93da4", false, strategy);
        Bugly.init(getApplicationContext(), "a476f93da4", false, strategy);
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
