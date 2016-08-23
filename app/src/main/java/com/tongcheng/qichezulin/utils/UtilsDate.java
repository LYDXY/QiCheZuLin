package com.tongcheng.qichezulin.utils;

import android.content.Context;
import android.widget.TextView;

import com.code19.library.DateUtils;
import com.jiongbull.jlog.JLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsDate {


    public static void getDate2(TextView textView) {
        // textView.setText(new SimpleDateFormat("MM-dd").format(System.currentTimeMillis()));
        textView.setText("00-00");
    }

    public static void getTime2(TextView textView) {
        String xingqi = "";
        xingqi = get_DAY_OF_WEEK(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        if (cal.get(Calendar.MINUTE) < 10) {
            //   textView.setText(xingqi + " " + cal.get(Calendar.HOUR_OF_DAY) + ":0" + cal.get(Calendar.MINUTE));
        } else {
            //   textView.setText(xingqi + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        }
        textView.setText("周几" + "00:" + "00");

    }


    public static void getTomorrow(TextView textView1, TextView textView2) {
        String xingqi = "";
        Date date = DateUtils.getDateAfter(new Date(), 1);
        xingqi = get_DAY_OF_WEEK(date);
        DateUtils.formatDateCustom(date, "MM-dd");
        DateUtils.formatDateCustom(date, "HH:mm");
        //  textView1.setText(DateUtils.formatDateCustom(date, "MM-dd"));
        // textView2.setText(xingqi + " " + DateUtils.formatDateCustom(date, "HH:mm"));
        textView1.setText("00-00");
        textView2.setText("周几" + "00:" + "00");
    }


    //计算时间差
    public static void getTimeBetween(Date date, Context context, TextView textView1, TextView textView2) {
        String xingqi;
        JLog.w("时间差:====" + DateUtils.subtractDate(new Date(), date) + "");
        long between = (DateUtils.subtractDate(new Date(), date)) / 1000;//除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        JLog.w("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
        if (day1 < 0) {
            Utils.ShowText(context, "取车的时间要在当前时间的两个小时后");
        } else if (hour1 < 2) {
            Utils.ShowText(context, "取车的时间要在当前时间的两个小时后");
        } else {
            xingqi = get_DAY_OF_WEEK(date);
            textView1.setText(DateUtils.formatDateCustom(date, "MM-dd"));
            textView2.setText(xingqi + " " + DateUtils.formatDateCustom(date, "HH:mm"));
        }
    }

    //计算时间差2
    public static int getTimeBetween2(Date date1, Date date2, Context context, TextView textView1, TextView textView2, TextView textView3) {
        int biaozhu = 0;
        String xingqi;
        JLog.w("时间差:====" + DateUtils.subtractDate(date1, date2) + "");
        long between = (DateUtils.subtractDate(date1, date2)) / 1000;//除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        JLog.w("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
        if (day1 < 0) {
            Utils.ShowText(context, "还车的时间要在取车时间之后");
            return 0;
        } else if (hour1 < 0) {
            Utils.ShowText(context, "还车的时间要在取车时间之后");
            return 0;
        } else if (minute1 < 0) {
            Utils.ShowText(context, "还车的时间要在取车时间之后");
            return 0;
        } else if (second1 < 0) {
            Utils.ShowText(context, "还车的时间要在取车时间之后");
            return 0;
        } else {
            xingqi = get_DAY_OF_WEEK(date2);
            textView1.setText(DateUtils.formatDateCustom(date2, "MM-dd"));
            textView2.setText(xingqi + " " + DateUtils.formatDateCustom(date2, "HH:mm"));
            if (day1 == 0) {
                textView3.setText("0" + day1);
                biaozhu = 1;
            } else if (day1 > 0 && day1 < 30) {
                textView3.setText(day1 + "");
                biaozhu = 2;
            } else if (day1 >= 30) {
                biaozhu = 3;
                textView3.setText(day1 + "");
            }
            JLog.w(biaozhu + "");
            JLog.w(day1 + "//////////////");
            JLog.w(DateUtils.formatDateCustom(date2, "MM-dd"));
            JLog.w(xingqi + " " + DateUtils.formatDateCustom(date2, "HH:mm"));
            return biaozhu;
        }

    }


    //计算时间差2
    public static int getTimeBetween3(Date date1, Date date2, Context context, TextView textView1, TextView textView2, TextView textView3) {
        int biaozhu = 0;
        String xingqi;
        JLog.w("时间差:====" + DateUtils.subtractDate(date1, date2) + ""); //计算取车时间和还车时间差
        long between = (DateUtils.subtractDate(date1, date2)) / 1000;//除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        JLog.w("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒"); // 必须都为负数才正常

        //判断当前选择的时间和系统时间的比较-----取车时间 与系统时间做比较
        long between222 = (DateUtils.subtractDate(new Date(), date2)) / 1000;//除以1000是为了转换成秒
        long day2 = between222 / (24 * 3600);
        long hour2 = between222 % (24 * 3600) / 3600;
        if (day2 < 0) {
            Utils.ShowText(context, "取车的时间要在当前时间的两个小时后");
            return 0;
        } else if (hour2 < 2) {
            Utils.ShowText(context, "取车的时间要在当前时间的两个小时后");
            return 0;
        } else {
            if (day1 > 0) {
                Utils.ShowText(context, "取车的时间要在还车的时间之前");
                return 0;
            } else if (hour1 > 0) {
                Utils.ShowText(context, "取车的时间要在还车的时间之前");
                return 0;
            } else if (minute1 > 0) {
                Utils.ShowText(context, "取车的时间要在还车的时间之前");
                return 0;
            } else if (second1 > 0) {
                Utils.ShowText(context, "取车的时间要在还车的时间之前");
                return 0;
            } else {
                xingqi = get_DAY_OF_WEEK(date2);
                textView1.setText(DateUtils.formatDateCustom(date2, "MM-dd"));
                textView2.setText(xingqi + " " + DateUtils.formatDateCustom(date2, "HH:mm"));
                if (Math.abs(day1) == 0) {
                    biaozhu = 1;
                    textView3.setText("" + Math.abs(day1));
                } else if (Math.abs(day1) < 30 && Math.abs(day1) > 0) {
                    textView3.setText("" + Math.abs(day1));
                    biaozhu = 2;
                } else {
                    biaozhu = 3;
                    textView3.setText(Math.abs(day1) + "");
                }
                JLog.w(biaozhu + "");
                JLog.w(day1 + "//////////////");
                JLog.w(DateUtils.formatDateCustom(date2, "MM-dd"));
                JLog.w(xingqi + " " + DateUtils.formatDateCustom(date2, "HH:mm"));
                return biaozhu;
            }
        }


    }


    //根据date判断今天是星期几
    public static String get_DAY_OF_WEEK(Date date) {
        String xingqi = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        JLog.w("一周中的第" + cal.get(Calendar.DAY_OF_WEEK) + "天");
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            xingqi = "周日";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
            xingqi = "周一";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
            xingqi = "周二";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
            xingqi = "周三";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
            xingqi = "周四";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
            xingqi = "周五";
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
            xingqi = "周六";
        }
        return xingqi;

    }
}
