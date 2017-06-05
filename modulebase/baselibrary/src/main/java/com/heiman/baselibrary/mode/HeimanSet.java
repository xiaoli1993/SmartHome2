package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.heiman.baselibrary.Json.HeimanCom;

/**
 * @Author : 肖力
 * @Time :  2017/5/21 10:03
 * @Description :设置函数类
 * @Modify record :
 */

public class HeimanSet extends PLBase {

    private PLBean PL;


    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }

    public static class PLBean {
        /**
         * 2.1.1.1.2 : {"timeZone":"+3.00"}
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_TIME_ZONE)
        private TimeZoneOID timeZoneOID;

        public static class TimeZoneOID {
            @Expose
            private String timeZone;

            public String getTimeZone() {
                return timeZone;
            }

            public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
            }
        }

        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_ADD_SUB)
        private AddSubOID addSubOID;

        public static class AddSubOID {
            @Expose
            private int join;

            public int getJoin() {
                return join;
            }

            public void setJoin(int join) {
                this.join = join;
            }
        }

        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GET_AES_KEY)
        private AESKeyOID aesKeyOID;

        public static class AESKeyOID {
            @Expose
            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_NAME)
        private DeviceNameOID deviceNameOID;

        public static class DeviceNameOID {
            @Expose
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION)
        private GwBasicOID gwBasicOID;

        public static class GwBasicOID {
            @Expose
            private int armtype = 9999;
            @Expose
            private int alarmlevel = 9999;
            @Expose
            private int soundlevel = 9999;
            @Expose
            private int betimer = 9999;
            @Expose
            private String gwlanguage = "";
            @Expose
            private int gwlightlevel = 9999;
            @Expose
            private int gwlightonoff = 9999;
            @Expose
            private int lgtimer = 9999;
            @Expose
            private String roomID = "";

            public int getArmtype() {
                return armtype;
            }

            public void setArmtype(int armtype) {
                this.armtype = armtype;
            }

            public int getAlarmlevel() {
                return alarmlevel;
            }

            public void setAlarmlevel(int alarmlevel) {
                this.alarmlevel = alarmlevel;
            }

            public int getSoundlevel() {
                return soundlevel;
            }

            public void setSoundlevel(int soundlevel) {
                this.soundlevel = soundlevel;
            }

            public int getBetimer() {
                return betimer;
            }

            public void setBetimer(int betimer) {
                this.betimer = betimer;
            }

            public String getGwlanguage() {
                return gwlanguage;
            }

            public void setGwlanguage(String gwlanguage) {
                this.gwlanguage = gwlanguage;
            }

            public int getGwlightlevel() {
                return gwlightlevel;
            }

            public void setGwlightlevel(int gwlightlevel) {
                this.gwlightlevel = gwlightlevel;
            }

            public int getGwlightonoff() {
                return gwlightonoff;
            }

            public void setGwlightonoff(int gwlightonoff) {
                this.gwlightonoff = gwlightonoff;
            }

            public int getLgtimer() {
                return lgtimer;
            }

            public void setLgtimer(int lgtimer) {
                this.lgtimer = lgtimer;
            }

            public String getRoomID() {
                return roomID;
            }

            public void setRoomID(String roomID) {
                this.roomID = roomID;
            }
        }

        public AddSubOID getAddSubOID() {
            return addSubOID;
        }

        public void setAddSubOID(AddSubOID addSubOID) {
            this.addSubOID = addSubOID;
        }

        public TimeZoneOID getTimeZoneOID() {
            return timeZoneOID;
        }

        public void setTimeZoneOID(TimeZoneOID timeZoneOID) {
            this.timeZoneOID = timeZoneOID;
        }

        public AESKeyOID getAesKeyOID() {
            return aesKeyOID;
        }

        public void setAesKeyOID(AESKeyOID aesKeyOID) {
            this.aesKeyOID = aesKeyOID;
        }

        public DeviceNameOID getDeviceNameOID() {
            return deviceNameOID;
        }

        public void setDeviceNameOID(DeviceNameOID deviceNameOID) {
            this.deviceNameOID = deviceNameOID;
        }

        public GwBasicOID getGwBasicOID() {
            return gwBasicOID;
        }

        public void setGwBasicOID(GwBasicOID gwBasicOID) {
            this.gwBasicOID = gwBasicOID;
        }
//        /**
//         * 2.1.1.1.7 : "+07:30"
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_TIME_ZONE)
//        private String timeZone;
//        /**
//         * 2.1.1.255.0.1.1 : 56
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_SOUND_LEVEL)
//        private int beepsoundlevel;
//        /**
//         * 2.1.1.255.0.1.2 : 56
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_TIMER)
//        private int betimer;
//        /**
//         * 2.1.1.255.0.1.3 : US
//         */
//
//        @SerializedName(HeimanCom.COM_GW_OID.GW_LANGUAGE)
//        private String gwlanguage;
//        /**
//         * 2.1.1.255.0.1.4 : 56
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_LIGHT_LEVEL)
//        private int gwlightlevel;
//        /**
//         * 2.1.1.255.0.1.5 : 1
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_LIGHT_ONOFF)
//        private int gwlightonoff;
//        /**
//         * 2.1.1.255.0.2 : DC_Gateway
//         */
//        @Expose
//        @SerializedName(HeimanCom.COM_GW_OID.GW_NAME)
//        private String _setGwName;
//
//
//        public int getBetimer() {
//            return betimer;
//        }
//
//        public void setBetimer(int betimer) {
//            this.betimer = betimer;
//        }
//
//        public String getTimeZone() {
//            return timeZone;
//        }
//
//        public void setTimeZone(String timeZone) {
//            this.timeZone = timeZone;
//        }
//
//        public int getBeepsoundlevel() {
//            return beepsoundlevel;
//        }
//
//        public void setBeepsoundlevel(int beepsoundlevel) {
//            this.beepsoundlevel = beepsoundlevel;
//        }
//
//        public String getGwlanguage() {
//            return gwlanguage;
//        }
//
//        public void setGwlanguage(String gwlanguage) {
//            this.gwlanguage = gwlanguage;
//        }
//
//        public int getGwlightlevel() {
//            return gwlightlevel;
//        }
//
//        public void setGwlightlevel(int gwlightlevel) {
//            this.gwlightlevel = gwlightlevel;
//        }
//
//        public int getGwlightonoff() {
//            return gwlightonoff;
//        }
//
//        public void setGwlightonoff(int gwlightonoff) {
//            this.gwlightonoff = gwlightonoff;
//        }
//
//        public String get_setGwName() {
//            return _setGwName;
//        }
//
//        public void set_setGwName(String _setGwName) {
//            this._setGwName = _setGwName;
//        }
    }
}
