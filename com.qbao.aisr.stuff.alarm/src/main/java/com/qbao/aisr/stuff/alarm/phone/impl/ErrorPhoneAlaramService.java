package com.qbao.aisr.stuff.alarm.phone.impl;

import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.alarm.phone.IPhoneAlaramMessageService;
import com.qbao.aisr.stuff.alarm.phone.common.CommonPhoneService;
/**
 *  错误级别的手机短消息
     * @author 贾红平
     *
     * $LastChangedDate: 2017-03-27 10:59:41 +0800 (Mon, 27 Mar 2017) $
     * $LastChangedRevision: 544 $
     * $LastChangedBy: liaijun $
 */
@Component
public class ErrorPhoneAlaramService extends CommonPhoneService implements IPhoneAlaramMessageService {
    @Override
    public void sendPhoneAlaramMessage(String message) {
        String[] phones=phones_list.split(",");
        for(String number:phones){
            executeSendPhoneMessage(number, message);
        }
    }

}
