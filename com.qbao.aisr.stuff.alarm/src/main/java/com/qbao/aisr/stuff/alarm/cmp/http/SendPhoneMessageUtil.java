package com.qbao.aisr.stuff.alarm.cmp.http;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 *  发送手机短消息
     * @author 贾红平
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
public class SendPhoneMessageUtil {
    
    /**
     * 手机号码
     */
    public static final String PHONE_NUMBER="phoneNumber";
    /**
     * 短消息网关地址
     */
    public static final String PHONE_MESSAGE_URL="http://dw.qbao.com/send_massage/custom_info.do";
    
    /**
     * 发送短消息
     */
    public static final String PHONE_MESSAGE="sendInfo";
    
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
        String str=HttpClientUtil.getHttpClientInstance().sendHttpPost(endUrl);
        if (str!=null) {
            JSONObject json=JSON.parseObject(str);
            String isData=json.getString(HTTP_DATA);
            if (isData==HTTP_STATUE_SUCCESS) {
                System.out.println("发送短消息成功!!");
                //发送短消息成功
            }
            if (isData==HTTP_STATUE_FAIL) {
                //发送短消息失败
                System.out.println("发送短消息失败!!");
            }
        }
        else{
            System.out.println("发送短消息失败!");
        }
        
    }
}
