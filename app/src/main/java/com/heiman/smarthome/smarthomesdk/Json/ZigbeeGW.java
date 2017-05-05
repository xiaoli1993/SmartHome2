package com.heiman.smarthome.smarthomesdk.Json;/**
 * Created by hp on 2016/12/19.
 */

import com.google.gson.Gson;
import com.heiman.smarthome.smarthomesdk.Constants;
import com.heiman.smarthome.smarthomesdk.bean.SceneBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 09:52
 * @Description :   网关通讯JOSON
 */
public class ZigbeeGW {
//    private static String TEIDS = MyApplication.getMyApplication().getAppid() + "";

    public static int ZIGBEEONE = 1;
    public static int ZIGBEETWO = 2;
    public static int ZIGBEETHREE = 3;
    public static int ZIGBEEFOUR = 4;
    public static int ZIGBEEFIVE = 5;
    public static int ZIGBEESIX = 6;
    public static int ZIGBEESEVEN = 7;
    public static int ZIGBEEEIGHT = 8;
    public static int ZIGBENINE = 9;
    public static int ZIGBEEZERO = 0;
    public static int ZIGBEEW = 240;
    public static int ZIGBEE = 248;

    /**
     * 获取网关消息以及名字
     *
     * @param Nickname
     * @return
     */
    public static String GetZigbeeGW(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.253");
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
    public static String GetMessage(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3."+ZIGBEEW);
            tada.put("2.1.1.2."+ZIGBEE);
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
     * 设置网关单独数据报警信息
     *
     * @param Nickname
     * @param Index    索引
     * @param enable   使能
     * @param sh       开始时间
     * @param sm       开始分钟
     * @param eh       结束时间
     * @param em       结束分钟
     * @param wf       周期
     * @return
     */
    public static String SetSubDeviceSetActAlmEn(String TEIDS,String Nickname,
                                                 int Index, boolean enable, int sh, int sm, int eh,
                                                 int em, int wf) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject RW = new JSONObject();
            RW.put("enable", enable);
            RW.put("sh", sh);
            RW.put("sm", sm);
            RW.put("eh", eh);
            RW.put("em", em);
            RW.put("wf", wf);
            data.put("2.1.1.3.4.20." + Index + ".5", RW);
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
     * 修改子设备名称
     *
     * @param Nickname
     * @param Index         索引
     * @param SubDeviceName 子设备名字
     * @return
     */
    public static String ChangeSubDeviceName(String TEIDS,String Nickname,
                                          int Index, String SubDeviceName) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.4.3." + Index, SubDeviceName);
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
     * 删除子设备
     *
     * @param Nickname
     * @param Index    索引
     * @return
     */
    public static String RemoveSensor(String TEIDS,String Nickname,
                                         int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.4.255." + Index, true);
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
     * 获取子设备
     *
     * @param Nickname
     * @return
     */
    public static String GetSubDevice(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4");
            tada.put("2.1.1.3.1");
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
     * 获取网关名称
     *
     * @param Nickname
     * @return
     */

    public static String GetGwName(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.253");
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
     * 加网许可
     *
     * @param Nickname
     * @param isEnable 0表示退出加网 1表示加网
     * @return
     */
    public static String SetAllowAddSubDevice(String TEIDS,String Nickname, boolean isEnable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.254", isEnable);
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
     * 设置最后一条消息
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String SetSubDeviceClear(String TEIDS,String Nickname, int Index, String descript) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject cdata = new JSONObject();
            cdata.put("clear", true);
            cdata.put("descript", descript);
            data.put("2.1.1.3.4.20." + Index + ".4", cdata);
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
     * 获取报警消息
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String GetAlarm(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.20." + Index + ".4");
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
     * 获取报警消息使用
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String GetActAlmEN(String TEIDS,String Nickname,
                                     int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.20." + Index + ".5");
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
     * 修改设备名称
     *
     * @param Nickname
     * @param devicename
     * @return
     */
    public static String ChangeDeviceName(String TEIDS,String Nickname, String devicename) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.253", devicename);
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
     * 获取温湿度
     *
     * @param Nickname
     * @param index
     * @return
     */
    public static String GetTHP(String TEIDS,String Nickname, int index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.22." + index);
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
     * 获取温度
     * @param Nickname
     * @param Index
     * @return
     */
    public static String GetTHPtemp(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.22." + Index + ".2");
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
     * 获取湿度
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String THPhumidity(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.22." + Index + ".3");
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
     * 温湿度压力
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String THPpressure(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.4.22." + Index + ".4");
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
     * 设置RGB亮度
     *
     * @param Nickname
     * @param Index
     * @param level    亮度值
     * @return
     */
    public static String SetRGBlevel(String TEIDS,String Nickname,
                                     int Index, int level) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.9." + Index, level);
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
     * 设置RGB等开关
     *
     * @param Nickname
     * @param Index
     * @param onoff    开关
     * @return
     */
    public static String SetRGBonoff(String TEIDS,String Nickname, int Index, boolean onoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.8." + Index, onoff);
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
     * 设置RGB灯 CT值
     *
     * @param Nickname
     * @param Index
     * @param ct
     * @return
     */
    public static String SetRGBct(String TEIDS,String Nickname, int Index,
                                  int ct) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.10." + Index, ct);
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
     * 设置RGB灯颜色
     *
     * @param Nickname
     * @param Index
     * @param R
     * @param G
     * @param B
     * @return
     */
    public static String SetRGBColor(String TEIDS,String Nickname,int Index, long R, long G, long B) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.12." + Index, G);
            data.put("2.1.1.3.1.13." + Index, B);
            data.put("2.1.1.3.1.11." + Index, R);
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
     * 设置RGB灯颜色开关
     *
     * @param Nickname
     * @param Index
     * @param onoff
     * @return
     */
    public static String SetRGBcoloronoff(String TEIDS,String Nickname,
                                          int Index, boolean onoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.14." + Index, onoff);
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
     * 删除RGB或者plug
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String RemoveRGBorPlug(String TEIDS,String Nickname,
                                  int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.255." + Index, true);
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
     * 设置延迟开关
     *
     * @param Nickname
     * @param leftTime
     * @param onoff
     * @param Index
     * @return
     */
    public static String SetCountDownONOFF(String TEIDS,String Nickname, int leftTime,
                                           boolean onoff, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject PL = new JSONObject();
            datat.put("leftTime", leftTime);
            PL.put("onoff", onoff);
            datat.put("PL", PL);
            data.put("2.1.1.3.1.16." + Index, datat);
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
     * 删除倒计时
     *
     * @param Nickname
     * @param Index
     * @return
     */
    public static String RemoveCountDown(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            datat.put("remove", true);
            data.put("2.1.1.3.1.16." + Index, datat);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除定时
     *
     * @param Nickname
     * @param i
     * @param Index
     * @return
     */
    public static String RemoveTimer(String TEIDS,String Nickname, int i, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.15." + Index + "255." + i, true);
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
     * @param Nickname
     * @param i
     * @param isEnable
     * @param Index
     * @return
     */
    public static String SetEnableTimer(String TEIDS,String Nickname, int i,
                                        boolean isEnable, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.15." + Index + "3." + i, isEnable);
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
     * @param Nickname
     * @param Index
     * @return
     */
    public static String GetCountdown(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.1.16." + Index);
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
     * @param Nickname
     * @param Index
     * @return
     */
    public static String GetTimer(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.1.15." + Index);
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

    public static String TimeTabletimeCmdsingletimeFlag1(
            String TEIDS,String Nickname, int month, int day, int hour, int min, boolean onoff,
            int timerId, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject timer1 = new JSONObject();
            JSONObject PL = new JSONObject();
            JSONObject timerCmd = new JSONObject();
            datat.put("timerCmd", timerCmd);
            datat.put("enable", true);
            datat.put("timerId", timerId);
            timerCmd.put("type", "single");
            timerCmd.put("timerFlag", 1);
            timerCmd.put("timer1", timer1);
            timer1.put("month", month);
            timer1.put("day", day);
            timer1.put("hour", hour);
            timer1.put("min", min);
            PL.put("onoff", onoff);
            timer1.put("PL", PL);
            data.put("2.1.1.3.1.15." + Index + ".0." + timerId, datat);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TimeTabletimeCmdweektimeFlag1(String TEIDS,String Nickname,
                                                       int wkFlag, int hour, int min, boolean onoff, int timerId, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject timer1 = new JSONObject();
            JSONObject PL = new JSONObject();
            JSONObject timerCmd = new JSONObject();
            datat.put("timerCmd", timerCmd);
            datat.put("enable", true);
            datat.put("timerId", timerId);
            timerCmd.put("type", "week");
            timerCmd.put("timerFlag", 1);
            timerCmd.put("wkFlag", wkFlag);
            timerCmd.put("timer1", timer1);
            timer1.put("hour", hour);
            timer1.put("min", min);
            PL.put("onoff", onoff);
            timer1.put("PL", PL);
            data.put("2.1.1.3.1.15." + Index + ".0." + timerId, datat);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TimeTabletimeCmdweektimeFlag2(String TEIDS,String Nickname,
                                                       int wkFlag, int hour, int min, int hour2, int min2, int timerId,
                                                       int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject timer1 = new JSONObject();
            JSONObject PL = new JSONObject();
            JSONObject timer2 = new JSONObject();
            JSONObject PL2 = new JSONObject();
            JSONObject timerCmd = new JSONObject();
            datat.put("timerCmd", timerCmd);
            timerCmd.put("type", "week");
            datat.put("enable", true);
            datat.put("timerId", timerId);
            timerCmd.put("timerFlag", 2);
            timerCmd.put("timer1", timer1);
            timerCmd.put("wkFlag", wkFlag);
            timer1.put("hour", hour);
            timer1.put("min", min);
            PL.put("onoff", true);
            timer1.put("PL", PL);
            timerCmd.put("timer2", timer2);
            timer2.put("hour", hour2);
            timer2.put("min", min2);
            PL2.put("onoff", false);
            timer2.put("PL", PL2);
            data.put("2.1.1.3.1.15." + Index + ".0." + timerId, datat);
            obj.put("PL", data);
            String jsonData = obj.toString();
            return jsonData;
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TimeTabletimeCmdsingletimeFlag2(
            String TEIDS,String Nickname, int month, int day, int hour, int min, int month2,
            int day2, int hour2, int min2, int timerId, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject datat = new JSONObject();
            JSONObject timer1 = new JSONObject();
            JSONObject PL = new JSONObject();
            JSONObject timer2 = new JSONObject();
            JSONObject PL2 = new JSONObject();
            JSONObject timerCmd = new JSONObject();
            datat.put("timerCmd", timerCmd);
            timerCmd.put("type", "single");
            datat.put("enable", true);
            datat.put("timerId", timerId);
            timerCmd.put("timerFlag", 2);
            timerCmd.put("timer1", timer1);
            timer1.put("month", month);
            timer1.put("day", day);
            timer1.put("hour", hour);
            timer1.put("min", min);
            PL.put("onoff", true);
            timer1.put("PL", PL);
            timerCmd.put("timer2", timer2);
            timer2.put("month", month2);
            timer2.put("day", day2);
            timer2.put("hour", hour2);
            timer2.put("min", min2);
            PL2.put("onoff", false);
            timer2.put("PL", PL2);
            data.put("2.1.1.3.1.15." + Index + ".0." + timerId, datat);
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
     * 网关静音按键
     *
     * @return
     */
    public static String SetMute(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.4.206", 1);// 发送1表示静音
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
     * 烟感静音
     *
     * @param Index
     * @return
     */
    public static String GetisAlarmSmoke(String TEIDS,String Nickname, int Index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.4.20.6." + Index, "isAlarm");
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
     * 网关功能设置
     *
     * @Setlightonoff 网关彩灯开关
     * @Setlightlevel 网关彩灯亮度调节
     * @SetSound 网关声音调节
     * @Setdefence 网关撤布防
     * @SetSoundtime 设置报警时间
     * @Setlighttime 设置夜灯时间
     **/
    public static String SetLightonoff(String TEIDS,String Nickname, boolean onoff) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.248", onoff);
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
     * 网关亮度
     *
     * @param Nickname
     * @param level    网关亮度分为 0-100 调节亮度
     * @return
     */
    public static String SetLightlevel(String TEIDS,String Nickname, int level) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.249", level);
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
     * 设置音量
     *
     * @param Nickname
     * @param sound    声音响度分为 0-160级调节
     * @return
     */
    public static String SetSound(String TEIDS,String Nickname, int sound) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.246", sound);
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
     * 根据不同 的国家区分报警语言
     *
     * @param Nickname
     * @param language 如中国发送"CN" 美国发送"US"等
     * @return
     */
    public static String Setlanguage(String TEIDS,String Nickname, String language) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.247", language);
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
     * 设置网关报警声音时间
     *
     * @param Nickname
     * @param time     最大不超过256
     * @return
     */
    public static String SetSoundtime(String TEIDS,String Nickname, int time) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.245", time);
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
     * 布防撤防设置
     *
     * @param Nickname
     * @param defence  0 一键撤防 1 一键布防 2 在家撤防 3 在家布防
     * @return
     */
    public static String Setdefence(String TEIDS,String Nickname, int defence) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.240", defence);
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
     * PIR时间使能设置，时间按秒计算
     *
     * @param Nickname
     * @param time
     * @param enablepir
     * @param enabledoor
     * @return
     */
    public static String Setlighttime(String TEIDS,String Nickname, int time, boolean enablepir, boolean enabledoor) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject light = new JSONObject();
            light.put("lgtimer", time);
            light.put("lgenable", enablepir);
            light.put("lgenabledoor", enabledoor);
            data.put("2.1.1.3.244", light);
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
     * 网关 GET 命令
     **/
    public static String Get(String TEIDS,String Nickname, int command, String commande) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3." + command);
            if (commande != null || commande.length() > 0) {
                tada.put("2.1.1.3." + commande);
            }
            // tada.put("2.1.1.2.1");
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
     * Zigbee插座指令
     */
    public static String SetPlug(String TEIDS,String Nickname, int index,
                                 int relayorusb,
                                 boolean enable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.1.30." + index + "." + relayorusb, enable);
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
     * 智能场景功能
     *
     * @SetScene 设置场景
     * @GetScene 获取场景
     * @EnableScene 使能场景
     * @RemoveScene 删除场景
     */

    public static String SetScene(String TEIDS,String Nickname, SceneBean sceneBean) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            Gson gson  = new Gson();
            String jsonObject = gson.toJson(sceneBean);
            JSONObject Scene = new JSONObject(jsonObject);
            data.put("2.1.1.3.3.250", Scene);
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
     * 获取场景
     *
     * @return
     */
    public static String GetScene(String TEIDS,String Nickname) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_GET);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONArray tada = new JSONArray();
            tada.put("2.1.1.3.3.250");
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
     * @param SceneId 场景ID
     * @param enable  场景使能   0.1 使能定时器 2 一键执行
     * @return
     */
    public static String EnableScene(String TEIDS,String Nickname, int SceneId, int enable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.3.250." + SceneId + ".3", enable);
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
     * @param SceneId 场景ID
     * @param enable  是否删除场景
     * @return
     */
    public static String RemoveScene(String TEIDS,String Nickname, int SceneId, boolean enable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            data.put("2.1.1.3.3.250." + SceneId + ".255", enable);
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
     * SetTHP 用户设置温湿度上下限
     *
     * @param index  设备索引
     * @param tckup  温度上限
     * @param tcklow 温度下限
     * @param hckup  湿度上限
     * @param hcklow 湿度下限
     * @param enable 使能温湿度
     * @return
     */
    public static String SetTHPAlarm(String TEIDS,String Nickname, int index, int tckup, int tcklow, int hckup, int hcklow, int enable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SmartPlug.mgetSN());
            obj.put("CID", Constants.JOSN_CID.COMMAND_SEND);
            obj.put("SID", Nickname);
            obj.put("TEID", TEIDS);
            JSONObject data = new JSONObject();
            JSONObject thp = new JSONObject();
            thp.put("tckup", tckup);
            thp.put("tcklow", tcklow);
            thp.put("hckup", hckup);
            thp.put("hcklow", hcklow);
            thp.put("ckvalid", enable);
            data.put("2.1.1.3.4.22." + index, thp);
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
