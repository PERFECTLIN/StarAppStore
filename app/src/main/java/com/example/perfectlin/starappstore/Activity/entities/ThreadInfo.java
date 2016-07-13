package com.example.perfectlin.starappstore.Activity.entities;

/**
 * Created by lenovo on 2016/7/10.
 */
public class ThreadInfo {
    private String url;
    private int id;
    private int start;
    private int end;
    private static int finished;

    public ThreadInfo() {
    }

    public ThreadInfo(String url, int finish, int end, int id, int start) {
        this.url = url;
        this.finished = finish;
        this.end = end;
        this.id = id;
        this.start = start;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finish) {
        this.finished = finish;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "url='" + url + '\'' +
                ", id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", finish=" + finished +
                '}';
    }
}
