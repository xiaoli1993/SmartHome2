package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("OF")
    private int onoff = 9999;
    @Expose
    @SerializedName("DT")
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
