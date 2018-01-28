package com.qbao.aisr.stuff.crawler.cmp.download;

import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlStatus;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.cmp.login.alimama.AlimamaLoginServiceImpl;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
@Component
public interface IDownloadService {



    public boolean  downLoad(DownloadContext context) throws CrawlerException, HttpProcessException;

//

}
