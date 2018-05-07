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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.my.tab.MainActivity.i;
//import static com.my.tab.R.id.mbtn1;
//import static com.my.tab.R.id.mtv2;

/**
 * Created by w8 on 2018/3/25.
 */

public class MyFragment4 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_detail4,container,false);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListView mList = (ListView) getActivity().findViewById(R.id.mList);
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            data.add("词语" + i);
        }

        List<Integer> data2 = new ArrayList<>();
        Random ran=new Random();
        for(int i = 0; i < 20; i ++){
            data2.add((ran.nextInt()%20+20)%20);
        }
        MyAdapter adapter = new MyAdapter(data,data2);
        mList.setAdapter(adapter);
        //ListView item点击事件
        mList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),"我是item点击事件 i = " + i + "l = " + l,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyAdapter extends BaseAdapter implements View.OnClickListener {
        //上下文
        private Context context;
        //数据项
        private List<String> data;
        private List<Integer> data2;
        public MyAdapter(List<String> data,List<Integer> data2){
            this.data = data;
            this.data2 = data2;
        }
        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(context == null)
                context = viewGroup.getContext();
            if(view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview2,null);
                viewHolder = new ViewHolder();
                viewHolder.mtv = (TextView)view.findViewById(R.id.mtv);
                viewHolder.mtv2 = (TextView)view.findViewById(R.id.mtv2);
                viewHolder.mbtn1 = (Button)view.findViewById(R.id.mbtn1);
                viewHolder.mbtn2 = (Button)view.findViewById(R.id.mbtn2);
                view.setTag(viewHolder);
            }
            //获取viewHolder实例
            viewHolder = (ViewHolder)view.getTag();
            viewHolder.mbtn1.setTag(R.id.mbtn1,i);
            viewHolder.mbtn2.setTag(R.id.mbtn2,i);
            viewHolder.mtv.setTag(R.id.mtv,i);
            viewHolder.mtv2.setTag(R.id.mtv2,i);
            //设置数据
            viewHolder.mtv.setText(data.get(i));
            viewHolder.mtv2.setText(data2.get(i).toString());
            //设置监听事件
            viewHolder.mtv.setOnClickListener(this);
            //设置数据
            viewHolder.mbtn1.setOnClickListener(this);
            viewHolder.mbtn2.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.mbtn1:
                    Log.d("tag", "Btn_onClick: " + "view = " + view);
                    Toast.makeText(context,"上调",Toast.LENGTH_SHORT).show();
                    data2.set(i,data2.get(i)+1);
                    this.notifyDataSetChanged();
                    //mtv2.setText(data2.get(i).toString());
                    break;
                case R.id.mbtn2:
                    Log.d("tag", "Btn_onClick: " + "view = " + view);
                    Toast.makeText(context,"下调",Toast.LENGTH_SHORT).show();
                    if(data2.get(i)-1!=0)
                    data2.set(i,data2.get(i)-1);
                    this.notifyDataSetChanged();
                   // view.mtv2.setText(data2.get(i).toString());
                    break;
                case R.id.mtv:
                    Log.d("tag", "Tv_onClick: " + "view = " + view);
                    Toast.makeText(context,data.get(i), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

         class ViewHolder{
            TextView mtv;
            TextView mtv2;
            Button mbtn1;
            Button mbtn2;
        }

    }
}
