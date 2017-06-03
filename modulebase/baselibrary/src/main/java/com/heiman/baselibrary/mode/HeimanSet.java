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
         * 2.1.1.1.7 : "+07:30"
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_TIME_ZONE)
        private String timeZone;
        /**
         * 2.1.1.255.0.1.1 : 56
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_SOUND_LEVEL)
        private int beepsoundlevel;
        /**
         * 2.1.1.255.0.1.2 : 56
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_TIMER)
        private int betimer;
        /**
         * 2.1.1.255.0.1.3 : US
         */

        @SerializedName(HeimanCom.COM_GW_OID.GW_LANGUAGE)
        private String gwlanguage;
        /**
         * 2.1.1.255.0.1.4 : 56
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_LIGHT_LEVEL)
        private int gwlightlevel;
        /**
         * 2.1.1.255.0.1.5 : 1
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_LIGHT_ONOFF)
        private int gwlightonoff;
        /**
         * 2.1.1.255.0.2 : DC_Gateway
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_NAME)
        private String _setGwName;


        public int getBetimer() {
            return betimer;
        }

        public void setBetimer(int betimer) {
            this.betimer = betimer;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public int getBeepsoundlevel() {
            return beepsoundlevel;
        }

        public void setBeepsoundlevel(int beepsoundlevel) {
            this.beepsoundlevel = beepsoundlevel;
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

        public String get_setGwName() {
            return _setGwName;
        }

        public void set_setGwName(String _setGwName) {
            this._setGwName = _setGwName;
        }
    }
}
