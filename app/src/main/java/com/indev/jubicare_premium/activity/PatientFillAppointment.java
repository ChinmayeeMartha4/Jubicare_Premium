package com.indev.jubicare_premium.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import com.indev.jubicare_premium.FINAL_VAR;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.database.AppointmentInput;
import com.indev.jubicare_premium.database.DiseaseInput;
import com.indev.jubicare_premium.database.DoctorAssignmentInput;
import com.indev.jubicare_premium.database.SignUpModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.R;
//import com.indev.telemedicine.acitivities.NotAssignedAppointments;
//import com.indev.telemedicine.acitivities.PatientListReplicaforSearch;
//import com.indev.telemedicine.acitivities.adapter.DiseaseAdapter;
//import com.indev.telemedicine.acitivities.counsellor.AppointmentStatus;
//import com.indev.telemedicine.acitivities.doctor.DiseaseInput;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
//import com.indev.telemedicine.acitivities.model.SignUpModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
//import com.indev.telemedicine.acitivities.utils.FINAL_VAR;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
//import com.indev.telemedicine.acitivities.utils.WebViewImageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class PatientFillAppointment extends AppCompatActivity {
    @BindView(R.id.et_patient_name)
    EditText et_patient_name;
    @BindView(R.id.et_remarks)
    EditText et_remarks;
    @BindView(R.id.et_remarks_counselor)
    EditText et_remarks_counselor;
    @BindView(R.id.ll_symptomdiease)
    LinearLayout ll_symptomdiease;
    @BindView(R.id.customLinearLayout)
    LinearLayout customLinearLayout;
    @BindView(R.id.ll_dateofPrescription)
    LinearLayout ll_dateofPrescription;
    @BindView(R.id.ll_multiselect)
    LinearLayout ll_multiselect;
    @BindView(R.id.ll_document)
    LinearLayout ll_document;
    @BindView(R.id.simpleSwitch)
    Switch simpleSwitch;
    @BindView(R.id.tv_document)
    TextView tv_document;
    @BindView(R.id.tv_symtoms)
    TextView tv_symtoms;
    @BindView(R.id.tv_patient_symptoms)
    TextView tv_patient_symptoms;
    @BindView(R.id.tv_remarks)
    TextView tv_remarks;
    @BindView(R.id.tv_remarks_counselor)
    TextView tv_remarks_counselor;
    @BindView(R.id.tv_symptoms_patient)
    TextView tv_symptoms_patient;

    /*for date of birth make it static*/
    static EditText et_date_of_birth;
    static EditText et_age;
    @BindView(R.id.et_aadhar_card)
    EditText et_aadhar_card;
    @BindView(R.id.et_pin_code)
    EditText et_pin_code;
    @BindView(R.id.tv_emergency_conytact)
    TextView tv_emergency_conytact;
    @BindView(R.id.ll_emergenct_et)
    LinearLayout ll_emergenct_et;
    @BindView(R.id.tv_pic)
    TextView tv_pic;
    @BindView(R.id.ll_camera)
    LinearLayout ll_camera;
    @BindView(R.id.ll_first)
    LinearLayout ll_first;
    @BindView(R.id.ll_second)
    LinearLayout ll_second;
    @BindView(R.id.ll_third)
    LinearLayout ll_third;

    @BindView(R.id.et_contact_number)
    EditText et_contact_number;
    @BindView(R.id.cb_emergency)
    CheckBox cb_emergency;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_contact_number_for_counsellor)
    EditText et_contact_number_for_counsellor;
    @BindView(R.id.et_emergency_contact)
    EditText et_emergency_contact;
    @BindView(R.id.iv_profile_image)
    ImageView iv_profile_image;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    @BindView(R.id.rgMedicationPrescribed)
    RadioGroup rgMedicationPrescribed;
    @BindView(R.id.rbYesMedicinePrescribed)
    RadioButton rbYesMedicinePrescribed;
    @BindView(R.id.rbNoMedicinePrescribed)
    RadioButton rbNoMedicinePrescribed;
    @BindView(R.id.rgTestDone)
    RadioGroup rgTestDone;
    @BindView(R.id.rbYesTestDone)
    RadioButton rbYesTestDone;
    @BindView(R.id.rbNoTestDone)
    RadioButton rbNoTestDone;

    @BindView(R.id.tv_date_of_prescribed_medication)
    TextView tv_date_of_prescribed_medication;
    @BindView(R.id.tv_test_done_date)
    TextView tv_test_done_date;
    @BindView(R.id.tv_contact_no_for_counsellor)
    TextView tv_contact_no_for_counsellor;
    @BindView(R.id.tv_contact_no_for_patient)
    TextView tv_contact_no_for_patient;
    @BindView(R.id.tv_doctor_assignment)
    TextView tv_doctor_assignment;
    @BindView(R.id.tv_attached_doc)
    TextView tv_attached_doc;
    @BindView(R.id.iv_attachments)
    ImageView iv_attachments;
    @BindView(R.id.spn_symptoms)
    MultiSpinnerSearch spn_symptoms;
    @BindView(R.id.spn_doctor_assignment)
    Spinner spn_doctor_assignment;
    @BindView(R.id.spn_bloodGroup)
    Spinner spn_bloodGroup;
    @BindView(R.id.spn_state)
    Spinner spn_state;
    @BindView(R.id.spn_district)
    Spinner spn_district;
    @BindView(R.id.spn_village)
    Spinner spn_village;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_district)
    EditText et_district;
    @BindView(R.id.et_block)
    EditText et_block;
    @BindView(R.id.spn_post_office)
    Spinner spn_post_office;
    @BindView(R.id.et_post_office)
    EditText et_post_office;
    @BindView(R.id.et_village)
    EditText et_village;

    @BindView(R.id.ll_spn_doctor_assigned)
    LinearLayout ll_spn_doctor_assigned;
    @BindView(R.id.ll_post_office)
    LinearLayout ll_post_office;
    @BindView(R.id.ll_block)
    LinearLayout ll_block;
    @BindView(R.id.ll_district)
    LinearLayout ll_district;
    @BindView(R.id.ll_state)
    LinearLayout ll_state;
    @BindView(R.id.ll_village)
    LinearLayout ll_village;
    @BindView(R.id.ll_et_postoffice)
    LinearLayout ll_et_postoffice;
    @BindView(R.id.ll_et_district)
    LinearLayout ll_et_district;
    @BindView(R.id.ll_et_block)
    LinearLayout ll_et_block;
    @BindView(R.id.ll_et_village)
    LinearLayout ll_et_village;
    @BindView(R.id.ll_et_state)
    LinearLayout ll_et_state;
    @BindView(R.id.ll_remarks_counselor)
    LinearLayout ll_remarks_counselor;

    @BindView(R.id.iv_image_call)
    ImageView iv_image_call;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.et_blood_group)
    EditText et_blood_group;
    @BindView(R.id.et_bplower)
    EditText et_bplower;
    @BindView(R.id.et_bpupper)
    EditText et_bpupper;
    @BindView(R.id.et_sugar)
    EditText et_sugar;
    @BindView(R.id.et_blood_oxygen_level)
    EditText et_blood_oxygen_level;
    @BindView(R.id.et_pulse_in_bpm)
    EditText et_pulse_in_bpm;
    @BindView(R.id.et_temperature)
    EditText et_temperature;

    @BindView(R.id.ll_spn_blood_Group)
    LinearLayout ll_spn_blood_Group;
    @BindView(R.id.ll_blood_group)
    LinearLayout ll_blood_group;
    /*covered area*/
    @BindView(R.id.tv_is_covered_area)
    TextView tv_is_covered_area;
    @BindView(R.id.ll_covered_area)
    LinearLayout ll_covered_area;
    @BindView(R.id.rgCoveredArea)
    RadioGroup rgCoveredArea;
    @BindView(R.id.rbYesCoveredArea)
    RadioButton rbYesCoveredArea;
    @BindView(R.id.rbNoCoveredArea)
    RadioButton rbNoCoveredArea;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    private final static int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 2000;

    AppointmentInput appointmentInput;
    DiseaseInput diseaseInput = new DiseaseInput();
    SignUpModel signUpModel;
    ArrayList<String> symptoms = new ArrayList<>();

    /*normal widgets*/
    private Context context = this;
    @BindView(R.id.spn_block)
    Spinner spn_block;
    private String name = "", gender = "", date_of_birth = "", age = "", aadhar_card = "",
            contact_number = "", address = "", contact_number_for_counsellor = "";
    private String screen_type = "";
    int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog mDatePickerDialog = null;
    Calendar mCalendar;
    InputStream inputStream;
    Bitmap bitmap;
    File destination;
    String imagePath;
    String image64 = "";
    SharedPrefHelper sharedPrefHelper;
    SqliteHelper sqliteHelper;
    int state_id, district_id, block_id, post_office_id, village_id;
    HashMap<String, Integer> stateNameHM, districtNameHM, blockNameHM, postOfficeNameHM, villageNameHM, casteNameHM;
    ProgressDialog mProgressDialog;
    PatientFilledDataModel patientFilledDataModel;
    ArrayList<String> stateArrayList, distrcitArrayList, blockArrayList, postOfficeArrayList, villageArrayList, symptomArrayList, casteArrayList;
    String strSymptom = "";
    HashMap<String, Integer> symptomHM;
    ArrayList<String> symptomAl;
    ArrayList<String> symptomValueAl;
    ArrayList<String> deseaseIds = new ArrayList<>();
    ArrayList<String> docName = new ArrayList<>();
    HashMap<String, Integer> docNameListHm = new HashMap<>();
    String profile_id = "";
    String patient_appointments_id = "";
    String fromCounselor = "";
    String fromCounselorSearch = "";
    String medicinePrescribed = "";
    String emergency = "N";
    String encodedImage = "";
    int doc_id = 0;
    String pin_code;
    ArrayList<Integer> dids = new ArrayList<>();
    DoctorAssignmentInput doctorAssignmentInput;
    String not_assigned_appointments = "";
    String id = "";
    boolean isEditable = false;
    String state_name = "";
    String district_name = "";
    String block_name = "";
    String village_name = "";
    String post_office_name = "";
    ArrayList<String> postOfficeNameAL = new ArrayList<>();
    String postOfficeName = "";
    ArrayList<String> districtAL = new ArrayList<>();
    String districtName = "";
    ArrayList<String> blockAL = new ArrayList<>();
    String blockName = "";
    ArrayList<String> bloodGroupArrayList = new ArrayList<>();
    String bloodGroup = "";
    String bloodGroupId = "";
    String contact_no;
    String coveredArea = "Y";
    android.app.Dialog appointment_alert;
    android.app.Dialog delete_alert;
    SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs;

    /*for validation*/
    private static EditText flagEditfield;
    private static String msg = "";

    DatePickerDialog datePickerDialog;
    private int mmYear;
    private int mmMonth;
    private int mmDay;
