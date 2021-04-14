package com.example.jubicare_premium.app_drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jubicare_premium.R;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class AppDrawer  extends AppCompatActivity {
    public View view;
    public FrameLayout frame;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public ActionBarDrawerToggle drawerToggle;
    public Menu menu;
    public Menu nav_menu;
    public TextView tv_name, tv_email;
    public ImageView iv_imageView;
    public TextView tvTitle;
    public RelativeLayout rl_bell_icon;
    public ImageView iv_notification;
    public TextView tv_notification_count;
    Context context = this;
    ProgressDialog mProgressDialog;
    SqliteHelper sqliteHelper;

    String filePath = null;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        view = getLayoutInflater().inflate(R.layout.activity_app_drawer, null);
        frame = view.findViewById(R.id.frame);
        getLayoutInflater().inflate(layoutResID, frame, true);
        super.setContentView(view);

        drawer = findViewById(R.id.drawer);
        iv_imageView = findViewById(R.id.iv_imageView);
        navigationView = findViewById(R.id.navigationView);
        tvTitle = findViewById(R.id.tvTitle);

        sqliteHelper = new SqliteHelper(this);
        mProgressDialog = new ProgressDialog(this);


        //make condition for admin and user
        navigationView.inflateMenu(R.menu.user_menu_list);
        menu = navigationView.getMenu();
        /*hide and show navigation items*/


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        View header = navigationView.getHeaderView(0);

        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        tv_email = (TextView) header.findViewById(R.id.tv_headerSubName);
        iv_imageView = (ImageView) header.findViewById(R.id.iv_imageView);

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(false);//when using our custom drawer icon
        drawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();


        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer);

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });



                nav_menu = navigationView.getMenu();

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        drawer.closeDrawers();
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.option_home:

                            case R.id.option_change_password:

                            case R.id.option_notifications:

                            case R.id.option_aboutUs:


                            case R.id.option_settings:

                            case R.id.option_Contact_us:

                            case R.id.option_logout:

                        }
                        return true;
                    }
                });
            }


        }