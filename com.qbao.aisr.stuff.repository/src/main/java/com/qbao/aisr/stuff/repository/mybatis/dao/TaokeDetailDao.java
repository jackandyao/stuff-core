package com.qbao.aisr.stuff.repository.mybatis.dao;

import com.qbao.aisr.stuff.model.mysql.TaokeDetail;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/16 9:53
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Repository
public interface TaokeDetailDao {

    @Insert("INSERT INTO taoke_detail (id,order_time,click_time,real_stuff_id,stuff_id,wangwang,shop_name,stuff_num,price,status,order_status,order_type,income_rate,sharing_rate,commission_rate,subsidy_rate,subsidy_type,pay_value,commission_value,estimate_value,settlement_value,platform,third_party_serivce,order_id,category_name,source_id,media_name,source_name,adv_id,adv_name,create_time,update_time,img_url,android_click_url,ios_click_url,rebate_value,name,rebate_type) \n" + "VALUES\n" + "  (#{id},#{orderTime},#{clickTime},#{realStuffId},#{stuffId},#{wangwang},#{shopName},#{stuffNum},#{price},#{status},#{orderStatus},#{orderType},#{incomeRate},#{sharingRate},#{commissionRate},#{subsidyRate},#{subsidyType},#{payValue},#{commissionValue},#{estimateValue},#{settlementValue},#{platform},#{thirdPartySerivce},#{orderId},#{categoryName},#{sourceId},#{mediaName},#{sourceName},#{advId},#{advName},#{createTime},#{updateTime},#{imgUrl},#{androidClickUrl},#{iosClickUrl},#{rebateValue},#{name} ,#{rebateType}  )" )
    @DataSource("stuffMasterDataSource")
    public void insert(TaokeDetail detail);

    @Select("select * from  taoke_detail where order_id=#{orderId} and real_stuff_id=#{realStuffId}")
    @ResultMap("TaokeDetailMap")
    @DataSource("stuffMasterDataSource")
    public TaokeDetail isExsit(TaokeDetail detail);

    @DataSource("stuffMasterDataSource")
    public void update(TaokeDetail detail);


    @Select("select * from taoke_detail where order_id=#{orderId}")
    @ResultMap("TaokeDetailMap")
    @DataSource("stuffMasterDataSource")
    public List<TaokeDetail> findByOrderId(long orderId);
}
