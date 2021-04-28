package com.example.jubicare_premium.database;

public class OldAppointmentPojo {
    private String date;
    private String doctor_name;
    private String user_id;
    private String profile_patient_id;

    public String getProfile_patient_id() {
        return profile_patient_id;
    }

    public void setProfile_patient_id(String profile_patient_id) {
        this.profile_patient_id = profile_patient_id;
    }

    public String getPatient_appointment_id() {
        return patient_appointment_id;
    }

    public void setPatient_appointment_id(String patient_appointment_id) {
        this.patient_appointment_id = patient_appointment_id;
    }

    private String patient_appointment_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    private String role_id;

    public String getDoctor_name1() {
        return doctor_name1;
    }

    public void setDoctor_name1(String doctor_name1) {
        this.doctor_name1 = doctor_name1;
    }

    private String doctor_name1;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }



    private static final String TABLE_NAME= "appointment";
    private static final String COLUMN_LOCAL_ID= "local_id";
    private static final String COLUMN_APPOINTMENT_ID= "appointment_id";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_DOCTOR_NAME= "doctor_name";
    private static final String COLUMN_DATE1= "date1";
    private static final String COLUMN_DOCTOR_NAME1= "doctor_name1";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_APPOINTMENT_ID + " INTEGER,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_DOCTOR_NAME + " TEXT ,"
                    + COLUMN_DATE1 + " TEXT,"
                    + COLUMN_DOCTOR_NAME1 + " TEXT "
                    + ")";


}
