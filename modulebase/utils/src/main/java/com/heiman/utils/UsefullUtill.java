/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Author : 张泽晋
 * @Time : 2017/4/22 8:27
 * @Description : 一些常用的方法封装
 * @Modify record :增加md5加密方法 张泽晋 2017/5/25
 * @Modify record:增加Bitmap到圆形Bitmap方法
 * @Modify record:增加图片压缩方法
 * @Modify record:增加判断点击时间间隔是否大于给定时间间隔方法
 */

public class UsefullUtill {
    private static Gson gson = new Gson();
    private static HashMap<Integer,Long> mapViewLastClickTime = new HashMap<>(); //用来记录控件最后一次点击时间

    /**
     * @param var 需要转成byte数组的int类型数据
     * @return data 转换后的byte数组
     */
    public static byte[] intToBytes(int var) {
        byte[] data = new byte[4];
        data[0] = (byte) ((var >> 24) & 0xff);
        data[1] = (byte) ((var >> 16) & 0xff);
        data[2] = (byte) ((var >> 8) & 0xff);
        data[3] = (byte) (var & 0xff);
        return data;
    }

    /**
     * @param var 需要转成byte数组的short类型数据
     * @return data 转换后的byte数组
     */
    public static byte[] shortToBytes(short var) {
        byte[] data = new byte[2];
        data[0] = (byte) ((var >> 8) & 0xff);
        data[1] = (byte) (var & 0xff);
        return data;
    }

    /**
     * @param var 需要转成byte数组的long类型数据
     * @return data 转换后的byte数组
     */
    public static byte[] longToBytes(long var) {
        byte[] data = new byte[8];
        data[0] = (byte) ((var >> 56) & 0xff);
        data[1] = (byte) ((var >> 48) & 0xff);
        data[2] = (byte) ((var >> 40) & 0xff);
        data[3] = (byte) ((var >> 32) & 0xff);
        data[4] = (byte) ((var >> 24) & 0xff);
        data[5] = (byte) ((var >> 16) & 0xff);
        data[6] = (byte) ((var >> 8) & 0xff);
        data[7] = (byte) (var & 0xff);
        return data;
    }

    /**
     * @param data   需要从中转换出int数据的byte数组
     * @param offset 转换的起始位置
     * @return var 转换后得到的int类型数据
     */
    public static int bytesToInt(byte[] data, int offset) {
        int var;
        var = (int) (((data[offset] & 0xFF) << 24)
                | ((data[offset + 1] & 0xFF) << 16)
                | ((data[offset + 2] & 0xFF) << 8)
                | (data[offset + 3] & 0xFF));
        return var;
    }

    /**
     * @param data   需要从中转换出short数据的byte数组
     * @param offset 转换的起始位置
     * @return var 转换后得到的short类型数据
     */
    public static short bytesToShort(byte[] data, int offset) {
        short var;
        var = (short) (((data[offset] & 0xFF) << 8)
                | (data[offset + 1] & 0xFF));
        return var;
    }

    /**
     * @param data   需要从中转换出int数据的byte数组
     * @param offset 转换的起始位置
     * @return var 转换后得到的int类型数据
     */
    public static long bytesToLong(byte[] data, int offset) {
        long var;
        var = (long) ((((long) data[offset] & 0xFF) << 56)
                | (((long) data[offset + 1] & 0xFF) << 48)
                | (((long) data[offset + 2] & 0xFF) << 40)
                | (((long) data[offset + 3] & 0xFF) << 32)
                | (((long) data[offset + 4] & 0xFF) << 24)
                | (((long) data[offset + 5] & 0xFF) << 16)
                | (((long) data[offset + 6] & 0xFF) << 8)
                | ((long) data[offset + 7] & 0xFF));
        return var;
    }

    /**
     * 将charArray转16进制字符串打印用于查看数据
     * @param chars
     * @return
     */
    public static String charsToHxString(char[] chars){
        String hxStr = "[";

        for (int i = 0; i < chars.length; i++){
            if (i == 0){
                hxStr = hxStr + "0x" + Integer.toHexString(chars[i]);
            }else {
                hxStr = hxStr + ", 0x" + Integer.toHexString(chars[i]);
            }
        }

        hxStr = hxStr + "]";
        return hxStr;
    }

    /**
     * 将byteArray转16进制字符串形式用于打印查看数据
     * @param data
     * @return
     */
    public static String bytesToHxString(byte[] data){
        String hxStr = "[";

        for (int i = 0; i < data.length ; i++) {
            if (i == 0){
                hxStr = hxStr + "0x" + Integer.toHexString(bytesToInt(new byte[]{ 0, 0, 0, data[i]},0));
            }else {
                hxStr = hxStr + " ,0x" + Integer.toHexString(bytesToInt(new byte[]{ 0, 0, 0, data[i]},0));
            }
        }

        hxStr = hxStr + "]";
        return hxStr;
    }

    /**
     * 将实体类转换成JSONString
     * @param object
     * @return JSONString
     */
    public static String getJSONStr(Object object){
        String jsonStr = "";

        try {
            jsonStr = gson.toJson(object);
        } catch (Exception e){
            LogUtil.e(e.getMessage());
        }

        return jsonStr;
    }

    /**
     * 将jsonStr解成实体类
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T  getModelFromJSONStr(String jsonStr, Class<T> tClass){
        T t = null;

        try {
            t = gson.fromJson(jsonStr,tClass);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }

        return t;
    }

    /**
     * 将int类型的ip地址转String类型的xxx.xxx.xxx.xxx的ip地址
     * @param ip int类型的ip地址
     * @return ipAddress String类型的xxx.xxx.xxx.xxx的ip地址
     */
    public static String getIp(int ip){
        String ipAddress = "";
        ipAddress = (ip & 0xff)
                + "." + (ip >> 8 & 0xff)
                + "." + (ip >> 16 & 0xff)
                + "." + (ip >> 24 & 0xff);
        LogUtil.e("ipAddress:" + ipAddress);
        return ipAddress;
    }

    /**
     * 获取手机mac地址<br/>
     * 错误返回12个0
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress())) {
                    macAddress = info.getMacAddress().replace(":", "");
                }
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 因为英文单词等自动拆行时不能被拆成两行
     * @param tv 要显示文本的TextView
     * @param rawText 要显示的文本
     * @return 按照tv的宽度作好换行的文本
     */
    public static String autoSplitText(TextView tv , String rawText) {
        Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        return sbNewText.toString();
    }

    /**
     * MD5加密
     * @param string 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(e.getMessage());
        }
        return "";
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap
     *            传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    /**
     * 使用Luban实现图片压缩
     * @param imageFile 要压缩的图片文件
     * @param onCompressListener 压缩图片回调监听
     */
    public static void photoCompress(Context context, File imageFile, OnCompressListener onCompressListener) {
        Luban.get(context)
                .load(imageFile)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(onCompressListener).launch();    //启动压缩
    }

    /**
     * 判断点击时间间隔是否大于给的interval
     * @param viewId 点击的View的id
     * @param interval 给定的点击时间间隔
     * @return
     */
    public static boolean judgeClick(int viewId, long interval){
        boolean valid = false;
        long current = System.currentTimeMillis();
        if (mapViewLastClickTime.containsKey(viewId)) {
            long last = mapViewLastClickTime.get(viewId);
            if ((current - last) > interval) {
                valid = true;
                mapViewLastClickTime.put(viewId, current);
            }
        } else {
            valid = true;
            mapViewLastClickTime.put(viewId, current);
        }
        return valid;
    }

}