package com.inydus.inydus.findSitter.sitter_profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DynamicDayTextView;
import com.inydus.inydus.a_others.DynamicLocationTextView;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.request.view.RequestActivity;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.findSitter.sitter_profile.presenter.SitterProfilePresenter;
import com.inydus.inydus.findSitter.sitter_profile.presenter.SitterProfilePresenterImpl;
import com.inydus.inydus.network.NetworkService;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SitterProfileActivity extends AppCompatActivity implements SitterProfileView{

    @Bind(R.id.img_sitter_profile)
    ImageView imgSitterProfile;
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
    @Bind(R.id.btnRequest_profile)
    Button btnRequest;
    @Bind(R.id.layout_location_profile)
    LinearLayout layoutLocation;
    @Bind(R.id.layout_ableTime_profile)
    LinearLayout layoutAbleTime;

    ArrayList<String> able_loc = new ArrayList<String>();
    NetworkService networkService;
    SitterProfilePresenter sitterProfilePresenter;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        networkService = ApplicationController.getInstance().getNetworkService();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        ButterKnife.bind(this);

        sitterProfilePresenter = new SitterProfilePresenterImpl(this, id);
        sitterProfilePresenter.setProfile();

        if(ApplicationController.getInstance().getLoginUser().user_type.equals("Parent")){
            btnRequest.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.btnRequest_profile)
    public void request(){
        sitterProfilePresenter.sendRequest();
    }

    @Override
    public void setProfileTextViews(SitterProfile sitterProfile) {
        txtName.setText(sitterProfile.user_name);
        txtInfo.setText(sitterProfile.sitter_info);
        txtSchool.setText(sitterProfile.sitter_univ);
        txtAge.setText(String.valueOf(sitterProfile.sitter_age));
        txtDept.setText(sitterProfile.sitter_dept);
        if (sitterProfile.sitter_gender == 0)
            txtGender.setText("남자");
        else
            txtGender.setText("여자");

        String loc = sitterProfile.sitter_able_loc;
        String[] locs = loc.split("_");
        Collections.addAll(able_loc, locs);
        layoutLocation.removeAllViews();
        setLocationTextViews(able_loc);

    }

    @Override
    public void setProfileImages(String id) {

        String baseUrl = ApplicationController.getInstance().getBaseUrl();

        int value = ApplicationController.getInstance().getPixelFromDP(100);

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", id)))
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(imgSitterProfile);
    }

    @Override
    public void setProfileTime(List<AbleTime> ableTimeList) {
        if (ableTimeList == null) {
            return;
        }

        makeAbleTimeTextView((ArrayList<AbleTime>) ableTimeList);

    }

    private void makeAbleTimeTextView(final ArrayList<AbleTime> ableTimeList) {

        for (int i = 0; i < ableTimeList.size(); i++) {

            final AbleTime ableTime = ableTimeList.get(i);

            DynamicDayTextView dynamicDayTextView = new DynamicDayTextView(SitterProfileActivity.this, ableTime);
            TextView txtDayItem = dynamicDayTextView.buildDynamicDayTextView();
            layoutAbleTime.addView(txtDayItem);
        }
    }

    private void setLocationTextViews(ArrayList<String> able_loc) {

        for (int i = 0; i < able_loc.size(); i++) {

            String ableLocation = able_loc.get(i);
            DynamicLocationTextView dynamicLocationTextView =
                    new DynamicLocationTextView(SitterProfileActivity.this, ableLocation);
            TextView txtLocationItem = dynamicLocationTextView.buildDynamicLocationTextView();
            layoutLocation.addView(txtLocationItem);
        }
    }

    @Override
    public void sendIntent(String rank, List<AbleTime> ableTimeList) {
        Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("rank", rank);
        intent.putParcelableArrayListExtra("ableTimeList", (ArrayList<AbleTime>)ableTimeList);
        startActivity(intent);
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
