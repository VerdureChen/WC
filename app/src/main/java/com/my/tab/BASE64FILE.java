package com.my.tab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by w8 on 2018/3/31.
 */

public class BASE64FILE {


    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();

        return new BASE64Encoder().encodeBuffer(buffer);
    }
    public static String readtxtFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        InputStreamReader inp =new InputStreamReader(inputFile,"GBK");
        BufferedReader reader = new BufferedReader(inp);
        String line;
        String fileContent = "";
        while((line = reader.readLine())!= null){
            fileContent += line;
        }
        inp.close();
        inputFile.close();
        return fileContent;
    }
    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static Bitmap decoderBase64toBitmap(String base64Code)throws Exception{

        byte[] buffer =new BASE64Decoder().decodeBuffer(base64Code);
        Bitmap bitmap= BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        return bitmap;
    }
    /**
     * 将base64字符保存文本文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code,String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }


}
