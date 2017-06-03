package com.heiman.baselibrary.mode;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/5/19 13:41
 * @Description :
 * @Modify record :
 */

public class AESKey {
    /**
     * CID : 30012
     * ENCRYPT : 0
     * PL : {"2.1.1.6":"AES128"}
     * RC : 1
     * SID : 12345
     * TEID : 100012461
     * SN : 2
     */

    private int CID;
    private int ENCRYPT;
    private PLBean PL;
    private int RC;
    private String SID;
    private String TEID;
    private int SN;

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getENCRYPT() {
        return ENCRYPT;
    }

    public void setENCRYPT(int ENCRYPT) {
        this.ENCRYPT = ENCRYPT;
    }

    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }

    public int getRC() {
        return RC;
    }

    public void setRC(int RC) {
        this.RC = RC;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getTEID() {
        return TEID;
    }

    public void setTEID(String TEID) {
        this.TEID = TEID;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public static class PLBean {
        /**
         * 2.1.1.6 : AES128
         */

        @SerializedName("2.1.1.1.8")
        private String AesKey;

        public String getAesKey() {
            return AesKey;
        }

        public void setAesKey(String AesKey) {
            this.AesKey = AesKey;
        }
    }
}