//    @BindView(R.id.iv_image1)
//    ImageView iv_image1;
//    TextView person1;
    @BindView(R.id.rg_age)
    RadioGroup rg_age;
    @BindView(R.id.rb_age)
    RadioButton rb_age;
    @BindView(R.id.rb_dob)
    RadioButton rb_dob;
    @BindView(R.id.tv_what_you_know)
    TextView tv_what_you_know;
    @BindView(R.id.tv_caste)
    TextView tv_caste;
    @BindView(R.id.tv_disability)
    TextView tv_disability;
    @BindView(R.id.ll_what_you_know)
    LinearLayout ll_what_you_know;
    @BindView(R.id.ll_spn_caste)
    LinearLayout ll_spn_caste;
    public static String age_in_month;
    @BindView(R.id.ll_age)
    LinearLayout ll_age;
    @BindView(R.id.ll_dob)
    LinearLayout ll_dob;
    String patient_by_mobile_search = "";
    @BindView(R.id.spn_caste)
    Spinner spn_caste;
    @BindView(R.id.rg_disability)
    RadioGroup rg_disability;
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;
    @BindView(R.id.rb_no)
    RadioButton rb_no;
    String disability = "N/A";
    String caste_id;
    String caste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_fill_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        initViews();

        Calendar c = Calendar.getInstance();
        mmYear = c.get(Calendar.YEAR); //current year
        mmMonth = c.get(Calendar.MONTH); //current month
        mmDay = c.get(Calendar.DAY_OF_MONTH); //current Day.

        getAllStateFromTable();
        setStateSpinner();
        setDistrictSpinner();
        setBlockSpinner();
        setPostOfficeSpinner();
        setVillageSpinner();

        setBloodGroupSpinner();

