package com.heiman.baselibrary.mode;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.mode.scene.PLBean;
import com.heiman.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;


/**
 * @Author : 肖力
 * @Time :  2017/5/6 14:05
 * @Description :
 * @Modify record :
 */

public class Scene extends PLBase {

    /**
     * PL : {"2.1.1.2.0":{"sceneID":2,"name":"场景","countTime":54240,"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":""}]}}
     */
    @Expose
    private PLBean PL;

    private String gatewayMac;

    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }

    public long getId(){
        return getBaseObjId();
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
