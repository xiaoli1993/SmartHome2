package com.heiman.gateway.modle;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/5/11 11:26
 * @Description :
 * @Modify record :
 */

public class SmartLinkS {
    /**
     * device : {"deviceName":"ZGW","macAddress":"845dd767d976","deviceIP":"IPV4_FMT"}
     */
    @Expose
    private DeviceBean device;

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public static class DeviceBean {
        /**
         * deviceName : ZGW
         * macAddress : 845dd767d976
         * deviceIP : IPV4_FMT
         */
        @Expose
        private String deviceName;
        @Expose
        private String macAddress;
        @Expose
        private String deviceIP;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public String getDeviceIP() {
            return deviceIP;
        }

        public void setDeviceIP(String deviceIP) {
            this.deviceIP = deviceIP;
        }
    }
}
