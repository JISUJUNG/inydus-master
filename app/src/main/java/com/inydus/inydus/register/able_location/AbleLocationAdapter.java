package com.inydus.inydus.register.able_location;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inydus.inydus.R;

import java.util.ArrayList;

public class AbleLocationAdapter extends BaseAdapter {

    private ArrayList<AbleLocation> ableLocations = null;
    private LayoutInflater layoutInflater = null;
    private Context ctx;

    public AbleLocationAdapter(Context ctx) {
        this.ctx = ctx;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAbleLocations(ArrayList<AbleLocation> ableLocations){
        this.ableLocations = ableLocations;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return (ableLocations != null) ? ableLocations.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (ableLocations != null && (0 <= position && position < ableLocations.size())) ? ableLocations.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (ableLocations != null && (0 <= position && position < ableLocations.size())) ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AbleLocationViewHolder ableLocationViewHolder = new AbleLocationViewHolder();

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.able_location_item, parent, false);
            ableLocationViewHolder.txtLocation = (TextView)convertView.findViewById(R.id.txt_location);

            convertView.setTag(ableLocationViewHolder);
        }

        else {
            ableLocationViewHolder = (AbleLocationViewHolder)convertView.getTag();
        }

        AbleLocation ableLocation = ableLocations.get(position);
        ableLocationViewHolder.txtLocation.setText(ableLocation.location);
        if(ableLocation.textColorCode == AbleLocation.NORMAL_TEXT_COLOR_CODE)
            ableLocationViewHolder.txtLocation.setTextColor(ContextCompat.getColor(ctx, R.color.blackColor));
        else if(ableLocation.textColorCode == AbleLocation.CLICKED_TEXT_COLOR_CODE)
            ableLocationViewHolder.txtLocation.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));

        return convertView;
    }

    public class AbleLocationViewHolder {
        TextView txtLocation;
    }
}
