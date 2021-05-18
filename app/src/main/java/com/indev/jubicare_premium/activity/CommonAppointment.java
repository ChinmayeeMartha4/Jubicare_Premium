package com.indev.jubicare_premium.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.adapter.TestDocsAdapterInProfile;
import com.indev.jubicare_premium.database.OldAppointmentPojo;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
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

import java.text.ParseException;
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

public class CommonAppointment extends AppCompatActivity {

    /*for dynamic inflate layout*/
    @BindView(R.id.ll_for_dynamic_add)
    LinearLayout ll_for_dynamic_add;
    public static ScrollView scrollView;
    String view_prescription_url = "";
    String commomProfile = "";
    PatientFilledDataModel patientFilledDataModel;
    String profile_id = "";
    SharedPrefHelper sharedPrefHelper;
    ProgressDialog mProgressDialog;
    String id = "";
    SqliteHelper sqliteHelper;
    String caste_id;
    boolean isEditable = false;
    private Context context = this;


    OldAppointmentPojo oldAppointmentPojo;
    String assigned_doctor="";
    String assigned_doctor_on="";
    String prescribed_medicine="";
    String prescribed_medicine_date="";
    String is_emergency="";
    String remarks="";
    String bp_lower="";
    String bp_upper="";
    String sugar="";
    String temperature="";
    String blood_oxygen_level="";
    String pulse="";
    String symptom="";
    TextView tv_patient_symptoms1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_appointment);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(this);
