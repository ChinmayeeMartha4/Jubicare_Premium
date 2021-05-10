package com.indev.jubicare_premium;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.activity.CommonProfile;
import com.indev.jubicare_premium.activity.Family;
import com.indev.jubicare_premium.activity.HealthRecord;
import com.indev.jubicare_premium.activity.OldAppointment;
import com.indev.jubicare_premium.activity.PatientActivity;
import com.indev.jubicare_premium.activity.PatientFillAppointment;
import com.indev.jubicare_premium.activity.OldPrescription;
import com.indev.jubicare_premium.activity.Reports;
import com.indev.jubicare_premium.adapter.FamilyListAdapter;
import com.indev.jubicare_premium.adapter.PatientAdapter;
import com.indev.jubicare_premium.app_drawer.AppDrawer;
import com.indev.jubicare_premium.database.FamilyPojo;
import com.indev.jubicare_premium.database.OldAppointmentPojo;
import com.indev.jubicare_premium.database.PharmacyPatientModel;
import com.indev.jubicare_premium.database.ReportsPojo;
import com.indev.jubicare_premium.database.SignUpModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppDrawer {
    CardView cv_appointment,cv_prescription,cv_report,cv_profile,cv_old_appointment,cv_family;

    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();
    private ArrayList<FamilyPojo> familyPojos;
    FamilyListAdapter familyListAdapter;
    Spinner spinner_person;
   OldAppointmentPojo oldAppointmentPojo;
   ReportsPojo reportsPojo;
   SqliteHelper sqliteHelper;
    PharmacyPatientModel pharmacyPatientModel = new PharmacyPatientModel();
    private ProgressDialog mProgressDialog;
    /*normal widgets*/
    private Context context = this;
    SharedPrefHelper sharedPrefHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
        oldAppointmentPojo =new OldAppointmentPojo();
        sqliteHelper = new SqliteHelper(this);
        initList();
        initview();
        spinner_person.setAdapter(familyListAdapter);

        download_patient();

        spinner_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FamilyPojo clickedItem = (FamilyPojo) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

                Intent intent = new Intent(HomeActivity.this, OldPrescription.class);
                startActivity(intent);
                finish();
            }
        });
        cv_old_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, OldAppointment.class);
                startActivity(intent);
                finish();
            }
        });


        cv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(HomeActivity.this, Reports.class);
                startActivity(intent);
                finish();
            }
        });
        cv_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, PatientActivity.class);
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

    private void initview() {
        cv_appointment=findViewById(R.id.cv_appointment);
        cv_profile=findViewById(R.id.cv_profile);
        cv_old_appointment=findViewById(R.id.cv_old_appointment);
        cv_prescription=findViewById(R.id.cv_prescription);
        cv_report=findViewById(R.id.cv_report);
        cv_family=findViewById(R.id.cv_family);
        spinner_person=findViewById(R.id.spinner_person);
//        String name = sharedPrefHelper.getString("name", "");
//        tv_welcomeName.setText(name);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
        familyListAdapter = new FamilyListAdapter(this,familyPojos );
    }

