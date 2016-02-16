package com.inydus.inydus.findSitter.sitter_profile.view;

import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

import java.util.List;

public interface SitterProfileView {
    void setProfileTextViews(SitterProfile sitterProfile);
    void setProfileImages(String id);
    void setProfileTime(List<AbleTime> ableTimeList);
    void sendIntent(String rank, List<AbleTime> ableTimeList);
    void networkError();
}   
