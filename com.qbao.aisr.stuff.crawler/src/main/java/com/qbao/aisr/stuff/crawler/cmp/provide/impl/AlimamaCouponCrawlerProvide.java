package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 18:13
 * $$LastChangedDate: 2017-06-15 10:40:19 +0800 (Thu, 15 Jun 2017) $$
 * $$LastChangedRevision: 1220 $$
 * $$LastChangedBy: liaijun $$
 */
@Component(value="alimamaCouponCrawlerProvide")
public class AlimamaCouponCrawlerProvide implements ICrawlerProvide {
	@Autowired
    private StuffCouponDao stuffCouponDao;
	@Value("${tbk.table.name}")
	private String tableName;

    @Override
    public void provide(CrawlerContext crawlerContext) throws InterruptedException, UnsupportedEncodingException {
    	boolean isLoop = true;
    	int page=1;
        while(isLoop){
    	int limit=2000;
    	int offset= (page - 1) * limit;
        //List<StuffCoupon> list = stuffCouponDao.queryStuffCoupon(tableName,offset,limit);
    	List<StuffCoupon> list = stuffCouponDao.queryStuffCouponByIntroduce(tableName,offset,limit);
        for(StuffCoupon sc : list){
            
            DownloadTask<StuffCoupon> task = new DownloadTask<StuffCoupon>();
            task.setEntryUrl(AlimamaUtil.couponURLForTime(sc.getRealStuffId()));
            task.setMethod(DlMethod.GET);
            task.setData(sc);
            crawlerContext.dlTaskQueue.put(task);
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            try {
            	//字符串链接url用＃代表已经处理，时间就用固定的时间，重跑job时清掉此时间
//				Date date = sdf.parse("2010-01-01 01:00:00");
//				sc.setEndTime(date);
//				sc.setStartTime(date);
//				stuffCouponDao.updateStuffCoupon(tableName, sc);
            	stuffCouponDao.updateStuffCouponByIntroduce(tableName,"#","#",sc.getStuffId());
			} catch (Exception e) {
				e.printStackTrace();
			}
            
            Thread.sleep(500);
            
        }
        if(CollectionUtils.isEmpty(list)||list.size()<limit){
        	isLoop=false;
        }
        page++;
      }

    }
}
