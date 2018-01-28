package com.qbao.aisr.stuff.alarm.phone.impl;

import com.qbao.aisr.stuff.alarm.phone.IPhoneAlaramMessageService;
import com.qbao.aisr.stuff.alarm.phone.common.CommonPhoneService;
/**
 *   一些系统警告级别需要通过手机发短消息
     * @author 贾红平
     * warn
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class WarnPhoneAlaramService extends CommonPhoneService implements IPhoneAlaramMessageService {
    @Override
    public void sendPhoneAlaramMessage(String message) {
        String[] phones=phones_list.split(",");
        for(String number:phones){
            executeSendPhoneMessage(number, message);
        }
    }

}
