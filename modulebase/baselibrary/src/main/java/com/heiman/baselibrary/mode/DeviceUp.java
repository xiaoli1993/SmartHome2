package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/6/20 14:30
 * @Description :
 * @Modify record :
 */

public class DeviceUp {

    /**
     * current : 2
     * newest : 2
     * description : {     "US": "你好",     "CN": "测试" }
     */

    private int current;
    private int newest;
    private String description;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getNewest() {
        return newest;
    }

    public void setNewest(int newest) {
        this.newest = newest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
