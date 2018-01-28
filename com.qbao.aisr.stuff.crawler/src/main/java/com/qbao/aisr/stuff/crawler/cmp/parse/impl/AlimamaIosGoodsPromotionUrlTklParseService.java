package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 19:34
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="alimamaIosGoodsPromotionUrlTklParseService")
public class AlimamaIosGoodsPromotionUrlTklParseService implements IParseService {
    Logger logger = LoggerFactory.getLogger(AlimamaIosGoodsPromotionUrlTklParseService.class);
    @Override
    public boolean parse(ParseContext context) {
        String jsonString = context.getParseTask().getContent();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        if(dataObject!=null&&dataObject.containsKey("taoToken")) {
            String taoToken = dataObject.getString("taoToken");
            logger.info("parse repsonse content:taokouling is -> "+taoToken);
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            StuffPromotion stuffPromotion = new StuffPromotion();
            StuffPromotion old = (StuffPromotion) context.getParseTask().getData();
            stuffPromotion.setId(old.getId());
            stuffPromotion.setIosPromotionUrl(taoToken);
            if(old!=null&&old.getAndroidPromotionUrl()!=null&&old.getAndroidPromotionUrl().startsWith("￥")){
                logger.info("current parse is ios ,update status into shangjia,androidPromotionUrl is ->"+ old.getAndroidPromotionUrl());
                stuffPromotion.setStatus(1);
            }else{
                stuffPromotion.setStatus(0);
            }

            list.add(stuffPromotion);
            context.setParseResult(list);
            return true;
        }else{
            return false;
        }
    }
}
