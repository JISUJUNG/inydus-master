package com.inydus.inydus.findSitter.findsitter.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.MyGestureListener;
import com.inydus.inydus.a_others.MyRecyclerViewListener;
import com.inydus.inydus.a_others.RecyclerViewController;
import com.inydus.inydus.findSitter.condition.ConditionActivity;
import com.inydus.inydus.findSitter.findsitter.model.Sitter;
import com.inydus.inydus.findSitter.findsitter.presenter.FindSitterPresenter;
import com.inydus.inydus.findSitter.findsitter.presenter.FindSitterPresenterImpl;
import com.inydus.inydus.findSitter.findsitter.presenter.SitterListAdapter;
import com.inydus.inydus.findSitter.sitter_profile.view.SitterProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FindSitterFragment extends Fragment implements
        FindSitterView,
        RecyclerViewController,
        View.OnClickListener {

    TextView txtFindSitterItem;
    @Bind(R.id.toolbar_findSitter)
    Toolbar toolbar;
    @Bind(R.id.recyclerView_findSitter)
    RecyclerView recyclerView;

    private HashMap<String, String> conditions;
    private SitterListAdapter adapter;
    GestureDetectorCompat gestureDetector;
    MyRecyclerViewListener listener;

    public FindSitterFragment() {
    }

    public static FindSitterFragment newInstance() {
        FindSitterFragment fragment = new FindSitterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_sitter, container, false);
        ButterKnife.bind(this, view);

        adapter = new SitterListAdapter(getActivity().getApplicationContext());

        initToolbar();
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        gestureDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener(recyclerView, this));
        listener = new MyRecyclerViewListener(gestureDetector);
        recyclerView.addOnItemTouchListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        initCondition();
        FindSitterPresenter presenter = new FindSitterPresenterImpl(this, conditions);
        presenter.makeListView();
        //getActivity().invalidateOptionsMenu();
    }

    private void initCondition() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("condition", Context.MODE_PRIVATE);
        int gender = sharedPreferences.getInt("gender", 0);
        int day = sharedPreferences.getInt("day", 0);
        String location = sharedPreferences.getString("able_location", "");

        conditions = new HashMap<>();
        conditions.put("sitter_gender", String.valueOf(gender));
        conditions.put("sitter_day", String.valueOf(day));
        conditions.put("able_loc", location);

    }

    @Override
    public void setSitterList(ArrayList<Sitter> sitters) {
        adapter.setSitterList(sitters);
    }

    private void initToolbar() {
        if (toolbar != null) {
            txtFindSitterItem = (TextView)toolbar.findViewById(R.id.toolbar_findSitter_item);
            txtFindSitterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ConditionActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Sitter sitter = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), SitterProfileActivity.class);
        intent.putExtra("id", sitter.user_id);
        startActivity(intent);
    }

    @Override
    public void onRecyclerViewItemClick(View view) {
        onClick(view);
    }
}