package com.indev.jubicare_premium;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonArray;
import com.indev.jubicare_premium.activity.CommonProfile;
import com.indev.jubicare_premium.activity.ForgotPassword;
import com.indev.jubicare_premium.activity.NewUserHome;
import com.indev.jubicare_premium.adapter.PatientAdapter;
import com.indev.jubicare_premium.database.LoginModel;
import com.indev.jubicare_premium.database.OldAppointmentPojo;
import com.indev.jubicare_premium.database.OrganizationModel;
import com.indev.jubicare_premium.database.PharmacyPatientModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Login extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btn_login;
//    @BindView(R.id.tv_sign_up)
//    TextView tv_signup;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_new_user)
    TextView tv_new_user;
    @BindView(R.id.et_mobile)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
        @BindView(R.id.cb_showPassword)
        CheckBox cb_showPassword;
    public static RelativeLayout rl_technology_partner;
//    /normal widgets/
    private ProgressDialog mProgressDialog;
    private Context context = this;
    private SharedPrefHelper sharedPrefHelper;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = Login.class.getSimpleName();
//    /--for validation--/
    private EditText flagEditfield;
    private String msg = "";
    boolean mPushTokenIsRegistered;
OrganizationModel organizationModel;
    private String mUserId;
    private long mSigningSequence = 1;
PharmacyPatientModel pharmacyPatientModel;
SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pharmacyPatientModel =new PharmacyPatientModel();
        sqliteHelper=new SqliteHelper(this);

        sharedPrefHelper = new SharedPrefHelper(this);
        //  btn_login=findViewById(R.id.btn_login);
        getSupportActionBar().hide();
        displayPassword();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    callLoginApi(view);
                }
//                callPatientListApi();
//                Intent intent = new Intent(LoginAcivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        tv_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, NewUserHome.class);
                startActivity(intent);
                finish();
            }
        });


        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

    }
