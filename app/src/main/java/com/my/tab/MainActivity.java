package com.my.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity {
    PagerSlidingTabStrip pst;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles = {"文本来源", "背景图片", "敬请期待","敬请期待","生成图片"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pst = (PagerSlidingTabStrip) findViewById(R.id.pst);
        viewPager = (ViewPager) findViewById(R.id.pager);
        fragments = new ArrayList<>();
        MyFragment myFragment = new MyFragment();
        MyFragment2 myFragment2 = new MyFragment2();
        MyFragment3 myFragment3 = new MyFragment3();
        MyFragment4 myFragment4 = new MyFragment4();
        MyFragment5 myFragment5 = new MyFragment5();
        fragments.add(myFragment);
        fragments.add(myFragment2);
        fragments.add(myFragment3);
        fragments.add(myFragment4);
        fragments.add(myFragment5);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapter(fragmentManager, titles, fragments));
        viewPager.setCurrentItem(0);
        //当ViewPager的onPagerChangeListener回调时，PagerSlidingTabStrip也一起随之变动
        //具体做法都已封装到了PagerSlidingTabStrip.setViewPager()方法里，使用时调用实例如下
        setPagerSliding(pst);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTextStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void updateTextStyle(int position) {
        LinearLayout tabsContainer = (LinearLayout) pst.getChildAt(0);
        for(int i=0; i< tabsContainer.getChildCount(); i++) {
            TextView textView = (TextView) tabsContainer.getChildAt(i);
            if(position == i) {
                textView.setTextSize(18);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                textView.setTextSize(12);
                textView.setTextColor(getResources().getColor(R.color.yellow));
            }
        }
    }


    public void setPagerSliding(PagerSlidingTabStrip p) {
        p.setViewPager(viewPager);
        p.setTextSize(50);
        p.setDividerWidth(5);
        p.setDividerPadding(20);
        p.setDividerColor(R.color.yellow);
        p.setTextColor(R.color.tabcolor);

    }





    //适配器
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] titles;
        ArrayList<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}