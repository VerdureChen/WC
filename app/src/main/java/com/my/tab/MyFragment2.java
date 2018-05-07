package com.my.tab;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.R.id.list;
import static android.app.Activity.RESULT_OK;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND;
import static android.support.constraint.R.id.parent;

/**
 * Created by w8 on 2018/3/9.
 */

public class MyFragment2 extends Fragment implements RadioGroup.OnCheckedChangeListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail2, container, false);

        return v;
    }
    private static final int REQUEST_CODE_CHOOSE = 23;
    //private List<Map<String, Object>> dataList;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup mRadioGroup1 = (RadioGroup) getActivity().findViewById(R.id.RadioGroup02);
        mRadioGroup1.setOnCheckedChangeListener(this);
        mRadioGroup1.check(R.id.RadioButton01);
    }

//    CallbackBundle callback = new CallbackBundle() {
//        Bundle bundle = new Bundle();
//
//        @Override
//        public void callback(Bundle bundle) {
//            //String filepath = bundle.getString("path");
//            //setTitle(filepath); // 把文件路径显示在标题上
//
//        }
//
//
//    };

    //Context context = MyApplication.getInstance();

//    Dialog dialog1 = OpenFileDialog.createDialog(0,context, "选择图片文件", callback,
//            ".bmp;.jpg;.png;.gif;",MainActivity.images);


    /*void initData() {
        //图标
        int icno[] = { R.drawable.k1, R.drawable.k2, R.drawable.k3,
                R.drawable.k4, R.drawable.k5, R.drawable.k6, R.drawable.k7,
                R.drawable.k8, R.drawable.k9, R.drawable.k10, R.drawable.k11, R.drawable.k12 };
        //图标下的文字
        String name[]={"苹果","五星","树叶","松鼠","图标 ","小猫","熊猫","蝴蝶","海豚","小狗","枫叶","游鱼"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }
    }*/







    @Override
    public void onCheckedChanged(RadioGroup rdp, int checkedId) {
        switch (checkedId) {
            case R.id.RadioButton01:
                changeButton(01);
                break;
            case R.id.RadioButton02:
                changeButton(02);
                break;

        }

    }





    public void changeButton(int i){
        LinearLayout rootLayout = (LinearLayout)getActivity().findViewById(R.id.fragmentContainer02);
        rootLayout.removeAllViews();
        switch (i){
            case 01:
                Button button = new Button(getActivity());
                button.setBackgroundResource(R.drawable.mybutton);
               // button.setTextColor(getResources().getColor(R.color.gray1));
                LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams(LinearLayout.
                        LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                button.setText(R.string.p211);
                button.setText("选择本地图片");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.dialog1.show();

//                        Matisse.from(getActivity())
//                                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF), false) // 选择 mime 的类型
//                                .countable(false)
//                                .maxSelectable(1) // 图片选择的最多数量
//                                .gridExpectedSize(400)
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f) // 缩略图的比例
//                                .theme(R.style.Matisse_Dracula)
//                                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
//                                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                    }
                });
                rootLayout.addView(button,button_parent_params);
                break;
            case 02:
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                // 获取需要添加的布局
                View layout = inflater.inflate(
                        R.layout.showmodel, null);
                layout.setLayoutParams(lp);
                rootLayout.addView(layout);
                final GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
                //initData();
                //String[] from={"img","text"};
                //int[] to={R.id.img,R.id.text};
                final int icno[] = {  R.mipmap.k2, R.mipmap.k3,
                        R.mipmap.k4, R.mipmap.k6,
                        R.mipmap.k8, R.mipmap.k9, R.mipmap.k10, R.mipmap.k11, R.mipmap.k12 };
                //图标下的文字
                String name[]={"五星","树叶","松鼠","小猫","蝴蝶","海豚","小狗","枫叶","游鱼"};
                //SimpleAdapter adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview, from, to);
                ReAdapter adapter=new ReAdapter(getActivity(),R.layout.gridview,name,icno);
                gridView.setAdapter(adapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        /*AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                        builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();*/
                        gridView.getAnimation();
                        for(int i=0;i<arg0.getCount();i++){
                            View v=arg0.getChildAt(i);
                            if (arg2 == i) {//当前选中的Item改变背景颜色
                                arg1.setBackgroundResource(R.color.toolbarcol);

                                OpenFileDialog.etn[2] = true;

                                MainActivity.i=i;

                                if(!OpenFileDialog.etn[1])
                                {
                                    Log.i("etn1=false","");
                                    Toast.makeText(getContext(),"select document",Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                v.setBackgroundResource(R.color.transparent);
                            }
                        }
                    }
                });
                break;
        }
    }
    List<Uri> mSelected;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }



    private class  ReAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private  String[] mDataSource;
    private int[] mIcons;
    private int mResource;
    private Context mContext;

    public ReAdapter(Context context,int resource,String[] datasource,int [] icons){
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


}

