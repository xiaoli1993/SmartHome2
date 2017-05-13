package com.heiman.baselibrary.http;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.heiman.utils.styleabletoastlibrary.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XlinkUtils {
    /**
     * md5加密
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * map转string
     *
     * @param map
     * @return
     */
    public static String getSting(HashMap<String, Object> map) {
        JSONObject jo = new JSONObject();
        Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            try {
                jo.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jo.toString();

    }

    /**
     * map转json
     *
     * @param map
     * @return
     */
    public static JSONObject getJsonObject(Map<String, Object> map) {
        JSONObject jo = new JSONObject();
        Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            try {
                jo.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jo;

    }

    /**
     * 截取 byte
     *
     * @param bytes  源数据
     * @param offset 偏移量
     * @param len    长度
     * @return
     */
    public static byte[] subBytes(byte[] bytes, int offset, int len) {
        byte[] b = new byte[len];

        System.arraycopy(bytes, offset, b, 0, len);
        return b;
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws
     */
    public static byte[] base64Decrypt(String key) {
        byte[] bs = Base64.decode(key, Base64.DEFAULT);
        if (bs == null || bs.length == 0) {
            bs = key.getBytes();
        }
        return bs;
    }


    public static String getHexBinString(byte[] bs) {
        StringBuffer log = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            log.append(String.format("%02X", (byte) bs[i]) + " ");
        }
        return log.toString();
    }

    public static String getHexBinString(byte[] bs, String re) {
        StringBuffer log = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            if (i == bs.length - 1) {
                log.append(String.format("%02X", (byte) bs[i]));
            } else {
                log.append(String.format("%02X", (byte) bs[i]) + re);
            }
        }
        return log.toString();
    }

    /**
     * 把byte转化成 二进制.
     *
     * @param aByte
     * @return
     */
    public static String getBinString(byte aByte) {
        String out = "";
        int i = 0;
        for (i = 0; i < 8; i++) {
            int v = (aByte << i) & 0x80;
            v = (v >> 7) & 1;
            out += v;
        }
        return out;
    }

    static private final int bitValue0 = 0x01; // 0000 0001
    static private final int bitValue1 = 0x02; // 0000 0010
    static private final int bitValue2 = 0x04; // 0000 0100
    static private final int bitValue3 = 0x08; // 0000 1000
    static private final int bitValue4 = 0x10; // 0001 0000
    static private final int bitValue5 = 0x20; // 0010 0000
    static private final int bitValue6 = 0x40; // 0100 0000
    static private final int bitValue7 = 0x80; // 1000 0000

    /**
     * 设置flags
     *
     * @param index 第几个bit，从零开始排
     * @param value byte值
     * @return
     */
    public static byte setByteBit(int index, byte value) {
        if (index > 7) {
            throw new IllegalAccessError("setByteBit error index>7!!! ");
        }
        byte ret = value;
        if (index == 0) {
            ret |= bitValue0;
        } else if (index == 1) {
            ret |= bitValue1;
        } else if (index == 2) {
            ret |= bitValue2;
        } else if (index == 3) {
            ret |= bitValue3;
        } else if (index == 4) {
            ret |= bitValue4;
        } else if (index == 5) {
            ret |= bitValue5;
        } else if (index == 6) {
            ret |= bitValue6;
        } else if (index == 7) {
            ret |= bitValue7;
        }
        return ret;
    }

    /**
     * 读取 flags 里的小bit
     *
     * @param anByte
     * @param index
     * @return
     */
    public static boolean readFlagsBit(byte anByte, int index) {
        if (index > 7) {
            throw new IllegalAccessError("readFlagsBit error index>7!!! ");
        }
        int temp = anByte << (7 - index);
        temp = temp >> 7;
        temp &= 0x01;
        if (temp == 1) {
            return true;
        }
        // if((anByte & (01<<index)) !=0){
        // return true;
        // }
        return false;
    }

    /**
     * 将16位的short转换成byte数组
     *
     * @param s short
     * @return byte[] 长度为2
     */
    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getAdapterView(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public static String base64EncryptUTF(byte[] key)
            throws UnsupportedEncodingException {
        return new String(Base64.encode(key, Base64.DEFAULT), "UTF-8");
    }

    public static String base64Encrypt(byte[] key) {
        return new String(Base64.encode(key, Base64.DEFAULT));
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null || cm.getActiveNetworkInfo() == null) {
            return false;
        }

        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
        } else {
            intent = new Intent("/");
            ComponentName cm = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(cm);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 把输入流转换成字符数组
     *
     * @param inputStream 输入流
     * @return 字符数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();

        return bout.toByteArray();
    }

    /**
     * 字符串 16进制转bytes
     *
     * @param hexString
     * @return
     */
    public static byte[] stringToByteArray(String hexString) {
        if (hexString.isEmpty() || hexString.length() % 2 != 0)
            return null;
        hexString.replaceAll(":", "");
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            // 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static void shortTips(Context context, String tip, int bgcolor, int tvcolor, int drawable, boolean isspin) {
        Log.e("shortTips", tip);
        StyleableToast st = new StyleableToast(context, tip, Toast.LENGTH_SHORT);
        if (bgcolor != 0) {
            st.setBackgroundColor(bgcolor);
        }
        if (tvcolor != 0) {
            st.setTextColor(tvcolor);
        }
        if (drawable != 0) {
            st.setIcon(drawable);
        }
        if (isspin) {
            st.spinIcon();
        }
        st.setMaxAlpha();
        st.show();
    }

    public static void longTips(Context context, String tip) {
        Log.e("longTips", tip);
        StyleableToast st = new StyleableToast(context, tip, Toast.LENGTH_LONG);
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


}
