package com.inydus.inydus.register.postoffice_api.presenter;

import android.util.Log;

import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.register.postoffice_api.component.PostAPI_serviceGenerator;
import com.inydus.inydus.register.postoffice_api.model.AddressResponse;
import com.inydus.inydus.register.postoffice_api.view.PostDialogView;

import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PostDialogPresenterImpl implements PostDialogPresenter{

    PostDialogView view;

    public PostDialogPresenterImpl(PostDialogView view) {
        this.view = view;
    }

    @Override
    public void searchAddress(String query, String serviceKey) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("serviceKey", serviceKey);
        parameters.put("srchwrd", query);
        parameters.put("countPerPage", "50");
        parameters.put("currentPage", "1");

        NetworkService networkService = PostAPI_serviceGenerator.buildService();
        Call<AddressResponse> callAddress =  networkService.getAddress(parameters);

        callAddress.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Response<AddressResponse> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    AddressResponse addressResponse = response.body();
                    view.setAddressList(addressResponse);
                }
                else{
                    Log.i("MyTag", "오류 코드 : " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("MyTag", t.getMessage());
            }
        });
    }
}
