package com.inydus.inydus.setting.my_account.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.setting.my_account.view.ConfirmView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MyAccountPresenterImpl implements MyAccountPresenter {
    ApplicationController api;
    Authentication authentication;
    NetworkService networkService;
    ConfirmView view;

    public MyAccountPresenterImpl(Authentication authentication, ConfirmView view) {
        this.authentication = authentication;
        api = ApplicationController.getInstance();
        this.view = view;
        networkService = api.getNetworkService();
    }

    @Override
    public void pwconfirm() {
        Call<Object> confirm = networkService.confirm(authentication);

        confirm.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.confirmSucceed(authentication.user_id);
                }
                else{
                    view.confirmFailed();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }

    @Override
    public void changepasswd() {
        Call<Object> change = networkService.changepw(authentication);

        change.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.confirmSucceed(authentication.user_id);
                } else {
                    view.confirmFailed();
                }
            }
            @Override
            public void onFailure(Throwable t) {  view.networkError();}
        });
    }
}
