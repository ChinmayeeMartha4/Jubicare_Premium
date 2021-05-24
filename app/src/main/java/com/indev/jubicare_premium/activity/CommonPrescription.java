package com.indev.jubicare_premium.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.OldPrescriptionPojo;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
import com.indev.jubicare_premium.database.PrescriptionModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonPrescription extends AppCompatActivity {

    /*for dynamic inflate layout*/
//    @BindView(R.id.ll_for_dynamic_add)
//    LinearLayout ll_for_dynamic_add;
    public static ScrollView scrollView;
    String view_prescription_url = "";
    String commomProfile = "";
    PatientFilledDataModel patientFilledDataModel;
    String profile_id = "";
    SharedPrefHelper sharedPrefHelper;
    ProgressDialog mProgressDialog;
    String patient_appointments_id = "";
    String medicine = "";
    String date = "";
    String doctor_name = "";
    String id = "";
    String symptoms = "";
    SqliteHelper sqliteHelper;
    String caste_id;
    boolean isEditable = false;
    private Context context = this;
    TextView  view_prescription_click,tv_patient_symptoms,tv_doctor_name2,tv_date2,tv_medicine2;
    OldPrescriptionPojo oldPrescriptionPojo;
    PrescriptionModel prescriptionModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_prescription_profile_inflater);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(this);
        initViews();
        /*get intent here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            profile_id = bundle.getString("profile_patient_id", "");
            view_prescription_url = bundle.getString("url", "");
            date = bundle.getString("date", "");
            doctor_name = bundle.getString("doctor_name", "");
            medicine = bundle.getString("medicine", "");
            id = bundle.getString("id", "");
            symptoms = bundle.getString("symptoms", ",");
        }

       String [] a= symptoms.split(",");
        String symptom_name = "";
        for(int i=0;i < a.length;i++) {
            Log.e("TAG bdshdv bjscjsc", "onCreate: "+a[i]);

            if(i==0){
                symptom_name=sqliteHelper.getSymtomName(a[i], "symptom");
            } else {
                if (symptom_name != null) {
                    symptom_name = symptom_name + ", " + sqliteHelper.getSymtomName(a[i], "symptom");
                }
            }
        }
        tv_patient_symptoms.setText(""+symptom_name);

        tv_date2.setText(date);
        tv_doctor_name2.setText(doctor_name);
        tv_medicine2.setText(medicine);

    }

    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        oldPrescriptionPojo = new OldPrescriptionPojo();
        prescriptionModel = new PrescriptionModel();
        sharedPrefHelper = new SharedPrefHelper(context);
        sqliteHelper = new SqliteHelper(this);
        scrollView = findViewById(R.id.scrollView);
        tv_doctor_name2 = findViewById(R.id.tv_doctor_name2);
        tv_patient_symptoms =findViewById(R.id.tv_patient_symptoms);
        tv_date2 = findViewById(R.id.tv_date2);
        tv_medicine2 = findViewById(R.id.tv_medicine2);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommonPrescription.this, OldPrescription.class);
        startActivity(intent);
        finish();
    }
}