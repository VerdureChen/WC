package com.my.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity {
    PagerSlidingTabStrip pst;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles  = {"一","二","三"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pst=(PagerSlidingTabStrip)findViewById(R.id.pst);
        viewPager=(ViewPager)findViewById(R.id.pager);

        fragments=new ArrayList<>();
        MyFragment myFragment=new MyFragment();
        MyFragment2 myFragment2=new MyFragment2();
        MyFragment3 myFragment3=new MyFragment3();
        fragments.add(myFragment);
        fragments.add(myFragment2);
        fragments.add(myFragment3);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapter(fragmentManager,titles,fragments));
        viewPager.setCurrentItem(0);
        //当ViewPager的onPagerChangeListener回调时，PagerSlidingTabStrip也一起随之变动
        //具体做法都已封装到了PagerSlidingTabStrip.setViewPager()方法里，使用时调用实例如下
        pst.setViewPager(viewPager);
        pst.setTextSize(30);
    }

    //适配器
    public class MyPagerAdapter extends FragmentPagerAdapter{
        private String[] titles;
        ArrayList<Fragment> fragments;
        public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments){
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }
        @Override
        public CharSequence getPageTitle(int position){
            return titles[position];
        }
        @Override
        public Fragment getItem(int position){
            return fragments.get(position);
        }
        @Override
        public int getCount(){
            return fragments.size();
        }
    }
}
