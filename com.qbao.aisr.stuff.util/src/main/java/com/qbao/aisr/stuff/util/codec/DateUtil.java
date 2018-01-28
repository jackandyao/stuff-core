package com.qbao.aisr.stuff.util.codec;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
public final class DateUtil {

    // 默认显示日期的格式
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";
    // 默认显示日期的格式
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";
    // 默认显示日期时间的格式
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATATIMEF_NO_YEAR_STR = "MM-dd HH:mm:ss";

    public static final String DATATIMEF_HH_MM_SS = "HH:mm:ss";
    // 默认显示日期时间的格式 精确到毫秒
    public static final String DATATIMEF_STR_MIS = "yyyyMMddHHmmssSSS";
    // 默认显示日期时间的格式 精确到分钟
    public static final String DATATIMEF_STR_MI = "yyyy-MM-dd HH:mm";

    public static final String DATATIMEF_STR_MDHm = "MM-dd HH:mm";

    public static final String HH_STR = "HH";

    public static final String YYYYMMDD_STR = "yyyyMMdd";

    public static final String YYYYMM_STR = "yyyyMM";

    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

    //精确到秒
    public static final String DATATIMEF_STR_SEC = "yyyyMMddHHmmss";
    // 默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";

    public static final String ZHCN_MMDD_FORMAT_STR = "MM月dd日";
    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";
    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

    // 默认显示月份和日期的格式
    public static final String MONTHANDDATE_STR = "MM.dd";

    public static final String MONTH_DATE_STR = "MM-dd";

    public static final String HOUR_END = " 23:59:59";

    public static String getCurDate() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String time=format.format(date);
        return time;
    }

    public static String getCurTime() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat(DATATIMEF_STR_MIS);
        String time=format.format(date);
        return time;
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        java.text.DateFormat df = getDateFormat(formatStr);
        return date!=null?df.format(date):"";
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, DATATIMEF_STR);
    }

    private static java.text.DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
            return new SimpleDateFormat(DATAFORMAT_STR);
        } else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
            return new SimpleDateFormat(DATATIMEF_STR);
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
            return new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
            return new SimpleDateFormat(ZHCN_DATATIMEF_STR);
        } else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MIS)) {
            return new SimpleDateFormat(DATATIMEF_STR_MIS);
        } else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MI)) {
            return new SimpleDateFormat(DATATIMEF_STR_MI);
        } else {
            return new SimpleDateFormat(formatStr);
        }
    }

    /**
     * 判断目标时间是否处于某一时间段内
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public static boolean compareTargetTime(Date target,String begin,String end){
        //格式化时间 暂时不考虑传入参数的判断，其他地方如果要调用，最好扩展判断一下入参问题
        String targetTime = dateToDateString(target,DATATIMEF_HH_MM_SS);//HH:mm:ss
        return targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0;
    }

    public static boolean compareTargetDate(Date target,String begin,String end){
        String targetTime = dateToDateString(target,DATAFORMAT_STR);//yyyy-MM-dd
        return targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0;
    }
    
    /**
	 * 返回今天
	 * 
	 * @return
	 * @throws ParseException
	 */
    public static Date getTodayDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		try {
			today = format.parse(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return today;
    }

    public static String getTimeGapString(Date date1, Date date2) {
        if(date1 == null || date2 == null){
            return "";
        }
        //秒
        long result = Math.abs((date1.getTime() - date2.getTime())) / 1000;
        //分
        long m = result/60;
        //小时
        long h = m/60;

        if(h !=0 && h<=24){
            return h+"小时";
        }else if(h>24){
            return dateToDateString(date2);
        }else if(m != 0){
            return m +"分钟";
        }else{
            return result +"秒";
        }
    }

    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    public static String getInternalDateStr(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return org.apache.commons.lang3.time.DateFormatUtils.format(now.getTime(), DATAFORMAT_STR);
    }

    /**
     * str 转 date
     * @return
     * @throws ParseException 
     */
    public static Date strParseDate(String str, String fmtStr) {
    	SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		Date date = new Date();
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        return date;
    }

    /**
     *转23:59:59结尾
     * @param date 格式yyyy-MM-dd
     * @return
     * @author jijun
     * @date 2014年6月30日
     */
    public static String endOneDay(Date date) {

        String dateStr = dateToDateString(date,DATAFORMAT_STR);
        return dateStr + HOUR_END;
    }

    public static void main(String[] args) throws Exception {

    	System.out.println(getCurTime());
        Date date1 = strParseDate("2009-11-01 00:00:00",DATAFORMAT_STR);
        System.out.println(endOneDay(date1));
        System.out.println(getInternalDateStr(date1, 0));
    }

}