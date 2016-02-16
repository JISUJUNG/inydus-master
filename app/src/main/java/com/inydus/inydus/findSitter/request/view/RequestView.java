package com.inydus.inydus.findSitter.request.view;

public interface RequestView {

    void requestSucceed();
    void networkError();
    void notifyDuplicated();
}
