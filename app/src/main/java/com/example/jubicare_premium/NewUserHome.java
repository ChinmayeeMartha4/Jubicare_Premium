package com.example.jubicare_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

public class NewUserHome extends AppCompatActivity {
    LinearLayout ll_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_home);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Home" + "</font>"));

        ll_next=findViewById(R.id.ll_next);

        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUserHome.this, NewUserWelcomeHome.class);
                startActivity(intent);
                finish();
            }
        });
    }
}