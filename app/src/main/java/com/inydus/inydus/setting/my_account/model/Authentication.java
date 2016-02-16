package com.inydus.inydus.setting.my_account.model;

/**
 * Created by JSChoi on 2016-02-15.
 */
public class Authentication {

    public String user_id;
    public String user_passwd;

    public Authentication(String user_id, String user_passwd) {
        this.user_id = user_id;
        this.user_passwd = user_passwd;
    }
}

