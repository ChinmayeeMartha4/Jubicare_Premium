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
import java.util.Collections;
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
//    String organization = "";
    int organization;

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
    boolean isEditable = false;
    String org_name;

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
//        setOrganizationSpinner();
        download_organization("organization");

        organizationArrayList.clear();
        organizationNameHM = sqliteHelper.getAllOrganization();
        for (int i = 0; i < organizationNameHM.size(); i++) {
            organizationArrayList.add(organizationNameHM.keySet().toArray()[i].toString().trim());
        }
        Collections.sort(organizationArrayList);

        if (isEditable) {
            organizationArrayList.add(0, org_name);
        } else {
            organizationArrayList.add(0, getString(R.string.select_organization));
        }
        final ArrayAdapter q = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, organizationArrayList);
        q.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_org.setAdapter(q);



        spn_org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!spn_org.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_organization))) {
                    if (spn_org.getSelectedItem().toString().trim() != null) {
                        String value = spn_org.getSelectedItem().toString().trim();
                        organization = organizationNameHM.get(value);
                        Log.e("org : ", "===" + organization);

                        if (spn_org.getSelectedItem().toString().equalsIgnoreCase("Not Affiliated")) {

                            et_emp_id.setVisibility(View.GONE);
                            tv_emp_id.setVisibility(View.GONE);
                            tv_mobileNo.setVisibility(View.GONE);
                            et_mobileNo.setVisibility(View.GONE);
                            tv_welcome1.setText(Html.fromHtml("Changes Rs. <font color=\"#2A66CC\">" + "199" + "</font> per patient"));
                        }
                        else{
                            et_emp_id.setVisibility(View.VISIBLE);
                            tv_emp_id.setVisibility(View.VISIBLE);
                            tv_mobileNo.setVisibility(View.VISIBLE);
                            et_mobileNo.setVisibility(View.VISIBLE);
                            tv_welcome1.setText(Html.fromHtml("Changes Rs. <font color=\"#2A66CC\">" + "99" + "</font> per patient"));


                    }
                }}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (spn_org.getSelectedItem().toString().equalsIgnoreCase("Not Affiliated")) {
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






private void download_organization(String table) {
    organizationModel.setTable_name(table);


    Gson mGson = new Gson();
    String data = mGson.toJson(organizationModel);
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(JSON, data);
    TELEMEDICINE_API api_service = APIClient.getClient().create(TELEMEDICINE_API.class);
    if (body != null && api_service != null) {
        Call<JsonObject> server_response = api_service.download_organization(body);
        try {
            if (server_response != null) {
                server_response.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            try {
                                JsonObject singledataP = response.body();
                                Log.e("nxjknx", "yxjhjxj " + singledataP.toString());
                                mProgressDialog.dismiss();
                                JsonArray data = singledataP.getAsJsonArray("tableData");
                                //comment by vimal because they send Appointmenthistory = null instead of Appointmenthistory = []
                                JsonArray data2 = singledataP.getAsJsonArray("Appointmenthistory");

//                                JSONObject singledata = null;
                                JSONObject singledata2 = null;

//                                sqliteHelper.saveMasterTable(contentValues, "organization");

                                if (data.size() > 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        JSONObject singledata = new JSONObject(data.get(i).toString());
                                        Log.e("bcjhdbjcb", "onResponse: " + singledata.toString());

                                        Iterator keys = singledata.keys();
                                        ContentValues contentValues = new ContentValues();
                                        while (keys.hasNext()) {
                                            String currentDynamicKey = (String) keys.next();
                                            contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                        }
                                        sqliteHelper.saveMasterTable(contentValues, "organization");
                                    }}


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

//    APIClient.getClient().create(TELEMEDICINE_API.class).download_organization(body).enqueue(new Callback<JsonObject>() {
//        @Override
//        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//            try {
//                JsonObject jsonObject = response.body();
//                Log.e("Organization", "bhvhbv " + data.toString());
//                mProgressDialog.dismiss();
////                JsonArray data = jsonObject.getAsJsonArray("Organization");
//                if (jsonObject != null) {
//                    if (jsonObject.size() > 0) {
//
//                    for (int i = 0; i < jsonObject.size(); i++) {
//                        JSONObject object = new JSONObject(jsonObject.get(String.valueOf(i)).toString());
//                        Iterator keys = object.keys();
//                        ContentValues contentValues = new ContentValues();
//                        while (keys.hasNext()) {
//                            String currentDynamicKey = (String) keys.next();
//                            contentValues.put(currentDynamicKey, object.get(currentDynamicKey).toString());
//
//                        }
//                        sqliteHelper.saveMasterTable(contentValues, "organization");
//
//                    }
//                }
//            }
//
//                    mProgressDialog.dismiss();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
        }
    });
}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
       int org_id = organization;

            mProgressDialog = ProgressDialog.show(context, "payment", "Please Wait...", true);
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setOrg_id(org_id);
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