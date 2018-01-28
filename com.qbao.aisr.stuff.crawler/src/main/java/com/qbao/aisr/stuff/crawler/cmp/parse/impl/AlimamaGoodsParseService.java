package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.common.stuffenum.GoodsSource;
import com.qbao.aisr.stuff.crawler.cmp.http.common.util.StringUtil;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:41
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="alimamaGoodsParseService")
public class AlimamaGoodsParseService implements IParseService{

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsParseService.class);

    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;

    @Override
    public boolean parse(ParseContext context) {
        String jsonString="";
        try {
            jsonString = context.getParseTask().getContent();
            if(StringUtils.isBlank(jsonString)){
                logger.info("获取数据为空>>>>>>jsonString="+jsonString);
                return false;
            }
            JSONObject jsonObject = JSON.parseObject(jsonString);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = dataObject.getJSONArray("pageList");
            List<StuffPromotion> list = new ArrayList<StuffPromotion>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject goodsObject = jsonArray.getJSONObject(i);
                StuffPromotion stuffPromotion = new StuffPromotion();
                StuffCategory category = (StuffCategory) context.getParseTask().getData();
                if(category!=null) {
                    stuffPromotion.setCatId(category.getCatId());
                }
                stuffPromotion.setCreateTime(new Date());
                String imgUrl=goodsObject.getString("pictUrl");
                if(StringUtils.isNotBlank(imgUrl)){
                    if(imgUrl.indexOf("http")!=0){
                        imgUrl="http:"+imgUrl;
                    }
                }
                stuffPromotion.setImgUrl(imgUrl);
                stuffPromotion.setName(AlimamaUtil.filterGoodsName(goodsObject.getString("title")));
                stuffPromotion.setReservePrice(goodsObject.getBigDecimal("reservePrice"));
                stuffPromotion.setFinalPrice(goodsObject.getBigDecimal("zkPrice"));
                stuffPromotion.setPromotionRate((long) (goodsObject.getFloat("tkRate") * 100));
                stuffPromotion.setRealStuffId(goodsObject.getLong("auctionId"));
                stuffPromotion.setShopId(goodsObject.getLong("sellerId"));
                stuffPromotion.setOrderNum(goodsObject.getInteger("biz30day"));
                int userType = goodsObject.getInteger("userType");
                if (userType == 0) {
                    stuffPromotion.setSource(GoodsSource.TAOBAO.getName());
                } else {
                    stuffPromotion.setSource(GoodsSource.TMALL.getName());
                }
                stuffPromotion.setStatus(0);
                stuffPromotion.setUpdateTime(new Date());
                stuffPromotion.setUrl(goodsObject.getString("auctionUrl"));
                stuffPromotion.setShopName(goodsObject.getString("shopTitle"));
                stuffPromotion.setId(this.createStuffId(stuffPromotion.getRealStuffId(),stuffPromotion.getSource()));
                list.add(stuffPromotion);
            }
            context.setParseResult(list);
            return true;
        }catch (Exception e){
            logger.error("返回数据>>>>json="+jsonString+">>>>>>>>>>>>"+ExceptionUtils.getFullStackTrace(e));
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
}
