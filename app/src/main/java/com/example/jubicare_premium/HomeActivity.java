package com.example.jubicare_premium;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.jubicare_premium.activity.HealthRecord;
import com.example.jubicare_premium.activity.Prescription;
import com.example.jubicare_premium.activity.Reports;
import com.example.jubicare_premium.activity.TakeAppointment;
import com.example.jubicare_premium.app_drawer.AppDrawer;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.ReportsPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

public class HomeActivity extends AppDrawer {
    CardView cv_appointment,cv_prescription,cv_report,cv_profile;


    String stroa1="12 Sep 2020";
    String stroav1="Dr. Sheetal";
    String stroa2="18 Aug 2020";
    String stroav2="Dr.Anuradha Singh";
    String stroa3="23 Aug 2020";
    String stroav3="Dr. Ashok Agrawal";
    String stroa4="29 Aug 2020";
    String stroav4="Dr. Anuradha Singh";
    String stroa5="29 Feb 2020";
    String stroav5="Dr. Himilka";



    String strre1="12 Sep 2020";
    String strrev1="T3, T4, TSH";
    String strre2="18 Aug 2020";
    String strrev2="Lipid Profile";
    String strre3="23 Aug 2020";
    String strrev3="KFTT, LFT,Uric Acid";
    String strre4="23 Aug 2020";
    String strrev4="Vitamin D3";
    String strre5="29 Jan 2020";
    String strrev5="Complete Blood Count";



   AppointmentPojo appointmentPojo;
   ReportsPojo reportsPojo;
   SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        setTitle("Home");
        getSupportActionBar().hide();

        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
        appointmentPojo=new AppointmentPojo();
        reportsPojo=new ReportsPojo();
        sqliteHelper = new SqliteHelper(this);
        sqliteHelper.openDataBase();

        cv_appointment=findViewById(R.id.cv_appointment);
        cv_profile=findViewById(R.id.cv_profile);
        cv_prescription=findViewById(R.id.cv_prescription);
        cv_report=findViewById(R.id.cv_report);

        cv_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, TakeAppointment.class);
                startActivity(intent);
                finish();
            }
        });
        cv_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointmentPojo.setDate(stroa1);
                appointmentPojo.setDoctor_name(stroav1);
//                appointmentPojo.setDate(strsc2);
//                appointmentPojo.setDoctor_name(strscv2);
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
                Intent intent = new Intent(HomeActivity.this, Reports.class);
                startActivity(intent);
                finish();
            }
        });
        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, HealthRecord.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
