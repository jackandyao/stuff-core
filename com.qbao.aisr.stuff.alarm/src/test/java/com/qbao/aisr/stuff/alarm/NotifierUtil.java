/**
 * 
 */
package com.qbao.aisr.stuff.alarm;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;

import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.alarm.cmp.factory.SendTextMailCmpFactory;
import com.qbao.aisr.stuff.alarm.cmp.http.SendPhoneMessageUtil;
import com.qbao.aisr.stuff.alarm.facade.AlaramServiceFacotryFacade;
import com.qbao.aisr.stuff.alarm.mail.impl.ErrorMailAlarmService;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;


/**
 * @author sjwangping@qbao.com
 *
 * $LastChangedDate: 2017-03-11 16:23:00 +0800 (Sat, 11 Mar 2017) $
 * $LastChangedRevision: 141 $
 * $LastChangedBy: allen $
 */
public class NotifierUtil {

    public static Set<String> msgKeys = new HashSet<>();


    public static void notifyByEmail(Map<String, Object> param, Exception e) {
        JSONObject messageJson = getJsonObject(param, e, MessageConstanUtil.MAIL_ERROR);
        AlaramServiceFacotryFacade.sendAlaramMessageByMail(messageJson);
    }

    public static void notifyByEmail(Map<String, Object> param, String msg) {
        Exception e = new Exception(msg);
        JSONObject messageJson  = getJsonObject(param, e, MessageConstanUtil.MAIL_ERROR);
        AlaramServiceFacotryFacade.sendAlaramMessageByMail(messageJson);
    }

    public static void notifyByEmail(Map<String, Object> param, String msg, String alarmLevel) {
        Exception e = new Exception(msg);
        JSONObject  messageJson = getJsonObject(param, e, alarmLevel);
        AlaramServiceFacotryFacade.sendAlaramMessageByMail(messageJson);
    }

    public static void notifyByEmail(Map<String, Object> param, Exception e, String alarmLevel) {
        JSONObject messageJson = getJsonObject(param, e,alarmLevel);
        AlaramServiceFacotryFacade.sendAlaramMessageByMail(messageJson);
    }

    private static JSONObject getJsonObject(Map<String, Object> param, Exception e, String alarmLevel) {
        JSONObject messageJson = new JSONObject();
        messageJson.put(MessageConstanUtil.ALARM_CLASS_NAME, param.get(MessageConstanUtil.ALARM_CLASS_NAME));
        messageJson.put(MessageConstanUtil.ALARM_TYPE, alarmLevel);
        messageJson.put(MessageConstanUtil.ALARM_USERID, param.get("user_id"));
        messageJson.put(MessageConstanUtil.ALARM_EXCEPTION, e);
        return messageJson;
    }

    public static void notifyByPhone(String msg){
        String encodeMsg = StringUtils.EMPTY;
//        try {
//           // encodeMsg = URLEncoder.encode(msg,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            System.out.println(e.getMessage());
//        }
        //Void to send duplicate msg
        if(!msgKeys.contains(msg)){
            msgKeys.add(msg);
            AlaramServiceFacotryFacade.sendAlaramMessageByPhone(MessageConstanUtil.PHONE_ERROR, msg);
        }
    }
    public static void main(String[] args){
    	//NotifierUtil.notifyByEmail("测试一下", "在本机测试一下");
    	//SendPhoneMessageUtil.executeSendPhoneMessage("111", "测试一下");
    	 SendTextMailCmpFactory.sendMailSimpleTextMsg("测试一下", "在本机测试一下");
    	//ErrorMailAlarmService a=new ErrorMailAlarmService();
    	// Logger logger = Logger.getLogger(NotifierUtil.class);
    	//a.executeSendMailMessage("测试一下", "在本机测试一下", logger, "112233");
    }
    public static void notifyByEmail(String subject, String message) {
        Logger logger = Logger.getLogger(NotifierUtil.class);
        if(!msgKeys.contains(subject)) {
            msgKeys.add(subject);
            SMTPAppender appender = new SMTPAppender();
            try {
                appender.setSMTPUsername("zhuangaijack");
                appender.setSMTPPassword("aa11ss33");
                appender.setTo("liaijun@qbao.com");
                appender.setFrom("zhuangaijack@163.com");
                appender.setSMTPHost("smtp.163.com");
                appender.setLocationInfo(true);
                appender.setSubject(subject);
                appender.setLayout(new PatternLayout());
                appender.activateOptions();
                logger.addAppender(appender);
                logger.error(message.toString());
            } catch (Exception e) {
                logger.error("调用LOG4J发送邮件失败", e);
            }
        }
    }
}
