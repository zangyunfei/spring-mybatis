package com.zyf.springmybatis.common;

import java.security.MessageDigest;

/**
 * MD5加密算法
 *
 * @author zuohui jiang
 */
public class MD5Util {
    public final static String MD5(String text, String key) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = (text + key).getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5验证方法
     *
     * @param text
     *        明文
     * @param key
     *        密钥
     * @param md5
     *        密文
     * @return true/false
     */
    public static boolean verify(String text, String key, String md5) {
        String md5Text = MD5Util.MD5(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }

    public static void main(String[] args) {
//        System.out.print(MD5Util.MD5("qdbank", "1qaz2wsx3edc"));
    }
}
