package com.heiman.baselibrary.utils;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.R;
import com.heiman.baselibrary.http.HttpConstant;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.HttpDevice;
import com.heiman.baselibrary.mode.XlinkDevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;

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
                    version = 2;
                }
                deviceName = httpDevice.getName();
                if (deviceName.equals("") || deviceName == null) {
                    if (product_id.equals(Constant.ZIGBEE_PRODUCTID) || product_id.equals(Constant.ZIGBEE_H1GW_NEW_PRODUCTID)) {
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
                    version = 2;
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
}
