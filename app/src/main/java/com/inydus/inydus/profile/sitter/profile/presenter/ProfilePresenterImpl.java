package com.inydus.inydus.profile.sitter.profile.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.inydus.inydus.profile.sitter.profile.view.ProfileView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfilePresenterImpl implements ProfilePresenter{

    ApplicationController api;
    ProfileView view;

    public ProfilePresenterImpl(ProfileView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getDataFromServer(String id) {
        NetworkService networkService = api.getNetworkService();
        Call<SitterProfile> sitterProfileCall = networkService.getSitterProfile(id);
        sitterProfileCall.enqueue(new Callback<SitterProfile>() {
            @Override
            public void onResponse(Response<SitterProfile> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    SitterProfile sitterProfile = response.body();
                    Log.i("MyTag", response.body().able_times.get(0).sittertime_day);
                    api.setSitterProfile(sitterProfile);
                    setProfile();

                } else {
                    Log.i("MyTag", "상태 코드 : " + response.code());
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.i("MyTag", "에러 내용 : " + t.getMessage());
            }
        });
    }

    @Override
    public void setProfile() {
        view.setProfileImage();
        view.setProfileTexts(api.getSitterProfile());
        view.setProfileTime(api.getSitterProfile().able_times);
    }
}
