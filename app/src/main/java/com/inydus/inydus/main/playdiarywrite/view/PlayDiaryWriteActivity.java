package com.inydus.inydus.main.playdiarywrite.view;

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
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.inydus.inydus.R;
import com.inydus.inydus.a_others.ErrorController;
import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.main.playdiarywrite.presenter.PlayDiaryWritePresenter;
import com.inydus.inydus.main.playdiarywrite.presenter.PlayDiaryWritePresenterImpl;
import com.inydus.inydus.register.able_location.AbleLocationActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayDiaryWriteActivity extends AppCompatActivity implements PlayDiaryWriteView {

    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 234;

    int PICTURE_REQUEST_LOC = 0;

    @Bind(R.id.toolbar_play_diary_write)
    Toolbar toolbar;
    @Bind(R.id.datePicker_play_diary_write)
    DatePicker datePicker;
    @Bind(R.id.btn_search_play_diary_write)
    Button btn_search;
    @Bind(R.id.txtComment_play_diary_write)
    EditText txtComment;
    @Bind(R.id.layout_keyword_play_diary_write)
    LinearLayout layout_keyword;

    @Bind(R.id.img1_play_diary_write)
    ImageView img1;
    @Bind(R.id.img2_play_diary_write)
    ImageView img2;
    @Bind(R.id.img3_play_diary_write)
    ImageView img3;

    ArrayList<String> keywords = new ArrayList<>();
    PlayDiaryWritePresenter presenter;
    RequestBody requestBody1, requestBody2, requestBody3;
    private Uri outputFileUri;
    Map<String, RequestBody> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_diary_write);

        ButterKnife.bind(this);
        initToolbar();

        presenter = new PlayDiaryWritePresenterImpl(this);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayDiaryWriteActivity.this, AbleLocationActivity.class);
                intent.putExtra("from", "playdiary");
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.img1_play_diary_write)
    public void setImg1() {
        PICTURE_REQUEST_LOC = 1;
        openImageIntent();

    }

    @OnClick(R.id.img2_play_diary_write)
    public void setImg2() {
        PICTURE_REQUEST_LOC = 2;
        openImageIntent();

    }

    @OnClick(R.id.img3_play_diary_write)
    public void setImg3() {
        PICTURE_REQUEST_LOC = 3;
        openImageIntent();
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("놀이 일기");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_commit) {
            sendData();

        }
        return super.onOptionsItemSelected(item);
    }

    private void sendData() {

        String sitter_id = ApplicationController.getInstance().getLoginUser().user_id;
        String comment = txtComment.getText().toString();
        String date = setDate();
        String keyword = "";
        for (int i = 0; i < keywords.size(); i++) {
            keyword += "_" + keywords.get(i);
        }
        if (keyword.startsWith("_")) {
            keyword = keyword.substring(1);
        }

        makeSendingImg(img1, requestBody1);
        makeSendingImg(img2, requestBody2);
        makeSendingImg(img3, requestBody3);

        //Playdiary playdiary = new Playdiary(sitter_id, parent_id, child_name, date, comment, keyword);
        //presenter.sendPlayDiary(playdiary, requestBody1, requestBody2, requestBody3);

    }

    private String setDate() {
        String year = String.valueOf(datePicker.getYear());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String day = String.valueOf(datePicker.getDayOfMonth());

        return year + "_" + month + "_" + day;
    }


    private void setKeywordTextViews(ArrayList<String> keywords) {

        for (int i = 0; i < keywords.size(); i++) {
            TextView textView = new TextView(PlayDiaryWriteActivity.this);
            textView.setText(keywords.get(i));
            textView.setBackgroundResource(R.drawable.basic_border);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int value = ApplicationController.getInstance().getPixelFromDP(5);
            layoutParams.leftMargin = value;
            layout_keyword.addView(textView, layoutParams);
        }
    }


    private void makeSendingImg(ImageView img, RequestBody requestBody) {

        try {
            Bitmap bitmap = null;
            Drawable drawable = img.getDrawable();

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
            final File file = createTemporaryFile("image.jpg");

            FileOutputStream writer = new FileOutputStream(file);

            writer.write(bytes);

            stream.close();
            writer.close();

            requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

            Log.i("MyTag", "157:" + file.getPath());

        } catch (IOException e) {
            Log.i("MyTag", "실패 - catch");
            e.printStackTrace();
        }
    }

    private File createTemporaryFile(String name) throws IOException {

        File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/");
        File file = new File(temp, name);

        return file;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        keywords = intent.getStringArrayListExtra("keywords");
        layout_keyword.removeAllViews();
        setKeywordTextViews(keywords);
    }

    @Override
    public void sendPlayDiarySucceed() {
        finish();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }

    //TODO: 날짜 제어하기
    public static boolean isAfterToday(int year, int month, int day)
    {
        Calendar today = Calendar.getInstance();
        Calendar myDate = Calendar.getInstance();

        myDate.set(year, month, day);

        if (myDate.before(today))
        {
            return false;
        }
        return true;
    }

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
            final boolean isCamera;
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
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

                if (isCamera)
                    selectedImageUri = outputFileUri;
                else
                    selectedImageUri = data == null ? null : data.getData();

                if (PICTURE_REQUEST_LOC == 1) {
                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .centerCrop()
                            .into(img1);
                } else if (PICTURE_REQUEST_LOC == 2) {
                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .centerCrop()
                            .into(img2);
                } else if (PICTURE_REQUEST_LOC == 3) {
                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .centerCrop()
                            .into(img3);
                }

            }
        }
    }
}
