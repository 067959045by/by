package com.echofeng.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 作者: Administrator  by:echo
 * 描述: DateUtil
 * 时间: 2018/11/9 0009  10:41
 */
public class DateUtil {

    /**
     * 过去的某一时间,计算距离当前的时间
     */
    public static String CalculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        long reset = Long.valueOf(time); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;

        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {

            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数

            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "刚刚";
            }
        }
        return msg;

    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time;
        try {
            time = sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception e) {
//            time = "0000-00-00 00:00:00";
            time = seconds;
        }
        return time;
    }

    public static String timeStamp2mdt(long seconds) {
        return DateUtil.second2Date(seconds + "", "MM月dd日 HH:mm:ss");
    }

    /**
     * 时间戳转换成日期格式字符串
     * ramon 2020-9-10
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String second2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (seconds.length() == 10) {
            seconds += "000";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time;
        try {
            time = sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception e) {
//            time = "0000-00-00 00:00:00";
            time = seconds;
        }
        return time;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds) {

        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (seconds.length() == 10) {
            seconds += "000";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time;
        try {
            time = sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception e) {
            time = seconds;
        }
        return time;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(long seconds) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time;
        try {
            time = sdf.format(new Date(seconds));
        } catch (Exception e) {
//            time = "0000-00-00 00:00:00";
            time = "";
        }
        return time;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2DateMMDD(String seconds) {
        String time;
        try {
            time = DateUtil.second2Date(seconds , "MM月dd日 HH:mm:ss");
        } catch (Exception e) {
            time = seconds;
        }
        return time;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStampZoneDate(String seconds, String zone) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * @ProjectName: NBN
     * @ClassName: DateUtil
     * @Description: 获取时区号
     * @Author: echo
     * @CreateDate: 2019/12/24 18:43
     * @UpdateUser: 更新者
     * @UpdateDate: 2019/12/24 18:43
     * @UpdateRemark: 更新说明
     * @Version: 1.0
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 日期格式字符串转换成时间戳
     * 这里针对BBC时间格式做了特殊处理
     *
     * @return
     */
    public static String date2TimeStamp(String date_str) {
        String currenttime = null;
        if (date_str.contains("T")) {
            date_str = date_str.replace("T", " ");
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            currenttime = String.valueOf(sdf.parse(date_str).getTime());
            return currenttime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currenttime;
    }

    /**
     * 获取月日
     *
     * @param date
     * @return
     */
    public static String dateToMMDD(String date) {
        String res = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日  HH:mm:ss");
        try {
            long lt = dateToStamp(date);
            Date d = new Date(lt * 1000);
            res = simpleDateFormat.format(d);
        } catch (Exception e) {
            res = date;
        }
        return res;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static long timeStampLong() {
        long time = System.currentTimeMillis();

        return (time / 1000);
    }

    /**
     * 获取昨天开始时间（精确到秒）
     *
     * @return
     */
    public static long timeStampYesterday() {
        long nowTime = System.currentTimeMillis();
        long todayStartTime = nowTime - ((nowTime + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000L));
        return (todayStartTime / 1000L - ((24 * 60 * 60)) - (2 * 60 * 60));
    }

    /**
     * 获取昨天开始时间（精确到秒）
     *
     * @return
     */
    public static long timeStampToday() {
        long nowTime = System.currentTimeMillis();
        long todayStartTime = nowTime - ((nowTime + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000L));
        return (todayStartTime / 1000L);
    }

    /**
     * 取得昨天0点（精确到秒）
     *
     * @return
     */
    public static long getYesterday() {
        long time = System.currentTimeMillis();
        return time / 1000 - 86400;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 获取多久以前的时间
     */
    public static long getTimesAgo(int yearsNum) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int yearNow = c.get(Calendar.YEAR);
        int monthNow = c.get(Calendar.MONTH) + 1;
        int dayNow = c.get(Calendar.DATE);
        int yearAgo = yearNow - yearsNum;
        String timeStr = yearAgo + "-" + monthNow + "-" + dayNow + " 00:00:00";
        long dateSnapBirth = DateUtil.dateToStamp(timeStr);
        return dateSnapBirth;
    }

    /*

     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    /*

     * 时间戳计算获得天数
     */
    public static long dateToDays(long time1, long time2) {
        long day = 0;
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        return day;
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp3Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time;
        try {
            time = sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception e) {
//            time = "0000-00-00 00:00:00";
            time = seconds;
        }
        return time;
    }

    /**
     * 秒数转时长
     *
     * @param second
     * @return 日时分
     */
    public static String secondToTime(long second) {

        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数

        if (0 < days) {
            return days + "天，" + hours + "小时，" + minutes + "分";
        } else {
            return hours + "小时，" + minutes + "分";
        }
    }

    /**
     * 秒数转时长
     *
     * @param second
     * @return 日时分秒
     */
    public static String secondToTimeOfSec(long second) {

        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数

        if (0 < days) {
            return days + "天，" + hours + "小时，" + minutes + "分，" + second + "秒";
        } else {
            return hours + "小时，" + minutes + "分，" + second + "秒";
        }
    }

    /**
     * 单纯 几天以前的时间戳
     *
     * @param day
     * @return
     */
    public static long getAnyDayAgo(int day) {
        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -day);
        //    String three_days_ago = sdf1.format(calendar1.getTime());
        long ms = calendar.getTime().getTime();
        return (long) MathUtil.divide(ms, 1000, 0);
    }

    /**
     * 获取今天0点
     *
     * @return
     */
    public static long getToday() {
        Calendar calendar = Calendar.getInstance();
        return getDataZero(calendar.getTime());
    }

    /**
     * 几个月前的 时间戳
     *
     * @param month
     * @return
     */
    public static long getFewMouthAgo(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);
        return (long) MathUtil.divide(calendar.getTime().getTime(), 1000);
    }

    /**
     * 获取某一天的0点
     *
     * @param date
     * @return 秒 时间戳
     */
    public static long getDataZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return (long) MathUtil.divide(zero.getTime(), 1000);
    }

    /**
     * 某一天零点
     *
     * @param second
     * @return 秒 时间戳
     */
    public static long getDataZero(long second) {

        Date date = new Date(second*1000);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date zero = calendar.getTime();

        return zero.getTime()/1000;

    }


}
