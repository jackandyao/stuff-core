package com.qbao.aisr.stuff.alarm.mail.factory;

import com.qbao.aisr.stuff.alarm.mail.IMailAlarmMessageService;
import com.qbao.aisr.stuff.alarm.mail.impl.ErrorMailAlarmService;
import com.qbao.aisr.stuff.alarm.mail.impl.InfoMailAlarmService;
import com.qbao.aisr.stuff.alarm.mail.impl.WarnMailAlarmService;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *  封装发送邮件信息服务的工厂
     * @author 贾红平
     *
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class MailAlaramServiceFactory {
    /**
     * 执行发送邮件操作
     * @param json
     * @param type
     * @param subject
     * @param message
     */
    public static void sendMailAlaramMessage(String type,String subject,String message){
        getAlarmMessageService(type).sendMailAlarmMessage(subject, message);
    }
    
    /**
     * 邮件发送服务实例
     * @param type
     * @return
     */
    private static IMailAlarmMessageService getAlarmMessageService(String type){
        IMailAlarmMessageService mailAlarmMessageService=null;
        switch (type) {
        case MessageConstanUtil.MAIL_INFO:
                mailAlarmMessageService=new InfoMailAlarmService();
            break;
        case MessageConstanUtil.MAIL_WARN:
            mailAlarmMessageService=new WarnMailAlarmService();
            break;
        case MessageConstanUtil.MAIL_ERROR:
            mailAlarmMessageService=new ErrorMailAlarmService();
            break;
        default:
            break;
        }
        return mailAlarmMessageService;
    }
}
