package com.qbao.aisr.stuff.repository.mybatis.dao;

import com.qbao.aisr.stuff.model.mysql.UserStuffPromotionAppeal;
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
public interface UserStuffPromotionAppealDao {



    @DataSource("stuffSlaveDataSource")
    @ResultMap("UserStuffPromotionAppealMap")
    @Select(" select * FROM  user_stuff_promotion_appeal where  status = #{status} and is_pay= #{isPay} and rebate_value != 0 limit #{offset}, #{limit}")
    public List<UserStuffPromotionAppeal> queryByIsPayAndStatus(@Param("isPay") int isPay,@Param("status") int status, @Param("offset") long offset, @Param("limit") long limit);


    @DataSource("stuffSlaveDataSource")
    @Select("select count(*) from user_stuff_promotion_appeal where status = #{status} and rebate_value != 0 and is_pay= #{isPay}")
    public long queryTotalIsPayAndStatus(@Param("isPay") int isPay,@Param("status") int status);


    @DataSource("stuffMasterDataSource")
    public void batchUpdateIsPayStatus(@Param("ids") List<Long> ids, @Param("status") int status);


}
