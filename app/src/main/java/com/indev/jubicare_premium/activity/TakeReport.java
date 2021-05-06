package com.indev.jubicare_premium.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.AppointmentInput;
import com.indev.jubicare_premium.database.DiseaseInput;
import com.indev.jubicare_premium.database.DoctorAssignmentInput;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
import com.indev.jubicare_premium.database.SignUpModel;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakeReport extends AppCompatActivity {


    /*for date of birth make it static*/
    static EditText et_date_of_birth;
    static EditText et_age;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.et_doctor_name)
    EditText et_doctor_name;



    @BindView(R.id.et_test)
    EditText et_test;

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


    String disability = "N/A";
    String caste_id;
    String caste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Reports" + "</font>"));

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(TakeReport.this, Reports.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, Reports.class);
        startActivity(intent);
        finish();

    }
}