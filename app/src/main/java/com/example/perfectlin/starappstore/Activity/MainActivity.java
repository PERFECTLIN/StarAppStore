package com.example.perfectlin.starappstore.Activity;

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
import android.view.WindowManager;

import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Thread.HttpConnectionThread;
import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private CoverFlow fancyCoverFlow;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(); //初始化toolbar
        initNavigation(); //初始化Navigation
        initCoverFlow(); //初始化CoverFlow


    }

    private void initCoverFlow() {
        setupFlowWidthAndHeight();
        this.fancyCoverFlow = (CoverFlow)findViewById(R.id.activity_main_coverflow);

        new HttpConnectionThread(Key.REQUEST_URL,fancyCoverFlow,handler).start();  //开启线程解析Json数据


    }

    private void setupFlowWidthAndHeight() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Key.CoverFlow_height = height * 2 / 8;
        Key.CoverFlow_Width = width * 2 / 4;
    }


    private void initNavigation() {
        drawerlayout = (DrawerLayout) findViewById(R.id.activity_main_drawerlayout);
        navigationview = (NavigationView) findViewById(R.id.activity_main_navigationview);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        //设置navi点击回调
        if (navigationview != null) {
            navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
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
            case android.R.id.home:
                drawerlayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);

    }

}
