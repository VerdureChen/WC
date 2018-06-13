package com.my.tab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.my.tab.R.id.mbtn1;
import static com.my.tab.R.id.mbtn2;
import static com.my.tab.R.id.mtv;
import static com.my.tab.R.id.mtv2;

/**
 * Created by w8 on 2018/6/5.
 */

public class MyAdapter extends BaseAdapter implements View.OnClickListener {
    //上下文
    private Context context;
    //数据项
    private List<String> data;
    private List<Integer> data2;
    private Callback mCallback;
     int it;
    public interface Callback {
        public void click(View v);
    }
    public MyAdapter(List<String> data,List<Integer> data2,Callback callback){
        this.data = data;
        this.data2 = data2;
        mCallback = callback;
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
        it=i;
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
            viewHolder.mtv = (TextView)view.findViewById(mtv);
            viewHolder.mtv2 = (TextView)view.findViewById(mtv2);
            viewHolder.mbtn1 = (Button)view.findViewById(mbtn1);
            viewHolder.mbtn2 = (Button)view.findViewById(mbtn2);
            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        viewHolder.mbtn1.setTag(i); //之前格式为settag(mtv2,i),导致返回值为null
        viewHolder.mbtn2.setTag(i);
        viewHolder.mtv.setTag(i);
        viewHolder.mtv2.setTag(i);
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
        //Log.d("tag222", "Btn_onClick: " + "view = " + view+view.getTag());
        mCallback.click(view);
        this.notifyDataSetChanged();
    }

}

class ViewHolder{
    TextView mtv;
    TextView mtv2;
    Button mbtn1;
    Button mbtn2;
}

