package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 11:10
 * @Description :
 * @Modify record :
 */

public class PlugSet {
    /**
     * relayonoff  : 1
     * enableTime_r : 1
     * sh_r : 0
     * sm_r : 0
     * eh_r : 0
     * em_r : 0
     * wf_r : 255
     * usbonoff  : 1
     * enableTime_u : 1
     * sh_u : 0
     * sm_u : 0
     * eh_u : 0
     * em_u : 0
     * wf_u : 255
     */
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

    public int getRelayonoff() {
        return relayonoff;
    }

    public void setRelayonoff(int relayonoff) {
        this.relayonoff = relayonoff;
    }

    public int getEnableTime_r() {
        return enableTime_r;
    }

    public void setEnableTime_r(int enableTime_r) {
        this.enableTime_r = enableTime_r;
    }

    public int getSh_r() {
        return sh_r;
    }

    public void setSh_r(int sh_r) {
        this.sh_r = sh_r;
    }

    public int getSm_r() {
        return sm_r;
    }

    public void setSm_r(int sm_r) {
        this.sm_r = sm_r;
    }

    public int getEh_r() {
        return eh_r;
    }

    public void setEh_r(int eh_r) {
        this.eh_r = eh_r;
    }

    public int getEm_r() {
        return em_r;
    }

    public void setEm_r(int em_r) {
        this.em_r = em_r;
    }

    public int getWf_r() {
        return wf_r;
    }

    public void setWf_r(int wf_r) {
        this.wf_r = wf_r;
    }

    public int getUsbonoff() {
        return usbonoff;
    }

    public void setUsbonoff(int usbonoff) {
        this.usbonoff = usbonoff;
    }

    public int getEnableTime_u() {
        return enableTime_u;
    }

    public void setEnableTime_u(int enableTime_u) {
        this.enableTime_u = enableTime_u;
    }

    public int getSh_u() {
        return sh_u;
    }

    public void setSh_u(int sh_u) {
        this.sh_u = sh_u;
    }

    public int getSm_u() {
        return sm_u;
    }

    public void setSm_u(int sm_u) {
        this.sm_u = sm_u;
    }

    public int getEh_u() {
        return eh_u;
    }

    public void setEh_u(int eh_u) {
        this.eh_u = eh_u;
    }

    public int getEm_u() {
        return em_u;
    }

    public void setEm_u(int em_u) {
        this.em_u = em_u;
    }

    public int getWf_u() {
        return wf_u;
    }

    public void setWf_u(int wf_u) {
        this.wf_u = wf_u;
    }
}
