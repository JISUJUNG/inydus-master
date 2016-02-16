package com.inydus.inydus.main.mypage.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.inydus.inydus.findSitter.findsitter.view.FindSitterFragment;
import com.inydus.inydus.main.mypage.view.MyPageFragment;
import com.inydus.inydus.request_box.request_box.view.RequestBoxFragment;
import com.inydus.inydus.setting.setting.view.SettingFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MyPageFragment tab1 = new MyPageFragment();
                return tab1;
            case 1:
                FindSitterFragment tab2 = new FindSitterFragment();
                return tab2;
            case 2:
                RequestBoxFragment tab3 = new RequestBoxFragment();
                return tab3;
            case 3:
                SettingFragment tab4 = new SettingFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
