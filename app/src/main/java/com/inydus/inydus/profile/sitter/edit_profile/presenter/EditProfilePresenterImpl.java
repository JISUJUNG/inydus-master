package com.inydus.inydus.profile.sitter.edit_profile.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.sitter.edit_profile.view.EditProfileView;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditProfilePresenterImpl implements EditProfilePresenter {

    ApplicationController api;
    EditProfileView view;

    public EditProfilePresenterImpl(EditProfileView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void sendProfileData(final SitterProfile profile, RequestBody profile_image, RequestBody document, RequestBody profile_cover) {
        NetworkService networkService = api.getNetworkService();

        Call<SitterProfile> sendProfileCall = networkService.sendSitterProfile(profile_image, document, profile_cover, profile);
        sendProfileCall.enqueue(new Callback<SitterProfile>() {
            @Override
            public void onResponse(Response<SitterProfile> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    api.setSitterProfile(profile);
                    view.editProfileSucceed();
                    Log.i("MyTag", "상태코드_succeed : " + response.code());
                } else {
                    Log.i("MyTag", "상태코드 : " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
                Log.i("MyTag", t.getMessage());
            }
        });
    }


}
