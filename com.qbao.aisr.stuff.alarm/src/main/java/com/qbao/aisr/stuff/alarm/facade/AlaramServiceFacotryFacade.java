package com.qbao.aisr.stuff.alarm.facade;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.alarm.mail.factory.MailAlaramServiceFactory;
import com.qbao.aisr.stuff.alarm.phone.factory.PhoneAlaramServiceFactory;
import com.qbao.aisr.stuff.alarm.util.AlarmExceptionUtil;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 * 封装报警,错误级别的信息服务工厂类
 * 
 * @author 贾红平
 * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
 * $LastChangedRevision: 138 $
 * $LastChangedBy: allen $
 */
public class AlaramServiceFacotryFacade {

    /**
     * 定义邮件基本信息
     */
    private static String type;
    private static String userId = "0";
    private static String spuId = "0";
    private static String className;
    private static String message;
    private static Exception exception = null;
    private static Set<String> exceptionKeys = new HashSet<>();

    /**
     * 初始化参数值
     * 
     * @param json
     */
    private static void initParam(JSONObject json) {
        if (json.containsKey(MessageConstanUtil.ALARM_TYPE)) {
            type = json.getString(MessageConstanUtil.ALARM_TYPE);
        }
        if (json.containsKey(MessageConstanUtil.ALARM_USERID)) {
            userId = json.getString(MessageConstanUtil.ALARM_USERID);
        }
        if (json.containsKey(MessageConstanUtil.ALARM_SPU_ID)) {
            spuId = json.getString(MessageConstanUtil.ALARM_SPU_ID);
        }
        if (json.containsKey(MessageConstanUtil.ALARM_CLASS_NAME)) {
            className = json.getString(MessageConstanUtil.ALARM_CLASS_NAME);
        }
        if (json.containsKey(MessageConstanUtil.ALARM_MESSAGE)) {
            message = json.getString(MessageConstanUtil.ALARM_MESSAGE);
        }
        if (json.containsKey(MessageConstanUtil.ALARM_EXCEPTION)) {
            exception = (Exception) json.get(MessageConstanUtil.ALARM_EXCEPTION);
        }
    }

    private static boolean isDuplicationException(Exception ex) {
        if (null == ex) {
            return true;
        }
        String key = AlarmExceptionUtil.getExceptionKey(ex);
        if (exceptionKeys.contains(key)) {
            return true;
        } else {
            exceptionKeys.add(key);
            return false;
        }
    }

    /**
     * 基于邮件发送日志级别信息
     * 
     * @param type
     * 发送日志级别类型
     * @param subject
     * 邮件主题
     * @param message
     * 邮件内容
     */
    public static void sendAlaramMessageByMail(JSONObject json) {
        initParam(json);
        if (!isDuplicationException(exception)) {
            MailAlaramServiceFactory.sendMailAlaramMessage(type, getMessageInfo(), exception == null ? message : exception.getMessage());
        }
    }

    /**
     * 基于手机发送短消息-通过封装参数对象
     */
    public static void sendAlaramMessageByPhone(JSONObject json) {
        initParam(json);
        if (!isDuplicationException(exception)) {
            PhoneAlaramServiceFactory.sendPhoneAlaramMessage(getMessageInfo(), type);
        }
    }

     /**
     * 基于手机发送错误短信息-通过各个参数
     *
     * @param type
     * @param message
     */
     public static void sendAlaramMessageByPhone(String type, String message) {
         PhoneAlaramServiceFactory.sendPhoneAlaramMessage(type, message);
     }

    /**
     * 拼接错误信息
     * 
     * @return
     */
    private static String getMessageInfo() {
        StringBuffer sb = new StringBuffer();
        try{
            if (Integer.parseInt(userId) >= 0) {
                sb.append(AlarmExceptionUtil.initMap.get(MessageConstanUtil.ALARM_USERID) + userId).append(",");
            }
        }catch(NumberFormatException e){
            
        }
        try{
            if (Integer.parseInt(spuId) > 0) {
                sb.append(AlarmExceptionUtil.initMap.get(MessageConstanUtil.ALARM_SPU_ID) + spuId).append(",");
            }
        }catch(NumberFormatException e){
            
        }
       
        
        if (className != null) {
            sb.append(AlarmExceptionUtil.initMap.get(MessageConstanUtil.ALARM_CLASS_NAME) + className).append(",");
        }
        
        if (exception != null) {
            sb.append(AlarmExceptionUtil.initMap.get(MessageConstanUtil.ALARM_EXCEPTION) + AlarmExceptionUtil.getExecptionInfo(exception)).append(",");
        }
        
        if (message != null) {
            sb.append(AlarmExceptionUtil.initMap.get(MessageConstanUtil.ALARM_MESSAGE) + className);
        }
        return sb.toString();
    }

}
