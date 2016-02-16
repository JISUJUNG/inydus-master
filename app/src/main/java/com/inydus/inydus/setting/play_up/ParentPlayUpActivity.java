package com.inydus.inydus.setting.play_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DividerItemDecoration;
import com.inydus.inydus.setting.play_up.component.PlayUpListAdapter;
import com.inydus.inydus.setting.play_up.model.PlayUp;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ParentPlayUpActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_playUp_parent)
    Toolbar toolbar;
    @Bind(R.id.recyclerView_playUp_parent)
    RecyclerView recyclerView;

    ArrayList<PlayUp> playUpList;
    PlayUpListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_play_up);

        ButterKnife.bind(this);

        initToolbar();
        initValue();
    }

    private void initValue() {
        playUpList = new ArrayList<>();

        //***************************더미데이터***************************
        PlayUp playUp = new PlayUp("dd@dd", "노광훈", 20, 40);
        PlayUp playUp2 = new PlayUp("lovemin624@gmail.com", "심현민", 2, 30);
        PlayUp playUp3 = new PlayUp("asd@naver.com", "홍길동", 30, 30);
        playUpList.add(playUp);
        playUpList.add(playUp2);
        playUpList.add(playUp3);
        //***************************더미데이터***************************

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new PlayUpListAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setPlayUpList(playUpList);

    }

    private void initToolbar() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
