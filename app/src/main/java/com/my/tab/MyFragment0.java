package com.my.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;

import static android.view.View.MEASURED_HEIGHT_STATE_SHIFT;
import static android.view.View.inflate;

/**
 * Created by w8 on 2018/5/24.
 */

public class MyFragment0 extends Fragment implements RadioGroup.OnCheckedChangeListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_detail0,container,false);

        return v;
}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        RadioGroup mRadioGroup1 = (RadioGroup) getActivity().findViewById(R.id.RadioGroup001);
        mRadioGroup1.setOnCheckedChangeListener(this);
        mRadioGroup1.check(R.id.RadioButton001);
    }

    @Override
    public void onCheckedChanged(RadioGroup rdp,int checkedId){
        switch(checkedId){
            case R.id.RadioButton001:
                changeButton(001);
                break;
            case R.id.RadioButton002:
                changeButton(002);
                break;
        }

    }

    public void changeButton(int i){
        final LinearLayout rootLayout = (LinearLayout)getActivity().findViewById(R.id
                .fragmentContainer001);
        rootLayout.removeAllViews();
        final Button button = new Button(getActivity());
        button.setBackgroundResource(R.drawable.mybutton);
        final LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        switch(i){
            case 001:
                final  TextView text1=new TextView(getActivity());
                text1.setText(R.string.log1);

                final EditText etext1=new EditText(getActivity());
                etext1.setHint(R.string.username);

                final EditText etext2=new EditText(getActivity());
                etext2.setHint(R.string.password);
                etext2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams textview1_parent_params4= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params5= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params6= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params7= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                text1.setPadding(20,20,20,20);
                text1.setTextSize(25);
                textview1_parent_params5.setMargins(50,20,50,20);
                textview1_parent_params6.setMargins(50,20,50,20);
                rootLayout.addView(text1,textview1_parent_params4);
                rootLayout.addView(etext1,textview1_parent_params5);
                rootLayout.addView(etext2,textview1_parent_params6);

                RelativeLayout.LayoutParams li = new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                // 获取需要添加的布局
                View layout = inflater.inflate(
                        R.layout.seekb1, null);
                layout.setLayoutParams(li);
                rootLayout.addView(layout);
               // View vv=View.inflate(getActivity(),R.layout.seekb1,null);
                SeekBar sekbar= (SeekBar) getActivity().findViewById(R.id.sb_progress22);
                final TextView tv = (TextView) getActivity().findViewById(R.id.tv22);
                sekbar.setOnSeekBarChangeListener(new SeekBar
                        .OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (seekBar.getProgress() == seekBar.getMax()) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setTextColor(getResources().getColor(R.color.toolbarcol));
                            tv.setText("完成验证");
                        } else {
                            tv.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (seekBar.getProgress() != seekBar.getMax()) {
                            seekBar.setProgress(0);
                            tv.setVisibility(View.VISIBLE);
                            tv.setTextColor(getResources().getColor(R.color.gray1));
                            tv.setText("请按住滑块，拖动到最右边");
                        }
                        else {
                            seekBar.setEnabled(false);

                        }

                    }
                });
                RelativeLayout.LayoutParams li1 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater1 = LayoutInflater.from(getActivity());
                // 获取需要添加的布局
                View layout1 = inflater1.inflate(
                        R.layout.zhuce, null);
                layout1.setLayoutParams(li1);
                rootLayout.addView(layout1);
                TextView zhuce=(TextView)getActivity().findViewById(R.id.zhuce);
                zhuce.setText(R.string.zhuce);
                Button zhuce2=(Button)getActivity().findViewById(R.id.zhuce2);
                zhuce2.setText(R.string.zhuce2);
                zhuce2.setBackgroundResource(R.drawable.mybutton);
                zhuce2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //登陆
                        //
                        //
                        MainActivity.username = etext1.getText().toString();
                        MainActivity.password = etext2.getText().toString();
                        POST.postAsynlogin(Json.jsonuserdata(MainActivity.username,MainActivity.password));
                        while(MainActivity.code.equals(""));


                            if (MainActivity.code.equals("200")) {
                                rootLayout.removeAllViews();
                                text1.setText(R.string.success1);
                                MainActivity.code="";
                                MainActivity.login = true;
                                RelativeLayout.LayoutParams textview1_parent_params8 = new RelativeLayout
                                        .LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                rootLayout.addView(text1, textview1_parent_params8);

                            }
                            if (MainActivity.code.equals("403")) {
                                rootLayout.removeAllViews();
                                text1.setText(MainActivity.message);
                                MainActivity.code="";
                                MainActivity.login = false;
                                RelativeLayout.LayoutParams textview1_parent_params8 = new RelativeLayout
                                        .LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                rootLayout.addView(text1, textview1_parent_params8);
                            }
