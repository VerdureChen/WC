package com.my.tab;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Decoder.BASE64Decoder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * Created by w8 on 2018/3/31.
 */

public class POST {


    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static void postAsynFile(String json) {
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_1)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//                .build();
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
        RequestBody jsonbody = RequestBody.create(JSON,json);

        final Request request = new Request.Builder()
                .url("http://wc.justjian.site/api/t")
                .post(jsonbody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("failQAQ",e.getMessage());


            }
            String resp = new String();
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //InputStream is = response.body().byteStream();

                resp = response.body().string();
                Log.i("给点反应!",resp);

                int dix = resp.indexOf("\"pic_b64\":");
                int dix2 = resp.indexOf("\"wordlist\":");

                Log.i("wordlist",dix2+"");
                if(dix<0)
                    return ;
                else {
                    MainActivity.wordlist = resp.substring(dix2+1).replaceAll("\"| |\n","");
                    MainActivity.wordlist = MainActivity.wordlist.replaceAll("wordlist:","")
                            .replace("\\\\","\\").replace("{\\","").replace("}","")
                            .replace("\\:",":")
                            .replace(",\\",",");


                    StringBuffer sb = new StringBuffer();
                    Pattern p = Pattern.compile("(?i)\\\\u([\\da-f]{4})");
                    Matcher m = p.matcher(MainActivity.wordlist);
                    while(m.find()) {
                        m.appendReplacement(sb, Character.toString((char)Integer.parseInt(m.group(1), 16)));
                    }
                    m.appendTail(sb);
                    MainActivity.wordlist = sb.toString();
//                    int dix3 = MainActivity.wordlist.indexOf(":");
//                    MainActivity.wordlist = MainActivity.wordlist.substring(0,dix3);
                    Log.i("wordlist",MainActivity.wordlist);
                    resp =  (resp.substring(dix+1).replaceAll("\"| ",""));
                    resp = resp.replaceAll("pic_b64:","");
                    Log.i("dix"+dix,resp);
                    try {
                        File dict = new File("/storage/emulated/0/wordcloud");
                        if(!(dict.exists()))dict.mkdir();
                        else Log.i("wordcloud 文件夹已存在?","存在");

                        BASE64FILE.decoderBase64File(resp,"/storage/emulated/0/wordcloud/wordcloud.jpg");
                        Log.i("解码解码解码",resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

        });

    }

    static void postAsynregister(String json) {
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_1)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//                .build();
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
        RequestBody jsonbody = RequestBody.create(JSON,json);

        final Request request = new Request.Builder()
                .url("http://wc.justjian.site/api/register")
                .post(jsonbody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("failQAQ",e.getMessage());


            }
            String resp = new String();
            @Override
            public void onResponse(Call call, Response response) throws IOException {


                resp = response.body().string();
                Log.i("给点反应!",resp);

                int dix = resp.indexOf("\"code\":");
                if(dix<0){
                    MainActivity.code = "code failed";
                    return ;}
                else {
                    MainActivity.code = resp.substring(dix+1).replaceAll("\"| |\n","").replaceAll("code:","");
                    MainActivity.code = MainActivity.code.substring(0,MainActivity.code.length()-1);
                    dix = MainActivity.code.lastIndexOf("message:");
                    if(dix>=0)MainActivity.code = MainActivity.code.substring(0,dix);
                    Log.i("code",MainActivity.code);
                }


            }

        });

    }

    static void postAsynlogin (String json){
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_1)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//                .build();
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
        RequestBody jsonbody = RequestBody.create(JSON,json);

        final Request request = new Request.Builder()
                .url("http://wc.justjian.site/api/login")
                .post(jsonbody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("failQAQ",e.getMessage());


            }
            String resp = new String();
            @Override
            public void onResponse(Call call, Response response) throws IOException {


                resp = response.body().string();
                Log.i("给点反应!",resp);

                int dix = resp.indexOf("\"code\":");
                if(dix<0){
                    MainActivity.code = "code failed";
                    return ;}
                else {
                    MainActivity.code = resp.substring(dix+1).replaceAll("\"| |\n","").replaceAll("code:","");
                    dix = MainActivity.code.lastIndexOf("message");
                    if(dix>=0){
                        int dix2 = resp.lastIndexOf("message");
                        MainActivity.message = resp.substring(dix2,resp.length()-2)
                                .replaceAll("\"|\n","").replaceAll("message: ","");
                        MainActivity.code = MainActivity.code.substring(0,dix-1);
                        Log.i("message",MainActivity.message);
                    }
                    else{
                        MainActivity.code = MainActivity.code.substring(0,MainActivity.code.length()-1);
                    }
                    Log.i("code",MainActivity.code);
                }


            }

        });
    }

    static void postAsynretrive (String json){
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_1)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//                .build();
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
        RequestBody jsonbody = RequestBody.create(JSON,json);

        final Request request = new Request.Builder()
                .url("http://wc.justjian.site/api/retrive")
                .post(jsonbody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("failQAQ",e.getMessage());


            }
            String resp = new String();
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                resp = response.body().string();
                Log.i("给点反应!",resp);

                int dix = resp.indexOf("\"pic_b64\":");
                if(dix<0)
                    return ;
                else {
                    resp =  (resp.substring(dix+1).replaceAll("\"| ",""));
                    resp = resp.replaceAll("pic_b64:","");
                    Log.i("dix"+dix,resp);
                    try {
                        File dict = new File("/storage/emulated/0/wordcloud");
                        if(!(dict.exists()))dict.mkdir();
                        else Log.i("wordcloud 文件夹已存在?","存在");

                        BASE64FILE.decoderBase64File(resp,"/storage/emulated/0/wordcloud/wordcloud.jpg");
                        Log.i("解码解码解码",resp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

}
