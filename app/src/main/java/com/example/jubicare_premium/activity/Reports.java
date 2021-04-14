package com.example.jubicare_premium.activity;

import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.AppointmentPojo;

public class Reports extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Reports" + "</font>"));
    }
}
