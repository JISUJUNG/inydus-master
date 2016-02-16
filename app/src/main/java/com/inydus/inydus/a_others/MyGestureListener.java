package com.inydus.inydus.a_others;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

    RecyclerView recyclerView;
    RecyclerViewController controller;

    public MyGestureListener(RecyclerView recyclerView, RecyclerViewController controller) {
        this.recyclerView = recyclerView;
        this.controller = controller;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        controller.onRecyclerViewItemClick(view);
        return super.onSingleTapConfirmed(e);
    }
}
