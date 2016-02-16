package com.inydus.inydus.setting.setting.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.setting.setting.view.SettingView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SettingPresenterImpl implements SettingPresenter{

    ApplicationController api;
    SettingView view;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void logoutFromServer() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Object> logout = networkService.logout();
        logout.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    view.logoutSucceed();
                }
                else{
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
