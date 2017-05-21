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

    /**
     * ENCRYPT : 0
     * PL : {"2.1.1.1.7":"+3"}
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
         * "2.1.1.1.7":"+3"
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_TIME_ZONE)
        private String timeZone;
        /**
         * 2.1.1.255.0.1.1 : 114
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_SOUND_LEVEL)
        private int beepsoundlevel;
        /**
         * "2.1.1.255.0.1.2":30
         */
        @Expose
        @SerializedName(HeimanCom.COM_GW_OID.GW_BEEP_TIMER)
        private int betimer;

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
    }
}
