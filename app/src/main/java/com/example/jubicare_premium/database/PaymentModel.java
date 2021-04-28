package com.example.jubicare_premium.database;

public class PaymentModel {
//    public String getMobile_no() {
//        return mobile_no;
//    }
//
//    public void setMobile_no(String mobile_no) {
//        this.mobile_no = mobile_no;
//    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    String contact_no;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String otp;
    String user_id;

    public String getSet_otp() {
        return set_otp;
    }

    public void setSet_otp(String set_otp) {
        this.set_otp = set_otp;
    }

    String set_otp;


}
