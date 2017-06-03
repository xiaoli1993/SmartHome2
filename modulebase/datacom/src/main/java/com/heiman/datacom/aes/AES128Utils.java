package com.heiman.datacom.aes;

import com.heiman.aes.AES128Converter;
import com.heiman.datacom.base64.Base64;


/**
 * @Author : 肖力
 * @Time :  2017/4/26 8:58
 * @Description : 海曼AES128加密 Base64编码处理
 * @Modify record :
 */

public class AES128Utils {
    /**
     * AES128加密，进行BASE64编码
     *
     * @param inputStr  数据字符
     * @param AESKeyStr 加密字符串，16字节
     * @return 返回加密后数据
     * @throws Exception
     */
    public static String HmEncrypt(String inputStr, String AESKeyStr) throws Exception {
        byte[] inputCharArray = inputStr.getBytes();
        byte[] encyptCharArray = new byte[inputCharArray.length + 16];
        int len = AES128Converter.EncryptBuf(inputCharArray, inputCharArray.length, encyptCharArray, AESKeyStr.getBytes());
        byte[] chars = new byte[len];
        System.arraycopy(encyptCharArray, 0, chars, 0, len);
        return "^" + new String(Base64.encode(chars), "UTF-8") + "&";
    }

    /**
     * AES128解密，BASE64解码
     *
     * @param inputStr  加密后数据字符
     * @param AESKeyStr 加密字符串，16字节
     * @return 返回解密后数据
     * @throws Exception
     */
    public static String HmDecrypt(String inputStr, String AESKeyStr) throws Exception {
        byte[] encrypted1 = Base64.decode(inputStr.substring(1, inputStr.length() - 1).getBytes());
        byte[] chars = new byte[encrypted1.length];
        System.arraycopy(encrypted1, 0, chars, 0, encrypted1.length);
        byte[] decrypt = new byte[chars.length + 16];
        int len = AES128Converter.DecryptBuf(chars, chars.length, decrypt, AESKeyStr.getBytes());
        return new String(decrypt, 0, len, "UTF-8");
    }

    /**
     * AES128解密，BASE64解码
     *
     * @param inputStr  加密后数据字符
     * @param AESKeyStr 加密字符串，16字节
     * @return 返回解密后数据
     * @throws Exception
     */
    public static byte[] HmbyeDecrypt(String inputStr, String AESKeyStr) throws Exception {
        byte[] encrypted1 = Base64.decode(inputStr.substring(1, inputStr.length() - 1).getBytes());
        byte[] chars = new byte[encrypted1.length];
        System.arraycopy(encrypted1, 0, chars, 0, encrypted1.length);
        byte[] decrypt = new byte[chars.length + 16];
        int len = AES128Converter.DecryptBuf(chars, chars.length, decrypt, AESKeyStr.getBytes());
        return decrypt;
    }

}
