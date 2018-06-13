package com.my.tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenFileDialog {
    public static String tag = "OpenFileDialog";
    static Bundle bundle = new Bundle();
    static final public String sRoot = Environment.getRootDirectory().toString();//获取手机根目录

    static final public String sParent = "..";
    static final public String sFolder = ".";
    static final public String sEmpty = "";
    static final private String sOnErrorMsg = "No rights to access!";



    public static String [] str = new String[2];
    public static boolean [] etn = new boolean[3];
    public static String json;


    // 参数说明
    // context:上下文
    // dialogid:对话框ID
    // title:对话框标题
    // callback:一个传递Bundle参数的回调接口
    // suffix:需要选择的文件后缀，比如需要选择mp3文件的时候设置为".mp3;"，注意最后需要一个分号(;)
    // images:用来根据后缀显示的图标资源ID。
    //  根目录图标的索引为sRoot;
    //  父目录的索引为sParent;
    //  文件夹的索引为sFolder;
    //  默认图标的索引为sEmpty;
    //  其他的直接根据后缀进行索引，比如.txt文件图标的索引为"txt"
    //文件选择View，继承ListView
    static class FileSelectView extends ListView implements OnItemClickListener{


        private CallbackBundle callback = null;
        public String path = sRoot;
        private List<Map<String, Object>> list = null;
        private int dialogid = 0;

        private String suffix = null;

        private Map<String, Integer> imagemap = null;

        public FileSelectView(Context context, int dialogid, CallbackBundle callback, String suffix, Map<String, Integer> images) {
            super(context);
            this.imagemap = images;
            this.suffix = suffix==null?"":suffix.toLowerCase();
            this.callback = callback;

            this.dialogid = dialogid;
            this.setOnItemClickListener(this);
            refreshFileList();
        }

        private String getSuffix(String filename){
            int dix = filename.lastIndexOf('.');//搜索'.'最后出现的位置
            if(dix<0){
                return "";
            }
            else{
                return filename.substring(dix+1);//截掉dix+1个字符，剩下的就是格式
            }
        }

        private int getImageId(String s){
            if(imagemap == null){
                return 0;
            }
            else if(imagemap.containsKey(s)){
                return imagemap.get(s);
            }
            else if(imagemap.containsKey(sEmpty)){
                return imagemap.get(sEmpty);
            }
            else {
                return 0;
            }
        }

        private int refreshFileList()
        {
            // 刷新文件列表
            File[] files = null;
            try{
                files = new File(path).listFiles();
            }
            catch(Exception e){
                files = null;
            }
            if(files==null){
                // 访问出错
                Toast.makeText(getContext(), sOnErrorMsg,Toast.LENGTH_SHORT).show();
                return -1;
            }
            if(list != null){
                list.clear();
            }
            else{
                list = new ArrayList<Map<String, Object>>(files.length);
            }

            // 用来先保存文件夹和文件夹的两个列表
            ArrayList<Map<String, Object>> lfolders = new ArrayList<Map<String, Object>>();
            ArrayList<Map<String, Object>> lfiles = new ArrayList<Map<String, Object>>();

            if(!this.path.equals(sRoot)){
                // 添加根目录 和 上一层目录
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", sRoot);
                map.put("path", sRoot);
                map.put("img", getImageId(sRoot));
                list.add(map);

                map = new HashMap<String, Object>();
                map.put("name", sParent);
                map.put("path", path);
                map.put("img", getImageId(sParent));
                list.add(map);
            }

            for(File file: files)
            {
                if(file.isDirectory() && file.listFiles()!=null){
                    // 添加文件夹
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", file.getName());
                    map.put("path", file.getPath());
                    map.put("img", getImageId(sFolder));
                    lfolders.add(map);
                }
                else if(file.isFile()){
                    // 添加文件
                    String sf = getSuffix(file.getName()).toLowerCase();
                    if(suffix == null || suffix.length()==0 || (sf.length()>0 && suffix.indexOf("."+sf+";")>=0)){
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("name", file.getName());
                        map.put("path", file.getPath());
                        map.put("img", getImageId(sf));
                        lfiles.add(map);
                    }
                }
            }

            list.addAll(lfolders); // 先添加文件夹，确保文件夹显示在上面
            list.addAll(lfiles);    //再添加文件


            SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.filedialog, new String[]{"img", "name", "path"}, new int[]{R.id.filedialogitem_img, R.id.filedialogitem_name, R.id.filedialogitem_path});
            this.setAdapter(adapter);
            return files.length;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            // 条目选择
            String pt = (String) list.get(position).get("path");
            String fn = (String) list.get(position).get("name");
            if(fn.equals(sRoot) || fn.equals(sParent)){
                // 如果是更目录或者上一层
                File fl = new File(pt);
                String ppt = fl.getParent();
                if(ppt != null){
                    // 返回上一层
                    path = ppt;
                }
                else{
                    // 返回更目录
                    path = sRoot;
                }
            }
            else{
                File fl = new File(pt);
                if(fl.isFile()){
                    // 如果是文件
                    Toast.makeText(getContext(),pt,Toast.LENGTH_SHORT).show();

                    // 设置回调的返回值


                    bundle.putString("path", pt);
                    bundle.putString("name", fn);

                    // 调用事先设置的回调函数
                    this.callback.callback(bundle);
                    return;
                }
                else if(fl.isDirectory()){
                    // 如果是文件夹
                    // 那么进入选中的文件夹
                    path = pt;
                }
            }
            this.refreshFileList();
        }


    }



    public static Dialog createDialog(final int id, final Context context, String title, final CallbackBundle callback, final String suffix, Map<String, Integer> images){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final FileSelectView fileselectview = new FileSelectView(context, id, callback, suffix, images);
        builder.setView(fileselectview);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //File file = new File(bundle.getString("path"));
                Toast.makeText(context, "确认选择"+bundle.getString("name"), Toast.LENGTH_SHORT).show();
                dialog.cancel();
                if (bundle!=bundle.EMPTY)
                {str[id] = bundle.getString("path");
                    etn[id] = true;
                    OpenFileDialog.bundle.clear();}



                if(etn[0]&&etn[1] ){//选择本地图片
                    Toast.makeText(context, "选择了本地图片和文档,点击右上角按钮生成图片", Toast.LENGTH_SHORT).show();
                    File file3 = new File(str[0]);
                    File file4 = new File(str[1]);
                    File file5 ;
                    file5 = new File("/storage/emulated/0/wordcloud/wordcloud.jpg");
                    if(file5.exists())file5.delete();

                    try {
                        json = Json.jsoncreate(file3,file4);
                    } catch (IOException e) {
                        e.printStackTrace();
                        json = "";
                    }
                    Log.i("json",json);
                }

                if(etn[0]&&MainActivity.textflag || etn[0]&&MainActivity.soundflag){
                    File file3 = new File(str[0]);
                    File file5 ;
                    file5 = new File("/storage/emulated/0/wordcloud/wordcloud.jpg");
                    if(file5.exists())file5.delete();

                    if(MainActivity.textflag){
                    try{
                        json = Json.jsoncreatetext(file3,MainActivity.text);
                    }catch (IOException e) {
                        e.printStackTrace();
                        json = "";
                    }}
                    if(MainActivity.soundflag){
                        try{
                            json = Json.jsoncreatetext(file3,MainActivity.soundtext);
                        }catch (IOException e) {
                            e.printStackTrace();
                            json = "";
                        }
                    }
                    Log.i("json",json);
                }




            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context,fileselectview.path,Toast.LENGTH_SHORT).show();
            }
        });
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog dialog = builder.create();
        dialog.setTitle(title);
        return dialog;
    }

}