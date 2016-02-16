package com.inydus.inydus.main.mypage.view;

import com.inydus.inydus.main.mypage.model.Playdiary;

import java.util.ArrayList;

public interface MyPageView {
    void setPlayDiaryList(ArrayList<Playdiary> playdiaries);
    void networkError();
}
