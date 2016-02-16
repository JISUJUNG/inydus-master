package com.inydus.inydus.request_box.request_detail.view;

import com.inydus.inydus.request_box.request_detail.model.MyRequestDetail;

public interface RequestDetailView {
    void setRequestDatas(MyRequestDetail requestData);
    void networkError();
}
