package com.example.jubicare_premium.database;

public class Postoffice {
    private  int  id;
    private String name;
    private String created_at;
    private String del_action;
    private String block_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public static final String TABLE_NAME = "post_office";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_BLOCK_ID = "block_id";
    public static final String COLUMN_DEL_ACTION = "del_action";
    public static final String COLUMN_is_visible_patient = "is_visual_patient";
    public static final String COLUMN_CREATED_DATE = "created_at";
    public static final String COLUMN_FLAG = "flag";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT ,"
                    + COLUMN_is_visible_patient + " TEXT ,"
                    + COLUMN_DEL_ACTION + " TEXT ,"
                    + COLUMN_CREATED_DATE + " TEXT ,"
                    + COLUMN_BLOCK_ID + " TEXT ,"
                    + COLUMN_FLAG + "  INTEGER   "
                    + ")";
}
