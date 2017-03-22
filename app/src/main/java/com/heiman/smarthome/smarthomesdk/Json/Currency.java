package com.heiman.smarthome.smarthomesdk.Json;/**
 * Copyright ©深圳市海曼科技有限公司
 */


import com.heiman.smarthome.smarthomesdk.Constants;
import com.heiman.smarthome.smarthomesdk.http.HttpManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/23 14:25
 * @Description :
 */
public class Currency {
    private static String TEIDS = HttpManage.getInstance().getUserid() + "";

    /**
     * 设置时区
     *
     * @param Nickname
     * @param GMT      时区
     * @return
     */
    public static String SetGMT(String Nickname, String GMT) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.2.1.7", GMT);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取信息
     *
     * @param Nickname
     * @return
     */
    public static String Getbasiclnfo(String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.2.1");
            data.put("OID", tada);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 設置PL值
     *
     * @param Nickname 用户名称
     * @param data     PL中值
     * @return
     */
    public static String SetPL(String Nickname, JSONObject data) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取OID
     *
     * @param Nickname 用户名
     * @param OID      需要获取的OID值
     * @return
     */
    public static String GetCountdown(String Nickname, JSONArray OID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("OID", OID);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 升级设备
     *
     * @param Nickname
     * @param isEn     是否升级
     * @return
     */
    public static String Update(String Nickname, int isEn) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.4.6", isEn);
            // data.put("2.1.1.4", "name");
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
