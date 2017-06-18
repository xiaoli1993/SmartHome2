package com.heiman.baselibrary.http;/**
 * Created by hp on 2016/12/16.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.utils.UsefullUtill;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import com.heiman.smarthome.BaseApplication;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/16 10:05
 * @Description : HTTP获取
 */
public class HttpManage {
    private static HttpManage instance;

    public static String COMPANY_ID = "1007d2ada7d4a000";
    //    public static String COMPANY_ID = "100edcb0237f4200";
    private static String host = "https://console.heiman.cn:443";
    private static final String FEEDBACK_HOST = "http://47.88.192.21:80/";
    public static final int MAX_UPLOAD_IMAGE_FILE_SIZE = 2 * 1024 * 1024;

    public static String getCompanyId() {
        return COMPANY_ID;
    }

    public static void setCompanyId(String companyId) {
        HttpManage.COMPANY_ID = companyId;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        HttpManage.host = host;
    }

    // 注册
    private final String registerUrl = host + "/v2/user_register";
    // 获取验证码
    private final String getTokenCodeUrl = host + "/v2/user_register/verifycode";
    // 登录
    private final String loginUrl = host + "/v2/user_auth";
    // 第三方用户登录
    private final String thirdpartyloginUrl = host + "/v2/user_auth_third";
    // 第三方用户初始化登录密码
    private final String initthirdpwdUrl = host + "/v2/user/{user_id}/init_pwd";
    // 修改昵称
    private final String User = host + "/v2/user/";
    // 绑定第三方
    private final String bindingthirdUrl = host + "/v2/user/{user_id}/bind_third";
    // 使用验证码找回密码密码
    private final String foundbackUrl = host + "/v2/user/password/foundback";
    // 忘记密码
    private final String forgetUrl = host + "/v2/user/password/forgot";
    // 获取用户信息
    private final String getUserInfoUrl = host + "/v2/user/{user_id}";
    // 设置用户扩展属性
    private final String Property = host + "/v2/user/{user_id}/property";
    // 获取某个用户绑定的设备列表。
    private final String subscribeListUrl = host + "/v2/user/%d/subscribe/devices";
    // public final String subscribeListUrl = host + "/v2/user/%d/subscribe/devices?version=%d";
    // 设备管理员分享设备给指定用户
    private final String shareDeviceUrl = host + "/v2/share/device";
    // 用户拒绝设备分享
    private final String denyShareUrl = host + "/v2/share/device/deny";
    // 用户确认设备分享
    private final String acceptShareUrl = host + "/v2/share/device/accept";
    // 管理员（用户）获取所有设备分享请求列表
    private final String shareListUrl = host + "/v2/share/device/list";
    // 设备管理员分享设备给指定用户
    private final String sharedeviceUrl = host + "/v2/upgrade/device";
    // 管理员（用户）获取所有设备分享请求列表
    private final String listsharedeviceUrl = host + "/v2/share/device/list";
    // 设备管理员收回设备分享
    private final String cancelsharedeviceUrl = host + "/v2/share/device/cancel";
    // 管理员或用户删除这条分享记录
    private final String deletesharedeviceUrl = host + "/v2/share/device/delete";
    // 获取设备信息
    private final String getDeviceUrl = host + "/v2/product/{product_id}/device/{device_id}";
    // 订阅设备（待定）
    private final String subscribeUrl = host + "/v2/user/{user_id}/subscribe";
    // 取消订阅设备（待定）
    private final String unsubscribeUrl = host + "/v2/user/{user_id}/unsubscribe";
    // 注册设备信息
    private final String registerDevice = host + "/v2/user/{user_id}/register_device";

    // 获取用户订阅的设备列表
    private final String getsubDevicesUrl = host + "/v2/user/{user_id}/subscribe/devices?version=0";
    // 注册GCM
    private final String gcmregisterUrl = host + "/v2/user/{user_id}/gcm_register";
    // 注销GCM
    private final String gcmunregisterUrl = host + "/v2/user/{user_id}/gcm_unregister";
    // 注销APN
    private final String apnunregisterUrl = host + "/v2/user/{user_id}/apn_unregister";
    // 修改用户信息
    private final String modifyUserUrl = host + "/v2/user/{user_id}";
    // 重置密码
    private final String resetPasswordUrl = host + "/v2/user/password/reset";
    // 刷新Access_Token
    private final String refreshUrl = host + "/v2/user/token/refresh";
    //.管理员或用户删除这条分享记录
    private final String deleteShareUrl = host + "/v2/share/device/delete/{invite_code}";
    // 检查固件版本
    public final String checkUpdateUrl = host + "/v1/user/device/version";
    //    private final String checkUpdateUrl = "http://console.heiman.cn:8887/v1/user/device/version";
    // 固件升级
    public final String upgradeUrl = host + "/v1/user/device/upgrade";
    //    private final String upgradeUrl = "http://console.heiman.cn:8887/v1/user/device/upgrade";
    // 查询设备固件
    private final String newestversionUrl = host + "/v2/upgrade/device/newest_version";
    // 手动升级设备
    private final String updeviceUrl = host + "/v2/upgrade/device";
    // 上传用户头像
    private final String headportraitUrl = host + "/v2/user/avatar/upload?avatarType=jpg";
    // 数据表
    private final String tabletUrl = host + "/v2/data/";
    // 数据表S查询
    private final String datastUrl = host + "/v2/datas/";
    //增加反馈信息
    private final String feedbackInsert = FEEDBACK_HOST + "SmartHome/feedBack/join";
    //查询反馈记录
    private final String feedbackList = FEEDBACK_HOST + "SmartHome/feedBack/list";
    //删除指定反馈记录id的反馈记录
    private final String feedbackDelete = FEEDBACK_HOST + "SmartHome/feedBack/cancel/{id}";
    //上传意见反馈图片
    private final String feedbackUploadPhoto = FEEDBACK_HOST + "SmartHome/feedBack/uploadPictures";

    //家庭功能-->创建家庭
    private final String Home = host + "/v2/home";

