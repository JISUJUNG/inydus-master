package com.inydus.inydus.request_box.request_box.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.request_box.request_box.model.MyRequest;

import java.util.ArrayList;

public class RequestListAdapter extends BaseAdapter {

    private ArrayList<MyRequest> source = null;
    private LayoutInflater layoutInflater = null;
    ApplicationController api = ApplicationController.getInstance();

    public RequestListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSource(ArrayList<MyRequest> source) {
        this.source = source;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (source != null) ? source.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (source != null && (0 <= position && position < source.size())) ? source.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (source != null && (0 <= position && position < source.size())) ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RequestListViewHolder requestListViewHolder = new RequestListViewHolder();

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_request_box, parent, false);

            requestListViewHolder.imgGender = (ImageView) convertView.findViewById(R.id.imgGender_box);
            requestListViewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName_box);
            requestListViewHolder.txtAge = (TextView) convertView.findViewById(R.id.txtAge_box);
            requestListViewHolder.txtAdres = (TextView) convertView.findViewById(R.id.txtAdres_box);
            requestListViewHolder.txtState = (TextView) convertView.findViewById(R.id.txtState_box);

            convertView.setTag(requestListViewHolder);
        } else {
            requestListViewHolder = (RequestListViewHolder) convertView.getTag();
        }

        MyRequest myRequest = source.get(position);

        if (ApplicationController.getInstance().getLoginUser().user_type.equals("Sitter")) {
            if (myRequest.child_gender == MyRequest.MALE)
                requestListViewHolder.imgGender.setImageResource(R.drawable.btn_male);
            else
                requestListViewHolder.imgGender.setImageResource(R.drawable.btn_female);
            requestListViewHolder.txtAdres.setText(myRequest.child_adres);
            requestListViewHolder.txtName.setText(myRequest.child_name);
            requestListViewHolder.txtAge.setText(Integer.toString(myRequest.child_age) + "세");
        } else {
            if (myRequest.sitter_gender == MyRequest.MALE)
                requestListViewHolder.imgGender.setImageResource(R.drawable.btn_male);
            else
                requestListViewHolder.imgGender.setImageResource(R.drawable.btn_female);
            requestListViewHolder.txtAdres.setText(api.getChildProfile().child_adres + api.getChildProfile().child_adres_detail);
            requestListViewHolder.txtName.setText(myRequest.user_name);
            requestListViewHolder.txtAge.setText(Integer.toString(myRequest.sitter_age) + "세");
        }

        if (myRequest.pro_accept.equals("Accept"))
            requestListViewHolder.txtState.setText("수락");
        else if (myRequest.pro_accept.equals("Deny"))
            requestListViewHolder.txtState.setText("거절");
        else if (myRequest.pro_accept.equals("Wait"))
            requestListViewHolder.txtState.setText("대기");

        return convertView;
    }
}
