package com.qbao.aisr.stuff.crawler.cmp.login;

import java.util.HashMap;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public class LoginForm {
    private String userName;
    private String userPass;
    private String action;

    private HashMap<String,Object> params ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}
