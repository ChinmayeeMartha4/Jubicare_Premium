package com.indev.jubicare_premium.database;

public class PrescriptionModel {
    private String user_id;
    private String patient_appointment_id;
    private String symptom_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPatient_appointment_id() {
        return patient_appointment_id;
    }

    public void setPatient_appointment_id(String patient_appointment_id) {
        this.patient_appointment_id = patient_appointment_id;
    }

    public String getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(String symptom_id) {
        this.symptom_id = symptom_id;
    }

    public String getAppointment_file() {
        return appointment_file;
    }

    public void setAppointment_file(String appointment_file) {
        this.appointment_file = appointment_file;
    }

    public String getProfile_patient_id() {
        return profile_patient_id;
    }

    public void setProfile_patient_id(String profile_patient_id) {
        this.profile_patient_id = profile_patient_id;
    }

    public String getPrescribed_medicine_date() {
        return prescribed_medicine_date;
    }

    public void setPrescribed_medicine_date(String prescribed_medicine_date) {
        this.prescribed_medicine_date = prescribed_medicine_date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    private String appointment_file;
    private String profile_patient_id;
    private String prescribed_medicine_date;
    private String doctor_name;
    private String medicine;

}