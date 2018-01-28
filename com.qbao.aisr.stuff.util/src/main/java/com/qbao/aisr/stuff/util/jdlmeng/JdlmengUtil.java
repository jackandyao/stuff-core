package com.qbao.aisr.stuff.util.jdlmeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuaizhihu on 2017/3/2.
 */
public class JdlmengUtil {
    public static String createConertUrlForItem(String auctionid,String adzoneid,String siteid,String token){
        return "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionid+
                "&adzoneid="+adzoneid+"&siteid="+siteid
                +"&_tb_token_="+token;
    }


    public static String createGoodsSearchUrl(String catName,int startPrice,int endPrice,int startRate,int endRate,int orderNum,int size) throws UnsupportedEncodingException {
        //catName = URLEncoder.encode(catName,"UTF-8");
        StringBuffer urlBuffer = new StringBuffer("https://media.jd.com/gotoadv/goods?category1=0&keyword="+catName);
        urlBuffer.append("&pageSize=").append(50);
        /*if(orderNum>0) {
            urlBuffer.append("&startBiz30day=").append(orderNum);
        }*/
        /*if(startPrice>0) {
            urlBuffer.append("&fromPrice=").append(startPrice);
        }
        if(endPrice>0) {
            urlBuffer.append("&toPrice=").append(endPrice);
        }*/
        /*if(startRate>0) {
            urlBuffer.append("&startTkRate=").append(startRate);
        }
        if(endRate>0){
            urlBuffer.append("&endTkRate=").append(endRate);
        }*/
        //urlBuffer.append("&_t="+System.currentTimeMillis());
        //return "https://passport.jd.com/gotoadv/goods?keyword="+catName;
        //return "https://media.jd.com/index/overview";
        return urlBuffer.toString();
    }

    public static String filterGoodsName(String goodsName){
        return goodsName.replaceAll("<.*?>","");
    }
    public static String filterLong(String str){
        Pattern p = Pattern.compile("-?([1-9]\\d*\\d*)");
        Matcher m = p.matcher(str);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static String filterNumber(String num){
        String rs = num.replace("/[\r\n]/g", "").split("<div>")[0];
        Pattern p = Pattern.compile("-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)");
        Matcher m = p.matcher(rs);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }
 /*
    public static void main(String[] args){
        System.out.println(JdlmengUtil.filterGoodsName("适用 联想2200<span class=H>定</span><span class=H>影</span><span class=H>上辊</span>7205 7250兄弟2140 7340 7450 7030加热辊"));
    }*/
}
