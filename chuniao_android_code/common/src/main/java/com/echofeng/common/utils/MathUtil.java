package com.echofeng.common.utils;

import java.math.BigDecimal;

/**
 * ProjectName: NBN
 * ClassName: MathUtil
 * Description:
 * Author: RAMON
 * CreateDate: 2020/7/15 10:34
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
public class MathUtil {
    /**
     * 加
     *
     * @param a
     * @param b
     * @return
     */
    public static double add(double a, double b) {
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);

        BigDecimal res = aB.add(bB);
        return res.doubleValue();
    }

    /**
     * 减
     *
     * @param a
     * @param b
     * @return
     */
    public static double subtract(double a, double b) {
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.subtract(bB);
        return res.doubleValue();
    }
    /**
     * 减
     *
     * @param a
     * @param b
     * @return
     */
    public static double subtract(BigDecimal a, BigDecimal b) {
        BigDecimal res = a.subtract(b);
        return res.doubleValue();
    }

    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static double multiply(double a, double b) {
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.multiply(bB);
        return Double.parseDouble(NumberUtils.big2(res.doubleValue()));
    }

    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static double multiplyFull(double a, double b) {
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.multiply(bB);
        return res.doubleValue();
    }
    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static double multiplyFull(BigDecimal a, double b) {
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = a.multiply(bB);
        return res.doubleValue();
    }
    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static double multiplyFull(BigDecimal a, BigDecimal b) {
        BigDecimal res = a.multiply(b);
        return res.doubleValue();
    }

    /**
     * 除以 （四舍五入保留2位）
     *
     * @param a
     * @param b
     * @return
     */
    public static double divide(long a, long b) {
        if (a==0.0||b==0.0){
            return 0.0;
        }
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.divide(bB, 8, BigDecimal.ROUND_HALF_UP);
        return res.doubleValue();
    }  /**
     * 除以 （四舍五入保留2位）
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        BigDecimal res = a.divide(b, 8, BigDecimal.ROUND_HALF_UP);
        return res;
    }

    /**
     * 除以 （四舍五入保留2位）
     *
     * @param a
     * @param b
     * @return
     */
    public static double divide(double a, double b) {
        if (a==0.0||b==0.0){
            return 0.0;
        }
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.divide(bB, 8, BigDecimal.ROUND_HALF_UP);
        return res.doubleValue();
    }

    /**
     * 除以
     *
     * @param a
     * @param b
     * @param scale 四舍五入 位数
     * @return
     */
    public static double divide(double a, double b, int scale) {
        if (a==0.0||b==0.0){
            return 0.0;
        }
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.divide(bB, scale, BigDecimal.ROUND_HALF_UP);
        return res.doubleValue();
    }

    /**
     * 除以
     *
     * @param a
     * @param b
     * @param scale 有效位数
     * @param mode  位数规则
     * @return
     */
    public static double divide(double a, double b, int scale, int mode) {
        if (a==0.0||b==0.0){
            return 0.0;
        }
        BigDecimal aB = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        BigDecimal res = aB.divide(bB, 8, mode);
        return res.doubleValue();
    }

    /**
     * 绝对值
     *
     * @param a
     * @return
     */
    public static double abs(double a) {
        BigDecimal aB = new BigDecimal(a);
        BigDecimal res = aB.abs();
        return res.doubleValue();
    }

}
