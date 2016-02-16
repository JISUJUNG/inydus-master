package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.inydus.inydus.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Intro extends AppCompatActivity {

    Bundle childProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity__intro);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");
    }

    @OnClick(R.id.btn_next_regCP_intro)
    public void setBtnNext(){
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Name.class);
        intent.putExtra("childProfile", childProfile);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

}
