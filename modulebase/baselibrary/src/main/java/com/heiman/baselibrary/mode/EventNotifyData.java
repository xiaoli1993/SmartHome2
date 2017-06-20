package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/3/30 18:47
 * @Description :
 * @Modify record :
 */

public class EventNotifyData {
    /**
     * index : 1
     * value : {"notification":{"title":"WDoor_86AC21","body_loc_args":["2017-3-30 18:46:55"],"body_loc_key":"ALRM_RESUME_17","sound":"message.mp3"},"zigbeeMac":"86AC211FC9435000"}
     * msg : {"notification":{"title":"WDoor_86AC21","body_loc_args":["2017-3-30 18:46:55"],"body_loc_key":"ALRM_RESUME_17","sound":"message.mp3"},"zigbeeMac":"86AC211FC9435000"}
     */

    private int index;
    private String value;
    private String msg;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
