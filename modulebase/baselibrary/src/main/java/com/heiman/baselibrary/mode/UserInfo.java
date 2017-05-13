package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;

import org.litepal.crud.DataSupport;

/**
 * @Author : 肖力
 * @Time :  2017/4/19 9:25
 * @Description :用户信息
 * @Modify record :
 */

public class UserInfo extends DataSupport {

    /**
     * corp_id : 1007d2ada7d4a000
     * region_id : 1001
     * city : 3
     * id : 1144507844
     * is_vaild : true
     * create_date : 2016-08-29T08:32:32.82Z
     * passwd_inited : true
     * age : 0
     * province : 44
     * gender : 1
     * status : 1
     * nickname : heiman_肖力
     * active_date : 2016-08-29T08:33:19.601Z
     * avatar : http://static.heimantech.com/4d1ed668543c746718f7e80fe7f529ae.jpg
     * country : 1
     * authorize_code : 220edcaf87054000
     * source : 2
     * email : 554674787@qq.com
     * address : 福前路84号
     * account : 554674787@qq.com
     */
    @Expose
    private String corp_id;//企业ID
    @Expose
    private int region_id;//所在区域ID
    @Expose
    private String city;//用户所在城市
    @Expose
    private int id;//用户ID
    @Expose
    private boolean is_vaild;//用户账号是否已认证
    @Expose
    private String create_date;//创建时间
    @Expose
    private boolean passwd_inited;//密码是否初始化
    @Expose
    private int age;//	年龄
    @Expose
    private String province;//	用户所在省份
    @Expose
    private int gender;//用户性别, 1为男, 2为女, -1为未知
    @Expose
    private int status;//	用户状态
    @Expose
    private String nickname;//用户昵称
    @Expose
    private String active_date;
    @Expose
    private String avatar;//头像资源地址url
    @Expose
    private String country;//用户所在国家
    @Expose
    private String authorize_code;
    @Expose
    private int source;//用户来源
    @Expose
    private String email;//用户邮箱
    @Expose
    private String phone;//用户手机
    @Expose
    private String address;//用户地址
    @Expose
    private String account;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_vaild() {
        return is_vaild;
    }

    public void setIs_vaild(boolean is_vaild) {
        this.is_vaild = is_vaild;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public boolean isPasswd_inited() {
        return passwd_inited;
    }

    public void setPasswd_inited(boolean passwd_inited) {
        this.passwd_inited = passwd_inited;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getActive_date() {
        return active_date;
    }

    public void setActive_date(String active_date) {
        this.active_date = active_date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAuthorize_code() {
        return authorize_code;
    }

    public void setAuthorize_code(String authorize_code) {
        this.authorize_code = authorize_code;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
