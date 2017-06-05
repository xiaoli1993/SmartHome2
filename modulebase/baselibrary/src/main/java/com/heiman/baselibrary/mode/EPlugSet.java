package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 11:14
 * @Description :
 * @Modify record :
 */

public class EPlugSet {
    /**
     * relayonoff : 1
     * enableTime : 1
     * sh : 0
     * sm : 0
     * eh : 0
     * em : 0
     * wf : 255
     */
    @Expose
    private int relayonoff = 9999;
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

    public int getRelayonoff() {
        return relayonoff;
    }

    public void setRelayonoff(int relayonoff) {
        this.relayonoff = relayonoff;
    }
}
