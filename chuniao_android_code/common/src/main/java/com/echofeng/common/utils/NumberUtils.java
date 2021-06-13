package com.echofeng.common.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: NumberUtils
 * Author: echo
 * Date: 2019/11/15 14:08
 * Description: 数字计算相关工具类
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NumberUtils {
    public static float getProgress(int current, int maxNumber) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String progress = numberFormat.format((float) current / (float) maxNumber * 100).replace(",", "");
        float progressNum = Float.valueOf(progress);
        return progressNum;
    }

    public static boolean compareLess(float res, float target) {
        BigDecimal a = new BigDecimal(res);
        BigDecimal b = new BigDecimal(target);
        if (a.compareTo(b) == -1) {
            return true;
        }
        return false;
    }

    // 方法一：NumberFormat
    public static String big(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        // 是否以逗号隔开, 默认true以逗号隔开,如[123,456,789.128]
        nf.setGroupingUsed(false);
        // 结果未做任何处理
        return nf.format(d);
    }

    //方法二： BigDecimal

    /**
     * 截取模式
     *
     * @param d
     * @return
     */
    public static String bigRoundDown(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        //截取
        return d1.divide(d2, 0, BigDecimal.ROUND_DOWN).toPlainString();
    }

    //方法二： BigDecimal
    public static String big0(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留0位小数
        return d1.divide(d2, 0, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    //方法二： BigDecimal
    public static String big2(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    //方法二： BigDecimal
    public static String big4(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留4位小数
        return d1.divide(d2, 4, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    //方法二： BigDecimal
    public static String big4(BigDecimal d) {
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留4位小数
        return d.divide(d2, 4, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    //方法二： BigDecimal
    public static String big8(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留8位小数
        String s = d1.divide(d2, 8, BigDecimal.ROUND_HALF_UP).toPlainString();
        int index = s.length() - 1;
        while (index >= 0) {
            if (s.charAt(index) == 48) {
                index--;
                continue;
            }
            break;
        }
        if (s.charAt(index) == 46) {
            return s.substring(0, index);
        }
        return s.substring(0, index + 1);
    }

    //方法二： BigDecimal
    public static String big8(BigDecimal d) {
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留8位小数
        String s = d.divide(d2, 8, BigDecimal.ROUND_HALF_UP).toPlainString();
        int index = s.length() - 1;
        while (index >= 0) {
            if (s.charAt(index) == 48) {
                index--;
                continue;
            }
            break;
        }
        if (s.charAt(index) == 46) {
            return s.substring(0, index);
        }
        return s.substring(0, index + 1);
    }

    public static float getMaxFloat(List<Float> data) {
        float max = (float) Collections.max(data);
        return max;
    }

    /**
     * 设置智能输入2为小数，无输入监听
     *
     * @param edittext
     */
    public static void set2Edittext(EditText edittext) {
        set2Edittext(edittext, null);
    }

    /**
     * 设置只能输入两位小数，设置输入监听
     *
     * @param edittext
     * @param listener
     */
    public static void set2Edittext(EditText edittext, TextWatcher listener) {

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (listener != null) {
                    listener.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //限制输入2位小数
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        edittext.setText(s);

                        edittext.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edittext.setText(s);
                    edittext.setSelection(2);
                }

//                if (s.toString().startsWith("0")
//                        && s.toString().trim().length() > 1) {
//                    if (!s.toString().substring(1, 2).equals(".")) {
//                        edittext.setText(s.subSequence(0, 1));
//                        edittext.setSelection(1);
//                    }
//                }

                if (listener != null) {
                    listener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listener != null) {
                    listener.afterTextChanged(s);
                }
            }
        });

    }

    /**
     * 验证小数输入字符串是否合规
     *
     * @param trim
     * @return
     */
    public static boolean verifyDouble(String trim) {
        if (!TextUtils.isEmpty(trim)) {
            String firstChar = String.valueOf(trim.charAt(0));
            String lastChar = String.valueOf(trim.charAt(trim.lastIndexOf(trim)));
            if (firstChar.equals(".")) {
                MyToast.showToast("请输入正确金额");
                return false;
            } else if (lastChar.equals(".")) {
                MyToast.showToast("请输入正确金额");
                return false;
            } else {
                return true;
            }
        } else {
            MyToast.showToast("请输入金额");
            return false;
        }
    }
    /**
     * 限制小数位数以及小数形式
     * @param editText
     * @param s
     * @param limitCounts 小数后位数
     */
    public static void textWatchDecimalLimit(EditText editText, CharSequence s, int limitCounts) {
        //删除.后面超过limitCounts位的数字
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > limitCounts) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + limitCounts + 1);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }

        //如果.在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0并且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }
    //设置账号输入框的输入格式为:3 4 4 4 4
    public static void iniSetBankNumberEtText(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;
            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }
                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 3 || index == 8 || index == 13 || index == 18)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }
                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }
                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }
}
