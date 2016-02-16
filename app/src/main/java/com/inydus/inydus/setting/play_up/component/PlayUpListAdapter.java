package com.inydus.inydus.setting.play_up.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.setting.play_up.model.PlayUp;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PlayUpListAdapter extends RecyclerView.Adapter<PlayUpListAdapter.PlayUpListViewHolder> {

    private ArrayList<PlayUp> playUpList = null;
    private Context ctx;

    public PlayUpListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setPlayUpList(ArrayList<PlayUp> playUpList) {
        this.playUpList = playUpList;
        this.notifyDataSetChanged();
    }

    public PlayUp getItem(int position) {
        return (playUpList != null && (0 <= position && position < playUpList.size())) ? playUpList.get(position) : null;
    }

    @Override
    public PlayUpListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_play_up, parent, false);
        return new PlayUpListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayUpListViewHolder holder, int position) {
        PlayUp playUp = playUpList.get(position);
        String baseUrl = ApplicationController.getInstance().getBaseUrl();

        int value = ApplicationController.getInstance().getPixelFromDP(100);

        Glide.with(holder.imgProfile.getContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", playUp.user_id)))
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(ctx))
                .into(holder.imgProfile);


        if (playUp.playTime == playUp.totalPlayTime) {
            holder.imgGrayBackground.setVisibility(View.VISIBLE);
            holder.imgGrayBackground.setClickable(false);
        } else {
            holder.imgGrayBackground.setVisibility(View.GONE);
            holder.imgGrayBackground.setClickable(true);
        }

        holder.txtName.setText(playUp.user_name + " 놀이친구");

        int currentPlayTime = playUp.playTime;
        int totalPlayTime = playUp.totalPlayTime;
        String playTime = String.format("%d/%d", currentPlayTime, totalPlayTime);
        holder.txtPlayTime.setText(playTime);
    }

    @Override
    public int getItemCount() {
        return (playUpList != null) ? playUpList.size() : 0;
    }


    public static class PlayUpListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile;
        TextView txtName;
        TextView txtPlayTime;
        ImageView imgGrayBackground;

        public PlayUpListViewHolder(View itemView) {
            super(itemView);
            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile_playUp);
            txtName = (TextView) itemView.findViewById(R.id.txtName_playUp);
            txtPlayTime = (TextView) itemView.findViewById(R.id.txtPlayTime_playUp);
            imgGrayBackground = (ImageView) itemView.findViewById(R.id.grayBackground_playUp);
        }
    }
}
