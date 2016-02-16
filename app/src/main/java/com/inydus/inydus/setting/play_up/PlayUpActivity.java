package com.inydus.inydus.setting.play_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inydus.inydus.R;

import butterknife.ButterKnife;

public class PlayUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_up);

        ButterKnife.bind(this);
    }
}
