package com.qbao.aisr.stuff.repository.mybatis.dao;


import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-06-10 19:21:13 +0800 (Sat, 10 Jun 2017) $$
 * $$LastChangedRevision: 1153 $$
 * $$LastChangedBy: liaijun $$
 */
@Component
public interface StuffPromotionTmpDao {
    @Select("select * from stuff_promotion where id =#{stuffId}" )
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findStuffPromotionByStuffId(long stuffId);

    @Select("select * from stuff_promotion where real_stuff_id =#{realStuffId}" )
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findStuffPromotionByRealStuffId(long realStuffId);

    @Select("select * from stuff_promotion where real_stuff_id =#{realStuffId} and source=#{source} limit 0,1" )
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findStuffPromotionByRealStuffIdAndSource(@Param("realStuffId") long realStuffId,@Param("source")String source);

    @Insert("INSERT INTO stuff_promotion_tmp_${name} (id,real_stuff_id, name,reserve_price,final_price,rebate_id,promotion_rate,promotion_url,url,img_url,cat_id,status,source,shop_id,shop_name,order_num,click_num,create_time,update_time)  VALUES (#{id},#{realStuffId},#{name},#{reservePrice},#{finalPrice},#{rebateId},#{promotionRate},#{promotionUrl},#{url},#{imgUrl},#{catId},#{status},#{source},#{shopId},#{shopName},#{orderNum},#{clickNum},#{createTime},#{updateTime}) ;")
    @DataSource("stuffMasterDataSource")
    public void  insert(@Param("name") String name,@Param("stuffPromotion") StuffPromotion stuffPromotion);


    @DataSource("stuffMasterDataSource")
    public void  insertBatch(@Param("name") String name,@Param("list")List<StuffPromotion> stuffPromotionList);

    @DataSource("stuffMasterDataSource")
    public void  insertJdlmengBatch(List<StuffPromotion> stuffPromotionList);

