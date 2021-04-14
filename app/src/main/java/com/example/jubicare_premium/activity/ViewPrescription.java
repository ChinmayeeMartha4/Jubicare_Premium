package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;

public class ViewPrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPrescription.this, Prescription.class);
        startActivity(intent);
        finish();
    }
}