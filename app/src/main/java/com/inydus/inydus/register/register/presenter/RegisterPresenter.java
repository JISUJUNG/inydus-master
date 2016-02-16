package com.inydus.inydus.register.register.presenter;

import com.inydus.inydus.register.register.model.RegisterUser;

public interface RegisterPresenter {

    void checkEmailDuplication(String email);
    void registerToServer(RegisterUser user);
}
