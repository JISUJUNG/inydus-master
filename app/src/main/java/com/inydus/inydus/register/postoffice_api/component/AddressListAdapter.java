package com.inydus.inydus.register.postoffice_api.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.register.postoffice_api.model.AddressItem;

import java.util.ArrayList;

public class AddressListAdapter extends BaseAdapter{
    private ArrayList<AddressItem> source = null;
    private LayoutInflater layoutInflater = null;
    private Context ctx;

    public AddressListAdapter(Context context) {
        this.ctx = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSource(ArrayList<AddressItem> source){
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

        AddressViewHolder addressViewHolder = new AddressViewHolder();

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.item_post, parent, false);

            addressViewHolder.txtDoro = (TextView)convertView.findViewById(R.id.txtDoro);
            addressViewHolder.txtJibun = (TextView)convertView.findViewById(R.id.txtJibun);
            addressViewHolder.txtZipNum = (TextView)convertView.findViewById(R.id.txtAddressNum);

            convertView.setTag(addressViewHolder);
        }

        else {
            addressViewHolder = (AddressViewHolder)convertView.getTag();
        }

        AddressItem addressItem = source.get(position);

        addressViewHolder.txtDoro.setText(addressItem.InmAdres);
        addressViewHolder.txtJibun.setText(addressItem.rnAdres);
        addressViewHolder.txtZipNum.setText(String.valueOf(addressItem.zipNo));

        return convertView;
    }

    public static class AddressViewHolder {
        TextView txtDoro;
        TextView txtJibun;
        TextView txtZipNum;
    }
}
