package com.qbao.aisr.stuff.util.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.util.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *  发送手机短消息
 * @author 贾红平
 * $LastChangedDate: 2016-11-29 14:27:33 +0800 (周二, 29 十一月 2016) $
 * $LastChangedRevision: 1499 $
 * $LastChangedBy: shuaizhihu $
 */
public class SendPhoneMessageUtil {
    /**
     * 手机号码
     */
    public static final String PHONE_NUMBER = "phoneNumber";
    /**
     * 短消息网关地址
     */
    public static final String PHONE_MESSAGE_URL = "http://dw.qbao.com/send_massage/custom_info.do";
    /**
     * 发送短消息
     */
    public static final String PHONE_MESSAGE = "sendInfo";
    /**
     * 返回的数据
     */
    public static final String HTTP_DATA = "data";
    /**
     * 发送消息成功
     */
    public static final String HTTP_STATUE_SUCCESS = "success";
    /**
     * 发送消息失败
     */
    public static final String HTTP_STATUE_FAIL = "fail";
    public static Set<String> phoneMsgKeys = new HashSet<>();
    private static String[] phones = {  "18602507935", "15821805909","13601792232","13774317852" };

    /**
     *  发送短消息
     * @param phone
     * @param message
     */
    public static void executeSendPhoneMessage(String phone, String message) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 7 && hour <= 23) {
            if (!phoneMsgKeys.contains(message)) {
                phoneMsgKeys.add(message);
                String httpUrl = PHONE_MESSAGE_URL;
                String endUrl = httpUrl + "?" + PHONE_NUMBER + "=" + phone + "&" + PHONE_MESSAGE + "=" + message;
                String str = HttpClientUtil.getHttpClientInstance().sendHttpPost(endUrl);
                if (str != null) {
                    JSONObject json = JSON.parseObject(str);
                    String isData = json.getString(HTTP_DATA);
                    if (isData == HTTP_STATUE_SUCCESS) {
                        System.out.println("发送短消息成功!!");
                        //发送短消息成功
                    }
                    if (isData == HTTP_STATUE_FAIL) {
                        //发送短消息失败
                        System.out.println("发送短消息失败!!");
                    }
                } else {
                    System.out.println("发送短消息失败!");
                }
            }
        }
    }

    public static void sendPhoneMessage(String message) throws UnsupportedEncodingException {
        message = URLEncoder.encode(message,"utf-8");
        if (!phoneMsgKeys.contains(message)) {
            phoneMsgKeys.add(message);
            String httpUrl = PHONE_MESSAGE_URL;
            for (String phone : phones) {
                String endUrl = httpUrl + "?" + PHONE_NUMBER + "=" + phone + "&" + PHONE_MESSAGE + "=" + message;
                String str = HttpClientUtil.getHttpClientInstance().sendHttpPost(endUrl);
                if (str != null) {
                    JSONObject json = JSON.parseObject(str);
                    String isData = json.getString(HTTP_DATA);
                    if (isData == HTTP_STATUE_SUCCESS) {
                        System.out.println("发送短消息到" + phone + "成功!!");
                        //发送短消息成功
                    }
                    if (isData == HTTP_STATUE_FAIL) {
                        //发送短消息失败
                        System.out.println("发送短消息到" + phone + "失败!!");
                    }
                } else {
                    System.out.println("发送短消息到" + phone + "失败!");
                }
            }
        }
    }

}
