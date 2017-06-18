package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 15:09
 * @Description :
 * @Modify record :
 */

public class ThermostatSet {
    /**
     * onoff : 0
     * t_ckup : 10.00
     * t_cklow : 9.00
     * t_ckvalid  : 1
     */
    @Expose
    @SerializedName("OF")
    private int onoff = 9999;
    @Expose
    @SerializedName("TU")
    private String t_ckup = "";
    @Expose
    @SerializedName("TL")
    private String t_cklow = "";
    @Expose
    @SerializedName("TA")
    private int t_ckvalid = 9999;

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

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
}
