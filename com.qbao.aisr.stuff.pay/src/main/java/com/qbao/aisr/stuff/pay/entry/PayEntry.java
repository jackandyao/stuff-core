package com.qbao.aisr.stuff.pay.entry;

import com.qbao.aisr.stuff.pay.PayCouponService;
import com.qbao.aisr.stuff.util.message.NotifierUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangping
 * @createTime 2017/6/5 下午3:59
 * $$LastChangedDate: 2017-06-21 18:48:31 +0800 (Wed, 21 Jun 2017) $$
 * $$LastChangedRevision: 1308 $$
 * $$LastChangedBy: wangping $$
 */
public class PayEntry {
    public static Logger logger = Logger.getLogger(PayEntry.class);
    public static void main(String[] args) throws Exception {
        String paths[] = new String[] { "spring_pay.xml" };
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        PayCouponService service = ctx.getBean(PayCouponService.class);
        long start = System.currentTimeMillis();
        logger.info("Starting PayEntry job ...");
        try {
            service.pay();
        }
        catch(Exception ex){
            logger.error("返券失败:",ex);
            NotifierUtil.notifyByEmail("返券失败",ex.getMessage());
            NotifierUtil.notifyByPhone("返券失败:"+ ExceptionUtils.getRootCauseMessage(ex));
        }
        logger.info("finished pay job and cost " + (System.currentTimeMillis() - start) / 1000 + ".s");
    }

}
