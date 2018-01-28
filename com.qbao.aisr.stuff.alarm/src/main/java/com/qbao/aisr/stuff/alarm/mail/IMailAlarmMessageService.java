package com.qbao.aisr.stuff.alarm.mail;

import com.qbao.aisr.stuff.alarm.IAlarmMessageService;

/**
 *   通过邮件形式发送告警,错误等级别形式通知
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public interface IMailAlarmMessageService extends IAlarmMessageService{
    
    /**
     * 通过邮件发送消息
     * @param subject
     * @param message
     */
    public abstract void sendMailAlarmMessage(String subject,String message);
}
