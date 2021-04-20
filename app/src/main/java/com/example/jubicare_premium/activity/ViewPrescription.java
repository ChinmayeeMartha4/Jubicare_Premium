package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.PatientFilledDataModel;
import com.example.jubicare_premium.rest_api.APIClient;
import com.example.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;
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

public class ViewPrescription extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    /*for dynamic inflate layout*/
    @BindView(R.id.ll_for_dynamic_add)
    LinearLayout ll_for_dynamic_add;
    public static ScrollView scrollView;
    String view_prescription_url="";
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
        setContentView(R.layout.activity_view_prescription);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
        patientFilledDataModel=new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(this);
        initViews();
        /*get intent here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            profile_id = bundle.getString("profile_patient_id", "");
            view_prescription_url = bundle.getString("url", "");
        }


        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        patientFilledDataModel.setProfile_patient_id("12012");
        patientFilledDataModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        patientFilledDataModel.setRole_id(sharedPrefHelper.getString("role_id", ""));

        Gson gson = new Gson();
        String data = gson.toJson(patientFilledDataModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        /*send data here*/
//        getPatientProfileDetails(body);
            TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
            if (body != null && api_service != null) {
                Call<JsonObject> server_response = api_service.getCommonProfile(body);
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
                                        JsonArray data2 = singledataP.getAsJsonArray("Appointmenthistory");
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
                        R.layout.common_profile_inflater, ll_for_dynamic_add, false);
                //  TestDocsAdapterInProfile testDocsAdapterInProfile;
                final ImageView iv_test_docs;
                final RecyclerView rv_test_docs_inprofile;
                final Button btn_show_medical_info;
                final LinearLayout ll_medical_info;
                final TextView tv_assigned_doctor, tv_doctor_assigned, tv_patient_symptoms, tv_symptoms_patient,
                        tv_taking_any_prescription, tv_any_taking_prescription, tv_date_taking_any_prescription,
                        tv_taking_prescription_any_date, tv_any_emergency, tv_emergency_any, tv_patient_disease,
                        tv_disease_patient, tv_test_done, tv_is_test_done, tv_test_done_date, tv_is_test_done_date,
                        tv_assigned_doctor_date, tv_date_assigned_doctor, tv_assigned_pharmacists, tv_pharmacists_assigned,
                        tv_assigned_pharmacists_date, tv_date_pharmacists_assigned, tv_medicine_delivered,
                        tv_medicine_delivered_on, tv_is_verified, tv_verified_is, tv_assigned_by, tv_by_assigned,
                        tv_appointment_type, tv_type_appointment, tv_remarks_by_doctor, tv_by_remarks_doctor,
                        tv_is_doctor_done, tv_doctor_is_done, tv_is_doctor_done_on, tv_is_on_doctor_done,
                        tv_is_pharmacists_done, tv_pharmacists_is_done, tv_is_pharmacists_done_on, tv_pharmacists_is_done_on,
                        tv_patient_medicine, tv_medicine_patient, tv_days_prescription, tv_prescription_days,
                        tv_interval_prescription, tv_prescription_interval, tv_eating_schedule_prescription,
                        tv_prescription_eating_schedule, tv_sos_prescription, tv_prescription_sos, tv_name_test,
                        tv_test_name, tv_name_subtest, tv_subtest_name, tv_test_docs, tv_blood_oxygen_levelP, tv_tempratureP, tv_bp_upper, tv_patient_remarks,
                        tv_bplowerP, tv_bpupperP, tv_PulseP, tv_sugarP, tv_bp_lower, tv_Pulse, tv_sugar, tv_blood_oxygen_level, tv_temprature,
                        tv_patient_remarkss, view_prescription_click;
                tv_assigned_doctor = inflatedView.findViewById(R.id.tv_assigned_doctor);
                tv_doctor_assigned = inflatedView.findViewById(R.id.tv_doctor_assigned);
                tv_patient_symptoms = inflatedView.findViewById(R.id.tv_patient_symptoms);
                tv_symptoms_patient = inflatedView.findViewById(R.id.tv_symptoms_patient);
                tv_taking_any_prescription = inflatedView.findViewById(R.id.tv_taking_any_prescription);
                tv_any_taking_prescription = inflatedView.findViewById(R.id.tv_any_taking_prescription);
                tv_date_taking_any_prescription = inflatedView.findViewById(R.id.tv_date_taking_any_prescription);
                tv_taking_prescription_any_date = inflatedView.findViewById(R.id.tv_taking_prescription_any_date);
                tv_any_emergency = inflatedView.findViewById(R.id.tv_any_emergency);
                tv_emergency_any = inflatedView.findViewById(R.id.tv_emergency_any);
                tv_patient_disease = inflatedView.findViewById(R.id.tv_patient_disease);
                tv_disease_patient = inflatedView.findViewById(R.id.tv_disease_patient);
                tv_test_done = inflatedView.findViewById(R.id.tv_test_done);
                tv_is_test_done = inflatedView.findViewById(R.id.tv_is_test_done);
                tv_test_done_date = inflatedView.findViewById(R.id.tv_test_done_date);
                tv_is_test_done_date = inflatedView.findViewById(R.id.tv_is_test_done_date);
                tv_assigned_doctor_date = inflatedView.findViewById(R.id.tv_assigned_doctor_date);
                tv_date_assigned_doctor = inflatedView.findViewById(R.id.tv_date_assigned_doctor);
                tv_assigned_pharmacists = inflatedView.findViewById(R.id.tv_assigned_pharmacists);
                tv_pharmacists_assigned = inflatedView.findViewById(R.id.tv_pharmacists_assigned);
                tv_assigned_pharmacists_date = inflatedView.findViewById(R.id.tv_assigned_pharmacists_date);
                tv_date_pharmacists_assigned = inflatedView.findViewById(R.id.tv_date_pharmacists_assigned);
                tv_medicine_delivered = inflatedView.findViewById(R.id.tv_medicine_delivered);
                tv_medicine_delivered_on = inflatedView.findViewById(R.id.tv_medicine_delivered_on);
                tv_is_verified = inflatedView.findViewById(R.id.tv_is_verified);
                tv_verified_is = inflatedView.findViewById(R.id.tv_verified_is);
                tv_assigned_by = inflatedView.findViewById(R.id.tv_assigned_by);
                tv_by_assigned = inflatedView.findViewById(R.id.tv_by_assigned);
                tv_appointment_type = inflatedView.findViewById(R.id.tv_appointment_type);
                tv_type_appointment = inflatedView.findViewById(R.id.tv_type_appointment);
                tv_remarks_by_doctor = inflatedView.findViewById(R.id.tv_remarks_by_doctor);
                tv_by_remarks_doctor = inflatedView.findViewById(R.id.tv_by_remarks_doctor);
                tv_is_doctor_done = inflatedView.findViewById(R.id.tv_is_doctor_done);
                tv_doctor_is_done = inflatedView.findViewById(R.id.tv_doctor_is_done);
                tv_is_doctor_done_on = inflatedView.findViewById(R.id.tv_is_doctor_done_on);
                tv_is_on_doctor_done = inflatedView.findViewById(R.id.tv_is_on_doctor_done);
                tv_is_pharmacists_done = inflatedView.findViewById(R.id.tv_is_pharmacists_done);
                tv_pharmacists_is_done = inflatedView.findViewById(R.id.tv_pharmacists_is_done);
                tv_is_pharmacists_done_on = inflatedView.findViewById(R.id.tv_is_pharmacists_done_on);
                tv_pharmacists_is_done_on = inflatedView.findViewById(R.id.tv_pharmacists_is_done_on);
                tv_patient_medicine = inflatedView.findViewById(R.id.tv_patient_medicine);
                tv_medicine_patient = inflatedView.findViewById(R.id.tv_medicine_patient);
                tv_days_prescription = inflatedView.findViewById(R.id.tv_days_prescription);
                tv_prescription_days = inflatedView.findViewById(R.id.tv_prescription_days);
                tv_interval_prescription = inflatedView.findViewById(R.id.tv_interval_prescription);
                tv_prescription_interval = inflatedView.findViewById(R.id.tv_prescription_interval);
                tv_eating_schedule_prescription = inflatedView.findViewById(R.id.tv_eating_schedule_prescription);
                tv_prescription_eating_schedule = inflatedView.findViewById(R.id.tv_prescription_eating_schedule);
                tv_sos_prescription = inflatedView.findViewById(R.id.tv_sos_prescription);
                tv_prescription_sos = inflatedView.findViewById(R.id.tv_prescription_sos);
                tv_name_test = inflatedView.findViewById(R.id.tv_name_test);
                tv_test_name = inflatedView.findViewById(R.id.tv_test_name);
                tv_name_subtest = inflatedView.findViewById(R.id.tv_name_subtest);
                tv_subtest_name = inflatedView.findViewById(R.id.tv_subtest_name);
                tv_test_docs = inflatedView.findViewById(R.id.tv_test_docs);
                iv_test_docs = inflatedView.findViewById(R.id.iv_test_docs);
                rv_test_docs_inprofile = inflatedView.findViewById(R.id.rv_test_docs_inprofile);
                tv_patient_remarks = inflatedView.findViewById(R.id.tv_patient_remarks);
                tv_patient_remarkss = inflatedView.findViewById(R.id.tv_patient_remarkss);
                btn_show_medical_info = inflatedView.findViewById(R.id.btn_show_medical_info);
                ll_medical_info = inflatedView.findViewById(R.id.ll_medical_info);
                view_prescription_click = inflatedView.findViewById(R.id.view_prescription_click);
                tv_bplowerP = inflatedView.findViewById(R.id.tv_bplowerP);
                tv_bpupperP = inflatedView.findViewById(R.id.tv_bpupperP);
                tv_PulseP = inflatedView.findViewById(R.id.tv_PulseP);
                tv_sugarP = inflatedView.findViewById(R.id.tv_sugarP);
                tv_tempratureP = inflatedView.findViewById(R.id.tv_tempratureP);
                tv_bp_upper = inflatedView.findViewById(R.id.tv_bp_upper);
                tv_bp_lower = inflatedView.findViewById(R.id.tv_bp_lower);
                tv_Pulse = inflatedView.findViewById(R.id.tv_Pulse);
                tv_sugar = inflatedView.findViewById(R.id.tv_sugar);
                tv_blood_oxygen_level = inflatedView.findViewById(R.id.tv_blood_oxygen_level);
                tv_temprature = inflatedView.findViewById(R.id.tv_temprature);
                tv_blood_oxygen_levelP = inflatedView.findViewById(R.id.tv_blood_oxygen_levelP);






                /*click here for button*/
                btn_show_medical_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //btn_show_medical_info.setVisibility(View.GONE);
                        //ll_medical_info.setVisibility(View.VISIBLE);
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        if (ll_medical_info.getVisibility() == View.VISIBLE) {
                            ll_medical_info.setVisibility(View.GONE);
                        } else {
                            ll_medical_info.setVisibility(View.VISIBLE);
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }
                });

                try {
                    //appointments details of patient
                    if (!data2.isJsonNull() && data2.size() > 0) {
                        singledata2 = new JSONObject(data2.get(i).toString());
                        try {
                            String incommingDate = singledata2.get("created_at").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date newDate = sdf.parse(incommingDate);
                            sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");
                            String outputDate = sdf.format(newDate);
                            btn_show_medical_info.setText("Appointment on: " + outputDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*click here for doctor link*/
                        String view_prescription_url = singledata2.get("view_prescription_click").toString();
                        view_prescription_click.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TO DO
                                Intent intent = new Intent(context, ViewPrescription.class);
                                intent.putExtra("url", view_prescription_url);
                                startActivity(intent);
                            }
                        });

                        /*get patient_appointments_id for calling to patient*/
                        patient_appointments_id = singledata2.get("patient_appointments_id").toString();

                        if (!singledata2.get("prescribed_medicine").toString().equalsIgnoreCase("")) {
                            tv_taking_any_prescription.setText(singledata2.get("prescribed_medicine").toString());
                        } else {
                            tv_any_taking_prescription.setVisibility(View.GONE);
                            tv_taking_any_prescription.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("prescribed_medicine_date").toString().equalsIgnoreCase("")
                                && !singledata2.get("prescribed_medicine_date").toString().equalsIgnoreCase("0000-00-00")) {
                            String incomingDate = singledata2.get("prescribed_medicine_date").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String outputDate = sdf.format(newDate);
                                tv_date_taking_any_prescription.setText(outputDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_taking_prescription_any_date.setVisibility(View.GONE);
                            tv_date_taking_any_prescription.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_test_done").toString().equalsIgnoreCase("")) {
                            if (singledata2.get("is_test_done").toString().equalsIgnoreCase("Y")) {
                                tv_is_test_done.setText("Yes");
                            } else {
                                tv_is_test_done.setText("No");
                            }
                        } else {
                            tv_test_done.setVisibility(View.GONE);
                            tv_is_test_done.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_test_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("is_test_on").toString().equalsIgnoreCase("0000-00-00")) {
                            String incomingDate = singledata2.get("is_test_on").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String outputDate = sdf.format(newDate);
                                tv_is_test_done_date.setText(outputDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_is_test_done_date.setVisibility(View.GONE);
                            tv_test_done_date.setVisibility(View.GONE);
                        }

                        if (!singledata2.get("bp_upper").toString().equalsIgnoreCase("")) {
                            tv_bpupperP.setText(singledata2.get("bp_upper").toString());
                        } else {
                            tv_bp_upper.setVisibility(View.GONE);
                            tv_bpupperP.setVisibility(View.GONE);
                        }

                        if (!singledata2.get("bp_lower").toString().equalsIgnoreCase("")) {
                            tv_bplowerP.setText(singledata2.get("bp_lower").toString());
                        } else {
                            tv_bp_lower.setVisibility(View.GONE);
                            tv_bplowerP.setVisibility(View.GONE);
                        }

                        if (!singledata2.get("sugar").toString().equalsIgnoreCase("")) {
                            tv_sugarP.setText(singledata2.get("sugar").toString());
                        } else {
                            tv_sugar.setVisibility(View.GONE);
                            tv_sugarP.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("temperature").toString().equalsIgnoreCase("")) {
                            tv_tempratureP.setText(singledata2.get("temperature").toString());
                        } else {
                            tv_temprature.setVisibility(View.GONE);
                            tv_tempratureP.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("blood_oxygen_level").toString().equalsIgnoreCase("")) {
                            tv_blood_oxygen_levelP.setText(singledata2.get("blood_oxygen_level").toString());
                        } else {
                            tv_blood_oxygen_level.setVisibility(View.GONE);
                            tv_blood_oxygen_levelP.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("pulse").toString().equalsIgnoreCase("")) {
                            tv_PulseP.setText(singledata2.get("pulse").toString());
                        } else {
                            tv_Pulse.setVisibility(View.GONE);
                            tv_PulseP.setVisibility(View.GONE);
                        }

                        if (!singledata2.get("profile_doctors_full_name").toString().equalsIgnoreCase("")
                                && !singledata2.isNull("profile_doctors_full_name")) {
                            tv_assigned_doctor.setText(singledata2.get("profile_doctors_full_name").toString());
                        } else {
                            tv_assigned_doctor.setVisibility(View.GONE);
                            tv_doctor_assigned.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("assigned_doctor_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("assigned_doctor_on").toString().equalsIgnoreCase("0000-00-00 00:00:00")) {
                            String incomingDate = singledata2.get("assigned_doctor_on").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                                String outputDate = sdf.format(newDate);
                                tv_date_assigned_doctor.setText(outputDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_assigned_doctor_date.setVisibility(View.GONE);
                            tv_date_assigned_doctor.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("profile_pharmacists_full_name").toString().equalsIgnoreCase("")
                                && !singledata2.isNull("profile_pharmacists_full_name")) {
                            tv_pharmacists_assigned.setText(singledata2.get("profile_pharmacists_full_name").toString());
                        } else {
                            tv_assigned_pharmacists.setVisibility(View.GONE);
                            tv_pharmacists_assigned.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("assigned_pharmacists_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("assigned_pharmacists_on").toString().equalsIgnoreCase("0000-00-00 00:00:00")) {
                            String incomingDate = singledata2.get("assigned_pharmacists_on").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                                String outputDate = sdf.format(newDate);
                                tv_date_pharmacists_assigned.setText(outputDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_date_pharmacists_assigned.setVisibility(View.GONE);
                            tv_assigned_pharmacists_date.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("medicine_delivered_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("medicine_delivered_on").toString().equalsIgnoreCase("0")) {
                            tv_medicine_delivered_on.setText(singledata2.get("medicine_delivered_on").toString());
                        } else {
                            tv_medicine_delivered_on.setVisibility(View.GONE);
                            tv_medicine_delivered.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_verified").toString().equalsIgnoreCase("")) {
                            tv_verified_is.setText(singledata2.get("is_verified").toString());
                        } else {
                            tv_is_verified.setVisibility(View.GONE);
                            tv_verified_is.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_emergency").toString().equalsIgnoreCase("")) {
                            if (singledata2.get("is_emergency").toString().equalsIgnoreCase("Y")) {
                                tv_any_emergency.setText("Yes");
                            } else {
                                tv_any_emergency.setText("No");
                            }
                        } else {
                            tv_emergency_any.setVisibility(View.GONE);
                            tv_any_emergency.setVisibility(View.GONE);
                        }
//                        if (profile_tab.equalsIgnoreCase("profile_tab")) {
//                            tv_assigned_by.setVisibility(View.GONE);
//                            tv_by_assigned.setVisibility(View.GONE);
//                        }

                        if (!singledata2.get("profile_counselors_full_name").toString().equalsIgnoreCase("")
                                && !singledata2.isNull("profile_counselors_full_name")) {
                            tv_by_assigned.setText(singledata2.get("profile_counselors_full_name").toString());
                        } else {
                            tv_assigned_by.setVisibility(View.GONE);
                            tv_by_assigned.setVisibility(View.GONE);
                        }


                        if (!singledata2.get("appointment_type").toString().equalsIgnoreCase("")) {
                            if (singledata2.get("appointment_type").toString().equalsIgnoreCase("0")) {
                                tv_type_appointment.setText("Self");
                            } else {
                                tv_type_appointment.setText("Counsellor");
                            }
                        } else {
                            tv_appointment_type.setVisibility(View.GONE);
                            tv_type_appointment.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("remarkrs_by_doctor").toString().equalsIgnoreCase("")) {
                            tv_by_remarks_doctor.setText(singledata2.get("remarkrs_by_doctor").toString());
                        } else {
                            tv_remarks_by_doctor.setVisibility(View.GONE);
                            tv_by_remarks_doctor.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_doctor_done").toString().equalsIgnoreCase("")) {
                            if (singledata2.get("is_doctor_done").toString().equalsIgnoreCase("Y")) {
                                tv_doctor_is_done.setText("Yes");
                            } else {
                                tv_doctor_is_done.setText("No");
                            }
                        } else {
                            tv_is_doctor_done.setVisibility(View.GONE);
                            tv_doctor_is_done.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_doctor_done_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("is_doctor_done_on").toString().equalsIgnoreCase("0000-00-00 00:00:00")) {
                            String incomingDate = singledata2.get("is_doctor_done_on").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                                String outputDate = sdf.format(newDate);
                                tv_is_on_doctor_done.setText(outputDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_is_doctor_done_on.setVisibility(View.GONE);
                            tv_is_on_doctor_done.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_pharmacists_done").toString().equalsIgnoreCase("")) {
                            if (singledata2.get("is_pharmacists_done").toString().equalsIgnoreCase("Y")) {
                                tv_pharmacists_is_done.setText("Yes");
                            } else {
                                tv_pharmacists_is_done.setText("No");
                            }
                        } else {
                            tv_is_pharmacists_done.setVisibility(View.GONE);
                            tv_pharmacists_is_done.setVisibility(View.GONE);
                        }
                        if (!singledata2.get("is_pharmacists_done_on").toString().equalsIgnoreCase("")
                                && !singledata2.get("is_pharmacists_done_on").toString().equalsIgnoreCase("0000-00-00 00:00:00")) {
                            String incomingDate = singledata2.get("is_pharmacists_done_on").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date newDate = sdf.parse(incomingDate);
                                sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                                String outputDate = sdf.format(newDate);
                                tv_pharmacists_is_done_on.setText(outputDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_is_pharmacists_done_on.setVisibility(View.GONE);
                            tv_pharmacists_is_done_on.setVisibility(View.GONE);
                        }

                        //pending for retrieve array data
                        //disease, symptom, medicine
                        String disease = "";
                        String symptom = "";
                        String medicine = "";
                        String quantity_by_chemist = "";
                        String prescription_days = "";
                        String prescription_interval = "";
                        String prescription_eating_schedule = "";
                        String prescription_sos = "";
                        String test_name = "";
                        String name = "";
                        String test_value = "";

                        JSONArray diseaseArray = singledata2.getJSONArray("disease");
                        if (diseaseArray != null && diseaseArray.length() > 0) {
                            for (int j = 0; j < diseaseArray.length(); j++) {
                                JSONObject jsonObject = diseaseArray.getJSONObject(j);
                                if (j == 0) {
                                    disease = jsonObject.getString("disease_name");
                                } else {
                                    if (disease != null) {
                                        disease = disease + ", " + jsonObject.getString("disease_name");
                                    }
                                }
                            }
                            if (disease != null) {
                                tv_disease_patient.setText(disease);
                            } else {
                                tv_disease_patient.setVisibility(View.GONE);
                                tv_patient_disease.setVisibility(View.GONE);
                            }
                        } else {
                            tv_patient_disease.setVisibility(View.GONE);
                            tv_disease_patient.setVisibility(View.GONE);
                        }
                        /*get patient remarks here*/
                        if (!singledata2.get("remarks").toString().equalsIgnoreCase("")) {
                            tv_patient_remarkss.setText(singledata2.get("remarks").toString());
                        } else {
                            tv_patient_remarks.setVisibility(View.GONE);
                            tv_patient_remarkss.setVisibility(View.GONE);
                        }
                        JSONArray symptomArray = singledata2.getJSONArray("symptom");
                        if (symptomArray != null && symptomArray.length() > 0) {
                            for (int k = 0; k < symptomArray.length(); k++) {
                                JSONObject jsonObject = symptomArray.getJSONObject(k);
                                if (k == 0) {
                                    symptom = jsonObject.getString("symptom").trim();
                                } else {
                                    if (symptom != null) {
                                        symptom = symptom + ", " + jsonObject.getString("symptom").trim();
                                    }
                                }
                            }
                            if (symptom != null) {
                                tv_patient_symptoms.setText(symptom);
                            } else {
                                tv_symptoms_patient.setVisibility(View.GONE);
                                tv_patient_symptoms.setVisibility(View.GONE);
                            }
                        } else {
                            tv_symptoms_patient.setVisibility(View.GONE);
                            tv_patient_symptoms.setVisibility(View.GONE);
                        }
                        /*for dynamic table layout of medicine*/
                        TableLayout stk = inflatedView.findViewById(R.id.table_main);
                        // stk.setBackgroundResource(R.drawable.tableborder);
                        TableRow tbrow0 = new TableRow(this);
                        //tbrow0.setBackgroundColor(getResources().getColor(R.color.color_light_gray));
                        //tbrow0.setPadding(0,0,1,1);
                        TextView tv0 = new TextView(this);
                        tv0.setText("Medicine");
                        tv0.setTextColor(Color.BLACK);
                        // tv0.setBackgroundColor(getResources().getColor(R.color.color_light_gray));
                        tv0.setBackgroundResource(R.drawable.cell_border);
                        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv0.setTypeface(boldTypeface);
                        tv0.setTextSize(16);
                        tbrow0.addView(tv0);

                        TextView tv1 = new TextView(this);
                        tv1.setText("Days of Prescription");
                        tv1.setTextColor(Color.BLACK);
                        Typeface DaysofPrescription = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv1.setTypeface(DaysofPrescription);
                        tv1.setTextSize(16);
                        tv1.setBackgroundResource(R.drawable.cell_border);
                        tbrow0.addView(tv1);

                        TextView tv2 = new TextView(this);
                        tv2.setText("Prescription Interval");
                        tv2.setTextColor(Color.BLACK);
                        Typeface prescription = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv2.setTypeface(prescription);
                        tv2.setTextSize(16);
                        tv2.setBackgroundResource(R.drawable.cell_border);
                        tbrow0.addView(tv2);

                        TextView tv3 = new TextView(this);
                        tv3.setText("Prescription Eating Schedule");
                        tv3.setTextColor(Color.BLACK);
                        Typeface prescriptionEating = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv3.setTypeface(prescriptionEating);
                        tv3.setTextSize(16);
                        tv3.setBackgroundResource(R.drawable.cell_border);
                        tbrow0.addView(tv3);

                        TextView tv4 = new TextView(this);
                        tv4.setText("Prescription SOS");
                        tv4.setTextColor(Color.BLACK);
                        Typeface prescriptionSOS = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv4.setTypeface(prescriptionSOS);
                        tv4.setTextSize(16);
                        tv4.setBackgroundResource(R.drawable.cell_border);
                        tbrow0.addView(tv4);

                        TextView tv5 = new TextView(this);
                        tv5.setText("Quantity Given by Pharmacist");
                        tv5.setTextColor(Color.BLACK);
                        Typeface QuantityByPharmacist = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv5.setTypeface(QuantityByPharmacist);
                        tv5.setTextSize(16);
                        tv5.setBackgroundResource(R.drawable.cell_border);
                        tbrow0.addView(tv5);

                        stk.addView(tbrow0); //for add all column

                        JSONArray medicineArray = singledata2.getJSONArray("medicine");
                        if (medicineArray != null && medicineArray.length() > 0) {
                            for (int l = 0; l < medicineArray.length(); l++) {
                                JSONObject jsonObject = medicineArray.getJSONObject(l);
                                medicine = jsonObject.getString("medicine_name");
                                quantity_by_chemist = jsonObject.getString("quantity_by_chemist");
                                prescription_days = jsonObject.getString("prescription_days");
                                prescription_interval = jsonObject.getString("prescription_interval_name");
                                prescription_eating_schedule = jsonObject.getString("prescription_eating_schedule");
                                prescription_sos = jsonObject.getString("prescription_sos");

                                TableRow tbrow = new TableRow(this);
                                TextView t1v = new TextView(this);
                                t1v.setText(medicine);
                                t1v.setTextColor(Color.BLACK);
                                t1v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t1v);
                                TextView t2v = new TextView(this);
                                t2v.setText(prescription_days);
                                t2v.setTextColor(Color.BLACK);
                                t2v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t2v);
                                TextView t3v = new TextView(this);
                                t3v.setText(prescription_interval);
                                t3v.setTextColor(Color.BLACK);
                                t3v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t3v);
                                TextView t4v = new TextView(this);
                                t4v.setText(prescription_eating_schedule);
                                t4v.setTextColor(Color.BLACK);
                                t4v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t4v);
                                TextView t5v = new TextView(this);
                                t5v.setText(prescription_sos);
                                t5v.setTextColor(Color.BLACK);
                                t5v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t5v);
                                TextView t6v = new TextView(this);
                                t6v.setText(quantity_by_chemist);
                                t6v.setTextColor(Color.BLACK);
                                t6v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t6v);

                                stk.addView(tbrow);
                            }
                        } else {
                            tv_patient_medicine.setVisibility(View.GONE);
                            tv_medicine_patient.setVisibility(View.GONE);
                            tv_days_prescription.setVisibility(View.GONE);
                            tv_prescription_days.setVisibility(View.GONE);
                            tv_interval_prescription.setVisibility(View.GONE);
                            tv_prescription_interval.setVisibility(View.GONE);
                            tv_eating_schedule_prescription.setVisibility(View.GONE);
                            tv_prescription_eating_schedule.setVisibility(View.GONE);
                            tv_sos_prescription.setVisibility(View.GONE);
                            tv_prescription_sos.setVisibility(View.GONE);
                            stk.setVisibility(View.GONE);
                        }

                        /*for dynamic table layout of test*/
                        TableLayout stkk = inflatedView.findViewById(R.id.table_main_test);
                        TableRow tbrow00 = new TableRow(this);

                    /*TextView tv00 = new TextView(this);
                    tv00.setText("Test Name");
                    tv00.setTextColor(Color.BLACK);
                    tv00.setBackgroundResource(R.drawable.cell_border);
                    Typeface testNameTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    tv00.setTypeface(testNameTypeface);
                    tv00.setTextSize(16);
                    tbrow00.addView(tv00);*/

                        TextView tv11 = new TextView(this);
                        tv11.setText("Test Name");
                        tv11.setTextColor(Color.BLACK);
                        Typeface subTestNameTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv11.setTypeface(subTestNameTypeface);
                        tv11.setTextSize(16);
                        tv11.setBackgroundResource(R.drawable.cell_border);
                        tbrow00.addView(tv11);

                        TextView tv22 = new TextView(this);
                        tv22.setText("Test value");
                        tv22.setTextColor(Color.BLACK);
                        Typeface testValueTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                        tv22.setTypeface(testValueTypeface);
                        tv22.setTextSize(16);
                        tv22.setBackgroundResource(R.drawable.cell_border);
                        tbrow00.addView(tv22);

                        stkk.addView(tbrow00); //for add all column

                        JSONArray testArray = singledata2.getJSONArray("test");
                        if (testArray != null && testArray.length() > 0) {
                            for (int m = 0; m < testArray.length(); m++) {
                                JSONObject jsonObject = testArray.getJSONObject(m);
                                //test_name = jsonObject.getString("test_name");
                                name = jsonObject.getString("name");
                                test_value = jsonObject.getString("test_value");

                                TableRow tbrow = new TableRow(this);

                            /*TextView t1v = new TextView(this);
                            t1v.setText(test_name);
                            t1v.setTextColor(Color.BLACK);
                            t1v.setBackgroundResource(R.drawable.cell_border);
                            tbrow.addView(t1v);*/

                                TextView t2v = new TextView(this);
                                t2v.setText(name);
                                t2v.setTextColor(Color.BLACK);
                                t2v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t2v);

                                TextView t3v = new TextView(this);
                                t3v.setText(test_value);
                                t3v.setTextColor(Color.BLACK);
                                t3v.setBackgroundResource(R.drawable.cell_border);
                                tbrow.addView(t3v);

                                stkk.addView(tbrow);
                            }

                        } else {
                            tv_name_test.setVisibility(View.GONE);
                            tv_test_name.setVisibility(View.GONE);
                            tv_name_subtest.setVisibility(View.GONE);
                            tv_subtest_name.setVisibility(View.GONE);
                            stkk.setVisibility(View.GONE);
                        }
                        JSONArray test_docsArray = singledata2.getJSONArray("test_docs");
                        ArrayList<String> testdocss = new ArrayList<>();

                        if (test_docsArray != null && test_docsArray.length() > 0) {
                            for (int j = 0; j < test_docsArray.length(); j++) {
                                JSONObject jsonObject = test_docsArray.getJSONObject(j);
                                testdocss.add(jsonObject.getString("name"));

                            }
                            rv_test_docs_inprofile.setVisibility(View.VISIBLE);
                            // testDocsAdapterInProfile = new TestDocsAdapterInProfile(context, testdocss);
                            rv_test_docs_inprofile.setLayoutManager(new GridLayoutManager(context, 3));
                            // rv_test_docs_inprofile.setAdapter(testDocsAdapterInProfile);

                        } else {
                            tv_test_docs.setVisibility(View.GONE);
                            iv_test_docs.setVisibility(View.GONE);
                            rv_test_docs_inprofile.setVisibility(View.GONE);
                        }
                    } else {
                        tv_any_taking_prescription.setVisibility(View.GONE);
                        tv_taking_any_prescription.setVisibility(View.GONE);
                        tv_taking_prescription_any_date.setVisibility(View.GONE);
                        tv_date_taking_any_prescription.setVisibility(View.GONE);
                        tv_test_done.setVisibility(View.GONE);
                        tv_is_test_done.setVisibility(View.GONE);
                        tv_is_test_done_date.setVisibility(View.GONE);
                        tv_test_done_date.setVisibility(View.GONE);
                        tv_assigned_doctor.setVisibility(View.GONE);
                        tv_doctor_assigned.setVisibility(View.GONE);
                        tv_assigned_doctor_date.setVisibility(View.GONE);
                        tv_date_assigned_doctor.setVisibility(View.GONE);
                        tv_assigned_pharmacists.setVisibility(View.GONE);
                        tv_pharmacists_assigned.setVisibility(View.GONE);
                        tv_date_pharmacists_assigned.setVisibility(View.GONE);
                        tv_assigned_pharmacists_date.setVisibility(View.GONE);
                        tv_medicine_delivered_on.setVisibility(View.GONE);
                        tv_medicine_delivered.setVisibility(View.GONE);
                        tv_is_verified.setVisibility(View.GONE);
                        tv_verified_is.setVisibility(View.GONE);
                        tv_emergency_any.setVisibility(View.GONE);
                        tv_any_emergency.setVisibility(View.GONE);
                        tv_assigned_by.setVisibility(View.GONE);
                        tv_by_assigned.setVisibility(View.GONE);
                        tv_appointment_type.setVisibility(View.GONE);
                        tv_type_appointment.setVisibility(View.GONE);
                        tv_remarks_by_doctor.setVisibility(View.GONE);
                        tv_by_remarks_doctor.setVisibility(View.GONE);
                        tv_is_doctor_done.setVisibility(View.GONE);
                        tv_doctor_is_done.setVisibility(View.GONE);
                        tv_is_doctor_done_on.setVisibility(View.GONE);
                        tv_is_on_doctor_done.setVisibility(View.GONE);
                        tv_is_pharmacists_done.setVisibility(View.GONE);
                        tv_pharmacists_is_done.setVisibility(View.GONE);
                        tv_is_pharmacists_done_on.setVisibility(View.GONE);
                        tv_pharmacists_is_done_on.setVisibility(View.GONE);
                        tv_patient_disease.setVisibility(View.GONE);
                        tv_disease_patient.setVisibility(View.GONE);
                        tv_symptoms_patient.setVisibility(View.GONE);
                        tv_patient_symptoms.setVisibility(View.GONE);
                        tv_patient_medicine.setVisibility(View.GONE);
                        tv_medicine_patient.setVisibility(View.GONE);
                        tv_days_prescription.setVisibility(View.GONE);
                        tv_prescription_days.setVisibility(View.GONE);
                        tv_interval_prescription.setVisibility(View.GONE);
                        tv_prescription_interval.setVisibility(View.GONE);
                        tv_eating_schedule_prescription.setVisibility(View.GONE);
                        tv_prescription_eating_schedule.setVisibility(View.GONE);
                        tv_sos_prescription.setVisibility(View.GONE);
                        tv_prescription_sos.setVisibility(View.GONE);
                        tv_name_test.setVisibility(View.GONE);
                        tv_test_name.setVisibility(View.GONE);
                        tv_name_subtest.setVisibility(View.GONE);
                        tv_subtest_name.setVisibility(View.GONE);
                        tv_test_docs.setVisibility(View.GONE);
                        iv_test_docs.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ll_for_dynamic_add.addView(inflatedView);
            }


            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(view_prescription_url);

        }


    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(context);
        scrollView = findViewById(R.id.scrollView);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPrescription.this, Prescription.class);
        startActivity(intent);
        finish();
    }
}