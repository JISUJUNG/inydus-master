package com.inydus.inydus.register.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inydus.inydus.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTypeActivity extends AppCompatActivity {

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnSitter_type)
    public void setBtnSitter(){
        type = "Sitter";
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @OnClick(R.id.btnParent_type)
    public void setBtnParent(){
        type = "Parent";
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
