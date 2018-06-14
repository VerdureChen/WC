package com.my.tab;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.astuetz.PagerSlidingTabStrip;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.R.id.list;
import static android.support.v7.appcompat.R.styleable.MenuItem;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements EventListener {
    public static String background;
    public static String colorm;
    public static String Fonts;
    public static String doc;
    public static Bitmap bitmap;
    public static String text;
    public static int i;
    public static boolean [] flag = new boolean[3];
    public static boolean textflag,freflag;
    public static boolean soundflag;
    public static boolean login,register;
    public static String soundtext;
    public static String code ="";
    public static String message="";
    public static String username,password,wordlist;
    public static int number;



    PagerSlidingTabStrip pst;
    static  ViewPager viewPager;
    ArrayList<Fragment> fragments;
    String[] titles = {"用户","文本", "模板", "参数","词表","词云"};
    static Toolbar toolbar;
    static  AlertDialog dialog3;
    static  AlertDialog dialog4;
    static  AlertDialog dialog5;

    static Map<String, Integer> images = new HashMap<String, Integer>();
    static Dialog dialog1;
    static Dialog dialog2,dialog6;
    static CallbackBundle callback = new CallbackBundle() {
        Bundle bundle = new Bundle();

        @Override
        public void callback(Bundle bundle) {
            //String filepath = bundle.getString("path");
            //setTitle(filepath); // 把文件路径显示在标题上

        }


    };
    public static EventManager asr;

    public static boolean logTime = true;

    public static boolean enableOffline = false; // 测试离线命令词，需要改成true


    /**
     * 测试参数填在这里
     */


     void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

     void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordlist = "";

        initPermission();
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        //setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        pst = (PagerSlidingTabStrip) findViewById(R.id.pst);
        viewPager = (ViewPager) findViewById(R.id.pager);
        fragments = new ArrayList<>();
        MyFragment myFragment = new MyFragment();
        MyFragment0 myFragment0 = new MyFragment0();
        MyFragment2 myFragment2 = new MyFragment2();
        MyFragment3 myFragment3 = new MyFragment3();
        MyFragment4 myFragment4 = new MyFragment4();
        final MyFragment5 myFragment5 = new MyFragment5();
        fragments.add(myFragment0);
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
        flag[0]= false;
        flag[1]= false;
        flag[2]= false;
        new File("/storage/emulated/0/wordcloud/wordcloud.jpg").delete();
        final ProgressBar pro1=(ProgressBar)findViewById(R.id.proBar1);
        pro1.setVisibility(ProgressBar.GONE);
        View it=MainActivity.toolbar.findViewById(R.id.action_settings);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro1.setVisibility(ProgressBar.VISIBLE);
                Toast.makeText(getApplicationContext(), "请完善生成词云的文本或图片！", Toast.LENGTH_SHORT)
                        .show();
                File file5 ;

                file5 = new File("/storage/emulated/0/wordcloud/wordcloud.jpg");
                if(file5.exists())file5.delete();
                if(soundflag){//选择输入语音
                    if(OpenFileDialog.etn[0]){//选择本地图片
                        SET(flag[0],flag[1],flag[2]);
                        setlogin(login);
                        if(file5.exists())file5.delete();
                        setfre(freflag);
                        Log.i("json",OpenFileDialog.json);
                        POST.postAsynFile(OpenFileDialog.json);

                        while(!file5.exists());
                        MainActivity.viewPager.setCurrentItem(5);
                        Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();

                         OpenFileDialog.etn[1]=false;OpenFileDialog.etn[2]=false;
//                        soundflag=false;

                    }
                    if(OpenFileDialog.etn[2]){
                        if(file5.exists())file5.delete();
                        OpenFileDialog.json = Json.jsoncreatemubantext(i,soundtext);
                        SET(flag[0],flag[1],flag[2]);
                        setlogin(login);
                        setfre(freflag);
                        Log.i("json",OpenFileDialog.json);
                        POST.postAsynFile(OpenFileDialog.json);
                        while(!file5.exists());
                        MainActivity.viewPager.setCurrentItem(5);
                        Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();
                        OpenFileDialog.etn[1]=false;
//                        OpenFileDialog.etn[2]=false;

                    }
                    textflag = false;
//                    soundflag = false;


                }



                if(textflag){//选择输入文字
                    if(OpenFileDialog.etn[0]){//选择本地图片

                        SET(flag[0],flag[1],flag[2]);
                        setlogin(login);
                        setfre(freflag);
//                        flag[0]=false;flag[1]=false;flag[2]=false;
                        if(file5.exists())file5.delete();
                        Log.i("json",OpenFileDialog.json);
                        POST.postAsynFile(OpenFileDialog.json);
                        while(!file5.exists());
                        MainActivity.viewPager.setCurrentItem(5);
                        Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();


//                        OpenFileDialog.etn[0]=false;
                        OpenFileDialog.etn[1]=false;
                        OpenFileDialog.etn[2]=false;

                    }
                    if(OpenFileDialog.etn[2]){
                            if(file5.exists())file5.delete();
                            OpenFileDialog.json = Json.jsoncreatemubantext(i,text);
                            SET(flag[0],flag[1],flag[2]);
                        setlogin(login);
//                            flag[0]=false;flag[1]=false;flag[2]=false;
                        setfre(freflag);
                            Log.i("json",OpenFileDialog.json);
                            POST.postAsynFile(OpenFileDialog.json);
                            Log.i("run?","run");
                            while(!file5.exists());
                            MainActivity.viewPager.setCurrentItem(5);
                            Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();
                            OpenFileDialog.etn[1]=false;
                            OpenFileDialog.etn[0]=false;
//                            OpenFileDialog.etn[2]=false;
//                            textflag=false;


                    }

                    soundflag = false;
                }

                if(OpenFileDialog.etn[0] && OpenFileDialog.etn[1] )
                {

                    SET(flag[0],flag[1],flag[2]);
                    setlogin(login);
//                    flag[0]=false;flag[1]=false;flag[2]=false;
                    setfre(freflag);
                        if(file5.exists())file5.delete();
                    Log.i("json",OpenFileDialog.json);
                        POST.postAsynFile(OpenFileDialog.json);
                        while(!file5.exists());
                        MainActivity.viewPager.setCurrentItem(5);
                        Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();


//                    OpenFileDialog.etn[0]=false;OpenFileDialog.etn[1]=false;
                    OpenFileDialog.etn[2]=false;
                    textflag=false;soundflag =false;
                }

                if(OpenFileDialog.etn[2] && OpenFileDialog.etn[1] ){
                    doc=OpenFileDialog.str[1];
                    File filedoc = new File(doc);
                    Log.i("i",""+i);
                    try {
                        if(file5.exists())file5.delete();
                        OpenFileDialog.json = Json.jsoncreatemuban(i,filedoc);
                        SET(flag[0],flag[1],flag[2]);
                        setlogin(login);
                        setfre(freflag);
//                        flag[0]=false;flag[1]=false;flag[2]=false;
                        Log.i("json",OpenFileDialog.json);
                        POST.postAsynFile(OpenFileDialog.json);
                        Log.i("run?","run");
                        while(!file5.exists());
                        MainActivity.viewPager.setCurrentItem(5);
                        Toast.makeText(getApplicationContext(), "图片生成！即将跳转！", Toast.LENGTH_SHORT).show();

                        OpenFileDialog.etn[0]=false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    soundflag = false;
                    textflag=false;
                }
                freflag = false;

                pro1.setVisibility(ProgressBar.GONE);

            }
        });
