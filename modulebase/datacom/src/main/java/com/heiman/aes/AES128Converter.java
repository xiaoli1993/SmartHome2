/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.aes;


import android.util.Log;

/**
 * @Author : 张泽晋
 * @Time : 2017/4/25 14:10
 * @Description :
 * @Modify record :
 */
public class AES128Converter {

    static {
        try {
            System.loadLibrary("AES128Converter");
        } catch (Exception e) {
            Log.e("AES128Converter", "static initializer: " + e.getMessage());
        }
    }

    public static native int EncryptBuf(byte[] encryptStr, int inStrLength, byte[] output, byte[] AESKey);

    public static native int DecryptBuf(byte[] decryptStr, int inStrLength, byte[] output, byte[] AESKey);
}
