package com.heiman.smarthome.smarthomesdk;/**
 * Created by hp on 2016/12/19.
 */

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 13:58
 * @Description :
 */
public class ModuleInfo {
    private String mac;
    private String ModuleIP;
    private String mid;

    public ModuleInfo() {
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModuleIP() {
        return this.ModuleIP;
    }

    public void setModuleIP(String moduleIP) {
        this.ModuleIP = moduleIP;
    }

    public void setMid(String string) {
        this.mid = string;
    }

    public String getMid() {
        return this.mid;
    }
}