    @Select("select * from stuff_promotion_tmp_${name} where id=#{id} limit 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion  isExsit(@Param("name") String name,@Param("stuffPromotion")StuffPromotion stuffPromotion);

    @Select("SELECT * FROM stuff_promotion_tmp_${name} WHERE android_promotion_url IS NULL AND source IN(\"taobao\",\"tmall\") LIMIT 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findAndroidNotPromotion(@Param("name") String name);

    @Select("SELECT * FROM stuff_promotion_tmp_${name} WHERE ios_promotion_url IS NULL AND source IN(\"taobao\",\"tmall\") LIMIT 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findIosNotPromotion(@Param("name") String name);

    @Select("SELECT * FROM stuff_promotion_tmp_${name} WHERE pc_promotion_url IS NULL AND source IN(\"taobao\",\"tmall\") LIMIT 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findPCNotPromotion(@Param("name") String name);

    @Select("SELECT * FROM stuff_promotion_tmp_${name} WHERE ios_promotion_url is not null and ios_promotion_url!='#' and android_promotion_url IS NULL AND source IN(\"taobao\",\"tmall\") LIMIT 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findAndroidNotPromotionQiWan(@Param("name") String name);

    @Select("SELECT * FROM stuff_promotion_tmp_${name} WHERE  android_promotion_url is not null and android_promotion_url!='#'  and ios_promotion_url IS NULL AND source IN(\"taobao\",\"tmall\") LIMIT 0,1")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public StuffPromotion findIosNotPromotionQiWan(@Param("name") String name);

    @Update("update stuff_promotion_tmp_${name} set android_promotion_url =#{androidPromotionUrl},operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updatePromotionUrl(@Param("name") String name,@Param("id") long id,@Param("promotionUrl") String promotionUrl);

    @Update("update stuff_promotion_tmp_${name} set android_promotion_url =#{androidPromotionUrl} ,operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateAndroidPromotionUrl(@Param("name") String name,@Param("id") long id,@Param("androidPromotionUrl") String androidPromotionUrl);

    @Update("update stuff_promotion_tmp_${name} set ios_promotion_url =#{iosPromotionUrl},operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateIosPromotionUrl(@Param("name") String name,@Param("id") long id,@Param("iosPromotionUrl") String iosPromotionUrl);



    @Update("update stuff_promotion_tmp_${name} set pc_promotion_url =#{pcPromotionUrl},operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updatePCPromotionUrl(@Param("name") String name,@Param("id") long id,@Param("pcPromotionUrl") String pcPromotionUrl);

    @Update("update stuff_promotion_tmp_${name} set status =#{status} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateStatus(@Param("name") String name,@Param("id") long id,@Param("status") int status);

    @Update("update stuff_promotion set ios_promotion_url =#{iosPromotionUrl},operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateIosPromotionUrlByZs(@Param("name") String name,@Param("id") long id,@Param("iosPromotionUrl") String iosPromotionUrl);

    @Update("update stuff_promotion set ios_promotion_url =#{iosPromotionUrl},operator_source=#{name},status =1 where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateIosPromotionUrlStatusByZs(@Param("name") String name,@Param("id") long id,@Param("iosPromotionUrl") String iosPromotionUrl);



    @Update("update stuff_promotion set android_promotion_url =#{androidPromotionUrl} ,operator_source=#{name},status =1 where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateAndroidPromotionUrlStatusByZs(@Param("name") String name,@Param("id") long id,@Param("androidPromotionUrl") String androidPromotionUrl);

    @Update("update stuff_promotion set android_promotion_url =#{androidPromotionUrl} ,operator_source=#{name} where id=#{id}")
    @ResultMap("StuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public void updateAndroidPromotionUrlByZs(@Param("name") String name,@Param("id") long id,@Param("androidPromotionUrl") String androidPromotionUrl);

    @Select("SELECT id FROM stuff_promotion_tmp_${name}")
    @DataSource("stuffMasterDataSource")
    public List<Long> findAllStuffIds(@Param("name") String name);

    @Select("SELECT id FROM stuff_promotion_jdlmeng_tmp")
    @DataSource("stuffMasterDataSource")
    public List<Long> findJdlmengAllStuffIds();


    @Select("truncate table stuff_promotion_tmp_${name}")
    @DataSource("stuffMasterDataSource")
    public void clear(@Param("name") String name);

   @Update("CREATE TABLE IF NOT EXISTS `stuff_promotion_tmp_${name}` (\n" + "  `id` bigint(20) NOT NULL COMMENT '统一商品id',\n" + "  `real_stuff_id` bigint(20) NOT NULL COMMENT '实际商品id',\n" + "  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',\n" + "  `reserve_price` decimal(10,2) DEFAULT NULL COMMENT '原价',\n" + "  `final_price` decimal(10,2) DEFAULT NULL COMMENT '商品最终价格',\n" + "  `rebate_id` int(20) DEFAULT NULL COMMENT '返利类型 rebate 表 id',\n" + "  `promotion_rate` int(11) DEFAULT NULL COMMENT '推广佣金比 1234即12.34%.34',\n" + "  `android_promotion_url` varchar(512) DEFAULT NULL COMMENT 'android推广链接',\n"+ "  `ios_promotion_url` varchar(512) DEFAULT NULL COMMENT 'ios推广链接',\n"+ "  `url` varchar(512) DEFAULT NULL COMMENT '商品链接',\n" + "  `img_url` varchar(512) DEFAULT NULL COMMENT '商品图片链接',\n" + "  `cat_id` varchar(12) DEFAULT NULL COMMENT 'stuff_category 表 的 商品类目cat_id',\n" + "  `status` int(11) DEFAULT NULL COMMENT '商品状态 0:审核,1:上架,2下架',\n" + "  `source` varchar(255) DEFAULT NULL COMMENT '商品来源:taobao,tmall,jd',\n" + "  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',\n" + "  `shop_name` varchar(255) DEFAULT NULL COMMENT '商家名称',\n" + "  `order_num` int(11) DEFAULT NULL COMMENT '推广销量',\n" + "  `click_num` int(11) DEFAULT NULL COMMENT '点击量',\n" + "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  PRIMARY KEY (`id`),`operator_source` varchar(255) DEFAULT '' COMMENT '操作人',\n" + "  UNIQUE KEY `real_stuff_id_source` (`real_stuff_id`,`source`),\n" + "  KEY `shop_id` (`shop_id`)\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8")
   @DataSource("stuffMasterDataSource")
    public void createTmp(@Param("name") String name);
    @Update("CREATE TABLE `stuff_promotion` (\n" + "  `id` bigint(20) NOT NULL COMMENT '统一商品id',\n" + "  `real_stuff_id` bigint(20) NOT NULL COMMENT '实际商品id',\n" + "  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',\n" + "  `reserve_price` decimal(10,2) DEFAULT NULL COMMENT '原价',\n" + "  `final_price` decimal(10,2) DEFAULT NULL COMMENT '商品最终价格',\n" + "  `rebate_id` int(20) DEFAULT NULL COMMENT '返利类型 rebate 表 id',\n" + "  `promotion_rate` int(11) DEFAULT NULL COMMENT '推广佣金比 1234即12.34%.34',\n" + "  `android_promotion_url` varchar(512) DEFAULT NULL COMMENT 'android推广链接',\n"+ "  `ios_promotion_url` varchar(512) DEFAULT NULL COMMENT 'ios推广链接',\n"+ "  `url` varchar(512) DEFAULT NULL COMMENT '商品链接',\n" + "  `img_url` varchar(512) DEFAULT NULL COMMENT '商品图片链接',\n" + "  `cat_id` varchar(12) DEFAULT NULL COMMENT 'stuff_category 表 的 商品类目cat_id',\n" + "  `status` int(11) DEFAULT NULL COMMENT '商品状态 0:审核,1:上架,2下架',\n" + "  `source` varchar(255) DEFAULT NULL COMMENT '商品来源:taobao,tmall,jd',\n" + "  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',\n" + "  `shop_name` varchar(255) DEFAULT NULL COMMENT '商家名称',\n" + "  `order_num` int(11) DEFAULT NULL COMMENT '推广销量',\n" + "  `click_num` int(11) DEFAULT NULL COMMENT '点击量',\n" + "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  PRIMARY KEY (`id`),\n" + "  UNIQUE KEY `real_stuff_id_source` (`real_stuff_id`,`source`),\n" + "  KEY `shop_id` (`shop_id`)\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8")
    @DataSource("stuffMasterDataSource")
    public void createProd();

    @Update("CREATE TABLE `stuff_promotion_jdlmeng_tmp` (\n" + "  `id` bigint(20) NOT NULL COMMENT '统一商品id',\n" + "  `real_stuff_id` bigint(20) NOT NULL COMMENT '实际商品id',\n" + "  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',\n" + "  `reserve_price` decimal(10,2) DEFAULT NULL COMMENT '原价',\n" + "  `final_price` decimal(10,2) DEFAULT NULL COMMENT '商品最终价格',\n" + "  `rebate_id` int(20) DEFAULT NULL COMMENT '返利类型 rebate 表 id',\n" + "  `promotion_rate` int(11) DEFAULT NULL COMMENT '推广佣金比 1234即12.34%.34',\n" + "  `promotion_url` varchar(255) DEFAULT NULL COMMENT '推广链接',\n" + "  `url` varchar(255) DEFAULT NULL COMMENT '商品链接',\n" + "  `img_url` varchar(512) DEFAULT NULL COMMENT '商品图片链接',\n" + "  `cat_id` varchar(12) DEFAULT NULL COMMENT 'stuff_category 表 的 商品类目cat_id',\n" + "  `status` int(11) DEFAULT NULL COMMENT '商品状态 0:审核,1:上架,2下架',\n" + "  `source` varchar(255) DEFAULT NULL COMMENT '商品来源:taobao,tmall,jd',\n" + "  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',\n" + "  `shop_name` varchar(255) DEFAULT NULL COMMENT '商家名称',\n" + "  `order_num` int(11) DEFAULT NULL COMMENT '推广销量',\n" + "  `click_num` int(11) DEFAULT NULL COMMENT '点击量',\n" + "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,\n" + "  PRIMARY KEY (`id`),\n" + "  UNIQUE KEY `real_stuff_id_source` (`real_stuff_id`,`source`),\n" + "  KEY `shop_id` (`shop_id`)\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8")
    @DataSource("stuffMasterDataSource")
    public void createJdlmengTmp();

    @Update("RENAME TABLE stuff_promotion TO stuff_promotion_${date}, stuff_promotion_tmp_${name} TO stuff_promotion")
    @DataSource("stuffMasterDataSource")
    public void rename(@Param("name") String name,@Param("date") String date);

    @Update("RENAME TABLE stuff_promotion TO stuff_promotion_tmp_${name}")
    @DataSource("stuffMasterDataSource")
    public void renameProdToTmp(@Param("name") String name);

    @Update("insert into stuff_promotion_tmp_${name}  select * from stuff_promotion  where id not in(select id from stuff_promotion_tmp_${name}) ")
    @DataSource("stuffMasterDataSource")
    public void refrash(@Param("name") String name);

    @Update("update stuff_promotion_tmp_${name} ,stuff_promotion set stuff_promotion_tmp_${name}.rebate_id = stuff_promotion.rebate_id  where stuff_promotion.id = stuff_promotion_tmp_${name}.id")
    @DataSource("stuffMasterDataSource")
    public void updateRebateId(@Param("name") String name);

    @Update("update  stuff_promotion_tmp_${name} set ios_promotion_url = null where ios_promotion_url='#'")
    @DataSource("stuffMasterDataSource")
    public void updateIOSUrlSetNull(@Param("name") String name);
    @Update("update  stuff_promotion_tmp_${name} set android_promotion_url = null where android_promotion_url='#'")
    @DataSource("stuffMasterDataSource")
    public void updateAndroidUrlSetNull(@Param("name") String name);
}
