package com.heiman.baselibrary.manage;
/**
 * Copyright ©深圳市海曼科技有限公司
 */


import com.heiman.baselibrary.mode.SubDevice;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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
        listDev = DataSupport.order("deviceType").find(SubDevice.class);
        return listDev;
    }

    /**
     * 获取单个设备
     *
     * @param mac
     * @return
     */
    public SubDevice getDevice(String mac, int index) {
        List<SubDevice> Xldevice = DataSupport.where("deviceMac = ? and index = ?", mac, index + "").find(SubDevice.class);
        return Xldevice.get(0);
    }

    /**
     * 获取单个设备
     *
     * @param mac
     * @return
     */
    public SubDevice getDevice(String mac, String subMac) {
        List<SubDevice> Xldevice = DataSupport.where("deviceMac = ? and zigbeeMac = ?", mac, subMac).find(SubDevice.class);
        return Xldevice.get(0);
    }

    /**
     * 添加设备
     *
     * @param dev
     */
    public void addDevice(SubDevice dev) {
        List<SubDevice> Xldevice = null;
        try {
            Xldevice = DataSupport.where("deviceMac = ? and zigbeeMac = ?", dev.getWifiDevice().getDeviceMac(), dev.getZigbeeMac() + "").find(SubDevice.class);
        } catch (Exception e) {
            dev.save();
        }
        if (Xldevice != null && !Xldevice.isEmpty()) {
            dev.updateAll("deviceMac = ? and zigbeeMac = ?", dev.getWifiDevice().getDeviceMac(), dev.getZigbeeMac());
        } else {
            dev.save();
        }
    }

    /**
     * 更新数据
     *
     * @param device
     */
    public void updateDevice(SubDevice device) {
        device.updateAll("deviceMac = ? and zigbeeMac = ?", device.getWifiDevice().getDeviceMac(), device.getZigbeeMac());
    }

    /**
     * 删除设备
     *
     * @param mac
     */
    public void removeDevice(String mac, String zigbeeMac) {
        DataSupport.deleteAll(SubDevice.class, "deviceMac = ? and zigbeeMac = ?", mac, zigbeeMac);

    }

    /**
     * 清空设备
     */
    public synchronized void clearAllDevice() {
        DataSupport.deleteAll(SubDevice.class);
        listDev.clear();
        XlinkAgent.getInstance().removeAllDevice();
    }
}
