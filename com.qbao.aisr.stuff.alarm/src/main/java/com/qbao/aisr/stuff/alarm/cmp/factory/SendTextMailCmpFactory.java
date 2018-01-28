package com.qbao.aisr.stuff.alarm.cmp.factory;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *   发送邮件实例工厂,本身应该使用基于spring注入形式来开发的,
 *   但是为了考虑整体代码以及该组件的绝对隔离,抛弃一切外界因素,
 *   所以一些东西就没有通过配置来
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class SendTextMailCmpFactory {
    
    private static JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
    /**
     * 发送简单文本格式内容
     */
    public static void sendMailSimpleTextMsg(String subject,String message){
        senderImpl.setHost(MessageConstanUtil.MAIL_HOST_NAME);
        SimpleMailMessage mailMessage = new SimpleMailMessage();  
        mailMessage.setTo(MessageConstanUtil.MAIL_TO_USER);  
        mailMessage.setFrom(MessageConstanUtil.MAIL_FROM_USER);  
        mailMessage.setSubject(subject);  
        mailMessage.setText(message);  
        senderImpl.setUsername(MessageConstanUtil.MAIL_USER_NAME); 
        senderImpl.setPassword(MessageConstanUtil.MAIL_PASS_WORD);       
        senderImpl.send(mailMessage);  
        System.out.println("邮件发送成功..");  
    }
    
    
    
    /**
     * 发送简单html格式内容
     */
    
    
    /**
     * 发送带附件的格式内容
     */
    
     
}
