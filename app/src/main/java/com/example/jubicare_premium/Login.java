package com.example.jubicare_premium;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jubicare_premium.activity.ForgotPassword;
import com.example.jubicare_premium.activity.HealthRecord;
import com.example.jubicare_premium.activity.NewUserHome;
import com.example.jubicare_premium.activity.datadownload.DataDownloadInput;
import com.example.jubicare_premium.database.UserPojo;
import com.example.jubicare_premium.rest_api.APIClient;
import com.example.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText et_mobile, et_password;
    TextView tv_sign_up;
    @BindView(R.id.btn_login)
    Button btn_login;
    SqliteHelper sqliteHelper;
    UserPojo userPojo;
    ProgressDialog mProgressDialog;
    SharedPrefHelper sharedPrefHelper;
    private ProgressDialog dialog;
    Context context = this;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    //    private String[] masterTables = {"state", "district", "block", "village", "post_office", "symptom","blood_group"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Login" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        userPojo=new UserPojo();
        sharedPrefHelper = new SharedPrefHelper(context);
        ButterKnife.bind(this);

        /*download master tables here*/
//        getMasterTables(Login.this);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        userPojo = sqliteHelper.getLoginData();
        tv_sign_up = findViewById(R.id.tv_sign_up);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userPojo.setMobile(et_mobile.getText().toString());
                userPojo.setPassword(et_password.getText().toString());
                sqliteHelper.saveLoginData(userPojo);
                callLoginApi();

                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, NewUserHome.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @OnClick({R.id.btn_login, R.id.tv_sign_up, R.id.tv_forgot_password})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                Intent intent1 = new Intent(context, Login.class);
                startActivity(intent1);
                break;
            case R.id.btn_login:

//                if (checkValidation()) {
                     /*Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/
                    /* if (et_email.getText().toString().equalsIgnoreCase("Agent") || et_password.getText().toString().equalsIgnoreCase("Agent")){
                         Intent intent=new Intent(Login.this,AgentHome.class);
                         startActivity(intent);
                         finish();
                     }*/
                    callLoginApi();
//                }
                break;
            case R.id.tv_forgot_password:
                Intent intent4 = new Intent(context, ForgotPassword.class);
                startActivity(intent4);
                break;
        }
    }
    private void callLoginApi() {
        dialog=ProgressDialog.show(this, "", "Please wait...", true);

        userPojo.setMobile(et_mobile.getText().toString().trim());
        userPojo.setPassword(et_password.getText().toString().trim());
//        userPojo.setMobile_token(sharedPrefHelper.getString("Token", ""));

        Gson gson = new Gson();
        String data = gson.toJson(userPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        APIClient.getClient().create(TELEMEDICINE_API.class).login(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("login", "login" + jsonObject.toString());
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String user_id = jsonObject.optString("user_id");
                    sharedPrefHelper.setString("user_id", user_id);
                    Log.e("TAG", "onResponse: "+sharedPrefHelper.getString("user_id", ""));
                    if (Integer.valueOf(status) == 1) {
                        sharedPrefHelper.setString("is_login","1");

                        sharedPrefHelper.setString("user_id", user_id);

                        Log.e("Login", "Data : ," + status + "," + message +","+ user_id);

                        Log.e("Login", "Data : ," + status + "," + message +","+ user_id);

                         sqliteHelper.dropTable("faq");

                        sharedPrefHelper.setString("user_id", user_id);
                        Intent intentMainActivity = new Intent(Login.this, HomeActivity.class);
                        startActivity(intentMainActivity);
                        finish();
                    } else {
                        Toast.makeText(context, "Invalid id", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Login.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("hgfh","ggh"+ t  +","+ call);
                dialog.dismiss();
            }
        });
    }
}

