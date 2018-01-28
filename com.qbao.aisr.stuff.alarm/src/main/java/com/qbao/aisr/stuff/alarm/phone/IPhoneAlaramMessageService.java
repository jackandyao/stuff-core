package com.qbao.aisr.stuff.alarm.phone;

import com.qbao.aisr.stuff.alarm.IAlarmMessageService;

/**
 *   通过手机短消息发送错误级别消息
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public interface IPhoneAlaramMessageService extends IAlarmMessageService {
    public abstract void sendPhoneAlaramMessage(String message);
}
