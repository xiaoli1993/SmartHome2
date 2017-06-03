/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.utils;

import android.util.Log;


/**
 * @Author : 张泽晋
 * @Time : 2017/4/24 8:16
 * @Description :对Log进行了封装以便获得更多的信息
 * @Modify record :2017/5/8 张泽晋 增加isLogAble属性增加setIsLogAble()方法增加w(),i(),v(),wtf()方法
 */
public class LogUtil {
    private static String className;
    private static String methodName;
    private static int lineNumber;
    private static boolean isLogAble;

    private static String createLog(String msg) {
        return methodName + ":" + lineNumber + " [" + msg + "]";
    }

    public static void setIsLogAble(boolean isLogAble) {
        LogUtil.isLogAble = isLogAble;
    }

    private static boolean getIsLogAble() {
        return isLogAble;
    }

    private static void getElements(StackTraceElement[] elements) {
        className = elements[1].getFileName();
        methodName = elements[1].getMethodName();
        lineNumber = elements[1].getLineNumber();
    }

    public static void e(String msg) {

        if (!getIsLogAble()) {
            return;
        }

        getElements(new Throwable().getStackTrace());
        Log.e(className, createLog(msg));
    }

    public static void w(String msg) {

        if (!getIsLogAble()) {
            return;
        }

        getElements(new Throwable().getStackTrace());
        Log.w(className, createLog(msg));
    }

    public static void i(String msg) {

        if (!getIsLogAble()) {
            return;
        }

        getElements(new Throwable().getStackTrace());
        Log.i(className, createLog(msg));
    }

    public static void v(String msg) {

        if (!getIsLogAble()) {
            return;
        }

        getElements(new Throwable().getStackTrace());
        Log.v(className, createLog(msg));
    }

    public static void wtf(String msg) {

        if (!getIsLogAble()) {
            return;
        }

        getElements(new Throwable().getStackTrace());
        Log.wtf(className, createLog(msg));
    }
}
