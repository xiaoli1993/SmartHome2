package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

/**
 * Copyright ©深圳市海曼科技有限公司
 */

public class PLBase extends DataSupport {
    /**
     * TEID : 1144507844
     * PL : {}
     * RC : 1
     * SN : 8
     * CID : 30022
     * SID :
     */
    @Expose
    private String TEID;
    @Expose(serialize = true, deserialize = false)
    private int RC;
    @Expose
    private int SN;
    @Expose
    private int CID;
    @Expose
    private String SID;
    @Expose
    private int ENCRYPT;

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

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }


}
