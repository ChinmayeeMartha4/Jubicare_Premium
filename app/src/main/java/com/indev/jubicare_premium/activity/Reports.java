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
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.adapter.ReportsAdapter;
import com.indev.jubicare_premium.database.ReportsPojo;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reports extends AppCompatActivity {
    ReportsAdapter reportsAdapter;
//    ReportsPojo reportsPojo;
    SqliteHelper sqliteHelper;
    ReportsPojo reportsPojo = new ReportsPojo();
    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();

    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.person3)
    TextView person4;
    RecyclerView reports_recyclerView;
    private ProgressDialog mProgressDialog;
    private Context context=this;
    SharedPrefHelper sharedPrefHelper;
    String id="";
    String full_name="";
    PatientModel patientModel;
    int personID = 0;
    String personName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Reports" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            personID = bundle.getInt("personID", 0);
            personName = bundle.getString("personName", "");

        }
        reportsPojo.setProfile_patient_id(String.valueOf(personID));
        reportsPojo.setFull_name(sharedPrefHelper.getString("personName", ""));

        String personName = sharedPrefHelper.getString("personName", "");
//        patientFilledDataModel.setProfile_patient_id(id);
        person4.setText(personName);


        getPatientProfileDetails();


        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(Reports.this, TakeReport.class);
                intent.putExtra("personID",sharedPrefHelper.getInt("personID" ,0));
                intent.putExtra("personName",sharedPrefHelper.getString("personName", ""));
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
        reports_recyclerView = (RecyclerView) findViewById(R.id.reports_recyclerView);
        add = (FloatingActionButton) findViewById(R.id.add);
        patientModel = new PatientModel();
        person4 = findViewById(R.id.person4);

    }
    private void getPatientProfileDetails() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        reportsPojo.setUser_id(sharedPrefHelper.getString("user_id", ""));
        reportsPojo.setRole_id(sharedPrefHelper.getString("role_id", ""));
        reportsPojo.setProfile_patient_id(String.valueOf(personID));

        Gson gson = new Gson();
        String data = gson.toJson(reportsPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
        if (body != null && api_service != null) {
            Call<JsonObject> server_response = api_service.download_reports(body);
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
                                    JsonArray data = singledataP.getAsJsonArray("data");
                                    //comment by vimal because they send Appointmenthistory = null instead of Appointmenthistory = []
                                    JsonArray data2 = singledataP.getAsJsonArray("Appointmenthistory");

//                                    JSONObject singledata = null;
                                    JSONObject singledata2 = null;

                                    if (data.size() > 0) {
                                        for (int i = 0; i < data.size(); i++) {
                                            JSONObject singledata = new JSONObject(data.get(i).toString());
                                            Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());
//                                            String id = singledata.optString("id");
//                                            String date = singledata.optString("date");
//                                            String doctor_name = singledata.optString("doctor_name");
//                                            String test = singledata.optString("test");
//                                            sharedPrefHelper.setString("id", id);
//                                            sharedPrefHelper.setString("date", date);
//                                            sharedPrefHelper.setString("doctor_name", doctor_name);
//                                            sharedPrefHelper.setString("test", test);
                                            String id = singledata.getString("id");
                                            reportsPojo.setId(id);

                                            Iterator keys = singledata.keys();
                                            ContentValues contentValues = new ContentValues();
                                            while (keys.hasNext()) {
                                                String currentDynamicKey = (String) keys.next();
                                                contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                            }
                                            patientContentValue.add(contentValues);

                                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                            reportsAdapter = new ReportsAdapter(context, patientContentValue);
                                            reports_recyclerView.setLayoutManager(mLayoutManager);
                                            reports_recyclerView.setAdapter(reportsAdapter);

                                        }
                                    }
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
