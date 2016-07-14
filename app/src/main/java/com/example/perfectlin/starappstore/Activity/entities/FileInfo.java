package com.example.perfectlin.starappstore.Activity.entities;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/7/10.
 */
public class FileInfo implements Serializable{
    private int id;
    private String url;
    private String FileName;
    private static  int length;
    private int finish;

    public FileInfo() {
    }

    public FileInfo(int id, String url, String fileName, int length, int finish) {
        this.id = id;
        this.url = url;
        FileName = fileName;
        this.length = length;
        this.finish = finish;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getId() {

        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return FileName;
    }

    public int getFinish() {
        return finish;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", FileName='" + FileName + '\'' +
                ", length=" + length +
                ", finish=" + finish +
                '}';
    }
}