    /**
     * code : 5031001
     * msg : service unavailable
     */
    public static HttpManage getInstance() {
        if (instance == null) {
            instance = new HttpManage();
        }
        return instance;
    }


    /**
     * 全局的http代理
     */
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        // 设置网络超时时间
        client.setTimeout(1000 * 10);
        client.setConnectTimeout(1000 * 5);
    }

    /**
     * 获取手机验证码
     *
     * @param user     手机号码/邮箱
     * @param callback 注册监听器
     */
    public void getTokenCode(Context context, String user, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        if (SmartHomeUtils.isEmial(user)) {
            params.put("email", user);
        } else {
            params.put("phone", user);
        }
        params.put("corp_id", COMPANY_ID);
        post(context, getTokenCodeUrl, params, callback);
    }

    /**
     * http 注册接口
     *
     * @param user     用户 手机/邮箱
     * @param name     昵称（别名，仅供后台管理平台观看，对用户来说记住uid和pwd就行）
     * @param pwd      密码
     * @param code     验证码
     * @param language 语言选择 zh-cn中文 en-us 英文
     */
    public void registerUser(Context context, String user,
                             String name, String pwd, String code,
                             String language, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        if (SmartHomeUtils.isEmial(user)) {
            params.put("email", user);
        } else {
            params.put("phone", user);
            params.put("verifycode", code);
        }
        params.put("nickname", name);
        params.put("corp_id", COMPANY_ID);
        params.put("password", pwd);
        params.put("source", "2");
        params.put("local_lang", language);
        post(context, registerUrl, params, callback);
    }


    /**
     * http 邮箱登录接口
     *
     * @param user 用户 邮箱/手机
     * @param pwd  密码
     */
    public void doLogin(Context context, String user, String pwd, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        if (SmartHomeUtils.isEmial(user)) {
            params.put("email", user);
        } else {
            params.put("phone", user);
        }
        params.put("corp_id", COMPANY_ID);
        params.put("password", pwd);
        params.put("resource", "2");
        post(context, loginUrl, params, callback);
    }

    /**
     * 第三方登录
     *
     * @param source       用户来源，根据第三方选择不同的来源
     *                     1	web
     *                     2	Android客户端
     *                     3	IOS客户端
     *                     4	微信用户
     *                     5	QQ用户
     *                     6	微博用户
     *                     10	其它遵循xlink统一身份认证规范的用户来源
     * @param open_id      APP在第三方平台登录成功返回的用户标识
     * @param access_token APP在第三方平台登录成功返回的用户凭证
     * @param name         第三方用户昵称
     * @param plugin_id    所属应用插件ID
     * @param callback
     */
    public void doThirdLogin(Context context, String source, String open_id, String access_token, String name, String plugin_id,
                             final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("corp_id", COMPANY_ID);
        params.put("source", source);
        params.put("open_id", open_id);
        params.put("access_token", access_token);
        params.put("name", name);
        params.put("resource", 2 + "");
        params.put("plugin_id", plugin_id);
        post(context, thirdpartyloginUrl, params, callback);
    }

    /**
     * 第三方用户登入后，初始化用于登录XLINK平台的登录密码,每个第三方用户只能初始化一次密码。
     *
     * @param password 登录密码
     * @param callback
     */
    public void initThirPwd(Context context, String password, final ResultCallback callback) {
        String url = initthirdpwdUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", password);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, headers, params, callback);

    }

    /**
     * 第三方绑定
     *
     * @param open_id      APP在第三方平台登录成功返回的用户标识
     * @param access_token APP在第三方平台登录成功返回的用户凭证
     * @param source       用户来源，根据第三方选择不同的来源
     *                     1	web
     *                     2	Android客户端
     *                     3	IOS客户端
     *                     4	微信用户
     *                     5	QQ用户
     *                     6	微博用户
     *                     10	其它遵循xlink统一身份认证规范的用户来源
     * @param callback
     */
    public void onBindthird(Context context, String open_id, String access_token, String source, final ResultCallback callback) {
        String url = bindingthirdUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("open_id", open_id);
        params.put("access_token", access_token);
        params.put("source", source);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, headers, params, callback);

    }

    /**
     * 11.获取用户详细信息
     */
    public void getUserInfo(Context context, final ResultCallback callback) {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, url, headers, callback);
    }



    /**
     * .重置密码
     */
    public void resetPassword(Context context, String newPasswd, String oldPasswd, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("old_password", oldPasswd);
        params.put("new_password", newPasswd);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, resetPasswordUrl, headers, params, callback);
    }

    /**
     * http 忘记密码
     *
     * @param mail 用户 邮箱
     */
    public void forgetbyEmailPasswd(Context context, String mail, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", mail);
        params.put("corp_id", COMPANY_ID);
        post(context, forgetUrl, params, callback);

    }

    /**
     * http 忘记密码
     *
     * @param phone        用户 手机 /邮箱
     * @param code         验证码
     * @param new_password 新密码
     */
    public void forgetbyPhonePasswd(Context context, String phone, String code, String new_password, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("verifycode", code);
        params.put("new_password", new_password);
        params.put("corp_id", COMPANY_ID);
        post(context, foundbackUrl, params, callback);
    }

    /**
     * .修改用户信息
     */
    public void modifyUser(Context context, String nickname, final ResultCallback callback) {
        String url = modifyUserUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("nickname", nickname);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, url, headers, params, callback);
    }

    /**
     * onGCM 用户注册GCM推送服务
     *
     * @param appid        app推送ID
     * @param device_token GCM离线推送凭证
     * @param callback     重设监听器
     */
    public void onGCM(Context context, String appid, String device_token, final ResultCallback callback) {
        String url = gcmregisterUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appid);
        params.put("device_token", device_token);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, url, headers, params, callback);
    }

    /**
     * onDeleteGCM 用户取消GCM推送服务
     *
     * @param appid    app推送ID
     * @param callback 重设监听器
     */
    public void onDeleteGCM(Context context, String appid, final ResultCallback callback) {
        String url = gcmunregisterUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appid);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, url, headers, params, callback);
    }

    /**
     * onDeleteAPN 用户取消APN推送服务
     *
     * @param appid    app推送ID
     * @param callback 重设监听器
     */
    public void onDeleteAPN(Context context, String appid, final ResultCallback callback) {
        String url = apnunregisterUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appid);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, url, headers, params, callback);
    }

    /**
     * http //.管理员（用户）获取所有设备分享请求列表
     */
    public void getShareList(Context context, final ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, shareListUrl, headers, callback);
    }


    /**
     * .设备分享
     * 用户可以将设备分享给其他用户，与其他用户共同拥有设备，共同控制设备，分享设备的用户为设备的管理者，被分享者为设备的普通用户。
     * 只有设备的管理者才可分享设备给其他用户
     * <p>
     * 1.设备管理者向其他用户发出设备分享
     * 2.系统产生一则设备分享记录到设备管理者和被分享用户的消息列表中，消息将在一定时间后失效。
     * 3.被分享用户这从消息列表获知这一则设备分享消息，可选择【接受】或【拒绝】别人分享的设备。
     * 4.设备管理者可用在被分享用户【接受】或【拒绝】之前可以【取消】分享这个分享设备的消息，此时分享记录即被设为失效。
     * 5.被分享用户【接受】了分享，与设备管理者共同拥有这一个分享的设备。
     * <p>
     * 设备分享的方式有三种：
     * 通过用户ID进行分享
     * 二维码分享
     * 邮件方式分享
     *
     * @param user     分享给谁；在进行二维码或者邮箱分享的时候，对方ID不确定，只需拿到分享码即可。可以为对方手机号或者邮箱号。
     * @param deviceId 所要分享的设备ID
     * @param mode     分享方式：
     *                 app	string	通过用户ID分享
     *                 qrcode	string	二维码方式分享
     *                 email	string	邮件方式分享
     *                 back      "invite_code" : "分享ID"
     */
    public void shareDevice(Context context, String user, int deviceId, String mode, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", user);
        params.put("expire", "7200");
        params.put("mode", mode);
        params.put("device_id", deviceId + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, shareDeviceUrl, headers, params, callback);
    }


    /**
     * 用户拒绝设备分享
     *
     * @param inviteCode 分享ID
     */
    public void denyShare(Context context, String inviteCode, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("invite_code", inviteCode);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, denyShareUrl, headers, params, callback);
    }

    /**
     * 用户确认设备分享
     *
     * @param inviteCode 分享ID
     */
    public void acceptShare(Context context, String inviteCode, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("invite_code", inviteCode);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, acceptShareUrl, headers, params, callback);
    }


    /**
     * .管理员或用户删除这条分享记录
     */
    public void deleteSharedevice(Context context, String invite_code, final ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, deletesharedeviceUrl + "/" + invite_code, headers, callback);

    }

    /**
     * .撤销分享设备指令
     *
     * @param invite_code "分享ID"
     */
    public void cancelShareDevice(Context context, String invite_code, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("invite_code", invite_code);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, cancelsharedeviceUrl, headers, params, callback);

    }


    /**
     * .设备管理员收回设备分享
     *
     * @param user      对方手机号或者邮箱号
     * @param device_id 设备ID
     * @param mode      分享方式：
     *                  <p>
     *                  "app": app方式分享
     *                  <p>
     *                  "qrcode": 二维码方式分享
     *                  <p>
     *                  "email": 邮件方式分享
     *                  back      "invite_code" : "分享ID"
     */
    public void shareDevice(Context context, String user, String device_id, String mode, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("device_id", device_id);
        params.put("user", user);
        params.put("expire", "7200");
        params.put("mode", mode);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, sharedeviceUrl, headers, params, callback);
    }

    /**
     * .管理员（用户）获取所有设备分享请求列表
     * back :
     * invite_code	是	分享ID
     * from_id	是	分享者ID，一般都是设备管理员的ID。
     * from_user	是	分享者帐号，一般都是设备管理员的ID。
     * to_id	是	分享给谁
     * to_user	是	分享给谁的帐号
     * device_id	是	设备ID
     * state	是	分享状态;{
     * pending	string	等待接收
     * accept	string	已接收
     * deny	string	拒绝
     * overtime	string	超时
     * cancel	string	已取消
     * invalid	string	无效的请求
     * }
     * create_date	是	分享产生时间
     * expire_date	是	分享过期时间
     */
    public void getlistSharedevice(Context context, final ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, listsharedeviceUrl, headers, callback);
    }

    /**
     * 订阅设备
     */
    public void subscribe(Context context, String productId, int deviceId, final ResultCallback callback) {
        String url = subscribeUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("product_id", productId);
        params.put("device_id", deviceId + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, headers, params, callback);
    }

    /**
     * 取消订阅设备
     *
     * @param deviceId 设备ID
     */
    public void unsubscribe(Context context, int deviceId, final ResultCallback callback) {
        String url = unsubscribeUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("device_id", deviceId + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, headers, params, callback);
    }


    /**
     * 获取用户订阅的设备列表
     */
    public void getSubscribe(Context context, final ResultCallback callback) {
        String url = getsubDevicesUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        BaseApplication.getLogger().v(url + "\n" + BaseApplication.getMyApplication().getAccessToken());
        get(context, url, headers, callback);
    }

    /**
     * 设置用户扩展属性
     * <p>
     * 用户可以设置自定义扩展属性，扩展属性为Key-Value结构，用户扩展属性限制最多为10个。
     *
     * @param key   扩展属性key值
     * @param value 扩展属性value值
     */
    public void setProperty(Context context, String key, String value, final ResultCallback callback) {
        String url = Property.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put(key, value);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, headers, params, callback);
    }

    /**
     * .获取用户扩展属性
     */
    public void getOneProperty(Context context, String key, final ResultCallback callback) {
        String url = Property.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, url + "/" + key, headers, callback);
    }

    /**
     * 设置消息已读
     * <p>
     * 通过本接口将消息从未读设置为已读，可同时设置多条消息。
     *
     * @param data 消息ID
     */
    public void setMessageRead(Context context, String data, final ResultCallback callback) {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        // params.put("device_id", device_id);
        post(context, url + "/message_read", entity, callback, headers);

    }

    /**
     * 设置子设备AVS联动
     *
     * @param product_id 产品地址
     * @param deviceid   设备ID
     * @param data       数据
     * @param callback
     */
    public void setSubDevice(Context context, String product_id, int deviceid, String data,
                             final ResultCallback callback) {

        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, host + "/v2/product/" + product_id + "/device/"
                + deviceid + "/subdevices", entity, callback, headers);

    }

    /**
     * 获取设备信息
     *
     * @param deviceId 设备ID
     */
    public void getDevice(Context context, String productIdd, int deviceId, final ResultCallback callback) {
        String url = getDeviceUrl.replace("{device_id}", deviceId + "");
        url = url.replace("{product_id}", productIdd);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, url, headers, callback);
    }

    /**
     * 设置设备名称
     *
     * @param productIdd 设备PID
     * @param deviceId   设备ID
     * @param Devicename 设备名称
     */
    public void setDevicename(Context context, String productIdd, int deviceId,
                              String Devicename, final ResultCallback callback) {
        String url = getDeviceUrl.replace("{device_id}", deviceId + "");
        url = url.replace("{product_id}", productIdd);
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", Devicename);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, url, headers, params, callback);
    }

    /**
     * http //.获取某个用户绑定的设备列表。
     */
    public void getSubscribeList(Context context, int uid, int versionid, final ResultCallback callback) {
        String url = String.format(subscribeListUrl, uid);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, url, headers, callback);
    }

    /**
     * http //.删除某个用户绑定的设备列表。
     */
    public void deleteShare(Context context, String inviteCode, final ResultCallback callback) {
        String url = deleteShareUrl.replace("{invite_code}", inviteCode);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, url, headers, callback);
    }

    /**
     * .修改手机号或者邮箱 在用户已登录的情况下，通过手机或邮箱验证码更换或认证手机号码。
     * 1.请求发送验证码
     *
     * @param user 手机或邮箱
     */
    public void onGetCodeChage(Context context, String user, final ResultCallback callback) {

        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        String Urlphoenoremail;
        if (SmartHomeUtils.isEmial(user)) {
            params.put("email", user);
            Urlphoenoremail = "/email_verifycode";
        } else {
            params.put("phone", user);
            Urlphoenoremail = "/phone/sms_verifycode";
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + Urlphoenoremail, params, headers, callback);
    }

    /**
     * .修改手机号或者邮箱 在用户已登录的情况下，通过手机或邮箱验证码更换或认证手机号码。
     * 2.通过验证码完成修改
     *
     * @param user       手机或邮箱
     * @param verifycode 验证码
     * @param password   用户登录密码
     * @param callback
     */
    public void onChagePhoneorEmail(Context context, String user, String verifycode, String password, final ResultCallback callback) {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        Map<String, String> params = new HashMap<String, String>();
        String Urlphoenoremail;
        if (SmartHomeUtils.isEmial(user)) {
            params.put("email", user);
            Urlphoenoremail = "/email";
        } else {
            params.put("phone", user);
            Urlphoenoremail = "/phone";
        }
        params.put("verifycode", verifycode);
        params.put("password", password);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + Urlphoenoremail, params, headers, callback);
    }


    /**
     * .刷新凭证
     */
    public void onRefresh(Context context, final ResultCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("refresh_token", BaseApplication.getMyApplication().getRefresh_token());
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, refreshUrl, params, headers, callback);
    }

    /**
     * .刷新凭证
     */
    public void onRefreshs(Context context, final ResultCallback callback) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("refresh_token", BaseApplication.getMyApplication().getRefresh_token());
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, refreshUrl, params.getJsonEntity(), callback, headers);
    }

    /**
     * .用户查询设备固件最新版本
     *
     * @param product_id 产品ID
     * @param device_id  设备ID
     */
    public void onVersion(Context context, String product_id, String device_id, final ResultCallback callback) {
        RequestParams params = new RequestParams();
        params.put("product_id", product_id);
        params.put("device_id", device_id);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, newestversionUrl, params.getJsonEntity(), callback, headers);
    }

    /**
     * .用户手动升级设备
     *
     * @param product_id 产品ID
     * @param device_id  设备ID
     */
    public void onUpdevice(Context context, String product_id, String device_id, final ResultCallback callback) {
        RequestParams params = new RequestParams();
        params.put("product_id", product_id);
        params.put("device_id", device_id);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, updeviceUrl, params.getJsonEntity(), callback, headers);
    }

    /**
     * .创建数据表/查询
     * {
     * "offset":"请求列表的偏移量",
     * "limit":"请求数量",
     * "filter":["字段A","字段B"],
     * "query":
     * {
     * "filed1":{"$in":["字段值","字段值"]},
     * "filed3":{"$lt":"字段值"}
     * },
     * "order":
     * {
     * "filed1":"desc",
     * "filed2":"asc"
     * }
     * }
     * offset	　否｜　从某个偏移量开始请求，默认为0
     * limit	否	请求的条目数量，默认为10
     * filter	否	字段过滤，可以指定返回结果列表的字段
     * query	否	查询条件，可以根据不同字段加上不同的比较指令来查询，支持比较指令包含如下
     * $in：包含于该列表任意一个值
     * $lt：小于该字段值
     * $lte：小于或等于字段值
     * $gt：大于该字段值
     * $gte：大于或等于该字段值
     * order	否	可以指定通过某字段排序，desc降序，asc升序
     *
     * @param data      数据内容
     * @param tableName 表名称
     */
    public void postData(Context context, String data, String tableName, final ResultCallback callback) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, tabletUrl + tableName, entity, callback, headers);
        BaseApplication.getLogger().i("Access-Token:" + BaseApplication.getMyApplication().getAccessToken() + "\ndata:" + data + "\nURL:" + tabletUrl + tableName + "/");
    }

    /**
     * .创建数据表/查询
     * {
     * "offset":"请求列表的偏移量",
     * "limit":"请求数量",
     * "filter":["字段A","字段B"],
     * "query":
     * {
     * "filed1":{"$in":["字段值","字段值"]},
     * "filed3":{"$lt":"字段值"}
     * },
     * "order":
     * {
     * "filed1":"desc",
     * "filed2":"asc"
     * }
     * }
     * offset	　否｜　从某个偏移量开始请求，默认为0
     * limit	否	请求的条目数量，默认为10
     * filter	否	字段过滤，可以指定返回结果列表的字段
     * query	否	查询条件，可以根据不同字段加上不同的比较指令来查询，支持比较指令包含如下
     * $in：包含于该列表任意一个值
     * $lt：小于该字段值
     * $lte：小于或等于字段值
     * $gt：大于该字段值
     * $gte：大于或等于该字段值
     * order	否	可以指定通过某字段排序，desc降序，asc升序
     *
     * @param data      数据内容
     * @param tableName 表名称
     */
    public void ckData(Context context, String data, String tableName, final ResultCallback callback) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, datastUrl + tableName, entity, callback, headers);
    }

    /**
     * .更新数据表
     *
     * @param data      数据内容
     * @param tableName 表名称
     */
    public void putData(Context context, String data, String tableName, String object_id, final ResultCallback callback) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, tabletUrl + tableName + "/" + object_id, entity, callback, headers);
        BaseApplication.getLogger().i("Access-Token:" + BaseApplication.getMyApplication().getAccessToken() + "\ndata:" + data + "\nURL:" + tabletUrl + tableName + "/" + object_id);
    }

    /**
     * 获取数据
     *
     * @param tableName 表名称
     * @param object_id 数据表类型
     */
    public void getData(Context context, String tableName, String object_id,
                        final ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, tabletUrl + tableName + "/" + object_id, headers, callback);
    }

    /**
     * 删除数据表
     *
     * @param tableName 表名称
     * @param object_id 数据表类型
     */
    public void deleteData(Context context, String tableName, String object_id,
                           final ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, tabletUrl + tableName + "/" + object_id, headers, callback);
    }

    /**
     * .上传头像
     *
     * @param bitmap ""
     */
    public void uploadHeadportrait(Context context, Bitmap bitmap, final ResultCallback callback) {
        byte[] datas = SmartHomeUtils.getBitmapByte(bitmap);
        ByteArrayEntity entity = new ByteArrayEntity(datas);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, headportraitUrl, entity, callback, headers);
    }

    /**
     * 查询所有ID的数据
     *
     * @param offset      偏移量
     * @param limit       个数
     * @param device      设备集合
     * @param create_date 创建时间
     * @param callback
     * @throws JSONException
     */
    public void GetMessagesID(Context context, String offset,
                              String limit, JSONArray device,
                              String create_date,
                              final ResultCallback callback) throws JSONException {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        RequestParams params = new RequestParams();
        params.put("offset", offset);
        params.put("limit", limit);

        JSONObject order = new JSONObject();
        order.put("create_date", "desc");
        params.put("order", order);

        if (device != null) {
            JSONObject query = new JSONObject();
            JSONObject from = new JSONObject();
            JSONObject id = new JSONObject();
            query.put("create_date", id);
            id.put("$gte", create_date);
            query.put("from", from);
            from.put("$in", device);
            params.put("query", query);
        }

        Map<String, String> headers = new HashMap<String, String>();
        BaseApplication.getLogger().d(url + "/messages" + "\n" + params.getStringData());
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + "/messages", params.getJsonEntity(), callback, headers);
    }

    /**
     * 查询所有ID的数据
     *
     * @param offset      偏移量
     * @param limit       个数
     * @param device      设备集合
     * @param create_date 创建时间
     * @param queryCreate 时间请求
     * @param callback
     * @throws JSONException
     */
    public void getMessagesID(Context context, String offset,
                              String limit, JSONArray device,
                              String queryCreate,
                              String create_date,
                              final ResultCallback callback) throws JSONException {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        RequestParams params = new RequestParams();
        params.put("offset", offset);
        params.put("limit", limit);

        JSONObject order = new JSONObject();
        order.put("create_date", "desc");
        params.put("order", order);

        if (device != null) {
            JSONObject query = new JSONObject();
            JSONObject from = new JSONObject();
            JSONObject id = new JSONObject();
            if (!SmartHomeUtils.isEmptyString(create_date)) {
                query.put("create_date", id);
                id.put(queryCreate, create_date);
            }
            query.put("from", from);
            from.put("$in", device);
            params.put("query", query);
        }

        Map<String, String> headers = new HashMap<String, String>();
        BaseApplication.getLogger().d(url + "/messages" + "\n" + params.getStringData());
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + "/messages", params.getJsonEntity(), callback, headers);
    }

    /**
     * 注册设备
     *
     * @param product_id
     * @param mac
     * @param name
     * @param callback
     */
    public void registerDevice(Context context, String product_id, String mac, String name, final ResultCallback callback) {

        String url = registerDevice.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
//        Map<String, String> params = new HashMap<String, String>();
        RequestParams params = new RequestParams();
        params.put("product_id", product_id);
        params.put("mac", mac);
        params.put("name", name);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url, params.getJsonEntity(), callback, headers);
        BaseApplication.getLogger().i("url:" + url + "\tAccessToken:" + BaseApplication.getMyApplication().getAccessToken()
                + "product_id:" + product_id + "mac:" + mac + "name:" + name);
    }

    /**
     * 获取消息列表
     *
     * @param offset
     * @param limit
     * @param device
     * @param is_pushhs
     * @param callback
     * @throws JSONException
     */
    public void GetMessages(Context context, String offset,
                            String limit, JSONArray device,
                            boolean is_pushhs,
                            final ResultCallback callback) throws JSONException {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        RequestParams params = new RequestParams();
        params.put("offset", offset);
        params.put("limit", limit);

        JSONObject order = new JSONObject();
        order.put("create_date", "desc");
        params.put("order", order);

        if (device != null) {
            JSONObject query = new JSONObject();
            JSONObject from = new JSONObject();
            JSONObject is_push = new JSONObject();
            JSONArray is_pushs = new JSONArray();
            query.put("from", from);
            query.put("is_push", is_push);
            is_push.put("$in", is_pushs);
            is_pushs.put(is_pushhs);
            from.put("$in", device);
            params.put("query", query);
        }

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + "/messages", params.getJsonEntity(), callback, headers);

    }

    /**
     * 自定义获取消息列表
     *
     * @param params   请求字段
     * @param callback
     * @throws JSONException
     */
    public void GetMessages(Context context, RequestParams params,
                            final ResultCallback callback) throws JSONException {
        String url = getUserInfoUrl.replace("{user_id}", BaseApplication.getMyApplication().getAppid() + "");
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, url + "/messages", entity, callback, headers);
    }

    /**
     * 增加反馈记录
     *
     * @param userId         用户id
     * @param content        反馈内容
     * @param label          反馈标签
     * @param contact        联系方式
     * @param image          图片的下载地址
     * @param resultCallback
     */
    public void feedbackInsert(Context context, String userId, String content, String label, String contact, String image, final ResultCallback resultCallback) {
        HashMap<String, String> params = new HashMap<>();

        if (!TextUtils.isEmpty(userId)) {
            params.put("userId", userId);
        }
        params.put("content", content);
        params.put("label", label);

        if (!TextUtils.isEmpty(contact)) {

            if (SmartHomeUtils.isEmial(contact)) {
                params.put("email", contact);
            } else {
                params.put("phone", contact);
            }
        }

        if (!TextUtils.isEmpty(image)) {
            params.put("image", image);
        }
        String json = UsefullUtill.getJSONStr(params);
        BaseApplication.getLogger().e("json:" + json);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        headers.put(AsyncHttpClient.HEADER_CONTENT_TYPE, "application/json;charset=utf-8");
        put(context, feedbackInsert, entity, resultCallback, headers);
    }

    /**
     * 查询意见反馈记录
     *
     * @param userId         用户id
     * @param offset         偏移
     * @param limit          查询条数
     * @param resultCallback
     */
    public void feedbackList(Context context, String userId, int offset, int limit, final ResultCallback resultCallback) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("filter", new String[]{"feed_back.id", "feed_back.user_id", "feed_back.user_name", "feed_back.content", "feed_back.Image", "feed_back.label", "feed_back.create_time"});
        HashMap<String, Object> userIdMap = new HashMap<>();
        userIdMap.put("$eq", userId);
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("feed_back.user_id", userIdMap);
        params.put("query", queryMap);
        String json = UsefullUtill.getJSONStr(params);
        BaseApplication.getLogger().e("json:" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            BaseApplication.getLogger().e("jsonObject:" + jsonObject);
        } catch (JSONException e) {
            BaseApplication.getLogger().e(e.getMessage());
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        headers.put(AsyncHttpClient.HEADER_CONTENT_TYPE, "application/json;charset=utf-8");
        post(context, feedbackList, entity, resultCallback, headers);
    }

    /**
     * 删除意见反馈信息
     *
     * @param feedbackId     意见反馈id
     * @param resultCallback
     */
    public void feedbackDelete(Context context, String feedbackId, final ResultCallback resultCallback) {
        String url = feedbackDelete.replace("{id}", feedbackId);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        headers.put(AsyncHttpClient.HEADER_CONTENT_TYPE, "application/json;charset=utf-8");
        delete(context, url, headers, resultCallback);
    }

    /**
     * @param file 需要上传的文件
     * @return 图片在服务器的存储路径
     */
    public String feedbackUploadPhoto(File file) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        try {
            URL url = new URL(feedbackUploadPhoto);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + "utf-8" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                BaseApplication.getLogger().e("response code:" + res);
                if (res == 200) {
                    BaseApplication.getLogger().e("request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    BaseApplication.getLogger().e("result : " + result);
                } else {
                    BaseApplication.getLogger().e("request error");
                }
            }
        } catch (MalformedURLException e) {
            BaseApplication.getLogger().e(e.getMessage());
        } catch (IOException e) {
            BaseApplication.getLogger().e(e.getMessage());
        }
        return result;
    }

    //    public void checkUpdate(String deviceId,final ResultCallback callback){
