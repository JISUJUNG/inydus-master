package com.inydus.inydus.register.able_location;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.findSitter.condition.ConditionActivity;
import com.inydus.inydus.profile.sitter.edit_profile.view.EditProfileActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class AbleLocationActivity extends AppCompatActivity {

    private int count;
    private String from;

    @Bind(R.id.gridView_able_location)
    GridView gridView_location;
    @Bind(R.id.toolbar_able_location)
    Toolbar toolbar;

    AbleLocationAdapter adapter;
    ArrayList<AbleLocation> ableLocation_list;
    ArrayList<String> able_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_able_location);

        ButterKnife.bind(this);
        count = 0;

        Intent intent = getIntent();
        from = intent.getStringExtra("from");

        initToolbar();
        initModel();
        initGrid();

    }

    private void initGrid() {
        adapter = new AbleLocationAdapter(getApplicationContext());
        gridView_location.setAdapter(adapter);

        adapter.setAbleLocations(ableLocation_list);
    }

    private void initModel() {

        able_loc = new ArrayList<>();
        String[] ableLocations = getResources().getStringArray(R.array.able_location);
        ableLocation_list = new ArrayList<>();
        for (int i = 0; i < ableLocations.length; i++) {
            AbleLocation ableLocation_temp = new AbleLocation(AbleLocation.NORMAL_TEXT_COLOR_CODE, ableLocations[i]);
            ableLocation_list.add(i, ableLocation_temp);
        }
    }

    @OnItemClick(R.id.gridView_able_location)
    public void setGridView_location(int position) {
        AbleLocation ableLocation = (AbleLocation) adapter.getItem(position);
        int limit;
        if (from.equals("condition"))
            limit = 2;
        else
            limit = 4;

        if (ableLocation.textColorCode == AbleLocation.NORMAL_TEXT_COLOR_CODE) {
            if (count > limit) {
                String msg = String.format("최대 %d지역까지 선택 가능합니다.", limit + 1);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return;
            }
            ableLocation.textColorCode = AbleLocation.CLICKED_TEXT_COLOR_CODE;
            count++;
            able_loc.add(ableLocation.location);
        } else if (ableLocation.textColorCode == AbleLocation.CLICKED_TEXT_COLOR_CODE) {
            ableLocation.textColorCode = AbleLocation.NORMAL_TEXT_COLOR_CODE;
            count--;
            able_loc.remove(ableLocation.location);
        }


        ableLocation_list.set(position, ableLocation);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.able_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_done_able_location) {
            if (from.equals("condition")) {
                Intent intent = new Intent(getApplicationContext(), ConditionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putStringArrayListExtra("able_loc", able_loc);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putStringArrayListExtra("able_loc", able_loc);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("가능 지역");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}
