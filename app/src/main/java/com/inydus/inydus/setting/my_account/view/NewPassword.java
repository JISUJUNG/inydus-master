package com.inydus.inydus.setting.my_account.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.setting.my_account.presenter.MyAccountPresenter;
import com.inydus.inydus.setting.my_account.presenter.MyAccountPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPassword extends AppCompatActivity implements ConfirmView {

    @Bind(R.id.editPW_new)
    EditText editpw_new;
    @Bind(R.id.editPW_new_confirm)
    EditText editpw_new_confirm;

    ApplicationController api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        ButterKnife.bind(this);

        api = ApplicationController.getInstance();
    }

    @OnClick(R.id.btnPw_new_confirm)
    public void setBtnPwNewConfirm(){

        attempt_new_confirm();
        //Intent intent = new Intent(getApplicationContext(), PasswordConfrimActivity.class);
        //startActivity(intent);
        //// TODO: 2016-02-12 계정 비밀번호 변경
    }

    private void attempt_new_confirm() {
       if(new_pw_check())
       {
           attempt_update_password();
       }
        else {
           Toast.makeText(getApplicationContext(), "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
           editpw_new_confirm.requestFocus();
       }
    }

    @Override
    public void confirmSucceed(String email) {
        Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void confirmFailed() {
        editpw_new_confirm.setError(getString(R.string.error_incorrect_password));
        editpw_new_confirm.requestFocus();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    private void attempt_update_password() {
        String email = api.getLoginUser().user_id;
        String password = editpw_new_confirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editpw_new_confirm.setError(getString(R.string.error_invalid_password));
            focusView = editpw_new_confirm;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            Authentication authentication = new Authentication(email, password);
            MyAccountPresenter presenter = new MyAccountPresenterImpl(authentication, this);
            presenter.changepasswd();
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 3;
    }

    private boolean new_pw_check() {
        return editpw_new.getText().toString().equals(editpw_new_confirm.getText().toString());
    }
}
