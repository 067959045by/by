package com.echofeng.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Utils {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String md5(String input)  {
        byte[] bytes = new byte[0];
        try {
            bytes = MessageDigest.getInstance("MD5").digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return printHexBinary(bytes);
    }

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

}