//        String url = checkUpdateUrl;
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("deviceid",deviceId);
//        Header[] headers = new Header[3];
//        String data = new Gson().toJson(map);
//        Map<String,String> header = new HashMap<String, String>();
//        // AccessID
//        header.put("X-AccessId", HttpAgent.UPDATE_ACCESS_ID);
//        header.put("X-ContentMD5", HttpAgent.MD5(data));
//        header.put("X-Sign", HttpAgent.MD5(HttpAgent.UPDATE_SECRET_KEY + HttpAgent.MD5(data)));
//        post(url, header, map, callback);
//    }
//
//    public void upgrade(String deviceId,final ResultCallback callback){
//        String url = upgradeUrl;
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("deviceid",deviceId);
//        Header[] headers = new Header[3];
//        String data = new Gson().toJson(map);
//        Map<String,String> header = new HashMap<String, String>();
//        // AccessID
//        header.put("X-AccessId", HttpAgent.UPDATE_ACCESS_ID);
//        header.put("X-ContentMD5", HttpAgent.MD5(data));
//        header.put("X-Sign", HttpAgent.MD5(HttpAgent.UPDATE_SECRET_KEY + HttpAgent.MD5(data)));
//        post(url, header, map, callback);
//    }
//=========================================================================================
    private void post(Context context, String url, HttpEntity entity, ResultCallback handler, Map<String, String> headers) {
        Header[] headersdata = map2Header(headers);
        client.post(context, url, headersdata, entity, "application/json", handler);
    }

    private void post(Context context, String url, Map<String, String> params, ResultCallback callback) {
        // 请求entity
        StringEntity entity = params2StringEntity(params);
        client.post(context, url, entity, "application/json", callback);
    }

    private void get(Context context, String url, Map<String, String> headers, ResultCallback callback) {
        Header[] headersdata = map2Header(headers);
        client.get(context, url, headersdata, null, callback);
    }

    private void delete(Context context, String url, com.loopj.android.http.RequestParams params, Map<String, String> headers, ResultCallback callback) {
        Header[] headersdata = map2Header(headers);
        client.delete(context, url, headersdata, params, callback);
    }

    private void delete(Context context, String url, Map<String, String> headers, ResultCallback callback) {
        Header[] headersdata = map2Header(headers);
        client.delete(context, url, headersdata, null, callback);
    }

    private void post(Context context, String url, Map<String, String> headers, Map<String, String> params, ResultCallback callback) {
        // 请求entity
        StringEntity entity = params2StringEntity(params);
        Header[] headersdata = map2Header(headers);
        client.post(context, url, headersdata, entity, "application/json", callback);
    }

    private void put(Context context, String url, HttpEntity entity, ResultCallback handler, Map<String, String> headers) {
        Header[] headersdata = map2Header(headers);
        client.put(context, url, headersdata, entity, "application/json", handler);
    }

    private void put(Context context, String url, Map<String, String> headers, Map<String, String> params, ResultCallback callback) {
        // 请求entity
        StringEntity entity = params2StringEntity(params);
        Header[] headersdata = map2Header(headers);
        client.put(context, url, headersdata, entity, "application/json", callback);
    }

    private StringEntity params2StringEntity(Map<String, String> params) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(new Gson().toJson(params), "UTF-8");
        } catch (Exception e) {
        }
        return entity;
    }

    private Header[] map2Header(Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        Header[] headersdata = new Header[headers.size()];
        int i = 0;
        for (String key : headers.keySet()) {
            headersdata[i] = new XHeader(key, headers.get(key));
            i++;
        }
        return headersdata;
    }

    public static abstract class ResultCallback<T> extends TextHttpResponseHandler {
        Type mType;
        private Gson mGson;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
            mGson = new Gson();
        }

        @Override
        public void onFailure(int code, Header[] headers, String msg, Throwable throwable) {
            if (code > 0) {
                try {
                    ErrorEntity errorEntity = mGson.fromJson(msg, ErrorEntity.class);
                    onError(headers, errorEntity.error);
                } catch (Exception e) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.error.setMsg(throwable.getMessage());
                    errorEntity.error.setCode(HttpConstant.PARAM_NETIO_ERROR);
                    onError(headers, errorEntity.error);
                }
            } else {
                ErrorEntity errorEntity = new ErrorEntity();
                errorEntity.error.setMsg(throwable.getMessage());
                errorEntity.error.setCode(HttpConstant.PARAM_NETIO_ERROR);
                onError(headers, errorEntity.error);
            }
        }

        @Override
        public void onSuccess(int code, Header[] headers, String msg) {
//            BaseApplication.getLogger().json(msg);
            if (mType == String.class) {
                onSuccess(code, (T) msg);
            } else {
                T o = mGson.fromJson(msg, mType);
                onSuccess(code, o);
            }
        }


        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            System.out.println(superclass);
            if (superclass instanceof Class) {
                System.out.println(superclass);
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Header[] headers, Error error);

        public abstract void onSuccess(int code, T response);
    }


    public static class Error {
        private int code;
        private String msg;

        public void setCode(int code) {
            this.code = code;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    private static class ErrorEntity {
        public Error error;

        public ErrorEntity() {
            error = new Error();
        }
    }


    /********************************************************************
     ********************************************************************
     **********************     Home管理    *****************************
     ********************************************************************
     ********************************************************************/

    /**
     * 创建家庭
     *
     * @param name     "home的名称"
     * @param callback
     */
    public void createHome(Context context, String name, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("name", name);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home, params.getJsonEntity(), callback, headers);
    }

    /**
     * 修改家庭名字
     *
     * @param homeid   家庭ID
     * @param name     家庭名字
     * @param callback
     */
    public void changeHomeName(Context context, String homeid, String name, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("name", name);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, Home + "/" + homeid, params.getJsonEntity(), callback, headers);
        BaseApplication.getLogger().i("uri:" + Home + homeid
                + "\nAccess-Token:" + BaseApplication.getMyApplication().getAccessToken()
                + "\nname:" + name);
    }

    /**
     * 删除家庭名字
     *
     * @param homeid   家庭ID
     * @param callback
     */
    public void deleteHome(Context context, String homeid, ResultCallback callback) {

//        RequestParams params = new RequestParams();
//        params.put("name", name);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, Home + "/" + homeid, headers, callback);
    }

    /**
     * 邀请  Home c成员 只有超级管理员和管理员才能添加成员
     *
     * @param account     用户注册的账号
     * @param role        成员的角色类型
     * @param authority   对设备的控制权限
     * @param expire_time 成员的到期时间
     * @param mode        邀请方式
     * @param homeid      家庭ID
     * @param callback
     */
    public void userInviteHome(Context context, String account, String role, String authority, String expire_time, String mode, String homeid, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("role", role);
        params.put("authority", authority);
        params.put("expire_time", expire_time);
        params.put("mode", mode);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/user_invite", params.getJsonEntity(), callback, headers);
    }

    /**
     * 用户通过本接口可以查看已接收到的Home邀请的所有记录列表。
     *
     * @param userid   用户ID
     * @param callback
     */
    public void getUserInviteeHomeList(Context context, String userid, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "s/invitee_list?user_id=" + userid, headers, callback);
    }

    /**
     * 用户获取发出的邀请记录列表。
     *
     * @param userid   用户ID
     * @param callback
     */
    public void getUserInviterHomeList(Context context, String userid, ResultCallback callback) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "s/inviter_list?user_id=" + userid, headers, callback);
    }

    /**
     * 接收到home管理员发送的邀请后，用户响应邀请
     *
     * @param invite_id 邀请ID
     * @param homeid    家庭ID
     * @param callback
     */
    public void userAcceptHome(Context context, String invite_id, String homeid, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("invite_id", invite_id);
//        post2(Home + homeid + "/user_accept", params.getJsonEntity(), callback, getHeaders());
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/user_accept", params.getJsonEntity(), callback, headers);
    }

    /**
     * 接收到home管理员发送的邀请后，用户响应邀请
     *
     * @param invite_id 邀请ID
     * @param reason    用户拒绝分享的原因
     * @param homeid    家庭ID
     * @param callback
     */
    public void userDenyHome(Context context, String invite_id, String reason, String homeid, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("invite_id", invite_id);
        if (reason != null && reason.equals("")) {
            params.put("reason", reason);
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/user_accept", params.getJsonEntity(), callback, headers);
    }

    /**
     * 超级管理员能删除管理员,超级管理员和管理员能删除其他成员。除了创建者外，任何成员能够自行退出Home。
     *
     * @param homeid   家庭ID
     * @param callback
     */
    public void deleteHomeUser(Context context, String homeid, String userid, ResultCallback callback) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, Home + "/" + homeid + "/user/" + userid, headers, callback);

    }

    /**
     * 超级管理员能修改管理员,超级管理员和管理员才能修改其他成员。
     *
     * @param homeid      家庭ID
     * @param expire_time home成员的角色类型
     * @param expire_time home成员的到期时间
     * @param callback
     */
    public void changeHomeUser(Context context, String homeid, String role, String expire_time, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("role", role);
        params.put("expire_time", expire_time);
//        put(Home + homeid, params.getJsonEntity(), callback, getHeaders());
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, Home + "/" + homeid, params.getJsonEntity(), callback, headers);
    }

    /**
     * 用户获取Home列表
     *
     * @param callback
     */
    public void getHomeList(Context context, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "s?user_id=" + BaseApplication.getMyApplication().getAppid() + "", headers, callback);
    }

    /**
     * home的管理员把设备加入到home中。
     *
     * @param device_id 设备ID
     * @param authority 对设备的控制权限，R可读，W可写，RW可读可写
     * @param sub_role  用户和设备的订阅关系；0：管理员；1：普通用户
     * @param homeid    家庭ID
     * @param callback
     */
    public void addHomeDevice(Context context, String device_id, String authority, String sub_role, String homeid, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("device_id", device_id);
        params.put("authority", authority);
        params.put("sub_role", sub_role);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/user_accept", params.getJsonEntity(), callback, headers);
    }

    /**
     * home的管理员把设备从home中移除。
     *
     * @param homeid   家庭ID
     * @param deviceid 设备ID
     * @param callback
     */
    public void deleteHomeDevice(Context context, String homeid, String deviceid, ResultCallback callback) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, Home + "/" + homeid + "/device/" + deviceid, headers, callback);

    }

    /**
     * home的成员可以获取home的设备列表
     *
     * @param homeid   家庭ID
     * @param callback
     */
    public void GetDeviceHomeList(Context context, String homeid, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "/" + homeid + "/devices", headers, callback);
    }

    /**
     * 管理员及以上才能修改其他home成员对设备的操作权限。
     *
     * @param homeid    家庭ID
     * @param userid    home成员的用户Id
     * @param deviceid  设备ID
     * @param authority 对设备的控制权限，R可读，W可写，RW可读可写；
     * @param callback
     */
    public void changeHomeDevicePermission(Context context, String homeid, String userid, String deviceid, String authority, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("user_id", userid);
        params.put("device_id", deviceid);
        params.put("authority", authority);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        put(context, Home + "/" + homeid + "/device_permission", params.getJsonEntity(), callback, headers);

    }

    /**
     * home的管理员可以为home添加扩展属性
     *
     * @param data     {
     *                 "{key}":{
     *                 "name":"扩展属性的名称",
     *                 "value":"扩展属性的值"
     *                 }
     *                 "{key}":{
     *                 "name":"扩展属性的名称",
     *                 "value":"扩展属性的值"
     *                 }
     *                 }
     *                 <p>
     *                 注意：扩展属性字段{key}不得包含小数点、空字符，不能以美元符号（$）开头。
     * @param homeid   家庭ID
     * @param callback
     */
    public void setHomeProperty(Context context, String data, String homeid, ResultCallback callback) {

        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/property", entity, callback, headers);
    }

    /**
     * home的成员可以获取设备扩展属性列表。
     *
     * @param homeid   家庭ID
     * @param callback
     */
    public void getHomeProperty(Context context, String homeid, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "/" + homeid + "/property", headers, callback);
    }

    /**
     * 超级管理员管理员可以获取home所有成员的设备权限信息，其他成员能获取自己对应的设备权限信息。
     *
     * @param homeid   家庭ID
     * @param userid   用户id。如果为空，则返回所有home成员的设备权限信息
     * @param callback
     */
    public void getHomeDeviceAuthority(Context context, String homeid, String userid, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, Home + "s/" + homeid + "/device_authority?user_id=" + userid, headers, callback);
    }

    /**
     * 用户通过本接口删除自己邀请或者邀请自己的记录。
     *
     * @param id_list  ["记录ID1","记录ID2"...]
     * @param callback
     */
    public void deleteInvitationHome(Context context, JSONArray id_list, ResultCallback callback) {

        com.loopj.android.http.RequestParams params = new com.loopj.android.http.RequestParams();
        params.put("id_list", id_list);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        delete(context, Home + "s/invitation/delete", params, headers, callback);

    }

    /**
     * 邀请者可以取消邀请动作
     *
     * @param invite_id 邀请ID
     * @param homeid    家庭ID
     * @param callback
     */
    public void cancelHomeInvite(Context context, String invite_id, String homeid, ResultCallback callback) {

        RequestParams params = new RequestParams();
        params.put("invite_id", invite_id);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        post(context, Home + "/" + homeid + "/invite_cancel", params.getJsonEntity(), callback, headers);
    }

    /**
     * 通过本接口可以获取任意用户的公开信息，此接口无需任何权限
     *
     * @param userid   用户id。
     * @param callback
     */
    public void getOpenInfo(Context context, String userid, ResultCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Access-Token", BaseApplication.getMyApplication().getAccessToken());
        get(context, User + userid + "/open_info", headers, callback);
    }

    /***********************************************************
     * ************************** end ***************************
     ***********************************************************/

}
