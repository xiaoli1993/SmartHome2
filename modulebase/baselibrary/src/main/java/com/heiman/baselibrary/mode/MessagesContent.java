package com.heiman.baselibrary.mode;
/**
 * Copyright ©深圳市海曼科技有限公司
 */


import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/22 17:30
 * @Description :
 */
/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/22 17:30
 * @Description :
 */
public class MessagesContent {
    @Expose
    private NotificationBean notification;
    @Expose
    private String zigbeeMac;
    @Expose
    private String plugMac;
    @Expose
    private String time;
    @Expose
    private String gasMac;
    @Expose
    private String redMac;
    @Expose
    private String airMac;
    @Expose
    private MessageBean message;

    public String getGasMac() {
        return gasMac;
    }

    public void setGasMac(String gasMac) {
        this.gasMac = gasMac;
    }

    public String getRedMac() {
        return redMac;
    }

    public void setRedMac(String redMac) {
        this.redMac = redMac;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }

    public String getZigbeeMac() {
        return zigbeeMac;
    }

    public void setZigbeeMac(String zigbeeMac) {
        this.zigbeeMac = zigbeeMac;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public String getPlugMac() {
        return plugMac;
    }

    public void setPlugMac(String plugMac) {
        this.plugMac = plugMac;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAirMac() {
        return airMac;
    }

    public void setAirMac(String airMac) {
        this.airMac = airMac;
    }

    public static class NotificationBean {
        @Expose
        private String title;
        @Expose
        private String body_loc_key;
        @Expose
        private String sound;
        @Expose
        private List<String> body_loc_args;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody_loc_key() {
            return body_loc_key;
        }

        public void setBody_loc_key(String body_loc_key) {
            this.body_loc_key = body_loc_key;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public List<String> getBody_loc_args() {
            return body_loc_args;
        }

        public void setBody_loc_args(List<String> body_loc_args) {
            this.body_loc_args = body_loc_args;
        }
    }


    public static class MessageBean {
        @Expose
        private String electricity;
        @Expose
        private String time;
        @Expose
        private int TVOC;
        @Expose
        private int PM25;
        @Expose
        private int PM10;
        @Expose
        private int AQI;
        @Expose
        private int CHCO;
        @Expose
        private String temp;
        @Expose
        private String humidity;
        @Expose
        private String name;
        @Expose
        private int ispower;
        @Expose
        private boolean onoff;
        @Expose
        private String  SID;

        public String getSID() {
            return SID;
        }

        public void setSID(String SID) {
            this.SID = SID;
        }

        public boolean isOnoff() {
            return onoff;
        }

        public void setOnoff(boolean onoff) {
            this.onoff = onoff;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIspower() {
            return ispower;
        }

        public void setIspower(int ispower) {
            this.ispower = ispower;
        }

        public int getTVOC() {
            return TVOC;
        }

        public void setTVOC(int TVOC) {
            this.TVOC = TVOC;
        }

        public int getPM25() {
            return PM25;
        }

        public void setPM25(int PM25) {
            this.PM25 = PM25;
        }

        public int getPM10() {
            return PM10;
        }

        public void setPM10(int PM10) {
            this.PM10 = PM10;
        }

        public int getAQI() {
            return AQI;
        }

        public void setAQI(int AQI) {
            this.AQI = AQI;
        }

        public int getCHCO() {
            return CHCO;
        }

        public void setCHCO(int CHCO) {
            this.CHCO = CHCO;
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

        public String getElectricity() {
            return electricity;
        }

        public void setElectricity(String electricity) {
            this.electricity = electricity;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
