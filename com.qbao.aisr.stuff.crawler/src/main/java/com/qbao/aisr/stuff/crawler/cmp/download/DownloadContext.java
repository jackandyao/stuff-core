package com.qbao.aisr.stuff.crawler.cmp.download;

import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlStatus;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpMethods;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public class DownloadContext {

     Logger logger = LoggerFactory.getLogger(DownloadContext.class);

     private HttpConfig httpConfig;

     private String responseContent;

     private DlStatus dlStatus;

     public void setTask(DownloadTask c) throws CrawlerException {
          if(StringUtils.isEmpty(c.getEntryUrl())){
               throw new CrawlerException("entry URL must not be null or 空 ");
          }
          if(httpConfig==null) {
               httpConfig = HttpConfig.custom();
          }
          httpConfig.url(c.getEntryUrl());
          if(c.getMethod() == DlMethod.POST){
               httpConfig.method(HttpMethods.POST);
          }
          responseContent = null;
          dlStatus = null;
     }

     public DownloadContext(HttpConfig httpConfig){
          this.httpConfig = httpConfig;
     }
     public HttpConfig getHttpConfig() {
          return httpConfig;
     }

     public void setHttpConfig(HttpConfig httpConfig) {
          this.httpConfig = httpConfig;
     }

     public String getResponseContent() {
          return responseContent;
     }

     public void setResponseContent(String responseContent) {
          this.responseContent = responseContent;
     }

     public DlStatus getDlStatus() {
          return dlStatus;
     }

     public void setDlStatus(DlStatus dlStatus) {
          this.dlStatus = dlStatus;
     }
}
