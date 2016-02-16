package com.inydus.inydus.register.register.model;

public class RegisterUser {

    public String user_id;
    public String user_passwd;
    public String user_name;
    public String user_phone;
    public  String user_type;

    public RegisterUser(String user_id, String user_passwd, String user_name, String user_phone, String user_type) {
        this.user_id = user_id;
        this.user_passwd = user_passwd;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_type = user_type;
    }
}
