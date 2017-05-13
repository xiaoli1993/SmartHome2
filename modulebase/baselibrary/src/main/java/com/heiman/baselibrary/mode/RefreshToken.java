package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/4/10 9:47
 * @Description :
 * @Modify record :
 */

public class RefreshToken {
    /**
     * access_token : 新的调用凭证
     * refresh_token : 新的刷新凭证
     * expire_in : 有效期
     */

    private String access_token;
    private String refresh_token;
    private String expire_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }
}
