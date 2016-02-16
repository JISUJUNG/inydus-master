package com.inydus.inydus.login.model;

public class Authentication {

    public String user_id;
    public String user_passwd;

    public Authentication(String user_id, String user_passwd) {
        this.user_id = user_id;
        this.user_passwd = user_passwd;
    }
}
