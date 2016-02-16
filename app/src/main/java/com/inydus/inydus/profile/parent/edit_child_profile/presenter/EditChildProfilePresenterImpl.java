package com.inydus.inydus.profile.parent.edit_child_profile.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.parent.edit_child_profile.view.EditChildProfileView;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditChildProfilePresenterImpl implements EditChildProfilePresenter {

    ApplicationController api;
    EditChildProfileView view;

    public EditChildProfilePresenterImpl(EditChildProfileView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void sendChildProfile(final ChildProfile childProfile, RequestBody requestBody) {

        NetworkService networkService = api.getNetworkService();
        Call<String> callChildProfileTest = networkService.sendChildProfile(requestBody, childProfile) ;
        callChildProfileTest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    api.setChildProfile(childProfile);
                    view.editChildProfileSucceed();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
