package com.heiman.baselibrary.utils;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.R;
import com.heiman.baselibrary.http.HttpConstant;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.DeviceSS;
import com.heiman.baselibrary.mode.EventIsOnline;
import com.heiman.baselibrary.mode.EventNotifyData;
import com.heiman.baselibrary.mode.HttpDevice;
import com.heiman.baselibrary.mode.Notifications;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.utils.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;
import io.xlink.wifi.sdk.bean.EventNotify;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/21 08:28
 * @Description :
 */
public class SmartHomeUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断List是否为空
     *
     * @param list 字符串
     * @return 是否为空
     */
    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 报警数据返回 转换成type
     *
     * @param loc_key 报警loc_key
     * @return
     */
    public static int getType(String loc_key) {
        int type = 0;
        String loc_keya[] = loc_key.split("_");
        try {
            type = Integer.parseInt(loc_keya[2]);
        } catch (Exception e) {
            type = Integer.parseInt(loc_keya[1]);
        }
        return type;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 获取deviceid  json数组
     *
     * @return
     */
    public static JSONArray getdeviceid() {
        JSONArray deviceid = new JSONArray();
        List<XlinkDevice> device = DeviceManage.getInstance().getDevices();
        for (int i = 0; i < device.size(); i++) {
            if (device.get(i).getDeviceId() > 1000) {
                deviceid.put(device.get(i).getDeviceId());
            }
        }
        return deviceid;
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

    public static XlinkDevice subscribeToDevice(String jsonObj) {
        XlinkDevice device = new XlinkDevice();
        int devicetype = 0;
        String deviceName = "";
        int version = 0;
        Gson gson = new Gson();
        HttpDevice httpDevice = gson.fromJson(jsonObj, HttpDevice.class);
        try {
            String product_id = httpDevice.getProduct_id();
            int mcu_version = httpDevice.getMcu_version();
            String mac = httpDevice.getMac();
            int firmware_version = httpDevice.getFirmware_version();
            int access_key = httpDevice.getAccess_key();
            long deviceid = httpDevice.getId();
            try {
                if (product_id.equals(Constant.ZIGBEE_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY;
                    version = 2;
                } else if (product_id.equals(Constant.PLUGIN_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN;
                    version = 1;
                } else if (product_id.equals(Constant.METRTING_PLUGIN_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN;
                    version = 2;
                } else if (product_id.equals(Constant.AIR_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_AIR;
                    version = 2;
                } else if (product_id.equals(Constant.REMOTE_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_RC;
                    version = 2;
                } else if (product_id.equals(Constant.GAS_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GAS;
                    version = 2;
                } else if (product_id.equals(Constant.ZIGBEE_H1GW_NEW_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW;
                    version = 3;
                } else if (product_id.equals(Constant.ZIGBEE_H2GW_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW;
                    version = 3;
                }
                deviceName = httpDevice.getName();
                if (deviceName.equals("") || deviceName == null) {
                    if (product_id.equals(Constant.ZIGBEE_PRODUCTID) || product_id.equals(Constant.ZIGBEE_H1GW_NEW_PRODUCTID) || product_id.equals(Constant.ZIGBEE_H2GW_PRODUCTID)) {
                        deviceName = "ZGW_" + mac;
                    } else if (product_id.equals(Constant.PLUGIN_PRODUCTID)) {
                        deviceName = "PLUG_" + mac;
                    } else if (product_id.equals(Constant.METRTING_PLUGIN_PRODUCTID)) {
                        deviceName = "EWPLUG_" + mac;
                    } else if (product_id.equals(Constant.AIR_PRODUCTID)) {
                        deviceName = "AIR_" + mac;
                    } else if (product_id.equals(Constant.REMOTE_PRODUCTID)) {
                        deviceName = "RC_" + mac;
                    } else if (product_id.equals(Constant.GAS_PRODUCTID)) {
                        deviceName = "Gas_" + mac;
                    }
                }
            } catch (Exception e) {
                if (product_id.equals(Constant.ZIGBEE_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY;
                    deviceName = "ZGW_" + mac;
                    version = 2;
                } else if (product_id.equals(Constant.PLUGIN_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN;
                    deviceName = "PLUG_" + mac;
                    version = 1;
                } else if (product_id.equals(Constant.METRTING_PLUGIN_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN;
                    deviceName = "EWPLUG_" + mac;
                    version = 2;
                } else if (product_id.equals(Constant.AIR_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_AIR;
                    deviceName = "AIR_" + mac;
                    version = 2;
                } else if (product_id.equals(Constant.REMOTE_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_RC;
                    deviceName = "RC_" + mac;
                    version = 2;
                } else if (product_id.equals(Constant.GAS_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GAS;
                    deviceName = "Gas_" + mac;
                    version = 2;
                } else if (product_id.equals(Constant.ZIGBEE_H1GW_NEW_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW;
                    deviceName = "ZGW_" + mac;
                    version = 3;
                } else if (product_id.equals(Constant.ZIGBEE_H2GW_PRODUCTID)) {
                    devicetype = Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW;
                    deviceName = "ZGW_" + mac;
                    version = 3;
                }
            }
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
//            XDevice xdevice = XlinkAgent.JsonToDevice(obj);
            device.setxDevice(obj.toString());
            device.setDeviceType(devicetype);
            device.setDeviceName(deviceName);
            device.setDeviceMac(mac);
            device.setAccessKey(access_key + "");
            device.setDate(new Date());
            device.setProductId(httpDevice.getProduct_id());
            device.setDeviceId(httpDevice.getId());
            if (httpDevice.isIs_online()) {
                device.setDeviceState(1);
            }
            device.setActive_code(httpDevice.getActive_code());
            device.setActive_date(httpDevice.getActive_date());
            device.setLast_login(httpDevice.getLast_login());
            device.setAuthorize_code(httpDevice.getAuthorize_code());
            DeviceManage.getInstance().addDevice(device);
            return device;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 获取PID
     */

    public static String getPID(int type) {
        switch (type) {
            case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                return Constant.REMOTE_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                return Constant.ZIGBEE_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                return Constant.PLUGIN_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                return Constant.METRTING_PLUGIN_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_AIR:
                return Constant.AIR_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
                return Constant.ZIGBEE_H1GW_NEW_PRODUCTID;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW:
                return Constant.ZIGBEE_H2GW_PRODUCTID;
            default:
                return Constant.ZIGBEE_PRODUCTID;
        }
    }

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

    public static String getSSid(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (wm != null) {
            WifiInfo wi = wm.getConnectionInfo();
            if (wi != null) {
                String s = wi.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                } else {
                    return s;
                }
            }
        }
        return "";
    }

    public static String showRcCode(int RC) {
        switch (RC) {
            case Constant.JOSN_RC.UNKNOWN_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.RC_0);
            case Constant.JOSN_RC.PARAMETER_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.RC_1);
            case Constant.JOSN_RC.DECRYPTION_FAILURE:
                return BaseApplication.getMyApplication().getString(R.string.RC_2);
            case Constant.JOSN_RC.THE_OID_DOES_NOT_EXIST:
                return BaseApplication.getMyApplication().getString(R.string.RC_3);
            case Constant.JOSN_RC.CID_DOES_NOT_EXIST:
                return BaseApplication.getMyApplication().getString(R.string.RC_4);
            case Constant.JOSN_RC.PL_CANNOT_BE_EMPTY:
                return BaseApplication.getMyApplication().getString(R.string.RC_5);
            case Constant.JOSN_RC.TEID_CANNOT_BE_EMPTY:
                return BaseApplication.getMyApplication().getString(R.string.RC_6);
            default:
                return BaseApplication.getMyApplication().getString(R.string.RC_0);
        }
    }

    /**
     * 返回HTTP错误码
     *
     * @param code 错误码
     * @return
     */
    public static String showHttpCode(int code) {
        switch (code) {
            case HttpConstant.PARAM_NETIO_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.PARAM_NETIO_ERROR);
            case HttpConstant.PARAM_VALID_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.PARAM_VALID_ERROR);
            case HttpConstant.PARAM_MUST_NOT_NULL:
                return BaseApplication.getMyApplication().getString(R.string.PARAM_MUST_NOT_NULL);
            case HttpConstant.PHONE_VERIFYCODE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.PHONE_VERIFYCODE_NOT_EXISTS);
            case HttpConstant.PHONE_VERIFYCODE_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.PHONE_VERIFYCODE_ERROR);
            case HttpConstant.REGISTER_PHONE_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.REGISTER_PHONE_EXISTS);
            case HttpConstant.REGISTER_EMAIL_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.REGISTER_EMAIL_EXISTS);
            case HttpConstant.LOGIN_CONTINUOUS:
                return BaseApplication.getMyApplication().getString(R.string.LOGIN_CONTINUOUS);
            case HttpConstant.REGISTER_EMAIL_USUE_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.REGISTER_EMAIL_USUE_EXISTS);
            case HttpConstant.REGISTER_PHONE_USUE_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.REGISTER_PHONE_USUE_EXISTS);
            case HttpConstant.ACCOUNT_PASSWORD_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.ACCOUNT_PASSWORD_ERROR);
            case HttpConstant.ACCOUNT_VAILD_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.ACCOUNT_VAILD_ERROR);
            case HttpConstant.MEMBER_STATUS_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_STATUS_ERROR);
            case HttpConstant.REFRESH_TOKEN_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.REFRESH_TOKEN_ERROR);
            case HttpConstant.MEMBER_ROLE_TYPE_UNKOWN:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_ROLE_TYPE_UNKOWN);
            case HttpConstant.MEMBER_INVITE_NOT_ADMIN:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_INVITE_NOT_ADMIN);
            case HttpConstant.CAN_NOT_MODIFY_OTHER_MEMBER_INFO:
                return BaseApplication.getMyApplication().getString(R.string.CAN_NOT_MODIFY_OTHER_MEMBER_INFO);
            case HttpConstant.CAN_NOT_DELETE_YOURSELF:
                return BaseApplication.getMyApplication().getString(R.string.CAN_NOT_DELETE_YOURSELF);
            case HttpConstant.PRODUCT_LINK_TYPE_UNKOWN:
                return BaseApplication.getMyApplication().getString(R.string.PRODUCT_LINK_TYPE_UNKOWN);
            case HttpConstant.CAN_NOT_DELETE_RELEASE_PRODUCT:
                return BaseApplication.getMyApplication().getString(R.string.CAN_NOT_DELETE_RELEASE_PRODUCT);
            case HttpConstant.FIRMWARE_VERSION_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.FIRMWARE_VERSION_EXISTS);
            case HttpConstant.DATAPOINT_TYPE_UNKOWN:
                return BaseApplication.getMyApplication().getString(R.string.DATAPOINT_TYPE_UNKOWN);
            case HttpConstant.DATAPOINT_INDEX_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DATAPOINT_INDEX_EXISTS);
            case HttpConstant.CANT_NOT_DELETE_RELEASED_DATAPOINT:
                return BaseApplication.getMyApplication().getString(R.string.CANT_NOT_DELETE_RELEASED_DATAPOINT);
            case HttpConstant.DEVICE_MAC_ADDRESS_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DEVICE_MAC_ADDRESS_EXISTS);
            case HttpConstant.CAN_NOT_DELETE_ACTIVATED_DEVICE:
                return BaseApplication.getMyApplication().getString(R.string.CAN_NOT_DELETE_ACTIVATED_DEVICE);
            case HttpConstant.PROPERTY_KEY_PROTECT:
                return BaseApplication.getMyApplication().getString(R.string.PROPERTY_KEY_PROTECT);
            case HttpConstant.PROPERTY_LIMIT:
                return BaseApplication.getMyApplication().getString(R.string.PROPERTY_LIMIT);
            case HttpConstant.PROPERTY_ADD_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.PROPERTY_ADD_EXISTS);
            case HttpConstant.PROPERTY_UPDATE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.PROPERTY_UPDATE_NOT_EXISTS);
            case HttpConstant.PROPERTY_KEY_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.PROPERTY_KEY_ERROR);
            case HttpConstant.EMAIL_VERIFYCODE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.EMAIL_VERIFYCODE_NOT_EXISTS);
            case HttpConstant.EMAIL_VERIFYCODE_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.EMAIL_VERIFYCODE_ERROR);
            case HttpConstant.USER_STATUS_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.USER_STATUS_ERROR);
            case HttpConstant.USER_PHONE_NOT_VAILD:
                return BaseApplication.getMyApplication().getString(R.string.USER_PHONE_NOT_VAILD);
            case HttpConstant.USER_EMAIL_NOT_VAILD:
                return BaseApplication.getMyApplication().getString(R.string.USER_EMAIL_NOT_VAILD);
            case HttpConstant.USER_HAS_SUBSCRIBE_DEVICE:
                return BaseApplication.getMyApplication().getString(R.string.USER_HAS_SUBSCRIBE_DEVICE);
            case HttpConstant.USER_HAVE_NO_SUBSCRIBE_DEVICE:
                return BaseApplication.getMyApplication().getString(R.string.USER_HAVE_NO_SUBSCRIBE_DEVICE);
            case HttpConstant.UPGRADE_TASK_NAME_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.UPGRADE_TASK_NAME_EXISTS);
            case HttpConstant.UPGRADE_TASK_STATUS_UNKOWN:
                return BaseApplication.getMyApplication().getString(R.string.UPGRADE_TASK_STATUS_UNKOWN);
            case HttpConstant.UPGRADE_TASK_HAVE_STARTING_VERSION:
                return BaseApplication.getMyApplication().getString(R.string.UPGRADE_TASK_HAVE_STARTING_VERSION);
            case HttpConstant.DEVICE_ACTIVE_FAIL:
                return BaseApplication.getMyApplication().getString(R.string.DEVICE_ACTIVE_FAIL);
            case HttpConstant.DEVICE_AUTH_FAIL:
                return BaseApplication.getMyApplication().getString(R.string.DEVICE_AUTH_FAIL);
            case HttpConstant.SUBSCRIBE_AUTHORIZE_CODE_ERROR:
                return BaseApplication.getMyApplication().getString(R.string.SUBSCRIBE_AUTHORIZE_CODE_ERROR);
            case HttpConstant.EMPOWER_NAME_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.EMPOWER_NAME_EXISTS);
            case HttpConstant.ALARM_RULE_NAME_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.ALARM_RULE_NAME_EXISTS);
            case HttpConstant.DATA_TABLE_NAME_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DATA_TABLE_NAME_EXISTS);
            case HttpConstant.PRODUCT_FIRMWARE_FILE_SIZE_LIMIT:
                return BaseApplication.getMyApplication().getString(R.string.PRODUCT_FIRMWARE_FILE_SIZE_LIMIT);
            case HttpConstant.APP_APN_LICENSE_FILE_SIZE_LIMIT:
                return BaseApplication.getMyApplication().getString(R.string.APP_APN_LICENSE_FILE_SIZE_LIMIT);
            case HttpConstant.APP_APN_IS_NOT_ENABLE:
                return BaseApplication.getMyApplication().getString(R.string.APP_APN_IS_NOT_ENABLE);
            case HttpConstant.PRODUCT_CAN_NOT_REGISTER_DEVICE:
                return BaseApplication.getMyApplication().getString(R.string.PRODUCT_CAN_NOT_REGISTER_DEVICE);
            case HttpConstant.INVALID_ACCESS:
                return BaseApplication.getMyApplication().getString(R.string.INVALID_ACCESS);
            case HttpConstant.NEED_ACCESS_TOKEN:
                return BaseApplication.getMyApplication().getString(R.string.NEED_ACCESS_TOKEN);
            case HttpConstant.ACCESS_TOKEN_INVALID:
                return BaseApplication.getMyApplication().getString(R.string.ACCESS_TOKEN_INVALID);
            case HttpConstant.NEED_CORP_API:
                return BaseApplication.getMyApplication().getString(R.string.NEED_CORP_API);
            case HttpConstant.NEED_CORP_ADMIN_MEMBER:
                return BaseApplication.getMyApplication().getString(R.string.NEED_CORP_ADMIN_MEMBER);
            case HttpConstant.NEED_DATA_PERMISSION:
                return BaseApplication.getMyApplication().getString(R.string.NEED_DATA_PERMISSION);
            case HttpConstant.INVAILD_ACCESS_PRIVATE_DATA:
                return BaseApplication.getMyApplication().getString(R.string.INVAILD_ACCESS_PRIVATE_DATA);
            case HttpConstant.SHARE_CANCELED:
                return BaseApplication.getMyApplication().getString(R.string.SHARE_CANCELED);
            case HttpConstant.SHARE_ACCEPTED:
                return BaseApplication.getMyApplication().getString(R.string.SHARE_ACCEPTED);
            case HttpConstant.URL_NOT_FOUND:
                return BaseApplication.getMyApplication().getString(R.string.URL_NOT_FOUND);
            case HttpConstant.MEMBER_ACCOUNT_NO_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_ACCOUNT_NO_EXISTS);
            case HttpConstant.MEMBER_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_NOT_EXISTS);
            case HttpConstant.MEMBER_INVITE_EMAIL_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.MEMBER_INVITE_EMAIL_NOT_EXISTS);
            case HttpConstant.PRODUCT_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.PRODUCT_NOT_EXISTS);
            case HttpConstant.FIRMWARE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.FIRMWARE_NOT_EXISTS);
            case HttpConstant.DATAPOINT_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DATAPOINT_NOT_EXISTS);
            case HttpConstant.DEVICE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DEVICE_NOT_EXISTS);
            case HttpConstant.DEVICE_PROPERTY_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DEVICE_PROPERTY_NOT_EXISTS);
            case HttpConstant.CORP_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.CORP_NOT_EXISTS);
            case HttpConstant.USER_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.USER_NOT_EXISTS);
            case HttpConstant.USER_PROPERTY_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.USER_PROPERTY_NOT_EXISTS);
            case HttpConstant.UPGRADE_TASK_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.UPGRADE_TASK_NOT_EXISTS);
            case HttpConstant.EMPOWER_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.EMPOWER_NOT_EXISTS);
            case HttpConstant.ALARM_RULE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.ALARM_RULE_NOT_EXISTS);
            case HttpConstant.DATA_TABLE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DATA_TABLE_NOT_EXISTS);
            case HttpConstant.DATA_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.DATA_NOT_EXISTS);
            case HttpConstant.SHARE_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.SHARE_NOT_EXISTS);
            case HttpConstant.CORP_EMAIL_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.CORP_EMAIL_NOT_EXISTS);
            case HttpConstant.APP_NOT_EXISTS:
                return BaseApplication.getMyApplication().getString(R.string.APP_NOT_EXISTS);
            case HttpConstant.NOT_A_DEVICE_ADMIN:
                return BaseApplication.getMyApplication().getString(R.string.NOT_A_DEVICE_ADMIN);
            case HttpConstant.SERVICE_EXCEPTION:
                return BaseApplication.getMyApplication().getString(R.string.SERVICE_EXCEPTION);
            default:
                return BaseApplication.getMyApplication().getString(R.string.OTHER_ER);
        }
    }

    /**
     * 这个是广播注册
     *
     * @return
     */
    public static IntentFilter regFilter() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.BROADCAST_RECVPIPE);
        myIntentFilter.addAction(Constant.BROADCAST_DEVICE_CHANGED);
        myIntentFilter.addAction(Constant.BROADCAST_DEVICE_SYNC);
        myIntentFilter.addAction(Constant.BROADCAST_RECVPIPE_SYNC);
        myIntentFilter.addAction(Constant.BROADCAST_CONNENCT_SUCCESS);
        myIntentFilter.addAction(Constant.BROADCAST_CONNENCT_FAIL);
        myIntentFilter.addAction(Constant.BROADCAST_SEND_OVERTIME);
        myIntentFilter.addAction(Constant.BROADCAST_SEND_SUCCESS);
        myIntentFilter.addAction(Constant.BROADCAST_SEND_SUCCESS);
        return myIntentFilter;
    }

    /**
     * 根据Type转换成图片
     *
     * @param isSub      是否是子设备
     * @param deviceType 设备类型
     * @return
     */
    public static int typeToIcon(boolean isSub, int deviceType) {
        if (isSub) {
            switch (deviceType) {
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                    return R.drawable.device_light;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                    return R.drawable.device_door;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                    return R.drawable.device_water;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                    return R.drawable.device_pir;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                    return R.drawable.device_smoke;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                    return R.drawable.device_thp;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                    return R.drawable.device_gas;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                    return R.drawable.device_co;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                    return R.drawable.device_sos;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                    return R.drawable.device_sw;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                    return R.drawable.device_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                    return R.drawable.device_e_plug;

                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                    return R.drawable.device_e_plug;

                default:
                    return R.drawable.device_gw;
            }
        } else {
            switch (deviceType) {
                case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                    return R.drawable.device_rc;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                    return R.drawable.device_gw;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                    return R.drawable.device_plug;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                    return R.drawable.device_e_plug;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_AIR:
                    return R.drawable.device_e_plug;
                default:
                    return R.drawable.device_gw;
            }
        }
    }

    /**
     * 子设备对应状态
     *
     * @param subDevice 子设备
     * @param ssBean    状态
     */
    public static void typeSetSS(SubDevice subDevice, DeviceSS.PLBean.OIDBean.DEVBean.SSBean ssBean) {
        if (ssBean.getOL() == 0) {
            subDevice.setOnlineStatus(false);
        } else {
            subDevice.setOnlineStatus(true);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        switch (subDevice.getDeviceType()) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                subDevice.setRgbOnoff(ssBean.getOF());
                subDevice.setRgblevel(ssBean.getLE());
                subDevice.setR(ssBean.getCR());
                subDevice.setG(ssBean.getCG());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:

                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setTemp(ssBean.getTP() + "");
                subDevice.setHumidity(ssBean.getHY() + "");
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                subDevice.setBatteryPercent(ssBean.getBP());
                if (ssBean.getBA() == 0) {
                    subDevice.setBatteryAlm(false);
                } else {
                    subDevice.setBatteryAlm(true);
                }
                subDevice.setDeviceOnoff(ssBean.getOF());
                try {
                    Date dateTmp = dateFormat.parse(ssBean.getTM() + "");
                    subDevice.setLastDate(dateTmp);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                subDevice.setRelayOnoff(ssBean.getRO());
                subDevice.setUsbOnoff(ssBean.getUO());

            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                subDevice.setRelayOnoff(ssBean.getRO());
                subDevice.setPower(ssBean.getPW());
                subDevice.setKwm(ssBean.getPH());
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
        }
        SubDeviceManage.getInstance().addDevice(subDevice);
    }

    /**
     * 根据Type转换成昵称
     *
     * @param isSub      是否是子设备
     * @param deviceType 设备类型
     * @return
     */
    public static String typeToNikeName(boolean isSub, int deviceType) {
        if (isSub) {
            switch (deviceType) {
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_rgb);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_door);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_water);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_pir);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_smorke);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_temp_hum);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_gas);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_co);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_sos);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_sw);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_plug);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_e_plug);

                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_one_onoff);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_two_onoff);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_three_onoff);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_rc);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_relay);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_sound_light_alarm);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_Illuminance);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_air);
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_temp_k);

                default:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_plug);
            }
        } else {
            switch (deviceType) {
                case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_rc);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_min_gw);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_min_gw);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_max_gw);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_plug);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_e_plug);
                case Constant.DEVICE_TYPE.DEVICE_WIFI_AIR:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_air);
                default:
                    return BaseApplication.getMyApplication().getString(R.string.device_name_max_gw);
            }
        }
    }

    /**
     * 设备类型转成y1
     *
     * @param deviceType
     * @return
     */
    public static int typeToY1(int deviceType) {

        switch (deviceType) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                return 4;
            default:
                return 0;
        }
    }

    /**
     * 设备类型转成y2
     *
     * @param deviceType
     * @return
     */
    public static int typeToY2(int deviceType) {

        switch (deviceType) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                return 5;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                return 6;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                return 5;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                return 7;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                return 2;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                return 3;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                return 4;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                return 8;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                return 9;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                return 1;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public static void startActivityForName(Context context, String activityName, Bundle paramBundle) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(context, clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onClickDevice(Context context, SubDevice subDevice) {
        Bundle paramBundle = new Bundle();
        paramBundle.putBoolean(Constant.IS_DEVICE, true);
        paramBundle.putString(Constant.DEVICE_MAC, subDevice.getDeviceMac());
        paramBundle.putBoolean(Constant.IS_SUB, true);
        paramBundle.putString(Constant.ZIGBEE_MAC, subDevice.getZigbeeMac());
        switch (subDevice.getDeviceType()) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:

                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                startActivityForName(context, "com.heiman.temphum.TempHumActivity", paramBundle);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                startActivityForName(context, "com.heiman.metrtingplugin.MetrtingPluginActivity", paramBundle);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                break;
        }
    }

    public static final int Period_Sign = 0x80;
    public static final int Monday = 0x01;
    public static final int Tuesday = 0x02;
    public static final int Wednesday = 0x04;
    public static final int Thursday = 0x08;
    public static final int Friday = 0x10;
    public static final int Saturday = 0x20;
    public static final int Sunday = 0x40;
    public static final int Everyday = 0x7f;

    public static String getWkString(Context mContext, int wk) {
        String WkString = "";
        int i = 0x01;
        while (i < 0x80) {
            int s = wk & i;
            switch (s) {
                case Monday:
                    WkString = mContext.getString(R.string.Monday);
                    break;
                case Tuesday:
                    WkString = WkString + " " + mContext.getString(R.string.Tuesday);
                    break;
                case Wednesday:
                    WkString = WkString + " " + mContext.getString(R.string.Wednesday);
                    break;
                case Thursday:
                    WkString = WkString + " " + mContext.getString(R.string.Thursday);
                    break;
                case Friday:
                    WkString = WkString + " " + mContext.getString(R.string.Friday);
                    break;
                case Saturday:
                    WkString = WkString + " " + mContext.getString(R.string.Saturday);
                    break;
                case Sunday:
                    WkString = WkString + " " + mContext.getString(R.string.Sunday);
                    break;
            }
            i = i << 1;
        }
        if (WkString.equals(mContext.getString(R.string.Monday)
                + " " + mContext.getString(R.string.Tuesday)
                + " " + mContext.getString(R.string.Wednesday)
                + " " + mContext.getString(R.string.Thursday)
                + " " + mContext.getString(R.string.Friday)
                + " " + mContext.getString(R.string.Saturday)
                + " " + mContext.getString(R.string.Sunday))) {
            WkString = mContext.getString(R.string.everyday);
        } else if (WkString.equals(mContext.getString(R.string.Monday)
                + " " + mContext.getString(R.string.Tuesday)
                + " " + mContext.getString(R.string.Wednesday)
                + " " + mContext.getString(R.string.Thursday)
                + " " + mContext.getString(R.string.Friday))) {
            WkString = mContext.getString(R.string.Daily);
        } else if (WkString.equals(" " + mContext.getString(R.string.Saturday)
                + " " + mContext.getString(R.string.Sunday))) {
            WkString = mContext.getString(R.string.Weekdays);
        }
        return WkString;
    }

    /**
     * 反映射获取bodyLocKey
     *
     * @param bodyLocKey
     * @return
     */
    public static int getBodyString(String bodyLocKey) {
        try {
            Field field = R.string.class.getField(bodyLocKey);
            int i = field.getInt(new R.drawable());
            return i;
        } catch (Exception e) {
            BaseApplication.getLogger().e(e.toString());
            return R.string.unknow;
        }
    }

    /**
     * 判断程序是否处于后台云行
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }

    /**
     * 后台推送消息
     * <p>
     * //     * @param deviceName 设备名字
     * //     * @param deviceType 设备类型
     *
     * @param deviceMac 设备MAC
     * @param zigbeeMac Zigbee设备MAC
     * @param Ticker    标题
     * @param Title     头部
     * @param Content   内容
     * @param soundUri  音乐URL
     * @param icon      头像
     * @param ID        消息ID
     */
    private static void backgroundDisplay(String deviceMac, String zigbeeMac, String Ticker, long When, String Title, String Content, Uri soundUri, int icon, int ID) {
        Class clazz = null;
        try {
            clazz = Class.forName("com.heiman.smarthome.activity.MainActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(BaseApplication.getMyApplication(), clazz);
//        intent.putExtra(Constants.DEVICE_NAME, deviceName);
//        intent.putExtra(Constants.DEVICE_TYPES, deviceType);
        intent.putExtra(Constant.DEVICE_MAC, deviceMac);
        intent.putExtra(Constant.ZIGBEE_MAC, zigbeeMac);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(BaseApplication.getMyApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] vibrate = {100, 1000, 1000 * 30, 1000};
        Notification notification = new Notification.Builder(BaseApplication.getMyApplication())//实例化Builder
                .setTicker(Ticker)//在状态栏显示的标题
                .setWhen(When)//设置显示的时间，默认就是currentTimeMillis()
                .setContentTitle(Title)//设置标题
                .setContentText(Content)//设置内容
                .setSound(soundUri)
                .setVibrate(vibrate)
                .setLights(0x00FF00, 300, 1000)
                .setSmallIcon(icon) //设置图标
                .setWhen(System.currentTimeMillis()) //发送时间
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//设置是否自动按下过后取消
                .setOngoing(false)//设置为true时就不能删除  除非使用notificationManager.cancel(1)方法
                .build();//创建Notification
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.flags |= Notification.FLAG_INSISTENT;
        NotificationManager nManager = (NotificationManager) BaseApplication.getMyApplication().getSystemService(BaseApplication.getMyApplication().NOTIFICATION_SERVICE);
        nManager.notify(ID, notification);// id是应用中通知的唯一标识
    }

    /**
     * 前台显示
     * <p>
     * //     * @param deviceName 设备名字
     * //     * @param deviceType 设备类型
     *
     * @param deviceMac 设备MAC
     * @param zigbeeMac Zigbee设备MAC
     */
    private static void broughtDisplay(final String deviceMac, final String zigbeeMac, String Title, String Content, int icon) {
        Class clazz = null;
        try {
            clazz = Class.forName("com.heiman.smarthome.activity.AlarmActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(BaseApplication.getMyApplication(), clazz);
        intent.putExtra(Constant.DEVICE_MAC, deviceMac);
        if (!isEmptyString(zigbeeMac)) {
            intent.putExtra(Constant.IS_SUB, true);
        }
        intent.putExtra(Constant.IS_DEVICE, true);
        intent.putExtra(Constant.ZIGBEE_MAC, zigbeeMac);
        intent.putExtra("Title", Title);
        intent.putExtra("Content", Content);
        intent.putExtra("icon", icon);
        BaseApplication.getMyApplication().getCurrentActivity().startActivity(intent);
    }

    public static int getDeviceIcon() {
        return R.mipmap.ic_launcher;
    }

    /**
     * 显示报警消息
     *
     * @param eventNotify
     */
    public static void showAlarm(EventNotify eventNotify) {
        byte[] new_bts = Arrays.copyOfRange(eventNotify.notifyData, 2,
                eventNotify.notifyData.length);
        try {
            String res = new String(new_bts, "UTF-8");
            BaseApplication.getLogger().json(res);
            Gson gson = new Gson();
            try {
                EventNotifyData eventNotifyData = gson.fromJson(res, EventNotifyData.class);
                Notifications notification = gson.fromJson(eventNotifyData.getValue(), Notifications.class);
                String Title = notification.getNotification().getTitle();
                List<String> bodyLocArgs = notification.getNotification().getBody_loc_args();
                String Sound = notification.getNotification().getSound();
                String bodyLocKey = notification.getNotification().getBody_loc_key();
                XlinkDevice xlinkDevice = DeviceManage.getInstance().getDevice(eventNotify.formId);
                Uri soundUri;
                if (Sound.equals("alarm.mp3")) {
                    soundUri = Uri.parse("android.resource://" + BaseApplication.getMyApplication().getPackageName() + "/" + R.raw.alarm);
                } else if (Sound.equals("alarm_119.mp3")) {
                    soundUri = Uri.parse("android.resource://" + BaseApplication.getMyApplication().getPackageName() + "/" + R.raw.alarm_119);
                } else {
                    soundUri = Uri.parse("android.resource://" + BaseApplication.getMyApplication().getPackageName() + "/" + R.raw.message);
                }
                String zigbeeMac = "";
                String sAgeFormatString = BaseApplication.getMyApplication().getResources().getString(getBodyString(bodyLocKey));
                String Content = String.format(sAgeFormatString, bodyLocArgs.get(0));
                if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY) {
                    zigbeeMac = notification.getZigbeeMac();
                } else if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW || xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW) {
                    zigbeeMac = bodyLocArgs.get(1);
                }
                if (isApplicationBroughtToBackground(BaseApplication.getMyApplication())) {
                    try {
                        backgroundDisplay(xlinkDevice.getDeviceMac(), zigbeeMac, Title + BaseApplication.getMyApplication().getString(R.string.alarm), Time.stringToLong(bodyLocArgs.get(0), "yyyy-MM-dd HH:mm:ss"), Title, Content, soundUri, getDeviceIcon(), eventNotify.messageId);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    broughtDisplay(xlinkDevice.getDeviceMac(), zigbeeMac, Title, Content, getDeviceIcon());
                }

            } catch (Exception e) {
            }
            try {
                EventIsOnline eventIsOnline = gson.fromJson(res, EventIsOnline.class);
            } catch (Exception e) {

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
