package com.qbao.aisr.stuff.repository.mybatis.dao;


import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-03-03 17:59:07 +0800 (周五, 03 三月 2017) $$
 * $$LastChangedRevision: 53 $$
 * $$LastChangedBy: wangping $$
 */
@Component
public interface StuffCategoryDao {
    @Select("select * from stuff_category where valid_flag=1 and lev=#{lev}")
    @ResultMap("StuffCategoryMap")
    @DataSource("stuffSlaveDataSource")
    public List<StuffCategory> findStuffCategoryByLev(@Param("lev") int lev);

    @Select("select * from stuff_category where valid_flag=1 and pid=#{pid}")
    @ResultMap("StuffCategoryMap")
    @DataSource("stuffSlaveDataSource")
    public List<StuffCategory> findStuffCategoryByPid(@Param("pid") String pid);


    @Select("select * from stuff_category where valid_flag=1 and cat_id=#{catId}")
    @ResultMap("StuffCategoryMap")
    @DataSource("stuffSlaveDataSource")
    public StuffCategory findStuffCategoryById(@Param("catId") String catId);
    
    
    @Select("select * from stuff_category where valid_flag=1 and cat_name like #{catName} limit 1")
    @ResultMap("StuffCategoryMap")
    @DataSource("stuffSlaveDataSource")
    public StuffCategory findStuffCategoryByName(@Param("catName") String catName);


}
