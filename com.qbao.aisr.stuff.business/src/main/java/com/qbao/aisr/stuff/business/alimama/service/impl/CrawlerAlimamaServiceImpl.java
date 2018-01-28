package com.qbao.aisr.stuff.business.alimama.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.business.alimama.service.ICrawlerAlimamaService;
import com.qbao.aisr.stuff.business.alimama.service.IDownloadService;
import com.qbao.aisr.stuff.business.alimama.service.IParseService;
import com.qbao.aisr.stuff.business.alimama.util.*;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.model.mysql.TaokeDetail;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffKeywordDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.repository.redis.cache.annotation.CacheType;
import com.qbao.aisr.stuff.repository.redis.cache.annotation.RedisCache;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;

/**
 *
 */
@Service
public class CrawlerAlimamaServiceImpl implements ICrawlerAlimamaService {
    @Autowired
    StuffKeywordDao stuffKeywordDao;
    @Autowired
    private  AlimamaGoodsPromotionUrlService iDownloadService;
    @Autowired
    private StuffPromotionTmpDao stuffPromotionTmpDao;
    @Value("${tbk.name}")
    private String name;
    Logger logger= Logger.getLogger(CrawlerAlimamaServiceImpl.class);
    @RedisCache(expire = 60*60*24*10,clazz =String.class,cacheType = CacheType.OBJECT)
    public  String createConertUrlForItem(Long auctionid,String adzoneid,String siteid,String source,int device,String name)throws Exception{
        String url="";
        try {
            StuffPromotion stuffPromotion = stuffPromotionTmpDao.findStuffPromotionByRealStuffIdAndSource(auctionid,source);
            if(stuffPromotion!=null) {
                if(Utils.PHONETYPE_IOS==device) {
                    String iosUrl=stuffPromotion.getIosPromotionUrl();
                    if(StringUtils.isNotBlank(iosUrl)&&!Utils.FUHAO.equals(iosUrl)){
                        return iosUrl;
                    }
                }else {
                    String androidUrl=stuffPromotion.getAndroidPromotionUrl();
                    if(StringUtils.isNotBlank(androidUrl)&&!Utils.FUHAO.equals(androidUrl)){
                        return androidUrl;
                    }
                }
            }

            url=setUrl( auctionid, adzoneid, siteid);
            //商品没有转链接保存到库里
            saveKeyword( 0L, name,  source,  0);
        }catch (Exception ex){
           logger.error(ex.getMessage(),ex);
        }

        return url;

    }

    public void saveKeyword(long userId, String kw, String source, int total) {
        try {
            Date nowTime = new Date();
            stuffKeywordDao.saveStuffKeyword(userId, kw, source, nowTime, nowTime, total);
        } catch (Exception ex) {
            logger.error("用户查询商品时没有转链接时保存到库报错:" + ex.getMessage(), ex);
        }
    }

    @RedisCache(expire = 60*60*24*10,clazz =String.class,cacheType = CacheType.OBJECT)
    public String setUrl(Long auctionid,String adzoneid,String siteid) throws Exception{
        //因为正式库里数据多是转链接转好的,如果查询不到就说明还没正式入库.

        String url = this.createConertUrlForItem("" + auctionid, adzoneid, siteid);


        return url;
    }

