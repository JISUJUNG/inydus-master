package com.inydus.inydus.setting.matching_info.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.setting.matching_info.model.MatchingInfoThumbnail;
import com.inydus.inydus.setting.matching_info.presenter.MatchingInfoPresenter;
import com.inydus.inydus.setting.matching_info.presenter.MatchingInfoPresenterImpl;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MatchingInfoActivity extends AppCompatActivity implements MatchingInfoView {

    @Bind(R.id.toolbar_matching_info)
    Toolbar toolbar;
    @Bind(R.id.layoutMatchingInfoThumbnails)
    LinearLayout layout;

    LayoutInflater inflater;
    MatchingInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_info);

        ButterKnife.bind(this);

        presenter = new MatchingInfoPresenterImpl(this);
        inflater = LayoutInflater.from(this);
        initToolbar();
        presenter.setMatchingInfoThumbnails();
    }

    private void initToolbar() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void makeThumbnails(ArrayList<MatchingInfoThumbnail> thumbnails) {

        //더미데이터
        MatchingInfoThumbnail matchingInfoThumbnail = new MatchingInfoThumbnail("dd@dd", "노광훈", "010-1111-2222");
        MatchingInfoThumbnail matchingInfoThumbnail2 = new MatchingInfoThumbnail("lovemin624@gmail.com", "유원일", "010-1234-1423");
        ArrayList<MatchingInfoThumbnail> thumbnails_temp = new ArrayList<>();
        thumbnails_temp.add(matchingInfoThumbnail);
        thumbnails_temp.add(matchingInfoThumbnail2);

        for (int i = 0; i < thumbnails_temp.size(); i++) {
            makeDynamicLayoutItem(thumbnails_temp.get(i));
        }
    }

    private void makeDynamicLayoutItem(final MatchingInfoThumbnail thumbnail) {

        View view = inflater.inflate(R.layout.item_matching_info, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), thumbnail.user_id, Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), )
            }
        });

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginValue = ApplicationController.getInstance().getPixelFromDP(10);
        layoutParams.bottomMargin = marginValue;
        view.setLayoutParams(layoutParams);

        MatchingInfoThumbnailView thumbnailItem = new MatchingInfoThumbnailView(view);
        String baseUrl = ApplicationController.getInstance().getBaseUrl();
        int value = ApplicationController.getInstance().getPixelFromDP(80);

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", thumbnail.user_id)))
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .placeholder(R.mipmap.noldam_icon)
                .override(value, value)
                .into(thumbnailItem.imgProfile);

        thumbnailItem.txtName.setText(thumbnail.sitter_name);
        thumbnailItem.txtPhone.setText(thumbnail.sitter_phone);

        layout.addView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MatchingInfoThumbnailView {
        ImageView imgProfile;
        TextView txtName;
        TextView txtPhone;

        public MatchingInfoThumbnailView(View view) {

            if (view == null) {
                return;
            }
            imgProfile = (ImageView) view.findViewById(R.id.imgProfile_matchingInfoItem);
            txtName = (TextView) view.findViewById(R.id.txtName_matchingInfoItem);
            txtPhone = (TextView) view.findViewById(R.id.txtPhone_matchingInfoItem);
        }
    }
}
