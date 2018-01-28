package com.qbao.aisr.stuff.pay.bean;

/**
 * @author by zhangchanghong on 15/12/23.
 */
public class DataBizException extends Exception {
	private static final long serialVersionUID = -1915703276859932822L;
	private int code;
    private String msg;
    public DataBizException(){
        super();
    }

	public DataBizException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

    public DataBizException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMessage(){
        return this.msg;
    }

	public int getCode() {
		return code;
	}
}
