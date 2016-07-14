package com.example.perfectlin.starappstore.Activity.services;

import android.content.Context;
import android.content.Intent;

import com.example.perfectlin.starappstore.Activity.Download_db.ThreadDAO;
import com.example.perfectlin.starappstore.Activity.Download_db.ThreadDAOImpl;
import com.example.perfectlin.starappstore.Activity.entities.FileInfo;
import com.example.perfectlin.starappstore.Activity.entities.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * 下载任务类
 * Created by lenovo on 2016/7/11.
 */
public class DownloadTask {
    private Context mContext;
    private FileInfo mfileInfo;
    private ThreadDAO mDao=null;
    private int mFinished=0;
    public boolean isPause=false;

    public DownloadTask(Context mContext, FileInfo fileInfo) {
        this.mContext = mContext;
        this.mfileInfo = fileInfo;
        mDao=new ThreadDAOImpl(mContext);
    }
    public void download(){
        //读取数据库的线程信息
//        List<ThreadInfo> threadInfos=mDao.getThread(mfileInfo.getUrl());
        List<ThreadInfo> threadInfos=mDao.getThread(mfileInfo.getUrl());
        ThreadInfo threadInfo=null;
        if(threadInfos.size()==0){
            //初始化线程信息对象
            threadInfo=new ThreadInfo(mfileInfo.getUrl(),0,mfileInfo.getLength(),0,0);
        }else{
            threadInfo=threadInfos.get(0);
        }
        //创建子线程下载

            new DownloadThread(threadInfo).start();

    }


    //下载线程
    class DownloadThread extends Thread{
        private ThreadInfo mThreadInfo=null;
        public DownloadThread(ThreadInfo mThreadInfo){
            this.mThreadInfo=mThreadInfo;
        }

        @Override
        public void run() {
            //插入线程信息
            if (!mDao.isExists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mDao.insertThread(mThreadInfo);
            }


                HttpURLConnection conn = null;
                RandomAccessFile raf = null;
                InputStream input = null;
                try {
                    URL url = new URL(mThreadInfo.getUrl());
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setRequestMethod("GET");
                    //设置下载位置
                    int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                    conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                    //设置写入位置
                    File file = new File(DownloadService.DOWNLOAD_PATH, mfileInfo.getFileName());
                    raf = new RandomAccessFile(file, "rwd");
                    raf.seek(start);
                    Intent intent = new Intent(DownloadService.ACTION_UPPDATE);

                    mFinished += mThreadInfo.getFinished();//完成的进度
                    System.out.println("333333333333333333暂停完成的进度" + mFinished);
                    //开始下载

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                        //读取数据
                        System.out.println("333333333333333333读取数据");

                        input = conn.getInputStream();
                        byte buffer[] = new byte[1024 * 4];
                        long time = System.currentTimeMillis();//设置刷新时间
                        int len = -1;
                        while ((len = input.read(buffer)) != -1) {
                            //写入文件
                            raf.write(buffer, 0, len);
                            //把下载进度发送广播给Activity
                            mFinished += len;

                            if (System.currentTimeMillis() - time > 500) {
                                time = System.currentTimeMillis();  //设置刷新时间
                                intent.putExtra("finished", mFinished * 100 / mfileInfo.getLength());

                                System.out.println("44444444444444444写入数据");
                                mContext.sendBroadcast(intent);
                                System.out.println("4444444444444444发送数据回Activity");
                            }
                            if (isPause) {
                                System.out.println("777777777777777777暂停写入");
                                mDao.updataThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
                                System.out.println("444444444444444暂停时完成量" + mFinished);
                                Intent intent3 = new Intent(DownloadService.ACTION_STOP);
                                mContext.sendBroadcast(intent3);
                                return;
                            }
                        }
                        System.out.println("44444444444444x下载完成--");
                        int ok = 100;
                        Intent intent2 = new Intent(DownloadService.ACTION_OK);
                        intent2.putExtra("OK", ok);
                        intent2.putExtra("file_name",mfileInfo.getFileName());
                        mContext.sendBroadcast(intent2);
                        System.out.println("===============>删除前线程完成量"+mThreadInfo.getFinished());
                        System.out.println("===============>删除前we文件大小"+mfileInfo.getLength());

                        mDao.delete(mThreadInfo.getUrl(), mThreadInfo.getId());
                        System.out.println("44444444444444x完成删除线程数据");
                    }

                    //  下载暂停，保存下载进度
//                if(isPause){
//                    System.out.println("777777777777777777暂停写入");
//                    mDao.updataThread(mThreadInfo.getUrl(),mThreadInfo.getId(),mFinished);
//                    return;
//                }
                    //删除线程信息
//                mDao.delete(mThreadInfo.getUrl(),mThreadInfo.getId());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.disconnect();
                        raf.close();
                        input.close();
                        System.out.println("88888888888888888结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }

}
