package com.heiman.baselibrary.mode;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heiman.baselibrary.mode.link.PL;
import com.heiman.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;


/**
 * @Author : 肖力
 * @Time :  2017/5/6 13:59
 * @Description :
 * @Modify record :
 */

public class Link extends PLBase {

    /**
     * PL : {"2.1.1.3.0":{"linkID":2,"name":"联动","enable":true,"conList":{"type":0,"deviceid":135113,"condition":2,"value":1,"con_ep":1,"time":{"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}},"checkList":[{"type":0,"deviceId":135113,"check":2,"check_ep":2,"value":1,"time":{"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}}],"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":"","sceneID":1,"delay":122}]}}
     */

    private PL PL;

    private String gatewayMac;

    public long getId(){
        return getBaseObjId();
    }

    public PL getPL() {
        return PL;
    }

    public void setPL(PL PL) {
        this.PL = PL;
    }

    public String getGatewayMac() {
        return gatewayMac;
    }

    public void setGatewayMac(String gatewayMac) {
        this.gatewayMac = gatewayMac;
    }


    /**
     * 获取jsonString过滤掉DataSupport中的属性
     * @return
     */
    public String getJsonString() {
        String jsonStr = "";
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {

                if (f.getName().equals("gatewayMac")) {
                    return true;
                }

                Field[] shouldSkipFields = DataSupport.class.getDeclaredFields();

                for (int i = 0; i < shouldSkipFields.length; i++) {

                    if (f.getName().equals(shouldSkipFields[i].getName())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        try {
            jsonStr = gson.toJson(this);
        } catch (Exception e) {
            if (e != null) {
                LogUtil.e(e.getMessage());
            }
        }

        return jsonStr;
    }

}
