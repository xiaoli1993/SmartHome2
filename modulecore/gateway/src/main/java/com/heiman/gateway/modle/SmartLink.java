package com.heiman.gateway.modle;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/5/10 16:04
 * @Description :
 * @Modify record :
 */

public class SmartLink {


    /**
     * t : configok
     * r : 200
     * device : {"protocol":1,"device":{"deviceName":"","macAddress":"A020A6121309","deviceIP":"255.255.255.255","deviceID":1144501433,"productID":"160edcb0403e3400160edcb0403e3401","devicePort":5987,"version":2,"mcuHardVersion":0,"mcuSoftVersion":1,"deviceInit":true,"accesskey":134637,"subkey":-1}}
     */
    @Expose
    private String t;
    @Expose
    private int r;
    @Expose
    private DeviceBeanX device;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public DeviceBeanX getDevice() {
        return device;
    }

    public void setDevice(DeviceBeanX device) {
        this.device = device;
    }

    public static class DeviceBeanX {
        /**
         * protocol : 1
         * device : {"deviceName":"","macAddress":"A020A6121309","deviceIP":"255.255.255.255","deviceID":1144501433,"productID":"160edcb0403e3400160edcb0403e3401","devicePort":5987,"version":2,"mcuHardVersion":0,"mcuSoftVersion":1,"deviceInit":true,"accesskey":134637,"subkey":-1}
         */
        @Expose
        private int protocol;
        @Expose
        private DeviceBean device;

        public int getProtocol() {
            return protocol;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
        }

        public static class DeviceBean {
            /**
             * deviceName :
             * macAddress : A020A6121309
             * deviceIP : 255.255.255.255
             * deviceID : 1144501433
             * productID : 160edcb0403e3400160edcb0403e3401
             * devicePort : 5987
             * version : 2
             * mcuHardVersion : 0
             * mcuSoftVersion : 1
             * deviceInit : true
             * accesskey : 134637
             * subkey : -1
             */
            @Expose
            private String deviceName;
            @Expose
            private String macAddress;
            @Expose
            private String deviceIP;
            @Expose
            private int deviceID;
            @Expose
            private String productID;
            @Expose
            private int devicePort;
            @Expose
            private int version;
            @Expose
            private int mcuHardVersion;
            @Expose
            private int mcuSoftVersion;
            @Expose
            private boolean deviceInit;
            @Expose
            private int accesskey;
            @Expose
            private int subkey;

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

            public int getDeviceID() {
                return deviceID;
            }

            public void setDeviceID(int deviceID) {
                this.deviceID = deviceID;
            }

            public String getProductID() {
                return productID;
            }

            public void setProductID(String productID) {
                this.productID = productID;
            }

            public int getDevicePort() {
                return devicePort;
            }

            public void setDevicePort(int devicePort) {
                this.devicePort = devicePort;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            public int getMcuHardVersion() {
                return mcuHardVersion;
            }

            public void setMcuHardVersion(int mcuHardVersion) {
                this.mcuHardVersion = mcuHardVersion;
            }

            public int getMcuSoftVersion() {
                return mcuSoftVersion;
            }

            public void setMcuSoftVersion(int mcuSoftVersion) {
                this.mcuSoftVersion = mcuSoftVersion;
            }

            public boolean isDeviceInit() {
                return deviceInit;
            }

            public void setDeviceInit(boolean deviceInit) {
                this.deviceInit = deviceInit;
            }

            public int getAccesskey() {
                return accesskey;
            }

            public void setAccesskey(int accesskey) {
                this.accesskey = accesskey;
            }

            public int getSubkey() {
                return subkey;
            }

            public void setSubkey(int subkey) {
                this.subkey = subkey;
            }
        }
    }

}
