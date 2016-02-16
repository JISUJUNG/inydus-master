package com.inydus.inydus.findSitter.request.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DayController;
import com.inydus.inydus.a_others.DynamicDayTextView;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.a_others.TimeController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.request.model.Proposal;
import com.inydus.inydus.findSitter.request.presenter.RequestPresenter;
import com.inydus.inydus.findSitter.request.presenter.RequestPresenterImpl;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.profile.sitter.edit_profile.view.EditTimeDialogFragment;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.inydus.inydus.register.postoffice_api.view.PostDialogFragment;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RequestActivity extends AppCompatActivity implements
        RequestView,
        PostDialogFragment.PostDialogListener,
        EditTimeDialogFragment.EditTimeDialogListener {

    @Bind(R.id.editAddress_request)
    EditText editAddress;
    @Bind(R.id.editAddress_detail_request)
    EditText editAddress_detail;
    @Bind(R.id.txt_weekly_hour_request)
    TextView txtWeeklyHour;
    @Bind(R.id.txtPayDescription_request)
    TextView txtPayDescription;
    @Bind(R.id.txtTotalPay)
    TextView txtTotalPay;
    @Bind(R.id.layout_able_time_request)
    LinearLayout layoutAbleTime;
    @Bind(R.id.radioGroup_sibling_request)
    RadioGroup radioGroup_sibling;
    @Bind(R.id.radioGroup_desired_time_request)
    RadioGroup radioGroup_desired;

    String rank, option = "없음", id, day = "";
    private int optionPay = SitterProfile.SIBLING_PAY_0, pro_count = SitterProfile.DESIRED_TIME_10;
    int hour;
    ApplicationController api;
    RequestPresenter presenter;
    ArrayList<AbleTime> ableTimeList;
    ArrayList<AbleTime> desiredTimeList;
    View.OnClickListener txtDayClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        ButterKnife.bind(this);
        api = ApplicationController.getInstance();
        presenter = new RequestPresenterImpl(this);
        initValues();
        radioBtnEvent();
        calculatePay();

    }

    private void initValues() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        rank = intent.getStringExtra("rank");
        desiredTimeList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            desiredTimeList.add(i, null);
        }
        ableTimeList = (ArrayList<AbleTime>) intent.getSerializableExtra("ableTimeList");

        txtDayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desiredTimeList.remove(layoutAbleTime.indexOfChild(v));
                layoutAbleTime.removeView(v);
            }
        };
    }

    private void radioBtnEvent() {
        radioGroup_desired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn_10_time:
                        pro_count = SitterProfile.DESIRED_TIME_10;
                        break;
                    case R.id.radioBtn_20_time:
                        pro_count = SitterProfile.DESIRED_TIME_20;
                        break;
                    case R.id.radioBtn_30_time:
                        pro_count = SitterProfile.DESIRED_TIME_30;
                        break;

                }

                calculatePay();
            }
        });

        radioGroup_sibling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_sibling0:
                        option = "없음";
                        optionPay = SitterProfile.SIBLING_PAY_0;
                        break;
                    case R.id.radio_sibling1:
                        option = "1명 추가";
                        optionPay = SitterProfile.SIBLING_PAY_1;
                        break;
                    case R.id.radio_sibling2:
                        option = "2명 추가";
                        optionPay = SitterProfile.SIBLING_PAY_2;
                        break;
                }

                calculatePay();
            }
        });
    }

    @OnClick(R.id.txtBtn_address_request)
    public void setBtnAddress() {
        PostDialogFragment postDialogFragment = new PostDialogFragment();
        postDialogFragment.show(getFragmentManager(), "post");
    }

    @OnClick(R.id.txtBtn_add_request)
    public void setBtnTimeAdd() {
        EditTimeDialogFragment dialogFragment = new EditTimeDialogFragment();
        dialogFragment.show(getFragmentManager(), "edit_time");
    }

    @Override
    public void onFinishEditTimeDialog(ArrayList<AbleTime> ableTimeList) {

        for (int i = 0; i < ableTimeList.size(); i++) {
            if (ableTimeList.get(i) == null) {
                continue;
            }
            AbleTime ableTime_temp = ableTimeList.get(i);
            desiredTimeList.set(i, ableTime_temp);
        }

        makeDesiredTextView(desiredTimeList);
        TimeController timeController = new TimeController();
        day = timeController.networkFormat(desiredTimeList);
    }

    private void makeDesiredTextView(final ArrayList<AbleTime> ableTimeList) {

        layoutAbleTime.removeAllViews();

        float weeklyHour = 0;

        for (int i = 0; i < ableTimeList.size(); i++) {

            final AbleTime ableTime = ableTimeList.get(i);
            if (ableTime == null) {
                continue;
            }

            TimeController timeController = new TimeController();
            weeklyHour += timeController.timeToFloat(ableTime);
            Log.i("MyTag", ""+weeklyHour);
            DynamicDayTextView dynamicDayTextView = new DynamicDayTextView(RequestActivity.this, ableTime);
            TextView txtDayItem = dynamicDayTextView.buildClickableTextView();
            txtDayItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i < DayController.TXT_DAY.length; i++){
                        if(v.getTag().equals(DayController.TXT_DAY[i])){
                            desiredTimeList.set(i, null);
                        }
                    }
                    layoutAbleTime.removeView(v);
                }
            });
            layoutAbleTime.addView(txtDayItem);
        }
        TimeController timeController = new TimeController();
        txtWeeklyHour.setText(timeController.getTotalTime(weeklyHour));

        hour = (int)weeklyHour;
    }

    @Override
    public void onFinishPostDialog(String address) {
        editAddress.setText(address);
    }

    @OnCheckedChanged(R.id.chk_same_address_request)
    public void setChkSameAddress(boolean isChecked) {
        if (isChecked) {
            String address = api.getChildProfile().child_adres;
            String address_detail = api.getChildProfile().child_adres_detail;
            editAddress.setText(address);
            editAddress_detail.setText(address_detail);
        } else {
            editAddress.setText("");
            editAddress_detail.setText("");
        }
    }

    @OnClick(R.id.btnRequest_req)
    public void request() {
        String user_id = ApplicationController.getInstance().getLoginUser().user_id;
        int pro_pay = setPayByRank(rank) + optionPay;
        String pro_day = day;
        int pro_hour = hour;
        String pro_adres = editAddress.getText().toString();
        int pro_sibling = getSiblingOption();
        String pro_accept = "Wait";
        Proposal proposal = new Proposal(user_id, id, pro_pay, pro_day, pro_hour, pro_adres, pro_sibling, pro_accept, pro_count);
        presenter.sendRequest(proposal);
    }

    private int getSiblingOption() {
        switch (radioGroup_sibling.getCheckedRadioButtonId()) {
            case R.id.radio_sibling1:
                return SitterProfile.SIBLING_1;
            case R.id.radio_sibling2:
                return SitterProfile.SIBLING_2;
            default:
                return SitterProfile.SIBLING_0;
        }
    }

    public void calculatePay() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        int basicPay = setPayByRank(rank);
        int hourPay = basicPay + optionPay;
        int playTime = pro_count;
        int totalPay = hourPay * playTime;

        String txtHourPay = String.format("시급 : %s원(%s등급) + %s원(형제 %s)",
                format.format(basicPay), rank, format.format(optionPay), option);
        String txtPlayTime = String.format("총 놀이시간 : %d시간", pro_count);
        String txtCalculate = String.format("시급 %s원 X 총 놀이시간 %d시간 = %s원",
                format.format(hourPay), playTime, format.format(totalPay));
        String txtResult = String.format("%s 원", format.format(totalPay));
        String txtTotal = txtHourPay + "\n" + txtPlayTime
                + "\n" + txtCalculate;
        txtPayDescription.setText(txtTotal);
        txtTotalPay.setText(txtResult);
    }

    private int setPayByRank(String rank) {

        switch (rank) {
            case SitterProfile.RANK_A:
                return SitterProfile.RANK_PAY_A;
            case SitterProfile.RANK_B:
                return SitterProfile.RANK_PAY_B;
            case SitterProfile.RANK_C:
                return SitterProfile.RANK_PAY_C;
            default:
                return SitterProfile.RANK_PAY_C;
        }

    }

    @Override
    public void requestSucceed() {
        Toast.makeText(getApplicationContext(), "신청완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDuplicated() {
        Toast.makeText(getApplicationContext(), "이미 신청하였습니다. \n신청함을 확인해주세요.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
