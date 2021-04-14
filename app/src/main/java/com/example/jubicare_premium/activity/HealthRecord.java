package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;

public class HealthRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Health Record" + "</font>"));

    }
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(HealthRecord.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

}