package com.inydus.inydus.a_others;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;

public class DynamicLocationTextView {

    Context ctx;
    String ableLocation;

    public DynamicLocationTextView(Context ctx, String ableLocation) {
        this.ctx = ctx;
        this.ableLocation = ableLocation;
    }

    public TextView buildDynamicLocationTextView() {


        TextView txtLocationItem = new TextView(ctx);
        txtLocationItem.setText(ableLocation);
        txtLocationItem.setBackgroundResource(R.drawable.basic_border);
        txtLocationItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int value = ApplicationController.getInstance().getPixelFromDP(5);
        layoutParams.leftMargin = value;
        txtLocationItem.setLayoutParams(layoutParams);

        return txtLocationItem;
    }
}
