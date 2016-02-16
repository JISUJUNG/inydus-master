package com.inydus.inydus.login.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.login.model.LoginInfo;
import com.inydus.inydus.login.model.User;
import com.inydus.inydus.login.view.LoginView;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginPresenterImpl implements LoginPresenter{

    ApplicationController api;
    Authentication authentication;
    LoginView view;
    NetworkService networkService;

    public LoginPresenterImpl(Authentication authentication, LoginView view) {
        this.authentication = authentication;
        this.view = view;
        api = ApplicationController.getInstance();
        networkService = api.getNetworkService();
    }

    @Override
    public void loginToServer() {
        Call<LoginInfo> login = networkService.login(authentication);

        login.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Response<LoginInfo> response, Retrofit retrofit) {
                if(response.isSuccess()){

                    LoginInfo loginInfo = response.body();

                    User user = new User();
                    user.user_id = loginInfo.user_id;
                    user.user_name = loginInfo.user_name;
                    user.user_type = loginInfo.user_type;
                    if(loginInfo.user_type.equals("Sitter")){
                        api.setSitterProfile(loginInfo.sitter_profile);
                    }
                    else{
                        api.setChildProfile(loginInfo.child_profile);
                    }

                    api.setLoginUser(user);

                    view.loginSucceed(authentication.user_id);

                }
                else{
                    view.loginFailed();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.networkError();
            }
        });
    }

    @Override
    public void getSitterProfileFromServer(String id) {
        Call<SitterProfile> sitterProfileCall = networkService.getSitterProfile(id);
        //sitterProfileCall

    }

    @Override
    public void getChildProfileFromServer(String id) {

    }
}
