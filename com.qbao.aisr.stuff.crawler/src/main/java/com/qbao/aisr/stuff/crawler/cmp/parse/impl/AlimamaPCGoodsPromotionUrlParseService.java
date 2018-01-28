package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.CommonUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpHeader;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;


/**
 * @author shuaizhihu
 * @createTime 2017/3/13 19:34
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="alimamaPCGoodsPromotionUrlParseService")
public class AlimamaPCGoodsPromotionUrlParseService implements IParseService {
	 @Value("${tbk.pc.site.id}")
    private String tbkPCSiteId;
    @Value("${tbk.pc.adzone.id}")
    private String tbkPCAdzoneId;
	private static String clazz = AlimamaPCGoodsPromotionUrlParseService.class.getSimpleName();
	Logger logger = LoggerFactory.getLogger(AlimamaPCGoodsPromotionUrlParseService.class);
	Header[] headers = HttpHeader.custom().userAgent("User-Agent: Mozilla/5.0").build();
    @Override
    public boolean parse(ParseContext context) {
        String jsonString = context.getParseTask().getContent();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        String url = jsonObject.getString("url");
        if(dataObject!=null&&dataObject.containsKey("clickUrl")) {
            String clickUrl = dataObject.getString("clickUrl");
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            StuffPromotion stuffPromotion = new StuffPromotion();
            StuffPromotion old = (StuffPromotion) context.getParseTask().getData();
            stuffPromotion.setId(old.getId());
            stuffPromotion.setPcPromotionUrl(clickUrl);


            list.add(stuffPromotion);
            context.setParseResult(list);
            return true;
        }else if(StringUtils.isNotEmpty(url)){
        	String sessionId=getSessionId(url);
            String text=validateCode(sessionId);
    		JSONObject codeObj = JSON.parseObject(text);
    		if(codeObj!=null&&codeObj.containsKey("result")){
    		  String code=codeObj.getString("result");
    		  String submit="http://pin.aliyun.com/check_img?_ksTS=1497168071589_51&callback=jsonp52&identity=sm-union-pub&sessionid="+sessionId+"&delflag=0&type=default&isg=AubmTv8ug03UvFfgBGpWc4WXN1qoby_kWw-yJ9CO5InkU45tPVc1kVpl3Xil&code="+code;
    		  HttpCookies cookies= HttpCookies.custom();
    	      HttpConfig config =HttpConfig.custom().url(submit).context(cookies.getContext());
              String codeString="";
              String clickString ="";
			  try {
				codeString = HttpClientUtil.get(config.headers(headers));
				StuffPromotion stuffPromotion = (StuffPromotion) context.getParseTask().getData();
    			String clikUrl=AlimamaUtil.createConertUrlForItem(stuffPromotion.getRealStuffId()+"",tbkPCAdzoneId,tbkPCSiteId,"");
            	config =HttpConfig.custom().url(clikUrl).context(cookies.getContext());
                clickString = HttpClientUtil.get(config.headers(headers));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
    			
    			logger.info(">>>>>>>>>>>>>>>>>>>>codeString="+codeString);
    			logger.info(">>>>>>>>>>>>>>>>>>>>clickString="+clickString);
    		}

        	logger.info(">>>>>>>>>>>>>>>>>>>>url="+url);
        	return false;
        }else{
            return false;
        }
    }
    
    
    
    public String getSessionId(String url){
    	HttpCookies cookies= HttpCookies.custom();
    	HttpConfig config =HttpConfig.custom().url(url).context(cookies.getContext());
        String loginHtml="";
		try {
			loginHtml = HttpClientUtil.get(config.headers(headers));
		} catch (HttpProcessException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSON.parseObject(loginHtml);
        String pic = jsonObj.getString("url");
        logger.info(">>>>>>>>>>>>>>>>>>>>pic="+pic);
        config =HttpConfig.custom().url(pic).context(cookies.getContext());
        String picHtml="";
		try {
			picHtml = HttpClientUtil.get(config.headers(headers));
		} catch (HttpProcessException e) {
			e.printStackTrace();
		}
        Document htmlDocument = Jsoup.parse(picHtml);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> htmlDocument="+htmlDocument);
        String sessionId="";
        /*取得script下面的JS变量*/  
        Elements e = htmlDocument.getElementsByTag("script");
        /*循环遍历script下面的JS变量*/  
        for (Element element : e) { 
        	String[] ids=element.data().toString().split("sessionid");
        	if(ids.length>1){
            sessionId=ids[1];
        	sessionId=sessionId.substring(3, 35);
        	break;
        	}
        	
        }
       
        logger.info(">>>>>>>>>>>>>>> for后 变量sessionid＝"+sessionId );
        return sessionId;
    }
    
    
    
    public String validateCode(String sessionId){
    	String uuId= UUID.randomUUID().toString();
        String picImg="http://pin.aliyun.com/get_img?identity=sm-union-pub&sessionid="+sessionId+"&type=default&t="+System.currentTimeMillis();
        CommonUtil.downloadImage(picImg, clazz,uuId);
        new File("img/" + clazz).mkdirs();
		String img="img/" + clazz + "/" + uuId + ".jpg";
		String text="";
		try {
			text = JuheDemo.post("8001",new File(img));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>验证码 code="+text);
		return text;
    }
}
