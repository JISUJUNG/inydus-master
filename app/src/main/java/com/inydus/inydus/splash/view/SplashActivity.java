package com.inydus.inydus.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.main.mypage.view.MainActivity;
import com.inydus.inydus.splash.presenter.SplashPresenterImpl;
import com.inydus.inydus.tutorial.TutorialActivity;

public class SplashActivity extends AppCompatActivity implements SplashView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashPresenterImpl splashPresenterImpl = new SplashPresenterImpl(this);
        splashPresenterImpl.connectServer();
    }

    @Override
    public void connectingSucceed(int statusCode) {
        Intent intent;
        if(statusCode == 200){
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(statusCode == 401){
            intent = new Intent(getApplicationContext(), TutorialActivity.class);
        }
        else{
            return;
        }

        startActivity(intent);
        finish();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
