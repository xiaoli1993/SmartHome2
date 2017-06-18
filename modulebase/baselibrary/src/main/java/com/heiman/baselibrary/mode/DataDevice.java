package com.heiman.baselibrary.mode;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 作者：肖力
 * 邮箱：554674787@qq.com
 * 时间：2016/8/18 14:53
 */
public class DataDevice extends DataSupport {
    private long id;
    private Date date;//添加时间
    //    private XlinkDevice xlinkDevice;
//    private SubDevice subDevice;
    private String userid;// 用户ID
    private String userName;// 用户昵称
    private String deviceId;// 设备ID
    //    private String deviceMac;// 设备mac
    private String subMac;// 子设备mac
    //    private String subID;//  子设备ID
//    private String subType;// 子设备类型
//    private String actionName;// 动作名称
    private String year;//年
    private String month;//月
    private String day;//日
    private String HH;//时
    private String mm;//分
    private String ss;//秒
    private String bodyLocKey;//数据内容

    private int deviceMessageID;//消息的类型
    private String onoff;// 开关
    private String temp;// 温度
    private String humidity;// 湿度
    private String electricity;// 电量
    private String TOVC;// TOVC
    private String PM25;
    private String CHCO;
    private String AQI;
    private String PM10;
    private String GAS;
    private String IA;// 光照度
    private String TV;// TV值
    private String IS;//执行动作成功标志
    private String linkID;//联动ID
    private String linkName;//联动名称
    private String sceneID;//场景ID
    private String sceneName;//场景名称
    private String timeID;//定时ID
    private String delay;//延迟时间
    private String VS;//升级成功后的软件版本号
    private String data;// 数据

    private String messageID;//消息ID
    private int messageType;//消息类型
    private int notifyType;//通知类型
    private String createDate;//创建时间
    private boolean isRead;//是否已读
    private boolean isPush;//是否推送
    private String alertName;//触发告警规则名称
    private String alertValue;//触发告警的数据端点值


    /**
     * 用户操作记录
     */
    private String alarmlevel;
    private String soundlevel;
    private String betimer;
    private String gwlanguage;
    private String gwlightlevel;
    private String gwlightonoff;
    private String lgtimer;
    private String armtype;
    private String roomID;
    private int onoff1;
    private int onoff2;
    private int onoff3;
    private int enableTime1;
    private int sh1;
    private int sm1;
    private int eh1;
    private int em1;
    private int wf1;
    private int enableTime2;
    private int sh2;
    private int sm2;
    private int eh2;
    private int em2;
    private int wf2;
    private int enableTime3;
    private int sh3;
    private int sm3;
    private int eh3;
    private int em3;
    private int wf3;
    private int relayonoff;
    private int enableTime_r;
    private int sh_r;
    private int sm_r;
    private int eh_r;
    private int em_r;
    private int wf_r;
    private int usbonoff;
    private int enableTime_u;
    private int sh_u;
    private int sm_u;
    private int eh_u;
    private int em_u;
    private int wf_u;
    private int level;
    private int colorRed;
    private int colorGreen;
    private int colorBlue;
    private int duration;
    private int enableTime;
    private int sh;
    private int sm;
    private int eh;
    private int em;
    private int wf;
    private String h_ckup;
    private String h_cklow;
    private int h_ckvalid;
    private String t_ckup;
    private String t_cklow;
    private int t_ckvalid;

    public int getDeviceMessageID() {
        return deviceMessageID;
    }

    public void setDeviceMessageID(int deviceMessageID) {
        this.deviceMessageID = deviceMessageID;
    }

    public String getOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        this.onoff = onoff;
    }

    public String getIA() {
        return IA;
    }

    public void setIA(String IA) {
        this.IA = IA;
    }

    public String getTV() {
        return TV;
    }

    public void setTV(String TV) {
        this.TV = TV;
    }

    public String getIS() {
        return IS;
    }

    public void setIS(String IS) {
        this.IS = IS;
    }

    public String getLinkID() {
        return linkID;
    }

    public void setLinkID(String linkID) {
        this.linkID = linkID;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getSceneID() {
        return sceneID;
    }

    public void setSceneID(String sceneID) {
        this.sceneID = sceneID;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getTimeID() {
        return timeID;
    }

    public void setTimeID(String timeID) {
        this.timeID = timeID;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getVS() {
        return VS;
    }

    public void setVS(String VS) {
        this.VS = VS;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

//    public String getSubType() {
//        return subType;
//    }
//
//    public void setSubType(String subType) {
//        this.subType = subType;
//    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isPush() {
        return isPush;
    }

    public void setPush(boolean push) {
        isPush = push;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public XlinkDevice getXlinkDevice() {
//        return xlinkDevice;
//    }
//
//    public void setXlinkDevice(XlinkDevice xlinkDevice) {
//        this.xlinkDevice = xlinkDevice;
//    }
//
//    public SubDevice getSubDevice() {
//        return subDevice;
//    }
//
//    public void setSubDevice(SubDevice subDevice) {
//        this.subDevice = subDevice;
//    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

//    public String getDeviceMac() {
//        return deviceMac;
//    }

//    public void setDeviceMac(String deviceMac) {
//        this.deviceMac = deviceMac;
//    }

    public String getSubMac() {
        return subMac;
    }

    public void setSubMac(String subMac) {
        this.subMac = subMac;
    }

//    public String getSubID() {
//        return subID;
//    }

//    public void setSubID(String subID) {
//        this.subID = subID;
//    }
//
//    public String getActionName() {
//        return actionName;
//    }
//
//    public void setActionName(String actionName) {
//        this.actionName = actionName;
//    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHH() {
        return HH;
    }

    public void setHH(String HH) {
        this.HH = HH;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getBodyLocKey() {
        return bodyLocKey;
    }

    public void setBodyLocKey(String bodyLocKey) {
        this.bodyLocKey = bodyLocKey;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTOVC() {
        return TOVC;
    }

    public void setTOVC(String TOVC) {
        this.TOVC = TOVC;
    }

    public String getPM25() {
        return PM25;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    public String getCHCO() {
        return CHCO;
    }

    public void setCHCO(String CHCO) {
        this.CHCO = CHCO;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getGAS() {
        return GAS;
    }

    public void setGAS(String GAS) {
        this.GAS = GAS;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }


    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(String alertValue) {
        this.alertValue = alertValue;
    }
}

