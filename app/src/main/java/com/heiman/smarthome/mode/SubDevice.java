package com.heiman.smarthome.mode;/**
 * Created by hp on 2016/8/18.
 */

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：肖力
 * 邮箱：554674787@qq.com
 * 时间：2016/8/18 14:52
 */
public class SubDevice extends DataSupport {
    private long id;
    private Date date;
    private XlinkDevice wifiDevice;
    private List<DataDevice> subDevice = new ArrayList<DataDevice>();

    public List<DataDevice> getSubDevice() {
        return subDevice;
    }

    private boolean onlineStatus;//在线状态
    private boolean relayOnoff; //开关状态
    private boolean usbOnoff;//USB开关状态
    private boolean rgbOnoff;//RGB开关状态
    private int rgblevel;//亮度
    private int deviceOnoff;//设备状态
    private int deviceType;//设备类型
    private int index;//设备索引
    private String deviceName;//设备名称
    private String deviceState;//设备状态
    private String zigbeeMac;//设备mac地址
    private String temp;//温度
    private String humidity;//湿度
    private int pressure;//压力
    private String TOVC;
    private String PM25;
    private String CHCO;
    private String AQI;
    private String PM10;
    private String GAS;
    private int eh;
    private int sh;
    private int em;
    private int sm;
    private int wf;
    private boolean enable;
    private Date lastDate;

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

    public void setSubDevice(List<DataDevice> subDevice) {
        this.subDevice = subDevice;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public boolean isRelayOnoff() {
        return relayOnoff;
    }

    public void setRelayOnoff(boolean relayOnoff) {
        this.relayOnoff = relayOnoff;
    }

    public boolean isUsbOnoff() {
        return usbOnoff;
    }

    public void setUsbOnoff(boolean usbOnoff) {
        this.usbOnoff = usbOnoff;
    }

    public boolean isRgbOnoff() {
        return rgbOnoff;
    }

    public void setRgbOnoff(boolean rgbOnoff) {
        this.rgbOnoff = rgbOnoff;
    }

    public int getRgblevel() {
        return rgblevel;
    }

    public void setRgblevel(int rgblevel) {
        this.rgblevel = rgblevel;
    }

    public int getDeviceOnoff() {
        return deviceOnoff;
    }

    public void setDeviceOnoff(int deviceOnoff) {
        this.deviceOnoff = deviceOnoff;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getZigbeeMac() {
        return zigbeeMac;
    }

    public void setZigbeeMac(String zigbeeMac) {
        this.zigbeeMac = zigbeeMac;
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

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
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

    public int getEh() {
        return eh;
    }

    public void setEh(int eh) {
        this.eh = eh;
    }

    public int getSh() {
        return sh;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }

    public int getEm() {
        return em;
    }

    public void setEm(int em) {
        this.em = em;
    }

    public int getSm() {
        return sm;
    }

    public void setSm(int sm) {
        this.sm = sm;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getWf() {
        return wf;
    }

    public void setWf(int wf) {
        this.wf = wf;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
