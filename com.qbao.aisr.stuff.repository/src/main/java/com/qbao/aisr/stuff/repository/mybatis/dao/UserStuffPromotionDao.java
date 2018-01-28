package com.qbao.aisr.stuff.repository.mybatis.dao;

import com.qbao.aisr.stuff.model.mysql.UserStuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/16 15:13
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Repository
public interface UserStuffPromotionDao {

    @DataSource("stuffMasterDataSource")
    public void update(UserStuffPromotion userStuffPromotion);

    @Select("select * from  user_stuff_promotion where order_id = #{orderId} and status != 0 and  return_coupon_status !=1 ")
    @ResultMap("UserStuffPromotionMap")
    @DataSource("stuffMasterDataSource")
    public UserStuffPromotion findByOrderId(long orderId);


    @DataSource("stuffSlaveDataSource")
    @ResultMap("UserStuffPromotionMap")
    @Select(" select * FROM  user_stuff_promotion where  return_coupon_status = #{status} and rebate_value != 0 and rebate_type =#{rebateType}  limit #{offset}, #{limit}")
    public List<UserStuffPromotion> queryByReturnCouponStatus(@Param("status") int status, @Param("rebateType") int rebateType,@Param("offset") long offset, @Param("limit")long limit);


    @DataSource("stuffSlaveDataSource")
    @Select("select count(*) from user_stuff_promotion where return_coupon_status = #{status} and rebate_value != 0 and rebate_type =#{rebateType} ")
    public long queryTotalByReturnCouponStatus(@Param("status") int status,@Param("rebateType") int rebateType);


    @DataSource("stuffMasterDataSource")
    public void batchUpdateReturnCouponStatus(@Param("ids") List<Long> ids,@Param("status")int status);


}
