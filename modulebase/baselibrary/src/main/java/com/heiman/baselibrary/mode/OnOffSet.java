package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 11:15
 * @Description :
 * @Modify record :
 */

public class OnOffSet {

    /**
     * onoff1 : 0
     * onoff2 : 0
     * onoff3 : 0
     * enableTime1 : 1
     * sh1 : 0
     * sm1 : 0
     * eh1 : 0
     * em1 : 0
     * wf1 : 255
     * enableTime2 : 1
     * sh2 : 0
     * sm2 : 0
     * eh2 : 0
     * em2 : 0
     * wf2 : 255
     * enableTime3 : 1
     * sh3 : 0
     * sm3 : 0
     * eh3 : 0
     * em3 : 0
     * wf3 : 255
     */
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
    @SerializedName("HE2")
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
    @SerializedName("ME2")
    private int em3;
    @Expose
    @SerializedName("WF3")
    private int wf3;

    public int getEnableTime1() {
        return enableTime1;
    }

    public void setEnableTime1(int enableTime1) {
        this.enableTime1 = enableTime1;
    }

    public int getSh1() {
        return sh1;
    }

    public void setSh1(int sh1) {
        this.sh1 = sh1;
    }

    public int getSm1() {
        return sm1;
    }

    public void setSm1(int sm1) {
        this.sm1 = sm1;
    }

    public int getEh1() {
        return eh1;
    }

    public void setEh1(int eh1) {
        this.eh1 = eh1;
    }

    public int getEm1() {
        return em1;
    }

    public void setEm1(int em1) {
        this.em1 = em1;
    }

    public int getWf1() {
        return wf1;
    }

    public void setWf1(int wf1) {
        this.wf1 = wf1;
    }

    public int getEnableTime2() {
        return enableTime2;
    }

    public void setEnableTime2(int enableTime2) {
        this.enableTime2 = enableTime2;
    }

    public int getSh2() {
        return sh2;
    }

    public void setSh2(int sh2) {
        this.sh2 = sh2;
    }

    public int getSm2() {
        return sm2;
    }

    public void setSm2(int sm2) {
        this.sm2 = sm2;
    }

    public int getEh2() {
        return eh2;
    }

    public void setEh2(int eh2) {
        this.eh2 = eh2;
    }

    public int getEm2() {
        return em2;
    }

    public void setEm2(int em2) {
        this.em2 = em2;
    }

    public int getWf2() {
        return wf2;
    }

    public void setWf2(int wf2) {
        this.wf2 = wf2;
    }

    public int getEnableTime3() {
        return enableTime3;
    }

    public void setEnableTime3(int enableTime3) {
        this.enableTime3 = enableTime3;
    }

    public int getSh3() {
        return sh3;
    }

    public void setSh3(int sh3) {
        this.sh3 = sh3;
    }

    public int getSm3() {
        return sm3;
    }

    public void setSm3(int sm3) {
        this.sm3 = sm3;
    }

    public int getEh3() {
        return eh3;
    }

    public void setEh3(int eh3) {
        this.eh3 = eh3;
    }

    public int getEm3() {
        return em3;
    }

    public void setEm3(int em3) {
        this.em3 = em3;
    }

    public int getWf3() {
        return wf3;
    }

    public void setWf3(int wf3) {
        this.wf3 = wf3;
    }

    public int getOnoff1() {
        return onoff1;
    }

    public void setOnoff1(int onoff1) {
        this.onoff1 = onoff1;
    }

    public int getOnoff2() {
        return onoff2;
    }

    public void setOnoff2(int onoff2) {
        this.onoff2 = onoff2;
    }

    public int getOnoff3() {
        return onoff3;
    }

    public void setOnoff3(int onoff3) {
        this.onoff3 = onoff3;
    }
}
