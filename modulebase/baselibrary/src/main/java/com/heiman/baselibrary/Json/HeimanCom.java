package com.heiman.baselibrary.Json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.HeimanGet;
import com.heiman.baselibrary.mode.HeimanSet;

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
        public static final String GW_TIME_ZONE = "2.1.1.1.7";                                   // 时区
        public static final String GET_AES_KEY = "2.1.1.1.8";                                    // AES秘钥
        public static final String GW_BASIC_INFORMATION = "2.1.1.255.0.1";                       // 网关设置的基本信息
        public static final String GW_BEEP_SOUND_LEVEL = "2.1.1.255.0.1.1";                      // 网关声音调节
        public static final String GW_BEEP_TIMER = "2.1.1.255.0.1.2";                            // 网关报警声音时长
        public static final String GW_LANGUAGE = "2.1.1.255.0.1.3";                              // 网关语言
        public static final String GW_LIGHT_LEVEL = "2.1.1.255.0.1.4";                           // 网关灯亮度
        public static final String GW_LIGHT_ONOFF = "2.1.1.255.0.1.5";                           // 网关灯开关
        public static final String GW_LGENABLE_AND_LGTIMER = "2.1.1.255.0.1.6";                  // 网关小夜灯
        public static final String GW_NAME = "2.1.1.255.0.2";                                    // 网关名称
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
                        if (fa.getName().equals("beepsoundlevel")) {
                            return true;
                        } else if (fa.getName().equals("betimer")) {
                            return true;
                        } else if (fa.getName().equals("gwlanguage")) {
                            return true;
                        } else if (fa.getName().equals("gwlightlevel")) {
                            return true;
                        } else if (fa.getName().equals("gwlightonoff")) {
                            return true;
                        } else if (fa.getName().equals("_setGwName")) {
                            return true;
                        } else if (fa.getName().equals("RC")) {
                            return true;
                        } else {
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
        plBean.setTimeZone(timeZone);
        heimanSet.setPL(plBean);
        return gson.toJson(heimanSet);
    }


    public int getVersion() {
        return Version;
    }
}
