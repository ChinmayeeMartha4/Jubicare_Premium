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

    @POST("payment.php")
    Call<JsonObject> payment(@Body RequestBody body);

    @POST("old_prescription.php ")
    Call<JsonObject> sendPrescriptiondata(@Body RequestBody body);

    @POST("patient_appointments.php")
    Call<JsonObject> sendAppointdata(@Body RequestBody body);

    @POST("doctor_assign.php")
    Call<JsonObject> sendDoctorAssignmentdata(@Body RequestBody body);

   @POST("video_calling.php")
    Call<JsonObject> sendVideoData(@Query("user_id") String user_id,
                                   @Query("profile_patient_id") String profile_patient_id,
                                   @Query("patient_appointment_id") String patient_appointment_id,
                                   @Query("role_id") String role_id,
                                   @Query("calling_type") String calling_type,
                                   @Query("calling_screen") String calling_screen
                                   );

//    @POST("number_verification.php")
    @POST("emp_varification.php")
    Call<JsonObject> numbervarification(@Body RequestBody body);

    @POST("verify_otp.php")
    Call<JsonObject> verify_otp(@Body RequestBody body);

    @POST("master_table_download.php")
    Call<JsonObject> getMasterTables(@Body RequestBody body);

    @POST("download_counsellor_counting.php")
    Call<JsonObject> getCounsellorCount(@Body RequestBody body);

    @POST("download_doctor_counting.php")
    Call<JsonObject> getDoctorCount(@Body RequestBody body);

    @POST("download_doctor_pharmacist_counting.php")
    Call<JsonObject> getPharmacyCount(@Body RequestBody body);

    @POST("forget_password.php")
    Call<JsonObject> sendForgetPassword(@Body RequestBody body);

    @POST("change-password.php")
    Call<JsonObject> sendChangePassword(@Body RequestBody body);

    @POST("emergency_contact.php")
    Call<JsonObject> emergencyContactList(@Body RequestBody body);

    @POST("resend_otp.php")
    Call<JsonObject> resendOtp(@Body RequestBody body);

    @POST("profile.php")
    Call<JsonObject> getCommonProfile(@Body RequestBody body);

    @POST("doctor_calling.php")
    Call<JsonObject> getDoctorCall(@Body RequestBody body);

    @GET
    Call<JsonObject> getPinCode(@Url String PinCode);

    @POST("downloaded_notification.php")
    Call<JsonObject> getNotificationData(@Body RequestBody body);

    @POST("medicine-stock-list.php")
    Call<JsonObject> getMedicineStock(@Body RequestBody body);
       @POST("pharmacist_medicine_stock.php")
    Call<JsonObject> pharmacist_medicine_stock(@Body RequestBody body);


    @POST("pharmacist-village-list.php")
    Call<JsonObject> pharmacistVillageList(@Body RequestBody body);

    @POST("add-medicine-by-pharmacist.php")
    Call<JsonObject> addMedicineByPharmacist(@Body RequestBody body);

    @POST("list-of-village-schedule.php")
    Call<JsonObject> getVillageList(@Body RequestBody body);

    @POST("pharmacist-village-patient.php")
    Call<JsonObject> getHouseholdList(@Body RequestBody body);

    @POST("otp-generated-delivery-count.php")
    Call<JsonObject> getOtpGeneratedCount(@Body RequestBody body);

    @POST("otp-generated-delivery-list.php")
    Call<JsonObject> getOtpGeneratedVillageList(@Body RequestBody body);

    @POST("otp-generated-patient-list.php")
    Call<JsonObject> getOtpGeneratedPatientList(@Body RequestBody body);

    @POST("otp-not-generated-delivery-count.php")
    Call<JsonObject> getOtpNotGeneratedCount(@Body RequestBody body);

    @POST("otp-not-generated-delivery-list.php")
    Call<JsonObject> getOtpNotGeneratedVillageList(@Body RequestBody body);

    @POST("otp-not-generated-patient-list.php")
    Call<JsonObject> getOtpNotGeneratedPatientList(@Body RequestBody body);

    @POST("medicine_delivery_status-count.php")
    Call<JsonObject> getPatientDeniedCount(@Body RequestBody body);

    @POST("medicine_delivery_status.php")
    Call<JsonObject> getPatientDeniedDeliveryList(@Body RequestBody body);

    @POST("logout.php")
    Call<JsonObject> callLogoutApi(@Body RequestBody body);

    @POST("get-sub-test-type.php")
    Call<JsonObject> get_sub_test_type(@Body RequestBody body);

    @POST("get_medicine_list.php")
    Call<JsonObject> get_medicine_list(@Body RequestBody body);

    @POST("resend_otp_by_pharmacists.php")
    Call<JsonObject> resendOtpByPharmacists(@Body RequestBody body);

    @POST("profile_patient_list.php")
    Call<JsonObject> patientListingApi(@Body RequestBody body);

    @POST("delete_appointment.php")
    Call<JsonObject> deleteAppointmentApi(@Body RequestBody body);

    @POST("call_masking_rec.php")
    Call<JsonObject> callFromDoctorApi(@Body RequestBody body);

    @POST("get_ivrcdr_number.php")
    Call<JsonObject> callFromCounsellorFillFormApi(@Body RequestBody body);
    @POST("check_last_login.php")
    Call<JsonObject> check_last_login(@Body RequestBody body);

    @POST("patient_previous_history.php")
    Call<JsonObject> callPatientPreviousHistory(@Body RequestBody body);
}