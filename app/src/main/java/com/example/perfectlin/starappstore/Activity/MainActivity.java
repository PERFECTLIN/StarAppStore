package com.example.perfectlin.starappstore.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.widget.AdapterView;

import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlow;
import com.example.perfectlin.starappstore.Activity.Adapter.CoverFlowSampleAdapter;
import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private CoverFlow fancyCoverFlow;

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
        this.fancyCoverFlow.setAdapter(new CoverFlowSampleAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(0.9f);//
        this.fancyCoverFlow.setUnselectedSaturation(1f);//透明度
        this.fancyCoverFlow.setUnselectedScale(0.5f);//深度差
        this.fancyCoverFlow.setSpacing(0);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);//高度差
        this.fancyCoverFlow.setActionDistance(CoverFlow.ACTION_DISTANCE_AUTO);
        this.fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setupFlowWidthAndHeight() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Key.CoverFlow_height = height * 2 / 4;
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
