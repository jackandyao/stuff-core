package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Service(value="alimamaGoodsPromotionUrlParseService")
public class AlimamaGoodsPromotionUrlParseService implements IParseService {
    @Override
    public boolean parse(ParseContext context) {
        String jsonString = context.getParseTask().getContent();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        if(dataObject!=null&&dataObject.containsKey("clickUrl")) {
            String clickUrl = dataObject.getString("clickUrl");
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            StuffPromotion stuffPromotion = new StuffPromotion();
            StuffPromotion old = (StuffPromotion) context.getParseTask().getData();
            stuffPromotion.setId(old.getId());
            stuffPromotion.setAndroidPromotionUrl(clickUrl);
            if(old!=null&&old.getIosPromotionUrl()!=null&&old.getIosPromotionUrl().startsWith("https://s.click.taobao.com")){
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
