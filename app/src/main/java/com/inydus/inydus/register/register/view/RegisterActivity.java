package com.inydus.inydus.register.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.register.register.model.RegisterUser;
import com.inydus.inydus.register.register.presenter.RegisterPresenter;
import com.inydus.inydus.register.register.presenter.RegisterPresenterImpl;
import com.inydus.inydus.register.register_profile.parent.view.RegChildProfileActivity_Intro;
import com.inydus.inydus.register.register_profile.sitter.view.RegSitterProfileActivity_Intro;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter presenter;

    private String type;
    private Boolean nameValid = false, emailValid = false, pwValid = false, phoneNumValid = false;
    private Boolean inputValid = true;
    Bundle childProfile;


    @Bind(R.id.editName_reg)
    EditText editName;
    @Bind(R.id.editEmail_reg)
    EditText editEmail;
    @Bind(R.id.editPW_reg)
    EditText editPW;
    @Bind(R.id.editPW_test)
    EditText editPW_test;
    @Bind(R.id.editPhoneNum_reg)
    EditText editPhoneNum;
    @Bind(R.id.editVerification)
    EditText editVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenterImpl(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        ButterKnife.bind(this);
        checkEmailAndPW();
        childProfile = new Bundle();
    }

    @Override
    public void isDuplicated(String result) {
        if (result.equals("Duplicated")) {
            Toast.makeText(getApplicationContext(), "사용 중인 메일이 있습니다.", Toast.LENGTH_SHORT).show();
            emailValid = false;
        } else if (result.equals("Unduplicated")) {
            Toast.makeText(getApplicationContext(), "사용 가능한 메일입니다.", Toast.LENGTH_SHORT).show();
            emailValid = true;
        }
    }

    @OnClick(R.id.btnDuplication)
    public void setBtnDuplication() {
        final String email = editEmail.getText().toString();

        if (TextUtils.isEmpty(email)) editEmail.setError(getString(R.string.error_field_required));
        else {
            if (!isEmailValid(email)) editEmail.setError(getString(R.string.error_invalid_email));
            else presenter.checkEmailDuplication(email);
        }
    }

    @OnClick(R.id.btnVerification)
    public void setBtnVerification() {
        phoneNumValid = true;
        // TODO: 이것도 인증 api가 있을 듯
    }

    @OnClick(R.id.btnRegister_reg)
    public void setBtnRegister() {

        checkEditText();

        if (nameValid && emailValid && pwValid && phoneNumValid) {
            sendData();
        }
    }

    @Override
    public void registerSucceed() {
        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
        String id = editEmail.getText().toString();

        if (type.equals("Sitter")) {
            Intent intent = new Intent(getApplicationContext(), RegSitterProfileActivity_Intro.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
            childProfile.putString("id", id);
            Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Intro.class);
            intent.putExtra("childProfile", childProfile);
            startActivity(intent);
        }
        finish();
    }

    private void sendData() {
        String id = editEmail.getText().toString();
        String passwd = editPW.getText().toString();
        String name = editName.getText().toString();

        String phone = editPhoneNum.getText().toString();
        RegisterUser user = new RegisterUser(id, passwd, name, phone, type);

        presenter.registerToServer(user);
    }

    private void checkEditText() {
        String name = editName.getText().toString();
        String pw = editPW.getText().toString();

        if (TextUtils.isEmpty(name)) editName.setError(getString(R.string.error_field_required));
        else {
            if (!isNameValid(name)) editName.setError(getString(R.string.error_invalid_name));
            else nameValid = true;
        }

        if (TextUtils.isEmpty(pw)) editPW.setError(getString(R.string.error_field_required));
        else {
            if (!isPasswordValid()) editPW.setError(getString(R.string.error_invalid_password));
            else {
                if (!isPasswordCheck())
                    editPW_test.setError(getString(R.string.error_incorrect_password));
                else pwValid = true;
            }
        }

        if (!nameValid)
            Toast.makeText(getApplicationContext(), "이름을 확인해주세요.", Toast.LENGTH_SHORT).show();
        else if (!emailValid)
            Toast.makeText(getApplicationContext(), "이메일을 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
        else if (!pwValid)
            Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        else if (!phoneNumValid)
            Toast.makeText(getApplicationContext(), "전화번호 인증을 해주세요.", Toast.LENGTH_SHORT).show();

    }

    private void checkEmailAndPW() {
        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString();
                    if (v.getId() == R.id.editEmail_reg) {
                        if (!isEmailValid(text)) {
                            editEmail.setError(getString(R.string.error_invalid_email));
                        }
                    } else if (v.getId() == R.id.editPW_reg) {
                        if (!isPasswordValid()) {
                            editPW.setError(getString(R.string.error_invalid_password));
                        }
                    }
                }
            }
        };
        editEmail.setOnFocusChangeListener(focusChangeListener);
        editPW.setOnFocusChangeListener(focusChangeListener);
    }

    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid() {
        return editPW.getText().toString().length() >= 3;
    }

    private boolean isPasswordCheck() {
        return editPW.getText().toString().equals(editPW_test.getText().toString());
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    @OnClick(R.id.btnNext_reg_test)
    public void setBtnNext() {
        if (type.equals("Sitter")) {
            Intent intent = new Intent(getApplicationContext(), RegSitterProfileActivity_Intro.class);
            intent.putExtra("id", "pp@oi");
            startActivity(intent);
        } else {
            childProfile.putString("id", "pp@oi");
            Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Intro.class);
            intent.putExtra("childProfile", childProfile);
            startActivity(intent);
        }
    }

}
