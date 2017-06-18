package com.heiman.baselibrary.mode.scene.pLBean;

import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.mode.scene.pLBean.sceneBean.ExecListBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/6 21:46
 * @Description :
 * @Modify record :
 */

public class SceneBean extends DataSupport{
    /**
     * sceneID : 2
     * name : 场景
     * countTime : 54240
     * execList : [{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":""}]
     */
    @Expose
    private int sceneID;
    @Expose
    private String name;
    @Expose
    private int countTime;
    @Expose
    private List<ExecListBean> execList;

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public List<ExecListBean> getExecList() {
        return execList;
    }

    public void setExecList(List<ExecListBean> execList) {
        this.execList = execList;
    }

    public long getId(){
        return getBaseObjId();
    }
}
