package com.inydus.inydus.profile.parent.child_profile.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.parent.child_profile.view.ChildProfileView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ChildProfilePresenterImpl implements ChildProfilePresenter{

    ApplicationController api;
    ChildProfileView view;

    public ChildProfilePresenterImpl(ChildProfileView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getChildDataFromServer(String id) {
        NetworkService networkService = api.getNetworkService();
        Call<List<ChildProfile>> childProfileCall = networkService.getChildProfile(id);
        childProfileCall.enqueue(new Callback<List<ChildProfile>>() {
            @Override
            public void onResponse(Response<List<ChildProfile>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    if(response.body().size() == 0){
                        return;
                    }
                    ChildProfile childProfile = response.body().get(0);
                    api.setChildProfile(childProfile);
                    setProfile();
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

    @Override
    public void setProfile() {
        view.setChildProfileImage();
        view.setChildProfileTexts(api.getChildProfile());
    }
}
