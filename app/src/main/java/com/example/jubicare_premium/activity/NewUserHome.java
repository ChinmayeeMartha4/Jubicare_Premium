package com.example.jubicare_premium.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.LoginModel;
import com.example.jubicare_premium.database.PaymentModel;
import com.example.jubicare_premium.rest_api.APIClient;
import com.example.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserHome extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    private Context context = this;
    String add = "";
    @BindView(R.id.ll_next)
    LinearLayout ll_next;
    @BindView(R.id.et_mobileNo)
    EditText et_mobileNo;
    @BindView(R.id.et_emp_id)
    EditText et_emp_id;
    @BindView(R.id.spn_org)
    Spinner spn_org;
    @BindView(R.id.ll_org)
    LinearLayout ll_org;
    @BindView(R.id.tv_org)
    TextView tv_org;
    String org_id;
    String organization;
    SharedPrefHelper sharedPrefHelper;
    HashMap<String, Integer> organizationNameHM;
    ArrayList<String> organizationArrayList;
    String contact_no = "";
    String ottp = "";
    String user_idd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Payment" + "</font>"));
        sharedPrefHelper = new SharedPrefHelper(this);
        ll_next = findViewById(R.id.ll_next);
        et_mobileNo = findViewById(R.id.et_mobileNo);
//        setOrganizationSpinner();


        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                callpaymentApi();
//                Intent intent = new Intent(NewUserHome.this, NewUserWelcomeHome.class);
//                startActivity(intent);
//                finish();
            }
        });
    }
    private void callpaymentApi() {
//        String mobile_no = et_mobileNo.getText().toString().trim();
//        if (checkInternetConnection(context) == false) {
//            new AlertDialog.Builder(context)
//                    .setTitle("Alert !")
//                    .setMessage("Network Error, check your network connection.")
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        } else {
            mProgressDialog = ProgressDialog.show(context, "payment", "Please Wait...", true);
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setContact_no(contact_no);
            paymentModel.setUser_id(user_idd);
            paymentModel.setSet_otp(ottp);

            Gson mGson = new Gson();
            String data = mGson.toJson(paymentModel);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, data);
        APIClient.getClient().create(TELEMEDICINE_API.class).numbervarification(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    mProgressDialog.dismiss();
                    Log.e("chjJC", "njkdvnv " + jsonObject.toString());
                    String success = jsonObject.optString("success");
                    String user_id = jsonObject.optString("user_id");
                    String message = jsonObject.optString("message");
                    if (success.equals("1")) {
                        Toast.makeText(NewUserHome.this, "" + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewUserHome.this, NewUserWelcomeHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(context, "Please try again with correct OTP.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(NewUserHome.this, Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewUserHome.this, Login.class);

        startActivity(intent);
        finish();
    }
}