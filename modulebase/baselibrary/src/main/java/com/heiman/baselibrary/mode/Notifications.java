package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/3/30 18:48
 * @Description :
 * @Modify record :
 */

public class Notifications {
    /**
     * notification : {"title":"WDoor_86AC21","body_loc_args":["2017-3-30 18:46:55"],"body_loc_key":"ALRM_RESUME_17","sound":"message.mp3"}
     * zigbeeMac : 86AC211FC9435000
     */

    private NotificationBean notification;
    @Expose
    private String zigbeeMac;

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

    public static class NotificationBean {
        /**
         * title : WDoor_86AC21
         * body_loc_args : ["2017-3-30 18:46:55"]
         * body_loc_key : ALRM_RESUME_17
         * sound : message.mp3
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
