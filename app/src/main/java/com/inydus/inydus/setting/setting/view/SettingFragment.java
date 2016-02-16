package com.inydus.inydus.setting.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.login.view.LoginActivity;
import com.inydus.inydus.profile.parent.child_profile.view.ChildProfileActivity;
import com.inydus.inydus.profile.sitter.profile.view.ProfileActivity;
import com.inydus.inydus.setting.matching_info.view.MatchingInfoActivity;
import com.inydus.inydus.setting.my_account.view.MyAccountActivity;
import com.inydus.inydus.setting.play_up.ParentPlayUpActivity;
import com.inydus.inydus.setting.play_up.PlayUpActivity;
import com.inydus.inydus.setting.setting.presenter.SettingPresenter;
import com.inydus.inydus.setting.setting.presenter.SettingPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingFragment extends Fragment implements SettingView {

    @Bind(R.id.txtId_setting)
    TextView txtId;
    SettingPresenter presenter;
    ApplicationController api;
    String type;

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        presenter = new SettingPresenterImpl(this);

        initValues();

        return view;
    }

    private void initValues() {
        api = ApplicationController.getInstance();
        type = api.getLoginUser().user_type;
        txtId.setText(api.getLoginUser().user_id);
    }

    @OnClick(R.id.layoutBtnLogout)
    public void setBtnLogout() {
        presenter.logoutFromServer();
    }

    @OnClick(R.id.layoutBtn_myProfile)
    public void setBtnMyProfile() {

        if (type.equals("Sitter")) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), ChildProfileActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layoutBtnMatchingInfo)
    public void setBtnMatchingInfo(){
        if (type.equals("Sitter")) {
            Intent intent = new Intent(getActivity(), MatchingInfoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), MatchingInfoActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layoutBtnMyAccount)
    public void setBtnMyAccount(){
        if (type.equals("Sitter")) {
            Intent intent = new Intent(getActivity(), MyAccountActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), MyAccountActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layoutBtnPlayUp)
    public void setBtnPlayUp(){
        if (type.equals("Sitter")) {
            Intent intent = new Intent(getActivity(), PlayUpActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), ParentPlayUpActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void logoutSucceed() {
        Toast.makeText(getActivity(), "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getActivity());
        errorController.notifyNetworkError();
    }
}
