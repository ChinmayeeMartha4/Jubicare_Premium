package com.indev.jubicare_premium.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
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

    RecyclerView appointment_recyclerView;
    private ProgressDialog mProgressDialog;
    private Context context=this;
    SharedPrefHelper sharedPrefHelper;
String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Old Appointment" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id", "");
        }

        initViews();



        /*send data here*/
        getAppointmentProfileDetails();

    }
    private void initViews() {
        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
        appointment_recyclerView = (RecyclerView) findViewById(R.id.appointment_recyclerView);

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
                                    JsonArray data2 = singledataP.getAsJsonArray("Appointmenthistory");

//                                    JSONObject singledata = null;
                                    JSONObject singledata2 = null;

                                    if (data.size() > 0) {
                                        for (int i = 0; i < data.size(); i++) {
                                            JSONObject singledata = new JSONObject(data.get(i).toString());
                                            Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());
                                            String id = singledata.getString("id");
                                            oldAppointmentPojo.setId(id);

                                            Iterator keys = singledata.keys();
                                            ContentValues contentValues = new ContentValues();
                                            while (keys.hasNext()) {
                                                String currentDynamicKey = (String) keys.next();
                                                contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                            }
                                            patientContentValue.add(contentValues);

                                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                            oldAppointmentAdapter = new OldAppointmentAdapter(context, patientContentValue);
                                            appointment_recyclerView.setLayoutManager(mLayoutManager);
                                            appointment_recyclerView.setAdapter(oldAppointmentAdapter);

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
