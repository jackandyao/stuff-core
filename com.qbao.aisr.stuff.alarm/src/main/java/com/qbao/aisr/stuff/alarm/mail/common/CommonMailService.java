package com.qbao.aisr.stuff.alarm.mail.common;

import org.apache.log4j.Logger;

import com.qbao.aisr.stuff.alarm.cmp.thread.facade.SendThreadFacade;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

/**
 *   通过邮件发送不同级别消息
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class CommonMailService {
    public void executeSendMailMessage(String subject, String message,Logger logger,String level) {    
        try {
            SendThreadFacade.execueteSendThreadByType(MessageConstanUtil.SEND_TYPE_MAIL, message, subject, null);
         } catch (Exception e) {
            logger.error(e);
        }
    }
    
    
}
