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
import com.example.jubicare_premium.adapter.ReportsAdapter;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.ReportsPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {
    ReportsAdapter reportsAdapter;
//    ReportsPojo reportsPojo;
    SqliteHelper sqliteHelper;
    private ArrayList<ReportsPojo> reportsPojo;

    RecyclerView reports_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Reports" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        reportsPojo= sqliteHelper.getReportsData();
        reportsAdapter = new ReportsAdapter(this, reportsPojo);

        reports_recyclerView = (RecyclerView) findViewById(R.id.reports_recyclerView);


//      SchudelelistAdapter adapter = new SchudelelistAdapter(context, ListData);
        reports_recyclerView.setHasFixedSize(true);
        reports_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reports_recyclerView.setAdapter(reportsAdapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Reports.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
