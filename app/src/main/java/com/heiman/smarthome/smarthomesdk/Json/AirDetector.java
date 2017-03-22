package com.heiman.smarthome.smarthomesdk.Json;/**
 * Created by hp on 2016/9/21.
 */


import com.heiman.smarthome.smarthomesdk.Constants;
import com.heiman.smarthome.smarthomesdk.http.HttpManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 11:48
 * @Description :   空氣檢測儀JOSON
 */
public class AirDetector {

    private static String TEIDS = HttpManage.getInstance().getUserid() + "";


    public static class AIR_OID {
        public static final int MESSAGE_THRESHOLD_TVOC = 21;                         //TVOC报警阀值
        public static final int MESSAGE_THRESHOLD_PM25 = 22;                         //PM2.5报警阀值
        public static final int MESSAGE_THRESHOLD_CHCO = 23;                         //甲醛报警阀值
        public static final int MESSAGE_THRESHOLD_TEMP = 24;                         //温度报警阀值
        public static final int MESSAGE_THRESHOLD_HUMP = 25;                         //湿度报警阀值
        public static final int MESSAGE_DATA = 240;                                  //获取AIR各种数据
        public static final int MESSAGE_BETTIMER = 245;                              //报警时间
        public static final int MESSAGE_BEEPSOUNDONOFF = 246;                        //声音开关
        public static final int MESSAGE_LANGUAGE = 247;                              //语言设置
        public static final int MESSAGE_LIGHTONOFF = 248;                            //背景开关 || 获取设置信息
        public static final int MESSAGE_LIGHTCOCLOUR = 249;                          //背景颜色
        public static final int MESSAGE_TEMPUNIT = 250;                              //温度符号
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
     * 获取空气质量名字以及基本信息
     *
     * @return
     */
    public static String GetDeviceMessage(String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.7.253");
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
     * 获取空气数据信息
     *
     * @param OID
     * @return
     */
    public static String GetAirMessage(String Nickname, int OID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.7." + OID);
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
     * 获取温度报警
     *
     * @return
     */
    public static String GetTHPAlarm(String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.7." + AIR_OID.MESSAGE_THRESHOLD_TEMP);
            tada.put("2.1.1.7." + AIR_OID.MESSAGE_THRESHOLD_HUMP);
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
     * 设置设备名称
     *
     * @param devicename 设备名字
     * @return
     */
    public static String SetAirName(String Nickname, String devicename) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.7.253", devicename);
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
     * @param OID     AIR_OID
     * @param MESSAGE 数值
     * @return
     */
    public static String SetAirMessage(String Nickname, int OID, int MESSAGE) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.7." + OID, MESSAGE);
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
     * 语言设置
     *
     * @param language 语言 CN表示中文显示，US表英文显示
     * @return
     */
    public static String SetAirLanguage(String Nickname, String language) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.7.247", language);
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
     * @param alarm TVOC报警设置 0为不报警 1为轻度污染才报警 2 为重度污染才报警
     * @return
     */
    public static String SetAirTOVC(String Nickname, int alarm) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject tovc = new JSONObject();
            tovc.put("tv_alarm", alarm);
            data.put("2.1.1.7.21", tovc);
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
     * @param ckup    PM2.5报警阀值 取值范围0-999 注：单位 ug/m3  默认值75 ug/m3  （GB3095-2012中国家二级标准）
     * @param ckvalid 使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @return
     */
    public static String SetAirPM25(String Nickname, int ckup, int ckvalid) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject pm25 = new JSONObject();
            pm25.put("p_ckup", ckup);
            pm25.put("p_ckvalid", ckvalid);
            data.put("2.1.1.7.22", pm25);
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
     * @param ckup    甲醛报警上限值，单位mg/m3，注：该值被放大100倍（R/W）默认值为10（GB/T18883-2002标准）
     * @param ckvalid 使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @return
     */
    public static String SetAirCHCO(String Nickname, int ckup, int ckvalid) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject chco = new JSONObject();
            chco.put("c_ckup", ckup);
            chco.put("c_ckvalid", ckvalid);
            data.put("2.1.1.7.23", chco);
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
    public static String SetAirTEMP(String Nickname, int ckup, int cklow, int t_ckvalid_up, int t_ckvalid_low) {
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
            data.put("2.1.1.7.24", temp);
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
     * @param ckup          湿度报警上限值，单位%，注：该值被放大100倍（R/W）
     * @param cklow         湿度报警下限值，单位%，注：该值被放大100倍（R/W）
     * @param h_ckvalid_up  上限使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @param h_ckvalid_low 下限使能标志，1使能报警阀值，0禁能报警阀值（R/W）
     * @return
     */
    public static String SetAirHUMP(String Nickname, int ckup, int cklow, int h_ckvalid_up, int h_ckvalid_low) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject hump = new JSONObject();
            hump.put("h_ckup", ckup);
            hump.put("h_cklow", cklow);
            hump.put("h_ckvalid_up", h_ckvalid_up);
            hump.put("h_ckvalid_low", h_ckvalid_low);
            data.put("2.1.1.7.25", hump);
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
