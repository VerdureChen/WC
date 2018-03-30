package com.my.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;



/**
 * Created by w8 on 2018/3/9.
 */

public class MyFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_detail,container,false);
        return v;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        RadioGroup mRadioGroup1 = (RadioGroup) getActivity().findViewById(R.id.RadioGroup01);
        mRadioGroup1.setOnCheckedChangeListener(this);
    }



    @Override
    public void onCheckedChanged(RadioGroup rdp,int checkedId){
        switch(checkedId){
            case R.id.RadioButton01:
                changeButton(01);
                break;
            case R.id.RadioButton02:
                changeButton(02);
                break;
            case R.id.RadioButton03:
                changeButton(03);
                break;

        }

    }





    public void changeButton(int i){
        LinearLayout rootLayout = (LinearLayout)getActivity().findViewById(R.id.fragmentContainer01);
        rootLayout.removeAllViews();
        Button button = new Button(getActivity());
        LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        switch (i){
            case 01:
                button.setText(R.string.p11);
                break;
            case 02:
                button.setText(R.string.p12);
                break;
            case 03:
                EditText ed= new EditText(getActivity());
                button.setText(R.string.p13);
                LinearLayout.LayoutParams edit_parent_params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                rootLayout.addView(ed,edit_parent_params);
                break;
        }


        rootLayout.addView(button,button_parent_params);
    }
}
