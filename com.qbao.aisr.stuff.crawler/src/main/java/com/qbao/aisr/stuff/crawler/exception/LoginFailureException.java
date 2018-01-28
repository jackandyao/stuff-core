package com.qbao.aisr.stuff.crawler.exception;

/**
 * Created by xueming on 2017/3/17.
 */
public class LoginFailureException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2749568865492921428L;

    public LoginFailureException(Exception e){
        super(e);
    }

    /**
     * @param msg
     */
    public LoginFailureException(String msg) {
        super(msg);
    }

    /**
     * @param message
     * @param e
     */
    public LoginFailureException(String message, Exception e) {
        super(message, e);
    }
}
