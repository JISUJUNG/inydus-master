package com.inydus.inydus.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.inydus.inydus.R;
import com.inydus.inydus.login.view.LoginActivity;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity {

    @Bind(R.id.viewPager_tutorial)
    ViewPager viewPager;

    @Bind(R.id.indicator)
    CirclePageIndicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //************************************************************************
        Button btnNext = (Button)findViewById(R.id.btn_next_tutorial);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //************************************************************************

        ButterKnife.bind(this);
        SectionsPagerAdapter mSectionPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        mIndicator.setViewPager(viewPager);
    }
}
