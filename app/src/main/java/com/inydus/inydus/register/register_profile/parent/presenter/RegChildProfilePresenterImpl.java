package com.inydus.inydus.register.register_profile.parent.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.register.register_profile.parent.view.RegChildProfileView;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegChildProfilePresenterImpl implements RegChildProfilePresenter {

    RegChildProfileView view;
    ApplicationController api;

    public RegChildProfilePresenterImpl(RegChildProfileView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void sendChildProfileToServer(ChildProfile childProfile, RequestBody requestBody) {
        NetworkService networkService = api.getNetworkService();
        Call<String> callSendChildProfile = networkService.sendChildProfile(requestBody, childProfile);
        callSendChildProfile.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    view.regChildProfileSucceed();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
