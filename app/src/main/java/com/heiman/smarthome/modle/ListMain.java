package com.heiman.smarthome.modle;

/**
 * @Author : 肖力
 * @Time :  2017/3/28 15:58
 * @Description :
 * @Modify record :
 */

public class ListMain {
    private String deviceMac;
    private String subMac;
    private int deviceType;
    private boolean isSub;

    public ListMain(String deviceMac, String subMac, boolean issub, int deviceType) {
        this.deviceMac = deviceMac;
        this.subMac = subMac;
        this.isSub = issub;
        this.deviceType = deviceType;
    }


    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getSubMac() {
        return subMac;
    }

    public void setSubMac(String subMac) {
        this.subMac = subMac;
    }
}
