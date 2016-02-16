package com.inydus.inydus.findSitter.request.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.request.model.Proposal;
import com.inydus.inydus.findSitter.request.view.RequestView;
import com.inydus.inydus.network.NetworkService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestPresenterImpl implements RequestPresenter{

    ApplicationController api;
    RequestView view;

    public RequestPresenterImpl(RequestView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void sendRequest(Proposal proposal) {
        NetworkService networkService = api.getNetworkService();
        Call<String> sendProposalCall = networkService.sendProposal(proposal);
        sendProposalCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    view.requestSucceed();
                }
                else{
                    if(response.code() == 503)
                        view.notifyDuplicated();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }
}
