package com.example.perfectlin.starappstore.Activity.entities;

import android.graphics.drawable.Drawable;

/**
 * Created by lenovo on 2016/7/13.
 */
public class AppInfo {
    //图标
    private Drawable app_icon;
    //应用名称
    private String app_name;
    //应用版本号
    private String app_version;
    //应用包名
    private String packagename;


    public AppInfo(Drawable app_icon, String packagename, String app_version, String app_name) {
        this.app_icon = app_icon;
        this.packagename = packagename;
        this.app_version = app_version;
        this.app_name = app_name;
    }

    public AppInfo() {
        super();
    }

    public Drawable getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }
}
