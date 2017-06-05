package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/6/5 15:06
 * @Description :
 * @Modify record :
 */

public class RelaySoundSet {
    /**
     * onoff : 1
     * duration : 60
     */
    @Expose
    private int onoff = 9999;
    @Expose
    private int duration = 9999;

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
