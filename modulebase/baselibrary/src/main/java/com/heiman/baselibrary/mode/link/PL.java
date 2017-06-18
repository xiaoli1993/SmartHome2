/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.mode.link;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.heiman.baselibrary.mode.link.plBean.LinkBean;

import org.litepal.crud.DataSupport;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/12 9:16
 * @Description :
 * @Modify record :
 */

public class PL extends DataSupport{
    /**
     * 2.1.1.3.0 : {"linkID":2,"name":"联动","enable":true,"conList":{"type":0,"deviceid":135113,"condition":2,"value":1,"con_ep":1,"time":{"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}},"checkList":[{"type":0,"deviceId":135113,"check":2,"check_ep":2,"value":1,"time":{"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}}],"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":"","sceneID":1,"delay":122}]}
     */
    @Expose
    @SerializedName("2.1.1.3.0")
    private LinkBean OID;

    public long getId(){
        return getBaseObjId();
    }

    public LinkBean getOID() {
        return OID;
    }

    public void setOID(LinkBean OID) {
        this.OID = OID;
    }
}
