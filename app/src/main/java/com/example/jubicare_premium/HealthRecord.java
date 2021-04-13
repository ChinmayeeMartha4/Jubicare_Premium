package com.example.jubicare_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class HealthRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Health Record" + "</font>"));
    }
}