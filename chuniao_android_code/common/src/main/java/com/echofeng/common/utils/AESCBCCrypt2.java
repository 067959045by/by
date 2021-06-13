package com.echofeng.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCBCCrypt2 {

//    private final static String KEY = "X5HCC6sPrfyKJnA4nC3jPPMRQNKPQBmf";
    private final static String KEY = "j9waHdzZibRcXNehrityyc2na5CNGyEz";
//    private final static String IV = "1585695994402965";
    private final static String IV = "5532199547396139";
    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS7_PADDING = "AES/CBC/PKCS7Padding";
    //AES 加密
    private static final String AES = "AES";
    // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final String CHARSET = "UTF-8";
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * AES加密,返回BASE64编码后的加密字符串
     *
     * @param sSrc           -- 待加密内容
     * @return Base64编码后的字符串
     * @throws Exception
     */
    public static String aesEncrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance(CBC_PKCS7_PADDING);
//        final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
//        byte[] bytes = sKey.getBytes(CHARSET);
//        digest.update(bytes, 0, bytes.length);
//        byte[] key = digest.digest();
        byte[] raw = KEY.getBytes(CHARSET);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes(CHARSET));
        if (CBC_PKCS7_PADDING.contains("CBC")) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARSET));
        //此处使用BASE64做转码。
        String encoded = Base64.encodeToString(encrypted, Base64.NO_WRAP);
        return encoded;
    }
    /**
     * AES解密
     * @param sSrc -- 待解密Base64字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String aesDecrypt(String sSrc){
        try {
            byte[] raw = KEY.getBytes(CHARSET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CBC_PKCS7_PADDING);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(CHARSET));
            if (CBC_PKCS7_PADDING.contains("CBC")) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            } else {//ECB模式
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }
            //先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc,Base64.NO_WRAP);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, CHARSET);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * AES解密
     * @param sSrc -- 待解密Base64字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String aesDecrypt(String sSrc,String Key,String Iv){
        try {
            byte[] raw = Key.getBytes(CHARSET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CBC_PKCS7_PADDING);
            IvParameterSpec iv = new IvParameterSpec(Iv.getBytes(CHARSET));
            if (CBC_PKCS7_PADDING.contains("CBC")) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            } else {//ECB模式
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }
            //先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc,Base64.NO_WRAP);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, CHARSET);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }
}
