package com.example.administrator.testjson;

/**
 * Created by Administrator on 2017/4/23.
 */

public class Manager {
    private String uname;
    private String upwd;

    public Manager(String uname, String upwd) {
        this.uname = uname;
        this.upwd = upwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
