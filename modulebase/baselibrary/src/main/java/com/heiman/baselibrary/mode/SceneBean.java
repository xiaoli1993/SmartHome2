package com.heiman.baselibrary.mode;/**
 * Copyright ©深圳市海曼科技有限公司
 */

import java.util.List;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/1/4 10:31
 * @Description :
 */
public class SceneBean {
    /**
     * sceneID : 2
     * name : 场景
     * timeEnable : 1
     * countTime : 54240
     * time : {"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}
     * load : ["4099.65280","4097.65025","4098.65281"]
     */

    private int sceneID;
    private String name;
    private int timeEnable;
    private int countTime;
    /**
     * month : 0
     * day : 0
     * hour : 14
     * min : 40
     * type : 1
     * wkflag : 255
     */

    private TimeBean time;
    private List<String> load;

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeEnable() {
        return timeEnable;
    }

    public void setTimeEnable(int timeEnable) {
        this.timeEnable = timeEnable;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }

    public List<String> getLoad() {
        return load;
    }

    public void setLoad(List<String> load) {
        this.load = load;
    }

    public static class TimeBean {
        private int month;
        private int day;
        private int hour;
        private int min;
        private int type;
        private int wkflag;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getWkflag() {
            return wkflag;
        }

        public void setWkflag(int wkflag) {
            this.wkflag = wkflag;
        }
    }
}
