package com.inydus.inydus.setting.my_account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.setting.my_account.presenter.MyAccountPresenter;
import com.inydus.inydus.setting.my_account.presenter.MyAccountPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordConfrimActivity extends AppCompatActivity implements ConfirmView {

    @Bind(R.id.txtId_pwConfirm)
    TextView txtId;
    @Bind(R.id.editPW_comfirm)
    EditText editpw_confirm;

    ApplicationController api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirm);

        ButterKnife.bind(this);

        api = ApplicationController.getInstance();
        txtId.setText(api.getLoginUser().user_id);
    }

    @OnClick(R.id.btnPw_confirm)
    public void setBtnPwConfirm(){

        attemptconfirm();
        //Intent intent = new Intent(getApplicationContext(), PasswordConfrimActivity.class);
        //startActivity(intent);
        //// TODO: 2016-02-12 계정 비밀번호 변경
    }

    private void attemptconfirm() {
        String email = api.getLoginUser().user_id;
        String password = editpw_confirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editpw_confirm.setError(getString(R.string.error_invalid_password));
            focusView = editpw_confirm;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            Authentication authentication = new Authentication(email, password);
            MyAccountPresenter presenter = new MyAccountPresenterImpl(authentication, this);
            presenter.pwconfirm();
        }
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    @Override
    public void confirmSucceed(String email) {
        Intent intent = new Intent(getApplicationContext(), NewPassword.class); // pw 변경으로
        startActivity(intent);
        finish();
    }

    @Override
    public void confirmFailed() {
        editpw_confirm.setError(getString(R.string.error_incorrect_password));
        editpw_confirm.requestFocus();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 3;
    }
}
