package com.heiman.smarthome.manage;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.mode.XlinkDevice;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/22 14:23
 * @Description :
 */
public class DeviceManage {

    private static DeviceManage instance;

    public static DeviceManage getInstance() {
        if (instance == null) {
            instance = new DeviceManage();
        }
        return instance;
    }

    public static List<XlinkDevice> listDev = new ArrayList<XlinkDevice>();

    /**
     * 获取所有设备
     * @return
     */
    public synchronized List<XlinkDevice> getDevices() {
        listDev.clear();
        DataSupport.findAllAsync(XlinkDevice.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                listDev = (List<XlinkDevice>) t;
                for (XlinkDevice device : listDev) {
                    try {
                        JSONObject devicejson = new JSONObject(device.getxDevice());
                        XDevice xdevice = XlinkAgent.JsonToDevice(devicejson);
                        XlinkAgent.getInstance().initDevice(xdevice);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return listDev;
    }

    /**
     * 获取单个设备
     * @param mac
     * @return
     */
    public XlinkDevice getDevice(String mac) {
        List<XlinkDevice> Xldevice = DataSupport.where("deviceMac = ?", mac).find(XlinkDevice.class);
        return Xldevice.get(0);
    }
    /**
     * 获取单个设备
     * @param deviceid
     * @return
     */
    public XlinkDevice getDevice(int deviceid) {
        List<XlinkDevice> Xldevice = DataSupport.where("deviceId = ?", deviceid+"").find(XlinkDevice.class);
        return Xldevice.get(0);
    }
    /**
     * 添加设备
     * @param dev
     */
    public void addDevice(XlinkDevice dev) {
        List<XlinkDevice> Xldevice = DataSupport.where("deviceMac = ?", dev.getDeviceMac()).find(XlinkDevice.class);
        if (Xldevice != null && !Xldevice.isEmpty()) {
            dev.save();
        } else {
            dev.updateAll("deviceMac = ?", dev.getDeviceMac());
        }
    }

    /**
     * 更新数据
     * @param device
     */
    public void updateDevice(XlinkDevice device) {
        device.updateAll("deviceMac = ?", device.getDeviceMac());
    }

    /**
     * 删除设备
     * @param mac
     */
    public void removeDevice(String mac) {
        DataSupport.deleteAll(XlinkDevice.class, "deviceMac = ?", mac);
        XlinkAgent.getInstance().removeDevice(mac);
    }

    /**
     * 清空设备
     */
    public synchronized void clearAllDevice() {
        DataSupport.deleteAll(XlinkDevice.class);
        listDev.clear();
        XlinkAgent.getInstance().removeAllDevice();
        MyApplication.getLogger().e("清空数据++++clearAllDevice");
    }
}
