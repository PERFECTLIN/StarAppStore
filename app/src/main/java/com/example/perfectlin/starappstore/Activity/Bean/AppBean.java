package com.example.perfectlin.starappstore.Activity.Bean;

/**
 * Created by PERFECTLIN on 2016/7/11.
 */
public class AppBean {
    private String aKey;
    private String name;
    private String desc;
    private String icon_url;
    private String download_url;
    private int appSize;

    public int getAppSize() {
        return appSize;
    }

    public String getaKey() {
        return aKey;
    }

    public void setaKey(String aKey) {
        this.aKey = aKey;
    }

    public void setAppSize(int appSize) {
        this.appSize = appSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
