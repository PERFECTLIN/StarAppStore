package com.example.perfectlin.starappstore.Activity.Download_db;

import com.example.perfectlin.starappstore.Activity.entities.ThreadInfo;

import java.util.List;

/**
 * 数据库访问接口
 * Created by lenovo on 2016/7/11.
 */
public interface ThreadDAO {
    //插入线程
    public void insertThread(ThreadInfo threadInfo);

    //删除线程
    public void delete(String url, int thread_id);

    //更新线程进度
    public void updataThread(String url, int thread_id, int finished);

    //查询文件的线程信息
    public List<ThreadInfo> getThread(String url);

    //判断线程信息是否存在
    public boolean isExists(String url, int thread_id);
}
