package com.inydus.inydus.main.mypage.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.model.PlayDiaryViewHolder;
import com.inydus.inydus.main.mypage.model.Playdiary;

import java.util.ArrayList;

public class PlayDiaryListAdapter extends BaseAdapter {

    private ArrayList<Playdiary> playdiaries = null;
    private LayoutInflater layoutInflater = null;


    public PlayDiaryListAdapter(Context ctx){
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPlayDiary(ArrayList<Playdiary> playdiaries){
        this.playdiaries = playdiaries;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playdiaries != null ? playdiaries.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (playdiaries != null && (position >= 0 && position < playdiaries.size()) ? playdiaries.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return (playdiaries != null && (position >= 0 && position < playdiaries.size()) ? position : 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlayDiaryViewHolder playDiaryViewHolder = new PlayDiaryViewHolder();

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_play_diary, parent, false);
            playDiaryViewHolder.img_myPage_list_viewholder = (ImageView)convertView.findViewById(R.id.img_item_play_diary);
            playDiaryViewHolder.txtTitle_myPage_list_viewholder = (TextView)convertView.findViewById(R.id.txtTitle_item_play_diary);
            playDiaryViewHolder.txtDate_myPage_list_viewholder = (TextView)convertView.findViewById(R.id.txtDate_item_play_diary);
            playDiaryViewHolder.txtName_myPage_list_viewholder = (TextView)convertView.findViewById(R.id.txtName_item_play_diary);
            convertView.setTag(playDiaryViewHolder);
        }
        else{
            playDiaryViewHolder = (PlayDiaryViewHolder)convertView.getTag();
        }

        Playdiary playdiary = playdiaries.get(position);

        String baseUrl = ApplicationController.getInstance().getBaseUrl();

        Glide.with(playDiaryViewHolder.img_myPage_list_viewholder.getContext())
                .load(baseUrl.concat(String.format("/play/%s/%s/photo1", ApplicationController.getInstance().getLoginUser().user_id, playdiary.parent_id)))
                .into(playDiaryViewHolder.img_myPage_list_viewholder);

        playDiaryViewHolder.txtTitle_myPage_list_viewholder.setText(playdiary.play_title);
        String[] date = playdiary.play_date.split("_");
        playDiaryViewHolder.txtDate_myPage_list_viewholder.setText(date[1] + "월" + " " + date[2] + "일");
        playDiaryViewHolder.txtName_myPage_list_viewholder.setText(playdiary.child_name);

        return convertView;
    }
}
