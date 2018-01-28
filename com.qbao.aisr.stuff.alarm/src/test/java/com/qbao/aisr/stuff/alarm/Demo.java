package com.qbao.aisr.stuff.alarm;

import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.alarm.facade.AlaramServiceFacotryFacade;
import com.qbao.aisr.stuff.alarm.util.MessageConstanUtil;

public class Demo {
    public static void main(String[] args) {
        JSONObject json=new JSONObject();
        json.put(MessageConstanUtil.ALARM_TYPE, "mail_error");
        json.put("userId", "10001");
        json.put("className", "IGuessLikeServce:IHotSpuService");
        json.put("exec", new NullPointerException());
        AlaramServiceFacotryFacade.sendAlaramMessageByMail(json);
        for(int i=0;i<3;i++){
            System.out.println("第"+i+"封邮件发送完毕");
        }        
     }
}
