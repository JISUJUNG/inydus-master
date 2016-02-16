package com.inydus.inydus.login.view;

public interface LoginView {
    void loginSucceed(String email);
    void loginFailed();
    void networkError();
}
