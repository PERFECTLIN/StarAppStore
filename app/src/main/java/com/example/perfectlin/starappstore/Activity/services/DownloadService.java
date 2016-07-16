package com.example.perfectlin.starappstore.Activity.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.Activity.entities.FileInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;


/**
 * Created by lenovo on 2016/7/10.
 */
public class DownloadService extends Service {
    //路径
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/StarAppStore/";

    //标识符
    public static final String ACTION_SARET = "ACTION_SARET";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPPDATE = "ACTION_UPPDATE";
    public static final int MSG_INIT = 0;
    public static final String ACTION_OK = "ACTION_OK";
    private DownloadTask mTask = null;
//    SharedPreferences sp;
    //获取从Activity传来的数据值


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_SARET.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            System.out.println("------------start" + fileInfo);
            File file = new File(DOWNLOAD_PATH + fileInfo.getFileName());
//            String a = getFileMD5(file);
//            System.out.println("qqqqqqqqqqqqqqq开始的MD5"+a);
            int MD5_position= (int) intent.getSerializableExtra("MD5");
//            System.out.println("00000000000000000Service包名" +fileInfo.getFileName());
            System.out.println("00000000000000000完整包名" +( Key.APP_NAME[MD5_position]+".apk"));
//            String b=Key.APP_SHUZU[MD5_position];
            if (fileInfo.getFileName()!=(Key.APP_NAME[MD5_position]+".apk")) {
                //启动初始化线程
                new InitThread(fileInfo).start();
            } else {
                int ok = 100;
                Intent intent2 = new Intent(DownloadService.ACTION_OK);
                intent2.putExtra("OK", ok);
                intent2.putExtra("file_name", fileInfo.getFileName());
                this.sendBroadcast(intent2);
            }

        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            System.out.println("------------end" + fileInfo);
            if (mTask != null) {
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    System.out.println("--------->发回的文件数据" + fileInfo);

                    //启动下载任务
                    mTask = new DownloadTask(DownloadService.this, fileInfo);
                    mTask.download();
                    break;
            }

        }
    };


    //初始化线程
    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if (conn.getResponseCode() == 200) {
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                //在本地创建文件保存
                //判断是否存在
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");//随机访问

                //设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);

//                sp=getSharedPreferences("app_length",MODE_PRIVATE);
//                SharedPreferences.Editor editor=sp.edit();
//                editor.putInt("length",length);
//                editor.commit();
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                    conn.disconnect();//关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
