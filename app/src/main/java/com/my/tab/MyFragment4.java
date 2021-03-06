package com.my.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.my.tab.MyAdapter;
import com.my.tab.MyAdapter.Callback;

import static android.R.attr.data;
import static com.my.tab.MainActivity.i;
import static com.my.tab.MainActivity.viewPager;
import static com.my.tab.MainActivity.wordlist;
import static com.my.tab.R.id.mbtn1;
//import static com.my.tab.R.id.mbtn1;
//import static com.my.tab.R.id.mtv2;

/**
 * Created by w8 on 2018/3/25.
 */

public class MyFragment4 extends Fragment implements Callback{

    static int position;
    static final List<String> data = new ArrayList<>();
    static final List<Integer> data2 = new ArrayList<>();
    View v;
    ListView mList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v=inflater.inflate(R.layout.fragment_detail4,container,false);
        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){

        Log.d("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser&&v!=null) {
         mList = (ListView) getActivity().findViewById(R.id.mList);
        data.clear();
        data2.clear();
        int dix3 = MainActivity.wordlist.indexOf(":");
        MainActivity.number = 0;
        String word;
        int frequence;
        if(data2.size()==0){
            data.add("无词语");
            data2.add(1);
        }

        String wordlist = MainActivity.wordlist;

        while(dix3>=0){//获得wordlist并显示
            word = wordlist.substring(0,dix3);
            Log.i("word",word);
            if(MainActivity.number==0)data.set(0,word);
            else data.add(word);
            wordlist = wordlist.substring(dix3+1);
            Log.i("wordlist",wordlist);
            int dix4 = wordlist.indexOf(",");
            if(dix4>=0)
            {
                frequence = Integer.parseInt(wordlist.substring(0,dix4));
                wordlist = wordlist.substring(dix4+1);
            }
            else frequence = Integer.parseInt(wordlist);
            if(MainActivity.number==0)data2.set(0,frequence);
            else
                data2.add(frequence) ;

            dix3 = wordlist.indexOf(":");
            MainActivity.number++;
        }
        Log.i("检测number是否因为wordlist变为0",MainActivity.number+"");
//        for(i = 0; i < 20; i ++){
//            data.add("词语" + i);
//        }
//
//        Random ran=new Random();
//        for(i = 0; i < 20; i ++){
//            data2.add((ran.nextInt()%20+20)%20);
//        }
        MyAdapter adapter = new MyAdapter(data,data2,this);



        //ListView item点击事件
        mList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("item","我是item点击事件 i = " + i + "l = " + l);

                position = i;

            }


        });

        mList.setAdapter(adapter);}
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mList = (ListView) getActivity().findViewById(R.id.mList);
        data.clear();
        data2.clear();
        int dix3 = MainActivity.wordlist.indexOf(":");
        MainActivity.number = 0;
        String word;
        int frequence;
        if(data2.size()==0){
            data.add("无词语");
            data2.add(1);
        }

        String wordlist = MainActivity.wordlist;

        while(dix3>=0){//获得wordlist并显示
            word = wordlist.substring(0,dix3);
            Log.i("word",word);
            if(MainActivity.number==0)data.set(0,word);
            else data.add(word);
            wordlist = wordlist.substring(dix3+1);
            Log.i("wordlist",wordlist);
            int dix4 = wordlist.indexOf(",");
            if(dix4>=0)
            {
                frequence = Integer.parseInt(wordlist.substring(0,dix4));
                wordlist = wordlist.substring(dix4+1);
            }
            else frequence = Integer.parseInt(wordlist);
            if(MainActivity.number==0)data2.set(0,frequence);
            else
                data2.add(frequence) ;

            dix3 = wordlist.indexOf(":");
            MainActivity.number++;
        }
        Log.i("检测number是否因为wordlist变为0",MainActivity.number+"");
//        for(i = 0; i < 20; i ++){
//            data.add("词语" + i);
//        }
//
//        Random ran=new Random();
//        for(i = 0; i < 20; i ++){
//            data2.add((ran.nextInt()%20+20)%20);
//        }
        MyAdapter adapter = new MyAdapter(data,data2,this);



        //ListView item点击事件
        mList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("item","我是item点击事件 i = " + i + "l = " + l);

                position = i;

            }


        });

        mList.setAdapter(adapter);

    }


    @Override
    public  void onResume(){
        super.onResume();

    }
    /**
     * 接口方法，响应ListView按钮点击事件
     */

    @Override
    public void onStart(){
        super.onStart();

        if(getUserVisibleHint()&&mList!=null) {

        }
    }

    @Override
    public void click(View v) {
            Log.i("viewid", v.getId()+"");
        switch (v.getId()){
            case R.id.mbtn1:

                 Button btn11=(Button) v;
               int index = (Integer) btn11.getTag();
                Log.d("tag", "Btn_onClick: " + "view = " + v+v.getTag());
                Toast.makeText(getActivity(),"上调",Toast.LENGTH_SHORT).show();
                data2.set(index,data2.get(index)+1);
                MainActivity.freflag = true;
                ///mtv2.setText(data2.get(i).toString());
                break;
            case R.id.mbtn2:
                Log.d("tag", "Btn_onClick: " + "view = " + v+i);
                Button btn12=(Button) v;
                int index2 = (Integer) btn12.getTag();
                Toast.makeText(getActivity(),"下调",Toast.LENGTH_SHORT).show();
                if(data2.get(index2)-1!=0)
                    data2.set(index2,data2.get(index2)-1);
                MainActivity.freflag = true;
                // view.mtv2.setText(data2.get(i).toString());
                break;
            case R.id.mtv:
                Log.d("tag", "Tv_onClick: " + "view = " + v+i);
                TextView tx11=(TextView) v;
                int index3 = (Integer) tx11.getTag();
                Toast.makeText(getActivity(),data.get(index3), Toast.LENGTH_SHORT).show();
                break;
        }
}

@Override
    public void onPause(){
    super.onPause();
    v=null;
    mList=null;
}

}
