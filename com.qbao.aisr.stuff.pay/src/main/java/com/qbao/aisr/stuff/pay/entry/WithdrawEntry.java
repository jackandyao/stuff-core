package com.qbao.aisr.stuff.pay.entry;

import com.google.common.base.Preconditions;
import com.qbao.aisr.stuff.pay.WithdrawCouponService;
import com.qbao.aisr.stuff.util.message.NotifierUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;

/**
 * @author wangping
 * @createTime 2017/6/21 下午5:54
 * $$LastChangedDate: 2017-06-21 18:59:06 +0800 (Wed, 21 Jun 2017) $$
 * $$LastChangedRevision: 1309 $$
 * $$LastChangedBy: wangping $$
 */
public class WithdrawEntry {
    public static Logger logger = Logger.getLogger(WithdrawEntry.class);
    public static void main(String[] args) throws Exception {
        String paths[] = new String[] { "spring_pay.xml" };
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        WithdrawCouponService service = ctx.getBean(WithdrawCouponService.class);
        long start = System.currentTimeMillis();

        String fileName = StringUtils.trimToEmpty(args[0]);
//        String fileName = "/Users/wangping/Downloads/withdraw.txt";
        logger.info("Starting WithDrawEntry job ...");
        logger.info("process file :"+ fileName);
        List<String> lines =FileUtils.readLines(new File(fileName),"utf-8");
        if(null!=lines){
            for (String line : lines){
                String[] values = StringUtils.split(line,'\t');
                Long userId = NumberUtils.createLong(StringUtils.trimToNull(values[0]));
                Preconditions.checkNotNull(userId,"user id is null");
                String orderId = StringUtils.trimToNull(values[1]);
                Preconditions.checkNotNull(orderId,"order id is null");
                String source = StringUtils.trimToNull(values[2]);
                Preconditions.checkNotNull(source,"source is null");
                Long amt = NumberUtils.createLong(StringUtils.trimToNull(values[3]));
                Preconditions.checkNotNull(amt,"amt is null");
                if(amt == 0 ){
                    continue;
                }
                service.withdraw(userId,orderId,source,amt);
            }
        }
        try {

        }
        catch(Exception ex){
            logger.error("返券失败:",ex);
            NotifierUtil.notifyByEmail("返券失败",ex.getMessage());
            NotifierUtil.notifyByPhone("返券失败:"+ ExceptionUtils.getRootCauseMessage(ex));
        }
        logger.info("finished pay job and cost " + (System.currentTimeMillis() - start) / 1000 + ".s");
    }
}
