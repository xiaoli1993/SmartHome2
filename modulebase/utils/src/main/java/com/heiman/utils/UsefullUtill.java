/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.utils;

/**
 * @Author : 张泽晋
 * @Time : 2017/4/22 8:27
 * @Description :
 * @Modify record :
 */

public class UsefullUtill {
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
        var = (long) (((data[offset] & 0xFF) << 56)
                | ((data[offset + 1] & 0xFF) << 48)
                | ((data[offset + 2] & 0xFF) << 40)
                | ((data[offset + 3] & 0xFF) << 32)
                | ((data[offset + 4] & 0xFF) << 24)
                | ((data[offset + 5] & 0xFF) << 16)
                | ((data[offset + 6] & 0xFF) << 8)
                | (data[offset + 7] & 0xFF));
        return var;
    }

    public static String charsToHxString(char[] chars){
        String hxStr = "[";
        for (int i = 0; i < chars.length; i++){
            hxStr = hxStr + " " + Integer.toHexString(chars[i]);
        }
        hxStr = hxStr + "]";
        return hxStr;
    }
    public static String bytesToHxString(byte[] bytes){
        String hxStr = "[";
        for (int i = 0; i < bytes.length; i++){
            hxStr = hxStr + " " + Integer.toHexString(bytes[i]);
        }
        hxStr = hxStr + "]";
        return hxStr;
    }
}
