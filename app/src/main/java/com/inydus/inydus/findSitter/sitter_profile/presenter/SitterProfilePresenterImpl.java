package com.inydus.inydus.findSitter.sitter_profile.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.findSitter.sitter_profile.view.SitterProfileView;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SitterProfilePresenterImpl implements SitterProfilePresenter {

    SitterProfileView view;
    String id;
    List<AbleTime> ableTimeList;
    String rank;

    public SitterProfilePresenterImpl(SitterProfileView view, String id) {
        this.view = view;
        this.id = id;
    }

    @Override
    public void setProfile() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<SitterProfile> sitterProfileCall = networkService.getSitterProfile(id);
        sitterProfileCall.enqueue(new Callback<SitterProfile>() {
            @Override
            public void onResponse(Response<SitterProfile> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    SitterProfile sitterProfile = response.body();
                    rank = sitterProfile.sitter_rank;
                    view.setProfileTextViews(sitterProfile);
                    view.setProfileImages(id);
                    ableTimeList = sitterProfile.able_times;
                    view.setProfileTime(ableTimeList);
                }
                else{
                    Log.i("MyTag", "상태 코드 : " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
                Log.i("MyTag", t.getMessage());
            }
        });
    }

    @Override
    public void sendRequest() {
        view.sendIntent(rank, ableTimeList);
    }
}
