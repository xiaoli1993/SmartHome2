package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 11:03
 * @Description :
 * @Modify record :
 */

public class SensorSet {


    /**
     * enableTime : 1
     * sh : 0
     * sm : 0
     * eh : 0
     * em : 0
     * wf : 255
     */
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

    public int getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(int enableTime) {
        this.enableTime = enableTime;
    }

    public int getSh() {
        return sh;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }

    public int getSm() {
        return sm;
    }

    public void setSm(int sm) {
        this.sm = sm;
    }

    public int getEh() {
        return eh;
    }

    public void setEh(int eh) {
        this.eh = eh;
    }

    public int getEm() {
        return em;
    }

    public void setEm(int em) {
        this.em = em;
    }

    public int getWf() {
        return wf;
    }

    public void setWf(int wf) {
        this.wf = wf;
    }
}
