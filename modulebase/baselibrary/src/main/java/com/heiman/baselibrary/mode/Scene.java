package com.heiman.baselibrary.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/6 14:05
 * @Description :
 * @Modify record :
 */

public class Scene extends PLBase{
    /**
     * 2.1.1.2.0 : {"sceneID":2,"name":"场景","countTime":54240,"execList":[{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":""}]}
     */

    @SerializedName("2.1.1.2.0")
    private OID Scene;

    public OID getScene() {
        return Scene;
    }

    public void setScene(OID Scene) {
        this.Scene = Scene;
    }

    public static class OID {
        /**
         * sceneID : 2
         * name : 场景
         * countTime : 54240
         * execList : [{"type":0,"deviceId":135113,"exec_ep":1,"value":1,"value1":1,"value2":1,"value3":""}]
         */

        private int sceneID;
        private String name;
        private int countTime;
        private List<ExecListBean> execList;

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

        public int getCountTime() {
            return countTime;
        }

        public void setCountTime(int countTime) {
            this.countTime = countTime;
        }

        public List<ExecListBean> getExecList() {
            return execList;
        }

        public void setExecList(List<ExecListBean> execList) {
            this.execList = execList;
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
             */

            private int type;
            private int deviceId;
            private int exec_ep;
            private int value;
            private int value1;
            private int value2;
            private String value3;

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
        }
    }
}