//    private void callPatientListApi() {
//        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//        pharmacyPatientModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
//        pharmacyPatientModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
//
//        Gson mGson = new Gson();
//        String data = mGson.toJson(pharmacyPatientModel);
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//
//        APIClient.getClient().create(TELEMEDICINE_API.class).patientListingApi(body).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.body().toString());
//                        mProgressDialog.dismiss();
//                        patientContentValue.clear();
//                        String success = jsonObject.getString("success");
//                        if (success.equals("1")) {
//
//                        }
//                        JsonObject singledataP = response.body();
//                        JsonArray data = singledataP.getAsJsonArray("tableData");
//                        if (data.size() > 0) {
//                            for (int i = 0; i < data.size(); i++) {
//                                JSONObject singledata = new JSONObject(data.get(i).toString());
//                                Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());
//
//                                Iterator keys = singledata.keys();
//                                ContentValues contentValues = new ContentValues();
//                                while (keys.hasNext()) {
//                                    String currentDynamicKey = (String) keys.next();
//                                    contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
//                                }
//                                patientContentValue.add(contentValues);
//
//                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//                                familyListAdapter = new FamilyListAdapter(context, patientContentValue);
////                                rv_Patient_patient_list.setLayoutManager(mLayoutManager);
////                                rv_Patient_patient_list.setAdapter(familyListAdapter);
////                                familyListAdapter.onItemClick(new PatientAdapter.ClickListener() {
//                                    @Override
//                                    public void onItemClick(int position) {
//
//                                    }
//
//                                    @Override
//                                    public void onListItemClick(int position) {
//                                        Intent intent = new Intent(context, CommonProfile.class);
//                                        intent.putExtra("patient", "patient");
//                                        intent.putExtra("profile_patient_id", patientContentValue.get(position).get("profile_patient_id").toString());
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                });
//                            }
//                    }
////                        } else {
////                            tv_list_count.setVisibility(View.GONE);
////                            rv_Patient_patient_list.setVisibility(View.GONE);
////                            tvNoDataFound.setVisibility(View.VISIBLE);
////                            btn_go_home.setVisibility(View.VISIBLE);
////                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressDialog.dismiss();
//            }
//        });
//    }
private void download_patient() {


    Gson mGson = new Gson();
    String data = mGson.toJson(pharmacyPatientModel);
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(JSON, data);
    TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
    if (body != null && api_service != null) {
        Call<JsonObject> server_response = api_service.patientListingApi(body);
        try {
            if (server_response != null) {
                server_response.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            try {
                                JsonObject singledataP = response.body();
                                Log.e("nxjknx", "yxjhjxj " + singledataP.toString());
                                mProgressDialog.dismiss();
                                JsonArray data = singledataP.getAsJsonArray("tableData");
                                //comment by vimal because they send Appointmenthistory = null instead of Appointmenthistory = []

                                JSONObject singledata2 = null;

//                                sqliteHelper.saveMasterTable(contentValues, "organization");

                                if (data.size() > 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        JSONObject singledata = new JSONObject(data.get(i).toString());
                                        Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());

                                        Iterator keys = singledata.keys();
                                        ContentValues contentValues = new ContentValues();
                                        while (keys.hasNext()) {
                                            String currentDynamicKey = (String) keys.next();
                                            contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                        }
//                                        patientContentValue.add(contentValues);
                                        /*total count of list*/
//                                        tv_list_count.setText("Total - " + patientContentValue.size());

//                                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//                                        familyListAdapter = new PatientAdapter(context, patientContentValue);
//                                        rv_Patient_patient_list.setLayoutManager(mLayoutManager);
//                                        rv_Patient_patient_list.setAdapter(familyListAdapter);
//                                        patientAdapter.onItemClick(new PatientAdapter.ClickListener() {
//                                            @Override
//                                            public void onItemClick(int position) {
//
//                                            }
//
//                                            @Override
//                                            public void onListItemClick(int position) {
//                                                Intent intent = new Intent(context, HomeActivity.class);
//                                                intent.putExtra("patient", "patient");
//                                                intent.putExtra("profile_patient_id", patientContentValue.get(position).get("profile_patient_id").toString());
//                                                startActivity(intent);
//                                                finish();
//                                            }
//                                        });
//
                                    }}


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    private void initList() {
        familyPojos = new ArrayList<>();
//        tv_name.setText(familyPojos.getName()+"");
        familyPojos.add(new FamilyPojo("Arun Tiwari"));
        familyPojos.add(new FamilyPojo("Ram Tiwari"));
        familyPojos.add(new FamilyPojo("Lav Singh"));
        familyPojos.add(new FamilyPojo("Nikesh Singh"));
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
