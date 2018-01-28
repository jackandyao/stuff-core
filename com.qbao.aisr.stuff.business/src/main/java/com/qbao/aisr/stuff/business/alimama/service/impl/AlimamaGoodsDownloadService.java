package com.qbao.aisr.stuff.business.alimama.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbao.aisr.stuff.business.alimama.service.IDownloadService;
import com.qbao.aisr.stuff.business.alimama.util.DlStatus;
import com.qbao.aisr.stuff.business.alimama.util.DownloadContext;
import com.qbao.aisr.stuff.business.alimama.util.HttpClientUtil;
import org.springframework.stereotype.Service;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 10:11
 * $$LastChangedDate: 2017-05-24 11:05:43 +0800 (Wed, 24 May 2017) $$
 * $$LastChangedRevision: 982 $$
 * $$LastChangedBy: liaijun $$
 */
@Service
public class AlimamaGoodsDownloadService implements IDownloadService {

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsDownloadService.class);

    @Override
    public boolean downLoad(DownloadContext context) throws Exception {
        String json="";
        try {
            context.setResponseContent(HttpClientUtil.send(context.getHttpConfig()));
            json=context.getResponseContent();
            if(StringUtils.isBlank(json)){
                logger.info("获取数据为空>>>>>>json="+json);
                return false;
            }
            JSONObject jsonObject = JSON.parseObject(json);
            if(jsonObject.containsKey("status")) {
                if(jsonObject.getInteger("status")==1111){
                    logger.info("网络拥堵！请求失败!");
                    return false;
                }
            }
//            else  if(jsonObject.containsKey("invalidKey")){
//                logger.info("无效的key!!!url为:"+context.getHttpConfig().url());
//                return false;
//            }
            else if(jsonObject.containsKey("data")){
                logger.info(context.getResponseContent());
                context.setDlStatus(DlStatus.SUCCESS);
                return true;
            }
            logger.info(context.getResponseContent());
            return false;

        } catch(Exception e){
            logger.error("返回数据>>>>json="+json+">>>>>>>>>>>>"+ExceptionUtils.getFullStackTrace(e));
            context.setDlStatus(DlStatus.FAIL);
            return false;
        }

    }


}
