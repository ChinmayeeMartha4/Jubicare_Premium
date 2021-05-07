package com.indev.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  ViewPrescription extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    /*for dynamic inflate layout*/
//    @BindView(R.id.ll_for_dynamic_add)
//    LinearLayout ll_for_dynamic_add;
    public static ScrollView scrollView;
    String view_prescription_url="";
    String commomProfile = "";
    PatientFilledDataModel patientFilledDataModel;
    String profile_id = "";
    SharedPrefHelper sharedPrefHelper;
    ProgressDialog mProgressDialog;
    String patient_appointments_id = "";

    SqliteHelper sqliteHelper;
    String caste_id;
    boolean isEditable = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(this);
        initViews();
        /*get intent here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            profile_id = bundle.getString("profile_patient_id", "");
            view_prescription_url = bundle.getString("url", "");
        }


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(view_prescription_url);

    }


    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(context);
        scrollView = findViewById(R.id.scrollView);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPrescription.this, OldAppointment.class);
        startActivity(intent);
        finish();
    }
}