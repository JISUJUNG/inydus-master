package com.inydus.inydus.profile.sitter.edit_profile.view;

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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.DayController;
import com.inydus.inydus.a_others.DynamicDayTextView;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.login.model.User;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.sitter.edit_profile.presenter.EditProfilePresenter;
import com.inydus.inydus.profile.sitter.edit_profile.presenter.EditProfilePresenterImpl;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.inydus.inydus.register.able_location.AbleLocationActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EditProfileActivity extends AppCompatActivity implements EditTimeDialogFragment.EditTimeDialogListener, EditProfileView {

    private int REQUEST_LOCATION;
    private static final int YOUR_SELECT_DOCUMENT_REQUEST_CODE = 232;
    private static final int YOUR_SELECT_PROFILE_REQUEST_CODE = 234;
    private static final int YOUR_SELECT_COVER_REQUEST_CODE = 235;

    private static final int CROP_FROM_CAMERA = 2;
    private Boolean isSucceed = false;
    private Boolean nameValid = false;

    @Bind(R.id.toolbar_profileEdit)
    Toolbar toolbar;
    @Bind(R.id.editName_edit_profile)
    EditText editName;
    @Bind(R.id.datePicker_edit_profile)
    DatePicker datePicker;
    @Bind(R.id.editUniv_profile_edit)
    EditText editUniv;
    @Bind(R.id.editInfo_profile_edit)
    EditText editInfo;
    @Bind(R.id.editMajor_profile_edit)
    EditText editMajor;
    @Bind(R.id.layout_ableTime_editProfile)
    LinearLayout layoutAbleTime;
    @Bind(R.id.image_edit_profile)
    ImageView image_edit_profile;
    @Bind(R.id.img_edit_sitter_profile_cover)
    ImageView img_edit_sitter_cover;
    @Bind(R.id.txtDoc_edit_profile)
    TextView txtDoc;
    @Bind(R.id.radioGender_profile_edit)
    RadioGroup radioGroup;
    @Bind(R.id.radioMale_edit_profile)
    RadioButton radioMale;
    @Bind(R.id.radioFemale_edit_profile)
    RadioButton radioFemale;
    @Bind(R.id.layout_location_eidt_profile)
    LinearLayout layout_location;

    ArrayList<String> arrayList;
    ArrayList<AbleTime> ableTimes;
    ArrayList<AbleTime> desiredTimeList;
    String pay = "", time = "";
    ArrayAdapter adapter;
    String user_id;
    String baseUrl;
    private Uri outputFileUri;
    EditProfilePresenter presenter;
    RequestBody image_profile, image_cover, document;
    ApplicationController api = ApplicationController.getInstance();
    int value;
    ArrayList<String> able_loc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        presenter = new EditProfilePresenterImpl(this);

        initValues();
        initToolbar();
    }

    private void initValues() {

        desiredTimeList = new ArrayList<>();
        ableTimes = new ArrayList<>();
        arrayList = new ArrayList<>();
        for (int i = 8; i <= 20; i++) {
            arrayList.add(Integer.toString(i * 1000));
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        for (int i = 0; i < 7; i++) {
            desiredTimeList.add(i, null);
        }

        ArrayList<AbleTime> ableTimes = (ArrayList<AbleTime>) api.getSitterProfile().able_times;
        ableTimes = (ArrayList<AbleTime>) ableTimes.clone();
        for (int i = 0; i < DayController.TXT_DAY.length; i++) {
            AbleTime ableTime;
            if (ableTimes.size() == 0) {
                break;
            } else {
                ableTime = ableTimes.get(0);
            }
            if (DayController.TXT_DAY[i].equals(ableTime.sittertime_day)) {
                desiredTimeList.add(i, ableTime);
                ableTimes.remove(0);
            } else {
                desiredTimeList.add(i, null);
            }
        }

        value = ApplicationController.getInstance().getPixelFromDP(100);
        Intent intent = getIntent();
        if (intent.getStringExtra("from").equals("intro")) {
            User user = new User();
            user.user_id = intent.getStringExtra("id");
            api.setLoginUser(user);

            Glide.with(getApplicationContext())
                    .load(R.mipmap.pic_profile)
                    .override(value, value)
                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                    .into(image_edit_profile);

        } else {
            setContents();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        able_loc = intent.getStringArrayListExtra("able_loc");

        setLocationTextViews(able_loc);
    }


    private void setLocationTextViews(ArrayList<String> able_loc) {

        layout_location.removeAllViews();

        for (int i = 0; i < able_loc.size(); i++) {
            TextView textView = new TextView(EditProfileActivity.this);
            textView.setText(able_loc.get(i));
            textView.setBackgroundResource(R.drawable.basic_border);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int value = ApplicationController.getInstance().getPixelFromDP(5);
            layoutParams.leftMargin = value;
            layout_location.addView(textView, layoutParams);
        }
    }

    private void checkContents() {

        String name = editName.getText().toString();
        String univ = editUniv.getText().toString();
        String dept = editMajor.getText().toString();
        String info = editInfo.getText().toString();
        String doc = txtDoc.getText().toString();

        if (TextUtils.isEmpty(name)) editName.setError(getString(R.string.error_field_required));
        else {
            if (!isNameValid(name)) editName.setError(getString(R.string.error_invalid_name));
            else nameValid = true;
        }

        if (!nameValid)
            Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(univ)) {
            Toast.makeText(getApplicationContext(), "학교를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(dept)) {
            Toast.makeText(getApplicationContext(), "학과를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(info)) {
            Toast.makeText(getApplicationContext(), "자기소개를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(doc)) {
            Toast.makeText(getApplicationContext(), "인증서를 첨부해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (TextUtils.isEmpty(time)) {
            Toast.makeText(getApplicationContext(), "가능 시간을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else if (image_edit_profile.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "프로필 사진을 첨부해 주세요.", Toast.LENGTH_SHORT).show();
            isSucceed = false;
        } else isSucceed = true;
    }

    @OnClick(R.id.image_edit_profile)
    public void setImage_edit_profile() {
        REQUEST_LOCATION = YOUR_SELECT_PROFILE_REQUEST_CODE;
        openImageIntent();
    }

    @OnClick(R.id.img_edit_sitter_profile_cover)
    public void setImage_edit_cover_profile() {
        REQUEST_LOCATION = YOUR_SELECT_COVER_REQUEST_CODE;
        openImageIntent();
    }

    @OnClick(R.id.btn_doc_add)
    public void setDoc_edit_profile() {
        REQUEST_LOCATION = YOUR_SELECT_DOCUMENT_REQUEST_CODE;
        openImageIntent();
    }

    @OnClick(R.id.btn_loc_add)
    public void setLoc_edit_profile() {
        Intent i = new Intent(EditProfileActivity.this, AbleLocationActivity.class);
        i.putExtra("from", "editprofile");
        startActivity(i);
    }

    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("프로필 수정");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setContents() {

        SitterProfile sitterProfile = ApplicationController.getInstance().getSitterProfile();

        editName.setText(api.getLoginUser().user_name);
        editUniv.setText(sitterProfile.sitter_univ);
        editMajor.setText(sitterProfile.sitter_dept);
        editInfo.setText(sitterProfile.sitter_info);

        if (sitterProfile.sitter_gender == ChildProfile.MALE) {
            radioMale.setChecked(true);
        } else {
            radioFemale.setChecked(true);
        }

        String loc = sitterProfile.sitter_able_loc;
        String[] locs = loc.split("_");
        Collections.addAll(able_loc, locs);
        setLocationTextViews(able_loc);


        String birth = sitterProfile.sitter_birth;
        String[] births = birth.split("_");
        datePicker.init(Integer.parseInt(births[0]), Integer.parseInt(births[1]) - 1, Integer.parseInt(births[2]), null);

        makeDesiredTextView(desiredTimeList);

        baseUrl = api.getBaseUrl();
        user_id = api.getLoginUser().user_id;

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/profile", user_id)))
                .placeholder(R.mipmap.pic_profile)
                .override(value, value)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(image_edit_profile);

        Glide.with(getApplicationContext())
                .load(baseUrl.concat(String.format("/sit_profile/%s/cover", user_id)))
                .placeholder(R.mipmap.pic_profile)
                .into(img_edit_sitter_cover);

    }

    @OnClick(R.id.btn_time_add)
    public void setBtnTimeAdd() {
        EditTimeDialogFragment dialogFragment = new EditTimeDialogFragment();
        dialogFragment.show(getFragmentManager(), "edit_time");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_commit) {
            //checkContents();
            //if (isSucceed && nameValid)
            sendData();

        }

        return super.onOptionsItemSelected(item);
    }

    private void sendData() {

        String univ = editUniv.getText().toString();
        String major = editMajor.getText().toString();
        String info = editInfo.getText().toString();
        String able_location = getAbleLocationText();
        String birth = setDate();

        int age = calculateAge();
        int gender = getGender();
        ableTimes = getAbleTimeText();

        image_profile = makeImg(image_edit_profile, "profile.jpg");
        image_cover = makeImg(img_edit_sitter_cover, "cover.jpg");

        SitterProfile profile = new SitterProfile(user_id, gender, age, birth, major, info, pay, univ, able_location, ableTimes);
        presenter.sendProfileData(profile, image_profile, document, image_cover);

    }

    private ArrayList<AbleTime> getAbleTimeText() {

        ArrayList<AbleTime> ableTimes = new ArrayList<>();

        for (int i = 0; i < desiredTimeList.size(); i++) {
            if (desiredTimeList.get(i) == null) {
                continue;
            }
            AbleTime ableTime_temp = desiredTimeList.get(i);
            ableTimes.add(ableTime_temp);
        }

        return ableTimes;
    }

    private int getGender() {

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioMale_edit_profile:
                return ChildProfile.MALE;
            case R.id.radioFemale_edit_profile:
                return ChildProfile.FEMALE;
            default:
                return ChildProfile.MALE;
        }
    }

    private String getAbleLocationText() {

        String ableLoc = "";

        for (int i = 0; i < able_loc.size(); i++) {
            ableLoc += "_" + able_loc.get(i);
        }

        return ableLoc.substring(1);
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


    private File createTemporaryFile(String name) throws IOException {

        File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/");

        Log.i("MyTag", "" + Environment.getExternalStorageDirectory().getAbsolutePath());

        if (!temp.exists()) {
            temp.mkdir();
        }
        File file = new File(temp, name);

        Log.i("MyTag", "249 : 파일 크기 테스트 : " + file.length());
        return file;

    }

    private RequestBody makeImg(ImageView imageView,  String filename) {

        try {
            Drawable drawable = imageView.getDrawable();
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

            if (bitmap != null) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] bytes = stream.toByteArray();
                final File file = createTemporaryFile(filename);

                FileOutputStream writer = new FileOutputStream(file);

                writer.write(bytes);

                stream.close();
                writer.close();

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                return requestBody;

            }
        } catch (IOException e) {
            Log.i("MyTag", "실패 - catch");
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public void editProfileSucceed() {
        Toast.makeText(getApplicationContext(), "프로필 전송", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFinishEditTimeDialog(ArrayList<AbleTime> ableTimeList) {

        for (int i = 0; i < ableTimeList.size(); i++) {
            if (ableTimeList.get(i) == null) {
                continue;
            }
            AbleTime ableTime_temp = ableTimeList.get(i);
            desiredTimeList.set(i, ableTime_temp);
        }

        makeDesiredTextView(desiredTimeList);
    }

    private void makeDesiredTextView(final ArrayList<AbleTime> ableTimeList) {

        layoutAbleTime.removeAllViews();

        for (int i = 0; i < ableTimeList.size(); i++) {

            final AbleTime ableTime = ableTimeList.get(i);
            if (ableTime == null) {
                continue;
            }

            DynamicDayTextView dynamicDayTextView = new DynamicDayTextView(EditProfileActivity.this, ableTime);
            TextView txtDayItem = dynamicDayTextView.buildDynamicDayTextView();
            txtDayItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < DayController.TXT_DAY.length; i++) {
                        if (v.getTag().equals(DayController.TXT_DAY[i])) {
                            desiredTimeList.set(i, null);
                        }
                    }
                    layoutAbleTime.removeView(v);
                }
            });
            layoutAbleTime.addView(txtDayItem);
        }
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    private void openImageIntent() {

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "NOLDAM" + File.separator);
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

        if (REQUEST_LOCATION == YOUR_SELECT_PROFILE_REQUEST_CODE) {
            startActivityForResult(chooserIntent, YOUR_SELECT_PROFILE_REQUEST_CODE);
        } else if (REQUEST_LOCATION == YOUR_SELECT_DOCUMENT_REQUEST_CODE) {
            startActivityForResult(chooserIntent, YOUR_SELECT_DOCUMENT_REQUEST_CODE);
        } else if (REQUEST_LOCATION == YOUR_SELECT_COVER_REQUEST_CODE) {
            startActivityForResult(chooserIntent, YOUR_SELECT_COVER_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
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

            if (requestCode == YOUR_SELECT_PROFILE_REQUEST_CODE) {

                if (isCamera) selectedImageUri = outputFileUri;
                else selectedImageUri = data == null ? null : data.getData();

                Glide.with(getApplicationContext())
                        .load(selectedImageUri)
                        .override(value, value)
                        .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                        .into(image_edit_profile);


            } else if (requestCode == YOUR_SELECT_DOCUMENT_REQUEST_CODE) {

                if (isCamera) selectedImageUri = outputFileUri;
                else selectedImageUri = data == null ? null : data.getData();

                txtDoc.setText(String.valueOf(selectedImageUri.getPath()));

                if (selectedImageUri != null) {

                    try {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                        byte[] bytes = byteArray.toByteArray();

                        final File file = createTemporaryFile("doc.jpg");

                        FileOutputStream writer = new FileOutputStream(file);

                        writer.write(bytes);

                        byteArray.close();
                        writer.close();

                        document = RequestBody.create(MediaType.parse("image/jpg"), file);
                        Log.i("MyTag", "doc file 크기 : " + document.contentLength());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else if (requestCode == YOUR_SELECT_COVER_REQUEST_CODE) {

                if (isCamera) selectedImageUri = outputFileUri;
                else selectedImageUri = data == null ? null : data.getData();

                Glide.with(getApplicationContext())
                        .load(selectedImageUri)
                        .into(img_edit_sitter_cover);

            }
        }
    }


}

