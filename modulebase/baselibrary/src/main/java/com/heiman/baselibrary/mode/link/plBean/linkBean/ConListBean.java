/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean.linkBean;

import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.mode.link.plBean.linkBean.conListBean.TimeBean;

import org.litepal.crud.DataSupport;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:18
 * @Description : 
 * @Modify record :
 */

public class ConListBean extends DataSupport{
    /**
     * type : 0
     * deviceid : 135113
     * condition : 2
     * value : 1
     * con_ep : 1
     * time : {"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}
     */
    @Expose
    private int type;
    @Expose
    private int deviceid;
    @Expose
    private int condition;
    @Expose
    private int value;
    @Expose
    private int con_ep;
    @Expose
    private TimeBean time;

    public long getId(){
        return getBaseObjId();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCon_ep() {
        return con_ep;
    }

    public void setCon_ep(int con_ep) {
        this.con_ep = con_ep;
    }

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }
}
