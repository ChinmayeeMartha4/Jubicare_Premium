package com.indev.jubicare_premium.database;

public class ProfilePojo {
    private String age;
    private String date_of_birth;
    private String aadhar_card;
    private String height;
    private String weight;
    private String contact_number;
    private String address;

    public String getPost_office() {
        return post_office;
    }

    public void setPost_office(String post_office) {
        this.post_office = post_office;
    }

    private String post_office;

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAadhar_card() {
        return aadhar_card;
    }

    public void setAadhar_card(String aadhar_card) {
        this.aadhar_card = aadhar_card;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    private String emergency;
    private String gender;
    private String state;
    private String caste;
    private String district;
    private String block;
    private String village;

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    private String profile_id;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    private static final String TABLE_NAME= "profile";
    private static final String COLUMN_LOCAL_ID= "local_id";
    private static final String COLUMN_PROFILE_ID= "profile_id";
    private static final String COLUMN_AGE= "age";
    private static final String COLUMN_DATE_OF_BIRTH= "date_of_birth";
    private static final String COLUMN_AADHAR_CARD= "aadhar_card";
    private static final String COLUMN_DOCTOR_HEIGHT= "height";
    private static final String COLUMN_DOCTOR_WEIGHT= "weight";
    private static final String COLUMN_DOCTOR_CONTACT_NUMBER= "contact_number";
    private static final String COLUMN_DOCTOR_ADDRESS= "address";
    private static final String COLUMN_DOCTOR_EMERGENCY= "emergency";
    private static final String COLUMN_DOCTOR_GENDER= "gender";
    private static final String COLUMN_DOCTOR_STATE= "state";
    private static final String COLUMN_DOCTOR_CASTE= "caste";
    private static final String COLUMN_DOCTOR_DISTRICT= "district";
    private static final String COLUMN_DOCTOR_BLOCK= "block";
    private static final String COLUMN_DOCTOR_VILLAGE= "village";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PROFILE_ID + " INTEGER,"
                    + COLUMN_AGE + " TEXT,"
                    + COLUMN_DATE_OF_BIRTH + " TEXT ,"
                    + COLUMN_AADHAR_CARD + " TEXT,"
                    + COLUMN_DOCTOR_HEIGHT + " TEXT ,"
                    + COLUMN_DOCTOR_WEIGHT + " TEXT ,"
                    + COLUMN_DOCTOR_CONTACT_NUMBER + " TEXT ,"
                    + COLUMN_DOCTOR_ADDRESS + " TEXT ,"
                    + COLUMN_DOCTOR_EMERGENCY + " TEXT ,"
                    + COLUMN_DOCTOR_GENDER + " TEXT ,"
                    + COLUMN_DOCTOR_STATE + " TEXT ,"
                    + COLUMN_DOCTOR_CASTE + " TEXT ,"
                    + COLUMN_DOCTOR_DISTRICT + " TEXT ,"
                    + COLUMN_DOCTOR_BLOCK + " TEXT ,"
                    + COLUMN_DOCTOR_VILLAGE + " TEXT "
                    + ")";

}
