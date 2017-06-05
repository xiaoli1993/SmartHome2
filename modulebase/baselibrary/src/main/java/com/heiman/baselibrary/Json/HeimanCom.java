package com.heiman.baselibrary.Json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.AqiSet;
import com.heiman.baselibrary.mode.EPlugSet;
import com.heiman.baselibrary.mode.HeimanGet;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.Link;
import com.heiman.baselibrary.mode.OnOffSet;
import com.heiman.baselibrary.mode.PLBase;
import com.heiman.baselibrary.mode.PlugSet;
import com.heiman.baselibrary.mode.RGBSet;
import com.heiman.baselibrary.mode.RelaySoundSet;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.SensorSet;
import com.heiman.baselibrary.mode.THPSet;
import com.heiman.baselibrary.mode.ThermostatSet;
import com.heiman.baselibrary.utils.SmartHomeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 11:32
 * @Description :
 * @Modify record :
 */

public class HeimanCom {

    private int Version = 1;// 当前协议版本号


    //网关设备OID
    public static class COM_GW_OID {
        public static final String DEVICE_BASIC_INFORMATION = "2.1.1.1";                         // 基本信息
        public static final String GW_TIME_ZONE = "2.1.1.1.2";                                   // 时区
        public static final String GET_AES_KEY = "2.1.1.1.1";                                    // AES秘钥
        public static final String GW_BASIC_INFORMATION = "2.1.1.255.0.1";                       // 网关设置的基本信息
        public static final String GW_BEEP_SOUND_LEVEL = "2.1.1.255.0.1.1";                      // 网关声音调节
        public static final String GW_BEEP_TIMER = "2.1.1.255.0.1.2";                            // 网关报警声音时长
        public static final String GW_LANGUAGE = "2.1.1.255.0.1.3";                              // 网关语言
        public static final String GW_LIGHT_LEVEL = "2.1.1.255.0.1.4";                           // 网关灯亮度
        public static final String GW_LIGHT_ONOFF = "2.1.1.255.0.1.5";                           // 网关灯开关
        public static final String GW_LGENABLE_AND_LGTIMER = "2.1.1.255.0.1.6";                  // 网关小夜灯
        public static final String GW_NAME = "2.1.1.254";                                        // 网关名称
        public static final String GW_SUB = "2.1.1.0";                                           // 子设备信息
        public static final String GW_UPDATA = "2.1.1.6";                                        // 设备更新
        public static final String GW_DEPLOY_DEFENCE = "2.1.1.255.0.5.2";                        // 设置网关撤布防信息
        public static final String GW_DEPLOY_DEFENCE_ACTION = "2.1.1.255.0.5";                   // 网关撤布防执行
        public static final String GW_ADD_SUB = "2.1.1.255.0.6";                                 // 允许添加子设备

        public static final String SCENE = "2.1.1.2.0";                                          // 设备场景
        public static final String SCENE_ACTION = "2.1.1.2.0.sceneID.1";                         // 执行场景
        public static final String SCENE_DEL = "2.1.1.2.0.sceneID.254";                          // 删除场景
        public static final String LINK = "2.1.1.3.0";                                           // 设备联动
        public static final String LINK_EN = "2.1.1.3.0.linkID.1";                               // 使能联动
        public static final String LINK_DEL = "2.1.1.3.0.linkID.254";                            // 删除联动

        public static final String SUB_ROOM_ID = "2.1.1.0.253.index";                            // 房间ID
        public static final String SUB_NAME = "2.1.1.0.254.index";                               // 子设备名称
        public static final String SUB_DEL = "2.1.1.0.255.index";                                // 删除子设备
        public static final String TEMP_THRESHOLD = "2.1.1.0.2.1.index.1";                       // 温湿度设置

        public static final String SUB_PLUG = "2.1.1.0.4.1.index.3";                             // 子设备插座
        public static final String SUB_PLUG_RELAY = "2.1.1.0.4.1.index.1";                       // 子设备插座继电器开关
        public static final String SUB_PLUG_USB = "2.1.1.0.4.1.index.2";                         // 子设备插座USB开关

