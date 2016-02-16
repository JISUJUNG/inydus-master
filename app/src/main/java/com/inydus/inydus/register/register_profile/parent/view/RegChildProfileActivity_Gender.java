package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ToggleButton;

import com.inydus.inydus.R;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Gender extends AppCompatActivity {

    @Bind(R.id.btnMale_regCP_gender)
    ToggleButton btnMale;
    @Bind(R.id.btnFeMale_regCP_gender)
    ToggleButton btnFemale;

    Bundle childProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity__gender);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");
    }

    @OnClick(R.id.btn_next_regCP_gender)
    public void setBtnNext(){
        int gender;
        if(btnMale.isChecked()){
            gender = ChildProfile.MALE;
        }
        else{
            gender = ChildProfile.FEMALE;
        }

        childProfile.putInt("gender", gender);
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Birth.class);
        intent.putExtra("childProfile", childProfile);
        intent.putExtra("from", "gender");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @OnClick(R.id.btn_previous_regCP_gender)
    public void setBtnPrevious(){
        previousIntentEvent();
    }

    @OnClick(R.id.btnMale_regCP_gender)
    public void setBtnMale(){
        btnMale.setChecked(true);
        btnFemale.setChecked(false);
    }

    @OnClick(R.id.btnFeMale_regCP_gender)
    public void setBtnFemale(){
        btnMale.setChecked(false);
        btnFemale.setChecked(true);
    }
    @Override
    public void onBackPressed() {
        previousIntentEvent();
    }

    private void previousIntentEvent(){
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Name.class);
        intent.putExtra("childProfile", childProfile);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("from").equals("name")){
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else{
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        childProfile = intent.getBundleExtra("childProfile");
    }
}
