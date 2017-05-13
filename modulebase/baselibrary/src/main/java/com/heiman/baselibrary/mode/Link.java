package com.heiman.baselibrary.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/6 13:59
 * @Description :
 * @Modify record :
 */

public class Link extends PLBase{
    /**
     * 2.1.1.3.0 : {"linkID":2,"name":"联动","enable":true,"conList":{"type":0,"deviceid":135113,"condition":2,"value":1,"con_ep":2,"time":{"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}},"checkList":[{"type":0,"deviceId":135113,"check":2,"check_ep":1,"value":1,"time":{"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}}],"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":"","sceneID":1,"delay":122}]}
     */

    @SerializedName("2.1.1.3.0")
    private OID Link;

    public OID getLink() {
        return Link;
    }

    public void setLink(OID Link) {
        this.Link = Link;
    }

    public static class OID {
        /**
         * linkID : 2
         * name : 联动
         * enable : true
         * conList : {"type":0,"deviceid":135113,"condition":2,"value":1,"con_ep":2,"time":{"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}}
         * checkList : [{"type":0,"deviceId":135113,"check":2,"check_ep":1,"value":1,"time":{"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}}]
         * execList : [{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":"","sceneID":1,"delay":122}]
         */

        private int linkID;
        private String name;
        private boolean enable;
        private ConListBean conList;
        private List<CheckListBean> checkList;
        private List<ExecListBean> execList;

        public int getLinkID() {
            return linkID;
        }

        public void setLinkID(int linkID) {
            this.linkID = linkID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public ConListBean getConList() {
            return conList;
        }

        public void setConList(ConListBean conList) {
            this.conList = conList;
        }

        public List<CheckListBean> getCheckList() {
            return checkList;
        }

        public void setCheckList(List<CheckListBean> checkList) {
            this.checkList = checkList;
        }

        public List<ExecListBean> getExecList() {
            return execList;
        }

        public void setExecList(List<ExecListBean> execList) {
            this.execList = execList;
        }

        public static class ConListBean {
            /**
             * type : 0
             * deviceid : 135113
             * condition : 2
             * value : 1
             * con_ep : 2
             * time : {"month":0,"day":0,"hour":14,"min":40,"type":1,"wkflag":255}
             */

            private int type;
            private int deviceid;
            private int condition;
            private int value;
            private int con_ep;
            private TimeBean time;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDeviceid() {
                return deviceid;
            }

            public void setDeviceid(int deviceid) {
                this.deviceid = deviceid;
            }

            public int getCondition() {
                return condition;
            }

            public void setCondition(int condition) {
                this.condition = condition;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getCon_ep() {
                return con_ep;
            }

            public void setCon_ep(int con_ep) {
                this.con_ep = con_ep;
            }

            public TimeBean getTime() {
                return time;
            }

            public void setTime(TimeBean time) {
                this.time = time;
            }

            public static class TimeBean {
                /**
                 * month : 0
                 * day : 0
                 * hour : 14
                 * min : 40
                 * type : 1
                 * wkflag : 255
                 */

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

        public static class CheckListBean {
            /**
             * type : 0
             * deviceId : 135113
             * check : 2
             * check_ep : 1
             * value : 1
             * time : {"s_hour":0,"s_min":0,"e_hour":14,"e_min":40,"wkflag":255}
             */

            private int type;
            private int deviceId;
            private int check;
            private int check_ep;
            private int value;
            private TimeBeanX time;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(int deviceId) {
                this.deviceId = deviceId;
            }

            public int getCheck() {
                return check;
            }

            public void setCheck(int check) {
                this.check = check;
            }

            public int getCheck_ep() {
                return check_ep;
            }

            public void setCheck_ep(int check_ep) {
                this.check_ep = check_ep;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public TimeBeanX getTime() {
                return time;
            }

            public void setTime(TimeBeanX time) {
                this.time = time;
            }

            public static class TimeBeanX {
                /**
                 * s_hour : 0
                 * s_min : 0
                 * e_hour : 14
                 * e_min : 40
                 * wkflag : 255
                 */

                private int s_hour;
                private int s_min;
                private int e_hour;
                private int e_min;
                private int wkflag;

                public int getS_hour() {
                    return s_hour;
                }

                public void setS_hour(int s_hour) {
                    this.s_hour = s_hour;
                }

                public int getS_min() {
                    return s_min;
                }

                public void setS_min(int s_min) {
                    this.s_min = s_min;
                }

                public int getE_hour() {
                    return e_hour;
                }

                public void setE_hour(int e_hour) {
                    this.e_hour = e_hour;
                }

                public int getE_min() {
                    return e_min;
                }

                public void setE_min(int e_min) {
                    this.e_min = e_min;
                }

                public int getWkflag() {
                    return wkflag;
                }

                public void setWkflag(int wkflag) {
                    this.wkflag = wkflag;
                }
            }
        }

        public static class ExecListBean {
            /**
             * type : 0
             * deviceId : 135113
             * exec_ep : 1
             * value : 1
             * value1 : 1
             * value2 : 1
             * value3 :
             * sceneID : 1
             * delay : 122
             */

            private int type;
            private int deviceId;
            private int exec_ep;
            private int value;
            private int value1;
            private int value2;
            private String value3;
            private int sceneID;
            private int delay;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(int deviceId) {
                this.deviceId = deviceId;
            }

            public int getExec_ep() {
                return exec_ep;
            }

            public void setExec_ep(int exec_ep) {
                this.exec_ep = exec_ep;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getValue1() {
                return value1;
            }

            public void setValue1(int value1) {
                this.value1 = value1;
            }

            public int getValue2() {
                return value2;
            }

            public void setValue2(int value2) {
                this.value2 = value2;
            }

            public String getValue3() {
                return value3;
            }

            public void setValue3(String value3) {
                this.value3 = value3;
            }

            public int getSceneID() {
                return sceneID;
            }

            public void setSceneID(int sceneID) {
                this.sceneID = sceneID;
            }

            public int getDelay() {
                return delay;
            }

            public void setDelay(int delay) {
                this.delay = delay;
            }
        }
    }
}
