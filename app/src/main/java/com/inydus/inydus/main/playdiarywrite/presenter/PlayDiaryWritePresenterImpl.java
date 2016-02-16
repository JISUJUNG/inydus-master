package com.inydus.inydus.main.playdiarywrite.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.main.playdiarywrite.view.PlayDiaryWriteView;
import com.inydus.inydus.network.NetworkService;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JUNGJISU on 2016. 2. 9..
 */
public class PlayDiaryWritePresenterImpl implements PlayDiaryWritePresenter {

    PlayDiaryWriteView view;
    ApplicationController api;

    public PlayDiaryWritePresenterImpl(PlayDiaryWriteView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void sendPlayDiary(Playdiary playdiary, RequestBody requestBody1, RequestBody requestBody2, RequestBody requestBody3) {
        NetworkService networkService = api.getNetworkService();
        Call<String> sendProfileCall = networkService.sendPlayDiary(requestBody1, requestBody2, requestBody3, playdiary);
        sendProfileCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.sendPlayDiarySucceed();
                } else {
                    Log.i("MyTag", "상태코드 : " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
