package com.example.jubicare_premium.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.OldAppointmentPojo;
import com.example.jubicare_premium.database.ProfilePojo;
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
         db.execSQL(OldAppointmentPojo.CREATE_TABLE);
         db.execSQL(ReportsPojo.CREATE_TABLE);
         db.execSQL(UserPojo.CREATE_TABLE);
         db.execSQL(ProfilePojo.CREATE_TABLE);
         db.execSQL(AppointmentPojo.CREATE_TABLE);
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
    public ProfilePojo saveProfileData(ProfilePojo profilePojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        //     long meeting1id = 0;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("age", profilePojo.getAge());
                values.put("date_of_birth", profilePojo.getDate_of_birth());
                values.put("aadhar_card", profilePojo.getAadhar_card());
                values.put("height", profilePojo.getHeight());
                values.put("weight", profilePojo.getWeight());
                values.put("contact_number", profilePojo.getContact_number());
                values.put("address", profilePojo.getAddress());
                values.put("emergency", profilePojo.getEmergency());
                values.put("gender", profilePojo.getGender());
                values.put("state", profilePojo.getState());
                values.put("caste", profilePojo.getCaste());
                values.put("district", profilePojo.getDistrict());
                values.put("block", profilePojo.getBlock());
                values.put("village", profilePojo.getVillage());
                db.insert("profile", null, values);
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return profilePojo;
    }
    public AppointmentPojo saveappointmentsData(AppointmentPojo appointmentPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        //     long meeting1id = 0;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("bp_high", appointmentPojo.getBp_high());
                values.put("bp_low", appointmentPojo.getBp_low());
                values.put("sugar", appointmentPojo.getSugar());
                values.put("temperature", appointmentPojo.getTemperature());
                values.put("blood_oxygen_level", appointmentPojo.getBlood_oxygen_level());
                values.put("pulse", appointmentPojo.getPulse());
                values.put("blood_group_id", appointmentPojo.getBlood_group_id());
                values.put("symptom_id", appointmentPojo.getSymptom_id());
                values.put("prescribed_medicine", appointmentPojo.getPrescribed_medicine());
                values.put("prescribed_medicine_date", appointmentPojo.getPrescribed_medicine_date());
                values.put("image", appointmentPojo.getImage());
                values.put("is_emergency", appointmentPojo.getIs_emergency());
                values.put("patient_remarks", appointmentPojo.getPatient_remarks());
                db.insert("appointments", null, values);
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return appointmentPojo;
    }
    public OldAppointmentPojo saveAppointmentList(OldAppointmentPojo oldAppointmentPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        //     long meeting1id = 0;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("date", oldAppointmentPojo.getDate());
                values.put("doctor_name", oldAppointmentPojo.getDoctor_name());
                values.put("date1", oldAppointmentPojo.getDate1());
                values.put("doctor_name1", oldAppointmentPojo.getDoctor_name1());
                db.insert("appointment", null, values);
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return oldAppointmentPojo;
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
                         userPojo.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                         userPojo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
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

    public ProfilePojo getProfileData() {
        ProfilePojo profilePojo = new ProfilePojo();
        String query;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                query = "select  * from profile";

                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                         profilePojo = new ProfilePojo();
                        profilePojo.setAge(cursor.getString(cursor.getColumnIndex("age")));
                        profilePojo.setDate_of_birth(cursor.getString(cursor.getColumnIndex("date_of_birth")));
                        profilePojo.setAadhar_card(cursor.getString(cursor.getColumnIndex("aadhar_card")));
                        profilePojo.setHeight(cursor.getString(cursor.getColumnIndex("height")));
                        profilePojo.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                        profilePojo.setContact_number(cursor.getString(cursor.getColumnIndex("contact_number")));
                        profilePojo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        profilePojo.setEmergency(cursor.getString(cursor.getColumnIndex("emergency")));
                        profilePojo.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                        profilePojo.setState(cursor.getString(cursor.getColumnIndex("state")));
                        profilePojo.setCaste(cursor.getString(cursor.getColumnIndex("caste")));
                        profilePojo.setDistrict(cursor.getString(cursor.getColumnIndex("district")));
                        profilePojo.setBlock(cursor.getString(cursor.getColumnIndex("block")));
                        profilePojo.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                        cursor.moveToNext();

                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return profilePojo;
    }
    public AppointmentPojo getAppointmentsData() {
        AppointmentPojo appointmentPojo = new AppointmentPojo();
        String query;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                query = "select  * from profile";

                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        appointmentPojo = new AppointmentPojo();
                        appointmentPojo.setProfile_patient_id(cursor.getString(cursor.getColumnIndex("profile_patient_id")));
                        appointmentPojo.setPrescribed_medicine(cursor.getString(cursor.getColumnIndex("prescribed_medicine")));
                        appointmentPojo.setPrescribed_medicine_date(cursor.getString(cursor.getColumnIndex("prescribed_medicine_date")));
                        appointmentPojo.setIs_emergency(cursor.getString(cursor.getColumnIndex("is_emergency")));
                        appointmentPojo.setBp_high(cursor.getString(cursor.getColumnIndex("bp_high")));
                        appointmentPojo.setBp_low(cursor.getString(cursor.getColumnIndex("bp_low")));
                        appointmentPojo.setSugar(cursor.getString(cursor.getColumnIndex("sugar")));
                        appointmentPojo.setTemperature(cursor.getString(cursor.getColumnIndex("temperature")));
                        appointmentPojo.setBlood_oxygen_level(cursor.getString(cursor.getColumnIndex("blood_oxygen_level")));
                        appointmentPojo.setPulse(cursor.getString(cursor.getColumnIndex("pulse")));
                        appointmentPojo.setBlood_group_id(cursor.getString(cursor.getColumnIndex("blood_group_id")));
                        appointmentPojo.setSymptom_id(cursor.getString(cursor.getColumnIndex("symptom_id")));
                        appointmentPojo.setPatient_appointment_id(cursor.getString(cursor.getColumnIndex("patient_appointment_id")));
                        appointmentPojo.setPatient_remarks(cursor.getString(cursor.getColumnIndex("patient_remarks")));
                        appointmentPojo.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        cursor.moveToNext();

                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return appointmentPojo;
    }
    public ArrayList<OldAppointmentPojo> getAppointementData() {
        ArrayList<OldAppointmentPojo> arrayList = new ArrayList<OldAppointmentPojo>();
        String query;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                query = "select  * from appointment";

                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        OldAppointmentPojo oldAppointmentPojo = new OldAppointmentPojo();
                        oldAppointmentPojo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                        oldAppointmentPojo.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
                        oldAppointmentPojo.setDate1(cursor.getString(cursor.getColumnIndex("date1")));
                        oldAppointmentPojo.setDoctor_name1(cursor.getString(cursor.getColumnIndex("doctor_name1")));
                    cursor.moveToNext();
                    arrayList.add(oldAppointmentPojo);
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
