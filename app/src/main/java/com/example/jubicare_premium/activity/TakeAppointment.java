package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;

public class TakeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Take Appointment" + "</font>"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TakeAppointment.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}