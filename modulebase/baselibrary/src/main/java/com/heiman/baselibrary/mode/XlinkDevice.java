package com.heiman.baselibrary.mode;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;


/**
 * 作者：肖力
 * 邮箱：554674787@qq.com
 * 时间：2016/8/18 14:52
 */
public class XlinkDevice extends DataSupport {
    private long id;
    private Date date;
//    private List<DataDevice> dataDevice = new ArrayList<DataDevice>();
    private List<SubDevice> subDevice = new ArrayList<SubDevice>();

    private String softwareVer;// 软件版本
    private String timeZone;// 时区
    private String deviceMac;// mac地址
    private String accessKey;// 云智易密码
    private String hardwareVer;// 硬件版本
    private int deviceType;// 设备类型
    private String deviceName;// 设备名字
    private int deviceState;// 是否在线  设备状态 0不在线  1局域网在线 2云端在线
    private boolean isupdevice;// 是否升级
    private int userId;//用户ID
    private int deviceId;//设备ID
    private String productId;//设备PID
    private int bindFlag;// 是否绑定 1表示绑定 0表示未绑定
    private int authFlag;// 1表示分享设备，0表示未分享设备
    private boolean isHome;// 是否家庭设备
    private int homeID;// 是否家庭设备
    private boolean isShared;// 是否是分享权限,默认是可以控制
    private String ownerName;// 分享设备的拥有者
    private String roomName;// 房间名
    private String xDevice;//xDevice
    private String aesKey;//加密秘钥
    private String active_date; // 激活时间
    private String last_login; //最近上线时间
    private String authorize_code;// 认证码
    private String active_code;//激活码
    private String accessAESKey;// AES密码
    private String roomID;//房间ID
    //以下是网关数据
    private int armtype;//撤布防状态
    private int alarmlevel;//报警亮度
    private int soundlevel;//声音亮度
    private int betimer;//报警时长
    private String gwlanguage = "";//设备语言
    private int gwlightlevel;//网关灯亮度
    private int gwlightonoff;//网关灯开关
    private int lgtimer;//网关小夜灯时长


    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getAccessAESKey() {
        return accessAESKey;
    }

    public void setAccessAESKey(String accessAESKey) {
        this.accessAESKey = accessAESKey;
    }

    public String getActive_date() {
        return active_date;
    }

    public void setActive_date(String active_date) {
        this.active_date = active_date;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getAuthorize_code() {
        return authorize_code;
    }

    public void setAuthorize_code(String authorize_code) {
        this.authorize_code = authorize_code;
    }

    public String getActive_code() {
        return active_code;
    }

    public void setActive_code(String active_code) {
        this.active_code = active_code;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(int bindFlag) {
        this.bindFlag = bindFlag;
    }

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public int getHomeID() {
        return homeID;
    }

    public void setHomeID(int homeID) {
        this.homeID = homeID;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

//    public List<DataDevice> getDataDevice() {
//        return dataDevice;
//    }

//    public void setDataDevice(List<DataDevice> dataDevice) {
//        this.dataDevice = dataDevice;
//    }

    public List<SubDevice> getSubDevice() {
        return subDevice;
    }

    public void setSubDevice(List<SubDevice> subDevice) {
        this.subDevice = subDevice;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getHardwareVer() {
        return hardwareVer;
    }

    public void setHardwareVer(String hardwareVer) {
        this.hardwareVer = hardwareVer;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }

    public boolean isupdevice() {
        return isupdevice;
    }

    public void setIsupdevice(boolean isupdevice) {
        this.isupdevice = isupdevice;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public XDevice getxDevice() throws JSONException {
        JSONObject jsonObject = new JSONObject(xDevice);
        return XlinkAgent.JsonToDevice(jsonObject);
    }

    public void setxDevice(String xDevice) {
        this.xDevice = xDevice;
    }


    @Override
    public String toString() {
        return getDeviceName();
    }

    public int getArmtype() {
        return armtype;
    }

    public void setArmtype(int armtype) {
        this.armtype = armtype;
    }

    public int getAlarmlevel() {
        return alarmlevel;
    }

    public void setAlarmlevel(int alarmlevel) {
        this.alarmlevel = alarmlevel;
    }

    public int getSoundlevel() {
        return soundlevel;
    }

    public void setSoundlevel(int soundlevel) {
        this.soundlevel = soundlevel;
    }

    public int getBetimer() {
        return betimer;
    }

    public void setBetimer(int betimer) {
        this.betimer = betimer;
    }

    public String getGwlanguage() {
        return gwlanguage;
    }

    public void setGwlanguage(String gwlanguage) {
        this.gwlanguage = gwlanguage;
    }

    public int getGwlightlevel() {
        return gwlightlevel;
    }

    public void setGwlightlevel(int gwlightlevel) {
        this.gwlightlevel = gwlightlevel;
    }

    public int getGwlightonoff() {
        return gwlightonoff;
    }

    public void setGwlightonoff(int gwlightonoff) {
        this.gwlightonoff = gwlightonoff;
    }

    public int getLgtimer() {
        return lgtimer;
    }

    public void setLgtimer(int lgtimer) {
        this.lgtimer = lgtimer;
    }
}
