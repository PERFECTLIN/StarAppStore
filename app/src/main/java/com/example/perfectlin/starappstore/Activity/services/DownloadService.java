package com.example.perfectlin.starappstore.Activity.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.perfectlin.starappstore.Activity.entities.FileInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by lenovo on 2016/7/10.
 */
public class DownloadService extends Service {
    //路径
    public static final String DOWNLOAD_PATH=
            Environment.getExternalStorageDirectory().getAbsolutePath()+"/StarAppStore/";

    //标识符
    public static final String ACTION_SARET="ACTION_SARET";
    public static final String ACTION_STOP="ACTION_STOP";
    public static final String ACTION_UPPDATE="ACTION_UPPDATE";
    public static final  int MSG_INIT=0;
    public static final String ACTION_OK="ACTION_OK";
    private DownloadTask mTask=null;

    //获取从Activity传来的数据值
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(ACTION_SARET.equals(intent.getAction())){
            FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileInfo");
            System.out.println("------------start"+fileInfo);
            File file=new File(DOWNLOAD_PATH+fileInfo.getFileName());
            if(!file.exists()) {
                //启动初始化线程
                new InitThread(fileInfo).start();
            }
            else {
                int ok=100;
                Intent intent2 = new Intent(DownloadService.ACTION_OK);
                intent2.putExtra("OK", ok);
                intent2.putExtra("file_name",fileInfo.getFileName());
                this.sendBroadcast(intent2);
            }

        }else if(ACTION_STOP.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            System.out.println("------------end"+fileInfo);
            if(mTask!=null){
                mTask.isPause=true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_INIT:
                    FileInfo fileInfo= (FileInfo) msg.obj;
                    System.out.println("--------->发回的文件数据"+fileInfo);

                    //启动下载任务
                    mTask=new DownloadTask(DownloadService.this,fileInfo);
                    mTask.download();
                    break;
            }

        }
    };


    //初始化线程
    class InitThread extends Thread{
        private FileInfo mFileInfo;
        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn=null;
            RandomAccessFile raf=null;
            try {
                //连接网络文件
                URL url=new URL(mFileInfo.getUrl());
                conn= (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length=-1;
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    length=conn.getContentLength();
                }
                if(length<=0){
                    return;
                }
                //在本地创建文件保存
                     //判断是否存在
                File dir=new File(DOWNLOAD_PATH);
                if (!dir.exists()){
                    dir.mkdir();
                }
                File file=new File(dir,mFileInfo.getFileName());
                 raf=new RandomAccessFile(file,"rwd");//随机访问

                //设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT,mFileInfo).sendToTarget();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    raf.close();
                    conn.disconnect();//关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
