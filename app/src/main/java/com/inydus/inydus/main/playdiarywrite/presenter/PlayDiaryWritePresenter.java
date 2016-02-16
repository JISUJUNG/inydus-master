package com.inydus.inydus.main.playdiarywrite.presenter;

import com.inydus.inydus.main.mypage.model.Playdiary;
import com.squareup.okhttp.RequestBody;

/**
 * Created by JUNGJISU on 2016. 2. 9..
 */
public interface PlayDiaryWritePresenter {
    void sendPlayDiary(Playdiary playdiary, RequestBody requestBody1, RequestBody requestBody2, RequestBody requestBody3);
}
