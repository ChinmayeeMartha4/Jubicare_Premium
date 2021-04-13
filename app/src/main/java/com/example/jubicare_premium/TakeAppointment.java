package com.example.jubicare_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class TakeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Take Appointment" + "</font>"));
    }
}