package com.inydus.inydus.setting.my_account.view;

/**
 * Created by JSChoi on 2016-02-15.
 */
public interface ConfirmView {
    void confirmSucceed(String email);
    void confirmFailed();
    void networkError();
}
