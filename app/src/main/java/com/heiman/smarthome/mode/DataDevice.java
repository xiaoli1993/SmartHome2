package com.heiman.smarthome.mode;/**
 * Created by hp on 2016/8/18.
 */

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
    private XlinkDevice wifiDevice;
    private SubDevice subDevice;
    private String userid;// 用户ID
    private String userName;// 用户昵称
    private String deviceId;// 设备ID
    private String deviceMac;// 设备mac
    private String subMac;// 子设备mac
    private String subID;//  子设备ID
    private String actionName;// 动作名称
    private String year;//年
    private String month;//月
    private String day;//日
    private String HH;//时
    private String mm;//分
    private String ss;//秒
    private String bodyLocKey;//数据内容
    private String temp;//温度
    private String humidity;//湿度
    private String TOVC;
    private String PM25;
    private String CHCO;
    private String AQI;
    private String PM10;
    private String GAS;


    private String messageID;//消息ID
    private String messageType;//消息类型
    private String notifyType;//通知类型
    private String createDate;//创建时间
    private String isPush;//是否已读
    private String isRead;//是否推送
    private String alertName;//触发告警规则名称
    private String alertValue;//触发告警的数据端点值

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

    public XlinkDevice getWifiDevice() {
        return wifiDevice;
    }

    public void setWifiDevice(XlinkDevice wifiDevice) {
        this.wifiDevice = wifiDevice;
    }

    public SubDevice getSubDevice() {
        return subDevice;
    }

    public void setSubDevice(SubDevice subDevice) {
        this.subDevice = subDevice;
    }

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

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getSubMac() {
        return subMac;
    }

    public void setSubMac(String subMac) {
        this.subMac = subMac;
    }

    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

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

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
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
