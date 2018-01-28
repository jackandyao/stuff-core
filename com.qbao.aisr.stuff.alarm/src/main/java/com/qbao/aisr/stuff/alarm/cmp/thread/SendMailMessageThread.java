package com.qbao.aisr.stuff.alarm.cmp.thread;

import com.qbao.aisr.stuff.alarm.cmp.factory.SendTextMailCmpFactory;
/**
 *  异步发送邮件
     * @author 贾红平
     *
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class SendMailMessageThread implements Runnable{

    /**
     * 发送邮件标题
     */
    private String subject;
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * 发送邮件正文
     */
    private String message;
    
    public SendMailMessageThread(String subject,String message) {
         this.subject=subject;
         this.message=message;
    }
    
    @Override
    public void run() {
        synchronized (SendMailMessageThread.class) {
            SendTextMailCmpFactory.sendMailSimpleTextMsg(subject, message);
            
        }
    }
    
}
