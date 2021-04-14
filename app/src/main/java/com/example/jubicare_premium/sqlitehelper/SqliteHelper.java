package com.example.jubicare_premium.sqlitehelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.ReportsPojo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 public class SqliteHelper extends SQLiteOpenHelper {
        static final String DATABASE_NAME = "jubicare_premium.db";
        static final int DATABASE_VERSION = 2;
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
    }
