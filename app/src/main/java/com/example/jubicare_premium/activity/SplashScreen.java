package com.example.jubicare_premium.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.activity.datadownload.DataDownload;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity {

    SqliteHelper sqliteHelper;
    SharedPrefHelper sharedPrefHelper;
    private final int SPLASH_DISPLAY_LENGTH = 3000; //3 seconds
Context context =this;
String splashLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        sqliteHelper = new SqliteHelper(this);
        sqliteHelper.openDataBase();
        sharedPrefHelper = new SharedPrefHelper(this);
        getPermissionLocation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent intent1 = new Intent(context, WebViewDownload.class);
                //  startActivity(intent1);
                //  finish();
                String role_id = sharedPrefHelper.getString("role_id", "");
                splashLoaded = sharedPrefHelper.getString("isSplashLoaded", "No");
                if (splashLoaded.equals("")) {
                    DataDownload dataDownload = new DataDownload();
                    dataDownload.getMasterTables(SplashScreen.this);
                } else {
                    if (role_id.equalsIgnoreCase("")) {
                        Intent intent = new Intent(context, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void getPermissionLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                        Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        }
    }
}