package com.heiman.baselibrary.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/6/10 10:11
 * @Description :
 * @Modify record :
 */

public class ZigbeeNewDevice extends PLBase {
    /**
     * CID : 30022
     * PL : {"2.1.1.0":{"PG":10,"NU":1,"DEV":[{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"},{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"}]}}
     * RC : 1
     * SN : 8
     */

    private PLBean PL;

    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }


    public static class PLBean {
        /**
         * 2.1.1.0 : {"PG":10,"NU":1,"DEV":[{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"},{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"}]}
         */

        @SerializedName("2.1.1.0")
        private ZigbeeListBean ZigbeeList;

        public ZigbeeListBean getZigbeeList() {
            return ZigbeeList;
        }

        public void setZigbeeList(ZigbeeListBean ZigbeeList) {
            this.ZigbeeList = ZigbeeList;
        }

        public static class ZigbeeListBean {
            /**
             * PG : 10
             * NU : 1
             * DEV : [{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"},{"ZX":4096,"ZT":17,"ZM":"00AABB112233","ZN":"WDoor_4B342F12346789012346789012","RD":"gw123456789abcde"}]
             */

            @SerializedName("PG")
            private int ZbListSize;
            @SerializedName("NU")
            private int ZbListNu;
            @SerializedName("DEV")
            private List<SubDeviceBean> SubDevice;

            public int getZbListSize() {
                return ZbListSize;
            }

            public void setZbListSize(int ZbListSize) {
                this.ZbListSize = ZbListSize;
            }

            public int getZbListNu() {
                return ZbListNu;
            }

            public void setZbListNu(int ZbListNu) {
                this.ZbListNu = ZbListNu;
            }

            public List<SubDeviceBean> getSubDevice() {
                return SubDevice;
            }

            public void setSubDevice(List<SubDeviceBean> SubDevice) {
                this.SubDevice = SubDevice;
            }

            public static class SubDeviceBean {
                /**
                 * ZX : 4096
                 * ZT : 17
                 * ZM : 00AABB112233
                 * ZN : WDoor_4B342F12346789012346789012
                 * RD : gw123456789abcde
                 */

                @SerializedName("ZX")
                private int index;
                @SerializedName("ZT")
                private int deviceType;
                @SerializedName("ZM")
                private String deviceMac;
                @SerializedName("ZN")
                private String deviceName;
                @SerializedName("RD")
                private String roomID;

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public int getDeviceType() {
                    return deviceType;
                }

                public void setDeviceType(int deviceType) {
                    this.deviceType = deviceType;
                }

                public String getDeviceMac() {
                    return deviceMac;
                }

                public void setDeviceMac(String deviceMac) {
                    this.deviceMac = deviceMac;
                }

                public String getDeviceName() {
                    return deviceName;
                }

                public void setDeviceName(String deviceName) {
                    this.deviceName = deviceName;
                }

                public String getRoomID() {
                    return roomID;
                }

                public void setRoomID(String roomID) {
                    this.roomID = roomID;
                }
            }
        }
    }
}
