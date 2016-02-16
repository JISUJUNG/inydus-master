package com.inydus.inydus.profile.parent.child_profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.parent.child_profile.presenter.ChildProfilePresenter;
import com.inydus.inydus.profile.parent.child_profile.presenter.ChildProfilePresenterImpl;
import com.inydus.inydus.profile.parent.edit_child_profile.view.EditChildProfileActivity;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ChildProfileActivity extends AppCompatActivity implements ChildProfileView {

    ApplicationController api;
    String id = null;

    @Bind(R.id.toolbar_child_profile)
    Toolbar toolbar;
    @Bind(R.id.img_child_profile)
    ImageView imgChildProfile;
    @Bind(R.id.txtName_child_profile)
    TextView txtName;
    @Bind(R.id.txtAge_child_profile)
    TextView txtAge;
    @Bind(R.id.txtGender_child_profile)
    TextView txtGender;
    @Bind(R.id.txtAddress_child_profile)
    TextView txtAddress;
    @Bind(R.id.txtInfo_child_profile)
    TextView txtInfo;

    ChildProfilePresenter presenter;
    private static String signature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        ButterKnife.bind(this);

        api = ApplicationController.getInstance();
        id = api.getLoginUser().user_id;
        presenter = new ChildProfilePresenterImpl(this);
        presenter.setProfile();
        initToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.child_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit_child_profile) {
            Intent intent = new Intent(getApplicationContext(), EditChildProfileActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("프로필");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ChildProfilePresenter presenter = new ChildProfilePresenterImpl(this);

        if (api.getChildProfile() == null) {
            presenter.getChildDataFromServer(id);
        } else {
            presenter.setProfile();
        }
    }

    @Override
    public void setChildProfileImage() {
        String baseUrl = ApplicationController.getInstance().getBaseUrl();
        int value = ApplicationController.getInstance().getPixelFromDP(100);

        if (signature == null) {
            signature = UUID.randomUUID().toString();
        }

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/child/%s/profile", id)))
                .placeholder(R.mipmap.pic_profile)
                .override(value, value)
                .placeholder(R.mipmap.noldam_icon)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .signature(new StringSignature(signature))
                .into(imgChildProfile);

        Log.i("MyTag", baseUrl.concat(String.format("/child/%s/profile", id)));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        signature = intent.getStringExtra("signature");
        presenter.setProfile();
    }

    @Override
    public void setChildProfileTexts(ChildProfile childProfile) {

        txtName.setText(childProfile.child_name);
        txtInfo.setText(childProfile.child_info);
        txtAddress.setText(childProfile.child_adres);
        txtAge.setText(Integer.toString(childProfile.child_age));

        if (childProfile.child_gender == ChildProfile.MALE)
            txtGender.setText("남자");
        else
            txtGender.setText("여자");
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
