package com.example.jubicare_premium.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

public class SplashScreen extends AppCompatActivity {

    SqliteHelper sqliteHelper;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        sqliteHelper=new SqliteHelper(this);
        sharedPrefHelper=new SharedPrefHelper(this);
        sqliteHelper.openDataBase();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPrefHelper.getString("is_login", "").equalsIgnoreCase("")) {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 5000);
    }
}