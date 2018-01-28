package com.qbao.aisr.stuff.crawler.cmp.parse.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.common.stuffenum.GoodsSource;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:41
 * $$LastChangedDate: 2017-06-15 10:40:19 +0800 (Thu, 15 Jun 2017) $$
 * $$LastChangedRevision: 1220 $$
 * $$LastChangedBy: liaijun $$
 */
@Service(value="alimamaGoodsCouponParseService")
public class AlimamaGoodsCouponParseService implements IParseService {

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsCouponParseService.class);
   

    @Override
    public boolean parse(ParseContext context) {
        try {
            String jsonString = context.getParseTask().getContent();
            JSONObject jsonObject = JSON.parseObject(jsonString);
            Integer retrunCode = jsonObject.getInteger("er_code");

            if(10000==retrunCode) {
                JSONObject dataObject = jsonObject.getJSONObject("data");
                JSONArray jsonArray = dataObject.getJSONArray("list");
                List<StuffCoupon> list = new ArrayList<StuffCoupon>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject couponObject = jsonArray.getJSONObject(i);
                    StuffCoupon stuffCoupon = (StuffCoupon) context.getParseTask().getData();
//                    StuffCoupon stuffCoupon = new StuffCoupon();
//                    Long realStuffId = couponObject.getLong("goods_id");
//                    String isTmall = couponObject.getString("is_tmall");
//                    String source = "";
//                    if ("1".equals(isTmall)) {
//                        source = "tmall";
//                    } else {
//                        source = "taobao";
//                    }
//                    Long stuffId = createStuffId(realStuffId, source);
//                    stuffCoupon.setStuffId(stuffId);
//                    stuffCoupon.setRealStuffId(realStuffId);
//                    //stuffCoupon.setLink(); 券链接要爬取
//                    BigDecimal value=couponObject.getBigDecimal("coupon_price");
//                    stuffCoupon.setValue(value);
                    String introduce=couponObject.getString("goods_introduce");
                    stuffCoupon.setIntroduce(introduce);
                    String coupon_type=couponObject.getString("coupon_type");
                    String type="";
                    if("1".equals(coupon_type)){
                        type="单品券";
                    }else{
                        type="店铺券";
                    }
                    stuffCoupon.setType(type);
                    Integer coupon_limit=couponObject.getInteger("coupon_limit");
                    Integer totalCount=0;
                    Integer coupon_number=couponObject.getInteger("coupon_number");
                    Integer coupon_over=couponObject.getInteger("coupon_over");
                    if(-1==coupon_limit){
                        totalCount=-1;
                    }else{
                        totalCount=coupon_number+coupon_over;
                    }
                    stuffCoupon.setTotalCount(totalCount);
//                    stuffCoupon.setSource(source);
                    
                    
//                    Date startTime=couponObject.getDate("coupon_start_time");
//                    Date endTime=couponObject.getDate("coupon_end_time");
//                    stuffCoupon.setStartTime(startTime);
//                    stuffCoupon.setEndTime(endTime);

                    list.add(stuffCoupon);
                }
                context.setParseResult(list);
                return true;
            }

            return true;
        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }

    }

   


}
