package com.qbao.aisr.stuff.crawler.cmp.login;

import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpHeader;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import org.apache.http.Header;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public interface ILoginService {

    public boolean login(String userName ,String userPass) throws HttpProcessException;

    public HttpCookies getCookies() throws HttpProcessException;

    public boolean loginWithQrCode() throws HttpProcessException;

    public HttpConfig getConfig();



}
