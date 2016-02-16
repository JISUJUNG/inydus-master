package com.inydus.inydus.setting.my_account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends AppCompatActivity {

    @Bind(R.id.txtId_myAccount)
    TextView txtId;

    ApplicationController api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        ButterKnife.bind(this);

        api = ApplicationController.getInstance();
        txtId.setText(api.getLoginUser().user_id);
    }

    @OnClick(R.id.layoutBtnPwChange)
    public void setBtnPwChange(){
        Intent intent = new Intent(getApplicationContext(), PasswordConfrimActivity.class);
        startActivity(intent);
        //// TODO: 2016-02-12 계정 비밀번호 변경
    }
}
