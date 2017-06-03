package com.heiman.smarthome.modle;

/**
 * @Author : 肖力
 * @Time :  2017/6/1 14:50
 * @Description :
 * @Modify record :
 */

public class RoomEdit {

    public RoomEdit(boolean isSub, String deviceMac, int deviceType, String deviceName) {
        this.isSub = isSub;
        this.deviceMac = deviceMac;
        this.deviceType = deviceType;
        this.deviceName = deviceName;
    }

    private boolean isSub;
    private String deviceName;
    private String deviceMac;
    private int deviceType;

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
