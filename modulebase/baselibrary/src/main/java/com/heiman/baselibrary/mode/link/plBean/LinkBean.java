/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link.plBean;

import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.mode.link.plBean.linkBean.CheckListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ConListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ExecBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:17
 * @Description : 
 * @Modify record :
 */

public class LinkBean extends DataSupport{
    /**
     * linkID : 2
     * name : 联动
     * enable : true
     * conList : {"type":0,"deviceid":135113,"condition":2,"value":1,"con_ep":1,"time":{"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}}
     * checkList : [{"type":0,"deviceId":135113,"check":2,"check_ep":2,"value":1,"time":{"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}}]
     * execList : [{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":"","sceneID":1,"delay":122}]
     */
    @Expose
    private int linkID;
    @Expose
    private String name;
    @Expose
    private boolean enable;
    @Expose
    private ConListBean conList;
    @Expose
    private List<CheckListBean> checkList;
    @Expose
    private List<ExecBean> execList;

    public long getId(){
        return getBaseObjId();
    }

    public int getLinkID() {
        return linkID;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ConListBean getConList() {
        return conList;
    }

    public void setConList(ConListBean conList) {
        this.conList = conList;
    }

    public List<CheckListBean> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckListBean> checkList) {
        this.checkList = checkList;
    }

    public List<ExecBean> getExecList() {
        return execList;
    }

    public void setExecList(List<ExecBean> execList) {
        this.execList = execList;
    }
}
