package com.inydus.inydus.findSitter.condition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DayController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.register.able_location.AbleLocationActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConditionActivity extends AppCompatActivity {

    @Bind(R.id.radioGender_condition)
    RadioGroup radioGroup;
    @Bind(R.id.toolbar_condition)
    Toolbar toolbar;
    @Bind(R.id.layout_location_condition)
    LinearLayout layout_location;
    @Bind(R.id.layout_day_condition)
    LinearLayout layout_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        ButterKnife.bind(this);

        initToolbar();
        initCondition();
    }

    @OnClick(R.id.txtBtn_location_condition)
    public void setBtnLocation(){
        Intent intent = new Intent(getApplicationContext(), AbleLocationActivity.class);
        intent.putExtra("from", "condition");
        startActivity(intent);
    }

    private void initCondition() {
        SharedPreferences sharedPreferences = getSharedPreferences("condition", MODE_PRIVATE);
        switch (sharedPreferences.getInt("gender", ChildProfile.NO_MATTER)){
            case ChildProfile.MALE :
                radioGroup.check(R.id.radioMale_condition);
                break;
            case ChildProfile.FEMALE:
                radioGroup.check(R.id.radioFemale_condition);
                break;
            default:
                radioGroup.check(R.id.radioNoMatter_condition);
                break;
        }

        /*******************요일 계산*******************/
        int day = sharedPreferences.getInt("day", 0);
        int temp = day - DayController.SUN;
        if(temp >= 0){
            ((ToggleButton)layout_day.getChildAt(6)).setChecked(true);
            day = temp;
        }
        for(int i = 0; i < 6 ; i++){
            temp = day - DayController.DAY[i];
            if(temp >= 0){
                ((ToggleButton)layout_day.getChildAt(i)).setChecked(true);
                day = temp;
            }
        }
        /*******************요일 계산*******************/

        String location = sharedPreferences.getString("able_location","");
        String able_loc[] = location.split("_");
        ArrayList<String> able_loc_temp = new ArrayList<>();
        for(int i = 0; i < able_loc.length; i++){
            able_loc_temp.add(able_loc[i]);
        }
        setLocationTextViews(able_loc_temp);
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("조건 설정");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.condition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        else if(item.getItemId() == R.id.action_done_condition){
            submitCondition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ArrayList<String> able_loc = intent.getStringArrayListExtra("able_loc");
        layout_location.removeAllViews();

        setLocationTextViews(able_loc);


    }

    private void setLocationTextViews(ArrayList<String> able_loc) {

        for(int i = 0; i < able_loc.size(); i++){
            TextView textView = new TextView(ConditionActivity.this);
            textView.setText(able_loc.get(i));
            textView.setBackgroundResource(R.drawable.basic_border);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int value = ApplicationController.getInstance().getPixelFromDP(5);
            layoutParams.leftMargin = value;
            layout_location.addView(textView, layoutParams);
        }
    }

    public void submitCondition(){
        int gender;
        int day;
        String able_location;

        gender = getGender();
        day = getDay();
        able_location = getAbleLocation();

        SharedPreferences sharedPreferences = getSharedPreferences("condition", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gender", gender);
        editor.putInt("day", day);
        editor.putString("able_location", able_location);
        editor.commit();

        finish();
    }

    //성별
    private int getGender() {
        int gender;
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radioMale_condition:
                gender = ChildProfile.MALE;
                return gender;
            case R.id.radioFemale_condition:
                gender = ChildProfile.FEMALE;
                return gender;
            default:
                gender = ChildProfile.NO_MATTER;
                return gender;
        }
    }

    //가능 요일
    private int getDay() {
        int day = 0;

        for(int i = 0; i < layout_day.getChildCount(); i++){
            if(((ToggleButton)layout_day.getChildAt(i)).isChecked()){
                day += DayController.DAY[i];
            }
        }
        return day;
    }

    //가능 지역
    private String getAbleLocation() {
        String able_location = "";
        for(int i = 0; i < layout_location.getChildCount(); i++){
            String location = ((TextView) layout_location.getChildAt(i)).getText().toString();
            able_location += "_" + location;
        }

        if(able_location.startsWith("_")){
            able_location = able_location.substring(1);
        }

        return able_location;
    }
}
