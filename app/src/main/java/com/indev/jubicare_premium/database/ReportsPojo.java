package com.indev.jubicare_premium.database;

public class ReportsPojo {
    private String date;
    private String title;

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    private String doctor_name;
    private String user_id;
    private String profile_patient_id;
    private String test;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    private String full_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    private String patient_appointment_id;
    private String role_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



//    private static final String TABLE_NAME= "reports";
//    private static final String COLUMN_LOCAL_ID= "local_id";
//    private static final String COLUMN_REPORTS_ID= "report_id";
//    private static final String COLUMN_DATE= "date";
//    private static final String COLUMN_TITLE= "title";
//
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_REPORTS_ID + " INTEGER,"
//                    + COLUMN_DATE + " TEXT ,"
//                    + COLUMN_TITLE + " TEXT "
//                    + ")";


}
