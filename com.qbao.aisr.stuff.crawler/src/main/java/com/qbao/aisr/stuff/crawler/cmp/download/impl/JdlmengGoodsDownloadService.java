package com.qbao.aisr.stuff.crawler.cmp.download.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.exception.LoginFailureException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xueming
 * @createTime 2017/3/17 19:10
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="jdlmengGoodsDownloadService")
public class JdlmengGoodsDownloadService implements IDownloadService {

    Logger logger = LoggerFactory.getLogger(JdlmengGoodsDownloadService.class);

    @Resource(name = "jdlmengLoginServiceImpl")
    ILoginService iLoginService;

    @Value("${jd.jdlmeng.username}")
    private String userName;
    @Value("${jd.jdlmeng.userpass}")
    private String userPass;

    private static HttpCookies httpCookies;
    private  HttpConfig httpConfig;

    @Override
    public boolean downLoad(DownloadContext context) {
        try {
            String entryUrl = context.getHttpConfig().url();
            if (httpCookies == null) {
                this.login();
            }
            httpConfig = iLoginService.getConfig();
            String url = entryUrl;
            context.setResponseContent(HttpClientUtil.send(httpConfig.url(url).context(httpCookies.getContext())));
            //logger.info(context.getResponseContent());
            return true;
        } catch (LoginFailureException e) {
            logger.error(e.getMessage());
            return false;
        } catch (HttpProcessException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }
    private synchronized void login() throws LoginFailureException,HttpProcessException, InterruptedException {
        if(httpCookies == null) {
            if (!iLoginService.login(userName, userPass)) {
                throw new LoginFailureException("=================京东联盟登录失败=============================");
            }
            httpCookies = iLoginService.getCookies();
            httpConfig = iLoginService.getConfig();
        }




    }
}
