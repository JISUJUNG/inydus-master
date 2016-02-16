package com.inydus.inydus.findSitter.findsitter.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.findsitter.model.Sitter;
import com.inydus.inydus.findSitter.findsitter.view.FindSitterView;
import com.inydus.inydus.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FindSitterPresenterImpl implements FindSitterPresenter{

    FindSitterView view;
    HashMap<String, String> conditions;

    public FindSitterPresenterImpl(FindSitterView view, HashMap<String, String> conditions) {
        this.view = view;
        this.conditions = conditions;
    }

    @Override
    public void makeListView() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<List<Sitter>> sitterListCall = networkService.getSitterList(conditions);
        sitterListCall.enqueue(new Callback<List<Sitter>>() {
            @Override
            public void onResponse(Response<List<Sitter>> response, Retrofit retrofit) {
                view.setSitterList((ArrayList<Sitter>) response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}