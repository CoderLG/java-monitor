package cn.com.geovis.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * author: LG
 * date: 2019-11-07 17:41
 * desc:
 * 时间
 */
public class TimeUtils {

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     * 返回时差 单位为毫秒
     */
    public static Long TimeSub(Long startTime, Long endTime) {

        return endTime - startTime;
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     * 返回时差 单位为毫秒
     */
    public static Long TimeSub(String startTime, String endTime) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Long start = null;
        Long end = null;
        try {
            start = simpleFormat.parse(startTime).getTime();
            end = simpleFormat.parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return end - start;
    }

    public static  String getCurrentTime(){
        return format(System.currentTimeMillis());
    }

    /**
     * 时间格式化
     * @param time
     * @return
     */
    public static String format(long time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        String format = formatter.format(cal.getTime());
        return format;
    }

    /**
     * 时间格式化
     * @param time
     * @return
     */
    public static String format(Instant time){
        String format =  DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(LocalDateTime.ofInstant(time, ZoneId.systemDefault()));
        return format;
    }
}
