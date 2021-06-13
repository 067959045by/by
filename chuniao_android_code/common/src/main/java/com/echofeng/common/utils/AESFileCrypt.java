package com.echofeng.common.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: AESCrypt
 * Author: echo
 * Date: 2019/10/31 14:34
 * Description: AES加密
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class AESFileCrypt {
//    public static final String KEY = "a7RAM5P9yCX6MZcTGSGCsHSy4NrrX2ar";
    public static final String KEY = "4iiSDPQCACPZT2E9iR477bcTankaWhr8";
//    private static final String IV = "9312756593052434";
    private static final String IV = "8209658041411076";
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";
    private static final String TAG = "AESCrypt";


    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";


    /**
     * 转换密钥
     *
     * @param key   二进制密钥
     * @return 密钥
     */
    private static Key toKey(byte[] key){
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }


    /**
     * 解密
     *
     * @param file  待解密文件
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static InputStream decrypt(File file){
        byte[] keyByte = KEY.getBytes();
        Key key = toKey(keyByte);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_MODE, new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key,iv);
            //执行操作
            CipherInputStream cis = new CipherInputStream(new FileInputStream(file), cipher);
            return cis;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密文件
     *
     * @param file
     * @return
     */
    public static byte[] decryptData(File file) {
        byte[] encryptedBytes = FileUtils.file2Bytes(file);
        byte[] result = null ;
        try {
            byte[] sEnCodeFormat = KEY.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(sEnCodeFormat, KEY_ALGORITHM);
            byte[] initParam = IV.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            result = cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
