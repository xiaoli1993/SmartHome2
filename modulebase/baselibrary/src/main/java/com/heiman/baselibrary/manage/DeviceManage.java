package com.heiman.baselibrary.manage;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;

import org.json.JSONException;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public static ConcurrentHashMap<String, XlinkDevice> deviceMap = new ConcurrentHashMap<String, XlinkDevice>();

    public static DeviceManage getInstance() {
        if (instance == null) {
            instance = new DeviceManage();
        }
        return instance;
    }

    public static List<XlinkDevice> listDev = new ArrayList<XlinkDevice>();

    /**
     * 获取所有设备
     *
     * @return
     */
    public synchronized List<XlinkDevice> getDevices() {
        listDev.clear();
        Iterator<Map.Entry<String, XlinkDevice>> iter = deviceMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, XlinkDevice> entry = iter.next();
            try {
                XlinkAgent.getInstance().initDevice(entry.getValue().getxDevice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDev.add(entry.getValue());
        }
        return listDev;
    }

    public synchronized List<XlinkDevice> getDevices(String RoomID) {
        listDev.clear();
        Iterator<Map.Entry<String, XlinkDevice>> iter = deviceMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, XlinkDevice> entry = iter.next();
            try {
                XlinkAgent.getInstance().initDevice(entry.getValue().getxDevice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (entry.getValue().getRoomID().equals(RoomID)) {
                listDev.add(entry.getValue());
            }
        }
        return listDev;
    }

    /**
     * 获取单个设备
     *
     * @param mac
     * @return
     */
    public XlinkDevice getDevice(String mac) {
//        List<XlinkDevice> Xldevice = DataSupport.where("deviceMac = ?", mac).find(XlinkDevice.class);
        XlinkDevice dev = null;
        for (XlinkDevice device : getDevices()) {
            if (device.getDeviceMac().equals(mac)) {
                dev = device;
                break;
            }
        }
        return dev;
    }

    /**
     * 获取单个设备
     *
     * @param deviceid
     * @return
     */
    public XlinkDevice getDevice(int deviceid) {
//        List<XlinkDevice> Xldevice = DataSupport.where("deviceId = ?", deviceid + "").find(XlinkDevice.class);
        XlinkDevice dev = null;
        for (XlinkDevice device : getDevices()) {
            if (device.getDeviceId() == deviceid) {
                dev = device;
                break;
            }
        }
        return dev;
    }

    /**
     * 添加设备
     *
     * @param dev
     */
    public void addDevice(XlinkDevice dev) {
        XlinkDevice device = deviceMap.get(dev.getDeviceMac());
        if (device != null) { // 如果已经保存过设备，就不add
            deviceMap.put(dev.getDeviceMac(), device);
            dev.updateAllAsync("deviceMac = ?", dev.getDeviceMac()).listen(new UpdateOrDeleteCallback() {
                @Override
                public void onFinish(int rowsAffected) {
//                    BaseApplication.getLogger().i("更新设备：" + rowsAffected);
                }
            });
//            BaseApplication.getLogger().i("更新设备：" + dev.getDeviceMac());
            return;
        }
        deviceMap.put(dev.getDeviceMac(), dev);
//        dev.saveAsync();

        List<XlinkDevice> Xldevice = null;
        try {
            Xldevice = DataSupport.where("deviceMac = ?", dev.getDeviceMac()).find(XlinkDevice.class);
//            BaseApplication.getLogger().i("获取设备：" + Xldevice.get(0).getDeviceMac());
        } catch (Exception e) {
            dev.saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
//                    BaseApplication.getLogger().i("添加设备：" + success);
                }
            });
//            BaseApplication.getLogger().i("添加设备：" + dev.getDeviceMac());
        }
        if (!SmartHomeUtils.isEmptyList(Xldevice)) {
            dev.updateAllAsync("deviceMac = ?", dev.getDeviceMac());
//            BaseApplication.getLogger().i("更新设备：" + dev.getDeviceMac());
        } else {
            dev.saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
//                    BaseApplication.getLogger().i("添加设备：" + success);
                }
            });
//            BaseApplication.getLogger().i("添加设备：" + dev.getDeviceMac());
        }
    }

    public void addDevice(XlinkDevice dev, boolean isGet) {
        XlinkDevice device = deviceMap.get(dev.getDeviceMac());
        if (device != null) { // 如果已经保存过设备，就不add
            deviceMap.put(dev.getDeviceMac(), device);
            dev.updateAllAsync("deviceMac = ?", dev.getDeviceMac());

            return;
        }
        deviceMap.put(dev.getDeviceMac(), dev);
//        dev.saveAsync();
        if (isGet) {
            List<XlinkDevice> Xldevice = null;
            try {
                Xldevice = DataSupport.where("deviceMac = ?", dev.getDeviceMac()).find(XlinkDevice.class);
            } catch (Exception e) {
                dev.saveAsync();
            }
            if (Xldevice != null && !Xldevice.isEmpty()) {
                dev.updateAllAsync("deviceMac = ?", dev.getDeviceMac());
            } else {
                dev.saveAsync();
            }
        }
    }

    /**
     * 更新数据
     *
     * @param device
     */
    public void updateDevice(XlinkDevice device) {
        deviceMap.remove(device.getDeviceMac());
        deviceMap.put(device.getDeviceMac(), device);
        device.updateAllAsync("deviceMac = ?", device.getDeviceMac());
    }

    /**
     * 删除设备
     *
     * @param mac
     */
    public void removeDevice(String mac) {
        deviceMap.remove(mac);
        DataSupport.deleteAllAsync(XlinkDevice.class, "deviceMac = ?", mac);
        XlinkAgent.getInstance().removeDevice(mac);
    }

    /**
     * 清空设备
     */
    public synchronized void clearAllDevice() {
        DataSupport.deleteAllAsync(XlinkDevice.class);
        deviceMap.clear();
        listDev.clear();
        XlinkAgent.getInstance().removeAllDevice();
    }
}
