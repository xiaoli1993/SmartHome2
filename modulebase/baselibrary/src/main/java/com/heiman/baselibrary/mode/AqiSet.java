package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 15:08
 * @Description :
 * @Modify record :
 */

public class AqiSet {
    /**
     * t_ckup : 10.00
     * t_cklow : 9.00
     * t_ckvalid  : 1
     * h_ckup : 19.00
     * h_cklow : 1.00
     * h_ckvalid  : 1
     */
    @Expose
    @SerializedName("TU")
    private String t_ckup = "";
    @Expose
    @SerializedName("TL")
    private String t_cklow = "";
    @Expose
    @SerializedName("TA")
    private int t_ckvalid = 9999;
    @Expose
    @SerializedName("HU")
    private String h_ckup = "";
    @Expose
    @SerializedName("HL")
    private String h_cklow = "";
    @Expose
    @SerializedName("HA")
    private int h_ckvalid = 9999;

    public String getT_ckup() {
        return t_ckup;
    }

    public void setT_ckup(String t_ckup) {
        this.t_ckup = t_ckup;
    }

    public String getT_cklow() {
        return t_cklow;
    }

    public void setT_cklow(String t_cklow) {
        this.t_cklow = t_cklow;
    }

    public int getT_ckvalid() {
        return t_ckvalid;
    }

    public void setT_ckvalid(int t_ckvalid) {
        this.t_ckvalid = t_ckvalid;
    }

    public String getH_ckup() {
        return h_ckup;
    }

    public void setH_ckup(String h_ckup) {
        this.h_ckup = h_ckup;
    }

    public String getH_cklow() {
        return h_cklow;
    }

    public void setH_cklow(String h_cklow) {
        this.h_cklow = h_cklow;
    }

    public int getH_ckvalid() {
        return h_ckvalid;
    }

    public void setH_ckvalid(int h_ckvalid) {
        this.h_ckvalid = h_ckvalid;
    }
}
