/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean.linkBean.checkListBean;

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:21
 * @Description : 
 * @Modify record :
 */

public class TimeBeanX extends DataSupport{
    /**
     * s_hour : 0
     * s_min : 0
     * e_hour : 14
     * e_min : 40
     * wkflag : 255
     */
    @Expose
    private int s_hour;
    @Expose
    private int s_min;
    @Expose
    private int e_hour;
    @Expose
    private int e_min;
    @Expose
    private int wkflag;

    public long getId(){
        return getBaseObjId();
    }

    public int getS_hour() {
        return s_hour;
    }

    public void setS_hour(int s_hour) {
        this.s_hour = s_hour;
    }

    public int getS_min() {
        return s_min;
    }

    public void setS_min(int s_min) {
        this.s_min = s_min;
    }

    public int getE_hour() {
        return e_hour;
    }

    public void setE_hour(int e_hour) {
        this.e_hour = e_hour;
    }

    public int getE_min() {
        return e_min;
    }

    public void setE_min(int e_min) {
        this.e_min = e_min;
    }

    public int getWkflag() {
        return wkflag;
    }

    public void setWkflag(int wkflag) {
        this.wkflag = wkflag;
    }
}
