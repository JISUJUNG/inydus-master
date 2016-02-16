package com.inydus.inydus.splash.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.User;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.splash.view.SplashView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SplashPresenterImpl implements SplashPresenter{

    ApplicationController api;
    SplashView view;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void connectServer() {
        NetworkService networkService = api.getNetworkService();
        Call<User> loginTest = networkService.getSession();
        loginTest.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                view.connectingSucceed(response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
