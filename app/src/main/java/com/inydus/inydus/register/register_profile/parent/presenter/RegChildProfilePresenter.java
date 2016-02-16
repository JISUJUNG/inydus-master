package com.inydus.inydus.register.register_profile.parent.presenter;

import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.squareup.okhttp.RequestBody;

public interface RegChildProfilePresenter {

    void sendChildProfileToServer(ChildProfile childProfile, RequestBody requestBody);
}
