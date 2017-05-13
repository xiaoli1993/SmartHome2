package com.heiman.baselibrary.mode;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:37
 * @Description :
 * @Modify record :
 */

public class HeimanGet {
    /**
     * CID : 30021
     * ENCRYPT : 1
     * PL : {"OID":["2.1.1.255.0.2"]}
     * SID : 12345
     * SN : 2
     * TEID : 100018257
     */

    private int CID;
    private int ENCRYPT;
    private PLBean PL;
    private String SID;
    private int SN;
    private String TEID;

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

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public String getTEID() {
        return TEID;
    }

    public void setTEID(String TEID) {
        this.TEID = TEID;
    }

    public static class PLBean {
        private List<String> OID;

        public List<String> getOID() {
            return OID;
        }

        public void setOID(List<String> OID) {
            this.OID = OID;
        }
    }
}
