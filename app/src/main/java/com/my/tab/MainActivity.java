package com.my.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PagerSlidingTabStrip pst;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles = {"文本", "模板", "参数","词表","词云"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.inflateMenu(R.menu.menu_main);
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
        setPagerSliding(pst);
        viewPager.setCurrentItem(0);
        //当ViewPager的onPagerChangeListener回调时，PagerSlidingTabStrip也一起随之变动
        //具体做法都已封装到了PagerSlidingTabStrip.setViewPager()方法里，使用时调用实例如下

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
                textView.setTextColor(getResources().getColor(R.color.toolbarcol));
            } else {
                textView.setTextSize(15);
                textView.setTextColor(getResources().getColor(R.color.gray1));
            }
        }
    }



    public void setPagerSliding(PagerSlidingTabStrip p) {
        p.setViewPager(viewPager);
        p.setTextSize(50);
        p.setDividerWidth(0);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}