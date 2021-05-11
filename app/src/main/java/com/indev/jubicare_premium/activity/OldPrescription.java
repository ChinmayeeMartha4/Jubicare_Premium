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
import com.indev.jubicare_premium.adapter.OldPrescriptionAdapter;
//import com.indev.jubicare_premium.database.AppointmentPojo;
import com.indev.jubicare_premium.database.OldPrescriptionPojo;
import com.indev.jubicare_premium.database.PharmacyPatientModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OldPrescription extends AppCompatActivity {
    OldPrescriptionAdapter oldPrescriptionAdapter;
    SqliteHelper sqliteHelper;
//    private ArrayList<OldAppointmentPojo> oldAppointmentPojo;
    OldPrescriptionPojo oldPrescriptionPojo = new OldPrescriptionPojo();
    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();

    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.prescription_recyclerView)
    RecyclerView prescription_recyclerView;
    private ProgressDialog mProgressDialog;
    private Context context=this;
    SharedPrefHelper sharedPrefHelper;
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_prescription);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Prescription" + "</font>"));
//        appointmentPojo=new AppointmentPojo();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       initViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id", "");
        }



        /*send data here*/
        getPatientProfileDetails();
//        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
////        oldPrescriptionPojo.setProfile_patient_id(profile_id);
//        oldPrescriptionPojo.setUser_id("2");
//        oldPrescriptionPojo.setRole_id(sharedPrefHelper.getString("role_id", ""));
//
//        Gson gson = new Gson();
//        String data = gson.toJson(oldPrescriptionPojo);
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//        callPrescriptionListApi();

        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(OldPrescription.this, TakeOldPrescription.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initViews() {
        add = (FloatingActionButton) findViewById(R.id.add);
        prescription_recyclerView = (RecyclerView) findViewById(R.id.prescription_recyclerView);
        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
    }


    private void getPatientProfileDetails() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//        oldPrescriptionPojo.setProfile_patient_id(profile_id);
        oldPrescriptionPojo.setUser_id(sharedPrefHelper.getString("user_id", ""));
        oldPrescriptionPojo.setRole_id(sharedPrefHelper.getString("role_id", ""));

        Gson gson = new Gson();
        String data = gson.toJson(oldPrescriptionPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
        if (body != null && api_service != null) {
            Call<JsonObject> server_response = api_service.download_old_prescription(body);
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
                                            String id = singledata.getString("id");
                                            oldPrescriptionPojo.setId(id);
                                            Iterator keys = singledata.keys();
                                            ContentValues contentValues = new ContentValues();
                                            while (keys.hasNext()) {
                                                String currentDynamicKey = (String) keys.next();
                                                contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                            }
                                            patientContentValue.add(contentValues);

                                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                            oldPrescriptionAdapter = new OldPrescriptionAdapter(context, patientContentValue);
                                            prescription_recyclerView.setLayoutManager(mLayoutManager);
                                            prescription_recyclerView.setAdapter(oldPrescriptionAdapter);

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
            Intent intent = new Intent(OldPrescription.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OldPrescription.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
