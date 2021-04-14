package com.example.jubicare_premium.activity;

import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jubicare_premium.R;

public class Prescription extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointments);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Appointments" + "</font>"));
    }
}
