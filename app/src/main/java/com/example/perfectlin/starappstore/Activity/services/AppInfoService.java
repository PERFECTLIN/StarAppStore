package com.example.perfectlin.starappstore.Activity.services;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.perfectlin.starappstore.Activity.entities.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/13.
 */
public class AppInfoService {
    private Context context;
    private PackageManager pm;
    public static final String APP_NAME="APP_NAME";
    public static boolean heshi;
    String appName;
    ArrayList arrayList=new ArrayList();
    String name_shuzu[];

    public AppInfoService(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.appName=appName;

        pm = context.getPackageManager();
    }

    public boolean pipei(String appName){
        this.appName=appName;
        for(int i=0;i<arrayList.size();i++){
            if(appName.equals(arrayList.get(i))){
                return false;

            }
        }
            return true;

    }

    /**
     * 得到手机中所有的应用程序信息
     *
     * @return
     */
    public List<AppInfo> getAppInfos() {
        //创建要返回的集合对象
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        //获取手机中所有安装的应用集合
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        //遍历所有的应用集合
        for (ApplicationInfo info : applicationInfos) {

            AppInfo appInfo = new AppInfo();


            //获取应用程序的图标
            Drawable app_icon = info.loadIcon(pm);
            appInfo.setApp_icon(app_icon);

            //获取应用的名称
            String app_name = info.loadLabel(pm).toString();
            appInfo.setApp_name(app_name);

            //获取应用的包名
            String packageName = info.packageName;
            appInfo.setPackagename(packageName);
            try {
                //获取应用的版本号
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                String app_version = packageInfo.versionName;
                appInfo.setApp_version(app_version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        System.out.println("=====================>应用"+ app_name);
            arrayList.add(app_name);
//            if(app_name.equals(appName)){
//                heshi=false;
//                break;
//            }
        }
        return arrayList;

    }
}
