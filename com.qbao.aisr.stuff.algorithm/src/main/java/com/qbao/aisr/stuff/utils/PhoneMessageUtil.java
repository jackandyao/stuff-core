package com.qbao.aisr.stuff.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class PhoneMessageUtil {
	/**
     * 短消息网关地址
     */
    public static final String PHONE_MESSAGE_URL="http://dw.qbao.com/send_massage/custom_info.do";
    /**
     * 发送短消息
     */
    public static final String PHONE_MESSAGE="sendInfo";
    /**
     * 手机号码
     */
    public static final String PHONE_NUMBER="phoneNumber";
    /**
     * 返回的数据
     */
    public static final String HTTP_DATA="data";
    
    /**
     * 发送消息成功
     */
    public static final String HTTP_STATUE_SUCCESS="success";
    
    /**
     * 发送消息失败
     */
    public static final String HTTP_STATUE_FAIL="fail";
    /**
     *  发送短消息
     * @param phone
     * @param message
     */
    public static void executeSendPhoneMessage(String phone, String message) {
        String httpUrl=PHONE_MESSAGE_URL;
        String endUrl=httpUrl+"?"+PHONE_NUMBER+"="+phone+"&"+PHONE_MESSAGE+"="+message;
        String str=HttpUtil.getHttpResponse(endUrl);
        JSONObject json=JSON.parseObject(str);
        String isData=json.getString(HTTP_DATA);
        if (isData==HTTP_STATUE_SUCCESS) {
            
        }
        if (isData==HTTP_STATUE_FAIL) {
            
        }
        
    }
}
