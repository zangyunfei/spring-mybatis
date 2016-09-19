/**
 * Copyright(c) 2011-2012 by YouCredit Inc.
 * All Rights Reserved
 */
package com.zyf.springmybatis.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author ijay
 */
public class DateUtils {
    /**
     * yyyy/MM/dd
     */
    public static final String DATE_FORMAT_SIMPLE = "yyyy/MM/dd";
    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_MIDDLE = "yyyy-MM-dd";
    /**
     * yyyy年MM月dd日
     */
    public static final String DATE_FORMAT_CN = "yyyy年MM月dd日";
    /**
     * yyyy年M月
     */
    public static final String DATE_FORMAT_CN_YM = "yyyy年M月";
    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 以“今天”为基准生成day指定的日期，“日”以下数据将被置零。若“今天”的日期＝day，则直接返回“今天”
     *
     * @param day
     *        日期
     * @param afterToday
     *        是否“在今天之后”。如“今天”为2014-1-13，day为25，则afterToday为true时返回“2014-2-25”，
     *        false时返回2013-12-25
     * @return 新的日期对象
     */
    public static Date getDate4FixDay(int day, boolean afterToday) {
        return DateUtils.getDate4FixDay(new Date(), day, afterToday);
    }

    /**
     * 以date为基准生成day指定的日期，“日”以下数据将被置零。若date的日期＝day，则直接返回date
     *
     * @param date
     *        基准日期
     * @param day
     *        日期
     * @param afterToday
     *        是否应“在date之后”。true则compare(return, date) > 0
     * @return 新的日期对象
     */
    public static Date getDate4FixDay(Date date, int day, boolean afterToday) {
        date = org.apache.commons.lang3.time.DateUtils.truncate(date,
            Calendar.DAY_OF_MONTH);
        Date baseDay = org.apache.commons.lang3.time.DateUtils.setDays(date,
            day);

        int compare = org.apache.commons.lang3.time.DateUtils
            .truncatedCompareTo(baseDay, date, Calendar.DAY_OF_MONTH);

        if (afterToday && compare <= 0) {
            baseDay = org.apache.commons.lang3.time.DateUtils.addMonths(
                baseDay, 1);
        } else if (!afterToday && compare > 0) {
            baseDay = org.apache.commons.lang3.time.DateUtils.addMonths(
                baseDay, -1);
        }

        return baseDay;
    }

    /**
     * 计算date1（先）与date2（后）之间的差值，即date2 - date1
     *
     * @param date1
     *        起始时间
     * @param date2
     *        结束时间
     * @param field
     *        指定差值单位，由Calendar中常量字段定义
     * @return date2-date1
     */
    public static long getDelta(Date date1, Date date2, int field) {
        switch (field) {
            case Calendar.MILLISECOND:
                return DateUtils.getDeltaMilliseconds(date1, date2);
            case Calendar.SECOND:
                return DateUtils.getDeltaSeconds(date1, date2);
            case Calendar.MINUTE:
                return DateUtils.getDeltaMinutes(date1, date2);
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                return DateUtils.getDeltaHours(date1, date2);
            case Calendar.DAY_OF_MONTH:
            case Calendar.DAY_OF_WEEK:
            case Calendar.DAY_OF_WEEK_IN_MONTH:
            case Calendar.DAY_OF_YEAR:
                return DateUtils.getDeltaDays(date1, date2);
            case Calendar.MONTH:
                return DateUtils.getDeltaMonths(date1, date2);
            case Calendar.YEAR:
                return DateUtils.getDeltaYears(date1, date2);
            default:
                throw new IllegalArgumentException(
                    "\"field\" should be among MILLISECOND, SECOND, MINUTE, HOUR, HOUR_OF_DAY, DAY_OF_MONTH, "
                        + "DAY_OF_WEEK, DAY_OF_WEEK_IN_MONTH, DAY_OF_YEAR, MONTH, YEAR");
        }

    }

    public static int getDeltaYears(Date date1, Date date2) {
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }

    /**
     * 计算年龄，包含月份
     *
     * @param birthDay
     * @return
     */
    public static int getDeltaYearsOfAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return -1;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 获取两个时间间相差的月数，date2－date1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDeltaMonths(Date date1, Date date2) {
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        int monthDelta = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        int yearDalta = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);