//选择背景颜色
        dialog3= new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.diatit1)
                .setSingleChoiceItems(R.array.Radio_dialog_items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] items=getResources().getStringArray(R.array.Radio_dialog_items);
                                String locationname=items[which];
                                background=locationname; //需要传递的参数
                                //Toast.makeText(MainActivity.this,locationname,Toast.LENGTH_SHORT).show();
                            }

                        }
                )
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(background ==null)background = "白色";
                        flag[0] = true;
                        Toast.makeText(MainActivity.this,background,Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        flag[0] = false;
                        Toast.makeText(MainActivity.this,"false",Toast.LENGTH_SHORT).show();

                    }
                })
                .create();

//选择词云色调
        LayoutInflater factory=LayoutInflater.from(this);
        final View imageEntryView=factory.inflate(R.layout.colormap,null);
        final ListView  Lv=(ListView)findViewById(R.id.listv2);
        final int icno[] = { R.mipmap.afmhot, R.mipmap.viridis, R.mipmap.bone,
                R.mipmap.cool, R.mipmap.copper, R.mipmap.gist_heat, R.mipmap.gray,
                R.mipmap.hot, R.mipmap.inferno, R.mipmap.magma, R.mipmap.pink, R.mipmap.plasma,
                R.mipmap.spring,R.mipmap.summer,R.mipmap.autumn,R.mipmap.winter};
        final String name[]={"afmhot","viridis","bone","cool","copper ","gist_heat","gray","hot","inferno","magma","pink",
                "plasma","spring","summer","autumn","winter"};
        final ReAdapter2 adapter2=new ReAdapter2(this,R.layout.listview,name,icno);
