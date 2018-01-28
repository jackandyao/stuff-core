/**
 * 
 */
package com.qbao.aisr.stuff.util.message;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liaijun
 * @createTime 17/3/27
 * $$LastChangedDate: 2017-06-14 19:24:11 +0800 (Wed, 14 Jun 2017) $$
 * $$LastChangedRevision: 1218 $$
 * $$LastChangedBy: wangping $$
 */
public class NotifierUtil {

    public static Set<String> mailMsgKeys = new HashSet<>();
    static Logger logger = Logger.getLogger(NotifierUtil.class);



    public static void notifyByEmail(String email,String subject, String message) {
        if (!mailMsgKeys.contains(subject)) {
            mailMsgKeys.add(subject);
            SMTPAppender appender = new SMTPAppender();
            try {
                appender.setSMTPUsername("jackaiyaoforever@126.com");
                appender.setSMTPPassword("aa11ss33");
                appender.setTo(email);
                appender.setFrom("jackaiyaoforever@126.com");
                appender.setSMTPHost("smtp.126.com");
                appender.setLocationInfo(true);
                appender.setSubject(subject);
                appender.setLayout(new PatternLayout());
                appender.activateOptions();
                logger.addAppender(appender);
                logger.error(message);
            } catch (Exception e) {
                logger.error("调用LOG4J发送邮件失败", e);
            }
        }
    }
    public static void notifyByEmail(String subject, String message) {
        if (!mailMsgKeys.contains(subject)) {
            mailMsgKeys.add(subject);
            SMTPAppender appender = new SMTPAppender();
            try {
                appender.setSMTPUsername("jackaiyaoforever@126.com");
                appender.setSMTPPassword("aa11ss33");
                appender.setTo("786648643@qq.com,sjwangping@qbao.com,jiahongping@qbao.com,liaijun@qbao.com,louxueming@qbao.com,yaojie@qbao.com,sjzhangjun@qbao.com,zhangqin@qbao.com");
                appender.setFrom("jackaiyaoforever@126.com");
                appender.setSMTPHost("smtp.126.com");
                appender.setLocationInfo(true);
                appender.setSubject(subject);
                appender.setLayout(new PatternLayout());
                appender.activateOptions();
                logger.addAppender(appender);
                logger.error(message);
            } catch (Exception e) {
                logger.error("调用LOG4J发送邮件失败", e);
            }
        }
    }
    public static void notifyByPhone(String msg) {
        try {
            SendPhoneMessageUtil.sendPhoneMessage(msg);
        } catch (UnsupportedEncodingException e) {
            logger.error("调用短消息失败", e);
        }
    }
}
