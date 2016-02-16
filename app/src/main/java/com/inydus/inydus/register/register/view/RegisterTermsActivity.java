package com.inydus.inydus.register.register.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

import com.inydus.inydus.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterTermsActivity extends AppCompatActivity {

    @Bind(R.id.chk_service_terms)
    CheckBox chk_service_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_terms);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.txt_service_terms)
    public void serviceTerms(){
        Toast.makeText(getApplicationContext(), "서비스 이용약관", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_privacy_policy)
    public void privacyPolicy() {
        Toast.makeText(getApplicationContext(), "개인 정보 취급 방침", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_regUserInfo)
    public void setBtnRegUserInfo(){
        if(chk_service_terms.isChecked()){
            Intent intent = new Intent(getApplicationContext(), UserTypeActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