//        Lv.setAdapter(adapter2);
//        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Lv.getAnimation();
//                for(int i=0;i<parent.getCount();i++){
//                    View v=parent.getChildAt(i);
//                    if (position == i) {//当前选中的Item改变背景颜色
//                        view.setBackgroundResource(R.color.toolbarcol);
//                        Toast.makeText(MainActivity.this,name[i],Toast.LENGTH_LONG).show();
//                    } else {
//                        v.setBackgroundResource(R.color.transparent);
//                    }
//                }
//            }
//        });


        dialog4= new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.diatit2)
                .setSingleChoiceItems(adapter2, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String locationname= adapter2.getItem(which).toString();
                        colorm=locationname; //需要传递的参数
                        //Toast.makeText(MainActivity.this,"选中"+locationname,Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag[1] = true;
                        if(colorm ==null)flag[1]=false;
                        else{
                        Toast.makeText(MainActivity.this,"选中"+colorm,Toast.LENGTH_SHORT).show();}
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag[1]= false;
                    }
                })
                .create();

//选择字体
        dialog5= new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.diatit3)
                .setSingleChoiceItems(R.array.Radio_dialog_items2, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] items=getResources().getStringArray(R.array.Radio_dialog_items2);
                                String locationname=items[which];
                                Fonts=locationname; //需要传递的参数
                                //Toast.makeText(MainActivity.this,locationname,Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag[2] = true;
                        if(Fonts ==null)Fonts = "华文中宋";
                        Toast.makeText(MainActivity.this,"选中"+Fonts,Toast.LENGTH_SHORT).show();
                   }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag[2]= false;

                    }
                })
                .create();






        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.drawable.filefolder);   // 根目录图标
        images.put(OpenFileDialog.sParent, R.drawable.filefolder);    //返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.drawable.filefolder);   //文件夹图标
        images.put("txt", R.drawable.txticon);   //txt文件图标
        images.put("jpg", R.drawable.jpegiocn);
        images.put(OpenFileDialog.sEmpty, R.drawable.empty);

        dialog1 = OpenFileDialog.createDialog(0, this, "选择图片文件", callback,
                ".bmp;.jpg;.png;.gif;",images);
        dialog2 = OpenFileDialog.createDialog(1, this, "选择WORD文件", callback, ".doc;.docx;", images);
        dialog6 = OpenFileDialog.createDialog(1, this, "选择txt文件", callback, ".txt;",
                images);

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
        p.setTextColorResource(R.color.gray1);
        p.setDividerWidth(0);
        p.setDividerPadding(20);

    }





    //适配器
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] titles;
        ArrayList<Fragment> fragments;
        public FragmentManager fm;
        public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm=fm;
            this.titles = titles;
            this.fragments = fragments;

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Log.i("sssssssssssss", "getItem");
            fragment = fragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", "" + position);
            fragment.setArguments(bundle);
            return fragment;
        }
        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //
            if(position!=5&&position!=4)
            { Fragment fragment = fragments.get(position);
            fm.beginTransaction().hide(fragment).commit();}
            else{
                super.destroyItem(container, position, object);
                Log.i("wwwwwww", "getItem");
                if(position==4) Log.i("44444", "getItem");
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class  ReAdapter2 extends BaseAdapter {
        private LayoutInflater mInflater;
        private  String[] mDataSource;
        private int[] mIcons;
        private int mResource;
        private Context mContext;

        public ReAdapter2(Context context,int resource,String[] datasource,int [] icons){
            mContext=context;
            mResource=resource;
            mDataSource=datasource;
            mIcons=icons;
            mInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount(){
            return mDataSource.length;
        }

        @Override
        public Object getItem(int position){
            return mDataSource[position];
        }

        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position,View convertview,ViewGroup parent){
            ViewHolder holder;
            if(convertview==null) {
                convertview=mInflater.inflate(mResource,null);
                holder=new ViewHolder() ;
                holder.textView=(TextView)convertview.findViewById(R.id.text);
                holder.imageView=(ImageView)convertview.findViewById(R.id.img);
                convertview.setTag(holder);}
            else{
                holder=(ViewHolder)convertview.getTag();
            }
            holder.textView.setText(mDataSource[position]);
            Bitmap icon= BitmapFactory.decodeResource(mContext.getResources(),mIcons[position]);
            holder.imageView.setImageBitmap(icon);
            return convertview;
        }
        private class ViewHolder{
            TextView textView;
            ImageView imageView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    //   EventListener  回调方法
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = new String();


        if (params != null && !params.isEmpty()) {
            logTxt = " ;params :" + params;

            int dix =logTxt.lastIndexOf("results_recognition");
            int dix2 =logTxt.lastIndexOf("origin_result");
            if (dix < 0) {
                logTxt = "";
            } else {
                logTxt = logTxt.substring(dix + 23,dix2);
                logTxt = logTxt.replaceAll(",|]|\"","");
                soundtext = logTxt;
            }

        }

        printLog(logTxt);
    }

    public static void printLog(String text) {
        Log.i("语音识别", text);
//        txtLog.append(text + "\n");
    }



    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    private void setlogin(boolean login){
        if(login){
            OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"username\":\""+username+"\"}");
        }
    }
    public void setfre(boolean freflag){
        if(freflag){
            if(OpenFileDialog.json.indexOf("changelist")<0)
                OpenFileDialog.json = OpenFileDialog.json.replace("}","," +
                        "\"changelist\":\"{");
            else OpenFileDialog.json = OpenFileDialog.json.substring(0,OpenFileDialog.json.indexOf
                    ("changelist\":\"{"));
            int j =0;
            while(number>0){
                number--;
                OpenFileDialog.json = OpenFileDialog.json+"\""+MyFragment4.data.get(j)
                        +"\":"+MyFragment4.data2.get(j)
                        +",";
                Log.i("j",j+"");
                j++;
            }

            if(j==0) OpenFileDialog.json = OpenFileDialog.json+"}\"}";
                else OpenFileDialog.json = OpenFileDialog.json.substring(0,OpenFileDialog.json
                    .length
                    ()-1)
                    +"}\"}";
            Log.i("setfre",OpenFileDialog.json.substring(OpenFileDialog.json.indexOf("changelist")));

        }
        MyFragment4.data.clear();
        MyFragment4.data2.clear();
    }

    private void SET(boolean flag0,boolean flag1,boolean flag2){
        if(flag0){
            if(background.equals("白色"));
            if(background.equals("黑色")){

                OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"background_color\":\"black\"}");
                Log.i("black",background);
            }
        }
        if(flag1){
            OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"colormap\":\""+colorm+"\"}");
            Log.i("testflag2","true");
        }
        if(flag2){
            if(Fonts.equals("华文彩云"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"caiyun\"}");
            if(Fonts.equals("华文楷体"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"kaiti\"}");
            if(Fonts.equals("华康少女体"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"shaonv\"}");
            if(Fonts.equals("华文行楷"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"xingkai\"}");
            if(Fonts.equals("华康雅宋体"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"yasong\"}");
            if(Fonts.equals("华文中宋"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"zhongsong\"}");
            if(Fonts.equals("繁体"))OpenFileDialog.json = OpenFileDialog.json.replace("}",",\"font\":\"fan\"}");

            Log.i("testflag3","true");
        }

    }
}