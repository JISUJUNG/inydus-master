package com.inydus.inydus.profile.sitter.profile.view;

import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

import java.util.List;

public interface ProfileView {
    void setProfileImage();
    void setProfileTexts(SitterProfile sitterProfile);
    void setProfileTime(List<AbleTime> ableTimeList);
}
