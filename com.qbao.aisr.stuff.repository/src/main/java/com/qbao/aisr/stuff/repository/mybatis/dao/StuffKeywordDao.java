package com.qbao.aisr.stuff.repository.mybatis.dao;

import java.util.Date;
import java.util.List;

import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffKeyword;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-06-07 10:19:04 +0800 (Wed, 07 Jun 2017) $$
 * $$LastChangedRevision: 1107 $$
 * $$LastChangedBy: liaijun $$
 */
@Component
public interface StuffKeywordDao {

    @DataSource("stuffMasterDataSource")
    @Insert(" insert stuff_keyword ( user_id,keyword, source, create_time,update_time,total) values ( #{userId} , #{keyword},#{source},#{creatTime},#{updateTime},#{total})")
    public void saveStuffKeyword(@Param("userId") long userId, @Param("keyword") String keyword, @Param("source") String source, @Param("creatTime") Date creatTime, @Param("updateTime") Date updateTime, @Param("total") int total);


    @Select("select * from stuff_keyword where status='0")
    @ResultMap("keywordMap")
    @DataSource("stuffSlaveDataSource")
    public List<StuffKeyword> findStuffKeyword();

    @Update("update stuff_keyword set status =#{status} where id=#{id}")
    @ResultMap("keywordMap")
    @DataSource("stuffMasterDataSource")
    public void updateStatus(@Param("id") long id,@Param("status") String status);

    @Update("update stuff_keyword set status ='0' where status='2'")
    @ResultMap("keywordMap")
    @DataSource("stuffMasterDataSource")
    public void updateKeywordStatus();
}
