package com.example.perfectlin.starappstore.Activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Thread.GetFirJsonThread;
import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.Activity.entities.FileInfo;
import com.example.perfectlin.starappstore.Activity.services.AppInfoService;
import com.example.perfectlin.starappstore.Activity.services.DownloadService;
import com.example.perfectlin.starappstore.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private CoverFlow fancyCoverFlow;
    private Handler handler = new Handler();
    private TextView tv_name, tv_desc;
    CircularProgressButton download;
    int setup=1;
    ArrayList arrayList;
    SharedPreferences sp=null;
    AppInfoService app=null;
    private  String pipei_name;
    private int pipri_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextView();
        initToolbar(); //初始化toolbar
        initCoverFlow(); //初始化CoverFlow
        initNavigation(); //初始化Navigation
        initDownloadButton();
        regist_broadcast();//注册广播
//        sp=getSharedPreferences("app_list",MODE_PRIVATE);
//        SharedPreferences.Editor editor=sp.edit();

        app=new AppInfoService(MainActivity.this);
//        app.getAppInfos();   //获取app名字
    }

    private void regist_broadcast() {
        //注册广播播放器
        IntentFilter filter=new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPPDATE);
        filter.addAction(DownloadService.ACTION_OK);
        filter.addAction(DownloadService.ACTION_STOP);
        filter.addAction(AppInfoService.APP_NAME);
        registerReceiver(mReceiver,filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }



    private void initDownloadButton() {
       download= (CircularProgressButton) findViewById(R.id.progressbar);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = fancyCoverFlow.getSelectedItemPosition();
                /**
                 *    下面是获取对应的app的url
                 *    String url=GetFirJsonThread.APP_DOWNLOAD_URL[pos];
                 */
                String url=GetFirJsonThread.APP_DOWNLOAD_URL[pos];
                String name=GetFirJsonThread.APP_NAME[pos];
                pipei_name=name;
                pipri_pos=pos;
                app.getAppInfos();   //获取app名字
                if (app.pipei(name)){
                //创建文件信息
                    final FileInfo fileInfo=new FileInfo(0,url,name,0,0);
                    System.out.println("0000000000000000包名"+fileInfo.getFileName());
                    switch (setup){
                        case 1:
                            if(app.pipei(name)) {
                            Intent intent = new Intent(MainActivity.this, DownloadService.class);
                            intent.setAction(DownloadService.ACTION_SARET);
                            intent.putExtra("fileInfo", fileInfo);
                            intent.putExtra("MD5", pos);
                            System.out.println("");
                            download.setText("下载中");
                            setup = setup * -1;
                            startService(intent);//点击下载
                        }
                            else {
                            download.setText("已安装");
                        }
                            break;
                        case -1:
                            Intent intent2 = new Intent(MainActivity.this, DownloadService.class);
                            intent2.setAction(DownloadService.ACTION_STOP);
                            intent2.putExtra("fileInfo", fileInfo);
                            setup = setup * -1;
                            System.out.println("点击暂停下载");
                            startService(intent2);
                            download.setText("继续下载");

                            break;
                        case 2:
                           AppInfoService app2=new AppInfoService(MainActivity.this);
                            app2.getAppInfos();   //获取app名字
                            if(app.pipei(name)) {
//                                setup = 1;
                                System.out.println("点击安装");
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                String filePath = DownloadService.DOWNLOAD_PATH + fileInfo.getFileName() + ".apk";
                                i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                                MainActivity.this.startActivity(i);
                            }
                            else{
                                download.setText("已安装");
                            }
                            break;
                    }

                }else{
                    download.setText("已安装");
                    System.out.println("ppppppppppppppp已安装");
                }
            }
        });
    }
    //更新UI广播播放器
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("--------------------》开始接受返回Activite信息");
            if(DownloadService.ACTION_UPPDATE.equals(intent.getAction())){
                int  finish=intent.getIntExtra("finished",0);
                System.out.println("--------------------》正在接受返回Activite信息");
                download.setProgress(finish);
                System.out.println("======================>下载量"+finish);

            }
            if(DownloadService.ACTION_OK.equals(intent.getAction())){
                int ok=intent.getIntExtra("OK",0);
                System.out.println("======================>最终下载量"+ok);
                String a=intent.getStringExtra("file_name");
                if(ok==100){
                    download.setProgress(ok);
                    download.setText("安装");
                    setup=2; //安装

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String filePath = DownloadService.DOWNLOAD_PATH + a+".apk";
                    i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                    MainActivity.this.startActivity(i);

                }
            }
            if(DownloadService.ACTION_STOP.equals(intent.getAction())){
//                setup=setup * -1;
            }

        }
    };

    private void initTextView() {
        tv_name = (TextView) findViewById(R.id.activity_main_appname);
        tv_desc = (TextView) findViewById(R.id.activity_main_appdesc);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initCoverFlow() {
        setupFlowWidthAndHeight(); //设置CoverFlow的图片大小
        this.fancyCoverFlow = (CoverFlow) findViewById(R.id.activity_main_coverflow);
        new GetFirJsonThread(fancyCoverFlow, handler, this, tv_name, tv_desc).start();  //开启线程解析Json数据和加载CoverFlow图片
        fancyCoverFlow.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            File file = new File(DownloadService.DOWNLOAD_PATH + pipei_name);
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    download.setText("下载");
                    setup=1;
                    download.setProgress(0);
            }
        });


    }

    private void setupFlowWidthAndHeight() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Key.CoverFlow_height = height * 2 / 10;
        Key.CoverFlow_Width = width * 2 / 4;


    }


    private void initNavigation() {
        drawerlayout = (DrawerLayout) findViewById(R.id.activity_main_drawerlayout);
        navigationview = (NavigationView) findViewById(R.id.activity_main_navigationview);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        //设置navi点击回调
        if (navigationview != null) {
            navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    //如果要实现自动化就要循环APP_NAME来
                    if (item.getTitle().equals("星名片")) {
                        drawerlayout.closeDrawers();
                    } else if (item.getTitle().equals("猩印")) {
                        drawerlayout.closeDrawers();
                    } else if (item.getTitle().equals("星忆")) {
                        drawerlayout.closeDrawers();
                    } else if (item.getTitle().equals("星笺")) {
                        drawerlayout.closeDrawers();
                    }
                    return false;
                }
            });
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: //点击菜单打开drawer
                drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.navi_xingmingpian:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置menu菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
//        for (int i=0;i<GetFirJsonThread.APP_NAME.length;i++)
//        {
//            menu.add(0,i,i,GetFirJsonThread.APP_NAME[i]);
//        }
        return super.onCreateOptionsMenu(menu);

    }

}
