package com.inydus.inydus.profile.parent.edit_child_profile.presenter;

import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.squareup.okhttp.RequestBody;

public interface EditChildProfilePresenter {

    void sendChildProfile(ChildProfile childProfile, RequestBody requestBody);


}
