package com.qbao.aisr.stuff.alarm.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.google.common.collect.Maps;

/**
 * 异常工具类
 * 
 * @author 贾红平
 *
 * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
 * $LastChangedRevision: 138 $
 * $LastChangedBy: allen $
 */
public class AlarmExceptionUtil {

    /**
     * 定义邮件基本信息
     */

    public static Map<String, String> initMap = Maps.newHashMap();

    static {
        initMap.put(MessageConstanUtil.ALARM_TYPE, "告警级别:");
        initMap.put(MessageConstanUtil.ALARM_USERID, "用户账号:");
        initMap.put(MessageConstanUtil.ALARM_SPU_ID, "产品编号:");
        initMap.put(MessageConstanUtil.ALARM_CLASS_NAME, "调用业务接口:");
        initMap.put(MessageConstanUtil.ALARM_EXCEPTION, "出现异常类型:");
    }

    public static String getExecptionInfo(Exception exception) {
        if (exception!=null) {
            if (exception instanceof NullPointerException) {
                return "空指针异常：NullPointerException";
            }
            if (exception instanceof ClassCastException) {
                return "类型强制转换异常：ClassCastException";
            }
            if (exception instanceof NegativeArraySizeException) {
                return "数组负下标异常：NegativeArraySizeException";
            }
            if (exception instanceof ArrayIndexOutOfBoundsException) {
                return "数组下标越界异常：ArrayIndexOutOfBoundsException";
            }
            if (exception instanceof EOFException) {
                return "文件已结束异常：EOFException";
            }
            if (exception instanceof FileNotFoundException) {
                return "文件未找到异常：FileNotFoundException";
            }
            if (exception instanceof NumberFormatException) {
                return "字符串转换为数字异常：NumberFormatException";
            }
            if (exception instanceof SQLException) {
                return "操作数据库异常：SQLException";
            }
            if (exception instanceof NoSuchMethodException) {
                return "方法未找到异常：NoSuchMethodException";
            }
            if (exception instanceof IOException) {
                return "文件输入输出异常：IOException";
            }
            return exception.getMessage();
        }
        else{
            return "没有获取到任何异常";
        }

       
    }

    public static String getExceptionKey(Exception ex) {
        String fullMsg = ExceptionUtils.getFullStackTrace(ex);
        return ExceptionUtils.getMessage(ex) + " : " + getExceptionClassName(fullMsg);
    }

    private static String getExceptionClassName(String mesg) {
        String[] strs = StringUtils.split(mesg);
        for (String str : strs) {
            if (StringUtils.containsIgnoreCase(str, "com.qbao")) {
                return str;
            }
        }
        return strs[strs.length - 1];
    }

    public static void main(String[] args) {
        try {
            File file = new File("file.txt");
            //FileReader fr = new FileReader(file);
            file.getName();
            throw new RuntimeException("Runtime Exceptions Test");
        } catch (Exception e) {
            System.out.println("Exception key : " + getExceptionKey(e));
        }

    }

}
