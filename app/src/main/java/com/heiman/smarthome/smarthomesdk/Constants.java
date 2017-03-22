package com.heiman.smarthome.smarthomesdk;/**
 * Created by hp on 2016/12/19.
 */


import com.heiman.smarthome.smarthomesdk.Json.SmartPlug;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 08:39
 * @Description :
 */
public class Constants {

    // 默认密码
    public static final int passwrods = SmartPlug.mgetSN();
    // WIFI智能插座WS1SK
    public static String PLUGIN_PRODUCTID = "934fa144789a4ed4992b08234063f86f";
    // WIFI计量插座
    public static String METRTING_PLUGIN_PRODUCTID = "d8a138e506d04b8ba59e2f6511a9070c";
    //  Zigbee 网关id
    public static String ZIGBEE_PRODUCTID = "58529141178445228e756abd1341ae1c";
    //  空气质量检测
    public static String AIR_PRODUCTID = "160edcafc3a80200160edcafc3a80201";
    //  红外遥控器
    public static String REMOTE_PRODUCTID = "160edcb03fc9ba00160edcb03fc9ba01";
    //  红外遥控器
    public static String GAS_PRODUCTID = "160edcb0eb22e600160edcb0eb22e601";
    //Json CID指令集
    public static class JOSN_CID {
        public static final int COMMAND_SEND = 30011;                               //CID   控制指令
        public static final int COMMAND_SEND_BACK = 30012;                          //CID   返回控制指令
        public static final int COMMAND_GET = 30021;                                //CID   获取数据指令
        public static final int COMMAND_GET_BACK = 30022;                           //CID   返回数据指令
    }

    // 设备的设备类型TYPE
    public static class DEVICE_TYPE {
        // ZigBee设备
        public static final int DEVICE_ZIGBEE_RGB = 1;                           //ZIGBEE RGB灯
        public static final int DEVICE_ZIGBEE_DOORS = 17;                        //ZIGBEE 门磁
        public static final int DEVICE_ZIGBEE_WATER = 18;                        //ZIGBEE 水浸
        public static final int DEVICE_ZIGBEE_PIR = 19;                          //ZIGBEE 红外
        public static final int DEVICE_ZIGBEE_SMOKE = 20;                        //ZIGBEE 烟感
        public static final int DEVICE_ZIGBEE_THP = 21;                  //ZIGBEE 温湿度
        public static final int DEVICE_ZIGBEE_GAS = 22;                          //ZIGBEE 气感
        public static final int DEVICE_ZIGBEE_CO = 24;                           //ZIGBEE 一氧化碳
        public static final int DEVICE_ZIGBEE_SOS = 49;                          //ZIGBEE SOS报警器
        public static final int DEVICE_ZIGBEE_SW = 50;                           //ZIGBEE 遥控器
        public static final int DEVICE_ZIGBEE_PLUGIN = 67;                       //ZIGBEE 智能插座
        public static final int DEVICE_ZIGBEE_METRTING_PLUGIN = 68;              //ZIGBEE 计量插座
        // WiFi设备
        public static final int DEVICE_WIFI_RC = 100;                            //WIFI   红外遥控转发器
        public static final int DEVICE_WIFI_GATEWAY = 769;                       //WIFI   智能网关
        public static final int DEVICE_WIFI_PLUGIN = 1041;                       //WIFI   智能插座
        public static final int DEVICE_WIFI_METRTING_PLUGIN = 1042;              //WIFI   计量插座
        public static final int DEVICE_WIFI_AIR = 1043;                          //WIFI   空气质量检测
        public static final int DEVICE_WIFI_GAS = 1044;                          //WIFI   气感
        public static final int DEVICE_WIFI_IPC = 2049;                          //WIFI   摄像头
        // 虚拟设备类型
        public static final int VDEVICE_STB = 1;                                 //有线电视机顶盒(IPTV机顶盒,卫星电视)
        public static final int VDEVICE_TV = 2;                                  //电视机
        public static final int VDEVICE_DVD = 3;                                 //DVD播放机
        public static final int VDEVICE_PROJECTOR = 5;                           //投影仪
        public static final int VDEVICE_FAN = 6;                                 //风扇
        public static final int VDEVICE_AIR = 7;                                 //空调
        public static final int VDEVICE_LIGHT = 8;                               //智能灯泡
        public static final int VDEVICE_BOX = 10;                                //互联网机顶盒
        public static final int VDEVICE_SWEEPER = 12;                            //扫地机器人
        public static final int VDEVICE_AUDIO = 13;                              //音响
        public static final int VDEVICE_CAMERA = 14;                             //相机
        public static final int VDEVICE_PURIFIER = 15;                           //空气净化器
        public static final int VDEVICE_CUSTOM = 99;                             //自定义设备

    }

}
