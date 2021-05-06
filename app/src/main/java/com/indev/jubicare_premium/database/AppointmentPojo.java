package com.indev.jubicare_premium.database;

public class AppointmentPojo {
    private String profile_patient_id;
    private String prescribed_medicine;
    private String prescribed_medicine_date;
    private String is_emergency;
    private String bp_high;
    private String bp_low;
    private String sugar;
    private String temperature;
    private String blood_oxygen_level;
    private String pulse;
    private String blood_group_id;

    public String getProfile_patient_id() {
        return profile_patient_id;
    }

    public void setProfile_patient_id(String profile_patient_id) {
        this.profile_patient_id = profile_patient_id;
    }

    public String getPrescribed_medicine() {
        return prescribed_medicine;
    }

    public void setPrescribed_medicine(String prescribed_medicine) {
        this.prescribed_medicine = prescribed_medicine;
    }

    public String getPrescribed_medicine_date() {
        return prescribed_medicine_date;
    }

    public void setPrescribed_medicine_date(String prescribed_medicine_date) {
        this.prescribed_medicine_date = prescribed_medicine_date;
    }

    public String getIs_emergency() {
        return is_emergency;
    }

    public void setIs_emergency(String is_emergency) {
        this.is_emergency = is_emergency;
    }

    public String getBp_high() {
        return bp_high;
    }

    public void setBp_high(String bp_high) {
        this.bp_high = bp_high;
    }

    public String getBp_low() {
        return bp_low;
    }

    public void setBp_low(String bp_low) {
        this.bp_low = bp_low;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getBlood_oxygen_level() {
        return blood_oxygen_level;
    }

    public void setBlood_oxygen_level(String blood_oxygen_level) {
        this.blood_oxygen_level = blood_oxygen_level;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBlood_group_id() {
        return blood_group_id;
    }

    public void setBlood_group_id(String blood_group_id) {
        this.blood_group_id = blood_group_id;
    }

    public String getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(String symptom_id) {
        this.symptom_id = symptom_id;
    }

    public String getPatient_appointment_id() {
        return patient_appointment_id;
    }

    public void setPatient_appointment_id(String patient_appointment_id) {
        this.patient_appointment_id = patient_appointment_id;
    }

    public String getPatient_remarks() {
        return patient_remarks;
    }

    public void setPatient_remarks(String patient_remarks) {
        this.patient_remarks = patient_remarks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String symptom_id;
    private String patient_appointment_id;
    private String patient_remarks;
    private String image;

    private static final String TABLE_NAME= "appointments";
    private static final String COLUMN_LOCAL_ID= "local_id";
    private static final String COLUMN_PROFILE_PATIENT_ID= "profile_patient_id";
    private static final String COLUMN_PRESCRIBED_MEDICINE= "prescribed_medicine";
    private static final String COLUMN_PRESCRIBED_MEDICINE_DATE= "prescribed_medicine_date";
    private static final String COLUMN_IS_EMERGENCY= "is_emergency";
    private static final String COLUMN_BP_HIGH= "bp_high";
    private static final String COLUMN_BP_LOW= "bp_low";
    private static final String COLUMN_SUGAR= "sugar";
    private static final String COLUMN_TEMPERATURE= "temperature";
    private static final String COLUMN_BLOOD_OXYGEN_LEVEL= "blood_oxygen_level";
    private static final String COLUMN_PULSE= "pulse";
    private static final String COLUMN_BLOOD_GROUP_ID= "blood_group_id";
    private static final String COLUMN_SYMPTOM_ID= "symptom_id";
    private static final String COLUMN_PATIENT_APPOINTMENT_ID= "patient_appointment_id";
    private static final String COLUMN_PATIENT_REMARKS= "patient_remarks";
    private static final String COLUMN_iMAGE= "image";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BLOOD_GROUP_ID + " INTEGER,"
                    + COLUMN_PROFILE_PATIENT_ID + " INTEGER,"
                    + COLUMN_PRESCRIBED_MEDICINE + " TEXT,"
                    + COLUMN_PRESCRIBED_MEDICINE_DATE + " TEXT,"
                    + COLUMN_IS_EMERGENCY + " TEXT,"
                    + COLUMN_BP_HIGH + " TEXT,"
                    + COLUMN_BP_LOW + " TEXT,"
                    + COLUMN_SUGAR + " TEXT,"
                    + COLUMN_TEMPERATURE + " TEXT,"
                    + COLUMN_BLOOD_OXYGEN_LEVEL + " TEXT,"
                    + COLUMN_PULSE + " TEXT,"
                    + COLUMN_SYMPTOM_ID + " TEXT,"
                    + COLUMN_PATIENT_APPOINTMENT_ID + " TEXT ,"
                    + COLUMN_PATIENT_REMARKS + " TEXT,"
                    + COLUMN_iMAGE + " TEXT "
                    + ")";

}
