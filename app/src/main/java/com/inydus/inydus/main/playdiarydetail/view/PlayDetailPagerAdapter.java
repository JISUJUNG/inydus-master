package com.inydus.inydus.main.playdiarydetail.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.inydus.inydus.R;

import java.util.ArrayList;

public class PlayDetailPagerAdapter extends PagerAdapter {

    private ArrayList<String> playImageList;
    private Context ctx;
    private LayoutInflater inflater;

    public PlayDetailPagerAdapter(Context ctx) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public void setPlayImageList(ArrayList<String> playImageList) {
        this.playImageList = playImageList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playImageList != null ? playImageList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.fragment_play_diary_detail, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_play_diary_detail);
        Glide.with(ctx)
                .load(playImageList.get(position))
                .centerCrop()
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}