package com.inydus.inydus.register.postoffice_api.view;

import com.inydus.inydus.register.postoffice_api.model.AddressResponse;

public interface PostDialogView {

    void setAddressList(AddressResponse addressResponse);
    void networkError();
}
