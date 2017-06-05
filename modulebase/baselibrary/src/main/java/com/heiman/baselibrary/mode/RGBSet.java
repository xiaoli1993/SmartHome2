package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 11:08
 * @Description :
 * @Modify record :
 */

public class RGBSet {
    /**
     * onoff  : 1
     * level  : 100
     * colorRed  : 65535
     * colorGreen : 65535
     * colorBlue : 65535
     * enableTime : 1
     * sh : 0
     * sm : 0
     * eh : 0
     * em : 0
     * wf : 255
     */
    @Expose
    private int onoff = 9999;
    @Expose
    private int level = 9999;
    @Expose
    private int colorRed = 9999;
    @Expose
    private int colorGreen = 9999;
    @Expose
    private int colorBlue = 9999;
    @Expose
    private int enableTime = 9999;
    @Expose
    private int sh = 9999;
    @Expose
    private int sm = 9999;
    @Expose
    private int eh = 9999;
    @Expose
    private int em = 9999;
    @Expose
    private int wf = 9999;

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getColorRed() {
        return colorRed;
    }

    public void setColorRed(int colorRed) {
        this.colorRed = colorRed;
    }

    public int getColorGreen() {
        return colorGreen;
    }

    public void setColorGreen(int colorGreen) {
        this.colorGreen = colorGreen;
    }

    public int getColorBlue() {
        return colorBlue;
    }

    public void setColorBlue(int colorBlue) {
        this.colorBlue = colorBlue;
    }

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
