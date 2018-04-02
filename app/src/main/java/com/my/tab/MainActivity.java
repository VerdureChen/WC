package com.my.tab;

import android.app.Dialog;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v7.appcompat.R.styleable.MenuItem;

public class MainActivity extends AppCompatActivity {

    static int flag=0;
    PagerSlidingTabStrip pst;
    static  ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles = {"文本", "模板", "参数","词表","词云"};
    static Toolbar toolbar;



    static Map<String, Integer> images = new HashMap<String, Integer>();
    static Dialog dialog1;
    static Dialog dialog2;
    static CallbackBundle callback = new CallbackBundle() {
        Bundle bundle = new Bundle();

        @Override
        public void callback(Bundle bundle) {
            //String filepath = bundle.getString("path");
            //setTitle(filepath); // 把文件路径显示在标题上

        }


    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        //setSupportActionBar(toolbar);
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












        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.drawable.filefolder);   // 根目录图标
        images.put(OpenFileDialog.sParent, R.drawable.filefolder);    //返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.drawable.filefolder);   //文件夹图标
        images.put("txt", R.drawable.txticon);   //txt文件图标
        images.put("jpg", R.drawable.jpegiocn);
        images.put(OpenFileDialog.sEmpty, R.drawable.empty);

        dialog1 = OpenFileDialog.createDialog(0, this, "选择图片文件", callback,
                ".bmp;.jpg;.png;.gif;",images);
        dialog2 = OpenFileDialog.createDialog(1, this, "选择WORD文件", callback, ".txt;.doc;.docx;", images);

        //MainActivity.imv = (ImageView)findViewById(R.id.imagefinal);


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