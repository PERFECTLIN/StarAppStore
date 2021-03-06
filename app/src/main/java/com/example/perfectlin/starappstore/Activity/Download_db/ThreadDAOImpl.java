package com.example.perfectlin.starappstore.Activity.Download_db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.perfectlin.starappstore.Activity.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问接口实现
 * Created by lenovo on 2016/7/11.
 */
public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper mHelper=null;
    public ThreadDAOImpl(Context context){
        mHelper=new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished)values(?,?,?,?,?)",
        new Object[]{threadInfo.getId(),threadInfo.getUrl(),threadInfo.getStart(),threadInfo.getEnd(),threadInfo.getFinished()});
        db.close();
    }

    @Override
    public void delete(String url, int thread_id) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
             new Object[]{url,thread_id});
        db.close();
    }

    @Override
    public void updataThread(String url, int thread_id, int finished) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("update  thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished,url,thread_id});

        db.close();
    }

    @Override
    public List<ThreadInfo> getThread(String url) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        List<ThreadInfo> list=new ArrayList<>();

       Cursor cursor= db.rawQuery("select  * from thread_info where url = ?",
                new String []{url});
        while(cursor.moveToNext()){   //光标移到下一行
            ThreadInfo thread=new ThreadInfo();
            thread.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));//返回指定列的名称
            thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            thread.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            thread.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            thread.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(thread);
        }
        cursor.close();
        db.close();


        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db=mHelper.getWritableDatabase();

        Cursor cursor= db.rawQuery("select  * from thread_info where url = ? and thread_id = ?",
                new String []{url,thread_id+""});
     boolean exists=cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
