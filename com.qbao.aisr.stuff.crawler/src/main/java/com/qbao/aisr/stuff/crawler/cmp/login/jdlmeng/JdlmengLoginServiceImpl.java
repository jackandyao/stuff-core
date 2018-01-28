package com.qbao.aisr.stuff.crawler.cmp.login.jdlmeng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpHeader;
import com.qbao.aisr.stuff.crawler.cmp.http.common.util.IpDeal;
import com.qbao.aisr.stuff.crawler.cmp.http.common.util.OCR;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.util.regex.RegexUtil;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
@Service
public class JdlmengLoginServiceImpl implements ILoginService {

    Logger logger = LoggerFactory.getLogger(JdlmengLoginServiceImpl.class);

    private String loginUrl = "https://passport.jd.com/common/loginPage?from=media&ReturnUrl=http://media.jd.com/index/overview";
    private String submitUrl = "https://passport.jd.com/uc/loginService";
    private boolean isLogined = false;
    private HttpConfig config = null;
    private HttpCookies cookies = null;

    private Header[] currentHeaders;
    public Header[] getHeaderArr(){
        currentHeaders = HttpHeader.custom().userAgent(IpDeal.getUserAgent()).acceptLanguage("zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").accept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8").acceptEncoding("gzip, deflate, br").build();
        return currentHeaders;
    }

    public boolean login(String userName, String userPass) throws HttpProcessException {
        cookies= HttpCookies.custom();
        config =HttpConfig.custom().url(loginUrl).context(cookies.getContext());
        config.ip(IpDeal.getIp());
        String loginHtml = HttpClientUtil.get(config.headers(getHeaderArr()));

        Document htmlDocument = Jsoup.parse(loginHtml);
        Element element = htmlDocument.getElementById("formloginframe");
        Map<String, Object> map = new HashMap<String, Object>();
        if(element == null){
            logger.error("京东联盟登录！ 没有找到登录from元素，登录失败！");
            return false;
        }

        Element verifyCode = element.getElementById("JD_Verification1");
        if(verifyCode!=null){
            String vc = verifyCode.attr("src");
            if(vc!=null&&vc.length()>0){
                return false;//需要验证码则直接返回，进入下一次登录
                /*try {
                    verifyUtil(vc.trim());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }

        }
        Elements inputs = element.getElementsByTag("input");
        Iterator<Element> ins = inputs.iterator();
        while(ins.hasNext()){
            Element in = ins.next();
            if(!in.attr("name").equals("")) {
                map.put(in.attr("name"), in.val());
            }
        }
        map.put("loginname", userName);
        map.put("nloginpwd", userPass);
        //map.put("authcode","");

        config.url(submitUrl);
        String result = HttpClientUtil.post(config.map(map));
        if(result!=null&&result.contains("emptyAuthcode")){
            return false;
        }
        return true;
    }

    @Override
    public HttpCookies getCookies() {
            return this.cookies;
    }

    @Override
    public boolean loginWithQrCode() throws HttpProcessException {
              return true;
    }

    @Override
    public HttpConfig getConfig() {
        return config;
    }
    public String verifyUtil(String path) throws InterruptedException, HttpProcessException {
        String saveCodePath = "E:/verifycode/"+Math.random()+".png";//保存验证码图片路径
        HttpConfig config = HttpConfig.custom().headers(currentHeaders).context(HttpCookies.custom().getContext());//必须设置context，是为了携带cookie进行操作
        String result =null;//识别结果
        do {
            if(result!=null){
                System.err.println("校验失败！稍等片刻后继续识别");
                Thread.sleep((int)(Math.random()*10)*100);
            }
            //获取验证码
            //OCR.debug(); //开始Fiddler4抓包（127.0.0.1:8888）
            String code = OCR.ocrCode4Net(config.url(path), saveCodePath);
            //----------------------------在这打断点------------------------------------------------------------------------------
            while(code.length()!=4){//如果识别的验证码位数不等于5，则重新识别
                if(code.contains("亲,apiKey已经过期或错误,请重新获取")){
                    System.err.println(code);
                    return "";
                }
                code = OCR.ocrCode4Net(config.url(path), saveCodePath);
            }

            System.out.println("本地识别的验证码为："+code);
            System.out.println("验证码已保存到："+saveCodePath);

            System.out.println("开始校验验证码是否正确");
            //开始验证识别的验证码是否正确
            //result = HttpClientUtil.get(config.url(verifyUrl+"?vc="+code+"&qqid="+qq));

        //} while (!result.contains("succeed"));
        } while (false);

        System.out.println("识别验证码成功！反馈信息如下：\n" + result);
        return result;
    }


}
