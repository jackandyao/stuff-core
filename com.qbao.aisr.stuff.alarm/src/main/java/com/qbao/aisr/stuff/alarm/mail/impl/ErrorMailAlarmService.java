package com.qbao.aisr.stuff.alarm.mail.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.alarm.mail.IMailAlarmMessageService;
import com.qbao.aisr.stuff.alarm.mail.common.CommonMailService;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *   错误级别发送邮件
     * @author 贾红平
     * $LastChangedDate: 2017-03-27 10:59:41 +0800 (Mon, 27 Mar 2017) $
     * $LastChangedRevision: 544 $
     * $LastChangedBy: liaijun $
 */
@Component
public class ErrorMailAlarmService extends CommonMailService implements IMailAlarmMessageService {

    Logger logger = Logger.getLogger(WarnMailAlarmService.class);
    
    @Override
    public void sendMailAlarmMessage(String subject, String message) {
        executeSendMailMessage(subject, message, logger, MessageConstanUtil.LOGGER_ERROR);
    }

}
