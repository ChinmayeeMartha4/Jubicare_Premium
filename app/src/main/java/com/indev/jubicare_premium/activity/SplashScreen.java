package com.indev.jubicare_premium.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.HomeActivity;
import com.indev.jubicare_premium.Login;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.activity.datadownload.DataDownload;
import com.indev.jubicare_premium.database.OrganizationModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import org.json.JSONObject;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private SqliteHelper sqliteHelper;
    SharedPrefHelper sharedPrefHelper;
    private final int SPLASH_DISPLAY_LENGTH = 3000; //3 seconds
    private Context context =this;
//String splashLoaded;
OrganizationModel organizationModel;
    ProgressDialog mProgressDialog;

    private static String splashLoaded = "No";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        sqliteHelper = new SqliteHelper(this);
        sqliteHelper.openDataBase();
        mProgressDialog=new ProgressDialog(context);

        sharedPrefHelper = new SharedPrefHelper(this);
        getPermissionLocation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String role_id = sharedPrefHelper.getString("role_id", "");
                splashLoaded = sharedPrefHelper.getString("isSplashLoaded", "");
                if (splashLoaded.equals("")) {
                    DataDownload dataDownload = new DataDownload();
                    dataDownload.getMasterTables(SplashScreen.this);
//                    download_participant();

                } else {
                    if (role_id.equalsIgnoreCase("")) {
                        Intent intent = new Intent(context, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    if (role_id.equalsIgnoreCase("7")) {
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
//                if (splashLoaded.equals("")) {
////                    DataDownload dataDownload = new DataDownload();
////                    dataDownload.getMasterTables(SplashScreen.this);
//                    Intent intent=new Intent(SplashScreen.this,Login.class);
//                    startActivity(intent);
//                    finish();
//
//                } else {
//                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
//    public void download_participant() {
//        Gson mGson = new Gson();
//        String data = mGson.toJson(organizationModel);
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//        APIClient.getClient().create(TELEMEDICINE_API.class).download_organization(body).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    JsonObject jsonObject = response.body();
//                    Log.e("organization", "bhvhbv " + jsonObject.toString());
//                    mProgressDialog.dismiss();
//                    JsonArray data = jsonObject.getAsJsonArray("Organization");
//                    if (data != null) {
//                        if (data.size() > 0) {
////                if (jsonObject.has("Organization")) {
////                    JsonArray data = jsonObject.getAsJsonArray("Organization");
//                            for (int i = 0; i < data.size(); i++) {
//                                JSONObject object = new JSONObject(data.get(i).toString());
//                                Iterator keys = object.keys();
//                                ContentValues contentValues = new ContentValues();
//                                while (keys.hasNext()) {
//                                    String currentDynamicKey = (String) keys.next();
//                                    contentValues.put(currentDynamicKey, object.get(currentDynamicKey).toString());
//
//                                }
//                                sqliteHelper.saveMasterTable(contentValues, "organization");
//
//                            }
//                        }
//                    }
//                    else {
//                        mProgressDialog.dismiss();
//                        // rv_product_category.setVisibility(View.GONE);
//                        // tv_product_not_found.setVisibility(View.VISIBLE);
//                    }
//
////                Intent intent = new Intent(New.this, OtpActivity.class);
////                startActivity(intent);
////                finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
////                mProgressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
////            mProgressDialog.dismiss();
//            }
//        });
//    }

    private void getPermissionLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                        Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        }
    }
}