    @RedisCache(expire = 60*2,clazz =String.class,cacheType = CacheType.OBJECT)
    public  String createConertUrlForItem(String auctionid,String adzoneid,String siteid)throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //IDownloadService iDownloadService =new AlimamaGoodsPromotionUrlService();
        long start = System.currentTimeMillis();
        logger.info("Starting alimama PromotionUrlRunner job ...");
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date y = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -89);
        Date s = cal.getTime();
        //String url = "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid=84237539&adzoneid=76562672&siteid=23088972&scenes=1&_tb_token_=";
        String url="http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionid+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&scenes=1&_tb_token_=";
        logger.info("alimama  url :  " +url);
        DownloadTask<String> downloadTask = new DownloadTask<>();
        downloadTask.setData(format.format(now));
        downloadTask.setEntryUrl(url);
        downloadTask.setMethod(DlMethod.GET);

        logger.info("AlimamaServiceImpl  DownloadContext statrt <<<<<<<<<<<<<<<  url=" +url);
        DownloadContext context = new DownloadContext(HttpConfig.custom());
        context.setTask(downloadTask);
        iDownloadService.downLoad(context);

        logger.info("AlimamaServiceImpl  ParseContext statrt <<<<<<<<<<<<<<<  url=" +url);
        ParseTask parseTask = new ParseTask();
        parseTask.setContent(context.getResponseContent());
        ParseContext<TaokeDetail> parseContext = new ParseContext<>(parseTask);
        String jsonString = parseContext.getParseTask().getContent();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        String clickUrl ="";
        if(dataObject.containsKey("clickUrl")) {
            clickUrl = dataObject.getString("clickUrl");
            logger.info("clickUrl="+clickUrl);
        }
        logger.info("finished alimama PromotionUrlRunner job and cost " + (System.currentTimeMillis() - start) / 1000 + ".s");
        return clickUrl;
    }


    @RedisCache(expire = 60*2,clazz =StuffPromotion.class,cacheType = CacheType.LIST)
    public List<StuffPromotion> createGoodsSearchUrl(String catName,String catId,int startPrice,int endPrice,int startRate,int endRate,int orderNum,int size) throws Exception {


        IDownloadService iDownloadService = new AlimamaGoodsDownloadService();
        IParseService iParseService = new AlimamaGoodsParseService();
        long start = System.currentTimeMillis();
        logger.info("Starting alimama PromotionUrlRunner job ...");

        //String url = "http://pub.alimama.com/items/search.json?q=ffu&_t=1494916464863&toPage=1&b2c=1&auctionTag=&perPageSize=40&shopTag=b2c&t=1494916838317&_tb_token_=CLPlk10ocq&pvid=10_101.81.23.1_633_1494916834879";
        String url= AlimamaUtil.createGoodsSearchUrl(catName,startPrice,endPrice,startRate,endRate,orderNum,size);
        logger.info("alimama  url :  " +url);
        DownloadTask<StuffCategory> task = new DownloadTask<StuffCategory>();
        task.setEntryUrl(url);
        task.setMethod(DlMethod.GET);
        StuffCategory sc=new StuffCategory();
        sc.setCatId(catId);



        DownloadContext context = new DownloadContext(HttpConfig.custom());
        context.setTask(task);
        iDownloadService.downLoad(context);
        ParseTask parseTask = new ParseTask();
        parseTask.setContent(context.getResponseContent());
        parseTask.setData(sc);
        ParseContext<StuffPromotion> parseContext = new ParseContext<>(parseTask);
        iParseService.parse(parseContext);
        logger.info("finished alimama OrderDetails job and cost " + (System.currentTimeMillis() - start) / 1000 + ".s");

        return parseContext.getParseResult();
    }


    public  String getGoodsSearchUrl(String catName,int startPrice,int endPrice,int startRate,int endRate,int orderNum,int size) throws Exception {
        catName = URLEncoder.encode(catName,"UTF-8");
        StringBuffer urlBuffer = new StringBuffer( "http://pub.alimama.com/items/search.json?q="+catName);
        urlBuffer.append("&perPageSize=").append(size);
        if(orderNum>0) {
            urlBuffer.append("&startBiz30day=").append(orderNum);
        }
        if(startPrice>0) {
            urlBuffer.append("&startPrice=").append(startPrice);
        }
        if(endPrice>0) {
            urlBuffer.append("&endPrice=").append(endPrice);
        }
        if(startRate>0) {
            urlBuffer.append("&startTkRate=").append(startRate);
        }
        if(endRate>0){
            urlBuffer.append("&endTkRate=").append(endRate);
        }
        urlBuffer.append("&_t="+System.currentTimeMillis());
        return urlBuffer.toString();
    }

    public static void main(String[] args){
        CrawlerAlimamaServiceImpl s=new CrawlerAlimamaServiceImpl();
        try {
            //System.out.println(s.createGoodsSearchUrl("计算器", "106103104101", 30, 50, 10, 99, 30, 100));
            //System.out.println(s.createConertUrlForItem("84237539","76562672","23088972"));
            System.out.println(s.getCouponInfo("雪纺阔腿裤女九分裤百褶裙裙裤喇叭裤"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public List<StuffPromotion> getCouponInfo(String kw) throws Exception {


        IDownloadService iDownloadService = new AlimamaGoodsDownloadService();
        IParseService iParseService = new AlimamaGoodsCouponParseService();
        long start = System.currentTimeMillis();
        logger.info("Starting alimama PromotionUrlRunner job ...");

        String url = "http://openapi.qingtaoke.com/search?s_type=1&app_key=Hfwf26U5&v=1.0&key_word="+kw;
        logger.info("alimama  url :  " +url);
        DownloadTask<StuffCategory> task = new DownloadTask<StuffCategory>();
        task.setEntryUrl(url);
        task.setMethod(DlMethod.GET);





        DownloadContext context = new DownloadContext(HttpConfig.custom());
        context.setTask(task);
        iDownloadService.downLoad(context);
        ParseTask parseTask = new ParseTask();
        parseTask.setContent(context.getResponseContent());

        ParseContext<StuffPromotion> parseContext = new ParseContext<>(parseTask);
        iParseService.parse(parseContext);
        logger.info("finished alimama OrderDetails job and cost " + (System.currentTimeMillis() - start) / 1000 + ".s");

        return parseContext.getParseResult();
    }

}
