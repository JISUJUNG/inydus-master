package com.inydus.inydus.register.register.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.User;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.register.register.model.RegisterUser;
import com.inydus.inydus.register.register.view.RegisterView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterPresenterImpl implements RegisterPresenter{

    NetworkService networkService;
    RegisterView view;

    public RegisterPresenterImpl(RegisterView view) {
        this.view = view;
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    public void checkEmailDuplication(String email) {
        Call<String> duplicationCall = networkService.duplicationTest(email);
        duplicationCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    view.isDuplicated(response.body());
                }
                else{
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void registerToServer(RegisterUser user) {
        Call<User> registerCall = networkService.registerUser(user);
        registerCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.registerSucceed();

                } else {
                    Log.i("MyTag", "" + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
