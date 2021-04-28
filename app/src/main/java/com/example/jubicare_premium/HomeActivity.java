package com.example.jubicare_premium;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.jubicare_premium.activity.HealthRecord;
import com.example.jubicare_premium.activity.PatientFillAppointment;
import com.example.jubicare_premium.activity.Prescription;
import com.example.jubicare_premium.activity.Reports;
import com.example.jubicare_premium.activity.TakeAppointment;
import com.example.jubicare_premium.app_drawer.AppDrawer;
import com.example.jubicare_premium.database.OldAppointmentPojo;
import com.example.jubicare_premium.database.ReportsPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import java.util.ArrayList;

public class HomeActivity extends AppDrawer {
    CardView cv_appointment,cv_prescription,cv_report,cv_profile;

    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();

    String stroa1="12 Sep 2020";
    String stroav1="Dr. Sujinder Phogat";




    String strre1="12 Sep 2020";
    String strrev1="T3, T4, TSH";



   OldAppointmentPojo oldAppointmentPojo;
   ReportsPojo reportsPojo;
   SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        setTitle("Home");
        getSupportActionBar().hide();

        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
        oldAppointmentPojo =new OldAppointmentPojo();
        reportsPojo=new ReportsPojo();
        sqliteHelper = new SqliteHelper(this);

        cv_appointment=findViewById(R.id.cv_appointment);
        cv_profile=findViewById(R.id.cv_profile);
        cv_prescription=findViewById(R.id.cv_prescription);
        cv_report=findViewById(R.id.cv_report);

        cv_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, PatientFillAppointment.class);
                startActivity(intent);
                finish();
            }
        });
        cv_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldAppointmentPojo.setDate(stroa1);
                oldAppointmentPojo.setDoctor_name(stroav1);
//                appointmentPojo.setDate1(stroa2);
//                appointmentPojo.setDoctor_name1(stroav2);
//                sqliteHelper.saveAppointmentList(oldAppointmentPojo);

                Intent intent = new Intent(HomeActivity.this, Prescription.class);
                startActivity(intent);
                finish();
            }
        });


        cv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportsPojo.setDate(strre1);
                reportsPojo.setTitle(strrev1);
//                reportsPojo.setDate(strre2);
//                reportsPojo.setDoctor_name(strrev1);
                sqliteHelper.saveReportList(reportsPojo);

                Intent intent = new Intent(HomeActivity.this, Reports.class);
                startActivity(intent);
                finish();
            }
        });
        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, HealthRecord.class);
                intent.putExtra("commomProfile", "commomProfile");
                intent.putExtra("profile_id", "1");
                startActivity(intent);
                finish();
//                startActivity(intent);
//                finish();

            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Alert!");
        builder.setMessage("Are you sure to want to exit application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
//        Intent intent = new Intent(HomeActivity.this, Login.class);
//        startActivity(intent);
//        finish();
//    }
}
