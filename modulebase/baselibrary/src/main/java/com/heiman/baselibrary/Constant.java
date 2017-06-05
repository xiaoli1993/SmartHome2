package com.heiman.baselibrary;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import com.heiman.baselibrary.Json.SmartPlug;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/20 10:51
 * @Description :
 */
public class Constant {

    public static final String IO_URL = "io.heiman.cn";
    public static final int IO_PORT = 23778;

    // ------------启动监听
    public static final String PACKAGE_NAME = BaseApplication.getMyApplication().getPackageName();
    public static final String BROADCAST_ON_START = PACKAGE_NAME + ".onStart"; //
    public static final String BROADCAST_ON_LOGIN = PACKAGE_NAME + ".xlinkonLogin";

    public static final String BROADCAST_CLOUD_DISCONNECT = PACKAGE_NAME + ".clouddisconnect";
    public static final String BROADCAST_LOCAL_DISCONNECT = PACKAGE_NAME + ".localdisconnect";
    public static final String BROADCAST_RECVPIPE = PACKAGE_NAME + ".recv-pipe";
    public static final String BROADCAST_RECVPIPE_SYNC = PACKAGE_NAME + ".recv-pipe-sync";
    public static final String BROADCAST_DEVICE_CHANGED = PACKAGE_NAME + ".device-changed";
    public static final String BROADCAST_CONNENCT_SUCCESS = PACKAGE_NAME + ".connenctsuccess";
    public static final String BROADCAST_CONNENCT_FAIL = PACKAGE_NAME + ".connenctfail";
    public static final String BROADCAST_SEND_OVERTIME = PACKAGE_NAME + ".sendovertime";
    public static final String BROADCAST_SEND_SUCCESS = PACKAGE_NAME + ".sendsuccess";
    public static final String BROADCAST_EVENT_NOTIFY = PACKAGE_NAME + ".event-notify";
    public static final String BROADCAST_LOG_ACTION = PACKAGE_NAME + ".Log_action";
    public static final String BROADCAST_SEND_DATA = PACKAGE_NAME + ".send_data";
    public static final String BROADCAST_DEVICE_DATAPOINT_RECV = PACKAGE_NAME + ".device_recv_datapoint";
    public static final String BROADCAST_DATAPOINT_RECV = PACKAGE_NAME + ".recv_datapoint";

    public static final String BROADCAST_DEVICE_SYNC = PACKAGE_NAME + ".device-sync";
    public static final String BROADCAST_EXIT = PACKAGE_NAME + ".exit";
    public static final String BROADCAST_TIMER_UPDATE = PACKAGE_NAME + "timer-update";
    public static final String BROADCAST_SOCKET_STATUS = PACKAGE_NAME + "socket-status";
    // http 注册，获取appid回调
    public static final int HTTP_NETWORK_ERR = 1;

    // 数据包超时时间
    public static final int TIMEOUT = 10;// 设置请求超时时间

    public static final String DATA = "data";
    // public static final String DEVICE = "device";
    public static final String DEVICE_MAC = "device-mac";
    public static final String ROOM_ID = "room-id";
    public static final String ROOM_NAME = "room-name";
    public static final String ROOM_URL = "room-url";
    public static final String ZIGBEE_MAC = "zigbee-mac";
    public static final String IS_SUB = "is-sub";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String KEY = "key";

    public static final String SAVE_PRODUCTID = "pid";
    public static final String SAVE_COMPANY_ID = "COMPANY_ID";
    public static final String SAVE_EMAIL_ID = "EMAIL_ID";
    public static final String SAVE_PASSWORD_ID = "PASSWD_ID";
    public static final String IS_INIT = "isinit";
    public static final int TIMER_OFF = 0;
    public static final int TIMER_ON = 1;
    public static final int TIMER_BUFF_SIZE = 6;
    public static final int TIMER_MAX = 19;
    public static final String APPID = "appId";
    public static final String AUTHKEY = "authKey";
    public static final String IS_DEVICE = "isdevice";
    public static final String MY_ACCOUNT = "MY_ACCOUNT";// 用户账号
    public static final String MY_PASSWORD = "MY_PASSWORD";// 用户密码


    /**
     * 我的头像保存目录
     */
    public static String MyAvatarDir = "/sdcard/heiman/avatar/";
    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;// 拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;// 本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;// 系统裁剪头像


