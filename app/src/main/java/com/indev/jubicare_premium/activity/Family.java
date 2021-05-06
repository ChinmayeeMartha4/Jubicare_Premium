package com.indev.jubicare_premium.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.indev.jubicare_premium.HomeActivity;

public class Family extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        setTitle("Home");
        getSupportActionBar().hide();

        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Family" + "</font>"));

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
