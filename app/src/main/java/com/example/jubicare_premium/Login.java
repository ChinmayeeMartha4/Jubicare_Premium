package com.example.jubicare_premium;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jubicare_premium.activity.NewUserHome;
import com.example.jubicare_premium.database.UserPojo;
import com.example.jubicare_premium.sqlitehelper.SqliteHelper;

public class Login extends AppCompatActivity {
    EditText et_mobile, et_password;
    Button btn_login;
    TextView tv_sign_up;
    SqliteHelper sqliteHelper;
    UserPojo userPojo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(Html.fromHtml("<font color=\"#FFFFFFFF\">" + "Login" + "</font>"));
        sqliteHelper = new SqliteHelper(this);
        userPojo=new UserPojo();

        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        userPojo = sqliteHelper.getLoginData();
        tv_sign_up = findViewById(R.id.tv_sign_up);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteHelper.saveLoginData(userPojo);

                userPojo.setMobile(et_mobile.getText().toString());
                userPojo.setPassword(et_password.getText().toString());
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
    }

