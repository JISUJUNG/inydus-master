package com.inydus.inydus.main.mypage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.mypage.component.PlayDiaryListAdapter;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.main.mypage.presenter.MyPagePresenter;
import com.inydus.inydus.main.mypage.presenter.MyPagePresenterImpl;
import com.inydus.inydus.main.playdiarydetail.view.PlayDiaryDetailActivity;
import com.inydus.inydus.main.playdiarywrite.view.PlayDiaryWriteActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyPageFragment extends Fragment implements MyPageView {

    @Bind(R.id.listview_myPage)
    ListView listview;
    @Bind(R.id.fab_myPage)
    FloatingActionButton fab;

    ApplicationController api;
    PlayDiaryListAdapter adapter;

    public MyPageFragment() {
    }

    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        api = ApplicationController.getInstance();

        initAdapter();
        setListView();
        setFab();

        return view;
    }

    private void initAdapter() {
        adapter = new PlayDiaryListAdapter(getActivity().getApplicationContext());
        listview.setAdapter(adapter);

    }

    private void setFab() {

        String type = api.getLoginUser().user_type;

        if(type.equals("Sitter")) fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PlayDiaryWriteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();

        MyPagePresenter presenter = new MyPagePresenterImpl(this);
        presenter.getPlayDiaryFromServer(api.getLoginUser().user_id);

    }

    private void setListView() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity().getApplicationContext(), PlayDiaryDetailActivity.class);
                intent.putExtra("play_no", String.valueOf(((Playdiary)adapter.getItem(position)).play_no));
                intent.putExtra("parent_id", ((Playdiary) adapter.getItem(position)).parent_id);
                intent.putExtra("child_name", ((Playdiary) adapter.getItem(position)).child_name);

                startActivity(intent);

            }
        });
    }

    @Override
    public void setPlayDiaryList(ArrayList<Playdiary> playdiaries) {
        adapter.setPlayDiary(playdiaries);
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getActivity().getApplicationContext());
        errorController.notifyNetworkError();
    }
}
