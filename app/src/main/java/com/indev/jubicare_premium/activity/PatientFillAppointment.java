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
import android.text.Html;
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
    int personID = 0;
    String personName = "";
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
    @BindView(R.id.iv_image1)
    ImageView iv_image1;
    @BindView(R.id.person1)
    TextView person1;
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
PatientModel patientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_fill_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Take Appointment" + "</font>"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        initViews();
        getSpinnerValue();

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
//            profile_id = bundle.getString("profile_id", "");

            fromCounselor = bundle.getString("fromCounselor", "");
            fromCounselorSearch = bundle.getString("fromCounselorSearch", "");
            not_assigned_appointments = bundle.getString("not_assigned_appointments", "");
            patient_by_mobile_search = bundle.getString("patient_by_mobile_search", "");
            personID = bundle.getInt("personID", 0);
            personName = bundle.getString("personName", "");
            patient_appointments_id = bundle.getString("patient_appointments_id", "");
        }

        getSpinnerValue();

        patientFilledDataModel.setProfile_patient_id(String.valueOf(personID));
        patientFilledDataModel.setFull_name(sharedPrefHelper.getString("personName", ""));
        patientFilledDataModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
        patientFilledDataModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
        patientFilledDataModel.setPatient_appointment_id(patient_appointments_id);
//        String personName = sharedPrefHelper.getString("personName", "");

        person1.setText(personName);

        Gson gson = new Gson();
        String data = gson.toJson(patientFilledDataModel);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        /*send data here*/
        if (!fromCounselor.equals("fromCounselor")) {
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
                appointmentInput.setProfile_patient_id(id);
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
//                        sendProfileAndAppointment();
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

    private void sendAppointment() {
        mProgressDialog = ProgressDialog.show(PatientFillAppointment.this, "", "Please Wait...", true);
        appointmentInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
        appointmentInput.setProfile_patient_id(String.valueOf(personID));
        appointmentInput.setIs_emergency(emergency);
        appointmentInput.setPrescribed_medicine(medicinePrescribed);
        appointmentInput.setRemarks(et_remarks.getText().toString().trim());
        appointmentInput.setPrescribed_medicine_date(tv_date_of_prescribed_medication.getText().toString().trim());
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
        person1 = findViewById(R.id.person1);
        tv_date_of_prescribed_medication = findViewById(R.id.tv_date_of_prescribed_medication);

        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        appointmentInput = new AppointmentInput();
        patientModel = new PatientModel();
        signUpModel = new SignUpModel();
        doctorAssignmentInput = new DoctorAssignmentInput();

        person1 = findViewById(R.id.person1);
        iv_image1 = findViewById(R.id.iv_image1);
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

    @OnClick({R.id.btn_submit,
            R.id.et_contact_number_for_counsellor,
            R.id.et_date_of_birth,
            R.id.tv_date_of_prescribed_medication,
            R.id.tv_test_done_date,
            R.id.iv_attachments})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                break;
            case R.id.et_contact_number_for_counsellor:
                //setCallOnEnteredNumber();
                break;
            case R.id.et_date_of_birth:
                selectDate();
                break;
            case R.id.tv_date_of_prescribed_medication:
                setDate(tv_date_of_prescribed_medication);
                break;
            case R.id.tv_test_done_date:
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
                } else {
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

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        /*show hide toolbar item here*/

        return true;
    }

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
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        }

}