package com.my.tab;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import static android.app.Activity.RESULT_OK;

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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup mRadioGroup1 = (RadioGroup) getActivity().findViewById(R.id.RadioGroup02);
        mRadioGroup1.setOnCheckedChangeListener(this);
    }

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
                LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams(LinearLayout.
                        LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                button.setText(R.string.p211);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Matisse.from(getActivity())
                                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                                .countable(false)
                                .maxSelectable(1) // 图片选择的最多数量
                                .gridExpectedSize(400)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f) // 缩略图的比例
                                .theme(R.style.Matisse_Dracula)
                                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
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


}