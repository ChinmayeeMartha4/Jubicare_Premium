package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

public class TakeAppointment extends AppCompatActivity {
AppointmentPojo appointmentPojo;
SqliteHelper sqliteHelper;
EditText et_bplower,et_bpupper,et_sugar,et_temperature,et_blood_oxygen_level,et_pulse_in_bpm,et_remarks,et_date_of_prescribed_medication;
    Spinner spn_bloodGroup,spn_symptoms;
    RadioGroup rgMedicationPrescribed;
    RadioButton rbYesMedicinePrescribed,rbNoMedicinePrescribed;
    ImageView iv_attachments;
    TextView button_old_appointment,button_submit;
    int blood_id,symptom_id;
    CheckBox cb_emergency;
    String emergency = "N",MedicinePrescribed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appointment);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Take Appointment" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        appointmentPojo=new AppointmentPojo();

        initId();
//        appointmentPojo = sqliteHelper.getAppointmentsData();

        rgMedicationPrescribed.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rbYesMedicinePrescribed:
                    MedicinePrescribed = "Yes";
                    break;
                case R.id.rbNoMedicinePrescribed:
                    MedicinePrescribed = "No";
                    break;

            }
        });
        cb_emergency.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cb_emergency.isChecked()) {
                emergency = "Y";
            } else {
                emergency = "N";
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointmentPojo.setBp_low(et_bplower.getText().toString());
                appointmentPojo.setBp_high(et_bpupper.getText().toString());
                appointmentPojo.setSugar(et_sugar.getText().toString());
                appointmentPojo.setTemperature(et_temperature.getText().toString());
                appointmentPojo.setBlood_oxygen_level(et_blood_oxygen_level.getText().toString());
                appointmentPojo.setPulse(et_pulse_in_bpm.getText().toString());
                appointmentPojo.setPatient_remarks(et_remarks.getText().toString());
                appointmentPojo.setPrescribed_medicine_date(et_date_of_prescribed_medication.getText().toString());
                appointmentPojo.setPrescribed_medicine(MedicinePrescribed);
                appointmentPojo.setBlood_group_id(String.valueOf(blood_id));
                appointmentPojo.setSymptom_id(String.valueOf(symptom_id));
                appointmentPojo.setIs_emergency(String.valueOf(emergency));
//                sqliteHelper.saveappointmentsData(appointmentPojo);

                Intent intent = new Intent(TakeAppointment.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initId() {
        et_bplower = findViewById(R.id.et_bplower);
        et_bpupper = findViewById(R.id.et_bpupper);
        et_sugar = findViewById(R.id.et_sugar);
        et_temperature = findViewById(R.id.et_temperature);
        et_blood_oxygen_level = findViewById(R.id.et_blood_oxygen_level);
        et_pulse_in_bpm = findViewById(R.id.et_pulse_in_bpm);
        et_remarks = findViewById(R.id.et_remarks);
        et_date_of_prescribed_medication = findViewById(R.id.et_date_of_prescribed_medication);
        spn_bloodGroup = findViewById(R.id.spn_bloodGroup);
        spn_symptoms = findViewById(R.id.spn_symptoms);
        rgMedicationPrescribed = findViewById(R.id.rgMedicationPrescribed);
        rbYesMedicinePrescribed = findViewById(R.id.rbYesMedicinePrescribed);
        rbNoMedicinePrescribed = findViewById(R.id.rbNoMedicinePrescribed);
        iv_attachments = findViewById(R.id.iv_attachments);
        button_old_appointment = findViewById(R.id.button_old_appointment);
        button_submit = findViewById(R.id.button_submit);
        cb_emergency = findViewById(R.id.cb_emergency);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TakeAppointment.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}