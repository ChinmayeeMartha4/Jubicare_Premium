package com.indev.jubicare_premium.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.indev.jubicare_premium.Login;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.database.OrganizationModel;
import com.indev.jubicare_premium.database.PaymentModel;
import com.indev.jubicare_premium.database.PharmacyPatientModel;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    @BindView(R.id.tv_welcome1)
    TextView tv_welcome1;
    TextView tv_mobileNo;
    TextView tv_emp_id;
    String organization = "";

    String org_id;
//    String organization;
    SharedPrefHelper sharedPrefHelper;
    HashMap<String, Integer> organizationNameHM;
    ArrayList<String> organizationArrayList;
    String emp_id = "";
    String ottp = "";
    String user_idd = "";
    String[] Org = {"Select organization","Indev Consultancy Pvt.Ltd.", "Jubicare", "not affiliated with organization"};
    String strOrg;
    private static EditText flagEditfield;
    private static String msg = "";
    OrganizationModel organizationModel;
    SqliteHelper sqliteHelper;
    String organization_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Payment" + "</font>"));

        initview();


//        setOrganizationSpinner();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_idd = bundle.getString("user_idd");
            emp_id = bundle.getString("emp_id");

        }
        setOrganizationSpinner();
        download_participant("organization");


        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (spn_org.getSelectedItem().toString().equalsIgnoreCase("not affiliated with organization")) {
                    Intent intent = new Intent(NewUserHome.this, SignUp.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if (checkValidation()) {

                        callpaymentApi();


                    }

                }
            }
        });

    }

    private void initview() {
        ll_next = findViewById(R.id.ll_next);
        organizationModel=new OrganizationModel();

        et_mobileNo = findViewById(R.id.et_mobileNo);
        et_emp_id = findViewById(R.id.et_emp_id);
        spn_org = findViewById(R.id.spn_org);
        tv_emp_id = findViewById(R.id.tv_emp_id);
        spn_org = findViewById(R.id.spn_org);
        tv_mobileNo = findViewById(R.id.tv_mobileNo);
        tv_welcome1 = findViewById(R.id.tv_welcome1);
        organizationNameHM=new HashMap<>();
        organizationArrayList=new ArrayList<>();
        sharedPrefHelper = new SharedPrefHelper(this);
        organizationModel =new OrganizationModel();

        mProgressDialog=new ProgressDialog(context);
        sqliteHelper=new SqliteHelper(this);
    }

    private void setOrganizationSpinner() {
        organizationArrayList = sqliteHelper.getspnOrganizationData();
        spn_org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spn_org.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_organization))) {
                    int index = spn_org.getSelectedItemPosition();
                    organization = organizationArrayList.get(index);
                    organization_id = sqliteHelper.getSelectedItemId("organization",organization);
                    //Toast.makeText(context, "" + bloodGroupId, Toast.LENGTH_SHORT).show();
                } else {
                    organization_id = String.valueOf(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        organizationArrayList.add(0, getString(R.string.select_organization));
        final ArrayAdapter Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, organizationArrayList);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_org.setAdapter(Adapter);
    }





public void download_participant(String table) {
    organizationModel.setTable_name(table);


    Gson mGson = new Gson();
    String data = mGson.toJson(organizationModel);
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(JSON, data);
    APIClient.getClient().create(TELEMEDICINE_API.class).download_organization(body).enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            try {
                JsonObject jsonObject = response.body();
                Log.e("Organization", "bhvhbv " + data.toString());
                mProgressDialog.dismiss();
//                JsonArray data = jsonObject.getAsJsonArray("Organization");
                if (jsonObject != null) {
                    if (jsonObject.size() > 0) {

                    for (int i = 0; i < jsonObject.size(); i++) {
                        JSONObject object = new JSONObject(jsonObject.get(String.valueOf(i)).toString());
                        Iterator keys = object.keys();
                        ContentValues contentValues = new ContentValues();
                        while (keys.hasNext()) {
                            String currentDynamicKey = (String) keys.next();
                            contentValues.put(currentDynamicKey, object.get(currentDynamicKey).toString());

                        }
                        sqliteHelper.saveMasterTable(contentValues, "organization");

                    }
                }
            }

                    mProgressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
        }
    });
}


    private boolean checkValidation() {
        if (!et_emp_id.getText().toString().trim().matches("^[a-zA-Z0-9]+$")) {
            flagEditfield = et_emp_id;
            msg = "Please enter employee ID!";
            flagEditfield.setError(msg);
            et_emp_id.requestFocus();
            return false;
        }
        return true;

}
    private void callpaymentApi() {
        String emp_id = et_emp_id.getText().toString().trim();

            mProgressDialog = ProgressDialog.show(context, "payment", "Please Wait...", true);
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setEmp_id(emp_id);


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
                    String name = jsonObject.optString("name");
                    String message = jsonObject.optString("message");
                    sharedPrefHelper.setString("user_id", user_id);
                    sharedPrefHelper.setString("name", name);
                    if (success.equals("1")) {
                        Toast.makeText(NewUserHome.this, "" + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewUserHome.this, NewUserWelcomeHome.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
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