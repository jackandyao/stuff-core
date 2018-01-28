package com.qbao.aisr.stuff.alarm.mail.impl;

import org.apache.log4j.Logger;

import com.qbao.aisr.stuff.alarm.mail.IMailAlarmMessageService;
import com.qbao.aisr.stuff.alarm.mail.common.CommonMailService;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *   当部分应用的提示信息达到告警级别开始发送邮件给相关人员
 *   1 接口请求正常,但是没有数据返回
 *   2 服务器内存超过默认配置的阈值
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class WarnMailAlarmService extends CommonMailService implements IMailAlarmMessageService {
    
    Logger logger = Logger.getLogger(WarnMailAlarmService.class);
    
    @Override
    public void sendMailAlarmMessage(String subject, String message) {
        executeSendMailMessage(subject, message, logger, MessageConstanUtil.LOGGER_WARN);
    }

}
