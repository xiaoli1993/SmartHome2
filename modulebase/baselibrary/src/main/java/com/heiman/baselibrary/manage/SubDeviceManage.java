package com.heiman.baselibrary.manage;
/**
 * Copyright ©深圳市海曼科技有限公司
 */


import com.heiman.baselibrary.mode.SubDevice;

import org.litepal.crud.DataSupport;

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
public class SubDeviceManage {

    private static SubDeviceManage instance;
    public static ConcurrentHashMap<String, SubDevice> deviceMap = new ConcurrentHashMap<String, SubDevice>();

    public static SubDeviceManage getInstance() {
        if (instance == null) {
            instance = new SubDeviceManage();
        }
        return instance;
    }

    public static List<SubDevice> listDev = new ArrayList<SubDevice>();

    /**
     * 获取所有设备
     *
     * @return
     */
    public synchronized List<SubDevice> getDevices() {
        listDev.clear();
//        listDev = DataSupport.order("deviceType").find(SubDevice.class);
        Iterator<Map.Entry<String, SubDevice>> iter = deviceMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, SubDevice> entry = iter.next();
            listDev.add(entry.getValue());
        }
        return listDev;
    }

    public synchronized List<SubDevice> getDevices(String RoomID) {
        listDev.clear();
        Iterator<Map.Entry<String, SubDevice>> iter = deviceMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, SubDevice> entry = iter.next();
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
    public SubDevice getDevice(String mac, int index) {
//        List<SubDevice> Xldevice = DataSupport.where("deviceMac = ? and index = ?", mac, index + "").find(SubDevice.class);
        SubDevice dev = null;
        for (SubDevice device : getDevices()) {
            if (device.getIndex() == index) {
                dev = device;
                break;
            }
        }
        return dev;
    }

    /**
     * 获取单个设备
     *
     * @param mac
     * @return
     */
    public SubDevice getDevice(String mac, String subMac) {
//        List<SubDevice> Xldevice = DataSupport.where("deviceMac = ? and zigbeeMac = ?", mac, subMac).find(SubDevice.class);
        SubDevice dev = null;
        for (SubDevice device : getDevices()) {
            if (device.getZigbeeMac() == subMac) {
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
    public void addDevice(SubDevice dev) {
        List<SubDevice> Xldevice = null;
        SubDevice device = deviceMap.get(dev.getZigbeeMac());
        if (device != null) { // 如果已经保存过设备，就不add
            deviceMap.put(dev.getZigbeeMac(), device);
            dev.updateAllAsync("deviceMac = ? and zigbeeMac = ?", dev.getDeviceMac(), dev.getZigbeeMac());
            return;
        }
        deviceMap.put(dev.getZigbeeMac(), dev);
        dev.saveAsync();
//        try {
//            Xldevice = DataSupport.where("deviceMac = ? and zigbeeMac = ?", dev.getDeviceMac(), dev.getZigbeeMac() + "").find(SubDevice.class);
//        } catch (Exception e) {
//            dev.save();
//        }
//        if (!SmartHomeUtils.isEmptyList(Xldevice)) {
//            dev.updateAll("deviceMac = ? and zigbeeMac = ?", dev.getDeviceMac(), dev.getZigbeeMac());
//        } else {
//            dev.save();
//        }
    }

    /**
     * 更新数据
     *
     * @param device
     */
    public void updateDevice(SubDevice device) {
        deviceMap.remove(device.getDeviceMac());
        deviceMap.put(device.getDeviceMac(), device);
        device.updateAllAsync("deviceMac = ? and zigbeeMac = ?", device.getDeviceMac(), device.getZigbeeMac());
    }

    /**
     * 删除设备
     *
     * @param mac
     */
    public void removeDevice(String mac, String zigbeeMac) {
        deviceMap.remove(mac);
//        DataSupport.deleteAllAsync(SubDevice.class, "deviceMac = ?", mac);
        XlinkAgent.getInstance().removeDevice(mac);
        DataSupport.deleteAllAsync(SubDevice.class, "deviceMac = ? and zigbeeMac = ?", mac, zigbeeMac);

    }

    /**
     * 清空设备
     */
    public synchronized void clearAllDevice() {
        DataSupport.deleteAllAsync(SubDevice.class);
        deviceMap.clear();
        listDev.clear();
//        XlinkAgent.getInstance().removeAllDevice();
    }
}
