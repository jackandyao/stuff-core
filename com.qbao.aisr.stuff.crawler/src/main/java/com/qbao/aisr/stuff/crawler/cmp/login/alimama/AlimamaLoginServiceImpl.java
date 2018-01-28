package com.qbao.aisr.stuff.crawler.cmp.login.alimama;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpHeader;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.util.message.SendPhoneMessageUtil;
import com.qbao.aisr.stuff.util.regex.RegexUtil;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
@Service(value="alimamaLoginServiceImpl")
public class AlimamaLoginServiceImpl implements ILoginService {

    private String[] agents = {"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1623.0 Safari/537.36","Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1464.0 Safari/537.36\n","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.17 Safari/537.36"};

    Logger logger = LoggerFactory.getLogger(AlimamaLoginServiceImpl.class);
    Random random = new Random();

    @Value("${tbk.alimama.phone}")
    private String userPhone;

    private String loginUrl = "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http://www.alimama.com&full_redirect=true&disableQuickLogin=true";
    private boolean isLogined = false;
    private HttpConfig config = null;
    private HttpCookies cookies = null;
    String agent = randomGetAgent(agents);
    Header[] headers = HttpHeader.custom().userAgent(agent).build();
    @Override
    public boolean login(String userName, String userPass) throws HttpProcessException {
        cookies= HttpCookies.custom();
        config =HttpConfig.custom().url(loginUrl).context(cookies.getContext());
        String loginHtml = HttpClientUtil.get(config.headers(headers));
        Document htmlDocument = Jsoup.parse(loginHtml);
        Element element = htmlDocument.getElementById("J_Form");
        Map<String, Object> map = new HashMap<String, Object>();
        if(element == null){
            logger.error("阿里妈妈登录！ 没有找到登录from元素，登录失败！");
            return false;
        }
        Elements divs = element.getElementsByClass("submit");
        if(divs == null){
            logger.error("阿里妈妈登录！ 没有找到登录submit元素，登录失败！");
            return false;
        }
        Iterator<Element> es = divs.iterator();
        if(es.hasNext()){
            Elements inputs = es.next().getElementsByTag("input");
            Iterator<Element> ins = inputs.iterator();
            while(ins.hasNext()){
                Element in = ins.next();
                if(!in.attr("name").equals("")) {
                    map.put(in.attr("name"), in.val());
                }
            }
        }
        map.put("TPL_username", userName);
        map.put("TPL_password", userPass);
        String result = HttpClientUtil.post(config.map(map));
        System.out.println(result);
        String regex = "top.location.href = \"(.*?)\"";
        String[] loginCallUrls = RegexUtil.regex(regex,result);
        if(loginCallUrls.length<1){
            logger.info("阿里妈妈登录！ 没有找到回调url元素！");
//            regex = "self.location.href = \"(.*?)\"";
//            String[] selfCallUrls = RegexUtil.regex(regex,result);
//            if(selfCallUrls.length>0){
//                logger.info("阿里妈妈登录！ 本次登录需要人工验证！ 验证url为："+selfCallUrls[0]);
//                result = HttpClientUtil.get(config.url(selfCallUrls[0]).headers(headers).context(cookies.getContext()));
//                System.out.println(result);
//            }
            return false;
        }
        result = HttpClientUtil.get(config.url(loginCallUrls[0]).headers(headers).context(cookies.getContext()));
//        System.out.println(result);
        isLogined = true;
        return true;
    }

    @Override
    public HttpCookies getCookies() {
            return this.cookies;
    }

    @Override
    public boolean loginWithQrCode() throws HttpProcessException {
        cookies= HttpCookies.custom();
        config =HttpConfig.custom().url("https://qrlogin.taobao.com/qrcodelogin/generateQRCode4Login.do?from=alimama").context(cookies.getContext());
        String qrCodeJson = HttpClientUtil.get(config.headers(headers));
        logger.info("qrCodeJson="+qrCodeJson);
        JSONObject jsonObject = JSON.parseObject(qrCodeJson);
        String lgToken = jsonObject.getString("lgToken");
        String adToken = jsonObject.getString("adToken");

        boolean isXh = true;
        int i=0;
        while(isXh){
            logger.info("扫码图片："+jsonObject.getString("url"));
            String checkJson = HttpClientUtil.get(config.url("https://qrlogin.taobao.com/qrcodelogin/qrcodeLoginCheck.do?lgToken="+lgToken+"&adToken="+adToken+"&time="+System.currentTimeMillis()+"&&defaulturl=https%3A%2F%2Fwww.alimama.com"));
            JSONObject checkObject = JSON.parseObject(checkJson);
            String code = checkObject.getString("code");
            if(code.equals("10000")){
                logger.info("等待扫码！");
                if(i == 0){
                    logger.info("扫码手机: "+userPhone  );
                    SendPhoneMessageUtil.executeSendPhoneMessage(userPhone,"http:"+jsonObject.getString("url"));
                }
                i++;
            }else if(code.equals("10001")){
                logger.info("扫码成功，等待确认登录");
            }else if(code.equals("10004")){
                logger.info("二维码过期，需要重新登录");
                isXh = false ;
                //发送短信
                SendPhoneMessageUtil.executeSendPhoneMessage(userPhone,"二维码登录已过期!重新扫码！");
                return false;
            }else if(code.equals("10006")){
                String qurenUrl = checkObject.getString("url");
                logger.info("确认登陆! 确认url：" +qurenUrl);
                String loginHtml = HttpClientUtil.get(config.url(qurenUrl));
                logger.info(loginHtml);
                isXh = false ;
                break;
            }else{
                return false;
            }

            try {
                Thread.sleep(random.nextInt(10000)+5000);
            } catch (InterruptedException e) {
               logger.error(e.getMessage());
            }

        }

        return true;
    }
    private String randomGetAgent(String[] agents) {
        int index = random.nextInt(agents.length);
        String reslult = index > -1 && index < agents.length ? agents[index] :agents[0];
        logger.info("get agent : " + reslult);
        return  reslult;
    }
    @Override
    public HttpConfig getConfig() {
        return config;
    }


}
