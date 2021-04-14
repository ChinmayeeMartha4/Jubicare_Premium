package com.example.jubicare_premium.database;

public class ReportsPojo {
    private String date;
    private String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    private static final String TABLE_NAME= "reports";
    private static final String COLUMN_LOCAL_ID= "local_id";
    private static final String COLUMN_REPORTS_ID= "report_id";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_TITLE= "title";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_REPORTS_ID + " INTEGER,"
                    + COLUMN_DATE + " TEXT ,"
                    + COLUMN_TITLE + " TEXT "
                    + ")";


}
