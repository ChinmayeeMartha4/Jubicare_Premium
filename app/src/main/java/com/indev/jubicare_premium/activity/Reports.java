package com.indev.jubicare_premium.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.adapter.ReportsAdapter;
import com.indev.jubicare_premium.database.ReportsPojo;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;

public class Reports extends AppCompatActivity {
    ReportsAdapter reportsAdapter;
//    ReportsPojo reportsPojo;
    SqliteHelper sqliteHelper;
    private ArrayList<ReportsPojo> reportsPojo;
    @BindView(R.id.add)
    FloatingActionButton add;
    RecyclerView reports_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Reports" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reportsPojo= sqliteHelper.getReportsData();
        reportsAdapter = new ReportsAdapter(this, reportsPojo);

        reports_recyclerView = (RecyclerView) findViewById(R.id.reports_recyclerView);
        add = (FloatingActionButton) findViewById(R.id.add);


        reports_recyclerView.setHasFixedSize(true);
        reports_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reports_recyclerView.setAdapter(reportsAdapter);



        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(Reports.this, TakeReport.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Reports.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Reports.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
