package com.example.jubicare_premium.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.jubicare_premium.HomeActivity;
import com.example.jubicare_premium.Login;
import com.example.jubicare_premium.R;

public class NewUserWelcomeHome extends AppCompatActivity {
    TextView Tv_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_welcome_home);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
        Tv_pay=findViewById(R.id.Tv_pay);
        Tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUserWelcomeHome.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewUserWelcomeHome.this, NewUserHome.class);
        startActivity(intent);
        finish();
    }
}