package com.qbao.aisr.stuff.crawler.cmp.download.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlStatus;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.cmp.login.alimama.AlimamaLoginServiceImpl;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 10:11
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="alimamaGoodsDownloadService")
public class AlimamaGoodsDownloadService implements IDownloadService {

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsDownloadService.class);

    @Override
    public boolean downLoad(DownloadContext context) throws CrawlerException {
        try {
            context.setResponseContent(HttpClientUtil.send(context.getHttpConfig()));
            JSONObject jsonObject = JSON.parseObject(context.getResponseContent());
            if(jsonObject.containsKey("status")) {
                if(jsonObject.getInteger("status")==1111){
                    logger.info("网络拥堵！请求失败!");
                    return false;
                }
            }
//            else  if(jsonObject.containsKey("invalidKey")){
//                logger.info("无效的key!!!url为:"+context.getHttpConfig().url());
//                return false;
//            }
            else if(jsonObject.containsKey("data")){
                logger.info(context.getResponseContent());
                context.setDlStatus(DlStatus.SUCCESS);
                return true;
            }
            logger.info(context.getResponseContent());
            return false;

        } catch (HttpProcessException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            context.setDlStatus(DlStatus.FAIL);
            return false;
        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            context.setDlStatus(DlStatus.FAIL);
            return false;
        }

    }


    public static void main(String[] args) throws HttpProcessException, CrawlerException {
                IDownloadService cmp = new AlimamaGoodsDownloadService();
                String[] itemIds = new String[]{"9305297"};
                ILoginService iLoginService = new AlimamaLoginServiceImpl();
                if(iLoginService.loginWithQrCode()){
                    HttpCookies cookies = iLoginService.getCookies();
                    String _tb_token_ = "";
                    for (Cookie cookie : cookies.getCookieStore().getCookies()) {
                        System.out.println(cookie.toString());
                        if(cookie.getName().equals("_tb_token_")&&cookie.getDomain().equals("alimama.com")){
                            _tb_token_=cookie.getValue();
                        }
                    }
                    DownloadContext crawlerContext = new DownloadContext(iLoginService.getConfig());
                    while(true) {
                        for (String id : itemIds) {
                            /*String converUrl = AlimamaUtil.createConertUrlForItem(id, "71086882", "21068365", _tb_token_);
                            System.out.println(converUrl);
                            crawlerContext.getHttpConfig().url(converUrl);
                            cmp.downLoad(crawlerContext);
                            System.out.println(crawlerContext.getResponseContent());
                            crawlerContext.setResponseContent("");*/
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("登录失败！");
                }
            }

    }