//                            if (!(MainActivity.code.equals("200"))) {
//                                text1.setText("其他错误!");
//                                MainActivity.code="";
//                                MainActivity.login = false;
//                            }



//                        RelativeLayout.LayoutParams textview1_parent_params8 = new RelativeLayout
//                                .LayoutParams
//                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        rootLayout.addView(text1, textview1_parent_params8);
//                        button.setText(R.string.find);
//                        rootLayout.addView(button, button_parent_params);

//                        POST.postAsynretrive("{\"username\":\"nzx\"}");
                        }


                });

                break;
            case 002:
                final  TextView text11=new TextView(getActivity());
                text11.setText(R.string.log2);
                final EditText etext11=new EditText(getActivity());
                etext11.setHint(R.string.username);
                final EditText etext22=new EditText(getActivity());
                etext22.setHint(R.string.password);
                etext22.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams textview1_parent_params41= new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params51= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params61= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textview1_parent_params71= new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                text11.setPadding(20,20,20,20);
                text11.setTextSize(25);
                textview1_parent_params51.setMargins(50,20,50,20);
                textview1_parent_params61.setMargins(50,20,50,20);
                rootLayout.addView(text11,textview1_parent_params41);
                rootLayout.addView(etext11,textview1_parent_params51);
                rootLayout.addView(etext22,textview1_parent_params61);

                RelativeLayout.LayoutParams li11 = new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater11 = LayoutInflater.from(getActivity());
                // 获取需要添加的布局
                View layout11 = inflater11.inflate(
                        R.layout.seekb1, null);
                layout11.setLayoutParams(li11);
                rootLayout.addView(layout11);
                // View vv=View.inflate(getActivity(),R.layout.seekb1,null);
                SeekBar sekbar1= (SeekBar) getActivity().findViewById(R.id.sb_progress22);
                final TextView tv1 = (TextView) getActivity().findViewById(R.id.tv22);
                sekbar1.setOnSeekBarChangeListener(new SeekBar
                        .OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (seekBar.getProgress() == seekBar.getMax()) {
                            tv1.setVisibility(View.VISIBLE);
                            tv1.setTextColor(getResources().getColor(R.color.toolbarcol));
                            tv1.setText("完成验证");
                        } else {
                            tv1.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (seekBar.getProgress() != seekBar.getMax()) {
                            seekBar.setProgress(0);
                            tv1.setVisibility(View.VISIBLE);
                            tv1.setTextColor(getResources().getColor(R.color.gray1));
                            tv1.setText("请按住滑块，拖动到最右边");
                        }
                        else {
                            seekBar.setEnabled(false);

                        }

                    }
                });
                RelativeLayout.LayoutParams li12 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater12 = LayoutInflater.from(getActivity());
                // 获取需要添加的布局
                View layout12 = inflater12.inflate(
                        R.layout.zhuce, null);
                layout12.setLayoutParams(li12);
                rootLayout.addView(layout12);
                TextView zhuce11=(TextView)getActivity().findViewById(R.id.zhuce);
                zhuce11.setText(R.string.zhuce);
                Button zhuce22=(Button)getActivity().findViewById(R.id.zhuce2);
                zhuce22.setText(R.string.zhuce2);
                zhuce22.setBackgroundResource(R.drawable.mybutton);
                zhuce22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //注册
                        MainActivity.username = etext11.getText().toString();
                        MainActivity.password = etext22.getText().toString();
                        POST.postAsynregister(Json.jsonuserdata(MainActivity.username,MainActivity.password));
                        while(MainActivity.code.equals(""));

                        Log.i("code",MainActivity.code);
                        rootLayout.removeAllViews();
                        if(MainActivity.code.equals("200"))
                        {
                            text11.setText(R.string.success2);
                            MainActivity.code="";
                            MainActivity.register=true;
                        }
                        if(MainActivity.code.equals("400")){
                            text11.setText("用户已存在");
                            MainActivity.code="";
                            MainActivity.register=false;
                        }
                        RelativeLayout.LayoutParams textview1_parent_params8= new RelativeLayout
                                .LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        rootLayout.addView(text11,textview1_parent_params8);
                        button.setText(R.string.ret1);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RadioGroup mRadioGroup1 = (RadioGroup) getActivity().findViewById(R.id.RadioGroup001);
                                mRadioGroup1.check(R.id.RadioButton001);
                            }
                        });
                        rootLayout.addView(button,button_parent_params);
                    }
                });
        }
    }



}