        if (yearDalta != 0) {
            monthDelta += 12 * yearDalta;
        }

        return monthDelta;
    }

    /**
     * 获取两个时间间相差的月数，date2－date1，若date2的日期>=date1的日期，月数进一
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDeltaMonth(Date date1, Date date2) {
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        int monthDelta = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        int yearDalta = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);

        if (yearDalta != 0) {
            monthDelta += 12 * yearDalta;
        }

        if (cal2.get(Calendar.DATE) >= cal1.get(Calendar.DATE)) {
            monthDelta += 1;
        }

        return monthDelta;
    }

    /**
     * date2 - date1
     *
     * @param date1
     *        计算时会取整为“年-月-日”
     * @param date2
     *        计算时会取整为“年-月-日”
     * @return
     */
    public static int getDeltaDays(Date date1, Date date2) {
        return (int) (DateUtils.getDeltaHours(
            org.apache.commons.lang3.time.DateUtils.truncate(date1,
                Calendar.DAY_OF_MONTH), org.apache.commons.lang3.time.DateUtils
                .truncate(date2, Calendar.DAY_OF_MONTH)) / 24);
    }

    public static long getDeltaHours(Date date1, Date date2) {
        return DateUtils.getDeltaMinutes(date1, date2) / 60;
    }

    public static long getDeltaMinutes(Date date1, Date date2) {
        return DateUtils.getDeltaSeconds(date1, date2) / 60;

    }

    public static long getDeltaSeconds(Date date1, Date date2) {
        return DateUtils.getDeltaMilliseconds(date1, date2) / 1000;
    }

    public static long getDeltaMilliseconds(Date date1, Date date2) {
        return date2.getTime() - date1.getTime();
    }

    /**
     * 日期加天数|时分秒归零
     *
     * @param date
     *        需要修改的日期
     * @param addDayNo
     *        需要添加的天数
     * @return
     */
    public static Date addDaysNTruncate(Date date, int addDayNo) {
        return org.apache.commons.lang3.time.DateUtils.addDays(
            org.apache.commons.lang3.time.DateUtils.truncate(date,
                Calendar.DATE), addDayNo);
    }

    /**
     * 日期加年数
     *
     * @param date
     *        需要修改的日期
     * @param addYearNo
     *        需要添加的年数
     * @return
     */
    public static Date addYears(Date date, int addYearNo) {
        return org.apache.commons.lang3.time.DateUtils
            .addYears(date, addYearNo);
    }

    /**
     * 按days获取离baseDay最近（小于等于）的「回款日」。e.g. days = {10,
     * 25}，baseDay为2013-4-1时返回2013-3-25，baseDay为2013-4-11时返回2013-4-10，
     * baseDay为2013-4-25时返回2013-4-25
     *
     * @param baseDay
     * @param days
     * @return
     */
    public static Date getCurrentRepayDate(Date baseDay, Integer... days) {
        List<Integer> dayList = Arrays.asList(days);
        Collections.sort(dayList);
        Collections.reverse(dayList);

        baseDay = org.apache.commons.lang3.time.DateUtils.truncate(baseDay,
            Calendar.DATE);
        Date theDay = null;

        for (int day : dayList) {
            theDay = org.apache.commons.lang3.time.DateUtils.setDays(baseDay,
                day);

            if (baseDay.compareTo(theDay) >= 0) {
                return theDay;
            }
        }

        return org.apache.commons.lang3.time.DateUtils.addMonths(
            org.apache.commons.lang3.time.DateUtils.setDays(baseDay,
                dayList.get(0)), -1);
    }

    public static DateFormat getDateFormatForLdapUTCDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("utc"));

        return format;
    }

    /**
     * /**
     * 获取本月的最后一天
     *
     * @return Date
     */
    public static Date getActualMaximumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static String formatDuration(long duration) {
        return DateFormatUtils.format(duration, "H'h' m'm' s's' S'ms'",
            TimeZone.getTimeZone("GMT"));
    }

    /**
     * 日期格式化为字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, DateUtils.DATE_FORMAT_MIDDLE);
    }

    /**
     * 日期格式化为字符串 时间格式
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, DateUtils.TIME_FORMAT);
    }

    public static Date parseDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date,
                DateUtils.DATE_FORMAT_MIDDLE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseTime(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date,
                DateUtils.TIME_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
