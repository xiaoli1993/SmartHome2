package com.heiman.baselibrary.mode;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/20 13:55
 * @Description :
 */
public class Home extends DataSupport {
    /**
     * id : home 的 Id
     * name : home 的名称
     * user_list : [{"user_id":"用户 Id","role":"角色类型","expire_time":"到期的时间"}]
     * creator : 创建者 Id
     * create_time : 创建时间
     * version : 数据的版本号
     */

    private String id;
    private String name;
    private String creator;
    private String create_time;
    private String version;
    @Expose
    private String update_time;//最后一次修改时间
    private List<UserListBean> user_list;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<UserListBean> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListBean> user_list) {
        this.user_list = user_list;
    }

    public static class UserListBean {
        /**
         * user_id : 用户 Id
         * role : 角色类型
         * expire_time : 到期的时间
         */

        private String user_id;
        private String role;
        @Expose
        private String expire_time;
        @Expose
        private String email;//邮箱
        @Expose
        private String phone;//手机
        @Expose
        private String nickname;//昵称
        @Expose
        private String avatar;//URL

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }
    }
}
