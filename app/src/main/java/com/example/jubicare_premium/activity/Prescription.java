package com.example.jubicare_premium.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.adapter.OldAppointmentAdapter;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.OldAppointmentPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import java.util.ArrayList;

public class Prescription extends AppCompatActivity {
    OldAppointmentAdapter oldAppointmentAdapter;
    SqliteHelper sqliteHelper;
    AppointmentPojo appointmentPojo;
    private ArrayList<OldAppointmentPojo> oldAppointmentPojo;

    RecyclerView appointment_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointments);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Appointments" + "</font>"));
        appointmentPojo=new AppointmentPojo();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteHelper = new SqliteHelper(this);
        oldAppointmentPojo = sqliteHelper.getAppointementData();
        oldAppointmentAdapter = new OldAppointmentAdapter(this, oldAppointmentPojo);

        appointment_recyclerView = (RecyclerView) findViewById(R.id.appointment_recyclerView);


        appointment_recyclerView.setHasFixedSize(true);
        appointment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appointment_recyclerView.setAdapter(oldAppointmentAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Prescription.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Prescription.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
