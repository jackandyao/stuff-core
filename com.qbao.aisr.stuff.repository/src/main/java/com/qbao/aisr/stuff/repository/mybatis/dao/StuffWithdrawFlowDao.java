package com.qbao.aisr.stuff.repository.mybatis.dao;

import com.qbao.aisr.stuff.model.mysql.StuffWithdrawFlow;
import com.qbao.aisr.stuff.repository.mybatis.annotation.DataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author wangping
 * @createTime 2017/6/21 下午5:42
 * $$LastChangedDate: 2017-06-21 18:48:31 +0800 (Wed, 21 Jun 2017) $$
 * $$LastChangedRevision: 1308 $$
 * $$LastChangedBy: wangping $$
 */
@Component
public interface StuffWithdrawFlowDao {
    @Insert("INSERT INTO stuff_withdraw_flow (user_id, amt, create_time, pay_status, fail_msg, pay_trade_no,order_id,source,pay_source)"
            + "VALUES (#{userId},#{amt},#{createTime},#{payStatus},#{failMsg},#{payTradeNo},#{orderId},#{source},#{paySource})")
    @DataSource("stuffMasterDataSource")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(StuffWithdrawFlow stuffWithdrawFlow);

    @Update("UPDATE stuff_withdraw_flow SET pay_status = #{payStatus}, fail_msg = #{failMsg} , pay_trade_no = #{payTradeNo} where id = #{id}")
    @DataSource("stuffMasterDataSource")
    public void update(StuffWithdrawFlow stuffWithdrawFlow);

    @Select("select * from stuff_withdraw_flow where user_id = #{userId} and order_id = #{orderId} and  pay_source = #{paySource} and pay_status=#{payStatus} and source = #{source}")
    @DataSource("stuffMasterDataSource")
    @ResultMap("StuffWithdrawFlowMap")
    public StuffWithdrawFlow find(@Param("userId") Long userId,@Param("orderId") String orderId ,@Param("paySource") int paySource,@Param("payStatus") int payStatus,@Param("source") String source );

}
