/*
 * Copyright (C), 2002-2016
 * FileName: MD5.java
 * Author:   luwanchuan
 * Date:     2016年4月14日 上午10:40:33
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.qbao.aisr.stuff.util.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5操作
 *
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MD5 {

    private static Logger LOGGER = LoggerFactory.getLogger(MD5.class);

    /**
     * 使用ThreadLocal以空间换时间解决SimpleDateFormat线程安全问题
     */
    @SuppressWarnings("rawtypes")
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            MessageDigest messagedigest = null;
            try {
                messagedigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("初始化失败，MessageDigest不支持MD5Util", e);
            }
            return messagedigest;
        }
    };

    public static MessageDigest getMessageDigest() {
        return (MessageDigest) threadLocal.get();
    }

    public static String digest(String s, String charset) throws UnsupportedEncodingException {
        getMessageDigest().update(s.getBytes(charset));
        return HexUtil.bytes2Hexstr(getMessageDigest().digest());
    }

}
