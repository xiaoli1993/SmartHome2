package com.heiman.smarthome.smarthomesdk.Json;/**
 * Created by hp on 2016/11/7.
 */


import com.heiman.smarthome.smarthomesdk.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 10:30
 * @Description :   红外JOSON
 */
public class Remote {
//    private static String TEIDS = MyApplication.getMyApplication().getAppid() + "";

    /**
     * 获取OID
     *
     * @param Nickname 用户名
     * @param OID      需要获取的OID值
     * @return
     */
    public static String GetCountdown(String TEIDS,String Nickname, JSONArray OID) {
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
     * 设置时区
     *
     * @param Nickname
     * @param GMT      时区
     * @return
     */
    public static String SetGMT(String TEIDS,String Nickname, String GMT) {
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
    public static String Getbasiclnfo(String TEIDS,String Nickname) {
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
     * 获取红外转发器名字以及基本信息
     *
     * @return
     */
    public static String GetDeviceMessage(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.8.253");
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
     * 获取温度数据信息
     *
     * @return
     */
    public static String GetTemp(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.8.240");
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
     * 获取温度设置
     *
     * @return
     */
    public static String GetRCTempAlarm(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.8.24");
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
     * 报警阀值设置
     *
     * @param ckup          温度报警上限值，单位℃，注：该值被放大100倍（R/W）
     * @param cklow         温度报警下限值，单位℃，注：该值被放大100倍（R/W）
     * @param t_ckvalid_up  上限使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @param t_ckvalid_low 下限使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @return
     */
    public static String SetRCTempAlarm(String TEIDS,String Nickname, int ckup, int cklow, int t_ckvalid_up, int t_ckvalid_low) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject temp = new JSONObject();
            temp.put("t_ckup", ckup);
            temp.put("t_cklow", cklow);
            temp.put("t_ckvalid_up", t_ckvalid_up);
            temp.put("t_ckvalid_low", t_ckvalid_low);
            data.put("2.1.1.8.24", temp);
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
     * 红外学习功能
     *
     * @param objectId 数据ID
     * @return
     */
    public static String SetStudy(String TEIDS,String Nickname, String objectId) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.8.254", 1);
            data.put("objectId", objectId);
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
     * 设置设备名称
     *
     * @param devicename 设备名字
     * @return
     */
    public static String SetRenoteName(String TEIDS,String Nickname, String devicename) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.8.253", devicename);
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
     * @param code     码库
     * @param ispower  是否是开关
     * @param zip      压缩方式
     * @param objectId 数据ID
     * @return
     */
    public static String SetCode(String TEIDS,String Nickname, String code, int ispower, int zip, String objectId) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject rccontrol = new JSONObject();
            rccontrol.put("code", code);
            rccontrol.put("ispower", ispower);
            rccontrol.put("zip", zip);
            rccontrol.put("objectId", objectId);
            data.put("2.1.1.8.4.20", rccontrol);
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
