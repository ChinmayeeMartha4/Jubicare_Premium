package com.indev.jubicare_premium.database;

public class OrganizationModel {
    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    private String table_name;


    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDel_action() {
        return del_action;
    }

    public void setDel_action(String del_action) {
        this.del_action = del_action;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_of_emp() {
        return no_of_emp;
    }

    public void setNo_of_emp(String no_of_emp) {
        this.no_of_emp = no_of_emp;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }
    private String role_id;
    private String user_id;
    private int id;
    private String name;
    private String email;
    private String no_of_emp;
    private String date_of_creation;
    private String created_at;
    private String del_action;


    public static final String TABLE_NAME = "organization";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_ROLE_ID = "role_id";
    public static final String COLUMN_NO_OF_EMP = "no_of_emp";
    public static final String COLUMN_DATE_OF_CREATION = "date_of_creation";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_DEL_ACTION = "del_action";
    public static final String COLUMN_FLAG = "flag";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_ID + " INTEGER ,"
                    + COLUMN_ROLE_ID + " INTEGER ,"
                    + COLUMN_NO_OF_EMP + " TEXT ,"
                    + COLUMN_DATE_OF_CREATION + " TEXT ,"
                    + COLUMN_EMAIL + " TEXT ,"
                    + COLUMN_NAME + " TEXT ,"
                    + COLUMN_DEL_ACTION + " TEXT ,"
                    + COLUMN_FLAG + "  INTEGER   "

                    + ")";



}
