package com.inydus.inydus.profile.parent.edit_child_profile.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.bumptech.glide.signature.StringSignature;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.parent.child_profile.view.ChildProfileActivity;
import com.inydus.inydus.profile.parent.edit_child_profile.presenter.EditChildProfilePresenter;
import com.inydus.inydus.profile.parent.edit_child_profile.presenter.EditChildProfilePresenterImpl;
import com.inydus.inydus.register.postoffice_api.view.PostDialogFragment;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EditChildProfileActivity extends AppCompatActivity implements EditChildProfileView, PostDialogFragment.PostDialogListener {

    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 234;
    private Uri outputFileUri;


    private Boolean nameValid = false;
    private Boolean isSucceed = false;
    private final int MALE = 0;
    private final int FEMALE = 1;

    ApplicationController api;
    EditChildProfilePresenter presenter;

    @Bind(R.id.toolbar_edit_child_profile)
    Toolbar toolbar;

    @Bind(R.id.editName_child_profile)
    EditText editName;

    @Bind(R.id.datePicker_edit_child_profile)
    DatePicker datePicker;
    @Bind(R.id.editInfo_child_profile)
    EditText editInfo;
    @Bind(R.id.editAddress_basic_edit_child_profile)
    EditText editAdres_1;
    @Bind(R.id.editAddress_detail_edit_child_profile)
    EditText editAdres_2;

    @Bind(R.id.radioGender_edit_child_profile)
    RadioGroup radioGroup;
    @Bind(R.id.radioMale_edit_child_profile)
    RadioButton radioMale;
    @Bind(R.id.radioFemale_edit_child_profile)
    RadioButton radioFemale;


    ImageView imgChild;
    String user_id;
    RequestBody requestBody;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_profile);

        ButterKnife.bind(this);
        imgChild = (ImageView) findViewById(R.id.img_eidt_child_profile);

        presenter = new EditChildProfilePresenterImpl(this);
        api = ApplicationController.getInstance();

        initToolbar();
        setContents();

        imgChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();
            }
        });

    }


    @OnClick(R.id.txtBtn_search_adres_edit_child_profile)
    public void setBtnAddress() {
        PostDialogFragment postDialogFragment = new PostDialogFragment();
        postDialogFragment.show(getFragmentManager(), "post");

    }

    @Override
    public void onFinishPostDialog(String address) {
        editAdres_1.setText(address);
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("프로필 수정");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void checkContents() {

        String name = editName.getText().toString();
        String adres = editAdres_1.getText().toString();
        String adres_d = editAdres_2.getText().toString();
        String info = editInfo.getText().toString();

        if (TextUtils.isEmpty(name)) editName.setError(getString(R.string.error_field_required));
        else {
            if (!isNameValid(name)) editName.setError(getString(R.string.error_invalid_name));
            else nameValid = true;
        }

        if (!nameValid)
            Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(adres)) {
            Toast.makeText(getApplicationContext(), "기본주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(adres_d)) {
            Toast.makeText(getApplicationContext(), "상세주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(info)) {
            Toast.makeText(getApplicationContext(), "아이소개를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else isSucceed = true;
    }



    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }


    private void setContents() {

        ChildProfile childProfile = ApplicationController.getInstance().getChildProfile();
        editName.setText(childProfile.child_name);
        editInfo.setText(childProfile.child_info);

        if (childProfile.child_gender == ChildProfile.MALE) {
            radioMale.setChecked(true);
        } else {
            radioFemale.setChecked(true);
        }


        String adres = childProfile.child_adres;
        String adres_d = childProfile.child_adres_detail;
        editAdres_1.setText(adres);
        editAdres_2.setText(adres_d);

        String birth = childProfile.child_birth;
        String[] births = birth.split("_");
        datePicker.init(Integer.parseInt(births[0]), Integer.parseInt(births[1]) - 1, Integer.parseInt(births[2]), null);

        value = ApplicationController.getInstance().getPixelFromDP(100);

        String baseUrl = ApplicationController.getInstance().getBaseUrl();
        user_id = api.getLoginUser().user_id;

        Intent intent = getIntent();
        String signature = intent.getStringExtra("signature");

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/child/%s/profile", user_id)))
                .signature(new StringSignature(signature))
                .placeholder(R.mipmap.noldam_icon)
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(imgChild);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        checkContents();

        if (isSucceed && nameValid)
            sendData();

        return super.onOptionsItemSelected(item);

    }

    private void sendData() {

        String parent_id = api.getLoginUser().user_id;
        String name = editName.getText().toString();

        String birth = setDate();
        int age = calculateAge();
        String info = editInfo.getText().toString();
        String adres = editAdres_1.getText().toString();
        String adres_d = editAdres_2.getText().toString();

        int gender = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioMale_edit_child_profile:
                gender = MALE;
                break;
            case R.id.radioFemale_edit_child_profile:
                gender = FEMALE;
                break;
        }

        makeImg();

        ChildProfile childProfile = new ChildProfile(parent_id, name, age, gender, birth, adres, info, adres_d);

        presenter.sendChildProfile(childProfile, requestBody);
    }

    private int calculateAge() {
        int age;

        Calendar calendar = Calendar.getInstance();
        age = calendar.get(Calendar.YEAR) - datePicker.getYear() + 1;

        return age;
    }

    private String setDate() {
        String year = String.valueOf(datePicker.getYear());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String day = String.valueOf(datePicker.getDayOfMonth());

        return year + "_" + month + "_" + day;
    }


    private void makeImg() {

        user_id = ApplicationController.getInstance().getLoginUser().user_id;

        try {
            Drawable drawable = imgChild.getDrawable();
            Bitmap bitmap = null;

            if (drawable instanceof GlideBitmapDrawable) {
                bitmap = ((GlideBitmapDrawable) drawable.getCurrent()).getBitmap();
            } else if (drawable instanceof TransitionDrawable) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) drawable;
                int length = transitionDrawable.getNumberOfLayers();
                for (int i = 0; i < length; ++i) {
                    Drawable child = transitionDrawable.getDrawable(i);
                    if (child instanceof GlideBitmapDrawable) {
                        bitmap = ((GlideBitmapDrawable) child).getBitmap();
                        break;
                    } else if (child instanceof SquaringDrawable
                            && child.getCurrent() instanceof GlideBitmapDrawable) {
                        bitmap = ((GlideBitmapDrawable) child.getCurrent()).getBitmap();
                        break;
                    }
                }
            } else if (drawable instanceof SquaringDrawable) {
                bitmap = ((GlideBitmapDrawable) drawable.getCurrent()).getBitmap();
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] bytes = stream.toByteArray();
            final File file = createTemporaryFile(user_id);

            FileOutputStream writer = new FileOutputStream(file);

            writer.write(bytes);

            stream.close();
            writer.close();

            requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);


            Log.i("MyTag", "filename : " + file.getName());

        } catch (IOException e) {
            Log.i("MyTag", "실패 - catch");
            e.printStackTrace();
        }
    }

    private File createTemporaryFile(String name) throws IOException {

        File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/");

        if (!temp.exists()) {
            temp.mkdir();
        }

        File file = new File(temp, name);

        return file;
    }

    @Override
    public void editChildProfileSucceed() {
        String signature = UUID.randomUUID().toString();
        Toast.makeText(getApplicationContext(), "프로필 전송", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ChildProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("signature", signature);
        startActivity(intent);
    }


/*
    private void performCrop() {

        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(outputFileUri, "image*/
/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, CROP_FROM_CAMERA);
        }

        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
*/


    private void openImageIntent() {

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_" + System.nanoTime() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        //camera
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
                final boolean isCamera;

                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;

                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .override(value, value)
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(imgChild);


                } else {
                    selectedImageUri = data == null ? null : data.getData();

                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .override(value, value)
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(imgChild);
                }

            }
        }

    }

}