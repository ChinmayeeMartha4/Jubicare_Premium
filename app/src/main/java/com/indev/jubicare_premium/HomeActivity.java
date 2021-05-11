package com.indev.jubicare_premium;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.indev.jubicare_premium.activity.PatientModel;
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
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppDrawer {
    CardView cv_appointment, cv_prescription, cv_report, cv_profile, cv_old_appointment, cv_family;

    ArrayList<FamilyPojo> patientContentValue = new ArrayList<FamilyPojo>();
    private ArrayList<FamilyPojo> familyPojoArrayList;
    FamilyListAdapter familyListAdapter;
    Spinner spinner_person;
    OldAppointmentPojo oldAppointmentPojo;
    ReportsPojo reportsPojo;
    SqliteHelper sqliteHelper;
    ProgressDialog mprogressDialog;
    boolean isEditable = false;
    String person_name;
    PatientModel patientModel = new PatientModel();
    PharmacyPatientModel pharmacyPatientModel = new PharmacyPatientModel();
    private ProgressDialog mProgressDialog;
    /*normal widgets*/
    FamilyPojo familyPojo;
    HashMap<String, Integer> personHM = new HashMap<>();
    ArrayList<String> personArrayList1 = new ArrayList<>();
    ArrayList<String> personName = new ArrayList<>();

    private Context context = this;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
        oldAppointmentPojo = new OldAppointmentPojo();
        sharedPrefHelper=new SharedPrefHelper(this);
        sqliteHelper = new SqliteHelper(this);
        // initList();
        initview();
//        setpersonSpinnerData();
        download_patient();

        spinner_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPrefHelper.setString("id", "id");
                sharedPrefHelper.setString("name", "name");

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
        cv_appointment = findViewById(R.id.cv_appointment);
        cv_profile = findViewById(R.id.cv_profile);
        cv_old_appointment = findViewById(R.id.cv_old_appointment);
        cv_prescription = findViewById(R.id.cv_prescription);
        cv_report = findViewById(R.id.cv_report);
        cv_family = findViewById(R.id.cv_family);
        spinner_person = findViewById(R.id.spinner_person);
//        String name = sharedPrefHelper.getString("name", "");
//        tv_welcomeName.setText(name);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);

    }

    private void download_patient() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        patientModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        Gson mGson = new Gson();
        String data = mGson.toJson(patientModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        APIClient.getClient().create(TELEMEDICINE_API.class).patientpartnr(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        mProgressDialog.dismiss();
                        personHM.clear();
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {

                        }
                        JsonObject singledataP = response.body();
                        JsonArray data = singledataP.getAsJsonArray("tableData");
                        if (data.size() > 0) {
                            try {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    String name = singledata.getString("full_name");
                                    String id = singledata.getString("id");
                                    sharedPrefHelper.setString("id", id);
                                    sharedPrefHelper.setString("name", name);
                                    personHM.put(name, Integer.valueOf(id));
                                    personName.clear();
                                    for (int j = 0; j < personHM.size(); j++) {
                                        personName.add(personHM.keySet().toArray()[j].toString().trim());
                                        //docName.add(0,"Select Doctor");
//                                        final ArrayAdapter Adapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_spinner_item, personName);
                                        final ArrayAdapter Adapter = new ArrayAdapter(HomeActivity.this, R.layout.simple_spinner_items, personName);

                                        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner_person.setAdapter(Adapter);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

//    private void setpersonSpinnerData() {
//        personHM.clear();
//        personArrayList1.clear();
//
//        for (int i = 0; i < personHM.size(); i++) {
//            personArrayList1.add(personHM.keySet().toArray()[i].toString().trim());
//        }
//        if (isEditable) {
//            personArrayList1.add(0, person_name);
//        } else {
//            personArrayList1.add(0, "Select Person");
//        }
//        //  districtArrayList1.add(0, getString(R.string.selectDistrict));
////        final ArrayAdapter Adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, personArrayList1);
//        final ArrayAdapter Adapter1 = new ArrayAdapter(this, R.layout.simple_spinner_items, personArrayList1);
//
//        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_person.setAdapter(Adapter1);
//
//    }


//    private void initList() {
//        familyPojos = new ArrayList<>();
////        tv_name.setText(familyPojos.getName()+"");
//        familyPojos.add(new FamilyPojo("Arun Tiwari"));
//        familyPojos.add(new FamilyPojo("Ram Tiwari"));
//        familyPojos.add(new FamilyPojo("Lav Singh"));
//        familyPojos.add(new FamilyPojo("Nikesh Singh"));
//    }

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
