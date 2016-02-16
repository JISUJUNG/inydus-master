package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.inydus.inydus.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Birth extends AppCompatActivity {

    @Bind(R.id.datePicker)
    DatePicker datePicker;

    Bundle childProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity__birth);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");

    }

    @OnClick(R.id.btn_next_regCP_birth)
    public void setBtnNext(){

        int age = calculateAge();
        String birth = setDate();
        childProfile.putInt("age", age);
        childProfile.putString("birth", birth);

        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Adres.class);
        intent.putExtra("childProfile", childProfile);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    @OnClick(R.id.btn_previous_regCP_birth)
    public void setBtnPrevious(){
        previousIntentEvent();
    }

    private String setDate() {
        String year = String.valueOf(datePicker.getYear());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String day = String.valueOf(datePicker.getDayOfMonth());

        return year + "_" + month + "_" + day;
    }

    private int calculateAge() {
        int age;

        Calendar calendar = Calendar.getInstance();
        age = calendar.get(Calendar.YEAR) - datePicker.getYear() + 1;

        return age;
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        previousIntentEvent();
    }

    private void previousIntentEvent(){
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Gender.class);
        intent.putExtra("childProfile", childProfile);
        intent.putExtra("from", "birth");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("from").equals("gender")){
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else{
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        childProfile = intent.getBundleExtra("childProfile");
    }
}
