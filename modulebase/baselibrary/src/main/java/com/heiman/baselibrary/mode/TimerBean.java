package com.heiman.baselibrary.mode;/**
 * Copyright ©深圳市海曼科技有限公司
 */

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/1/3 15:56
 * @Description :
 */
public class TimerBean {

    /**
     * type : week
     * timerFlag : 2
     * timer1 : {"hour":18,"min":55,"PL":{"onoff":true}}
     * wkFlag : 255
     * timer2 : {"hour":15,"min":59,"PL":{"onoff":false}}
     */

    private TimerCmdBean timerCmd;
    /**
     * timerCmd : {"type":"week","timerFlag":2,"timer1":{"hour":18,"min":55,"PL":{"onoff":true}},"wkFlag":255,"timer2":{"hour":15,"min":59,"PL":{"onoff":false}}}
     * enable : true
     * timerId : 0
     */

    private boolean enable;
    private int timerId;

    public TimerCmdBean getTimerCmd() {
        return timerCmd;
    }

    public void setTimerCmd(TimerCmdBean timerCmd) {
        this.timerCmd = timerCmd;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getTimerId() {
        return timerId;
    }

    public void setTimerId(int timerId) {
        this.timerId = timerId;
    }

    public static class TimerCmdBean {
        private String type;
        private int timerFlag;
        /**
         * hour : 18
         * min : 55
         * PL : {"onoff":true}
         */

        private Timer1Bean timer1;
        private int wkFlag;
        /**
         * hour : 15
         * min : 59
         * PL : {"onoff":false}
         */

        private Timer2Bean timer2;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTimerFlag() {
            return timerFlag;
        }

        public void setTimerFlag(int timerFlag) {
            this.timerFlag = timerFlag;
        }

        public Timer1Bean getTimer1() {
            return timer1;
        }

        public void setTimer1(Timer1Bean timer1) {
            this.timer1 = timer1;
        }

        public int getWkFlag() {
            return wkFlag;
        }

        public void setWkFlag(int wkFlag) {
            this.wkFlag = wkFlag;
        }

        public Timer2Bean getTimer2() {
            return timer2;
        }

        public void setTimer2(Timer2Bean timer2) {
            this.timer2 = timer2;
        }

        public static class Timer1Bean {
            private int hour;
            private int min;
            /**
             * onoff : true
             */

            private PLBean PL;

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

            public PLBean getPL() {
                return PL;
            }

            public void setPL(PLBean PL) {
                this.PL = PL;
            }

            public static class PLBean {
                private boolean onoff;

                public boolean isOnoff() {
                    return onoff;
                }

                public void setOnoff(boolean onoff) {
                    this.onoff = onoff;
                }
            }
        }

        public static class Timer2Bean {
            private int hour;
            private int min;
            /**
             * onoff : false
             */

            private PLBean PL;

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

            public PLBean getPL() {
                return PL;
            }

            public void setPL(PLBean PL) {
                this.PL = PL;
            }

            public static class PLBean {
                private boolean onoff;

                public boolean isOnoff() {
                    return onoff;
                }

                public void setOnoff(boolean onoff) {
                    this.onoff = onoff;
                }
            }
        }
    }
}
