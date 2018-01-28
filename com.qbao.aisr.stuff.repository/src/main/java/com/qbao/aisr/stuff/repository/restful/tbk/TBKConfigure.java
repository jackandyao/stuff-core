package com.qbao.aisr.stuff.repository.restful.tbk;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by shuaizhihu on 2017/3/3.
 */
public class TBKConfigure {

    @Value("${tbk.appkey}")
    private String tb_appkey;
    @Value("${tbk.appsecret}")
    private String tb_appsecret;
    @Value("${tbk.url}")
    private String tb_url;

    public String getTb_appkey() {
        return tb_appkey;
    }

    public void setTb_appkey(String tb_appkey) {
        this.tb_appkey = tb_appkey;
    }

    public String getTb_appsecret() {
        return tb_appsecret;
    }

    public void setTb_appsecret(String tb_appsecret) {
        this.tb_appsecret = tb_appsecret;
    }

    public String getTb_url() {
        return tb_url;
    }

    public void setTb_url(String tb_url) {
        this.tb_url = tb_url;
    }
}
