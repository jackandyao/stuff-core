package com.qbao.aisr.stuff.repository.mybatis.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.model.mysql.StuffPayFlow;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;

/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-03-08 16:36:49 +0800 (星期三, 08 三月 2017) $$
 * $$LastChangedRevision: 103 $$
 * $$LastChangedBy: shuaizhihu $$
 */
@Component
public interface StuffPayFlowDao {
    
    @Insert("INSERT INTO stuff_pay_flow (user_id, amt, create_time, pay_status, fail_msg, pay_trade_no,order_id,source,pay_source)"
    		+ "VALUES (#{userId},#{amt},#{createTime},#{payStatus},#{failMsg},#{payTradeNo},#{orderId},#{source},#{paySource})")
    @DataSource("stuffMasterDataSource")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(StuffPayFlow stuffPayFlow);

    @Update("UPDATE stuff_pay_flow SET pay_status = #{payStatus}, fail_msg = #{failMsg} , pay_trade_no = #{payTradeNo} where id = #{id}")
    @DataSource("stuffMasterDataSource")
    public void update(StuffPayFlow stuffPayFlow);

    @Select("select * from stuff_pay_flow where user_id = #{userId} and order_id = #{orderId} and  pay_source = #{paySource} and pay_status=#{payStatus} and source = #{source}")
    @DataSource("stuffMasterDataSource")
    @ResultMap("StuffPayFlowMap")
    public StuffPayFlow find(@Param("userId") Long userId,@Param("orderId") String orderId ,@Param("paySource") int paySource,@Param("payStatus") int payStatus,@Param("source") String source );

}
