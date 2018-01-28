package com.qbao.aisr.stuff.business.alimama.service.impl;

import javax.annotation.Resource;
import javax.management.monitor.CounterMonitor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.business.alimama.service.IDownloadService;
import com.qbao.aisr.stuff.business.alimama.service.ILoginService;
import com.qbao.aisr.stuff.business.alimama.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 19:10
 * $$LastChangedDate: 2017-05-25 14:17:27 +0800 (Thu, 25 May 2017) $$
 * $$LastChangedRevision: 1003 $$
 * $$LastChangedBy: liaijun $$
 */
@Service
public class AlimamaGoodsPromotionUrlService implements IDownloadService {

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsPromotionUrlService.class);
    @Autowired
    AlimamaLoginServiceImpl iLoginService;
    @Value("${tbk.alimama.username}")
    private String userName;
    @Value("${tbk.alimama.userpass}")
    private String userPass;


    private static HttpCookies httpCookies;
    private HttpConfig httpConfig;

    @Override
    public boolean downLoad(DownloadContext context) {
        try {
            String entryUrl = context.getHttpConfig().url();
            if (httpCookies == null) {
                this.login();
            }
            httpConfig = AlimamaLoginServiceImpl.config;
//            context.getHttpConfig().context(httpCookies.getContext());
            String _tb_token_ = "";
            for (Cookie cookie : httpCookies.getCookieStore().getCookies()) {
                if (cookie.getName().equals("_tb_token_") && cookie.getDomain().equals("alimama.com")) {
                    _tb_token_ = cookie.getValue();
                }
            }
            if (StringUtils.isEmpty(_tb_token_)) {
                logger.error("_tb_token 在cookies没有获取到！");
                throw new RuntimeException("_tb_token 在cookies没有获取到！");
            }
            String url = entryUrl+ _tb_token_;
            context.setResponseContent(HttpClientUtil.send(httpConfig.url(url)));
            logger.info(context.getResponseContent());
            try {
                JSONObject jsonObject = JSON.parseObject(context.getResponseContent());
            } catch (Exception e) {
                if(context.getResponseContent().contains("亲，访问受限了")){
                    logger.error("访问受限！等待5秒钟");
                    Thread.sleep(5000);
                    return false;
                }
                logger.error("用户cookies失效");
                httpCookies = null;
                return false;
                //                Thread.sleep(5000);
            }
            return true;
        } catch (HttpProcessException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

    private synchronized void login() throws HttpProcessException, InterruptedException {
        if(httpCookies == null) {
            if (!iLoginService.login(userName, userPass)) {
                while (!iLoginService.loginWithQrCode()) {
                    Thread.sleep(5000);
                }
            }
            httpCookies = iLoginService.getCookies();
            httpConfig = iLoginService.getConfig();
            logger.info("====================登录成功-阿里妈妈==================================");
        }


    }
}
