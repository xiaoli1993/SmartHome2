package com.heiman.smarthome.smarthomesdk.utils;/**
 * Copyright ©深圳市海曼科技有限公司.
 */

import android.graphics.Bitmap;


import com.heiman.smarthome.smarthomesdk.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/16 10:03
 * @Description :
 */
public class Utils {
    /**
     * 检测邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmial(String email) {
        String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * bitmap转byte
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
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

    /**
     * 数据截取
     *
     * @param bytes1 1数据
     * @param bytes2 2数据
     * @return
     */
    public static byte[] combineTowBytes(byte[] bytes1, byte[] bytes2) {
        try {
            byte[] bytes3 = new byte[bytes1.length + bytes2.length];
            System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
            System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
            return bytes3;
        } catch (Exception e) {
            byte[] k = {1};
            return k;
        }
    }

    /**
     * id	是	设备ID
     * mac	是	设备MAC地址
     * is_active	是	是否激活，布尔值，true或false
     * active_date	是	激活时间，例：2015-10-09T08 : 15 : 40.843Z
     * is_online	是	是否在线，布尔值，true或false
     * last_login	是	最近登录时间，例：2015-10-09T08 : 15 : 40.843Z
     * active_code	是	激活码
     * authorize_code	是	认证码
     * mcu_mod	是	MCU型号
     * mcu_version	是	MCU版本号
     * firmware_mod	是	固件型号
     * firmware_version	是	固件版本号
     * product_id	是	所属的产品ID
     * access_key	是	设备本地密码
     * role	是	用户和设备的订阅关系，admin还user
     *
     * @return
     */

    public static XDevice JsontoDevice(JSONObject jsonObj) {
        int version = 0;
        try {
            String product_id = jsonObj.getString("product_id");
            int mcu_version = jsonObj.getInt("mcu_version");
            String mac = jsonObj.getString("mac");
            int firmware_version = jsonObj.getInt("firmware_version");
            int access_key = jsonObj.getInt("access_key");
            long deviceid = jsonObj.getLong("id");
            JSONObject obj = new JSONObject();
            obj.put("protocol", 1);
            JSONObject deviceJson = new JSONObject();
            deviceJson.put("macAddress", mac);
            deviceJson.put("deviceID", deviceid);
            deviceJson.put("version", version);
            deviceJson.put("mcuHardVersion", mcu_version);
            deviceJson.put("mucSoftVersion", firmware_version);
            deviceJson.put("productID", product_id);
            deviceJson.put("accesskey", access_key);
            obj.put("device", deviceJson);
            XDevice xdevice = XlinkAgent.JsonToDevice(obj);
            return xdevice;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 获取PID
     */

    public static String getPID(int type) {
        switch (type) {
            case Constants.DEVICE_TYPE.DEVICE_WIFI_RC:
                return Constants.REMOTE_PRODUCTID;
            case Constants.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                return Constants.ZIGBEE_PRODUCTID;
            case Constants.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                return Constants.PLUGIN_PRODUCTID;
            case Constants.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                return Constants.METRTING_PLUGIN_PRODUCTID;
            case Constants.DEVICE_TYPE.DEVICE_WIFI_AIR:
                return Constants.AIR_PRODUCTID;
            default:
                return Constants.ZIGBEE_PRODUCTID;
        }
    }

}
