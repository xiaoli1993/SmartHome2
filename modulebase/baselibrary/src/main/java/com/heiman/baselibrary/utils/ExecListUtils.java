/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.utils;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.R;
import com.heiman.baselibrary.mode.scene.pLBean.sceneBean.ExecListBean;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/9 8:43
 * @Description : 
 * @Modify record :
 */

public class ExecListUtils {

    /**
     * 根据设备类型和执行的动作获取动作名称一便在APP上显示
     * @param deviceType 设备类型
     * @param execListBean 执行的动作
     * @return
     */
    public static String execListToString(int deviceType, ExecListBean execListBean) {
        String exec = "";

        if (execListBean == null) {
            return exec;
        }
        switch (deviceType) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:  //智能灯泡
                exec = rgbExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT: //温控器
                exec = thermostatExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY: //网关
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW:
                exec = gatewayExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS: //门磁、红外
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                exec = doorsAndPirExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM: //声光报警
                exec = soundAndLightAlarmExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF: //开关
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                exec = onoffExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN: //计量插座
            case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                exec = metrtingPluginExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN: //USB插座、智能插座
            case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                exec = pluginExecListToString(execListBean);
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC: //红外转发、红外转发遥控器
            case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                exec = execListBean.getValue3(); //特点红外码
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY: //继电器模块
                exec = relayExecListToString(execListBean);
                break;
        }
        return exec;
    }

    /**
     * 智能灯泡根据执行动作获得执行动作名称
     * @param execListBean
     * @return
     */
    private static String rgbExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.rgb_off);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.rgb_on);
                }
                break;
            case 1:
                exec = BaseApplication.getMyApplication().getString(R.string.rgb_color_255);
                break;
            case 2:
                exec = BaseApplication.getMyApplication().getString(R.string.rgb_color_65535);
                break;
        }
        return exec;
    }

    /**
     * 根据温控器执行动作获取执行动作名称
     * @param execListBean
     * @return
     */
    private static String thermostatExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.thermostat_off);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.thermostat_on);
                }
                break;
            case 1:
                exec = BaseApplication.getMyApplication().getString(R.string.thermostat_control) + execListBean.getValue();
                break;
        }
        return exec;
    }

    /**
     * 根据网关执行动作获取执行动作名称
     * @param execListBean
     * @return
     */
    private static String gatewayExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.gateway_exec_dis_arm);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.gateway_exec_at_home);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.gateway_exec_go_out);
                }
                break;
            case 1:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.gateway_rgb_off);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.gateway_rgb_on);
                }
                break;
            case 2:
                exec = BaseApplication.getMyApplication().getString(R.string.gateway_rgb_color_255);
                break;
            case 3:
                exec = BaseApplication.getMyApplication().getString(R.string.gateway_rgb_color_65535);
                break;
        }
        return exec;
    }

    /**
     * 根据门磁红外执行动作获得动作名称
     * @param execListBean
     * @return
     */
    private static String doorsAndPirExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.doors_and_pir_dis_arm);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.doors_and_pir_arm);
                }
                break;
        }
        return exec;
    }

    /**
     * 根据声光报警器执行动作获取动作名称
     * @param execListBean
     * @return
     */
    private static String soundAndLightAlarmExecListToString(ExecListBean execListBean) {
        String exec = "";
        if (execListBean.getExec_ep() == 0 && execListBean.getValue() == 1) {
            exec = BaseApplication.getMyApplication().getString(R.string.sound_and_light_alarm_device_alarm);
        }
        return exec;
    }

    /**
     * 根据开关执行动作获取动作名称
     * @param execListBean
     * @return
     */
    private static String onoffExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_one_off);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_one_on);
                }
                break;
            case 1:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_two_off);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_two_on);
                }
                break;
            case 2:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_three_off);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.onoff_three_on);
                }
                break;
        }
        return exec;
    }

    /**
     * 根据计量插座执行动作获取动作名称
     * @param execListBean
     * @return
     */
    private static String metrtingPluginExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.metrting_plugin_off);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.metrting_plugin_on);
                }
                break;
        }
        return exec;
    }

    /**
     * 根据智能插座执行动作获取动作名称
     * @param execListBean
     * @return
     */
    private static String pluginExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_off);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_on);
                }
                break;
            case 1:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_usb_off);
                } else if (execListBean.getValue() == 1){
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_usb_on);
                }
                break;
            case 2:
                if (execListBean.getValue() == 1) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_off_usb_off);
                }
                break;
            case 3:
                if (execListBean.getValue() == 1) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_on_usb_off);
                }
                break;
            case 4:
                if (execListBean.getValue() == 1) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_off_usb_on);
                }
                break;
            case 5:
                if (execListBean.getValue() == 1) {
                    exec = BaseApplication.getMyApplication().getString(R.string.plugin_on_usb_on);
                }
                break;
        }
        return exec;
    }

    /**
     * 根据继电器执行动作获取动作名称
     * @param execListBean
     * @return
     */
    private static String relayExecListToString(ExecListBean execListBean){
        String exec = "";
        switch (execListBean.getExec_ep()) {
            case 0:
                if (execListBean.getValue() == 0) {
                    exec = BaseApplication.getMyApplication().getString(R.string.relay_off);
                } else {
                    exec = BaseApplication.getMyApplication().getString(R.string.relay_on);
                }
                break;
        }
        return exec;
    }

    /**
     * 设置开关设备的打开关闭
     * @param deviceId
     * @param on
     * @return
     */
    public static ExecListBean deviceOnOff(boolean isSubDevice, int deviceId, boolean on) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(isSubDevice ? 0 : 1);
        execListBean.setExec_ep(0);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(on ? 1 : 0);
        return execListBean;
    }

    /**
     * 设置智能灯泡灯光亮度0-255
     * @param deviceId
     * @param value
     * @return
     */
    public static ExecListBean setRGBLight(int deviceId, int value) {
        value = value < 0 ? 0 : value;
        value = value > 255 ? 255 :value;
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(0);
        execListBean.setExec_ep(1);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        return execListBean;
    }

    /**
     * 设置智能灯泡颜色0-65535
     * @param deviceId
     * @param value
     * @param value1
     * @param value2
     * @return
     */
    public static ExecListBean setRGBLightColor(int deviceId, int value, int value1, int value2) {
        value = value < 0 ? 0 : value;
        value = value > 65535 ? 65535 :value;
        value1 = value1 < 0 ? 0 : value1;
        value1 = value1 > 65535 ? 65535 :value1;
        value2 = value2 < 0 ? 0 : value2;
        value2 = value2 > 65535 ? 65535 :value2;
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(0);
        execListBean.setExec_ep(2);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        execListBean.setValue1(value1);
        execListBean.setValue2(value2);
        return execListBean;
    }

    /**
     * 设置温控器的温度值0-100
     * @param deviceId
     * @param value
     * @return
     */
    public static ExecListBean setThermostat(int deviceId, int value) {
        value = value < 0 ? 0 : value;
        value = value > 100 ? 100 :value;
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(0);
        execListBean.setExec_ep(1);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        return execListBean;
    }

    /**
     * 设置网关撤布防状态 0 撤防 1 在家布防 2 外出布防
     * @param deviceId
     * @param value
     * @return
     */
    public static ExecListBean setGatwayArm(int deviceId, int value) {
        value = value < 0 || value > 2 ? 2 : value; //认为value 不是0,1,2时都应设为2外出布防
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(1);
        execListBean.setExec_ep(0);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        return execListBean;
    }

    /**
     * 设置网关灯开关状态
     * @param deviceId
     * @param on
     * @return
     */
    public static ExecListBean setGatewayOnOff(int deviceId, boolean on) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(1);
        execListBean.setExec_ep(1);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(on ? 1 : 0);
        return execListBean;
    }

    /**
     * 设置网关灯亮度0-255
     * @param deviceId
     * @param value
     * @return
     */
    public static ExecListBean setGatewayLight(int deviceId, int value) {
        value = value < 0 ? 0 : value;
        value = value > 255 ? 255 :value;
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(1);
        execListBean.setExec_ep(2);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        return execListBean;
    }

    /**
     * 设置网关灯颜色0-65535
     * @param deviceId
     * @param value
     * @param value1
     * @param value2
     * @return
     */
    public static ExecListBean setGatewayLightColor(int deviceId, int value, int value1, int value2) {
        value = value < 0 ? 0 : value;
        value = value > 65535 ? 65535 :value;
        value1 = value1 < 0 ? 0 : value1;
        value1 = value1 > 65535 ? 65535 :value1;
        value2 = value2 < 0 ? 0 : value2;
        value2 = value2 > 65535 ? 65535 :value2;
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(1);
        execListBean.setExec_ep(3);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(value);
        execListBean.setValue1(value1);
        execListBean.setValue2(value2);
        return execListBean;
    }

    /**
     * 设置开关的开关状态
     * @param deviceId
     * @param exec_ep 0表示一路开关，1表示二路开关，2表示三路开关
     * @param on 开关状态
     * @return
     */
    public static ExecListBean setOnOffState(int deviceId, int exec_ep, boolean on) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(0);
        execListBean.setExec_ep(exec_ep);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(on ? 1 : 0);
        return execListBean;
    }

    /**
     * 设置USB智能插座电源开关状态或USB开关状态
     * @param isSubDevice
     * @param deviceId
     * @param exec_ep 0表示设置电源开关状态，1表示设置USB开关状态
     * @param on
     * @return
     */
    public static ExecListBean setUsbPluginOffOn (boolean isSubDevice, int deviceId, int exec_ep, boolean on) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(isSubDevice ? 0 : 1);
        execListBean.setExec_ep(exec_ep);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(on ? 1 : 0);
        return execListBean;
    }

    /**
     * 设置USB智能插座电源开关状态同时设置USB开关状态
     * @param isSubDevice
     * @param deviceId
     * @param pluginOn
     * @param UsbOn
     * @return
     */
    public static ExecListBean setUsbPluginOffOn(boolean isSubDevice, int deviceId, boolean pluginOn, boolean UsbOn) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(isSubDevice ? 0 : 1);
        execListBean.setExec_ep(UsbOn ? pluginOn ? 5 :4 : pluginOn ? 3 : 2);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue(1);
        return execListBean;
    }

    /**
     * 设置红外转发控制动作
     * @param isSubDevice
     * @param deviceId
     * @param value3 红外转发特征码
     * @return
     */
    public static ExecListBean setRc(boolean isSubDevice, int deviceId, String value3) {
        ExecListBean execListBean = new ExecListBean();
        execListBean.setType(isSubDevice ? 0 : 1);
        execListBean.setExec_ep(0);
        execListBean.setDeviceId(deviceId);
        execListBean.setValue3(value3);
        return execListBean;
    }
}
