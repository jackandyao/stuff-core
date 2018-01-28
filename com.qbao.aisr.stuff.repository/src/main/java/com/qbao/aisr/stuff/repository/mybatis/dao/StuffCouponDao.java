package com.qbao.aisr.stuff.repository.mybatis.dao;

import java.util.Date;
import java.util.List;

import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.model.mysql.UserStuffPromotionAppeal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;

/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-06-15 10:40:19 +0800 (Thu, 15 Jun 2017) $$
 * $$LastChangedRevision: 1220 $$
 * $$LastChangedBy: liaijun $$
 */
@Component
public interface StuffCouponDao {

    @DataSource("stuffMasterDataSource")
    @Insert("insert stuff_coupon ( stuff_id,real_stuff_id, link,value,introduce,type,total_count, source,start_time,end_time) values ( #{stuffId} , #{realStuffId},#{link},#{value},#{introduce},#{type},#{totalCount},#{source},#{startTime},#{endTime})")
    public void saveStuffCoupon(@Param("stuffId") long stuffId, @Param("realStuffId") Long realStuffId, @Param("link") String link, @Param("value") String value, @Param("introduce") String introduce,@Param("type") String type,@Param("totalCount") Integer totalCount,@Param("source") String source, @Param("startTime") Date startTime, @Param("endTime") Date endTime);



    @DataSource("stuffMasterDataSource")
    @Insert("insert stuff_coupon ( stuff_id,real_stuff_id, link,value,introduce,type,total_count, source,start_time,end_time) values ( #{stuffId} , #{realStuffId},#{link},#{value},#{introduce},#{type},#{totalCount},#{source},#{startTime},#{endTime})")
    public void insertStuffCoupon(StuffCoupon stuffCoupon);
    
    
    @DataSource("stuffSlaveDataSource")
    @ResultMap("StuffCouponMap")
    @Select(" select * FROM  stuff_coupon_${name} where start_time is null or end_time is null order by id asc  limit #{offset}, #{limit}")
    public List<StuffCoupon> queryStuffCoupon(@Param("name") String name, @Param("offset") long offset, @Param("limit") long limit);
    
    @DataSource("stuffSlaveDataSource")
    @ResultMap("StuffCouponMap")
    @Select(" select * FROM  stuff_coupon_${name} where introduce is null or type is null order by id asc  limit #{offset}, #{limit}")
    public List<StuffCoupon> queryStuffCouponByIntroduce(@Param("name") String name, @Param("offset") long offset, @Param("limit") long limit);
    
    @Update("update stuff_coupon_${name} set introduce =#{introduce},type=#{type} where stuff_id=#{stuffId}")
    @ResultMap("StuffCouponMap")
    @DataSource("stuffMasterDataSource")
    public void updateStuffCouponByIntroduce(@Param("name") String name,@Param("introduce") String introduce,@Param("type") String type,@Param("stuffId") Long stuffId);

    
    @Update("update stuff_coupon_${name} set introduce =null,type=null where introduce='#' or type='#'")
    @ResultMap("StuffCouponMap")
    @DataSource("stuffMasterDataSource")
    public void updateStuffCouponByBefore(@Param("name") String name);

    @DataSource("stuffSlaveDataSource")
    @ResultMap("StuffCouponMap")
    @Select(" select id FROM  stuff_coupon_${name} ")
    public List<Long> findAllStuffIds(@Param("name") String name);
   
    
    @Update("update stuff_coupon_${name} set start_time =#{stuffCoupon.startTime},end_time=#{stuffCoupon.endTime} where stuff_id=#{stuffCoupon.stuffId}")
    @ResultMap("StuffCouponMap")
    @DataSource("stuffMasterDataSource")
    public void updateStuffCoupon(@Param("name") String name,@Param("stuffCoupon")StuffCoupon stuffCoupon);
    
    @Update("update stuff_coupon_${name} set introduce =#{stuffCoupon.introduce},type=#{stuffCoupon.type},total_count=#{stuffCoupon.totalCount} where stuff_id=#{stuffCoupon.stuffId}")
    @ResultMap("StuffCouponMap")
    @DataSource("stuffMasterDataSource")
    public void updateStuffCouponByType(@Param("name") String name,@Param("stuffCoupon")StuffCoupon stuffCoupon);
    
    @Update("update stuff_coupon_${name} set start_time =null,end_time=null where end_time<#{time}")
    @ResultMap("StuffCouponMap")
    @DataSource("stuffMasterDataSource")
    public void updateStuffCouponByTime(@Param("name") String name,@Param("time") Date time);
    
    
    @DataSource("stuffMasterDataSource")
    public void  insertBatch(@Param("name") String name,@Param("list")List<StuffCoupon> stuffCouponList);

}
