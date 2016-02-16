package com.inydus.inydus.register.register_profile.sitter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.inydus.inydus.R;
import com.inydus.inydus.profile.sitter.edit_profile.view.EditProfileActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegSitterProfileActivity_Intro extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_sitter_profile_activity__intro);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @OnClick(R.id.btn_next_regSP_intro)
    public void setBtnNext() {
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("from", "intro");
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
