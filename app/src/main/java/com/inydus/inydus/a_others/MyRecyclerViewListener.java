package com.inydus.inydus.a_others;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public class MyRecyclerViewListener implements RecyclerView.OnItemTouchListener {

    GestureDetectorCompat gesture;

    public MyRecyclerViewListener(GestureDetectorCompat gesture) {
        this.gesture = gesture;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gesture.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
