package com.example.jubicare_premium.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.adapter.OldAppointmentAdapter;
import com.example.jubicare_premium.database.OldAppointmentPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import java.util.ArrayList;

public class Prescription extends AppCompatActivity {
    OldAppointmentAdapter oldAppointmentAdapter;
    SqliteHelper sqliteHelper;
//    AppointmentPojo appointmentPojo;
    private ArrayList<OldAppointmentPojo> oldAppointmentPojo;

    RecyclerView appointment_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointments);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Appointments" + "</font>"));
//        appointmentPojo=new AppointmentPojo();
//
//        sqliteHelper = new SqliteHelper(this);
//        oldAppointmentPojo = sqliteHelper.getAppointementData();
        oldAppointmentAdapter = new OldAppointmentAdapter(this, oldAppointmentPojo);

        appointment_recyclerView = (RecyclerView) findViewById(R.id.appointment_recyclerView);


//      SchudelelistAdapter adapter = new SchudelelistAdapter(context, ListData);
        appointment_recyclerView.setHasFixedSize(true);
        appointment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appointment_recyclerView.setAdapter(oldAppointmentAdapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Prescription.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
