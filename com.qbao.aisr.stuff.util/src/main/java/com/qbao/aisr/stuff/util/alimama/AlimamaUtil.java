package com.qbao.aisr.stuff.util.alimama;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by shuaizhihu on 2017/3/2.
 */
public class AlimamaUtil {
    public static String createConertUrlForItem(String auctionid,String adzoneid,String siteid,String token)throws UnsupportedEncodingException{
        //auctionid = URLEncoder.encode(auctionid,"UTF-8");
        //adzoneid = URLEncoder.encode(adzoneid,"UTF-8");
        //siteid = URLEncoder.encode(siteid,"UTF-8");
        //token = URLEncoder.encode(token,"UTF-8");
        /*   System.out.println("==========转换url================http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionid+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&_tb_token_="+token);*/
        return "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionid+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&scenes=1&_tb_token_="+token;
    }
    public static String createPCConertUrlForItem(String promotionURL,String adzoneid,String siteid,String token)throws UnsupportedEncodingException{
        //auctionid = URLEncoder.encode(auctionid,"UTF-8");
        //adzoneid = URLEncoder.encode(adzoneid,"UTF-8");
        //siteid = URLEncoder.encode(siteid,"UTF-8");
        //token = URLEncoder.encode(token,"UTF-8");
        /*   System.out.println("==========转换url================http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionid+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&_tb_token_="+token);*/
//    	if(StringUtils.isNotBlank(promotionURL)) {
//    		promotionURL = URLEncoder.encode(promotionURL, "UTF-8");
//        }
        return "http://pub.alimama.com/urltrans/urltrans.json?promotionURL="+promotionURL+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&_tb_token_="+token;
    }
    
    public static String couponURLForTime(Long stuffId){
    	return "http://openapi.qingtaoke.com/search?s_type=1&key_word="+stuffId+"&app_key=Hfwf26U5&page=xxx&v=1.0";
    }
    public static String createGoodsSearchURL(String catName,int size,int page) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(catName)) {
            catName = URLEncoder.encode(catName, "UTF-8");
        }
        StringBuffer urlBuffer = new StringBuffer( "http://pub.alimama.com/items/channel/tehui.json?q="+catName+"&channel=tehui");
        urlBuffer.append("&perPageSize=").append(size);
        urlBuffer.append("&toPage=").append(page);
        urlBuffer.append("&shopTag=&t=1495770703245&_t="+System.currentTimeMillis());
        return urlBuffer.toString();
    }
    public static String createGoodsSearchUrl(String catName,int startPrice,int endPrice,int startRate,int endRate,int orderNum,int size) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(catName)) {
            catName = URLEncoder.encode(catName, "UTF-8");
        }
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

    public static String filterGoodsName(String goodsName){
        return goodsName.replaceAll("<.*?>","");
    }

    public static void main(String[] args){
        System.out.println(AlimamaUtil.filterGoodsName("适用 联想2200<span class=H>定</span><span class=H>影</span><span class=H>上辊</span>7205 7250兄弟2140 7340 7450 7030加热辊"));
    }
}
