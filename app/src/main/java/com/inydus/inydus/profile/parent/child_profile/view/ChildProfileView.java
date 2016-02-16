package com.inydus.inydus.profile.parent.child_profile.view;

import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;

public interface ChildProfileView {
    void setChildProfileImage();
    void setChildProfileTexts(ChildProfile childProfile);
    void networkError();
}
