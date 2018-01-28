package com.qbao.aisr.stuff.repository.mybatis.dao;


import com.qbao.aisr.stuff.model.mysql.StuffRebate;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author wangping
 * @createTime 17/3/7 上午9:32
 * $$LastChangedDate: 2017-03-17 15:16:39 +0800 (Fri, 17 Mar 2017) $$
 * $$LastChangedRevision: 262 $$
 * $$LastChangedBy: shuaizhihu $$
 */
@Component
public interface StuffRebateDao {

    @DataSource("stuffMasterDataSource")
    @Select("select * from stuff_rebate where id = #{id} ")
    @ResultMap("StuffRebateMap")
    public StuffRebate findStuffReBate(@Param("id") int id);

    @DataSource("stuffMasterDataSource")
    @Select("select distinct sr.*\n" +
            "from stuff_promotion sp inner join stuff_rebate sr\n" +
            "on sp.rebate_id = sr.id\n" +
            "where sr.id =  #{stuffId}")
    @ResultMap("StuffRebateMap")
    public StuffRebate findStuffReBateByStuffId(@Param("stuffId") Long stuffId);

}
