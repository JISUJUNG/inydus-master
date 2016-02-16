package com.inydus.inydus.main.mypage.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inydus.inydus.R;
import com.inydus.inydus.main.mypage.component.MainPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    final static String MY_PAGE = "MyPage";
    final static String FIND_SITTER = "선생님찾기";
    final static String REQUEST_BOX = "신청함";
    final static String SETTING = "더보기";

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setTabHost();

    }

    private void setTabHost() {

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        setupTab(MY_PAGE);
        setupTab(FIND_SITTER);
        setupTab(REQUEST_BOX);
        setupTab(SETTING);

        final MainPagerAdapter adapter = new MainPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.getCustomView().setAlpha(1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha((float) 0.5);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupTab(final String tag) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_button, null);
        TextView txt_tab = (TextView) view.findViewById(R.id.txt_tab);
        ImageView img_tab = (ImageView) view.findViewById(R.id.img_tab);
        TabLayout.Tab tab = tabLayout.newTab();
        tabLayout.addTab(tab);
        txt_tab.setText(tag);
        if (tag.equals(MY_PAGE)) {
            img_tab.setImageResource(R.drawable.tab1);
            tab.setCustomView(view);
        } else if (tag.equals(FIND_SITTER)) {

            img_tab.setImageResource(R.drawable.tab2);
            tab.setCustomView(view);
            if(tab.getCustomView() != null){
                tab.getCustomView().setAlpha((float) 0.5);
            }

        } else if (tag.equals(REQUEST_BOX)) {

            img_tab.setImageResource(R.drawable.tab3);
            tab.setCustomView(view);
            if(tab.getCustomView() != null){
                tab.getCustomView().setAlpha((float) 0.5);
            }
        } else if (tag.equals(SETTING)) {

            img_tab.setImageResource(R.drawable.tab4);
            tab.setCustomView(view);
            if(tab.getCustomView() != null){
                tab.getCustomView().setAlpha((float) 0.5);
            }
        }
    }

  /*  private void animFab(final float scale) {

        ViewCompat.animate(fab)
//                .setInterpolator(AnimUtils.FAST_OUT_SLOW_IN_INTERPOLATOR) //사라지는 모양
                .setInterpolator(new FastOutLinearInInterpolator())
//                .setInterpolator(AnimUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
                .scaleX(scale)
                .scaleY(scale)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        if (scale == 1) fab.setVisibility(View.VISIBLE);
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (scale == 0) fab.setVisibility(View.GONE);
                    }
                })
                .setDuration(250)   //기간
                .withLayer()        //Software Type Hardware Type
                .start();

    }*/
}
