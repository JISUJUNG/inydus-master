package com.inydus.inydus.register.postoffice_api.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.register.postoffice_api.component.AddressListAdapter;
import com.inydus.inydus.register.postoffice_api.model.AddressItem;
import com.inydus.inydus.register.postoffice_api.model.AddressResponse;
import com.inydus.inydus.register.postoffice_api.presenter.PostDialogPresenter;
import com.inydus.inydus.register.postoffice_api.presenter.PostDialogPresenterImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class PostDialogFragment extends DialogFragment implements PostDialogView{

    private PostDialogFragment instance;
    private PostDialogPresenter presenter;
    AddressListAdapter adapter;

    @Bind(R.id.editAddress_post)
    EditText editAddress;
    @Bind(R.id.addressList_post)
    ListView listView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        instance = this;
        presenter = new PostDialogPresenterImpl(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.post_dialog, null);

        ButterKnife.bind(this, view);

        builder.setView(view)
                .setTitle("우편번호 검색");

        initAdapter();

        return builder.create();
    }

    public PostDialogFragment() {
    }

    public interface PostDialogListener {
        void onFinishPostDialog(String address);
    }

    @Override
    public void setAddressList(AddressResponse addressResponse) {
        if(addressResponse == null){
            Toast.makeText(getActivity(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter.setSource((ArrayList<AddressItem>) addressResponse.itemlist);
    }

    @OnItemClick(R.id.addressList_post)
    void setItemClickEvent(int position){
        AddressItem addressItem = (AddressItem) adapter.getItem(position);
        String address = addressItem.InmAdres;

        PostDialogListener activity = (PostDialogListener) getActivity();
        activity.onFinishPostDialog(address);
        instance.dismiss();
    }

    @OnClick(R.id.btnSearch_post)
    void setBtnSearch(){
        String query = editAddress.getText().toString();
        String serviceKey = getString(R.string.post_api_key);
        try {
            serviceKey = URLDecoder.decode(serviceKey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        presenter.searchAddress(query, serviceKey);
    }

    private void initAdapter() {
        adapter = new AddressListAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getActivity());
        errorController.notifyNetworkError();
    }
}
