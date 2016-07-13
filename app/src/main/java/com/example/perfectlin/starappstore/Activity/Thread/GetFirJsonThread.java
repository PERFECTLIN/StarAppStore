package com.example.perfectlin.starappstore.Activity.Thread;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlowSampleAdapter;
import com.example.perfectlin.starappstore.Activity.Bean.FirAppBean;
import com.example.perfectlin.starappstore.Activity.Bean.FirAppListBean;
import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by PERFECTLIN on 2016/7/12.
 */
public class GetFirJsonThread extends Thread {
    private CoverFlow fancyCoverFlow;
    private Handler handler;
    private Bitmap bitmap;
    private Context context;
    private TextView tv_name, tv_desc;
    private FirAppBean fb;


    public static String[] APP_ID;
    public static Bitmap[] bitmaps;
    public static List<FirAppListBean> firAppListBeanList;
    public static List<FirAppBean> firAppBeanList;
    public static String[] APP_NAME, APP_DESC, APP_DOWNLOAD_TOKEN, APP_DOWNLOAD_URL;

    public GetFirJsonThread(CoverFlow fancyCoverFlow, Handler handler, Context context, TextView tv_name, TextView tv_desc) {
        this.fancyCoverFlow = fancyCoverFlow;
        this.handler = handler;
        this.context = context;
        this.tv_name = tv_name;
        this.tv_desc = tv_desc;
    }

    @Override
    public void run() {
        dealJson(); //开始解析Json
        dealDownloadJson();
    }

    private void dealDownloadJson() {
        APP_DOWNLOAD_TOKEN = new String[firAppListBeanList.size()];
        for (int i = 0; i < firAppListBeanList.size(); i++) {
            try {
                URL downloadTokenUrl = new URL(Key.FIR_GET_DOWNLOAD_TOKEN_URL + firAppListBeanList.get(i).getId() + "/download_token?api_token=" + Key.FIR_API_TOKEN);
                HttpURLConnection conn = (HttpURLConnection) downloadTokenUrl.openConnection();
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while ((str = br.readLine()) != null) {
                        sb.append(str);
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(sb.toString());
                        APP_DOWNLOAD_TOKEN[i] = jsonObject.getString("download_token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //开启线程更新UI
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "下载连接网络请求异常", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //用post方式请求app下载地址
                APP_DOWNLOAD_URL=new String[firAppListBeanList.size()];
                for (int j = 0; j < firAppListBeanList.size(); j++) {
                    URL downloadUrl = new URL("http://download.fir.im/apps/" + APP_ID[j] + "/install");
                    HttpURLConnection conn1 = (HttpURLConnection) downloadUrl.openConnection();
                    conn1.setReadTimeout(5000);
                    conn1.setRequestMethod("POST");
                    OutputStream out = conn1.getOutputStream();
                    String content = "download_token=" + APP_DOWNLOAD_TOKEN[j];
                    out.write(content.getBytes());
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                    String str1;
                    StringBuffer sb1 = new StringBuffer();
                    while ((str1 = br1.readLine()) != null) {
                        sb1.append(str1);
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(sb1.toString());
                        APP_DOWNLOAD_URL[j] = jsonObject.getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dealJson() {

        URL appListUrl = null;
        try {
            appListUrl = new URL(Key.FIR_APPLIST_GET_API);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) appListUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                StringBuffer sb = new StringBuffer();
                while ((str = bf.readLine()) != null) {
                    sb.append(str); //将返回数据储存在StringBuffer里
                }

                //用Gson解析Json
                Gson gson = new Gson();
                Type type = new TypeToken<List<FirAppListBean>>() {
                }.getType();
                firAppListBeanList = gson.fromJson(sb.toString(), type);

                addBitmap();  //将appIcon放进Bitmap数组
                getAppName();//获取App的名字用于加载进NavigationView
                getAppDesc(); //获取App的描述

                //开启线程更新UI
                handler.post(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        setFancyCoverFlow();

                        Toast.makeText(context, "加载成功", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                //开启线程更新UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void setFancyCoverFlow() {
        fancyCoverFlow.setAdapter(new CoverFlowSampleAdapter(tv_name, tv_desc));
        fancyCoverFlow.setUnselectedAlpha(0.9f);//
        fancyCoverFlow.setUnselectedSaturation(1f);//透明度
        fancyCoverFlow.setUnselectedScale(0.5f);//深度差
        fancyCoverFlow.setSpacing(0);
        fancyCoverFlow.setMaxRotation(0);
        fancyCoverFlow.setScaleDownGravity(0.2f);//高度差
        fancyCoverFlow.setActionDistance(CoverFlow.ACTION_DISTANCE_AUTO);
        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = APP_NAME[i];
                String desc = APP_DESC[i];
                tv_name.setText(name);
                tv_desc.setText(desc);
                Toast.makeText(context, "---->" + fancyCoverFlow.getId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void addBitmap() {
        bitmaps = new Bitmap[firAppListBeanList.size()];
        for (int i = 0; i < firAppListBeanList.size(); i++) {

            bitmap = null;

            try {
                URLConnection urlConnection = new URL(firAppListBeanList.get(i).getIcon_url()).openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedInputStream bf = new BufferedInputStream(is);

                bitmap = BitmapFactory.decodeStream(bf);

                bitmaps[i] = bitmap;

                is.close();
                bf.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAppName() {
        APP_NAME = new String[firAppListBeanList.size()];
        for (int i = 0; i < firAppListBeanList.size(); i++) {
            APP_NAME[i] = firAppListBeanList.get(i).getName();
        }
    }

    public void getAppDesc() {
        APP_ID = new String[firAppListBeanList.size()];
        for (int i = 0; i < firAppListBeanList.size(); i++) {
            APP_ID[i] = firAppListBeanList.get(i).getId();
        }

        APP_DESC = new String[firAppListBeanList.size()];
        dealAppJson();//处理应用的Json信息
    }

    private void dealAppJson() {
        for (int i = 0; i < firAppListBeanList.size(); i++) {
            try {
                String url = Key.FIR_APP_GET_API + APP_ID[i] + "?api_token=" + Key.FIR_API_TOKEN;
                System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmurl" + url);
                URL firAppUrl = new URL(Key.FIR_APP_GET_API + APP_ID[i] + "?api_token=" + Key.FIR_API_TOKEN);
                HttpURLConnection conn = (HttpURLConnection) firAppUrl.openConnection();
                conn.setRequestMethod("GET");
                System.out.println("mmmmmmmmmmmmmmmmCODE" + conn.getResponseCode());
                if (conn.getResponseCode() == 200) {

                    BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String str;
                    StringBuffer sb1 = new StringBuffer();
                    while ((str = bf.readLine()) != null) {
                        sb1.append(str); //将返回数据储存在StringBuffer里
                    }


                    try {
                        //取出app对象的app描述
                        JSONObject jsonObject = new JSONObject(sb1.toString());
                        String desc = jsonObject.getString("desc");
                        APP_DESC[i] = desc;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("mmmmmmmmmmmmmmmm" + APP_DESC[i]);
                } else {
                    //开启线程更新UI
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}



