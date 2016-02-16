package com.inydus.inydus.request_box.request_detail.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.request_box.request_box.model.State;
import com.inydus.inydus.request_box.request_detail.model.MyRequestDetail;
import com.inydus.inydus.request_box.request_detail.view.RequestDetailView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestDetailPresenterImpl implements RequestDetailPresenter{

    ApplicationController api;
    RequestDetailView view;
    String parent_id, sitter_id;

    public RequestDetailPresenterImpl(RequestDetailView view, String parent_id, String sitter_id) {
        this.view = view;
        this.parent_id = parent_id;
        this.sitter_id = sitter_id;
        api = ApplicationController.getInstance();
    }

    @Override
    public void getRequestDataFromServer() {
        NetworkService networkService = api.getNetworkService();
        Call<List<MyRequestDetail>> mRequestDetailCall = networkService.getDetailRequest(parent_id, sitter_id);
        mRequestDetailCall.enqueue(new Callback<List<MyRequestDetail>>() {
            @Override
            public void onResponse(Response<List<MyRequestDetail>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.setRequestDatas(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }

    @Override
    public void sendStateToServer(String aod) {
        NetworkService networkService = api.getNetworkService();
        String sitter_id = api.getLoginUser().user_id;
        State state = new State(sitter_id, parent_id, aod);
        Call<String> callAoD = networkService.sendAcceptOrDeny(state);
        callAoD.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
