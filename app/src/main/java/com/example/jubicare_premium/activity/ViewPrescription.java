package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;

import com.example.jubicare_premium.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPrescription extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    String view_prescription_url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        ButterKnife.bind(this);

        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "View Prescription" + "</font>"));

    /*get intent here*/
    Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
        view_prescription_url=bundle.getString("url", "");
    }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(view_prescription_url);

}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPrescription.this, Prescription.class);
        startActivity(intent);
        finish();
    }
}