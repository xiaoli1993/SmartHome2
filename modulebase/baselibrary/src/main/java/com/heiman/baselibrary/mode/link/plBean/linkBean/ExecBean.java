/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean.linkBean;

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:22
 * @Description : 
 * @Modify record :
 */

public class ExecBean extends DataSupport{
    /**
     * type : 0
     * deviceId : 135113
     * exec_ep : 1
     * value : 1
     * value1 : 1
     * value2 : 1
     * value3 :
     * sceneID : 1
     * delay : 122
     */
    @Expose
    private int type;
    @Expose
    private int deviceId;
    @Expose
    private int exec_ep;
    @Expose
    private int value;
    @Expose
    private int value1;
    @Expose
    private int value2;
    @Expose
    private String value3;
    @Expose
    private int sceneID;
    @Expose
    private int delay;

    public long getId(){
        return getBaseObjId();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getExec_ep() {
        return exec_ep;
    }

    public void setExec_ep(int exec_ep) {
        this.exec_ep = exec_ep;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
