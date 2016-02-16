package com.inydus.inydus.main.playdiarydetail.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.main.playdiarydetail.view.PlayDiaryDetailView;
import com.inydus.inydus.network.NetworkService;

import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JUNGJISU on 2016. 2. 9..
 */
public class PlayDiaryDetailPresenterImpl implements PlayDiaryDetailPresenter {

    PlayDiaryDetailView view;
    ApplicationController api;
    HashMap<String, String> playdetails;

    public PlayDiaryDetailPresenterImpl(PlayDiaryDetailView view, HashMap<String, String> playdetails) {
        this.view = view;
        this.playdetails = playdetails;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getPlayDiaryDetail() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Playdiary> playdiaryDetailCall = networkService.getPlayDiaryDetail(playdetails);
        playdiaryDetailCall.enqueue(new Callback<Playdiary>() {
            @Override
            public void onResponse(Response<Playdiary> response, Retrofit retrofit) {
                view.setPlayDiaryDetail(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
