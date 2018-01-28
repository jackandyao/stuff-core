package com.qbao.aisr.stuff.crawler.exception;

/**
 * Created by shuaizhihu on 2017/3/2.
 */
public class CrawlerException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2749168865492921426L;

    public CrawlerException(Exception e){
        super(e);
    }

    /**
     * @param msg
     */
    public CrawlerException(String msg) {
        super(msg);
    }

    /**
     * @param message
     * @param e
     */
    public CrawlerException(String message, Exception e) {
        super(message, e);
    }
}
