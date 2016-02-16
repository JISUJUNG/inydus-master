package com.inydus.inydus.login.presenter;

public interface LoginPresenter {


    void loginToServer();
    void getSitterProfileFromServer(String id);
    void getChildProfileFromServer(String id);

}