        public static final String SUB_RGB_ONOFF = "2.1.1.0.3.1.index.1";                        // 子设备RGB开关
        public static final String SUB_RGB_R = "2.1.1.0.3.1.index.2";                            // 子设备RGB 颜色R
        public static final String SUB_RGB_G = "2.1.1.0.3.1.index.3";                            // 子设备RGB 颜色G
        public static final String SUB_RGB_B = "2.1.1.0.3.1.index.4";                            // 子设备RGB 颜色B
        public static final String SUB_RGB_LEVEL = "2.1.1.0.3.1.index.5";                        // 子设备RGB 亮度

        public static final String SUB_S = "2.1.1.0.1.1.index.2";                                // 子设备
        public static final String SUB_DEPLOY_DEFNCE = "2.1.1.0.1.1.index.3";                    // 子设备
        public static final String SUB_ALRM = "2.1.1.0.1.1.index.4";                             // 子设备

        public static final String SET_SUB = "2.1.1.0.y1.y2.index";
        public static final String SET_SUB_GETEP = "2.1.1.0.y1.y2.index.getep";


    }

    /**
     * 获取设备OID值
     *
     * @param SN      6位随机数用于判断ACK
     * @param encrypt 是否加密
     * @param OID     所需OID
     * @return
     */
    public static String getOID(int SN, int encrypt, List<String> OID) {
        Gson gson = new Gson();
        HeimanGet heimanGet = new HeimanGet();
        heimanGet.setCID(Constant.JOSN_CID.COMMAND_GET);
        heimanGet.setENCRYPT(encrypt);
        heimanGet.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        heimanGet.setSN(SN);
        heimanGet.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        HeimanGet.PLBean PL = new HeimanGet.PLBean();
        PL.setOID(OID);
        heimanGet.setPL(PL);
        return gson.toJson(heimanGet);
    }

