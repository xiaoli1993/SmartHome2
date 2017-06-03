package com.heiman.smarthome.modle;

/**
 * @Author : 肖力
 * @Time :  2017/6/1 19:10
 * @Description :
 * @Modify record :
 */

public class DeviceType {

    public DeviceType(boolean isSub, int itemType, int deviceType, String textString) {
        this.isSub = isSub;
        this.itemType = itemType;
        this.deviceType = deviceType;
        this.textString = textString;
    }

    public static final int ITEM_TYPE_WIFI_DEVICE = 0;
    public static final int ITEM_TYPE_SUB_DEVICE = 1;
    public static final int ITEM_TYPE_TEXT = 2;
    private int itemType;
    private int deviceType;
    private boolean isSub;
    private String textString;

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
