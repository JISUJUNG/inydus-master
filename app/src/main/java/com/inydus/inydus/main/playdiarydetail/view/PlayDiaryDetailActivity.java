package com.inydus.inydus.main.playdiarydetail.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.main.playdiarydetail.presenter.PlayDiaryDetailPresenter;
import com.inydus.inydus.main.playdiarydetail.presenter.PlayDiaryDetailPresenterImpl;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayDiaryDetailActivity extends AppCompatActivity implements PlayDiaryDetailView {

    @Bind(R.id.txt_keyword1_play_diary_detail)
    TextView keyword1;
    @Bind(R.id.txt_keyword2_play_diary_detail)
    TextView keyword2;
    @Bind(R.id.txt_keyword3_play_diary_detail)
    TextView keyword3;
    @Bind(R.id.txtName_play_diary_detail)
    TextView name;
    @Bind(R.id.txtTitle_play_diary_detail)
    TextView title;
    @Bind(R.id.txt_date_play_diary_detail)
    TextView date;
    @Bind(R.id.pager_play_diary_detail)
    ViewPager viewPager;
    @Bind(R.id.imgName_play_diary_detail)
    ImageView imgGender;

    ApplicationController api;
    String parent_id, child_name, play_no;
    HashMap<String, String> playdetails;
    ArrayList<String> playdiaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_diary_detail);

        ButterKnife.bind(this);

        Intent i = getIntent();
        parent_id = i.getStringExtra("parent_id");
        child_name = i.getStringExtra("child_name");
        play_no = i.getStringExtra("play_no");

        api = ApplicationController.getInstance();
        setPager();
    }

    private void setPager() {

        String baseUrl = api.getBaseUrl();

        playdiaryList = new ArrayList<>();
        playdiaryList.add(0,baseUrl.concat(String.format("/play/%s/%s/photo1", api.getLoginUser().user_id, parent_id)));
        playdiaryList.add(1,baseUrl.concat(String.format("/play/%s/%s/photo2", api.getLoginUser().user_id, parent_id)));
        playdiaryList.add(2,baseUrl.concat(String.format("/play/%s/%s/photo3", api.getLoginUser().user_id, parent_id)));
        PlayDetailPagerAdapter playDetailPagerAdapter = new PlayDetailPagerAdapter(getApplicationContext());
        viewPager.setAdapter(playDetailPagerAdapter);
        playDetailPagerAdapter.setPlayImageList(playdiaryList);
        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        playdetails = new HashMap<>();
        playdetails.put("user_id", api.getLoginUser().user_id);
        playdetails.put("parent_id", parent_id);
        playdetails.put("child_name", child_name);
        playdetails.put("play_no", play_no);

        PlayDiaryDetailPresenter presenter = new PlayDiaryDetailPresenterImpl(this, playdetails);
        presenter.getPlayDiaryDetail();

    }

    @Override
    public void setPlayDiaryDetail(Playdiary playdiary) {

        Log.i("MyTag", "image() : " + parent_id);
        Log.i("MyTag", "image() : " + playdiary.play_keyword);

        String[] keywords = (playdiary.play_keyword).split("_");
        keyword1.setText(keywords[0]);
        keyword2.setText(keywords[1]);
        keyword3.setText(keywords[2]);
        title.setText(playdiary.play_title);
        name.setText(playdiary.child_name);
        String dates[] = playdiary.play_date.split("_");
        date.setText(dates[1] + "월" + " " + dates[2] + "일");

        if(playdiary.child_gender == ChildProfile.MALE)
            imgGender.setImageResource(R.drawable.btn_male);
        else
            imgGender.setImageResource(R.drawable.btn_female);



    }
}
