package com.inydus.inydus.main.mypage.presenter;

import android.util.Log;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.view.MyPageView;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JUNGJISU on 2016. 1. 25..
 */
public class MyPagePresenterImpl implements MyPagePresenter {
    ApplicationController api;
    MyPageView view;

    public MyPagePresenterImpl(MyPageView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getPlayDiaryFromServer(String user_id) {
        NetworkService networkService = api.getNetworkService();
        Call<List<Playdiary>> playDiaryCall = networkService.getPlayDiaryList(user_id);
        playDiaryCall.enqueue(new Callback<List<Playdiary>>() {
            @Override
            public void onResponse(Response<List<Playdiary>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.setPlayDiaryList((ArrayList<Playdiary>)response.body());

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
}