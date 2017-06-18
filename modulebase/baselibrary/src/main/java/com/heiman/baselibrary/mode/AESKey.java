package com.heiman.baselibrary.mode;

import com.google.gson.annotations.SerializedName;
import com.heiman.baselibrary.Json.HeimanCom;

/**
 * @Author : 肖力
 * @Time :  2017/5/19 13:41
 * @Description :
 * @Modify record :
 */

public class AESKey {


    /**
     * SID : uid
     * PL : {"2.1.1.1.1":{"key":"^LHiuQl+zE0Am/7BG9bNdVDK53HOdSEfhl/Gw1htJkD0=&"}}
     * SN : 48
     * ENCRYPT : 0
     * TEID : 452873318
     * RC : 1
     * CID : 30022
     */

    private String SID;
    private PLBean PL;
    private int SN;
    private int ENCRYPT;
    private String TEID;
    private int RC;
    private int CID;

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getENCRYPT() {
        return ENCRYPT;
    }

    public void setENCRYPT(int ENCRYPT) {
        this.ENCRYPT = ENCRYPT;
    }

    public String getTEID() {
        return TEID;
    }

    public void setTEID(String TEID) {
        this.TEID = TEID;
    }

    public int getRC() {
        return RC;
    }

    public void setRC(int RC) {
        this.RC = RC;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public static class PLBean {
        /**
         * 2.1.1.1.1 : {"key":"^LHiuQl+zE0Am/7BG9bNdVDK53HOdSEfhl/Gw1htJkD0=&"}
         */

        @SerializedName(HeimanCom.COM_GW_OID.GET_AES_KEY)
        private AESkeyBean AESkey;

        public AESkeyBean getAESkey() {
            return AESkey;
        }

        public void setAESkey(AESkeyBean AESkey) {
            this.AESkey = AESkey;
        }

        public static class AESkeyBean {
            /**
             * key : ^LHiuQl+zE0Am/7BG9bNdVDK53HOdSEfhl/Gw1htJkD0=&
             */

            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }
}
