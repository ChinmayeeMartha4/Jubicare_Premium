package com.indev.jubicare_premium.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.adapter.OldAppointmentAdapter;
import com.indev.jubicare_premium.adapter.OldPrescriptionAdapter;
import com.indev.jubicare_premium.database.AppointmentPojo;
import com.indev.jubicare_premium.database.OldAppointmentPojo;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class OldAppointment extends AppCompatActivity {
    OldAppointmentAdapter oldAppointmentAdapter;
    SqliteHelper sqliteHelper;

    OldAppointmentPojo oldAppointmentPojo = new OldAppointmentPojo();
    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();
    ArrayList<ContentValues> patientContentValue1 = new ArrayList<ContentValues>();
    TextView person2;
    RecyclerView appointment_recyclerView;
    private ProgressDialog mProgressDialog;
    private Context context=this;
    SharedPrefHelper sharedPrefHelper;

    PatientModel patientModel;
    String symptom = "";
    int personID = 0;
    String personName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Appointments" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        sharedPrefHelper = new SharedPrefHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            personID = bundle.getInt("personID", 0);
            personName = bundle.getString("personName", "");

        }
        oldAppointmentPojo.setProfile_patient_id(String.valueOf(personID));
        oldAppointmentPojo.setFull_name(sharedPrefHelper.getString("personName", ""));
        person2.setText(personName);

//        if (personID.equals(personID)) {

//        if (sharedPrefHelper.getString("personID", "").equalsIgnoreCase(String.valueOf(12042))) {
//            appointment_recyclerView.setVisibility(View.VISIBLE);
//
//        }
            /*send data here*/
        getAppointmentProfileDetails();

    }
    private void initViews() {
        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
        patientModel = new PatientModel();
        appointment_recyclerView = (RecyclerView) findViewById(R.id.appointment_recyclerView);
        person2 = findViewById(R.id.person2);

    }
    private void getAppointmentProfileDetails() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        oldAppointmentPojo.setUser_id(sharedPrefHelper.getString("user_id", ""));
        oldAppointmentPojo.setRole_id(sharedPrefHelper.getString("role_id", ""));

        Gson gson = new Gson();
        String data = gson.toJson(oldAppointmentPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
        if (body != null && api_service != null) {
            Call<JsonObject> server_response = api_service.download_appointments(body);
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
                                    JsonArray data2 = singledataP.getAsJsonArray("sys");

//                                    JSONObject singledata = null;
//                                    JSONObject singledata2 = null;

                                    if (data.size() > 0) {
                                        for (int i = 0; i < data.size(); i++) {
                                            JSONObject singledata = new JSONObject(data.get(i).toString());
                                            Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());
                                            String id = singledata.getString("id");
                                            oldAppointmentPojo.setId(id);
                                            //sy[]=>yha pr sy ka array niklana hai
                                            //loop
                                            //add in string array

                                            Iterator keys = singledata.keys();
                                            ContentValues contentValues = new ContentValues();
                                            while (keys.hasNext()) {
                                                String currentDynamicKey = (String) keys.next();
                                                contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                            }
                                            patientContentValue.add(contentValues);

//                                            if (data2 != null && data2.size() > 0) {
//                                                        for (int k = 0; k < data2.size(); k++) {
////                                            JSONArray jsonArray = data2.getAsJsonArray(k);
//
//                                                            JSONArray singledata2 = new JSONArray(data.get(k).toString());
////                                                    JSONArray singledata = data2.getJSONArray(i);
//
//                                                            if (k == 0) {
//                                                                symptom = singledata2.getString(Integer.parseInt("sys")).trim();
//                                                            } else {
//                                                                if (symptom != null) {
//                                                                    symptom = symptom + ", " + singledata2.getString(Integer.parseInt("sys")).trim();
//                                                                }
//                                                            }
//
//                                                            patientContentValue1.add(contentValues);
//
                                                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                                            oldAppointmentAdapter = new OldAppointmentAdapter(context, patientContentValue, patientContentValue1);
                                                            appointment_recyclerView.setLayoutManager(mLayoutManager);
                                                            appointment_recyclerView.setAdapter(oldAppointmentAdapter);
//                                                        }}
                                        }
                                    }
//                                    if (data2.size()> 0) {
//                                        for (int j = 0; j < data2.size(); j++) {
//                                            JSONArray singledata2 = symptomArray.getJSONArray(j);
//                                            if (j == 0) {
//                                                symptom = singledata2.getString("symptom_name");
//                                            } else {
//                                                if (symptom != null) {
//                                                    symptom = symptom + ", " + singledata2.getString("symptom_name");
//                                                }
//                                            }
//                                        }
//                                    }

//                                    JSONArray symptomArray = singledata2.getJSONArray("sys");
//                                    if (data2 != null && data2.size() > 0) {
//                                        for (int k = 0; k < data2.size(); k++) {
////                                            JSONArray jsonArray = data2.getAsJsonArray(k);
//                                            JSONArray singledata2 = new JSONArray(data.get(k).toString());
//
//                                            if (k == 0) {
//                                                symptom = singledata2.getString(Integer.parseInt("sys")).trim();
//                                            } else {
//                                                if (symptom != null) {
//                                                    symptom = symptom + ", " + singledata2.getString(Integer.parseInt("sys")).trim();
//                                                }
//                                            }
//                                            Iterator<JSONArray> keys = singledata2.keys();
//                                            ContentValues contentValues = new ContentValues();
//                                            while (keys.hasNext()) {
//                                                String currentDynamicKey = (String) keys.next();
//                                                contentValues.put(currentDynamicKey, singledata2.get(Integer.parseInt(currentDynamicKey)).toString());
//                                            }
//                                            patientContentValue1.add(contentValues);
//
//                                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//                                            oldAppointmentAdapter = new OldAppointmentAdapter(context, patientContentValue1);
//                                            appointment_recyclerView.setLayoutManager(mLayoutManager);
//                                            appointment_recyclerView.setAdapter(oldAppointmentAdapter);
//
//
////                                            JSONArray array = (JSONArray)jsonObject.get("employee");
//                                            Iterator<JSONObject> iterator = array.iterator();
//                                            while (iterator.hasNext()) {
//                                                System.out.println(iterator.next().toString());
//

                                    } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(OldAppointment.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OldAppointment.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
