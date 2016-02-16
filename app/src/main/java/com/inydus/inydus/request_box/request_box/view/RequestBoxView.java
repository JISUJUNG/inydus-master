package com.inydus.inydus.request_box.request_box.view;

import com.inydus.inydus.request_box.request_box.model.MyRequest;

import java.util.ArrayList;

public interface RequestBoxView {
    void setRequestLists(ArrayList<MyRequest> requestLists);
    void networkError();
}
