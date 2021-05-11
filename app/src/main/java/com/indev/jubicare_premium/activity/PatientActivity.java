package com.indev.jubicare_premium.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.adapter.PatientAdapter;
import com.indev.jubicare_premium.database.PharmacyPatientModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientActivity  extends AppCompatActivity {
    @BindView(R.id.rv_Patient_patient_list)
    RecyclerView rv_Patient_patient_list;
    @BindView(R.id.tv_addprofile)
    TextView tv_addprofile;
    @BindView(R.id.etSearchBar)
    EditText etSearchBar;
    @BindView(R.id.btnSearch)
    ImageView btnSearch;
    @BindView(R.id.tv_list_count)
    TextView tv_list_count;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    @BindView(R.id.btn_go_home)
    TextView btn_go_home;

    /*normal widgets*/
    private Context context = this;
    SharedPrefHelper sharedPrefHelper;
    PatientAdapter patientAdapter;
    PharmacyPatientModel pharmacyPatientModel = new PharmacyPatientModel();
    ArrayList<ContentValues> patientContentValue = new ArrayList<ContentValues>();
    private ProgressDialog mProgressDialog;
    String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        setTitle("Patient List");
        initViews();

        callPatientListApi();
    }

    @OnClick({R.id.tv_addprofile, R.id.btnSearch, R.id.btn_go_home})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addprofile:
                Intent intent = new Intent(context, SignUp.class);
                intent.putExtra("addMember", "addMember");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSearch:
                searchInput = etSearchBar.getText().toString().trim();
                /*Intent intent1 = new Intent(context, PatientListReplicaforSearch.class);
                intent1.putExtra("searchInput", searchInput);
                intent1.putExtra("appointment_for", "appointment_for");
                startActivity(intent1);*/
                callPatientListApi();
                //finish();
                break;
            case R.id.btn_go_home:
                Intent intentPatient=new Intent(context, HomeActivity.class);
                intentPatient.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPatient);
                finish();
                break;
        }
    }

    private void callPatientListApi() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        pharmacyPatientModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        pharmacyPatientModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
        pharmacyPatientModel.setMobile(searchInput);

        Gson mGson = new Gson();
        String data = mGson.toJson(pharmacyPatientModel);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        APIClient.getClient().create(TELEMEDICINE_API.class).patientListingApi(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        mProgressDialog.dismiss();
                        patientContentValue.clear();
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {

                        }
                        JsonObject singledataP = response.body();
                        JsonArray data = singledataP.getAsJsonArray("tableData");
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
                                patientContentValue.add(contentValues);
                                /*total count of list*/
                                tv_list_count.setText("Total - " + patientContentValue.size());

                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                patientAdapter = new PatientAdapter(context, patientContentValue);
                                rv_Patient_patient_list.setLayoutManager(mLayoutManager);
                                rv_Patient_patient_list.setAdapter(patientAdapter);
                                patientAdapter.onItemClick(new PatientAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }

                                    @Override
                                    public void onListItemClick(int position) {
                                        Intent intent = new Intent(context, CommonProfile.class);
                                        intent.putExtra("patient", "patient");
                                        intent.putExtra("profile_patient_id", patientContentValue.get(position).get("profile_patient_id").toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        } else {
                            tv_list_count.setVisibility(View.GONE);
                            rv_Patient_patient_list.setVisibility(View.GONE);
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            btn_go_home.setVisibility(View.VISIBLE);
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

    private void initViews() {
        sharedPrefHelper = new SharedPrefHelper(this);
        mProgressDialog = new ProgressDialog(context);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PatientActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}


