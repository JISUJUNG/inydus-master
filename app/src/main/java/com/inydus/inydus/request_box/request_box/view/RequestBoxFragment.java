package com.inydus.inydus.request_box.request_box.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.request_box.request_box.component.RequestListAdapter;
import com.inydus.inydus.request_box.request_box.model.MyRequest;
import com.inydus.inydus.request_box.request_box.presenter.RequestBoxPresenter;
import com.inydus.inydus.request_box.request_box.presenter.RequestBoxPresenterImpl;
import com.inydus.inydus.request_box.request_detail.view.RequestDetailActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class RequestBoxFragment extends Fragment implements RequestBoxView {

    @Bind(R.id.listRequest_box)
    ListView listView;

    RequestListAdapter adapter;
    RequestBoxPresenter presenter;

    public RequestBoxFragment() {
    }

    public static RequestBoxFragment newInstance() {
        RequestBoxFragment fragment = new RequestBoxFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_box, container, false);
        ButterKnife.bind(this, view);
        presenter = new RequestBoxPresenterImpl(this);
        initAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.getRequestsFromServer();
    }

    @OnItemClick(R.id.listRequest_box)
    public void requestItemClick(int position){
        MyRequest myRequest = (MyRequest)adapter.getItem(position);
        Intent intent = new Intent(getActivity(), RequestDetailActivity.class);
        intent.putExtra("parent_id", myRequest.parent_id);
        intent.putExtra("sitter_id", myRequest.sitter_id);
        startActivity(intent);

    }

    @Override
    public void setRequestLists(ArrayList<MyRequest> requestLists) {
        adapter.setSource(requestLists);
    }

    private void initAdapter() {
        adapter = new RequestListAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getActivity());
        errorController.notifyNetworkError();
    }
}