//
    private void displayPassword() {
        cb_showPassword.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // show password
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // hide password
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void callLoginApi(View view) {
        String username = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        Snackbar.make(view, "Authenticating online" + "!!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        if (checkInternetConnection(context) == false) {
            new AlertDialog.Builder(context)
                    .setTitle("Alert !")
                    .setMessage("Network Error, check your network connection.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            mProgressDialog = ProgressDialog.show(context, "Login", "Please Wait...", true);
            LoginModel mLoginModel = new LoginModel();
            mLoginModel.setPassword(password);
            mLoginModel.setUser_name(username);
//                mLoginModel.setFirebase_token(sharedPrefHelper.getString("Token", ""));
//                mLoginModel.setApp_version(FINAL_VAR.app_version);
            Gson mGson = new Gson();
            String data = mGson.toJson(mLoginModel);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, data);
            APIClient.getClient().create(TELEMEDICINE_API.class).login(body).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        mProgressDialog.dismiss();
                        if (jsonObject.optString("success").equalsIgnoreCase("1")) {
                            sharedPrefHelper.setString("isSplashLoaded", "1");
                            String user_id = jsonObject.optString("user_id");
                            String message = jsonObject.optString("message");
                            String success = jsonObject.optString("success");
                            String role_id = jsonObject.optString("role_id");
                            String full_name = jsonObject.optString("full_name");
                            String contact_no = jsonObject.optString("contact_no");
                            String profile_pic = jsonObject.optString("profile_pic");
                            String mobile_token = jsonObject.optString("mobile_token");
                            sharedPrefHelper.setString("user_id", user_id);
                            sharedPrefHelper.setString("role_id", role_id);
                            sharedPrefHelper.setString("full_name", full_name);
                            sharedPrefHelper.setString("contact_no", contact_no);
                            sharedPrefHelper.setString("profile_pic", profile_pic);
                            sharedPrefHelper.setString("userName", username);
                            sharedPrefHelper.setString("mobile_token", mobile_token);
//                            download_batch();

//                           // if (success.equalsIgnoreCase("1") && role_id.equalsIgnoreCase("8")) {
//                                Intent intent = new Intent(LoginAcivity.this, HomeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                    /*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
//                                startActivity(intent);
//                                finish();
//                            }
                            if (success.equalsIgnoreCase("1") && role_id.equalsIgnoreCase("7")) {
//                                if (!mPushTokenIsRegistered) {
//                                    getSinchServiceInterface().getManagedPush(userName).registerPushToken(this);
//                                }
                                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());


                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    /*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                                startActivity(intent);
                                finish();
                            }


                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(context, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.dismiss();
                }
            });
        }
    }
//    public void download_batch() {
//        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//        Gson mGson = new Gson();
//        String data = mGson.toJson(organizationModel);
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//        APIClient.getClient().create(TELEMEDICINE_API.class).download_organization(body).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    JsonObject jsonObject = response.body();
//                    Log.e("cnccnnc", "bhvhbv " + jsonObject.toString());
//                    mProgressDialog.dismiss();
//                    JsonArray data = jsonObject.getAsJsonArray("Organization");
//                    if (data != null) {
//                        if (data.size() > 0) {
//                            for (int i = 0; i < data.size(); i++) {
//                                JSONObject object = new JSONObject(data.get(i).toString());
//                                Iterator keys = object.keys();
//                                ContentValues contentValues = new ContentValues();
//                                while (keys.hasNext()) {
//                                    String currentDynamicKey = (String) keys.next();
//                                    contentValues.put(currentDynamicKey, object.get(currentDynamicKey).toString());
//                                }
//                                sqliteHelper.saveMasterTable(contentValues, "organization");
//                            }
//                            Intent intent = new Intent(Login.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }
//
//                    } else {
//                        mProgressDialog.dismiss();
//                        // rv_product_category.setVisibility(View.GONE);
//                        // tv_product_not_found.setVisibility(View.VISIBLE);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mProgressDialog.dismiss();
//                new androidx.appcompat.app.AlertDialog.Builder(context).setTitle("Alert !")
//                        .setMessage("Network Error, check your network connection.").
//                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//                            }
//                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
//
//
//            }
//        });
//    }
//    private void callPatientListApi() {
//        mProgressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
//        pharmacyPatientModel.setUser_id(sharedPrefHelper.getString("user_id", ""));
//        pharmacyPatientModel.setRole_id(sharedPrefHelper.getString("role_id", ""));
////        pharmacyPatientModel.setMobile(searchInput);
//
//        Gson mGson = new Gson();
//        String data = mGson.toJson(pharmacyPatientModel);
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//
//        APIClient.getClient().create(TELEMEDICINE_API.class).patientListingApi(body).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.body().toString());
//                        mProgressDialog.dismiss();
//                        String success = jsonObject.getString("success");
//                        if (success.equals("1")) {
//
//                        }
//                        JsonObject singledataP = response.body();
//                        JsonArray data = singledataP.getAsJsonArray("tableData");
//                        if (data.size() > 0) {
//                            for (int i = 0; i < data.size(); i++) {
//                                JSONObject singledata = new JSONObject(data.get(i).toString());
//                                Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());
//
//                                Iterator keys = singledata.keys();
//                                ContentValues contentValues = new ContentValues();
//                                while (keys.hasNext()) {
//                                    String currentDynamicKey = (String) keys.next();
//                                    contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
//                                }
//
//                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//
//
//                            }
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressDialog.dismiss();
//            }
//        });
//    }
    public static boolean checkInternetConnection(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean checkValidation() {
        if (et_email.getText().toString().trim().length() == 0) {
            flagEditfield = et_email;
            msg = "Please enter username";
            flagEditfield.setError(msg);
            et_email.requestFocus();
            return false;
        } else if (et_password.getText().toString().trim().length() == 0) {
            flagEditfield = et_password;
            msg = "Please enter password";
            flagEditfield.setError(msg);
            et_password.requestFocus();
            return false;
        }
        return true;
    }
}