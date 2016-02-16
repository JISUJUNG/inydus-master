package com.inydus.inydus.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.login.presenter.LoginPresenter;
import com.inydus.inydus.login.presenter.LoginPresenterImpl;
import com.inydus.inydus.main.mypage.view.MainActivity;
import com.inydus.inydus.register.register.view.RegisterTermsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Bind(R.id.imgLogo_login)
    ImageView imgLogo_login;
    @Bind(R.id.email_login)
    EditText mEmailView;
    @Bind(R.id.password_login)
    EditText mPasswordView;
    @Bind(R.id.login_progress)
    View mProgressView;
    @Bind(R.id.login_form)
    View mLoginFormView;
    @Bind(R.id.chk_ID_save)
    CheckBox chk_id_save;
    @Bind(R.id.chk_login_remain)
    CheckBox chk_login_remain;

    private SharedPreferences sharedPreferences;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        ctx = getApplicationContext();

        sharedPreferences = getSharedPreferences("login_option", MODE_PRIVATE);

        if(sharedPreferences.getBoolean("id_save", true)){
            mEmailView.setText(sharedPreferences.getString("id", ""));
        }
        else{
            chk_id_save.setChecked(false);
        }

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_next_register)
    public void setBtnNextRegister(){
        Intent intent = new Intent(ctx, RegisterTermsActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this,
                            imgLogo_login, "logo");

            startActivity(intent, options.toBundle());
        } else {

            startActivity(intent);
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void setBtnSignIn(){
        attemptLogin();
    }

    @Override
    public void loginSucceed(String email) {
        showProgress(false);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", email);
        editor.putBoolean("id_save", chk_id_save.isChecked());
        editor.commit();

        Intent intent = new Intent(ctx, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        showProgress(false);
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void networkError() {
        showProgress(false);
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            Authentication authentication = new Authentication(email, password);
            LoginPresenter presenter = new LoginPresenterImpl(authentication, this);
            presenter.loginToServer();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 3;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}