//        View inflatedView = getLayoutInflater().inflate(
//                R.layout.common_profile_inflater, ll_for_dynamic_add, false);

        tv_patient_symptoms1 = findViewById(R.id.tv_patient_symptoms1);

        /*get intent here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            profile_id = bundle.getString("profile_patient_id", "");
            view_prescription_url = bundle.getString("url", "");
            id = bundle.getString("id", "");
            assigned_doctor = bundle.getString("assigned_doctor", "");
            assigned_doctor_on = bundle.getString("assigned_doctor_on", "");
            prescribed_medicine = bundle.getString("prescribed_medicine", "");
            prescribed_medicine_date = bundle.getString("prescribed_medicine_date", "");
            is_emergency = bundle.getString("is_emergency", "");
            remarks = bundle.getString("remarks", "");
            bp_lower = bundle.getString("bp_lower", "");
            bp_upper = bundle.getString("bp_upper", "");
            sugar = bundle.getString("sugar", "");
            temperature = bundle.getString("temperature", "");
            blood_oxygen_level = bundle.getString("blood_oxygen_level", "");
            pulse = bundle.getString("pulse", "");
            symptom = bundle.getString("sys", "");//get string array here
            //itreate here and add to text view
//            ArrayList<sys> myList = getIntent().getParcelableExtra("sys");
//            ArrayList<String> numbersList = (ArrayList<String>) getIntent().getSerializableExtra("sys");
//            tv_patient_symptoms1.setText(String.valueOf(numbersList));
            initViews();
            addDynamicProfile();
        }

    }

    private void addDynamicProfile() {
            View inflatedView = getLayoutInflater().inflate(
                    R.layout.common_old_appointment_profile_inflater, ll_for_dynamic_add, false);
            TestDocsAdapterInProfile testDocsAdapterInProfile;
            TextView tv_taking_prescription_any_date, tv_assigned_doctor1,tv_date_assigned_doctor1,tv_date_taking_any_prescription1,
                    tv_patient_symptoms1,tv_bpupperP1,tv_bplowerP1,tv_taking_any_prescription1,tv_sugarP1,tv_tempratureP1,
                    tv_PulseP1, tv_blood_oxygen_levelP1,tv_any_emergency1,tv_patient_remarkss1,view_prescription_click;
            tv_date_assigned_doctor1 = inflatedView.findViewById(R.id.tv_date_assigned_doctor1);
            tv_assigned_doctor1 = inflatedView.findViewById(R.id.tv_assigned_doctor1);
            tv_patient_symptoms1 = inflatedView.findViewById(R.id.tv_patient_symptoms1);
            tv_date_taking_any_prescription1 = inflatedView.findViewById(R.id.tv_date_taking_any_prescription1);
            tv_taking_any_prescription1 = inflatedView.findViewById(R.id.tv_taking_any_prescription1);
            tv_taking_prescription_any_date = inflatedView.findViewById(R.id.tv_taking_prescription_any_date);
            tv_any_emergency1 = inflatedView.findViewById(R.id.tv_any_emergency1);
            tv_patient_remarkss1 = inflatedView.findViewById(R.id.tv_patient_remarkss1);
            view_prescription_click = inflatedView.findViewById(R.id.view_prescription_click);
            tv_bpupperP1 = inflatedView.findViewById(R.id.tv_bpupperP1);
            tv_bplowerP1 =inflatedView.findViewById(R.id.tv_bplowerP1);
            tv_PulseP1 = inflatedView.findViewById(R.id.tv_PulseP1);
            tv_sugarP1 = inflatedView.findViewById(R.id.tv_sugarP1);
            tv_tempratureP1 = inflatedView.findViewById(R.id.tv_tempratureP1);
            tv_blood_oxygen_levelP1 =inflatedView.findViewById(R.id.tv_blood_oxygen_levelP1);

//            oldAppointmentPojo.setId(sharedPrefHelper.getString("id", ""));
//            oldAppointmentPojo.setDoctor_name(sharedPrefHelper.getString("assigned_doctor", ""));
//            oldAppointmentPojo.setDate(sharedPrefHelper.getString("assigned_doctor_on", ""));
//            oldAppointmentPojo.setPrescribed_medicine(sharedPrefHelper.getString("prescribed_medicine", ""));
//            oldAppointmentPojo.setPrescribed_medicine_date(sharedPrefHelper.getString("prescribed_medicine_date", ""));
//            oldAppointmentPojo.setIs_emergency(sharedPrefHelper.getString("is_emergency", ""));
//            oldAppointmentPojo.setRemarks(sharedPrefHelper.getString("remarks", ""));
//            oldAppointmentPojo.setBp_low(sharedPrefHelper.getString("bp_lower", ""));
//            oldAppointmentPojo.setBp_upper(sharedPrefHelper.getString("bp_upper", ""));
//            oldAppointmentPojo.setSugar(sharedPrefHelper.getString("sugar", ""));
//            oldAppointmentPojo.setTemperature(sharedPrefHelper.getString("temperature", ""));
//            oldAppointmentPojo.setBlood_oxygen_level(sharedPrefHelper.getString("blood_oxygen_level", ""));
//            oldAppointmentPojo.setPulse(sharedPrefHelper.getString("pulse", ""));
//            oldAppointmentPojo.setSymptom_id(sharedPrefHelper.getString("sys", ""));

            tv_assigned_doctor1.setText(assigned_doctor);
            tv_date_assigned_doctor1.setText(assigned_doctor_on);
            tv_date_taking_any_prescription1.setText(prescribed_medicine_date);
            tv_taking_any_prescription1.setText(prescribed_medicine);
            tv_any_emergency1.setText(is_emergency);
            tv_patient_remarkss1.setText(remarks);
            tv_bplowerP1.setText(bp_lower);
            tv_bpupperP1.setText(bp_upper);
            tv_sugarP1.setText(sugar);
            tv_tempratureP1.setText(temperature);
            tv_blood_oxygen_levelP1.setText(blood_oxygen_level);
            tv_PulseP1.setText(pulse);
//            tv_patient_symptoms1.setText(symptom);

            ll_for_dynamic_add.addView(inflatedView);
        }





    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        oldAppointmentPojo = new OldAppointmentPojo();
        sharedPrefHelper = new SharedPrefHelper(context);
        scrollView = findViewById(R.id.scrollView);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommonAppointment.this, OldAppointment.class);
        startActivity(intent);
        finish();
    }
}