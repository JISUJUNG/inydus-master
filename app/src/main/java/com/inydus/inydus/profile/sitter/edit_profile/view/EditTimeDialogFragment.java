package com.inydus.inydus.profile.sitter.edit_profile.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DayController;
import com.inydus.inydus.a_others.TimeController;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTimeDialogFragment extends DialogFragment {

    private EditTimeDialogFragment instance;
    Boolean isValid = true;

    private int TIME_PICKER_INTERVAL = 30;
    NumberPicker minutePicker;
    NumberPicker hourPicker;
    List<String> displayedValues;

    TimeController timeController;

    int interval = 1;

    int s_hour, s_min, e_hour, e_min;

    @Bind(R.id.txtMon_time_dialog)
    ToggleButton btnMon;
    @Bind(R.id.txtTue_time_dialog)
    ToggleButton btnTue;
    @Bind(R.id.txtWed_time_dialog)
    ToggleButton btnWed;
    @Bind(R.id.txtThu_time_dialog)
    ToggleButton btnThu;
    @Bind(R.id.txtFri_time_dialog)
    ToggleButton btnFri;
    @Bind(R.id.txtSat_time_dialog)
    ToggleButton btnSat;
    @Bind(R.id.txtSun_time_dialog)
    ToggleButton btnSun;
    @Bind(R.id.btnStart_time_dialog)
    ToggleButton btnStart;
    @Bind(R.id.btnEnd_time_dialog)
    ToggleButton btnEnd;
    @Bind(R.id.timePicker_time_dialog)
    TimePicker timePicker;
    @Bind(R.id.txtTime_time_dialog)
    TextView txtTime;
    ArrayList<ToggleButton> btnDays = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        instance = this;

        timeController = new TimeController();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_edit_time_dialog, null);

        ButterKnife.bind(this, view);

        btnDays.add(btnMon);
        btnDays.add(btnTue);
        btnDays.add(btnWed);
        btnDays.add(btnThu);
        btnDays.add(btnFri);
        btnDays.add(btnSat);
        btnDays.add(btnSun);

        builder.setView(view)
                .setTitle("시간 추가하기")
                .setPositiveButton("확인", null);

        s_hour = 14;
        s_min = 0;
        e_hour = s_hour + interval;
        e_min = s_min;
        setTimePickerInterval(timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (btnStart.isChecked()) {
                    s_hour = hourOfDay;
                    if (minute == 1)
                        s_min = 30;
                    else
                        s_min = minute;
                    if (s_hour + interval > 23) {
                        e_hour = 23;
                    } else {
                        e_hour = s_hour + interval;
                    }

                    setTextView();

                } else {
                    e_hour = hourOfDay;
                    if (minute == 1)
                        e_min = 30;
                    else
                        e_min = minute;
                    interval = e_hour - s_hour;
                    if (interval < 0 || (interval == 0 && minute == 0)) {
                        isValid = false;
                        txtTime.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
                        setTextView();

                    } else {
                        isValid = true;
                        e_hour = hourOfDay;
                        if (minute == 1)
                            e_min = 30;
                        else
                            e_min = minute;
                        interval = e_hour - s_hour;
                        txtTime.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                        setTextView();

                    }
                }
            }
        });

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ArrayList<AbleTime> able_time_list = new ArrayList<>();

                        if (isValid) {
                            int s_time = timeController.combineHourAndMin(s_hour, s_min);
                            int e_time = timeController.combineHourAndMin(e_hour, e_min);

                            for (int i = 0; i < btnDays.size(); i++) {
                                if (btnDays.get(i).isChecked()) {
                                    String day = DayController.TXT_DAY[i];
                                    AbleTime ableTime = new AbleTime(day, s_time, e_time);
                                    able_time_list.add(i, ableTime);
                                } else {
                                    able_time_list.add(i, null);
                                }
                            }

                            EditTimeDialogListener activity = (EditTimeDialogListener) getActivity();
                            activity.onFinishEditTimeDialog(able_time_list);
                            alertDialog.dismiss();
                        } else {
                            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                            txtTime.startAnimation(shake);
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    private void setTextView() {
        int startTime = timeController.combineHourAndMin(s_hour, s_min);
        int endTime = timeController.combineHourAndMin(e_hour, e_min);
        AbleTime ableTime = new AbleTime(null, startTime, endTime);
        timeController.setTimes(ableTime);
        String time = timeController.mTimeFormat();
        txtTime.setText(time);
    }

    public EditTimeDialogFragment() {
    }

    public interface EditTimeDialogListener {
        void onFinishEditTimeDialog(ArrayList<AbleTime> ableTimes);
    }

    @OnClick(R.id.btnStart_time_dialog)
    public void setBtnStartTime() {
        btnStart.setChecked(true);
        btnEnd.setChecked(false);

        if (s_hour < 12) {
            ((NumberPicker) (((ViewGroup) timePicker.getChildAt(0)).getChildAt(0))).setValue(0);
        } else {
            ((NumberPicker) (((ViewGroup) timePicker.getChildAt(0)).getChildAt(0))).setValue(1);
        }
        hourPicker.setValue(s_hour);
        s_min = 0;
        minutePicker.setValue(s_min);
        setTextView();
    }

    @OnClick(R.id.btnEnd_time_dialog)
    public void setBtnEndTime() {
        btnStart.setChecked(false);
        btnEnd.setChecked(true);

        if (e_hour < 12) {
            ((NumberPicker) (((ViewGroup) timePicker.getChildAt(0)).getChildAt(0))).setValue(0);
        } else {
            ((NumberPicker) (((ViewGroup) timePicker.getChildAt(0)).getChildAt(0))).setValue(1);
        }
        hourPicker.setValue(e_hour);
        e_min = 0;
        minutePicker.setValue(e_min);
        setTextView();
    }

    @SuppressLint("NewApi")
    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");

            Field field = classForid.getField("minute");
            Field hourField = classForid.getField("hour");
            hourPicker = (NumberPicker) timePicker
                    .findViewById(hourField.getInt(null));
            minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));

            hourPicker.setMinValue(9);
            hourPicker.setMaxValue(21);
            hourPicker.setValue(14);
            hourPicker.setWrapSelectorWheel(false);
            displayedValues = new ArrayList<String>();
            for (int i = 9; i < 22; i++) {
                if (i > 12)
                    displayedValues.add(String.valueOf(i - 12));
                else
                    displayedValues.add(String.valueOf(i));
            }

            hourPicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));


            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(1);
            minutePicker.setValue(0);
            displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
