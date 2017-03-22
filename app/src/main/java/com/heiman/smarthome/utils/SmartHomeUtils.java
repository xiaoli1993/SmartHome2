package com.heiman.smarthome.utils;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.heiman.smarthome.manage.DeviceManage;
import com.heiman.smarthome.mode.XlinkDevice;
import com.heiman.smarthome.smarthomesdk.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static XlinkDevice subscribeToDevice(JSONObject jsonObj) {
        XlinkDevice device = new XlinkDevice();
        int devicetype = 0;
        String deviceName = "";
        int version = 0;
        try {
            String product_id = jsonObj.getString("product_id");
            int mcu_version = jsonObj.getInt("mcu_version");
            String mac = jsonObj.getString("mac");
            int firmware_version = jsonObj.getInt("firmware_version");
            int access_key = jsonObj.getInt("access_key");
            long deviceid = jsonObj.getLong("id");
            try {
                if (product_id.equals(Constants.ZIGBEE_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_GATEWAY;
                    version = 2;
                } else if (product_id.equals(Constants.PLUGIN_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_PLUGIN;
                    version = 1;
                } else if (product_id.equals(Constants.METRTING_PLUGIN_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN;
                    version = 2;
                } else if (product_id.equals(Constants.AIR_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_AIR;
                    version = 2;
                } else if (product_id.equals(Constants.REMOTE_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_RC;
                    version = 2;
                } else if (product_id.equals(Constants.GAS_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_GAS;
                    version = 2;
                }
                deviceName = jsonObj.getString("name");
                if (deviceName.equals("") || deviceName == null) {
                    if (product_id.equals(Constants.ZIGBEE_PRODUCTID)) {
                        deviceName = "ZGW_" + mac;
                    } else if (product_id.equals(Constants.PLUGIN_PRODUCTID)) {
                        deviceName = "PLUG_" + mac;
                    } else if (product_id.equals(Constants.METRTING_PLUGIN_PRODUCTID)) {
                        deviceName = "EWPLUG_" + mac;
                    } else if (product_id.equals(Constants.AIR_PRODUCTID)) {
                        deviceName = "AIR_" + mac;
                    } else if (product_id.equals(Constants.REMOTE_PRODUCTID)) {
                        deviceName = "RC_" + mac;
                    } else if (product_id.equals(Constants.GAS_PRODUCTID)) {
                        deviceName = "Gas_" + mac;
                    }
                }
            } catch (Exception e) {
                if (product_id.equals(Constants.ZIGBEE_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_GATEWAY;
                    deviceName = "ZGW_" + mac;
                    version = 2;
                } else if (product_id.equals(Constants.PLUGIN_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_PLUGIN;
                    deviceName = "PLUG_" + mac;
                    version = 1;
                } else if (product_id.equals(Constants.METRTING_PLUGIN_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN;
                    deviceName = "EWPLUG_" + mac;
                    version = 2;
                } else if (product_id.equals(Constants.AIR_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_AIR;
                    deviceName = "AIR_" + mac;
                    version = 2;
                } else if (product_id.equals(Constants.REMOTE_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_RC;
                    deviceName = "RC_" + mac;
                    version = 2;
                } else if (product_id.equals(Constants.GAS_PRODUCTID)) {
                    devicetype = Constants.DEVICE_TYPE.DEVICE_WIFI_GAS;
                    deviceName = "Gas_" + mac;
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
            XDevice xdevice = XlinkAgent.JsonToDevice(obj);
            device.setxDevice(obj.toString());
            device.setDeviceType(devicetype);
            device.setDeviceName(deviceName);
            DeviceManage.getInstance().addDevice(device);
            return device;
        } catch (JSONException e) {
            return null;
        }
    }

}
