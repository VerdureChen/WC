package com.my.tab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.baidu.speech.asr.SpeechConstant;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.my.tab.MainActivity.asr;
import static com.my.tab.MainActivity.enableOffline;
import static com.my.tab.MainActivity.printLog;


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
        mRadioGroup1.check(R.id.RadioButton01);
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
            case R.id.RadioButton04:
                changeButton(04);
                break;
            case R.id.RadioButton05:
                changeButton(05);
                break;

        }

    }





    public void changeButton(int i){
        LinearLayout rootLayout = (LinearLayout)getActivity().findViewById(R.id.fragmentContainer01);
        rootLayout.removeAllViews();
        final Button button = new Button(getActivity());
        final ToggleButton button4=new ToggleButton(getActivity());
        //button.setTextColor(getResources().getColor(R.color.gray1));
        button.setBackgroundResource(R.drawable.mybutton);
        LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        switch (i){
            case 01:
                button.setText(R.string.p11);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity.dialog6.show();
                    }
                });
                break;
            case 02:
                button.setText(R.string.p12);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity.dialog2.show();
                    }
                });
                break;

            case 04:
                final EditText ed1= new EditText(getActivity());
                ed1.setMaxLines(3);
                button.setText(R.string.p14);
                LinearLayout.LayoutParams edit_parent_params1= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                rootLayout.addView(ed1,edit_parent_params1);


                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        MainActivity.textflag = true;
                        OpenFileDialog.etn[1]= false;
                        MainActivity.text =ed1.getText().toString();
                        if(button.getText().equals("点击确认"))
                        {ed1.setEnabled(false);
                         button.setText("点击修改");
                        }
                        else {ed1.setEnabled(true);
                            button.setText("点击确认");
                        }
                        Log.i("text",MainActivity.text);
                    }
                });
                break;
            case 05:
                button.setText("上传语音");
                final TextView tx=new TextView(getActivity());
                //button.setTextColor(getResources().getColor(R.color.gray1));
                tx.setText("点击上传语音按钮以显示输入的文字");
                tx.setMaxLines(5);
                tx.setHeight(300);
                button4.setText("开始录音");
                button4.setTextOff("开始录音");
                button4.setTextOn("结束录音");
                button4.setBackgroundResource(R.drawable.mybutton2);
                //button.setTextColor(getResources().getColor(R.color.gray1));
                //tx.setMovementMethod(ScrollingMovementMethod.getInstance());
               // tx.setMaxLines(5);


                LinearLayout.LayoutParams button_parent_params3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,120);
                LinearLayout.LayoutParams button_parent_params4= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                rootLayout.addView(button4,button_parent_params3);
                rootLayout.addView(tx,button_parent_params4);
                button4.setPadding(5,5,5,5);
                button4.setGravity(Gravity.CENTER);
                button4.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                       if(button4.isChecked()==true){
                           //根据按钮状态选择
                           start();

                           MainActivity.soundflag=true;
                       }
                       if(button4.isChecked()==false){
                           stop();

                       }
                    }
            });

                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i("语音识别上传",MainActivity.soundtext);
                        tx.setText(MainActivity.soundtext);
                    }
                });

        }


        rootLayout.addView(button,button_parent_params);
    }
    public void start() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        // params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        // params.put(SpeechConstant.PROP ,20000);
        // params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);

        // 复制此段可以自动检测错误
        (new AutoCheck(getContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
//                        txtLog.append(message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        Log.w("AutoCheckMessage", message);
                    }
                }
            }
        },enableOffline)).checkAsr(params);
        String jsonyuyin = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, jsonyuyin, null, 0, 0);
        printLog("输入参数：" + jsonyuyin);
    }

    public void stop() {
        printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }
}
