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

import com.google.gson.Gson;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
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

public class CommonReport extends AppCompatActivity {

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
    String patient_appointments_id = "";

    SqliteHelper sqliteHelper;
    String caste_id;
    boolean isEditable = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_report);
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
        }


        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        patientFilledDataModel.setProfile_patient_id("1");
        patientFilledDataModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        patientFilledDataModel.setRole_id(sharedPrefHelper.getString("role_id", ""));

        Gson gson = new Gson();
        String data = gson.toJson(patientFilledDataModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        /*send data here*/
        getPatientProfileDetails(body);
    }

    private void getPatientProfileDetails(RequestBody body) {

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
                                    JsonArray data = singledataP.getAsJsonArray("tableData");
                                    JsonArray data2 = singledataP.getAsJsonArray("data");
                                    //comment by vimal because they send Appointmenthistory = null instead of Appointmenthistory = []

                                    JSONObject singledata = null;
                                    JSONObject singledata2 = null;
                                    try {
                                        addDynamicProfile(data2, singledata2);

//                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
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


    private void addDynamicProfile(JsonArray data2, JSONObject singledata2) {
        for (int i = 0; i < data2.size(); i++) {
            Log.e("addmkmm", "addDynamicProfile: " + data2.toString());
            View inflatedView = getLayoutInflater().inflate(
                    R.layout.common_report_profile_inflater, ll_for_dynamic_add, false);
            //  TestDocsAdapterInProfile testDocsAdapterInProfile;

            final LinearLayout ll_medical_info;
            final TextView  view_prescription_click,tv_doctor_name1,tv_test1,tv_patient_date;
            tv_doctor_name1 = inflatedView.findViewById(R.id.tv_doctor_name1);
            tv_test1 = inflatedView.findViewById(R.id.tv_test1);
            tv_patient_date = inflatedView.findViewById(R.id.tv_patient_date);

            ll_medical_info = inflatedView.findViewById(R.id.ll_medical_info);
            view_prescription_click = inflatedView.findViewById(R.id.view_prescription_click);





            /*click here for button*/
//            btn_show_medical_info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
            //btn_show_medical_info.setVisibility(View.GONE);
            //ll_medical_info.setVisibility(View.VISIBLE);
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            if (ll_medical_info.getVisibility() == View.VISIBLE) {
                ll_medical_info.setVisibility(View.GONE);
            } else {
                ll_medical_info.setVisibility(View.VISIBLE);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
//                }
//            });

            try {
                //appointments details of patient
                if (!data2.isJsonNull() && data2.size() > 0) {
                    singledata2 = new JSONObject(data2.get(i).toString());
//                    try {
//                        String incommingDate = singledata2.get("created_at").toString();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date newDate = sdf.parse(incommingDate);
//                        sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");
//                        String outputDate = sdf.format(newDate);
////                        btn_show_medical_info.setText("Appointment on: " + outputDate);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    /*click here for doctor link*/
//                    String view_prescription_url = singledata2.get("view_prescription_click").toString();
//                    view_prescription_click.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //TO DO
//                            Intent intent = new Intent(context, ViewPrescription.class);
//                            intent.putExtra("url", view_prescription_url);
//                            startActivity(intent);
//                        }
//                    });

                    /*get patient_appointments_id for calling to patient*/
//                    patient_appointments_id = singledata2.get("patient_appointments_id").toString();


                    if (!singledata2.get("date").toString().equalsIgnoreCase("")
                            && !singledata2.get("date").toString().equalsIgnoreCase("0000-00-00")) {
                        String incomingDate = singledata2.get("date").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date newDate = sdf.parse(incomingDate);
                            sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String outputDate = sdf.format(newDate);
                            tv_patient_date.setText(outputDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        tv_patient_date.setVisibility(View.GONE);
                    }

                    if (!singledata2.get("doctor_name").toString().equalsIgnoreCase("")) {
                        tv_doctor_name1.setText(singledata2.get("doctor_name").toString());
                    } else {
                        tv_doctor_name1.setVisibility(View.GONE);
                    }
                    if (!singledata2.get("test").toString().equalsIgnoreCase("")) {
                        tv_test1.setText(singledata2.get("test").toString());
                    } else {
                        tv_test1.setVisibility(View.GONE);
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ll_for_dynamic_add.addView(inflatedView);
        }




    }


    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(context);
        scrollView = findViewById(R.id.scrollView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommonReport.this, Reports.class);
        startActivity(intent);
        finish();
    }



}
