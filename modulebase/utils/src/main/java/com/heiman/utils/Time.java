package com.heiman.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author 作者 E-mail: 554674787@qq.com
 * @version 1.0
 * @date 创建时间：2015-10-30 上午11:51:27
 * @parameter
 * @return
 */
public class Time {
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static int getHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return Integer.parseInt(format.format(date));
    }

    public static int getMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return Integer.parseInt(format.format(date));
    }
    public static int getSecond(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("ss");
        return Integer.parseInt(format.format(date));
    }
    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static int comparedate(Date previous_time, Date current_time) {
        if (previous_time.after(current_time)) {
            Log.i("comparision", "before");
            System.out.println("before");
            return 2;
        } else if (current_time.after(previous_time)) {
            Log.i("comparision", "after");
            System.out.println("after");
            return 1;
        } else {
            Log.i("comparision", "unknown");
            System.out.println("unknown");
            return 0;
        }
    }


    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static int hous(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        return h;// + s + "秒"
    }

    public static int cal(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return d;// + s + "分"
    }

    public static int calsss(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return s;// + s + "秒"
    }

    public static String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats)
                .format(new Date(timestamp));
        return date;
    }

    public static Date LongToDate(long timer) {
        String timers = null;
        try {
            timers = longToString(timer, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

        }
        Date dateTmp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            try {
                dateTmp = dateFormat.parse(timers);
            } catch (ParseException e) {
                dateTmp = Time.stringToDate(timers, "yyyy-MM-dd HH:mm:ss");
            }
        } catch (Exception e) {
            try {
                dateTmp = Time.stringToDate(timers, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return dateTmp;
    }

}
