package com.example.perfectlin.starappstore.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Thread.GetFirJsonThread;
import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private CoverFlow fancyCoverFlow;
    private Handler handler = new Handler();
    private TextView tv_name, tv_desc;
    private MenuItem menuItem;
    private Button bt_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTextView();
        initToolbar(); //初始化toolbar
        initCoverFlow(); //初始化CoverFlow
        initNavigation(); //初始化Navigation
        initDownloadButton();

    }

    private void initDownloadButton() {
        bt_download = (Button) findViewById(R.id.activity_main_download);

        bt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = fancyCoverFlow.getSelectedItemPosition();
                /**
                 *    下面是获取对应的app的url
                 *    String url=GetFirJsonThread.APP_DOWNLOAD_URL[pos];
                 */

            }
        });
    }

    private void initTextView() {
        tv_name = (TextView) findViewById(R.id.activity_main_appname);
        tv_desc = (TextView) findViewById(R.id.activity_main_appdesc);
    }

    private void initCoverFlow() {
        setupFlowWidthAndHeight(); //设置CoverFlow的图片大小
        this.fancyCoverFlow = (CoverFlow) findViewById(R.id.activity_main_coverflow);
        new GetFirJsonThread(fancyCoverFlow, handler, this, tv_name, tv_desc).start();  //开启线程解析Json数据和加载CoverFlow图片
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
