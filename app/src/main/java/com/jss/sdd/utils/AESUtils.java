package com.jss.sdd.utils;


import org.apache.xmlbeans.impl.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 128bit 加密解密工具类
 * Created by ibm on 2018/7/12.
 */
public class AESUtils {
    //日志
    //private static Logger logger = Logger.getLogger(AESUtils.class);

    public static final String KEY = "jingkeyunwang201";
    public static final String IV = "jingkeyunwang201";

    /*******************************************************************
     * AES加密算法
     * @param sSrc  原始字符串
     * @author moyun
     * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     ********************************************************************/
    public static String Encrypt(String sSrc, String sKey) throws Exception {

        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        String encode = new String(Base64.encode(encrypted));
        System.out.println("encode: " + encode);
        return encode;//此处使用BAES64做转码功能，同时能起到2次
    }

    /*******************************************************************
     * AES加密算法
     * @param sSrc  原始字符串
     * @author moyun
     * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     ********************************************************************/
    public static String AesEncrypt(String sSrc) throws Exception {
            //logger.info("【AESUtils】【AesEncrypt】 AesEncrypt : " + sSrc);
            try {
                    byte[] raw = KEY.getBytes();
                    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
                    IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                    byte[] encrypted = cipher.doFinal(sSrc.getBytes());
                    String encode = new String(Base64.encode(encrypted));
                    System.out.println("encode: " + encode);
                    return encode;//此处使用BAES64做转码功能，同时能起到2次
            } catch (Exception e) {
                   // logger.info("【AESUtils】【AesEncrypt】 Exception:" + e.getMessage());
                    return "";
            }
    }


    /*******************************************************************
     * AES解密算法
     * @param sSrc 密码字符串
     * @author moyun
     ********************************************************************/
    public static String Decrypt(String sSrc, String sKey) throws Exception {

        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("ASCII");

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(sSrc.getBytes());//先用bAES64解密
        try {
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            System.out.print(e.toString());
            return null;
        }
    }


    /*******************************************************************
     * AES解密算法
     * @param sSrc 密码字符串
     * @author moyun
     ********************************************************************/
    public static String AesDecrypt(String sSrc) throws Exception {
            //logger.info("【AESUtils】【AesDecrypt】 AesDecrypt : " + sSrc);
            byte[] raw = KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc.getBytes());//先用bAES64解密
            try {
                    byte[] original = cipher.doFinal(encrypted1);
                    String originalString = new String(original);
                    return originalString;
            } catch (Exception e) {
                  //  logger.info("【AESUtils】【AesDecrypt】 Exception:" + e.getMessage());
                    return "";
            }
    }
    public static void main(String[] args) throws Exception {
            //String pwd = "eK3U9bEf1531375979883";  （admin）BTxnb889   (twitter) eK3U9bEf  (staff) hK3U9bDf  (agent)LK3DFbEg
            String pwd =   "3821608272;234568" + Long.valueOf(System.currentTimeMillis()) ;//"13821608272;234568" eK3U9bEf 5HE9jOyl
            String epwd = Encrypt(pwd, "jingkeyunwang201");
            System.out.println("result:" + epwd);
            //System.out.println(Decrypt(epwd, "dufy20170329java"));eK3U9bEf 13821608272;234568
            //System.out.println(epwd);MZEuyexG   SK3U9bDG
            //System.out.println(Decrypt(epwd, "jingkeyunwang201"));
            //System.out.println(AesDecrypt(epwd));
            String temp="RANEr6GUY3sF6JaKVEa3T8obB5tolqtNTzCpLYWVnW4=";
            System.out.println("" + Long.valueOf(System.currentTimeMillis()));
    }

}
