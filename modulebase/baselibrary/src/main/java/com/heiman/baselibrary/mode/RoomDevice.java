package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/5/31 10:23
 * @Description : 具体房间设备
 * @Modify record :
 */

public class RoomDevice {
    /**
     * @param Room_id      房间ID
     * @param device_id    设备ID
     * @param subdevice_id 子设备ID
     */
    public RoomDevice(String Room_id, String device_id, String subdevice_id) {
        this.Room_id = Room_id;
        this.device_id = device_id;
        this.subdevice_id = subdevice_id;
    }

    private String Room_id;
    private String device_id;
    private String subdevice_id;

    public String getRoom_id() {
        return Room_id;
    }

    public void setRoom_id(String room_id) {
        Room_id = room_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSubdevice_id() {
        return subdevice_id;
    }

    public void setSubdevice_id(String subdevice_id) {
        this.subdevice_id = subdevice_id;
    }
}
