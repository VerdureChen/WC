package com.my.tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by w8 on 2018/3/9.
 */

public class MyFragment3 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_detail3,container,false);
        return v;
}
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button btn1=(Button)getActivity().findViewById(R.id.beijing);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dialog3.show();
            }
        });
        Button btn2=(Button)getActivity().findViewById(R.id.sediao);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dialog4.show();
            }
        });
        Button btn3=(Button)getActivity().findViewById(R.id.ziti);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dialog5.show();
            }
        });
    }}
