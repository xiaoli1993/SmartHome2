package com.heiman.smarthome.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/14 19:04
 * @Description :
 * @Modify record :
 */

public class DeviceMessages {
    /**
     * MG : {"ST":1,"SM":"","OF":1,"TP":"25","HD":"88","IA":"1","TV":0,"P2":20,"P1":22,"AQ":28,"CC":20,"IS":0,"SD":1,"SN":"","LD":2,"LN":"","TD":2,"DL":12,"UD":"2134","UN":"2","VS":"1.2.1","EP":1}
     * TM : 2017-3-1418: 36: 31
     * MT : 0
     * WM : 600194133048
     * WT : 100
     */
    @Expose
    private MGBean MG;
    @Expose
    private String TM;
    @Expose
    private int MT;
    @Expose
    private String WM;
    @Expose
    private int WT;

    public MGBean getMG() {
        return MG;
    }

    public void setMG(MGBean MG) {
        this.MG = MG;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public int getMT() {
        return MT;
    }

    public void setMT(int MT) {
        this.MT = MT;
    }

    public String getWM() {
        return WM;
    }

    public void setWM(String WM) {
        this.WM = WM;
    }

    public int getWT() {
        return WT;
    }

    public void setWT(int WT) {
        this.WT = WT;
    }

    public static class MGBean {
        /**
         * ST : 1
         * SM :
         * OF : 1
         * TP : 25
         * HD : 88
         * IA : 1
         * TV : 0
         * P2 : 20
         * P1 : 22
         * AQ : 28
         * CC : 20
         * IS : 0
         * SD : 1
         * SN :
         * LD : 2
         * LN :
         * TD : 2
         * DL : 12
         * UD : 2134
         * UN : 2
         * VS : 1.2.1
         * EP : 1
         */
        @Expose
        private int ST;
        @Expose
        private String SM;
        @Expose
        private int OF;
        @Expose
        private String TP;
        @Expose
        private String HD;
        @Expose
        private String IA;
        @Expose
        private int TV;
        @Expose
        private int P2;
        @Expose
        private int P1;
        @Expose
        private int AQ;
        @Expose
        private int CC;
        @Expose
        private int IS;
        @Expose
        private int SD;
        @Expose
        private String SN;
        @Expose
        private int LD;
        @Expose
        private String LN;
        @Expose
        private int TD;
        @Expose
        private int DL;
        @Expose
        private String UD;
        @Expose
        private String UN;
        @Expose
        private String VS;
        @Expose
        private int EP;
        @Expose
        @SerializedName("AL")
        private String alarmlevel;
        @Expose
        @SerializedName("SL")
        private String soundlevel;
        @Expose
        @SerializedName("BT")
        private String betimer;
        @Expose
        @SerializedName("LG")
        private String gwlanguage;
        @Expose
        @SerializedName("LL")
        private String gwlightlevel;
        @Expose
        @SerializedName("LO")
        private String gwlightonoff;
        @Expose
        @SerializedName("LT")
        private String lgtimer;
        @Expose
        @SerializedName("AT")
        private String armtype;
        @Expose
        @SerializedName("RD")
        private String roomID;
        @Expose
        @SerializedName("OF1")
        private int onoff1;
        @Expose
        @SerializedName("OF2")
        private int onoff2;
        @Expose
        @SerializedName("OF3")
        private int onoff3;
        @Expose
        @SerializedName("TM1")
        private int enableTime1;
        @Expose
        @SerializedName("HS1")
        private int sh1;
        @Expose
        @SerializedName("MS1")
        private int sm1;
        @Expose
        @SerializedName("HE1")
        private int eh1;
        @Expose
        @SerializedName("ME1")
        private int em1;
        @Expose
        @SerializedName("WF1")
        private int wf1;
        @Expose
        @SerializedName("TM2")
        private int enableTime2;
        @Expose
        @SerializedName("HS2")
        private int sh2;
        @Expose
        @SerializedName("MS2")
        private int sm2;
        @Expose
        @SerializedName("EH2")
        private int eh2;
        @Expose
        @SerializedName("ME2")
        private int em2;
        @Expose
        @SerializedName("WF2")
        private int wf2;
        @Expose
        @SerializedName("TM3")
        private int enableTime3;
        @Expose
        @SerializedName("HS3")
        private int sh3;
        @Expose
        @SerializedName("MS3")
        private int sm3;
        @Expose
        @SerializedName("HE3")
        private int eh3;
        @Expose
        @SerializedName("ME3")
        private int em3;
        @Expose
        @SerializedName("WF3")
        private int wf3;
        @Expose
        @SerializedName("RO")
        private int relayonoff = 9999;
        @Expose
        @SerializedName("ETR")
        private int enableTime_r = 9999;
        @Expose
        @SerializedName("HSr")
        private int sh_r = 9999;
        @Expose
        @SerializedName("MSr")
        private int sm_r = 9999;
        @Expose
        @SerializedName("HEr")
        private int eh_r = 9999;
        @Expose
        @SerializedName("MEr")
        private int em_r = 9999;
        @Expose
        @SerializedName("WFr")
        private int wf_r = 9999;
        @Expose
        @SerializedName("UO")
        private int usbonoff = 9999;
        @Expose
        @SerializedName("ETU")
        private int enableTime_u = 9999;
        @Expose
        @SerializedName("HSu")
        private int sh_u = 9999;
        @Expose
        @SerializedName("MSu")
        private int sm_u = 9999;
        @Expose
        @SerializedName("HEu")
        private int eh_u = 9999;
        @Expose
        @SerializedName("MEu")
        private int em_u = 9999;
        @Expose
        @SerializedName("WFu")
        private int wf_u = 9999;
        @Expose
        @SerializedName("LE")
        private int level = 9999;
        @Expose
        @SerializedName("CR")
        private int colorRed = 9999;
        @Expose
        @SerializedName("CG")
        private int colorGreen = 9999;
        @Expose
        @SerializedName("CB")
        private int colorBlue = 9999;
        @Expose
        @SerializedName("DT")
        private int duration = 9999;
        @Expose
        @SerializedName("ET")
        private int enableTime = 9999;
        @Expose
        @SerializedName("HS")
        private int sh = 9999;
        @Expose
        @SerializedName("MS")
        private int sm = 9999;
        @Expose
        @SerializedName("HE")
        private int eh = 9999;
        @Expose
        @SerializedName("ME")
        private int em = 9999;
        @Expose
        @SerializedName("WF")
        private int wf = 9999;
        @Expose
        @SerializedName("HU")
        private String h_ckup = "";
        @Expose
        @SerializedName("HL")
        private String h_cklow = "";
        @Expose
        @SerializedName("HA")
        private int h_ckvalid = 9999;
        @Expose
        @SerializedName("TU")
        private String t_ckup = "";
        @Expose
        @SerializedName("TL")
        private String t_cklow = "";
        @Expose
        @SerializedName("TA")
        private int t_ckvalid = 9999;

        public int getST() {
            return ST;
        }

        public void setST(int ST) {
            this.ST = ST;
        }

        public String getSM() {
            return SM;
        }

        public void setSM(String SM) {
            this.SM = SM;
        }

        public int getOF() {
            return OF;
        }

        public void setOF(int OF) {
            this.OF = OF;
        }

        public String getTP() {
            return TP;
        }

        public void setTP(String TP) {
            this.TP = TP;
        }

        public String getHD() {
            return HD;
        }

        public void setHD(String HD) {
            this.HD = HD;
        }

        public String getIA() {
            return IA;
        }

        public void setIA(String IA) {
            this.IA = IA;
        }

        public int getTV() {
            return TV;
        }

        public void setTV(int TV) {
            this.TV = TV;
        }

        public int getP2() {
            return P2;
        }

        public void setP2(int P2) {
            this.P2 = P2;
        }

        public int getP1() {
            return P1;
        }

        public void setP1(int P1) {
            this.P1 = P1;
        }

        public int getAQ() {
            return AQ;
        }

        public void setAQ(int AQ) {
            this.AQ = AQ;
        }

        public int getCC() {
            return CC;
        }

        public void setCC(int CC) {
            this.CC = CC;
        }

        public int getIS() {
            return IS;
        }

        public void setIS(int IS) {
            this.IS = IS;
        }

        public int getSD() {
            return SD;
        }

        public void setSD(int SD) {
            this.SD = SD;
        }

        public String getSN() {
            return SN;
        }

        public void setSN(String SN) {
            this.SN = SN;
        }

        public int getLD() {
            return LD;
        }

        public void setLD(int LD) {
            this.LD = LD;
        }

        public String getLN() {
            return LN;
        }

        public void setLN(String LN) {
            this.LN = LN;
        }

        public int getTD() {
            return TD;
        }

        public void setTD(int TD) {
            this.TD = TD;
        }

        public int getDL() {
            return DL;
        }

        public void setDL(int DL) {
            this.DL = DL;
        }

        public String getUD() {
            return UD;
        }

        public void setUD(String UD) {
            this.UD = UD;
        }

        public String getUN() {
            return UN;
        }

        public void setUN(String UN) {
            this.UN = UN;
        }

        public String getVS() {
            return VS;
        }

        public void setVS(String VS) {
            this.VS = VS;
        }

        public int getEP() {
            return EP;
        }

        public void setEP(int EP) {
            this.EP = EP;
        }
    }
}
