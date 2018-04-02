package com.my.tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.path;


/**
 * Created by w8 on 2018/3/25.
 */

public class MyFragment5 extends Fragment {

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_detail5,container,false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        File file5 = new File("/storage/emulated/0/wordcloud/wordcloud.jpg");
        ImageView imv = (ImageView)getActivity().findViewById(R.id.imagefinal) ;
        if(!file5.exists()){
            imv.setImageResource(R.drawable.bc5);
        }

        final Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/wordcloud/wordcloud.jpg");

        imv.setImageBitmap(bitmap);
        LinearLayout rootLayout = (LinearLayout)getActivity().findViewById(R.id.fragmentContainer03);
        rootLayout.removeAllViews();
        Button button = new Button(getActivity());
        button.setBackgroundResource(R.drawable.mybutton);
        //button.setTextColor(getResources().getColor(R.color.gray1));
        LinearLayout.LayoutParams button_parent_params= new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                button.setText(R.string.p211);
        button.setText("点击保存图片");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToGallery(getContext(),bitmap);
            }
        });
        rootLayout.addView(button,button_parent_params);}



        public static void saveImageToGallery(Context context, Bitmap bmp) {
            // 首先保存图片
            File appDir = new File(Environment.getExternalStorageDirectory(), "UCloud");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
            Toast.makeText(context, "图片已保存至相册！", Toast.LENGTH_SHORT).show();
        }
        //file5.delete();
    }