    /**
     * 设置时区
     *
     * @param SN       随机数
     * @param encrypt  是否加密
     * @param timeZone 时区
     * @return
     */
    public static String setTimeZone(int SN, int encrypt, String timeZone) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "aesKeyOID":
                                return true;
                            case "deviceNameOID":
                                return true;
                            case "gwBasicOID":
                                return true;
                            case "addSubOID":
                                return true;
                            case "RC":
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        HeimanSet heimanSet = new HeimanSet();
        heimanSet.setCID(Constant.JOSN_CID.COMMAND_SEND);
        heimanSet.setENCRYPT(encrypt);
        heimanSet.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        heimanSet.setSN(SN);
        heimanSet.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        HeimanSet.PLBean plBean = new HeimanSet.PLBean();
        HeimanSet.PLBean.TimeZoneOID timeZoneOID = new HeimanSet.PLBean.TimeZoneOID();
        timeZoneOID.setTimeZone(timeZone);
        plBean.setTimeZoneOID(timeZoneOID);
        heimanSet.setPL(plBean);
        return gson.toJson(heimanSet);
    }

    /**
     * 设置名字
     *
     * @param SN         随机数
     * @param encrypt    是否加密
     * @param deviceName 时区
     * @return
     */
    public static String setDeviceName(int SN, int encrypt, String deviceName) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "aesKeyOID":
                                return true;
                            case "timeZoneOID":
                                return true;
                            case "gwBasicOID":
                                return true;
                            case "addSubOID":
                                return true;
                            case "RC":
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        HeimanSet heimanSet = new HeimanSet();
        heimanSet.setCID(Constant.JOSN_CID.COMMAND_SEND);
        heimanSet.setENCRYPT(encrypt);
        heimanSet.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        heimanSet.setSN(SN);
        heimanSet.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        HeimanSet.PLBean plBean = new HeimanSet.PLBean();
        HeimanSet.PLBean.DeviceNameOID deviceNameOID = new HeimanSet.PLBean.DeviceNameOID();
        deviceNameOID.setName(deviceName);
        plBean.setDeviceNameOID(deviceNameOID);
        heimanSet.setPL(plBean);
        return gson.toJson(heimanSet);
    }

    /**
     * 允许加网
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param join    是否允许加网1为允许 0为不允许
     * @return
     */
    public static String setJoinSub(int SN, int encrypt, int join) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "aesKeyOID":
                                return true;
                            case "timeZoneOID":
                                return true;
                            case "gwBasicOID":
                                return true;
                            case "deviceNameOID":
                                return true;
                            case "RC":
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        HeimanSet heimanSet = new HeimanSet();
        heimanSet.setCID(Constant.JOSN_CID.COMMAND_SEND);
        heimanSet.setENCRYPT(encrypt);
        heimanSet.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        heimanSet.setSN(SN);
        heimanSet.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        HeimanSet.PLBean plBean = new HeimanSet.PLBean();
        HeimanSet.PLBean.AddSubOID addSubOID = new HeimanSet.PLBean.AddSubOID();
        addSubOID.setJoin(join);
        plBean.setAddSubOID(addSubOID);
        heimanSet.setPL(plBean);
        return gson.toJson(heimanSet);
    }

    /**
     * 设置基础信息
     *
     * @param SN         随机数
     * @param encrypt    是否加密
     * @param gwBasicOID 基础信息设置
     *                   alarmlevel	int	报警音量,范围是1-160(R/W)
     *                   soundlevel	int	提示音音量,范围是1-160(R/W)
     *                   betimer	int	报警时的时长,单位为秒,范围是0-255(R/W) 注：当被设置为0时,本地报警无效（喇叭与LED不做响应
     *                   gwlanguage	string	播放语音的语言,分别为CN和US两种,CN表示中文播报语音,US表示英文播报语音(R/W)
     *                   gwlightlevel	int	网关灯的亮度值,范围是1-100(R/W)
     *                   gwlightonoff	int	网关灯的开关,0-关,1-开(R/W)
     *                   lgenable	int	网关小夜灯联动红外的使能开关,0-关,1-开(R/W) 注：在红外撤防状态下,如果此时出发红外,同时GW检测到外界亮度值
     *                   lgtimer	int	网关小夜灯被触发时的亮起时长,单位为秒,范围是1-255(R/W)
     *                   armtype	int	网关布防模式，0-撤防,1-外出布防,2-在家布防
     *                   roomID	string	房间号
     * @return
     */
    public static String setBasic(int SN, int encrypt, final HeimanSet.PLBean.GwBasicOID gwBasicOID) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        if (gwBasicOID.getArmtype() == 9999) {
                            if (fa.getName().equals("armtype")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getAlarmlevel() == 9999) {
                            if (fa.getName().equals("alarmlevel")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getSoundlevel() == 9999) {
                            if (fa.getName().equals("soundlevel")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getBetimer() == 9999) {
                            if (fa.getName().equals("betimer")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getGwlanguage().equals("")) {
                            if (fa.getName().equals("gwlanguage")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getGwlightlevel() == 9999) {
                            if (fa.getName().equals("gwlightlevel")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getGwlightonoff() == 9999) {
                            if (fa.getName().equals("gwlightonoff")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getLgtimer() == 9999) {
                            if (fa.getName().equals("lgtimer")) {
                                return true;
                            }
                        }
                        if (gwBasicOID.getRoomID().equals("")) {
                            if (fa.getName().equals("roomID")) {
                                return true;
                            }
                        }
                        switch (fa.getName()) {
                            case "aesKeyOID":
                                return true;
                            case "addSubOID":
                                return true;
                            case "timeZoneOID":
                                return true;
                            case "deviceNameOID":
                                return true;
                            case "RC":
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        HeimanSet heimanSet = new HeimanSet();
        heimanSet.setCID(Constant.JOSN_CID.COMMAND_SEND);
        heimanSet.setENCRYPT(encrypt);
        heimanSet.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        heimanSet.setSN(SN);
        heimanSet.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        HeimanSet.PLBean plBean = new HeimanSet.PLBean();
        plBean.setGwBasicOID(gwBasicOID);
        heimanSet.setPL(plBean);
        return gson.toJson(heimanSet);
    }


    /**
     * 升级设备
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param x       wifi固件 2：ZigBee协调器固件 3：ZigBee子设备固件 4:声音升级
     * @param isEn    是否升级
     * @return
     */
    public static String upData(int SN, int encrypt, int x, int isEn) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject isEnJson = new JSONObject();
            data.put("2.1.1.4.6." + x, isEnJson);
            isEnJson.put("confirm", isEn);
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
     * 修改子设备名称
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param index   设备索引
     * @param name    设备名称
     * @return
     */
    public static String setSubName(int SN, int encrypt, int index, String name) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject nameJson = new JSONObject();
            data.put(COM_GW_OID.SUB_NAME.replace("{index}", index + ""), nameJson);
            nameJson.put("name", name);
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
     * 修改设备房间号
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param index   设备索引
     * @param roomID  房间ID
     * @return
     */
    public static String setRoomID(int SN, int encrypt, int index, String roomID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject roomJson = new JSONObject();
            data.put(COM_GW_OID.SUB_ROOM_ID.replace("{index}", index + ""), roomJson);
            roomJson.put("roomID", roomID);
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
     * 删除子设备
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param index   索引
     * @return
     */
    public static String deleteSubDevice(int SN, int encrypt, int index) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject deleteJson = new JSONObject();
            data.put(COM_GW_OID.SUB_DEL.replace("{index}", index + ""), deleteJson);
//            deleteJson.put("delete", delete);
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
     * 设置场景
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param scene   设置场景
     * @return
     */
    public static String setScene(int SN, int encrypt, Scene scene) {
        Gson gson = new Gson();
        scene.setCID(Constant.JOSN_CID.COMMAND_SEND);
        scene.setENCRYPT(encrypt);
        scene.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        scene.setSN(SN);
        scene.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        return gson.toJson(scene);
    }


    /**
     * 执行场景
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param sceneID 场景ID
     * @return
     */
    public static String actionScene(int SN, int encrypt, int sceneID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject deleteJson = new JSONObject();
            data.put(COM_GW_OID.SCENE_ACTION.replace("{sceneID}", sceneID + ""), deleteJson);
//            deleteJson.put("delete", delete);
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
     * 删除场景
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param sceneID 场景ID
     * @return
     */
    public static String deleteScene(int SN, int encrypt, int sceneID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject deleteJson = new JSONObject();
            data.put(COM_GW_OID.SCENE_DEL.replace("{sceneID}", sceneID + ""), deleteJson);
//            deleteJson.put("delete", delete);
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
     * 设置联动
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param link    设置场景
     * @return
     */
    public static String setScene(int SN, int encrypt, Link link) {
        Gson gson = new Gson();
        link.setCID(Constant.JOSN_CID.COMMAND_SEND);
        link.setENCRYPT(encrypt);
        link.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        link.setSN(SN);
        link.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        return gson.toJson(link);
    }

    /**
     * 执行场景
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param linkID  联动ID
     * @param enable  使能 0 关闭1开启
     * @return
     */
    public static String actionLink(int SN, int encrypt, int linkID, int enable) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject deleteJson = new JSONObject();
            data.put(COM_GW_OID.LINK_EN.replace("{linkID}", linkID + ""), deleteJson);
            deleteJson.put("enable", enable);
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
     * 删除场景
     *
     * @param SN      随机数
     * @param encrypt 是否加密
     * @param linkID  联动ID
     * @return
     */
    public static String deleteLink(int SN, int encrypt, int linkID) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("SN", SN);
            obj.put("CID", Constant.JOSN_CID.COMMAND_SEND);
            obj.put("SID", BaseApplication.getMyApplication().getUserInfo().getNickname());
            obj.put("TEID", BaseApplication.getMyApplication().getUserInfo().getId() + "");
            obj.put("ENCRYPT", encrypt);
            JSONObject data = new JSONObject();
            JSONObject deleteJson = new JSONObject();
            data.put(COM_GW_OID.LINK_DEL.replace("{linkID}", linkID + ""), deleteJson);
//            deleteJson.put("delete", delete);
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
     * 设置子设备数据
     *
     * @param SN         随机数
     * @param encrypt    是否加密
     * @param deviceType 设备类型
     * @param index      设备索引
     * @param subSeting  需要设置设备属性值
     * @return
     */
    public static String setSubData(int SN, int encrypt, int deviceType, String index, String subSeting) {
        Gson gson = new Gson();
        PLBase plBase = new PLBase();
        plBase.setCID(Constant.JOSN_CID.COMMAND_SEND);
        plBase.setENCRYPT(encrypt);
        plBase.setSID(BaseApplication.getMyApplication().getUserInfo().getNickname());
        plBase.setSN(SN);
        plBase.setTEID(BaseApplication.getMyApplication().getUserInfo().getId() + "");
        String base = gson.toJson(plBase);
        // 2.1.1.0.y1.y2.index.getep;
        String SUBOID = COM_GW_OID.SET_SUB.replace("{y1}", SmartHomeUtils.typeToY1(deviceType) + "").replace("{y1}", SmartHomeUtils.typeToY2(deviceType) + "").replace("{index}", index);
//        String subSeting = gson.toJson(objects);
        try {
            JSONObject obj = new JSONObject(base);
            JSONObject suboid = new JSONObject(subSeting);
            JSONObject data = new JSONObject();
            data.put(SUBOID, suboid);
            obj.put("PL", data);
            return obj.toString();
            // TODO:将jsonData发送到服务器
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * sensor报警设备设置
     *
     * @param sensorSet
     * @return
     */
    public static String getSerSorSeting(final SensorSet sensorSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "enableTime":
                                return sensorSet.getEnableTime() == 9999;
                            case "sh":
                                return sensorSet.getSh() == 9999;
                            case "sm":
                                return sensorSet.getSm() == 9999;
                            case "eh":
                                return sensorSet.getEh() == 9999;
                            case "em":
                                return sensorSet.getEm() == 9999;
                            case "wf":
                                return sensorSet.getWf() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(sensorSet);
    }

    /**
     * 获取温湿度设置状态
     *
     * @param thpSet
     * @return
     */
    public static String getTHPSeting(final THPSet thpSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "h_cklow":
                                return thpSet.getH_cklow().equals("");
                            case "h_ckup":
                                return thpSet.getH_ckup().equals("");
                            case "h_ckvalid":
                                return thpSet.getH_ckvalid() == 9999;
                            case "t_cklow":
                                return thpSet.getT_cklow().equals("");
                            case "t_ckup":
                                return thpSet.getT_ckup().equals("");
                            case "t_ckvalid":
                                return thpSet.getT_ckvalid() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(thpSet);
    }

    /**
     * 获取RGB灯设置状态
     *
     * @param rgbSet
     * @return
     */
    public static String getRGBSeting(final RGBSet rgbSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "onoff":
                                return rgbSet.getOnoff() == 9999;
                            case "em":
                                return rgbSet.getEm() == 9999;
                            case "eh":
                                return rgbSet.getEh() == 9999;
                            case "sm":
                                return rgbSet.getSm() == 9999;
                            case "sh":
                                return rgbSet.getSh() == 9999;
                            case "colorBlue":
                                return rgbSet.getColorBlue() == 9999;
                            case "colorGreen":
                                return rgbSet.getColorGreen() == 9999;
                            case "colorRed":
                                return rgbSet.getColorRed() == 9999;
                            case "enableTime":
                                return rgbSet.getEnableTime() == 9999;
                            case "level":
                                return rgbSet.getLevel() == 9999;
                            case "wf":
                                return rgbSet.getWf() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(rgbSet);
    }

    /**
     * 普通插座
     *
     * @param plugSet
     * @return
     */
    public static String getPlugSeting(final PlugSet plugSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "relayonoff":
                                return plugSet.getRelayonoff() == 9999;
                            case "enableTime_r":
                                return plugSet.getEnableTime_r() == 9999;
                            case "sh_r":
                                return plugSet.getSh_r() == 9999;
                            case "sm_r":
                                return plugSet.getSm_r() == 9999;
                            case "eh_r":
                                return plugSet.getEh_r() == 9999;
                            case "em_r":
                                return plugSet.getEm_r() == 9999;
                            case "wf_r":
                                return plugSet.getWf_r() == 9999;
                            case "usbonoff":
                                return plugSet.getUsbonoff() == 9999;
                            case "enableTime_u":
                                return plugSet.getEnableTime_u() == 9999;
                            case "sh_u":
                                return plugSet.getSh_u() == 9999;
                            case "sm_u":
                                return plugSet.getSm_u() == 9999;
                            case "eh_u":
                                return plugSet.getEh_u() == 9999;
                            case "em_u":
                                return plugSet.getEm_u() == 9999;
                            case "wf_u":
                                return plugSet.getWf_u() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(plugSet);
    }

    /**
     * 计量插座
     *
     * @param eplugSet
     * @return
     */
    public static String getEPlugSeting(final EPlugSet eplugSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "relayonoff":
                                return eplugSet.getRelayonoff() == 9999;
                            case "enableTime":
                                return eplugSet.getEnableTime() == 9999;
                            case "sh":
                                return eplugSet.getSh() == 9999;
                            case "sm":
                                return eplugSet.getSm() == 9999;
                            case "eh":
                                return eplugSet.getEh() == 9999;
                            case "em":
                                return eplugSet.getEm() == 9999;
                            case "wf":
                                return eplugSet.getWf() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(eplugSet);
    }

    /**
     * 一路二路三路开关
     *
     * @param onOffSet
     * @return
     */
    public static String getOnOffSeting(final OnOffSet onOffSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "onoff1":
                                return onOffSet.getOnoff1() == 9999;
                            case "onoff2":
                                return onOffSet.getOnoff2() == 9999;
                            case "onoff3":
                                return onOffSet.getOnoff3() == 9999;
                            case "enableTime1":
                                return onOffSet.getEnableTime1() == 9999;
                            case "sh1":
                                return onOffSet.getSh1() == 9999;
                            case "sm1":
                                return onOffSet.getSm1() == 9999;
                            case "eh1":
                                return onOffSet.getEh1() == 9999;
                            case "em1":
                                return onOffSet.getEm1() == 9999;
                            case "wf1":
                                return onOffSet.getWf1() == 9999;
                            case "enableTime2":
                                return onOffSet.getEnableTime2() == 9999;
                            case "sh2":
                                return onOffSet.getSh2() == 9999;
                            case "sm2":
                                return onOffSet.getSm2() == 9999;
                            case "eh2":
                                return onOffSet.getEh2() == 9999;
                            case "em2":
                                return onOffSet.getEm2() == 9999;
                            case "wf2":
                                return onOffSet.getWf2() == 9999;
                            case "enableTime3":
                                return onOffSet.getEnableTime3() == 9999;
                            case "sh3":
                                return onOffSet.getSh3() == 9999;
                            case "sm3":
                                return onOffSet.getSm3() == 9999;
                            case "eh3":
                                return onOffSet.getEh3() == 9999;
                            case "em3":
                                return onOffSet.getEm3() == 9999;
                            case "wf3":
                                return onOffSet.getWf3() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(onOffSet);
    }

    /**
     * 继电器以及声光警号
     *
     * @param relaySoundSet
     * @return
     */
    public static String getRelaySoundSeting(final RelaySoundSet relaySoundSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "duration":
                                return relaySoundSet.getDuration() == 9999;
                            case "onoff":
                                return relaySoundSet.getOnoff() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(relaySoundSet);
    }

    /**
     * 空气质量
     *
     * @param aqiSet
     * @return
     */
    public static String getAqiSeting(final AqiSet aqiSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "h_cklow":
                                return aqiSet.getH_cklow().equals("");
                            case "h_ckup":
                                return aqiSet.getH_ckup().equals("");
                            case "h_ckvalid":
                                return aqiSet.getH_ckvalid() == 9999;
                            case "t_cklow":
                                return aqiSet.getT_cklow().equals("");
                            case "t_ckup":
                                return aqiSet.getT_ckup().equals("");
                            case "t_ckvalid":
                                return aqiSet.getT_ckvalid() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(aqiSet);
    }

    /**
     * 温控器
     *
     * @param thermostatSet
     * @return
     */
    public static String getThermostatSeting(final ThermostatSet thermostatSet) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        switch (fa.getName()) {
                            case "t_ckvalid":
                                return thermostatSet.getT_ckvalid() == 9999;
                            case "t_cklow":
                                return thermostatSet.getT_cklow().equals("");
                            case "t_ckup":
                                return thermostatSet.getT_ckup().equals("");
                            case "onoff":
                                return thermostatSet.getOnoff() == 9999;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(thermostatSet);
    }


    public int getVersion() {
        return Version;
    }
}
