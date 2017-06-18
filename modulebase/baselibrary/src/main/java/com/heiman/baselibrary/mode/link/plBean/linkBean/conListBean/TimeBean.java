/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean.linkBean.conListBean;

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:19
 * @Description : 
 * @Modify record :
 */

public class TimeBean extends DataSupport{
    /**
     * month : 0
     * day : 0
     * hour : 14
     * min : 40
     * type : 1
     * wkflag : 255
     */
    @Expose
    private int month;
    @Expose
    private int day;
    @Expose
    private int hour;
    @Expose
    private int min;
    @Expose
    private int type;
    @Expose
    private int wkflag;

    public long getId(){
        return getBaseObjId();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWkflag() {
        return wkflag;
    }

    public void setWkflag(int wkflag) {
        this.wkflag = wkflag;
    }
}
