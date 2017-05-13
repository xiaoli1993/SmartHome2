package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/5/6 13:56
 * @Description :
 * @Modify record :
 */

public class UpDataDevice {
    /**
     * current : 当前的固件版本
     * newest : 最新的固件版本
     * description : 升级描述
     */

    private String current;
    private String newest;
    private String description;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNewest() {
        return newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
