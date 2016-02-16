package com.inydus.inydus.profile.sitter.edit_profile.presenter;

import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.squareup.okhttp.RequestBody;

public interface EditProfilePresenter {

    void sendProfileData(SitterProfile profile, RequestBody profile_image, RequestBody document, RequestBody profile_cover);
}
