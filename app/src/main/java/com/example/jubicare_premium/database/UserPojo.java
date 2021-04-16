package com.example.jubicare_premium.database;

public class UserPojo {
    private String mobile;
    private String local_id;

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;




        private static final String TABLE_NAME= "user";
        private static final String COLUMN_LOCAL_ID= "local_id";
        private static final String COLUMN_USER_ID= "user_id";
        private static final String COLUMN_MOBILE= "mobile";
        private static final String COLUMN_PASSWORD= "password";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_USER_ID + " INTEGER,"
                        + COLUMN_MOBILE + " TEXT ,"
                        + COLUMN_PASSWORD + " TEXT "
                        + ")";
}
