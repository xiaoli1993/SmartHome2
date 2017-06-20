package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/3/31 8:32
 * @Description :
 * @Modify record :
 */

public class EventIsOnline {
    /**
     * device_id : 452878031
     * type : online
     * operator : 0
     */

    private int device_id;
    private String type;
    private int operator;

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
