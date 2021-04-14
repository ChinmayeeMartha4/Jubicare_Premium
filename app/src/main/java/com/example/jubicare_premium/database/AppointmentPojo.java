package com.example.jubicare_premium.database;

public class AppointmentPojo {
    private String date;
    private String doctor_name;
    private String date1;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

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
