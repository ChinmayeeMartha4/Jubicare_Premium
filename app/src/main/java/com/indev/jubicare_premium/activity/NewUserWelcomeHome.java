package com.indev.jubicare_premium.activity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.indev.jubicare_premium.Login;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.PaymentModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserWelcomeHome extends AppCompatActivity {
    private Context context = this;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_careforRs)
    TextView tv_careforRs;
    @BindView(R.id.tv_welcomeName)
    TextView tv_welcomeName;
    SharedPrefHelper sharedPrefHelper;
    ProgressDialog mProgressDialog;
    String user_idd = "";
    String name = "";
    String name1 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_welcome_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Payment" + "</font>"));
        tv_pay=findViewById(R.id.tv_pay);
        tv_welcomeName=findViewById(R.id.tv_welcomeName);
        tv_careforRs=findViewById(R.id.tv_careforRs);
        sharedPrefHelper = new SharedPrefHelper(this);

        String name = sharedPrefHelper.getString("name", "");
        tv_welcomeName.setText(name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name1 = bundle.getString("text");

        }

        tv_careforRs.setText(name1);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callpaymentApi(view);
            }
        });
    }
    private void callpaymentApi(View view) {

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
            mProgressDialog = ProgressDialog.show(context, "payment", "Please Wait...", true);
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setUser_id(sharedPrefHelper.getString("user_id", ""));

            Gson mGson = new Gson();
            String data = mGson.toJson(paymentModel);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, data);
            APIClient.getClient().create(TELEMEDICINE_API.class).payment(body).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        mProgressDialog.dismiss();
                        if (jsonObject.optString("success").equalsIgnoreCase("1")) {
                            String user_id = jsonObject.optString("user_id");
                            String message = jsonObject.optString("message");
                            String success = jsonObject.optString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(NewUserWelcomeHome.this, Login.class);
                                Toast.makeText(NewUserWelcomeHome.this, "" + message, Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(NewUserWelcomeHome.this, NewUserHome.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewUserWelcomeHome.this, NewUserHome.class);
        startActivity(intent);
        finish();
    }
}