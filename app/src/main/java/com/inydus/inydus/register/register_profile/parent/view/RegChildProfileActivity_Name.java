package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.login.view.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Name extends AppCompatActivity {

    @Bind(R.id.edit_regCP_name)
    EditText editName;

    Bundle childProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity_name);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_next_regCP_name)
    public void setBtnNext(){
        String name = editName.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "아이 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        childProfile.putString("name", name);
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Gender.class);
        intent.putExtra("childProfile", childProfile);
        intent.putExtra("from", "name");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

        childProfile = intent.getBundleExtra("childProfile");
    }
}
