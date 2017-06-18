package com.heiman.smarthome.modle;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/6/14 18:58
 * @Description :
 * @Modify record :
 */

public class FcmNotification {
    /**
     * notification : {"title":"Air_237C1A47","body_loc_args":["2016-6-4 11:21:41","数据","deviceMac"],"body_loc_key":"tempup_100","sound":"alarm.mp3"}
     */

    private NotificationBean notification;

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }

    public static class NotificationBean {
        /**
         * title : Air_237C1A47
         * body_loc_args : ["2016-6-4 11:21:41","数据","deviceMac"]
         * body_loc_key : tempup_100
         * sound : alarm.mp3
         */

        private String title;
        private String body_loc_key;
        private String sound;
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
}
