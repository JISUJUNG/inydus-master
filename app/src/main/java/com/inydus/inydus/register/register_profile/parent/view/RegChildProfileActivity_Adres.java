package com.inydus.inydus.register.register_profile.parent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;

import com.inydus.inydus.R;
import com.inydus.inydus.register.postoffice_api.view.PostDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegChildProfileActivity_Adres extends AppCompatActivity implements PostDialogFragment.PostDialogListener{

    @Bind(R.id.editAddress_regCP_adres)
    EditText editAddress;
    @Bind(R.id.editAddress_detail_regCP_adres)
    EditText editAddress_detail;

    Bundle childProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_child_profile_activity__adres);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        childProfile = intent.getBundleExtra("childProfile");
    }

    @OnClick(R.id.btn_next_regCP_adres)
    public void setBtnNext(){
        String address = editAddress.getText().toString();
        String address_detail = editAddress_detail.getText().toString();
        if(TextUtils.isEmpty(address))
            editAddress.setError(getString(R.string.error_field_required));
        else{
            if(TextUtils.isEmpty(address_detail))
                editAddress_detail.setError(getString(R.string.error_field_required));
            else{

                childProfile.putString("adres", address);
                childProfile.putString("adres_d", address_detail);
                Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Info.class);
                intent.putExtra("childProfile", childProfile);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        }
    }

    @OnClick(R.id.btn_previous_regCP_adres)
    public void setBtnPrevious(){
        previousIntentEvent();
    }

    @OnClick(R.id.btnAddress_regCP_adres)
    public void setBtnAddress() {
        PostDialogFragment postDialogFragment = new PostDialogFragment();
        postDialogFragment.show(getFragmentManager(), "post");
    }

    @Override
    public void onFinishPostDialog(String address) {
        editAddress.setText(address);
    }

    @Override
    public void onBackPressed() {
        previousIntentEvent();
    }

    private void previousIntentEvent(){
        Intent intent = new Intent(getApplicationContext(), RegChildProfileActivity_Birth.class);
        intent.putExtra("childProfile", childProfile);
        intent.putExtra("from", "adres");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("from").equals("birth")){
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else{
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        childProfile = intent.getBundleExtra("childProfile");
    }
}