    // 默认密码
    public static final int passwrods = SmartPlug.mgetSN();
    // WIFI智能插座WS1SK
    public static String PLUGIN_PRODUCTID = "934fa144789a4ed4992b08234063f86f";
    // WIFI计量插座
    public static String METRTING_PLUGIN_PRODUCTID = "d8a138e506d04b8ba59e2f6511a9070c";
    //  ZigBee 网关id
    public static String ZIGBEE_PRODUCTID = "58529141178445228e756abd1341ae1c";
    //  空气质量检测
    public static String AIR_PRODUCTID = "160edcafc3a80200160edcafc3a80201";
    //  红外遥控器
    public static String REMOTE_PRODUCTID = "160edcb03fc9ba00160edcb03fc9ba01";
    //  WIFI气感
    public static String GAS_PRODUCTID = "160edcb0eb22e600160edcb0eb22e601";
    // ZigBee 新小网关
    public static String ZIGBEE_H1GW_NEW_PRODUCTID = "0fc2c50412264ce29dae547ff08b941e";

    public static String ZIGBEE_H1GW_NEW_KEY = "2f187a0983ea42db";


    //Json CID指令集
    public static class JOSN_CID {
        public static final int COMMAND_SEND = 30011;                               //CID   控制指令
        public static final int COMMAND_SEND_BACK = 30012;                          //CID   返回控制指令
        public static final int COMMAND_GET = 30021;                                //CID   获取数据指令
        public static final int COMMAND_GET_BACK = 30022;                           //CID   返回数据指令
    }

    //RC集合
    public static class JOSN_RC {
        public static final int SUCCESS = 1;                                         //成功
        public static final int UNKNOWN_ERROR = 0;                                   //未知错误
        public static final int PARAMETER_ERROR = -1;                                //参数错误
        public static final int DECRYPTION_FAILURE = -2;                             //解密失败 (明文回复)
        public static final int THE_OID_DOES_NOT_EXIST = -3;                        //不存在该OID
        public static final int CID_DOES_NOT_EXIST = -4;                             //CID不存在
        public static final int PL_CANNOT_BE_EMPTY = -5;                             //PL中不能为空
        public static final int TEID_CANNOT_BE_EMPTY = -6;                           //TEID不能为空
    }

    // 设备的设备类型TYPE
    public static class DEVICE_TYPE {
        // ZigBee设备
        public static final int DEVICE_ZIGBEE_RGB = 1;                           //ZIGBEE RGB灯
        public static final int DEVICE_ZIGBEE_ONE_ONOFF = 2;                     //ZIGBEE 一路开关
        public static final int DEVICE_ZIGBEE_TWO_ONOFF = 3;                     //ZIGBEE 二路开关
        public static final int DEVICE_ZIGBEE_THREE_ONOFF = 4;                   //ZIGBEE 三路开关
        public static final int DEVICE_ZIGBEE_RC = 5;                            //ZIGBEE 红外转发器
        public static final int DEVICE_ZIGBEE_RELAY = 6;                         //ZIGBEE 继电器

        public static final int DEVICE_ZIGBEE_DOORS = 17;                        //ZIGBEE 门磁
        public static final int DEVICE_ZIGBEE_WATER = 18;                        //ZIGBEE 水浸
        public static final int DEVICE_ZIGBEE_PIR = 19;                          //ZIGBEE 红外
        public static final int DEVICE_ZIGBEE_SMOKE = 20;                        //ZIGBEE 烟感
        public static final int DEVICE_ZIGBEE_THP = 21;                          //ZIGBEE 温湿度
        public static final int DEVICE_ZIGBEE_GAS = 22;                          //ZIGBEE 气感
        public static final int DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM= 23;         //ZIGBEE 声光警号

        public static final int DEVICE_ZIGBEE_CO = 24;                           //ZIGBEE 一氧化碳探测器
        public static final int DEVICE_ZIGBEE_ILLUMINANCE= 25;                   //ZIGBEE 光照度
        public static final int DEVICE_ZIGBEE_AIR = 26;                          //ZIGBEE 空气质量
        public static final int DEVICE_ZIGBEE_THERMOSTAT = 27;                   //ZIGBEE 温控器

        public static final int DEVICE_ZIGBEE_SOS = 49;                          //ZIGBEE SOS报警器
        public static final int DEVICE_ZIGBEE_SW = 50;                           //ZIGBEE 遥控器
        public static final int DEVICE_ZIGBEE_PLUGIN = 67;                       //ZIGBEE 智能插座
        public static final int DEVICE_ZIGBEE_METRTING_PLUGIN = 68;              //ZIGBEE 计量插座
        // WiFi设备
        public static final int DEVICE_WIFI_RC = 100;                            //WIFI   红外遥控转发器
        public static final int DEVICE_WIFI_GATEWAY = 769;                       //WIFI   智能网关
        public static final int DEVICE_WIFI_GATEWAY_HS1GW_NEW = 770;             //WIFI   新小智能网关
        public static final int DEVICE_WIFI_GATEWAY_HS2GW = 771;                 //WIFI   大网关
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
