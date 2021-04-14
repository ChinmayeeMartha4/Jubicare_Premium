package com.example.jubicare_premium.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.jubicare_premium.activity.Reports;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.UserPojo;
import com.example.jubicare_premium.database.ReportsPojo;

import java.io.File;
import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {
     static final String DATABASE_NAME = "jubicare_premium.db";
     static final int DATABASE_VERSION = 1;
     String DB_PATH_SUFFIX = "/databases/";
     int version;
     Context ctx;

     public SqliteHelper(@Nullable Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
         ctx = context;
     }

     @Override
     public void onCreate(SQLiteDatabase db) {
         db.execSQL(AppointmentPojo.CREATE_TABLE);
         db.execSQL(ReportsPojo.CREATE_TABLE);
         db.execSQL(UserPojo.CREATE_TABLE);
     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

     }

     public SQLiteDatabase openDataBase() throws SQLException {
         Log.e("version", "outside" + version);

         File dbFile = ctx.getDatabasePath(DATABASE_NAME);
         //  checkDbVersion(dbFile);
         return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
     }

     public void dropTable(String tablename) {
         SQLiteDatabase db = this.getWritableDatabase();
         try {
             db.execSQL("DELETE FROM'" + tablename + "'");
         } catch (Exception e) {
             e.printStackTrace();
             db.close();
         }
     }



     public UserPojo saveLoginData(UserPojo userPojo) {
         SQLiteDatabase db = this.getWritableDatabase();
         //     long meeting1id = 0;
         try {
             if (db != null && db.isOpen() && !db.isReadOnly()) {
                 ContentValues values = new ContentValues();
                 values.put("mobile", userPojo.getMobile());
                 values.put("password", userPojo.getPassword());
                 db.insert("user", null, values);
                 db.close(); // Closing database connection
             }
         } catch (Exception e) {
             e.printStackTrace();
             db.close();
         }
         return userPojo;
     }
    public AppointmentPojo saveAppointmentList(AppointmentPojo appointmentPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        //     long meeting1id = 0;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("date", appointmentPojo.getDate());
                values.put("doctor_name", appointmentPojo.getDoctor_name());
                values.put("date1", appointmentPojo.getDate1());
                values.put("doctor_name1", appointmentPojo.getDoctor_name1());
                db.insert("appointment", null, values);
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return appointmentPojo;
    }
    public ReportsPojo saveReportList(ReportsPojo reportsPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        //     long meeting1id = 0;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("date", reportsPojo.getDate());
                values.put("title", reportsPojo.getTitle());

                db.insert("reports", null, values);
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return reportsPojo;
    }


     public UserPojo getLoginData() {
         UserPojo userPojo = new UserPojo();
         String query;
         SQLiteDatabase db = this.getReadableDatabase();
         try {
             if (db != null && db.isOpen() && !db.isReadOnly()) {
                 query = "select  * from user";

                 Cursor cursor = db.rawQuery(query, null);

                 if (cursor != null && cursor.getCount() > 0) {
                     cursor.moveToFirst();
                     while (!cursor.isAfterLast()) {

                         userPojo = new UserPojo();
                         userPojo.setMobile(cursor.getString(cursor.getColumnIndex("local_id")));
                         userPojo.setPassword(cursor.getString(cursor.getColumnIndex("monthly_reporting_awc_id")));
                         cursor.moveToNext();
                     }
                     db.close();
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();

         }
         return userPojo;
     }
    public ArrayList<AppointmentPojo> getAppointementData() {
        ArrayList<AppointmentPojo> arrayList = new ArrayList<AppointmentPojo>();
        String query;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                query = "select  * from appointment";

                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        AppointmentPojo appointmentPojo = new AppointmentPojo();
                        appointmentPojo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                        appointmentPojo.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
                        appointmentPojo.setDate1(cursor.getString(cursor.getColumnIndex("date1")));
                        appointmentPojo.setDoctor_name1(cursor.getString(cursor.getColumnIndex("doctor_name1")));
                    cursor.moveToNext();
                    arrayList.add(appointmentPojo);
                }
                db.close();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return arrayList;
}

    public ArrayList<ReportsPojo> getReportsData() {
        ArrayList<ReportsPojo> arrayList = new ArrayList<ReportsPojo>();
        String query;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                query = "select  * from reports";

                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        ReportsPojo reportsPojo = new ReportsPojo();
                        reportsPojo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                        reportsPojo.setTitle(cursor.getString(cursor.getColumnIndex("title")));

                        cursor.moveToNext();
                        arrayList.add(reportsPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
 }
