package com.inydus.inydus.request_box.request_detail.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.request_box.request_detail.model.MyRequestDetail;
import com.inydus.inydus.request_box.request_detail.presenter.RequestDetailPresenter;
import com.inydus.inydus.request_box.request_detail.presenter.RequestDetailPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RequestDetailActivity extends AppCompatActivity implements RequestDetailView {

    @Bind(R.id.toolbar_requestDetail)
    Toolbar toolbar;
    @Bind(R.id.txtName_requestDetail)
    TextView txtName;
    @Bind(R.id.txtAge_requestDetail)
    TextView txtAge;
    @Bind(R.id.txtInfo_requestDetail)
    TextView txtInfo;
    @Bind(R.id.txtToTalPay_requestDetail)
    TextView txtPay;
    @Bind(R.id.txtAddress_requestDetail)
    TextView txtAddress;
    @Bind(R.id.img_requestDetail)
    ImageView img_req;

    String parent_id, sitter_id;
    RequestDetailPresenter presenter;
    ApplicationController api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        ButterKnife.bind(this);
        api = ApplicationController.getInstance();

        Intent intent = getIntent();
        parent_id = intent.getStringExtra("parent_id");
        sitter_id = intent.getStringExtra("sitter_id");

        presenter = new RequestDetailPresenterImpl(this, parent_id, sitter_id);
        presenter.getRequestDataFromServer();

    }

    @OnClick(R.id.btnAccept_requestDetail)
    public void btnAccept() {
        new AlertDialog.Builder(RequestDetailActivity.this)
                .setMessage("정말로 수락하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.sendStateToServer("Accept");
                        finish();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    @OnClick(R.id.btnDeny_requestDetail)
    public void btnDeny() {
        new AlertDialog.Builder(RequestDetailActivity.this)
                .setMessage("정말로 거절하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.sendStateToServer("Deny");
                        finish();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    @Override
    public void setRequestDatas(MyRequestDetail requestData) {
        String baseUrl = api.getBaseUrl();
        int value = api.getPixelFromDP(100);

        if (api.getLoginUser().user_type.equals("Sitter")) {
            Glide.with(getApplicationContext())
                    .load(baseUrl.concat(String.format("/child/%s/profile", parent_id)))
                    .placeholder(R.mipmap.pic_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .override(value, value)
                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                    .into(img_req);

            txtName.setText(requestData.child_name);
            txtAge.setText(String.valueOf(requestData.child_age));
            txtInfo.setText(requestData.child_info);
            txtPay.setText(requestData.pro_pay);
            txtAddress.setText(requestData.pro_address);
        } else {

            Glide.with(getApplicationContext())
                    .load(baseUrl.concat(String.format("/sit_profile/%s/profile", sitter_id)))
                    .placeholder(R.mipmap.pic_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .override(value, value)
                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                    .into(img_req);

            txtName.setText(requestData.child_name);
            txtAge.setText(String.valueOf(requestData.child_age));
            txtInfo.setText(requestData.child_info);
            txtPay.setText(requestData.pro_pay);
            txtAddress.setText(requestData.sitter_able_loc);
        }
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
