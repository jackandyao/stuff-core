package com.qbao.aisr.stuff.business.alimama.service;

import com.qbao.aisr.stuff.business.alimama.util.HttpConfig;
import com.qbao.aisr.stuff.business.alimama.util.HttpCookies;
import com.qbao.aisr.stuff.business.alimama.util.HttpProcessException;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public interface ILoginService {

    public boolean login(String userName, String userPass) throws HttpProcessException;

    public HttpCookies getCookies() throws HttpProcessException;

    public boolean loginWithQrCode() throws HttpProcessException;

    public HttpConfig getConfig();



}
