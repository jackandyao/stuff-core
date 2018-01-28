package com.qbao.aisr.stuff.business.alimama.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.business.alimama.service.IParseService;
import com.qbao.aisr.stuff.business.alimama.util.ParseContext;
import com.qbao.aisr.stuff.common.stuffenum.GoodsSource;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 13:41
 * $$LastChangedDate: 2017-06-06 16:53:21 +0800 (Tue, 06 Jun 2017) $$
 * $$LastChangedRevision: 1098 $$
 * $$LastChangedBy: liaijun $$
 */
@Service
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
                List<StuffPromotion> list = new ArrayList<StuffPromotion>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject couponObject = jsonArray.getJSONObject(i);
                    StuffCoupon stuffCoupon = new StuffCoupon();
                    Long realStuffId = couponObject.getLong("goods_id");
                    String isTmall = couponObject.getString("is_tmall");
                    String source = "";
                    if ("1".equals(isTmall)) {
                        source = "tmall";
                    } else {
                        source = "taobao";
                    }
                    Long stuffId = createStuffId(realStuffId, source);
                    stuffCoupon.setStuffId(stuffId);
                    stuffCoupon.setRealStuffId(realStuffId);
                    //stuffCoupon.setLink(); 券链接要爬取
                    BigDecimal value=couponObject.getBigDecimal("coupon_price");
                    stuffCoupon.setValue(value);
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
                    stuffCoupon.setSource(source);
                    Date startTime=couponObject.getDate("coupon_start_time");
                    Date endTime=couponObject.getDate("coupon_end_time");
                    stuffCoupon.setStartTime(startTime);
                    stuffCoupon.setEndTime(endTime);
//                    String paths[] = new String[]{"spring_repository.xml"};
//                    ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
//                    StuffCouponDao stuffCouponDao =  ctx.getBean(StuffCouponDao.class);
//                    stuffCouponDao.insertStuffCoupon(stuffCoupon);
                }
            }

            return true;
        }catch(Exception e){
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


}
