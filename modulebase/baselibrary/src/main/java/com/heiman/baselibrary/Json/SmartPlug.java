package com.heiman.baselibrary.Json;

import com.google.gson.Gson;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.TimerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 08:46
 * @Description :   插座JOSON
 */
public class SmartPlug {
//    private static String TEIDS = MyApplication.getMyApplication().getAppid() + "";

    public static int mgetSN() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }


    /**
     * 智能插座开关
     *
     * @param Nickname 用户名称
     * @param isopen   是否开启
     * @return
     */
    public static String SetPowerSwitch(String TEIDS,String Nickname, int isopen) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.4.1", isopen);
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

    /**
     * 插座倒计时
     *
     * @param Nickname 用户名
     * @param leftTime 时间
     * @param onoff    开关
     * @return
     */
    public static String SetPowerCountDown(String TEIDS,String Nickname, int leftTime, boolean onoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject PL = new JSONObject();
            datat.put("leftTime", leftTime);
            PL.put("onoff", onoff);
            datat.put("PL", PL);
            data.put("2.1.1.2.3", datat);
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
     * 停止删除倒计时
     *
     * @param Nickname 用户名
     * @return
     */
    public static String RemoveCountDown(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            datat.put("remove", true);
            data.put("2.1.1.2.3", datat);
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
     * 设置USB倒计时
     *
     * @param Nickname 用户名
     * @param leftTime 时长
     * @param usbonoff 开关
     * @return
     */
    public static String SetUSBCountDown(String TEIDS,String Nickname, int leftTime, boolean usbonoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject PL = new JSONObject();
            datat.put("leftTime", leftTime);
            PL.put("usbonoff", usbonoff);
            datat.put("PL", PL);
            data.put("2.1.1.2.3", datat);
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
     * 设置定时器使能
     *
     * @param Nickname 用户名
     * @param timerID        第几个定时器
     * @param isEnable 是否使能
     * @return
     */
    public static String SetTimerEnable(String TEIDS,String Nickname, int timerID, boolean isEnable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.2.2.3." + timerID, isEnable);
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
     * 获取倒计时
     *
     * @param Nickname 用户名
     * @return
     */
    public static String GetCountdown(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.2.3");
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
     * 获取定时器
     *
     * @param Nickname 用户名
     * @return
     */
    public static String GetTimer(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.2.2");
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
     * 设置定时器功能
     * @param Nickname 昵称设置
     * @param timerId   定时器ID
     * @param timerBean 定时器数据
     * @return
     */
    public static String SetTimer(String TEIDS,String Nickname, int timerId, TimerBean timerBean) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            Gson gson  = new Gson();
            String jsonObject = gson.toJson(timerBean);
            JSONObject datas = new JSONObject(jsonObject);
            data.put("2.1.1.2.2.0." + timerId, datas);
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
     * 删除定时器
     *
     * @param Nickname 用户名
     * @param timerID        第几个定时器
     * @return
     */
    public static String RemoveTimer(String TEIDS,String Nickname, int timerID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.2.2.255." + timerID, true);
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
     * 获取插座用户信息 包括名字 开关、USB开关、以及其他用户信息
     *
     * @param Nickname
     * @return
     */
    public static String GetSmartPlug(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.4.1");
            tada.put("2.1.1.4.5");
            tada.put("2.1.1.4.253");
//            tada.put("2.1.1.2.1");
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
     * 获取插座开关
     *
     * @param Nickname
     * @return
     */
    public static String GetSmartPlugSwitch(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.4.5");//power
            tada.put("2.1.1.4.1");//current
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
     * 获取插座功率
     *
     * @param Nickname
     * @return
     */
    public static String GetSmartPlugPVC(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.4.2");//power
//            tada.put("2.1.1.4.3");//current
//            tada.put("2.1.1.4.4");//voltage
//            tada.put("2.1.1.4.7");//Electricity
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
     * 获取插座电量
     *
     * @param Nickname
     * @return
     */
    public static String GetSmartPlugElectricity(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.4.7");
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
     * 校验
     *
     * @param Nickname
     * @return
     */
    public static String SetCalibration(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.2.4.8", 1);
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
     * 修改插座名称
     *
     * @param Nickname
     * @param devicename 插座名称
     * @return
     */
    public static String ChangeDeviceName(String TEIDS,String Nickname, String devicename) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.4.253", devicename);
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
     * 设置USB开关
     *
     * @param Nickname
     * @param onoff    开关
     * @return
     */
    public static String SetUSBSwitch(String TEIDS,String Nickname, boolean onoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.4.5", onoff);
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
