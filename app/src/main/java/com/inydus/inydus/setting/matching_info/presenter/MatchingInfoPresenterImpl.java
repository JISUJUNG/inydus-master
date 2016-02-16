package com.inydus.inydus.setting.matching_info.presenter;

import com.inydus.inydus.application.ApplicationController;
import com.inydus.inydus.setting.matching_info.view.MatchingInfoView;

public class MatchingInfoPresenterImpl implements MatchingInfoPresenter{

    MatchingInfoView view;
    ApplicationController api;

    public MatchingInfoPresenterImpl(MatchingInfoView view) {
        this.view = view;
        api = ApplicationController.getInstance();
    }

    @Override
    public void setMatchingInfoThumbnails() {
        view.makeThumbnails(null);
    }
}
