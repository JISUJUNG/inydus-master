package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.login.view.LoginActivity;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.register.register_profile.parent.presenter.RegChildProfilePresenter;
import com.inydus.inydus.register.register_profile.parent.presenter.RegChildProfilePresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Info extends AppCompatActivity implements RegChildProfileView {

    @Bind(R.id.edit_regCP_info)
    EditText editInfo;

    Bundle childProfile;
    RegChildProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity__info);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");

        presenter = new RegChildProfilePresenterImpl(this);

    }

    @OnClick(R.id.btn_submit_regCP_info)
    public void setBtnRegUserInfo(){

        String id = childProfile.getString("id");
        String name = childProfile.getString("name");
        int gender = childProfile.getInt("gender");
        int age = childProfile.getInt("age");
        String adres = childProfile.getString("adres");
        String adres_d = childProfile.getString("adres_d");
        String birth = childProfile.getString("birth");
        String info = editInfo.getText().toString();

        ChildProfile childProfile = new ChildProfile(id, name, age, gender, birth, adres, info, adres_d);
        presenter.sendChildProfileToServer(childProfile, null);

    }

    @OnClick(R.id.btn_previous_regCP_info)
    public void setBtnPrevious(){
        previousIntentEvent();
    }

    @Override
    public void regChildProfileSucceed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
    @Override
    public void onBackPressed() {
        previousIntentEvent();
    }

    private void previousIntentEvent(){
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Adres.class);
        intent.putExtra("childProfile", childProfile);
        intent.putExtra("from", "info");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("from").equals("adres")){
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else{
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        childProfile = intent.getBundleExtra("childProfile");
    }
}
