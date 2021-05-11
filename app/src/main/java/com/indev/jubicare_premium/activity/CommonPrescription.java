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
            symptoms = bundle.getString("symptoms", "");
        }
        oldPrescriptionPojo.setId(sharedPrefHelper.getString("id", ""));
        oldPrescriptionPojo.setDate(sharedPrefHelper.getString("date", ""));
        oldPrescriptionPojo.setDoctor_name(sharedPrefHelper.getString("doctor_name", ""));
        oldPrescriptionPojo.setMedicine(sharedPrefHelper.getString("medicine", ""));
        oldPrescriptionPojo.setSymptoms(sharedPrefHelper.getString("symptoms", ""));


//        ll_medical_info = inflatedView.findViewById(R.id.ll_medical_info);
//        view_prescription_click = inflatedView.findViewById(R.id.view_prescription_click);
//        String date = sharedPrefHelper.getString("date", "");
//        String doctor_name = sharedPrefHelper.getString("doctor_name", "");
//        String test = sharedPrefHelper.getString("test", "");
        tv_date2.setText(date);
        tv_doctor_name2.setText(doctor_name);
        tv_medicine2.setText(medicine);
        tv_patient_symptoms.setText(symptoms);

//        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//        patientFilledDataModel.setProfile_patient_id("1");
//        patientFilledDataModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
//        patientFilledDataModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
//
//        Gson gson = new Gson();
//        String data = gson.toJson(patientFilledDataModel);
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//        /*send data here*/
//        getPatientProfileDetails(body);
    }

//    private void getPatientProfileDetails(RequestBody body) {
//
//        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
//        if (body != null && api_service != null) {
//            Call<JsonObject> server_response = api_service.download_old_prescription(body);
//            try {
//                if (server_response != null) {
//                    server_response.enqueue(new Callback<JsonObject>() {
//                        @Override
//                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                            if (response.isSuccessful()) {
//                                try {
//                                    JsonObject singledataP = response.body();
//                                    Log.e("nxjknx", "yxjhjxj " + singledataP.toString());
//                                    mProgressDialog.dismiss();
//                                    JsonArray data = singledataP.getAsJsonArray("Appointmenthistory");
//                                    JsonArray data2 = singledataP.getAsJsonArray("data");
//                                    //comment by vimal because they send Appointmenthistory = null instead of Appointmenthistory = []
//
//                                    JSONObject singledata = null;
//                                    JSONObject singledata2 = null;
//                                    try {
//                                        addDynamicProfile(data2, singledata2);
//
////                                        }
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                            Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                            mProgressDialog.dismiss();
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }



//    private void addDynamicProfile(JsonArray data2, JSONObject singledata2) {
//        for (int i = 0; i < data2.size(); i++) {
//            Log.e("addmkmm", "addDynamicProfile: " + data2.toString());
//            View inflatedView = getLayoutInflater().inflate(
//                    R.layout.common_prescription_profile_inflater, ll_for_dynamic_add, false);
//            //  TestDocsAdapterInProfile testDocsAdapterInProfile;
//
//            final LinearLayout ll_medical_info;
//            final TextView  view_prescription_click,tv_patient_symptoms,tv_doctor_name2,tv_date2,tv_medicine2;
//            tv_doctor_name2 = inflatedView.findViewById(R.id.tv_doctor_name2);
//            tv_patient_symptoms = inflatedView.findViewById(R.id.tv_patient_symptoms);
//            tv_date2 = inflatedView.findViewById(R.id.tv_date2);
//            tv_medicine2 = inflatedView.findViewById(R.id.tv_medicine2);
//
//            ll_medical_info = inflatedView.findViewById(R.id.ll_medical_info);
//            view_prescription_click = inflatedView.findViewById(R.id.view_prescription_click);
//
//
//
//
//
//            /*click here for button*/
////            btn_show_medical_info.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
//            //btn_show_medical_info.setVisibility(View.GONE);
//            //ll_medical_info.setVisibility(View.VISIBLE);
//            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            if (ll_medical_info.getVisibility() == View.VISIBLE) {
//                ll_medical_info.setVisibility(View.GONE);
//            } else {
//                ll_medical_info.setVisibility(View.VISIBLE);
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
////                }
////            });
//
//            try {
//                //appointments details of patient
//                if (!data2.isJsonNull() && data2.size() > 0) {
//                    singledata2 = new JSONObject(data2.get(i).toString());
////                    try {
////                        String incommingDate = singledata2.get("created_at").toString();
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        Date newDate = sdf.parse(incommingDate);
////                        sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");
////                        String outputDate = sdf.format(newDate);
//////                        btn_show_medical_info.setText("Appointment on: " + outputDate);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//
//                    /*click here for doctor link*/
////                    String view_prescription_url = singledata2.get("view_prescription_click").toString();
////                    view_prescription_click.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            //TO DO
////                            Intent intent = new Intent(context, ViewPrescription.class);
////                            intent.putExtra("url", view_prescription_url);
////                            startActivity(intent);
////                        }
////                    });
//
//                    /*get patient_appointments_id for calling to patient*/
////                    patient_appointments_id = singledata2.get("patient_appointments_id").toString();
//
//
//                    if (!singledata2.get("date").toString().equalsIgnoreCase("")
//                            && !singledata2.get("date").toString().equalsIgnoreCase("0000-00-00")) {
//                        String incomingDate = singledata2.get("date").toString();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        try {
//                            Date newDate = sdf.parse(incomingDate);
//                            sdf = new SimpleDateFormat("dd-MM-yyyy");
//                            String outputDate = sdf.format(newDate);
//                            tv_date2.setText(outputDate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        } catch (java.text.ParseException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        tv_date2.setVisibility(View.GONE);
//                    }
//
//                    if (!singledata2.get("doctor_name").toString().equalsIgnoreCase("")) {
//                        tv_doctor_name2.setText(singledata2.get("doctor_name").toString());
//                    } else {
//                        tv_doctor_name2.setVisibility(View.GONE);
//                    }
//                    if (!singledata2.get("medicine").toString().equalsIgnoreCase("")) {
//                        tv_medicine2.setText(singledata2.get("medicine").toString());
//                    } else {
//                        tv_medicine2.setVisibility(View.GONE);
//                    }
//                    if (!singledata2.get("symptoms").toString().equalsIgnoreCase("")) {
//                        tv_patient_symptoms.setText(singledata2.get("symptoms").toString());
//                    } else {
//                        tv_patient_symptoms.setVisibility(View.GONE);
//                    }
//
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            ll_for_dynamic_add.addView(inflatedView);
//        }
//
//
//
//
//    }



    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        oldPrescriptionPojo = new OldPrescriptionPojo();
        sharedPrefHelper = new SharedPrefHelper(context);
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