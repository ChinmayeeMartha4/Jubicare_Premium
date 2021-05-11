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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.AppointmentInput;
import com.indev.jubicare_premium.database.DiseaseInput;
import com.indev.jubicare_premium.database.DoctorAssignmentInput;
import com.indev.jubicare_premium.database.PatientFilledDataModel;
import com.indev.jubicare_premium.database.PrescriptionModel;
import com.indev.jubicare_premium.database.SignUpModel;
import com.indev.jubicare_premium.database.TakeReportModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

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

public class TakeReport extends AppCompatActivity {


    /*for date of birth make it static*/
    static EditText et_date_of_birth;
    static EditText et_age;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.iv_attachments)
    ImageView iv_attachments;

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
    TakeReportModel takeReportModel;
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
    String id = "";
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
        initViews();


        Calendar c = Calendar.getInstance();
        mmYear = c.get(Calendar.YEAR); //current year
        mmMonth = c.get(Calendar.MONTH); //current month
        mmDay = c.get(Calendar.DAY_OF_MONTH); //current Day.




        /*get intent values here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            screen_type = bundle.getString("patient", "");
            profile_id = bundle.getString("profile_id", "");
            patient_appointments_id = bundle.getString("patient_appointments_id", "");
            fromCounselor = bundle.getString("fromCounselor", "");
            fromCounselorSearch = bundle.getString("fromCounselorSearch", "");
            not_assigned_appointments = bundle.getString("not_assigned_appointments", "");
            id = bundle.getString("id", "");

        }


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
        btn_submit.setOnClickListener(v -> {

//            int size = spn_symptoms.getSelectedIds().size();
//            if (size != 0) {
                sendAppointment();
//            }
        });
//        symptoms.add(0, "Fever");
//        symptoms.add(1, "Headache");
//        symptoms.add(2, "Vomating");
//        symptoms.add(3, "Viral");
    }

    private void setEditableFalse() {
        tv_date.setEnabled(false);
        et_doctor_name.setEnabled(false);
        et_test.setEnabled(false);
//        spn_symptoms.setEnabled(false);
        et_date_of_birth.setEnabled(false);
        et_age.setEnabled(false);

    }

    private void sendAppointment() {
        mProgressDialog = ProgressDialog.show(TakeReport.this, "", "Please Wait...", true);
        takeReportModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
//        takeReportModel.setProfile_patient_id(profile_id);
        takeReportModel.setDate(tv_date.getText().toString().trim());
        takeReportModel.setDoctor_name(et_doctor_name.getText().toString().trim());
        takeReportModel.setTest(et_test.getText().toString().trim());
        takeReportModel.setPhoto(image64);
        Gson gson1 = new Gson();
        String data1 = gson1.toJson(takeReportModel);
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

    private void initViews() {
        mCalendar = Calendar.getInstance();
        et_date_of_birth = findViewById(R.id.et_date_of_birth);
        et_age = findViewById(R.id.et_age);

        sqliteHelper = new SqliteHelper(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        takeReportModel = new TakeReportModel();
        signUpModel = new SignUpModel();
        doctorAssignmentInput = new DoctorAssignmentInput();

//        symptomHM = new HashMap<>();
//        symptomAl = new ArrayList<>();
//        symptomValueAl = new ArrayList<>();
//        symptomArrayList = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        patientFilledDataModel = new PatientFilledDataModel();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }


    private void sendAppointmentData(RequestBody body) {
        APIClient.getClient().create(TELEMEDICINE_API.class).sendReportdata(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    mProgressDialog.dismiss();
                    Log.e("chjJC", "njkdvnv " + jsonObject.toString());
                    String success = jsonObject.optString("success");
                    if (success.equals("1")) {
                        String message = jsonObject.optString("message");
                        Intent intent = new Intent(context, Reports.class);
                        startActivity(intent);
                        finish();

                        if (screen_type.equals("patient")) {
                            showAlertDialogForPatientAppointment();
                        } else {
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
                    Intent intent = new Intent(context, OldPrescription.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        });

        appointment_alert.show();
        appointment_alert.setCanceledOnTouchOutside(false);
    }




    @OnClick({R.id.btn_submit,
            R.id.tv_date,
            R.id.iv_attachments})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                /*Intent intent = new Intent(context, CheckDetailOfPatientAppointment.class);
                startActivity(intent);
                finish();*/
                break;


            case R.id.tv_date:
                setDate(tv_date);
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
//                    iv_profile_image.setImageBitmap(photo);
            }

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
//                et_age.setText(getAge(dob));
        }
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