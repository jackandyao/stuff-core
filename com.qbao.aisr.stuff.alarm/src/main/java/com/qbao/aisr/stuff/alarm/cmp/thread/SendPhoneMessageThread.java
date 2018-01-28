package com.qbao.aisr.stuff.alarm.cmp.thread;

import com.qbao.aisr.stuff.alarm.cmp.http.SendPhoneMessageUtil;
/**
 *  异步发送手机短消息
     * @author 贾红平
     *
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class SendPhoneMessageThread implements Runnable{
    
    /**
     * 发送手机号码
     */
    private String phone;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 短消息内容
     */
    private String message;
    
    public SendPhoneMessageThread(String phone,String message) {
         this.phone=phone;
         this.message=message;
    }
    
    @Override
    public void run() {
        synchronized (SendPhoneMessageThread.class) {
            SendPhoneMessageUtil.executeSendPhoneMessage(phone, message);
        }
    }
    
}
