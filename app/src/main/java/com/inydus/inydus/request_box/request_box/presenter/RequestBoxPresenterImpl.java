package com.inydus.inydus.request_box.request_box.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.request_box.request_box.model.MyRequest;
import com.inydus.inydus.request_box.request_box.view.RequestBoxView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestBoxPresenterImpl implements RequestBoxPresenter {

    ApplicationController api;
    RequestBoxView view;

    public RequestBoxPresenterImpl(RequestBoxView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getRequestsFromServer() {
        NetworkService networkService = api.getNetworkService();
        String user_id = api.getLoginUser().user_id;
        Call<List<MyRequest>> requestsCall = networkService.getRequests(user_id);
        requestsCall.enqueue(new Callback<List<MyRequest>>() {
            @Override
            public void onResponse(Response<List<MyRequest>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.setRequestLists((ArrayList<MyRequest>) response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
