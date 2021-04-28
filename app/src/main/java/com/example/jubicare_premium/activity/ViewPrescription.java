package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jubicare_premium.R;
import com.example.jubicare_premium.database.AppointmentPojo;
import com.example.jubicare_premium.database.PatientFilledDataModel;
import com.example.jubicare_premium.rest_api.APIClient;
import com.example.jubicare_premium.rest_api.TELEMEDICINE_API;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPrescription extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    /*for dynamic inflate layout*/
//    @BindView(R.id.ll_for_dynamic_add)
//    LinearLayout ll_for_dynamic_add;
    public static ScrollView scrollView;
    String view_prescription_url="";
    String commomProfile = "";
    PatientFilledDataModel patientFilledDataModel;
    String profile_id = "";
    SharedPrefHelper sharedPrefHelper;
    ProgressDialog mProgressDialog;
    String patient_appointments_id = "";

    SqliteHelper sqliteHelper;
    String caste_id;
    boolean isEditable = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        ButterKnife.bind(this);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(this);
//        initViews();
        /*get intent here*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            profile_id = bundle.getString("profile_patient_id", "");
            view_prescription_url = bundle.getString("url", "");
        }




        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(view_prescription_url);

    }


    private void initViews() {
        mProgressDialog = new ProgressDialog(context);
        patientFilledDataModel = new PatientFilledDataModel();
        sharedPrefHelper = new SharedPrefHelper(context);
        scrollView = findViewById(R.id.scrollView);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPrescription.this, Prescription.class);
        startActivity(intent);
        finish();
    }
}