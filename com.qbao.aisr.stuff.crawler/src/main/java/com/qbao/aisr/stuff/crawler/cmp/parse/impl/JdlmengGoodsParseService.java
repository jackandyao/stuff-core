package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import com.qbao.aisr.stuff.common.stuffenum.GoodsSource;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.util.jdlmeng.JdlmengUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:41
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="jdlmengGoodsParseService")
public class JdlmengGoodsParseService implements IParseService{

    Logger logger = LoggerFactory.getLogger(JdlmengGoodsParseService.class);

    @Override
    public boolean parse(ParseContext context) {
        try {
            String contentString = context.getParseTask().getContent();
            if(contentString.contains("没有查询到符合条件的数据")){
                return false;
            }
            Document htmlDocument = Jsoup.parse(contentString);
            Element tagElement = htmlDocument.getElementsByTag("tbody").get(0);
            Elements rowElement = tagElement.getElementsByTag("tr");
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            for(Element row:rowElement){
                StuffPromotion stuffPromotion = new StuffPromotion();
                StuffCategory category = (StuffCategory) context.getParseTask().getData();
                stuffPromotion.setCatId(category.getCatId());
                stuffPromotion.setCreateTime(new Date());
                stuffPromotion.setImgUrl(row.getElementsByTag("img").get(0).attr("src"));
                stuffPromotion.setName(row.getElementsByClass("min_wid_145_k").text());
                stuffPromotion.setReservePrice(new BigDecimal(JdlmengUtil.filterNumber(row.getElementsByTag("td").get(1).text())));
                stuffPromotion.setFinalPrice(new BigDecimal(JdlmengUtil.filterNumber(row.getElementsByTag("td").get(1).text())).subtract(new BigDecimal(JdlmengUtil.filterNumber(row.getElementsByTag("td").get(3).text()))));
                //System.out.printf("======"+row.getElementsByTag("td").get(3).text().split("<div>")[0]);
                //stuffPromotion.setPromotionRate((long) (goodsObject.getFloat("tkRate") * 100));
                //stuffPromotion.setPromotionRate(new Long(row.getElementsByTag("td").get(2).text().split("div")[0])*100);

                stuffPromotion.setRealStuffId(Long.parseLong(JdlmengUtil.filterLong(row.getElementsByClass("min_wid_145_k").attr("href"))));
                //stuffPromotion.setShopId(goodsObject.getLong("sellerId"));
                stuffPromotion.setOrderNum(Integer.parseInt(row.getElementsByTag("td").get(4).text().trim()));

                stuffPromotion.setSource(GoodsSource.JD.getName());
                stuffPromotion.setStatus(0);
                stuffPromotion.setUpdateTime(new Date());
                //stuffPromotion.setUrl(goodsObject.getString("auctionUrl"));
                stuffPromotion.setShopName(JdlmengUtil.filterGoodsName(row.getElementsByTag("a").get(1).text()));
                stuffPromotion.setId(this.createStuffId(stuffPromotion.getRealStuffId(),stuffPromotion.getSource()));
                list.add(stuffPromotion);
            }

         /*   JSONObject jsonObject = JSON.parseObject(jsonString);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = dataObject.getJSONArray("pageList");
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject goodsObject = jsonArray.getJSONObject(i);
                StuffPromotion stuffPromotion = new StuffPromotion();
                StuffCategory category = (StuffCategory) context.getParseTask().getData();
                stuffPromotion.setCatId(category.getCatId());
                stuffPromotion.setCreateTime(new Date());
                stuffPromotion.setImgUrl(goodsObject.getString("pictUrl"));
                stuffPromotion.setName(AlimamaUtil.filterGoodsName(goodsObject.getString("title")));
                stuffPromotion.setReservePrice(goodsObject.getBigDecimal("reservePrice"));
                stuffPromotion.setFinalPrice(goodsObject.getBigDecimal("zkPrice"));
                stuffPromotion.setPromotionRate((long) (goodsObject.getFloat("tkRate") * 100));
                stuffPromotion.setRealStuffId(goodsObject.getLong("auctionId"));
                stuffPromotion.setShopId(goodsObject.getLong("sellerId"));
                stuffPromotion.setOrderNum(goodsObject.getInteger("biz30day"));
                int userType = goodsObject.getInteger("userType");
                if (userType == 0) {
                    stuffPromotion.setSource("taobao");
                } else {
                    stuffPromotion.setSource("tmall");
                }
                stuffPromotion.setStatus(0);
                stuffPromotion.setUpdateTime(new Date());
                stuffPromotion.setUrl(goodsObject.getString("auctionUrl"));
                stuffPromotion.setShopName(goodsObject.getString("shopTitle"));
                list.add(stuffPromotion);
            }*/
            context.setParseResult(list);
            return true;
        }catch (Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }

    }
    private long createStuffId(Long realStuffId,String source){
        if(StringUtils.isEmpty(source)){
            return realStuffId;
        }
        GoodsSource[] goodsSources =  GoodsSource.values();
        for(GoodsSource goodsSource:goodsSources){
            if(goodsSource.getName().equals(source)) {
                Integer code = goodsSource.getCode();
                String stuffIdStr = realStuffId + "" + code;
                return Long.parseLong(stuffIdStr);
            }
        }
        return  realStuffId;
    }
    public static void main(String[] args) {
        /*StringBuffer sb = new StringBuffer();
        sb.append("");

        Document htmlDocument = Jsoup.parse(sb.toString());
        Elements element = htmlDocument.getElementsByClass("background_fff_k");
        System.out.printf("");*/
        /*String rs = " PC： ￥46.00 \n" +
                "   <div></div> 无线： ￥46.00 ".replace("/[\r\n]/g", "").split("<div>")[0];*/
        //rs = rs.replaceAll("-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)","");
        //System.out.printf("====="+rs);

        Long xx = Long.parseLong(JdlmengUtil.filterLong("http://item.jd.com/10433863147.html"));
        System.out.printf("========"+xx);
    }


}
