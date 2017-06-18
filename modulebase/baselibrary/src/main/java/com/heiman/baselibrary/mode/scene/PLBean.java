package com.heiman.baselibrary.mode.scene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;

import org.litepal.crud.DataSupport;


/**
 * @Author : 张泽晋
 * @Time : 2017/6/6 21:44
 * @Description :
 * @Modify record :
 */

public class PLBean extends DataSupport{
    /**
     * 2.1.1.2.0 : {"sceneID":2,"name":"场景","countTime":54240,"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":""}]}
     */
    @Expose
    @SerializedName("2.1.1.2.0")
    private SceneBean OID;

    public SceneBean getOID() {
        return OID;
    }

    public void setOID(SceneBean OID) {
        this.OID = OID;
    }

    public long getId(){
        return getBaseObjId();
    }
}