//        setDoctorListSpinner();
        simpleSwitch.setTextOn("Show");
        simpleSwitch.setTextOff("Hide");
        /*get intent values here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            screen_type = bundle.getString("patient", "");
            profile_id = bundle.getString("profile_id", "");
            patient_appointments_id = bundle.getString("patient_appointments_id", "");
            fromCounselor = bundle.getString("fromCounselor", "");
            fromCounselorSearch = bundle.getString("fromCounselorSearch", "");
            not_assigned_appointments = bundle.getString("not_assigned_appointments", "");
            patient_by_mobile_search = bundle.getString("patient_by_mobile_search", "");
            id = bundle.getString("id", "");
            name = bundle.getString("name", "");
        }

//        person1 = findViewById(R.id.person1);
//        iv_image1 = findViewById(R.id.iv_image1);

        //get preference user date here
//        String name = sharedPrefHelper.getString("name", "");
//        String iv_image1 = sharedPrefHelper.getString("iv_image1", "");
//
//
//        person1.setText(name);
//

        if (not_assigned_appointments.equalsIgnoreCase("not_assigned_appointments")) {
            setEditableFalse();
            et_height.setEnabled(false);
            et_weight.setEnabled(false);
            rbYesMedicinePrescribed.setEnabled(false);
            rbNoMedicinePrescribed.setEnabled(false);
            tv_date_of_prescribed_medication.setEnabled(false);
            tv_symptoms_patient.setEnabled(false);
            tv_document.setVisibility(View.GONE);
            ll_document.setVisibility(View.GONE);
            ll_post_office.setVisibility(View.GONE);
            ll_block.setVisibility(View.GONE);
            ll_district.setVisibility(View.GONE);
            ll_et_postoffice.setVisibility(View.VISIBLE);
            ll_et_district.setVisibility(View.VISIBLE);
            ll_et_block.setVisibility(View.VISIBLE);
            ll_et_state.setVisibility(View.VISIBLE);
            ll_et_village.setVisibility(View.VISIBLE);
            ll_spn_blood_Group.setVisibility(View.GONE);
            ll_blood_group.setVisibility(View.VISIBLE);
            et_remarks.setVisibility(View.VISIBLE);
            tv_remarks.setVisibility(View.VISIBLE);
            et_remarks.setEnabled(false);
            ll_remarks_counselor.setVisibility(View.VISIBLE);
            tv_remarks_counselor.setVisibility(View.VISIBLE);
            setTitle("Not Assigned Appointments");
        } else {
            setTitle("Take Appointment");
            ll_remarks_counselor.setVisibility(View.GONE);
            tv_remarks_counselor.setVisibility(View.GONE);
            tv_document.setVisibility(View.VISIBLE);
            ll_document.setVisibility(View.VISIBLE);

        }
        /*condition for show contact no according to screen type*/
        if (screen_type.equalsIgnoreCase("patient")
                || patient_by_mobile_search.equalsIgnoreCase("patient_by_mobile_search")) {
            tv_contact_no_for_counsellor.setVisibility(View.GONE);
            et_contact_number_for_counsellor.setVisibility(View.GONE);
            tv_doctor_assignment.setVisibility(View.GONE);
            ll_spn_doctor_assigned.setVisibility(View.GONE);
            ll_symptomdiease.setVisibility(View.GONE);
            simpleSwitch.setVisibility(View.VISIBLE);
            customLinearLayout.setVisibility(View.GONE);
            ll_post_office.setVisibility(View.GONE);
            ll_block.setVisibility(View.GONE);
            ll_district.setVisibility(View.GONE);
            ll_dateofPrescription.setVisibility(View.GONE);
            iv_image_call.setVisibility(View.GONE);
            ll_spn_blood_Group.setVisibility(View.GONE);
            ll_blood_group.setVisibility(View.VISIBLE);
            /*symptom text*/
            tv_symptoms_patient.setVisibility(View.GONE);
            tv_patient_symptoms.setVisibility(View.GONE);
            setEditableFalse();
        } else {
            tv_contact_no_for_patient.setVisibility(View.GONE);
            et_contact_number.setVisibility(View.GONE);
        }
        if (!patient_appointments_id.equals("")) {
            tv_doctor_assignment.setVisibility(View.VISIBLE);
            ll_spn_doctor_assigned.setVisibility(View.VISIBLE);
            simpleSwitch.setVisibility(View.VISIBLE);
            customLinearLayout.setVisibility(View.GONE);
            ll_symptomdiease.setVisibility(View.GONE);
            tv_emergency_conytact.setVisibility(View.GONE);
            ll_camera.setVisibility(View.GONE);
            ll_emergenct_et.setVisibility(View.GONE);
            tv_pic.setVisibility(View.GONE);
            tv_symtoms.setVisibility(View.GONE);
            ll_multiselect.setVisibility(View.GONE);
        }
        if (fromCounselor.equalsIgnoreCase("fromCounselor")) {
            tv_emergency_conytact.setVisibility(View.VISIBLE);
            ll_first.setVisibility(View.VISIBLE);
            ll_second.setVisibility(View.VISIBLE);
            ll_third.setVisibility(View.VISIBLE);
            ll_camera.setVisibility(View.GONE);
            ll_emergenct_et.setVisibility(View.VISIBLE);
            tv_pic.setVisibility(View.GONE);
            tv_doctor_assignment.setVisibility(View.GONE);
            ll_spn_doctor_assigned.setVisibility(View.GONE);
            ll_symptomdiease.setVisibility(View.GONE);
            customLinearLayout.setVisibility(View.GONE);
            ll_et_postoffice.setVisibility(View.GONE);
            ll_et_district.setVisibility(View.GONE);
            ll_et_state.setVisibility(View.GONE);
            ll_et_block.setVisibility(View.GONE);
            ll_et_village.setVisibility(View.GONE);
            et_state.setEnabled(false);
            et_district.setEnabled(false);
            et_block.setEnabled(false);
            ll_state.setVisibility(View.VISIBLE);
            ll_village.setVisibility(View.VISIBLE);

            tv_what_you_know.setVisibility(View.VISIBLE);
            ll_what_you_know.setVisibility(View.VISIBLE);
            ll_dob.setVisibility(View.GONE);
            ll_age.setVisibility(View.GONE);
            ll_remarks_counselor.setVisibility(View.GONE);
            tv_remarks_counselor.setVisibility(View.GONE);
            tv_symptoms_patient.setVisibility(View.GONE);
            tv_patient_symptoms.setVisibility(View.GONE);
            tv_caste.setVisibility(View.VISIBLE);
            ll_spn_caste.setVisibility(View.VISIBLE);
            tv_disability.setVisibility(View.VISIBLE);
            rg_disability.setVisibility(View.VISIBLE);
        } else {
            tv_caste.setVisibility(View.GONE);
            ll_spn_caste.setVisibility(View.GONE);
            tv_disability.setVisibility(View.GONE);
            rg_disability.setVisibility(View.GONE);
            tv_emergency_conytact.setVisibility(View.GONE);
            ll_camera.setVisibility(View.GONE);
            ll_emergenct_et.setVisibility(View.GONE);
            tv_pic.setVisibility(View.GONE);
        }
        getSpinnerValue();
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (simpleSwitch.isChecked()) {
                    ll_first.setVisibility(View.VISIBLE);
                    ll_second.setVisibility(View.VISIBLE);
                } else {
                    ll_first.setVisibility(View.GONE);
                    ll_second.setVisibility(View.GONE);
                }
            }
        });
        patientFilledDataModel.setProfile_patient_id(profile_id);
        patientFilledDataModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        patientFilledDataModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
        patientFilledDataModel.setPatient_appointment_id(patient_appointments_id);

        Gson gson = new Gson();
        String data = gson.toJson(patientFilledDataModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        /*send data here*/
        if (!fromCounselor.equals("fromCounselor")) {
//            getDetailsPatientAlreadyFilled(body);
        }
        setCasteSpinner();


        iv_image_call.setOnClickListener(v -> {
            if (fromCounselor.equalsIgnoreCase("fromCounselor")) {
                PatientFilledDataModel appointmentInput = new PatientFilledDataModel();
                appointmentInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
                appointmentInput.setRole_id(sharedPrefHelper.getString("role_id", ""));
                Gson gson1 = new Gson();
                String data1 = gson1.toJson(appointmentInput);
                MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");
                RequestBody body1 = RequestBody.create(JSON1, data1);

                mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//                callFormCounsellorFillApp(body1);
            } else {
                PatientFilledDataModel appointmentInput = new PatientFilledDataModel();
                appointmentInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
                appointmentInput.setProfile_patient_id(profile_id);
                appointmentInput.setReciver_no(et_contact_number.getText().toString().trim());
                appointmentInput.setCalling_type("Voice");
                if (not_assigned_appointments.equalsIgnoreCase("not_assigned_appointments")) {
                    appointmentInput.setCalling_screen("Not Assigned Appointments");
                }
                appointmentInput.setPatient_appointment_id(patient_appointments_id);
                appointmentInput.setRole_id(sharedPrefHelper.getString("role_id", ""));

                Gson gson1 = new Gson();
                String data1 = gson1.toJson(appointmentInput);
                MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");
                RequestBody body1 = RequestBody.create(JSON1, data1);

                mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//                callDoctor(body1);
            }

        });

        /*covered area*/
        rgCoveredArea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbYesCoveredArea:
                        coveredArea = "Y";
                        break;
                    case R.id.rbNoCoveredArea:
                        coveredArea = "N";
                        break;
                }
            }
        });
        rg_disability.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_yes:
                        disability = "Yes";
                        break;
                    case R.id.rb_no:
                        disability = "No";
                        break;

                }
            }
        });

        rgMedicationPrescribed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbYesMedicinePrescribed:
                        medicinePrescribed = "Yes";
                        ll_dateofPrescription.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbNoMedicinePrescribed:
                        medicinePrescribed = "No";
                        ll_dateofPrescription.setVisibility(View.GONE);
                        break;
                }
            }
        });

        cb_emergency.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cb_emergency.isChecked()) {
                emergency = "Y";
            } else {
                emergency = "N";
            }
        });

        iv_profile_image.setOnClickListener(view -> setProfilePhotoClick());


        btn_submit.setOnClickListener(v -> {

            int size = spn_symptoms.getSelectedIds().size();
                    if (size != 0) {
                        sendAppointment();
                    }
            if (screen_type.equals("patient") || patient_by_mobile_search.equals("patient_by_mobile_search")) {
//                int size = spn_symptoms.getSelectedIds().size();
                if (size != 0) {
                    sendAppointment();
                } else {
                    Toast.makeText(context, "Please Select Symptoms", Toast.LENGTH_SHORT).show();
                }
            }
            else if (!patient_appointments_id.equals("")) {
            } else {
//                int size = spn_symptoms.getSelectedIds().size();
                if (size != 0) {
                    if (checkValidation()) {
                        sendProfileAndAppointment();
                    }
                } else {
                    Toast.makeText(context, "Please Select Symptoms", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rg_gender.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_male:
                    gender = "M";
                    break;
                case R.id.rb_female:
                    gender = "F";
                    break;
                case R.id.rb_other:
                    gender = "O";
                    break;
            }
        });
        spn_doctor_assignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doc_id = docNameListHm.get(spn_doctor_assignment.getSelectedItem().toString().trim());
                // Toast.makeText(context, ""+doc_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        symptoms.add(0, "Fever");
        symptoms.add(1, "Headache");
        symptoms.add(2, "Vomating");
        symptoms.add(3, "Viral");


        /*get date of birth form age*/
        rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_age:
                        ll_age.setVisibility(View.VISIBLE);
                        ll_dob.setVisibility(View.GONE);
                        getDateOfBirthFromAge();
                        break;
                    case R.id.rb_dob:
                        ll_dob.setVisibility(View.VISIBLE);
                        ll_age.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

    private void getDateOfBirthFromAge() {
        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float aa = 0;
                int year = 0;
                int month = 0;
                String ageee = et_age.getText().toString().trim();
                if (ageee.length() > 0) {
                    if (ageee.contains(".")) {
                        String[] dobDate = ageee.split("\\.");
                        year = Integer.parseInt(dobDate[0]);
                        if (dobDate.length > 1) {
                            month = Integer.parseInt(dobDate[1]);
                        }
                    } else {
                        year = Integer.parseInt(ageee);
                    }
                    if (et_age.getText().toString().trim().equalsIgnoreCase("")) {
                        year = 0;
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - year);
                        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH) + 1 - month);
                        calendar.set(Calendar.DAY_OF_MONTH, 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd", Locale.getDefault());
                        String formatted = dateFormat.format(calendar.getTime());
                        et_date_of_birth.setText(formatted);
                    }
                } else {
                    et_date_of_birth.setText("");
                }
            }
        });
    }

    private void setCasteSpinner() {
        casteArrayList = sqliteHelper.getspnCasteData();
        spn_caste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_caste.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_caste))) {
                    int index = spn_caste.getSelectedItemPosition();
                    caste = casteArrayList.get(index);
                    caste_id = sqliteHelper.getSelectedItemId("caste", caste);
                    //Toast.makeText(context, "" + bloodGroupId, Toast.LENGTH_SHORT).show();
                } else {
                    caste_id = String.valueOf(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        casteArrayList.add(0, getString(R.string.select_caste));
        final ArrayAdapter Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, casteArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_caste.setAdapter(Adapter);
    }

    private void setSpinnerDistrict() {
        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spn_district.getSelectedItemPosition();
                districtName = districtAL.get(index);
                //Toast.makeText(context, ""+districtName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        districtAL.add(0, getString(R.string.select_district));
        final ArrayAdapter Adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, districtAL);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_district.setAdapter(Adapter);
    }


    private void setBloodGroupSpinner() {
        bloodGroupArrayList = sqliteHelper.getspnBloodGroupData();
        spn_bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_bloodGroup.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_bloodgroup))) {
                    int index = spn_bloodGroup.getSelectedItemPosition();
                    bloodGroup = bloodGroupArrayList.get(index);
                    bloodGroupId = sqliteHelper.getSelectedItemId("blood_group", bloodGroup);
                    //Toast.makeText(context, ""+bloodGroupId, Toast.LENGTH_SHORT).show();
                } else {
                    bloodGroupId = String.valueOf(9);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bloodGroupArrayList.add(0, getString(R.string.select_bloodgroup));
        final ArrayAdapter Adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, bloodGroupArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_bloodGroup.setAdapter(Adapter);

    }

    private void setSpinnerBlock() {
        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spn_block.getSelectedItemPosition();
                blockName = blockAL.get(index);
                //Toast.makeText(context, ""+blockName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        blockAL.add(0, getString(R.string.select_block));
        final ArrayAdapter Adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, blockAL);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_block.setAdapter(Adapter);
    }

    private void callPinCodeApi() {
        et_pin_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_state.setText("");
                et_district.setText("");
                et_block.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //sendPinCodeData();
            }
        });
    }

    private void setSpinnerPostOffice() {
        spn_post_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spn_post_office.getSelectedItemPosition();
                postOfficeName = postOfficeNameAL.get(index);
                //Toast.makeText(context, ""+postOfficeName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        postOfficeNameAL.add(0, getString(R.string.select_post_office));
        final ArrayAdapter Adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, postOfficeNameAL);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_post_office.setAdapter(Adapter);
    }



    private void setEditableFalse() {
        et_patient_name.setEnabled(false);
        rb_male.setEnabled(false);
        rb_female.setEnabled(false);
        rb_other.setEnabled(false);
        et_date_of_birth.setEnabled(false);
        et_age.setEnabled(false);
        et_aadhar_card.setEnabled(false);
        et_contact_number.setEnabled(false);
        et_contact_number_for_counsellor.setEnabled(false);
        et_address.setEnabled(false);
        et_pin_code.setEnabled(false);
        spn_state.setEnabled(false);
        spn_district.setEnabled(false);
        spn_block.setEnabled(false);
        spn_block.setEnabled(false);
        spn_village.setEnabled(false);
        et_state.setEnabled(false);
        et_district.setEnabled(false);
        et_block.setEnabled(false);
        spn_post_office.setEnabled(false);
        et_post_office.setEnabled(false);
        et_village.setEnabled(false);
        /*et_height.setEnabled(false);
        et_weight.setEnabled(false);*/
        et_blood_group.setEnabled(false);
    }

    private void sendAppointment() {
        mProgressDialog = ProgressDialog.show(PatientFillAppointment.this, "", "Please Wait...", true);
        appointmentInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
        appointmentInput.setProfile_patient_id(profile_id);
        appointmentInput.setIs_emergency(emergency);
        appointmentInput.setPrescribed_medicine(medicinePrescribed);
        appointmentInput.setRemarks(et_remarks.getText().toString().trim());
        appointmentInput.setPrescribed_medicine_date(tv_date_of_prescribed_medication.getText().toString().trim());
//        appointmentInput.setHeight(et_height.getText().toString().trim());
//        appointmentInput.setWeight(et_weight.getText().toString().trim());
        appointmentInput.setBp_upper(et_bpupper.getText().toString().trim());
        appointmentInput.setBp_lower(et_bplower.getText().toString().trim());
        appointmentInput.setSugar(et_sugar.getText().toString().trim());
        appointmentInput.setTemperature(et_temperature.getText().toString().trim());
        appointmentInput.setBlood_oxygen_level(et_blood_oxygen_level.getText().toString().trim());
        appointmentInput.setPulse(et_pulse_in_bpm.getText().toString().trim());


        int size = spn_symptoms.getSelectedIds().size();
        dids = addDiseasesIds(size);
        String s1 = dids.toString().trim().replace("[", "");
        String ss2 = s1.replace("]", "");
        appointmentInput.setDisease_id(ss2.trim());

        String idsd = "";
        List<KeyPairBoolData> dds = spn_symptoms.getSelectedItems();
        for (int i = 0; i < dds.size(); i++) {
            String name = dds.get(i).getName();

            if (i == 0) {
                idsd = String.valueOf(symptomHM.get(name));
            } else if (idsd != null) {
                idsd = idsd + "," + String.valueOf(symptomHM.get(name));
            }
        }

        appointmentInput.setSymptom_id(idsd.trim());

            appointmentInput.setAppointment_type("1");

        appointmentInput.setApp_version(FINAL_VAR.app_version); //app version for patient
        appointmentInput.setAppointment_file(image64);

        Gson gson1 = new Gson();
        String data1 = gson1.toJson(appointmentInput);
        MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");
        RequestBody body1 = RequestBody.create(JSON1, data1);
        /*send data here*/
        sendAppointmentData(body1);
    }

    private ArrayList<Integer> addDiseasesIds(int size) {
        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ids.add(0);
        }

        return ids;
    }

    private void setProfilePhotoClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendProfileAndAppointment() {
        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
        name = et_patient_name.getText().toString().trim();
        date_of_birth = et_date_of_birth.getText().toString().trim();
        aadhar_card = et_aadhar_card.getText().toString().trim();
        contact_number = et_contact_number_for_counsellor.getText().toString().trim();
        pin_code = et_pin_code.getText().toString().trim();
        address = et_address.getText().toString().trim();
        et_emergency_contact.getText().toString().trim();
        /*sign up model*/
        signUpModel = new SignUpModel();
        signUpModel.setFull_name(name);
        signUpModel.setDob(date_of_birth);
        signUpModel.setHeight(et_height.getText().toString().trim());
        signUpModel.setWeight(et_weight.getText().toString().trim());

        signUpModel.setAadhar_no(aadhar_card);
        signUpModel.setAddress(address);
        signUpModel.setEmergency_contact_no(et_emergency_contact.getText().toString().trim());
        signUpModel.setContact_no(contact_number);
        signUpModel.setGender(gender);
        signUpModel.setPin_code(et_pin_code.getText().toString().trim());
        signUpModel.setBlood_group_id(bloodGroupId);
        signUpModel.setState_id((state_id));
        signUpModel.setDistrict_id(String.valueOf(district_id));
        signUpModel.setBlock_id(String.valueOf(block_id));
        signUpModel.setPost_office_id(String.valueOf(post_office_id));
        signUpModel.setVillage_id((village_id));
        signUpModel.setCaste_id(String.valueOf(caste_id));
        signUpModel.setDisability(disability);
        signUpModel.setCovered_area(coveredArea);
        signUpModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
        signUpModel.setProfile_pic(encodedImage);
        signUpModel.setMobile_token("1234");
        /*here age condition for month*/
        if (et_age.getText().toString().trim().equalsIgnoreCase("0")) {
            if (age_in_month.length() == 1) {
                age_in_month = "0.0" + age_in_month;
            }
            if (age_in_month.length() == 2) {
                age_in_month = "0." + age_in_month;
            }
            signUpModel.setAge(age_in_month);
        } else {
            signUpModel.setAge(et_age.getText().toString().trim());
        }

        signUpModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        signUpModel.setProfile_type("1");
        signUpModel.setApp_version(FINAL_VAR.app_version); //app version for counsellor
        Gson gson = new Gson();
        String data = gson.toJson(signUpModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        /*send data here*/
        sendSignUpData(body);
    }

    private void sendSignUpData(final RequestBody signUpModel) {
        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
        if (signUpModel != null && api_service != null) {
            Call<JsonObject> server_response = api_service.sendSignupData(signUpModel);
            try {
                if (server_response != null) {
                    server_response.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    mProgressDialog.dismiss();
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")) {
                                        String profile_id = jsonObject.getString("profile_id");
                                        appointmentInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
                                        appointmentInput.setProfile_patient_id(profile_id);
                                        appointmentInput.setIs_emergency(emergency);
                                        appointmentInput.setPrescribed_medicine(medicinePrescribed);
                                        appointmentInput.setPrescribed_medicine_date(tv_date_of_prescribed_medication.getText().toString().trim());
                                        appointmentInput.setRemarks(et_remarks.getText().toString().trim());
                                        appointmentInput.setApp_version(FINAL_VAR.app_version);
                                        int size = spn_symptoms.getSelectedIds().size();
                                        dids = addDiseasesIds(size);
                                        dids = addDiseasesIds(size);
                                        String s1 = dids.toString().trim().replace("[", "");
                                        String ss2 = s1.replace("]", "");
                                        appointmentInput.setDisease_id(ss2.trim());
                                        //String symIds = spn_symptoms.getSelectedIds().toString().trim();
                                        //String s = symIds.replace("[", "");
                                        //String ss = s.replace("]", "");
                                        String idsd = "";
                                        List<KeyPairBoolData> dds = spn_symptoms.getSelectedItems();
                                        for (int i = 0; i < dds.size(); i++) {
                                            String name = dds.get(i).getName();

                                            if (i == 0) {
                                                idsd = String.valueOf(symptomHM.get(name));
                                            } else if (idsd != null) {
                                                idsd = idsd + "," + String.valueOf(symptomHM.get(name));
                                            }
                                        }
                                        appointmentInput.setSymptom_id(idsd.trim());
                                        appointmentInput.setAppointment_type("1");
                                        appointmentInput.setAppointment_file(image64);
                                        // Added by ram on 16 March 2020 for a newly added fields
                                        appointmentInput.setBp_upper(et_bpupper.getText().toString().trim());
                                        appointmentInput.setBp_lower(et_bplower.getText().toString().trim());
                                        appointmentInput.setSugar(et_sugar.getText().toString().trim());
                                        appointmentInput.setTemperature(et_temperature.getText().toString().trim());
                                        appointmentInput.setBlood_oxygen_level(et_blood_oxygen_level.getText().toString().trim());
                                        appointmentInput.setPulse(et_pulse_in_bpm.getText().toString().trim());
                                        Gson gson = new Gson();
                                        String data = gson.toJson(appointmentInput);
                                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                        RequestBody body = RequestBody.create(JSON, data);
                                        /*send data here*/
                                     //   sendAppointmentData(body);

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
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }


    private void getSpinnerValue() {
        symptomHM = sqliteHelper.getSymptoms();
        HashMap<String, Integer> sortedMapAsc = sortByComparator(symptomHM);

        symptomAl.clear();
        symptomValueAl.clear();

        for (int i = 0; i < sortedMapAsc.size(); i++) {
            symptomAl.add(sortedMapAsc.keySet().toArray()[i].toString().trim());
            symptomValueAl.add(String.valueOf(sortedMapAsc.get(symptomAl.get(i))));
        }


        final List<KeyPairBoolData> listArray0 = new ArrayList<>();
        for (int i = 0; i < symptomAl.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(symptomAl.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        spn_symptoms.setItems(listArray0, -1, new SpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        symptomAl.add(sortedMapAsc.keySet().toArray()[i].toString().trim());
                        symptomValueAl.add(String.valueOf(sortedMapAsc.get(symptomAl.get(i))));
                    }
                }
            }
        });

    }

    private HashMap<String, Integer> sortByComparator(HashMap<String, Integer> symptomHM) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(symptomHM.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());

            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    private void getAllStateFromTable() {
        stateArrayList.clear();
        stateNameHM = sqliteHelper.getAllState();
        for (int i = 0; i < stateNameHM.size(); i++) {
            stateArrayList.add(stateNameHM.keySet().toArray()[i].toString().trim());
        }

        if (isEditable) {
            stateArrayList.add(0, state_name);
            getAllDistrictFromTable(0);
            getAllBlockFromTable(0);
            getAllVillageFromTable(0);
        } else {
            stateArrayList.add(0, getString(R.string.select_state));
        }
        final ArrayAdapter Adapter = new ArrayAdapter(this, simple_spinner_item, stateArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_state.setAdapter(Adapter);
    }

    private void setStateSpinner() {
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!spn_state.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_state))) {
                    if (spn_state.getSelectedItem().toString().trim() != null) {
                        state_id = stateNameHM.get(spn_state.getSelectedItem().toString().trim());
                        getAllDistrictFromTable(state_id);
                    }

                } else {
                    getAllDistrictFromTable(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getAllDistrictFromTable(int state_id) {
        distrcitArrayList.clear();
        districtNameHM = sqliteHelper.getAllDistrict(state_id);
        for (int i = 0; i < districtNameHM.size(); i++) {
            distrcitArrayList.add(districtNameHM.keySet().toArray()[i].toString().trim());
        }
        if (isEditable) {
            distrcitArrayList.add(0, district_name);
        } else {
            distrcitArrayList.add(0, getString(R.string.select_district));
        }

        final ArrayAdapter Adapter = new ArrayAdapter(this, simple_spinner_item, distrcitArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_district.setAdapter(Adapter);
    }

    private void setDistrictSpinner() {
        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_district.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_district))) {
                    if (spn_district.getSelectedItem().toString().trim() != null) {
                        district_id = districtNameHM.get(spn_district.getSelectedItem().toString().trim());
                        //Toast.makeText(context, "District_id "+district_id, Toast.LENGTH_SHORT).show();
                        getAllBlockFromTable(district_id);
                    }
                } else {
                    getAllBlockFromTable(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAllBlockFromTable(int district_id) {
        blockArrayList.clear();
        blockNameHM = sqliteHelper.getAllBlock(district_id);
        for (int i = 0; i < blockNameHM.size(); i++) {
            blockArrayList.add(blockNameHM.keySet().toArray()[i].toString().trim());
        }
        if (isEditable) {
            blockArrayList.add(0, block_name);
        } else {
            blockArrayList.add(0, getString(R.string.select_block));
        }
        final ArrayAdapter Adapter = new ArrayAdapter(this, simple_spinner_item, blockArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_block.setAdapter(Adapter);
    }

    private void setBlockSpinner() {
        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_block.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_block))) {
                    if (spn_block.getSelectedItem().toString().trim() != null) {
                        block_id = blockNameHM.get(spn_block.getSelectedItem().toString().trim());
                        getAllPostOfficeFromTable(block_id);
                    }
                } else {
                    getAllPostOfficeFromTable(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAllPostOfficeFromTable(int block_id) {
        postOfficeArrayList.clear();
        postOfficeNameHM = sqliteHelper.getAllPostOffice(block_id);
        for (int i = 0; i < postOfficeNameHM.size(); i++) {
            postOfficeArrayList.add(postOfficeNameHM.keySet().toArray()[i].toString().trim());
        }

        postOfficeArrayList.add(0, getString(R.string.select_post_office));
        final ArrayAdapter Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, postOfficeArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_post_office.setAdapter(Adapter);
        //}
    }

    private void setPostOfficeSpinner() {
        spn_post_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_post_office.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_post_office))) {
                    if (spn_post_office.getSelectedItem().toString().trim() != null) {
                        post_office_id = postOfficeNameHM.get(spn_post_office.getSelectedItem().toString().trim());
                        getAllVillageFromTable(post_office_id);
                    }
                } else {
                    getAllVillageFromTable(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAllVillageFromTable(int post_office_id) {
        villageArrayList.clear();
        villageNameHM = sqliteHelper.getAllVillage(post_office_id);
        for (int i = 0; i < villageNameHM.size(); i++) {
            villageArrayList.add(villageNameHM.keySet().toArray()[i].toString().trim());
        }

        if (isEditable) {
            villageArrayList.add(0, village_name);
        } else {
            villageArrayList.add(0, getString(R.string.select_village));
        }
        final ArrayAdapter Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, villageArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_village.setAdapter(Adapter);
        //}
    }

    private void setVillageSpinner() {
        spn_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_village.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_village))) {
                    village_id = villageNameHM.get(spn_village.getSelectedItem().toString().trim());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initViews() {
        mCalendar = Calendar.getInstance();
        et_date_of_birth = findViewById(R.id.et_date_of_birth);
        et_age = findViewById(R.id.et_age);
        tv_date_of_prescribed_medication = findViewById(R.id.tv_date_of_prescribed_medication);

        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        appointmentInput = new AppointmentInput();
        signUpModel = new SignUpModel();
        doctorAssignmentInput = new DoctorAssignmentInput();

        stateNameHM = new HashMap<>();
        districtNameHM = new HashMap<>();
        casteNameHM = new HashMap<>();
        blockNameHM = new HashMap<>();
        postOfficeNameHM = new HashMap<>();
        villageNameHM = new HashMap<>();

        symptomHM = new HashMap<>();
        symptomAl = new ArrayList<>();
        symptomValueAl = new ArrayList<>();

        stateArrayList = new ArrayList<>();
        casteArrayList = new ArrayList<>();
        distrcitArrayList = new ArrayList<>();
        blockArrayList = new ArrayList<>();
        postOfficeArrayList = new ArrayList<>();
        villageArrayList = new ArrayList<>();
        symptomArrayList = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        patientFilledDataModel = new PatientFilledDataModel();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }


    private void sendAppointmentData(RequestBody body) {
        APIClient.getClient().create(TELEMEDICINE_API.class).sendAppointdata(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    mProgressDialog.dismiss();
                    Log.e("chjJC", "njkdvnv " + jsonObject.toString());
                    String success = jsonObject.optString("success");
                    if (success.equals("1")) {
                        String message = jsonObject.optString("message");
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        //Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

                         if (screen_type.equals("patient")) {
                            showAlertDialogForPatientAppointment();
                        } else {
//                            showAlertDialogForCounsellorAppointment();
                        }
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void showAlertDialogForPatientAppointment() {
        appointment_alert = new android.app.Dialog(this);

        appointment_alert.setContentView(R.layout.submit_appointment_dialog);
        appointment_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = appointment_alert.getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        TextView tv_appointment_added = (TextView) appointment_alert.findViewById(R.id.tv_appointment_added);
        TextView tv_appointment_msg = (TextView) appointment_alert.findViewById(R.id.tv_appointment_msg);
        Button btn_ok = (Button) appointment_alert.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_alert.dismiss();
                if (screen_type.equals("patient")) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        });

        appointment_alert.show();
        appointment_alert.setCanceledOnTouchOutside(false);
    }

    private void showAlertDialogForDoctorAssignment() {
        appointment_alert = new android.app.Dialog(this);

        appointment_alert.setContentView(R.layout.submit_appointment_dialog);
        appointment_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = appointment_alert.getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        TextView tv_appointment_added = (TextView) appointment_alert.findViewById(R.id.tv_appointment_added);
        TextView tv_appointment_msg = (TextView) appointment_alert.findViewById(R.id.tv_appointment_msg);
        Button btn_ok = (Button) appointment_alert.findViewById(R.id.btn_ok);

        /*change text dynamically*/
        tv_appointment_added.setText("Doctor Assigned");
        tv_appointment_msg.setText("Doctor has been assigned to patient successfully.");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_alert.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        appointment_alert.show();
        appointment_alert.setCanceledOnTouchOutside(false);
    }

    private void getDetailsPatientAlreadyFilled(RequestBody body) {
        mProgressDialog = ProgressDialog.show(context, "", "Please wait", true);
        TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
        if (body != null && api_service != null) {
            Call<JsonObject> server_response = api_service.download_profile(body);
            try {
                if (server_response != null) {
                    server_response.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful()) {
                                JsonObject singledataP = response.body();
                                Log.e("bjbjcbbd", "onResponsen " + singledataP.toString());
                                mProgressDialog.dismiss();
                                JsonArray data = singledataP.getAsJsonArray("tableData");
                                JsonArray data2 = singledataP.getAsJsonArray("Appointmenthistory"); //commented by vimal
                                // comment because they send Appointmenthistory = null instead of Appointmenthistory = [null]

                                JSONObject singledata = null;
                                JSONObject singledata2 = null;
                                try {
                                    if (!data.isJsonNull() && data.size() > 0) {
                                        singledata = new JSONObject(data.get(0).toString());

                                        String full_name = singledata.getString("full_name");
                                        String contact_no = singledata.getString("contact_no");
                                        String gender = singledata.getString("gender");
                                        String age = singledata.getString("age");

                                        /*change date format here*/
                                        String incomingDateDob = singledata.get("dob").toString();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date newDate = sdf.parse(incomingDateDob);
                                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                                        String outputDateDob = sdf.format(newDate);
                                        et_date_of_birth.setText(outputDateDob);

                                        String aadhar_no = singledata.getString("aadhar_no");
                                        pin_code = singledata.getString("pin_code");
                                        String state_id = singledata.getString("state_id");
                                        state_name = singledata.getString("state_name");
                                        String district_id = singledata.getString("district_id");
                                        district_name = singledata.getString("district_name");
                                        String block_id = singledata.getString("block_id");
                                        block_name = singledata.getString("block_name");
                                        String village_id = singledata.getString("village_id");
                                        village_name = singledata.getString("village_name");
                                        String post_office_id = singledata.getString("post_office_id");
                                        post_office_name = singledata.getString("post_office_name");
                                        String address = singledata.getString("address");
                                        String blood_group_name = singledata.getString("blood_group_name");
                                        String emergency_contact_no = singledata.getString("emergency_contact_no");
                                        String pin_code = singledata.getString("pin_code");
                                        String height = singledata.getString("height");
                                        String weight = singledata.getString("weight");

                                        et_patient_name.setText(full_name);
                                        if (gender.equalsIgnoreCase("M")) {
                                            rb_male.setChecked(true);
                                        } else if (gender.equalsIgnoreCase("F")) {
                                            rb_female.setChecked(true);
                                        } else {
                                            rb_other.setChecked(true);
                                        }
                                        et_age.setText(age);
                                        et_aadhar_card.setText(aadhar_no);
                                        et_contact_number.setText(contact_no);
                                        et_emergency_contact.setText(emergency_contact_no);
                                        et_contact_number_for_counsellor.setText(contact_no);
                                        et_address.setText(address);
                                        if (!pin_code.equalsIgnoreCase("") && !pin_code.equalsIgnoreCase("0")) {
                                            et_pin_code.setText(pin_code);
                                        } else {
                                            et_pin_code.setText("");
                                        }
                                        et_state.setText(state_name);
                                        et_district.setText(district_name);
                                        et_block.setText(block_name);
                                        et_post_office.setText(post_office_name);
                                        et_village.setText(village_name);
                                        et_height.setText(height);
                                        et_weight.setText(weight);


                                        et_blood_group.setText(blood_group_name);
                                    }
                                    isEditable = true;
                                    getAllStateFromTable();

                                    //medical information
                                    if (!data2.isJsonNull() && data2.size() > 0) {
                                        singledata2 = new JSONObject(data2.get(0).toString());
                                        String prescribed_medicine = singledata2.getString("prescribed_medicine");
                                        String prescribed_medicine_date = singledata2.getString("prescribed_medicine_date");
                                        String is_emergency = singledata2.getString("is_emergency");
                                        String remarks = singledata2.getString("remarks");
                                        String bp_lower = singledata2.getString("bp_lower");
                                        String bp_upper = singledata2.getString("bp_upper");
                                        String sugar = singledata2.getString("sugar");
                                        String temperature = singledata2.getString("temperature");
                                        String blood_oxygen_level = singledata2.getString("blood_oxygen_level");
                                        String pulse = singledata2.getString("pulse");
                                        if (not_assigned_appointments.equalsIgnoreCase("not_assigned_appointments")) {
                                            String acctchment = singledata2.getString("appointment_file");

                                            if (!acctchment.equalsIgnoreCase("")) {
                                                tv_attached_doc.setVisibility(View.VISIBLE);
                                                tv_attached_doc.setText(acctchment);
                                                tv_attached_doc.setOnClickListener(new View.OnClickListener() {
                                                    @Override

                                                    public void onClick(View view) {
//                                                        String url = APIClient.IMAGE_URL_DOC_APPO + acctchment;
//                                                        Intent intent = new Intent(context, WebViewImageActivity.class);
//                                                        intent.putExtra("url", url);
//                                                        context.startActivity(intent);
                                                    }
                                                });
                                            } else {
                                                tv_attached_doc.setVisibility(View.GONE);
                                            }

                                            /*remarks show here at the time of assigned doctor*/
                                            if (!remarks.equalsIgnoreCase("")) {
                                                et_remarks.setText(remarks);
                                            } else {
                                            }


                                        }

                                        //set all fields value here
                                        if (!screen_type.equals("patient") ||
                                                !patient_by_mobile_search.equalsIgnoreCase("patient_by_mobile_search")) {
                                            if (prescribed_medicine.equalsIgnoreCase("Yes")) {
                                                rbYesMedicinePrescribed.setChecked(true);
                                            } else if (prescribed_medicine.equalsIgnoreCase("No")) {
                                                rbNoMedicinePrescribed.setChecked(true);
                                            }
                                            if (!prescribed_medicine_date.equalsIgnoreCase("")
                                                    && !prescribed_medicine_date.equalsIgnoreCase("0000-00-00")) {
                                                /*change date format here*/
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                Date newDate = sdf.parse(prescribed_medicine_date);
                                                sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                String outputDateDob = sdf.format(newDate);
                                                tv_date_of_prescribed_medication.setText(outputDateDob);
                                            } else {
                                                tv_date_of_prescribed_medication.setText("");
                                            }
                                        }
                                        if (!screen_type.equals("patient")) {
                                            if (is_emergency.equalsIgnoreCase("Y")) {
                                                cb_emergency.setChecked(true);
                                            } else {
                                                cb_emergency.setChecked(false);
                                            }
                                        }


                                        if (fromCounselorSearch.equals("fromCounselorSearch") || screen_type.equals("patient")) {
                                            cb_emergency.setChecked(false);
                                            tv_date_of_prescribed_medication.setText("");
                                            rbYesMedicinePrescribed.setChecked(false);
                                            rbNoMedicinePrescribed.setChecked(false);

                                        }

                                        String symptom = "";
                                        JSONArray symptomArray = singledata2.getJSONArray("symptom");
                                        if (symptomArray != null && symptomArray.length() > 0) {
                                            for (int i = 0; i < symptomArray.length(); i++) {
                                                JSONObject jsonObject = symptomArray.getJSONObject(i);
                                                if (i == 0) {
                                                    symptom = jsonObject.getString("symptom");
                                                } else {
                                                    if (symptom != null) {
                                                        symptom = symptom + ", " + jsonObject.getString("symptom");
                                                    }
                                                }
                                            }
                                            if (symptom != null) {
                                                tv_symptoms_patient.setText(symptom.trim());
                                            } else {
                                                tv_symptoms_patient.setVisibility(View.GONE);
                                                tv_patient_symptoms.setVisibility(View.GONE);
                                            }
                                        } else {
                                            tv_symptoms_patient.setVisibility(View.GONE);
                                            tv_patient_symptoms.setVisibility(View.GONE);
                                        }


                                        et_bplower.setText(bp_lower);
                                        et_bpupper.setText(bp_upper);
                                        et_sugar.setText(sugar);
                                        et_temperature.setText(temperature);
                                        et_blood_oxygen_level.setText(blood_oxygen_level);
                                        et_pulse_in_bpm.setText(pulse);
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

    @OnClick({R.id.btn_submit,
            R.id.et_contact_number_for_counsellor,
            R.id.et_date_of_birth,
            R.id.tv_date_of_prescribed_medication,
            R.id.tv_test_done_date,
            R.id.iv_attachments})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                /*Intent intent = new Intent(context, CheckDetailOfPatientAppointment.class);
                startActivity(intent);
                finish();*/
                break;
            case R.id.et_contact_number_for_counsellor:
                //setCallOnEnteredNumber();
                break;
            case R.id.et_date_of_birth:
                //setDateOfBirthClick();
                selectDate();
                break;
            case R.id.tv_date_of_prescribed_medication:
                setDate(tv_date_of_prescribed_medication);
                break;
            case R.id.tv_test_done_date:
                //setDate(tv_test_done_date);
                break;
            case R.id.iv_attachments:
                uploadImageOrDocuments(PICK_IMAGE_CAMERA, PICK_IMAGE_GALLERY);
                break;
        }
    }

    private void uploadImageOrDocuments(int pickImageCamera, int pickImageGallery) {
        try {
            final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, PICK_IMAGE_CAMERA);
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStream = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                image64 = encodeTobase64(bitmap);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_" + timeStamp + ".jpg");
                iv_attachments.setImageBitmap(bitmap);
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagePath = destination.getAbsolutePath();
                String[] file1 = imagePath.split("/storage/emulated/0/Android/data/com.indev.telemedicine/files/Pictures/");
                /*attachment.setDocument_source(file1[1]);
                attachment.setDocument_type("uam");
                attachment.setImage_latitude(sharedPrefHelper.getString("LAT", ""));
                attachment.setImage_longitude(sharedPrefHelper.getString("LONG", ""));
                attachment.setEnterprise_id(String.valueOf(enterpriseId));
                sqliteHelper.saveAttachments(attachment);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            try {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    image64 = encodeTobase64(bitmap);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    destination = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_" + timeStamp + ".jpg");
                    iv_attachments.setImageBitmap(bitmap);
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imagePath = destination.getAbsolutePath();
                    String[] file1 = imagePath.split("/storage/emulated/0/Android/data/com.indev.telemedicine/files/Pictures/");
                    /*attachment.setDocument_source(file1[1]);
                    attachment.setDocument_type("uam");
                    attachment.setImage_latitude(sharedPrefHelper.getString("LAT", ""));
                    attachment.setImage_longitude(sharedPrefHelper.getString("LONG", ""));
                    attachment.setEnterprise_id(String.valueOf(enterpriseId));
                    sqliteHelper.saveAttachments(attachment);*/
                } else {
                    //user simply backpressed from gallery
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST) {

            if (data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                iv_profile_image.setImageBitmap(photo);
            }

            // user simply back pressed from gallery
        }
    }

    private String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 15, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void setDate(TextView currentTextView) {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        if (mDatePickerDialog == null) {
            mDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                    //TODO
                    currentTextView.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));

                    mCalendar.set(java.util.Calendar.MONTH, monthOfYear);
                    mCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                    mCalendar.set(java.util.Calendar.YEAR, year);

                }
            }, year, month, day);

                    /*calendar.set(java.util.Calendar.YEAR, 1993);
                    calendar.set(java.util.Calendar.MONTH, 0);
                    calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);*/
            mDatePickerDialog.setCanceledOnTouchOutside(true);
        }

        mDatePickerDialog.show();
    }


    private void selectDate() {
        datePickerDialog = new DatePickerDialog(PatientFillAppointment.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mmYear = i;
                mmMonth = i1;
                mmDay = i2;
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                String dob = sdf1.format(c.getTime());
                String formattedDate = sdf.format(c.getTime());
                et_date_of_birth.setText(formattedDate);
                et_age.setText(getAge(dob));
            }

        }, mmYear, mmMonth, mmDay);

        datePickerDialog.show();
    }

    public void setDateOfBirthClick() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @SuppressLint("NewApi")
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            String dt = day + "-" + month + "-" + year;
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf1.format(c.getTime());
            String formattedDate = sdf.format(c.getTime());
            et_date_of_birth.setText(formattedDate);
            et_age.setText(getAge(dob));
        }
    }

    public static String getAge(String date) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = format.parse(date);
            dob.setTime(date1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long d = dob.getTimeInMillis();
        int year = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        int month = 0, totalDaysDifference = 0;
        if (today.get(Calendar.MONTH) >= dob.get(Calendar.MONTH)) {
            month = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        } else {
            month = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
            month = 12 + month;
            year--;
        }

        if (today.get(Calendar.DAY_OF_MONTH) >= dob.get(Calendar.DAY_OF_MONTH)) {
            totalDaysDifference = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);
        } else {
            totalDaysDifference = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);
            totalDaysDifference = 30 + totalDaysDifference;
            month--;
        }
        double age = (year * 12) + month;
        Integer ageInt = (int) age;

        age_in_month = ageInt.toString(); //for months return this.
        int calAge = (Integer.parseInt(age_in_month) / 12); //for years return this.
        return String.valueOf(calAge);
    }

    private void setCallOnEnteredNumber() {
        contact_number_for_counsellor = et_contact_number_for_counsellor.getText().toString().trim();
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact_number_for_counsellor));
        startActivity(callIntent);
    }

    private boolean checkValidation() {
//        if (et_patient_name.getText().toString().trim().length() == 0) {
//            flagEditfield = et_patient_name;
//            msg = "Please enter name!";
//            flagEditfield.setError(msg);
//            et_patient_name.requestFocus();
//            return false;
//        }
//        if (!et_patient_name.getText().toString().trim().matches("[a-zA-Z ]+")) {
//            flagEditfield = et_patient_name;
//            msg = "The name can only consist of alphabets!";
//            flagEditfield.setError(msg);
//            et_patient_name.requestFocus();
//            return false;
//        }
//        if (rg_gender.getVisibility() == View.VISIBLE) {
//            int selected_id = rg_gender.getCheckedRadioButtonId();
//            if (selected_id <= 0) {
//                Toast.makeText(context, "Please select gender!", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        if (rg_age.getVisibility() == View.VISIBLE) {
//            int selectedId = rg_age.getCheckedRadioButtonId();
//            if (selectedId <= 0) {
//                Toast.makeText(context, "Please choose option 'age or date of birth'.", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        if (rb_age.isChecked() && et_age.getText().toString().trim().length() == 0) {
//            flagEditfield = et_age;
//            msg = "Please enter age!";
//            flagEditfield.setError(msg);
//            et_age.requestFocus();
//            return false;
//        }
//        if (rb_dob.isChecked() && et_date_of_birth.getText().toString().trim().length() == 0) {
//            flagEditfield = et_date_of_birth;
//            msg = "Please select date of birth!";
//            flagEditfield.setError(msg);
//            et_date_of_birth.requestFocus();
//            return false;
//        }
//        if (et_contact_number_for_counsellor.getText().toString().trim().length() == 0) {
//            flagEditfield = et_contact_number_for_counsellor;
//            msg = "Please enter contact number!";
//            flagEditfield.setError(msg);
//            et_contact_number_for_counsellor.requestFocus();
//            return false;
//        }
//        if (et_contact_number_for_counsellor.getText().toString().trim().length() < 10) {
//            flagEditfield = et_contact_number_for_counsellor;
//            msg = "Please enter 10 digits contact number!";
//            flagEditfield.setError(msg);
//            et_contact_number_for_counsellor.requestFocus();
//            return false;
//        }
//        if (et_pin_code.getText().toString().trim().length() == 0) {
//            flagEditfield = et_pin_code;
//            msg = "Please enter pin code!";
//            flagEditfield.setError(msg);
//            et_pin_code.requestFocus();
//            return false;
//        }
//        if (spn_state.getSelectedItem().toString().trim().equalsIgnoreCase("Select State")) {
//            TextView errorText = (TextView) spn_state.getSelectedView();
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Please select state!");
//            Toast.makeText(context, "Please select state!", Toast.LENGTH_LONG).show();
//            errorText.requestFocus();
//            return false;
//        }
//        if (spn_district.getSelectedItem().toString().trim().equalsIgnoreCase("Select District")) {
//            TextView errorText = (TextView) spn_district.getSelectedView();
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Please select district!");
//            Toast.makeText(context, "Please select district!", Toast.LENGTH_LONG).show();
//            errorText.requestFocus();
//            return false;
//        }
//        if (spn_block.getSelectedItem().toString().trim().equalsIgnoreCase("Select Block")) {
//            TextView errorText = (TextView) spn_block.getSelectedView();
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Please select block!");
//            Toast.makeText(context, "Please select block!", Toast.LENGTH_LONG).show();
//            errorText.requestFocus();
//            return false;
//        }
//        if (spn_post_office.getSelectedItem().toString().trim().equalsIgnoreCase("Select Post Office")) {
//            TextView errorText = (TextView) spn_post_office.getSelectedView();
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Please select post office!");
//            Toast.makeText(context, "Please select post office!", Toast.LENGTH_LONG).show();
//            errorText.requestFocus();
//            return false;
//        }
//        if (spn_village.getSelectedItem().toString().trim().equalsIgnoreCase("Select Village")) {
//            TextView errorText = (TextView) spn_village.getSelectedView();
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Please select village!");
//            Toast.makeText(context, "Please select village!", Toast.LENGTH_LONG).show();
//            errorText.requestFocus();
//            return false;
//        }
//        if (et_address.getText().toString().trim().length() == 0) {
//            flagEditfield = et_address;
//            msg = "Please enter address!";
//            flagEditfield.setError(msg);
//            et_address.requestFocus();
//            return false;
//        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        /*show hide toolbar item here*/
//        if (not_assigned_appointments.equalsIgnoreCase("not_assigned_appointments")) {
////            MenuItem item = menu.findItem(R.id.deleteAppointment);
//            item.setVisible(true);
//        }
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) finish();
//
////        if (item.getItemId() == R.id.deleteAppointment) {
////            showDeleteAppointmentPopup();
////        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void showDeleteAppointmentPopup() {
//        delete_alert = new android.app.Dialog(this);
//
//        delete_alert.setContentView(R.layout.delete_appointment_alert_dialog);
//        delete_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams params = delete_alert.getWindow().getAttributes();
//        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
//
//        TextView tv_appointment_added = (TextView) delete_alert.findViewById(R.id.tv_appointment_added);
//        TextView tv_appointment_msg = (TextView) delete_alert.findViewById(R.id.tv_appointment_msg);
//        EditText et_reason = (EditText) delete_alert.findViewById(R.id.et_reason);
//        Button btn_yes = (Button) delete_alert.findViewById(R.id.btn_yes);
//        Button btn_no = (Button) delete_alert.findViewById(R.id.btn_no);
//
//        btn_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TO DO
//                if (checkValidationET(et_reason)) {
//                    delete_alert.dismiss();
//                    deleteAppointmentApiCall(et_reason);
//                }
//            }
//        });
//        btn_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TO DO
//                delete_alert.dismiss();
//            }
//        });
//
//        delete_alert.show();
//        delete_alert.setCanceledOnTouchOutside(false);
//    }

    private boolean checkValidationET(EditText et_reason) {
        if (et_reason.getText().toString().trim().length() == 0) {
            String msg = "Please enter reason.";
            et_reason.setError(msg);
            et_reason.requestFocus();
            return false;
        }
        return true;
    }


@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        Intent intent = new Intent(PatientFillAppointment.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    return super.onOptionsItemSelected(item);
}
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        }
//        if (patient_by_mobile_search.equalsIgnoreCase("patient_by_mobile_search")) {
//            Intent intent = new Intent(context, PatientListReplicaforSearch.class);
//            startActivity(intent);
//            finish();
//        }
//        if (not_assigned_appointments.equalsIgnoreCase("not_assigned_appointments")) {
//            Intent intent = new Intent(context, NotAssignedAppointments.class);
//            intent.putExtra("fromNotAssignment", "fromNotAssignment");
//            startActivity(intent);
//            finish();
//        }

}