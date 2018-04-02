package com.my.tab;

import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by w8 on 2018/3/31.
 */

public class Json {



    private static String getSuffix(String filename) {
        int dix = filename.lastIndexOf('.');//搜索'.'最后出现的位置
        if (dix < 0) {
            return "";
        } else {
            return filename.substring(dix + 1);//截掉dix+1个字符，剩下的就是格式
        }
    }

    public static String jsoncreate(File img, File document) throws IOException {
        String suffix = getSuffix(img.getName());
        String suffix1 = getSuffix(document.getName());
        Log.w(suffix,"图片格式");
        if (!((suffix.equals("gif"))||suffix.equals("jpg")||suffix.equals("png")||suffix.equals("bmp")||suffix.equals("jpeg"))) {
            Log.w("图片格式不符", "jsoncreate");
            return "图片格式不符~!";
        }
        Log.w(suffix1,"文档格式");
        if (!((suffix1.equals("docx"))||(suffix1.equals("doc"))||(suffix1.equals("txt") ))) {
            Log.w("文档格式不符", "jsoncreate");
            return "文档格式不符~!";
        }


        String imgpath = img.getPath();
        String docupath = document.getPath();
        String imgdata;
        String documentdata;
        try {

            imgdata = BASE64FILE.encodeBase64File(imgpath).replaceAll("\r|\n", "");
            documentdata =BASE64FILE.encodeBase64File(docupath).replaceAll("\r|\n","");

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.w("解码BASE64异常啦", "jsoncreate");
            return "解码BASE64异常啦！";
        }


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("photo", imgdata);
//            jsonObject.put("doc", documentdata);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        String json;
        json = "{\"photo\":\""+imgdata+"\",\"doc\":\""+documentdata+"\"}";




        return json;



    }




}
