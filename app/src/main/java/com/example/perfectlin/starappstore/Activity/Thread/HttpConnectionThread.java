package com.example.perfectlin.starappstore.Activity.Thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlowSampleAdapter;
import com.example.perfectlin.starappstore.Activity.Bean.AppBean;
import com.example.perfectlin.starappstore.Activity.Utils.JsonData;
import com.example.perfectlin.starappstore.Activity.Utils.Key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by PERFECTLIN on 2016/7/10.
 */
public class HttpConnectionThread extends Thread {
    private String url;
    public static ArrayList<AppBean> arr_applist = new ArrayList<AppBean>();  //用来存放app信息的数组
    private CoverFlow fancyCoverFlow;
    private Bitmap bitmap;
    private Handler handler;
    public static Bitmap[] bitmaps;

    public HttpConnectionThread(String url, CoverFlow fancyCoverFlow, Handler handler) {
        this.url = url;
        this.fancyCoverFlow = fancyCoverFlow;
        this.handler = handler;
    }

    @Override
    public void run() {
        System.out.println("------------------>Json解析开始");
        dealJson();
        System.out.println("------------------>Json解析完毕");
    }

    private void dealJson() {
        System.out.println("--------------->进入dealJson");
//        try {
//            URL httpsurl = new URL(url);
//            final HttpURLConnection conn = (HttpURLConnection) httpsurl.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setReadTimeout(5000);
//            OutputStream out = conn.getOutputStream();
//            String content = "uKey=" + Key.U_KEY + "&page=" + Key.PAGE + "&_api_key=" + Key.API_KEY;
//            out.write(content.getBytes());
//            final StringBuffer sb = new StringBuffer();
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String str;
//            while ((str = br.readLine()) != null) {
//                sb.append(str);
//            }
//            System.out.println("--------------->读完流");
//            System.out.println("sb--------------->"+sb.toString());
        try {
            System.out.println("--------------->处理对象");

            JSONObject jsonObject = new JSONObject(JsonData.JSONDATA);
            System.out.println("jsonObject--------------->" + jsonObject.toString());

            int resultCode = jsonObject.getInt("code");
            System.out.println("CODE------------->" + resultCode);
            if (resultCode == 0) {

                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray list = data.getJSONArray("list");
                System.out.println("list.length()--------------->" + list.length());
                for (int i = 0; i < list.length(); i++) {
                    AppBean appBean = new AppBean();
                    JSONObject appObject = list.getJSONObject(i);

                    String aKey = appObject.getString("appKey");
                    String appname = appObject.getString("appName");
                    String desc = appObject.getString("appDescription");
                    String icon_url = appObject.getString("appIcon");
                    int appsize = appObject.getInt("appFileSize");

                    appBean.setaKey(aKey);
                    appBean.setName(appname);
                    appBean.setDesc(desc);
                    appBean.setAppSize(appsize);
                    appBean.setIcon_url(icon_url);
                    arr_applist.add(appBean);

                    System.out.println("arr.applist长度------------------>" + arr_applist.size());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setImage();

        handler.post(new Runnable() {
            @Override
            public void run() {
                fancyCoverFlow.setAdapter(new CoverFlowSampleAdapter());
                fancyCoverFlow.setUnselectedAlpha(0.9f);//
                fancyCoverFlow.setUnselectedSaturation(1f);//透明度
                fancyCoverFlow.setUnselectedScale(0.5f);//深度差
                fancyCoverFlow.setSpacing(0);
                fancyCoverFlow.setMaxRotation(0);
                fancyCoverFlow.setScaleDownGravity(0.2f);//高度差
                fancyCoverFlow.setActionDistance(CoverFlow.ACTION_DISTANCE_AUTO);
                fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        });

    }
    private void setImage() {
        System.out.println("------------------>setImage");
        bitmaps = new Bitmap[arr_applist.size()];
        System.out.println("arr_applist------------------>"+arr_applist.size());
        for (int i = 0; i < arr_applist.size(); i++) {

            bitmap = null;

            try {
                URLConnection urlConnection = new URL("http://o1wh05aeh.qnssl.com/image/view/app_icons/" + arr_applist.get(i).getIcon_url()).openConnection();
                System.out.println("url------------------>" + "http://o1wh05aeh.qnssl.com/image/view/app_icons/" + arr_applist.get(i).getIcon_url());
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
        System.out.println("bitmaps长度------------------>" + bitmaps.length);
    }

}
