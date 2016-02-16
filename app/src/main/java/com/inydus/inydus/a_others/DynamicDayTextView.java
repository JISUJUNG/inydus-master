package com.inydus.inydus.a_others;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;

public class DynamicDayTextView {

    Context ctx;
    AbleTime ableTime;

    public DynamicDayTextView(Context ctx, AbleTime ableTime) {
        this.ctx = ctx;
        this.ableTime = ableTime;
    }

    public TextView buildDynamicDayTextView() {
        String able_times = "";
        TextView txtDayItem = new TextView(ctx);
        TimeController timeController = new TimeController();
        timeController.setTimes(ableTime);
        String day = ableTime.sittertime_day;
        able_times += day + " - " + timeController.mTimeFormat();

        txtDayItem.setTag(day);
        txtDayItem.setText(able_times);
        txtDayItem.setBackgroundResource(R.drawable.basic_border);
        txtDayItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int paddingValue = ApplicationController.getInstance().getPixelFromDP(5);
        txtDayItem.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        txtDayItem.setLayoutParams(layoutParams);

        return txtDayItem;
    }

    public TextView buildClickableTextView() {
        TextView txtDayItem = buildDynamicDayTextView();

        StateListDrawable states = new StateListDrawable();
        int selectedBgColor = Color.parseColor("#dddddd");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable bgColor = null;
            states.addState(new int[] {android.R.attr.state_pressed}, new ColorDrawable(selectedBgColor));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                bgColor = ctx.getDrawable(R.drawable.basic_border);
                states.addState(new int[] {android.R.attr.state_enabled}, bgColor);
            }
            txtDayItem.setBackground(states);
        }
        txtDayItem.setClickable(true);

        return txtDayItem;
    }
}
