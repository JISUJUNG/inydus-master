package com.inydus.inydus.findSitter.findsitter.presenter;

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
import com.inydus.inydus.findSitter.findsitter.model.Sitter;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SitterListAdapter extends RecyclerView.Adapter<SitterListAdapter.SitterListViewHolder> {

    private ArrayList<Sitter> sitterList = null;
    private Context ctx;

    public SitterListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setSitterList(ArrayList<Sitter> sitterList) {
        this.sitterList = sitterList;
        this.notifyDataSetChanged();
    }

    public Sitter getItem(int position) {
        return (sitterList != null && (0 <= position && position < sitterList.size())) ? sitterList.get(position) : null;
    }

    @Override
    public SitterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_sitter, parent, false);
        return new SitterListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SitterListViewHolder holder, int position) {
        Sitter sitter = sitterList.get(position);
        String baseUrl = ApplicationController.getInstance().getBaseUrl();

        int value = ApplicationController.getInstance().getPixelFromDP(100);

        Glide.with(holder.imgProfile.getContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", sitter.user_id)))
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(ctx))
                .into(holder.imgProfile);

        Glide.with(holder.imgProfile.getContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/cover", sitter.user_id)))
                .into(holder.imgCover);
        holder.txtName.setText(sitter.user_name);
        holder.txtComment.setText(sitter.sitter_comment);
    }

    @Override
    public int getItemCount() {
        return (sitterList != null) ? sitterList.size() : 0;
    }

    /*@Override
    public int getCount() {
        return (sitterList != null) ? sitterList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (sitterList != null && (0 <= position && position < sitterList.size())) ? sitterList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (sitterList != null && (0 <= position && position < sitterList.size())) ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SitterListViewHolder sitterListViewHolder = new SitterListViewHolder();

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.item_sitter, parent, false);

            sitterListViewHolder.imgProfile = (ImageView)convertView.findViewById(R.id.imgFindSitter_item);
            sitterListViewHolder.txtName = (TextView)convertView.findViewById(R.id.txtFindSitter_item);

            convertView.setTag(sitterListViewHolder);
        }

        else {
            sitterListViewHolder = (SitterListViewHolder)convertView.getTag();
        }

        Sitter sitter = sitterList.get(position);
        String baseUrl = ApplicationController.getInstance().getBaseUrl();

        Glide.with(convertView.getContext()).load(baseUrl.concat(String.format("/ssphoto/%s/image", sitter.user_id))).into(sitterListViewHolder.imgProfile);
        sitterListViewHolder.txtName.setText(sitter.user_id);

        return convertView;
    }*/

    public static class SitterListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile;
        ImageView imgCover;
        TextView txtName;
        TextView txtComment;

        public SitterListViewHolder(View itemView) {
            super(itemView);
            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile_sitterItem);
            imgCover = (ImageView) itemView.findViewById(R.id.imgCover_sitterItem);
            txtName = (TextView) itemView.findViewById(R.id.txtName_sitterItem);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment_sitterItem);
        }
    }
}
