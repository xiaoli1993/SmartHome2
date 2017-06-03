package com.heiman.baselibrary.manage;

import com.heiman.baselibrary.mode.DataDevice;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/26 10:19
 * @Description :
 * @Modify record :
 */

public class DiaryManage {


    /**
     * 获取设备ID记录
     *
     * @param userid
     * @param deviceId
     * @param year
     * @param month
     * @param day
     * @param limit
     * @param offset
     * @return
     */
    public List<DataDevice> getDeviceDiary(String userid, String deviceId, String year, String month, String day, int limit, int offset) {
        List<DataDevice> dataDeviceList = DataSupport.where("userid = ? and deviceId = ? and year = ? and month = ? and day = ?", userid, deviceId, year + "", month + "", day + "").limit(limit).offset(offset).find(DataDevice.class);
        return dataDeviceList;
    }

    /**
     * 根据时间获取设备记录
     *
     * @param wifiMac
     * @param subMac
     * @param year
     * @param month
     * @param day
     * @return
     */
    public List<DataDevice> getDeviceDiary(String userid, String wifiMac, String subMac, String year, String month, String day, int limit, int offset) {
        List<DataDevice> dataDeviceList = DataSupport.where("userid = ? and deviceMac = ? and subMac = ? and year = ? and month = ? and day = ?", userid, wifiMac, subMac, year + "", month + "", day + "").limit(limit).offset(offset).find(DataDevice.class);
        return dataDeviceList;
    }

    /**
     * 获取所有报警记录
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void getDeviceDiary(String userid, String year, String month, String day, FindMultiCallback findMultiCallback) {
        final List<DataDevice> dataDeviceList;
        DataSupport.where("userid = ? and alertName = ? and year = ? and month = ? and day = ?", "gcm notification", userid, year + "", month + "", day + "").findAsync(DataDevice.class).listen(findMultiCallback);
    }

    /**
     * 获取所有报警记录
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void getDeviceDiary(String userid, String year, String month, String day, int limit, int offset, FindMultiCallback findMultiCallback) {
        final List<DataDevice> dataDeviceList;
        DataSupport.where("userid = ? and alertName = ? and year = ? and month = ? and day = ?", "gcm notification", userid, year + "", month + "", day + "").limit(limit).offset(offset).findAsync(DataDevice.class).listen(findMultiCallback);
    }
}
