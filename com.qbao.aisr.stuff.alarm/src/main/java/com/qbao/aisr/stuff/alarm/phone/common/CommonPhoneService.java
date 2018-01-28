package com.qbao.aisr.stuff.alarm.phone.common;
import com.qbao.aisr.stuff.alarm.cmp.thread.facade.SendThreadFacade;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;
/**
 *   执行手机发送短消息
     * @author 贾红平
     * 共用发送方法
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class CommonPhoneService {
    
    protected static String phones_list=MessageConstanUtil.PHONES_NUMBER;
    
    /**
     *  发送短消息
     * @param phone 手机号码
     * @param message 短信息
     */
    public void executeSendPhoneMessage(String phone, String message) {
        try {
            SendThreadFacade.execueteSendThreadByType(MessageConstanUtil.SEND_TYPE_PHONE, message, null, phone);
        } catch (Exception e) {
            
        }
     }
}
