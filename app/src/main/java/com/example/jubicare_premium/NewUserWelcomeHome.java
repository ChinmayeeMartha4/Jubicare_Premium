package com.example.jubicare_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class NewUserWelcomeHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_welcome_home);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));
    }
}