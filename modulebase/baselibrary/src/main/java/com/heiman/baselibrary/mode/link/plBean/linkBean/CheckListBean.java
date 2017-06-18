/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean.linkBean;

import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.mode.link.plBean.linkBean.checkListBean.TimeBeanX;

import org.litepal.crud.DataSupport;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:20
 * @Description : 
 * @Modify record :
 */

public class CheckListBean extends DataSupport{
    /**
     * type : 0
     * deviceId : 135113
     * check : 2
     * check_ep : 2
     * value : 1
     * time : {"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}
     */
    @Expose
    private int type;
    @Expose
    private int deviceId;
    @Expose
    private int check;
    @Expose
    private int check_ep;
    @Expose
    private int value;
    @Expose
    private TimeBeanX time;

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

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getCheck_ep() {
        return check_ep;
    }

    public void setCheck_ep(int check_ep) {
        this.check_ep = check_ep;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TimeBeanX getTime() {
        return time;
    }

    public void setTime(TimeBeanX time) {
        this.time = time;
    }
}
