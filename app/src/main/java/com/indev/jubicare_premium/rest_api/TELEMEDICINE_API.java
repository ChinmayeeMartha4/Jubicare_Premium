package com.indev.jubicare_premium.rest_api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TELEMEDICINE_API {
    @POST("login.php")
    Call<JsonObject> login(@Body RequestBody body);

    @POST("signup.php")
    Call<JsonObject> sendSignupData(@Body RequestBody sign);

    @POST("edit_profile_patient.php")
    Call<JsonObject> sendEditProfileData(@Body RequestBody body);

    @POST("payment.php")
    Call<JsonObject> setpaymentData(@Body RequestBody body);

    @POST("download_listing_doctors.php")
    Call<JsonObject> download_DoctortList(@Body RequestBody body);

    @POST("profile.php")
    Call<JsonObject> download_profile(@Body RequestBody body);

    @POST("download_old_prescription.php")
    Call<JsonObject> download_old_prescription(@Body RequestBody body);

    @POST("download_org.php")
    Call<JsonObject> download_organization(@Body RequestBody body);

    @POST("download_appointments.php")
    Call<JsonObject> download_appointments(@Body RequestBody body);

    @POST("download_reports.php")
    Call<JsonObject> download_reports(@Body RequestBody body);

    @POST("payment.php")
    Call<JsonObject> payment(@Body RequestBody body);

    @POST("old_prescription.php ")
    Call<JsonObject> sendPrescriptiondata(@Body RequestBody body);

    @POST("patient_appointments.php")
    Call<JsonObject> sendAppointdata(@Body RequestBody body);

    @POST("reports.php")
    Call<JsonObject> sendReportdata(@Body RequestBody body);

    @POST("doctor_assign.php")
    Call<JsonObject> sendDoctorAssignmentdata(@Body RequestBody body);

    //    @POST("number_verification.php")
    @POST("emp_varification.php")
    Call<JsonObject> numbervarification(@Body RequestBody body);

    @POST("verify_otp.php")
    Call<JsonObject> verify_otp(@Body RequestBody body);

    @POST("master_table_download.php")
    Call<JsonObject> getMasterTables(@Body RequestBody body);


    @POST("forget_password.php")
    Call<JsonObject>sendForgetPassword(@Body RequestBody body);

    @POST("change-password.php")
    Call<JsonObject> sendChangePassword(@Body RequestBody body);


    @POST("profile.php")
    Call<JsonObject> getCommonProfile(@Body RequestBody body);


    @POST("pharmacist-village-list.php")
    Call<JsonObject> pharmacistVillageList(@Body RequestBody body);

    @POST("profile_patient_list.php")
    Call<JsonObject> patientListingApi(@Body RequestBody body);

    @POST("download_patient_partner.php")
    Call<JsonObject> patientpartnr(@Body RequestBody body);

    @POST("delete_appointment.php")
    Call<JsonObject> deleteAppointmentApi(@Body RequestBody body);

    @POST("call_masking_rec.php")
    Call<JsonObject> callFromDoctorApi(@Body RequestBody body);

}