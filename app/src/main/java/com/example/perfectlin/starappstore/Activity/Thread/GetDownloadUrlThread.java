package com.example.perfectlin.starappstore.Activity.Thread;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by PERFECTLIN on 2016/7/13.
 */
public class GetDownloadUrlThread extends Thread {
    public GetDownloadUrlThread() {
    }

    @Override
    public void run() {
        //用post方式请求app下载地址
        for (int j = 0; j < GetFirJsonThread.firAppListBeanList.size(); j++) {
            URL downloadUrl = null;
            try {
                downloadUrl = new URL("http://download.fir.im/apps/" + GetFirJsonThread.APP_ID[j] + "/install");
                HttpURLConnection conn1 = (HttpURLConnection) downloadUrl.openConnection();
                conn1.setReadTimeout(5000);
                conn1.setRequestMethod("POST");
                OutputStream out = conn1.getOutputStream();
                String content = "download_token=" +GetFirJsonThread.APP_DOWNLOAD_TOKEN[j];
                out.write(content.getBytes());
                BufferedReader br1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                String str1;
                StringBuffer sb1 = new StringBuffer();
                while ((str1 = br1.readLine()) != null) {
                    sb1.append(str1);
                }
                try {
                    JSONObject jsonObject = new JSONObject(sb1.toString());
                    GetFirJsonThread.APP_DOWNLOAD_URL[j] = jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
