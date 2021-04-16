package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ProgressDialog;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.ProfilePojo;
import com.example.jubicare_premium.database.UserPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthRecord extends AppCompatActivity {
    @BindView(R.id.rb_age)
    RadioButton rb_age;
    @BindView(R.id.rb_dob)
    RadioButton rb_dob;
    EditText et_patient_name,et_aadhar_card, et_height, et_weight, et_contact_number, et_address, et_emergenct;
    RadioButton rb_male, rb_female, rb_other;
    RadioGroup rg_gender, rg_age;
    Spinner spn_state, spn_caste, spn_district, spn_block, spn_village;
    LinearLayout ll_age, ll_dob;
    CheckBox cb_emergency;
    String emergency = "N";
    TextView button_submit1;
    private String name = "", gender = "", date_of_birth = "", age = "", aadhar_card = "",
            contact_number = "", address = "", contact_number_for_counsellor = "";
    DatePickerDialog datePickerDialog;
    private int mmYear;
    private int mmMonth;
    private int mmDay;
    public static String age_in_month;
    static EditText et_date_of_birth;
    static EditText et_age;
    ProfilePojo profilePojo;
    SqliteHelper sqliteHelper;
    int state_id, district_id, block_id, village_id;
    int caste_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Health Record" + "</font>"));
        inserid();
        ButterKnife.bind(this);
        Calendar c = Calendar.getInstance();
        mmYear = c.get(Calendar.YEAR); // current year
        mmMonth = c.get(Calendar.MONTH); // current month
        mmDay = c.get(Calendar.DAY_OF_MONTH); //current Day
        sqliteHelper = new SqliteHelper(this);
        profilePojo=new ProfilePojo();

        profilePojo = sqliteHelper.getProfileData();
//        getAllStateFromTable();
//        setStateSpinner();
//        setDistrictSpinner();
//        setBlockSpinner();
//        setPostOfficeSpinner();
//        setVillageSpinner();
//        setBloodGroupSpinner();

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

        rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_age:
                        ll_age.setVisibility(View.VISIBLE);
                        ll_dob.setVisibility(View.GONE);
//                        getDateOfBirthFromAge();
                        break;
                    case R.id.rb_dob:
                        ll_dob.setVisibility(View.VISIBLE);
                        ll_age.setVisibility(View.GONE);
                        break;
                }
            }
        });

        button_submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profilePojo.setAge(et_age.getText().toString());
                profilePojo.setDate_of_birth(et_date_of_birth.getText().toString());
                profilePojo.setAadhar_card(et_aadhar_card.getText().toString());
                profilePojo.setHeight(et_height.getText().toString());
                profilePojo.setWeight(et_weight.getText().toString());
                profilePojo.setContact_number(et_contact_number.getText().toString());
                profilePojo.setAddress(et_address.getText().toString());
                profilePojo.setEmergency(et_emergenct.getText().toString());
                profilePojo.setGender(gender);
                profilePojo.setState(String.valueOf(state_id));
                profilePojo.setCaste(String.valueOf(caste_id));
                profilePojo.setDistrict(String.valueOf(district_id));
                profilePojo.setBlock(String.valueOf(block_id));
                profilePojo.setVillage(String.valueOf(village_id));
                sqliteHelper.saveProfileData(profilePojo);

                Intent intent = new Intent(HealthRecord.this, HomeActivity.class);
                startActivity(intent);
                finish();
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


    @OnClick({R.id.button_submit1,
            R.id.et_contact_number,
            R.id.et_date_of_birth,
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit1:

                break;
            case R.id.et_contact_number:
                //setCallOnEnteredNumber();
                break;
            case R.id.et_date_of_birth:
                selectDate();
                break;


        }
    }
    private void setDateOfBirthClick() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
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
            et_age.setText(getage(dob));
        }
    }

    private void selectDate() {
        Calendar c = Calendar.getInstance();
        mmYear = c.get(Calendar.YEAR); // current year
        mmMonth = c.get(Calendar.MONTH); // current month
        mmDay = c.get(Calendar.DAY_OF_MONTH); //current Day.

        datePickerDialog = new DatePickerDialog(HealthRecord.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mmYear = i;
                mmMonth = i1;
                mmDay = i2;
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                String dob = sdf1.format(c.getTime());
                String formattedDate = sdf.format(c.getTime());
                et_date_of_birth.setText(formattedDate);
                et_age.setText(getage(dob));
            }

        }, mmYear, mmMonth, mmDay);

        datePickerDialog.show();
    }

    public static String getage(String date) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

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

    private void inserid() {
        et_patient_name = findViewById(R.id.et_patient_name);
        et_age = findViewById(R.id.et_age);
        et_date_of_birth = findViewById(R.id.et_date_of_birth);
        et_aadhar_card = findViewById(R.id.et_aadhar_card);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        et_contact_number = findViewById(R.id.et_contact_number);
        et_address = findViewById(R.id.et_address);
        et_emergenct = findViewById(R.id.et_emergenct);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        rb_other = findViewById(R.id.rb_other);
        rb_age = findViewById(R.id.rb_age);
        rb_dob = findViewById(R.id.rb_dob);
        rg_gender = findViewById(R.id.rg_gender);
        rg_age = findViewById(R.id.rg_age);
        spn_state = findViewById(R.id.spn_state);
        spn_caste = findViewById(R.id.spn_caste);
        spn_district = findViewById(R.id.spn_district);
        spn_block = findViewById(R.id.spn_block);
        spn_village = findViewById(R.id.spn_village);
        cb_emergency = findViewById(R.id.cb_emergency);
        ll_age = findViewById(R.id.ll_age);
        ll_dob = findViewById(R.id.ll_dob);
        button_submit1 = findViewById(R.id.button_submit1);

    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HealthRecord.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}