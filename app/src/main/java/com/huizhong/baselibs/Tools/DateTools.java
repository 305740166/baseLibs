package com.huizhong.baselibs.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zongfu on 16/5/5.
 */
public class DateTools {

    /**
     * long 转 string
     */
    public static String longToString(long time,String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        Date dt = new Date(time);
        return sdf.format(dt);
    }

    /**
     * string 转 long
     */
    public static long stringToLong(String str,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long time = 0;
        try {
            time = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    /**
     * Calendar 转 string
     */
    public static String calendarToString(Calendar calendar, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * string 转 Calendar
     */
    public static Calendar stringToCalendar(String str, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, int minute, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String mydate1 = "";
        try {
            Date date1 = sdf.parse(sj1);
            long Time = (date1.getTime() / 1000) + minute * 60;
            date1.setTime(Time * 1000);
            mydate1 = sdf.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    public static String getPublicTime() {
        Calendar calendar = Calendar.getInstance();
        return calendarToString(calendar, "yyyy-MM-dd HH:mm:ss");
    }
}
