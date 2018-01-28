package com.qbao.aisr.stuff.alarm.phone.factory;

import com.qbao.aisr.stuff.alarm.phone.IPhoneAlaramMessageService;
import com.qbao.aisr.stuff.alarm.phone.impl.ErrorPhoneAlaramService;
import com.qbao.aisr.stuff.alarm.phone.impl.WarnPhoneAlaramService;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *  封装短消息的服务工厂
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class PhoneAlaramServiceFactory {
    
    /**
     * 执行手机短消息发送
     * @param phoneNumber
     * @param message
     * @param type
     */
    public static void sendPhoneAlaramMessage(String type,String message){
        getIPhoneAlaramMessageService(type).sendPhoneAlaramMessage(message);
    }
    
    /**
     * 封装手机短消息实例
     * @param type
     * @return
     */
    private static IPhoneAlaramMessageService getIPhoneAlaramMessageService(String type){
        IPhoneAlaramMessageService phoneAlaramMessageService=null;
        switch (type) {
        case MessageConstanUtil.PHONE_WARN:
            phoneAlaramMessageService=new WarnPhoneAlaramService();
            break;
        case MessageConstanUtil.PHONE_ERROR:
            phoneAlaramMessageService=new ErrorPhoneAlaramService();
            break;
        default:
            break;
        }
        return phoneAlaramMessageService;
    }
}
