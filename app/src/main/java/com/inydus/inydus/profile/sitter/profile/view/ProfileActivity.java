package com.inydus.inydus.profile.sitter.profile.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DynamicDayTextView;
import com.inydus.inydus.a_others.DynamicLocationTextView;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.sitter.edit_profile.view.EditProfileActivity;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.inydus.inydus.profile.sitter.profile.presenter.ProfilePresenter;
import com.inydus.inydus.profile.sitter.profile.presenter.ProfilePresenterImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    @Bind(R.id.img_sitter_profile)
    ImageView imgSitterProfile;
    @Bind(R.id.img_sitter_profile_cover)
    ImageView imgSitterProfileCover;
    @Bind(R.id.toolbar_profile)
    Toolbar toolbar;
    @Bind(R.id.txtName_profile)
    TextView txtName;
    @Bind(R.id.txtInfo_profile)
    TextView txtInfo;
    @Bind(R.id.txtSchool_profile)
    TextView txtSchool;
    @Bind(R.id.txtAge_profile)
    TextView txtAge;
    @Bind(R.id.txtGender_profile)
    TextView txtGender;
    @Bind(R.id.txtDept_profile)
    TextView txtDept;
    @Bind(R.id.layout_location_profile)
    LinearLayout layoutLocation;
    @Bind(R.id.layout_ableTime_profile)
    LinearLayout layoutAbleTime;

    ApplicationController api = ApplicationController.getInstance();
    String id = null;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        initToolbar();
        id = api.getLoginUser().user_id;

    }

    private void initToolbar() {

        if (toolbar != null) {
            toolbar.setTitle("프로필");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
            intent.putExtra("from", "profile");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProfilePresenter presenter = new ProfilePresenterImpl(this);

        if (api.getSitterProfile() == null) {
            presenter.getDataFromServer(id);
        } else {
            presenter.setProfile();
        }
    }

    @Override
    public void setProfileImage() {
        String baseUrl = ApplicationController.getInstance().getBaseUrl();
        value = ApplicationController.getInstance().getPixelFromDP(100);

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", id)))
                .placeholder(R.mipmap.pic_profile)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(imgSitterProfile);

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/cover", id)))
                .placeholder(R.mipmap.pic_profile)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgSitterProfileCover);

        Log.i("MyTag", "sitter : " + baseUrl.concat(String.format("/sit_profile/%s/profile", id)));

    }

    @Override
    public void setProfileTexts(SitterProfile sitterProfile) {

        txtName.setText(api.getLoginUser().user_name);
        txtInfo.setText(sitterProfile.sitter_info);
        txtSchool.setText(sitterProfile.sitter_univ);
        txtAge.setText(String.valueOf(sitterProfile.sitter_age));
        txtDept.setText(sitterProfile.sitter_dept);

        ArrayList<String> able_loc = new ArrayList<>();
        String loc = sitterProfile.sitter_able_loc;
        String[] locs = loc.split("_");
        Collections.addAll(able_loc, locs);
        setLocationTextViews(able_loc);

        if (sitterProfile.sitter_gender == ChildProfile.MALE)
            txtGender.setText("남자");
        else
            txtGender.setText("여자");
    }

    private void setLocationTextViews(ArrayList<String> able_loc) {

        layoutLocation.removeAllViews();

        for (int i = 0; i < able_loc.size(); i++) {

            String ableLocation = able_loc.get(i);
            DynamicLocationTextView dynamicLocationTextView =
                    new DynamicLocationTextView(ProfileActivity.this, ableLocation);
            TextView txtLocationItem = dynamicLocationTextView.buildDynamicLocationTextView();
            layoutLocation.addView(txtLocationItem);
        }
    }

    @Override
    public void setProfileTime(List<AbleTime> ableTimeList) {
        if (ableTimeList == null) {
            return;
        }

        makeAbleTimeTextView((ArrayList<AbleTime>)ableTimeList);

    }

    private void makeAbleTimeTextView(final ArrayList<AbleTime> ableTimeList) {

        layoutAbleTime.removeAllViews();

        for (int i = 0; i < ableTimeList.size(); i++) {

            final AbleTime ableTime = ableTimeList.get(i);

            DynamicDayTextView dynamicDayTextView = new DynamicDayTextView(ProfileActivity.this, ableTime);
            TextView txtDayItem = dynamicDayTextView.buildDynamicDayTextView();
            layoutAbleTime.addView(txtDayItem);
        }
    }
}
