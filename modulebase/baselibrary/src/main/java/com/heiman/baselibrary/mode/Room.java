package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

/**
 * @Author : 肖力
 * @Time :  2017/5/31 10:22
 * @Description :房间
 * @Modify record :
 */

public class Room {
    /**
     * updateAt : 2017-05-31T10:31:47.354Z
     * createAt : 2017-05-31T10:31:47.354Z
     * objectId : 592e2b1390124f41b8e4621b
     * _region : 1
     * user_id : 1144507844
     * creator : 1144507844
     */
    @Expose(deserialize = true, serialize = false)
    private String updateAt;
    @Expose(deserialize = true, serialize = false)
    private String createAt;
    @Expose(deserialize = true, serialize = false)
    private String objectId;
    @Expose(deserialize = true, serialize = false)
    private int _region;
    @Expose(deserialize = true, serialize = false)
    private int creator;

    /**
     * @param room_name   房间名称
     * @param room_bg_url 背景图片URL
     */
    public Room(String room_name, String room_bg_url) {
        this.room_name = room_name;
        this.room_bg_url = room_bg_url;
    }

    private String room_name;
    private String room_bg_url;

    public String getRoom_bg_url() {
        return room_bg_url;
    }

    public void setRoom_bg_url(String room_bg_url) {
        this.room_bg_url = room_bg_url;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int get_region() {
        return _region;
    }

    public void set_region(int _region) {
        this._region = _region;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
