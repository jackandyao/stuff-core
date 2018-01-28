package com.qbao.aisr.stuff.crawler;

import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.cmp.login.jdlmeng.JdlmengLoginServiceImpl;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.jdlmeng.JdlmengCrawlerAbstractFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/6 12:08
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring_crawler.xml"})
public class JdlmengCrawlerServiceTest {

    @Autowired
    CrawlerService crawlerService;

    @Test
    public void test() throws Exception {
        //HttpCookies httpCookies = loginJdlmeng();
        CrawlerAbstractFactory crawlerAbstractFactory = new JdlmengCrawlerAbstractFactory();
        crawlerService.init(crawlerAbstractFactory);
        crawlerService.start(crawlerAbstractFactory);
    }
    /*public HttpCookies loginJdlmeng(){
        HttpCookies httpCookies = null;
        try {
            ILoginService loginService = new JdlmengLoginServiceImpl();
            //loginService.loginWithQrCode();
            boolean loginResult = loginService.login("mtmengtian","mengtian@1314");
            httpCookies = loginService.getCookies();
            String rs = "";
            if(loginResult){
                rs = "登录成功";
            }else{
                rs = "登录失败";
            }
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return httpCookies;
    }*/
}
