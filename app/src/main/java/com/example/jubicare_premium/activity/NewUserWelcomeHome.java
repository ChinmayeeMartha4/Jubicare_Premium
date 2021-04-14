package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;

public class NewUserWelcomeHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_welcome_home);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewUserWelcomeHome.this, NewUserHome.class);
        startActivity(intent);
        finish();
    }
}