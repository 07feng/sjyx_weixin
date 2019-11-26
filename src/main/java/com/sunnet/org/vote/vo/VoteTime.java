package com.sunnet.org.vote.vo;

import com.sunnet.org.util.DateStyle;
import com.sunnet.org.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteTime {

    public static Date startTime = DateUtil.StringToDate("2018-11-20 00:00:01", DateStyle.YYYY_MM_DD_HH_MM_SS);

    public static Date endTime = DateUtil.StringToDate("2018-12-09 23:59:59", DateStyle.YYYY_MM_DD_HH_MM_SS);

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
                return false;
        }
    }


    /**
     * 判断两个时间点大小关系 A > B .true;  A < B .false;
     * @param A
     * @param B
     * @return
     */
    public static boolean AbigBCalendar(Date A, Date B) {
        Calendar date = Calendar.getInstance();
        date.setTime(A);
        Calendar end = Calendar.getInstance();
        end.setTime(B);
        if ( date.after(end)) {
            return true;
        } else if ( A.compareTo(B) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
