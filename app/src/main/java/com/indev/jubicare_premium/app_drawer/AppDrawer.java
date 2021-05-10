package com.indev.jubicare_premium.app_drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indev.jubicare_premium.Login;
import com.indev.jubicare_premium.R;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.indev.jubicare_premium.change_password.ChangePassword;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.indev.jubicare_premium.sqlitehelper.SqliteHelper;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class AppDrawer  extends AppCompatActivity {
    public View view;
    public FrameLayout frame;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public ActionBarDrawerToggle drawerToggle;
    public Menu menu;
    public Menu nav_menu;
    public TextView tv_name, tv_number;
    public ImageView iv_imageView;
    public TextView tvTitle;
    public RelativeLayout rl_bell_icon;
    public ImageView iv_notification;
    public TextView tv_notification_count;
    Context context = this;
    ProgressDialog mProgressDialog;
    SqliteHelper sqliteHelper;
    SharedPrefHelper sharedPrefHelper;

    String filePath = null;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        view = getLayoutInflater().inflate(R.layout.activity_app_drawer, null);
        frame = view.findViewById(R.id.frame);
        getLayoutInflater().inflate(layoutResID, frame, true);
        super.setContentView(view);

        drawer = findViewById(R.id.drawer);
        iv_imageView = findViewById(R.id.iv_imageView);
        navigationView = findViewById(R.id.navigationView);
        tvTitle = findViewById(R.id.tvTitle);
        sharedPrefHelper = new SharedPrefHelper(this);

        sqliteHelper = new SqliteHelper(this);
        mProgressDialog = new ProgressDialog(this);


        //make condition for admin and user
        navigationView.inflateMenu(R.menu.user_menu_list);
        menu = navigationView.getMenu();
        /*hide and show navigation items*/


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        View header = navigationView.getHeaderView(0);

        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        tv_number = (TextView) header.findViewById(R.id.tv_headerSubName);
        iv_imageView = (ImageView) header.findViewById(R.id.iv_imageView);

        //get preference user date here
        String full_name = sharedPrefHelper.getString("full_name", "");
        String profile_pic = sharedPrefHelper.getString("profile_pic", "");
        String contact_no = sharedPrefHelper.getString("contact_no", "");


        tv_name.setText(full_name);
        tv_number.setText(contact_no);
        if (profile_pic.equalsIgnoreCase("")) {
            iv_imageView.setImageResource(R.drawable.user_placeholder);
        } else {
            Picasso.with(context)
                    .load(APIClient.IMAGE_URL + profile_pic)
                    .placeholder(R.drawable.user_placeholder)
                    .into(iv_imageView);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(false);//when using our custom drawer icon
        drawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();


        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer);

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });



                nav_menu = navigationView.getMenu();

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        drawer.closeDrawers();
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.option_home:
                                drawer.closeDrawers();
                                break;
                            case R.id.option_change_password:
                                Intent intent = new Intent(context, ChangePassword.class);
                                startActivity(intent);
                                //  finish();
                                //  Toast.makeText(AppDrawer.this, "Change Password", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                                break;
                            case R.id.option_notifications:

                            case R.id.option_aboutUs:
                                Toast.makeText(AppDrawer.this, "About Us", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                                break;

                            case R.id.option_settings:
                                Toast.makeText(AppDrawer.this, "Settings", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                                break;
                            case R.id.option_Contact_us:

                            case R.id.option_logout:
                                logoutDialog();
//                                if (isInternetOn()) {
//
////                                    callLogoutApi(context,mProgressDialog);
//
//                                } else {
//                                    Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//                                }
//                                overridePendingTransition(R.anim.open_next, R.anim.close_next);
                                break;
                        }
                        return true;
                    }
                });
            }

    private void logoutDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Logout!")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefHelper.setString("is_login","");
                        Intent i = new Intent(AppDrawer.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    public boolean isInternetOn() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED
                || connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;

        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

//    private void callLogoutApi(Context context, ProgressDialog progressDialog) {
//        sharedPrefHelper.setString("role_id", "");
//        SharedPreferences preferences = getSharedPreferences("Sinch", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
////        if (!sharedPrefHelper.getString("sinch_user","").equals("")) {
////            if (getSinchServiceInterface() != null) {
////                UserController uc = Sinch.getUserControllerBuilder()
////                        .context(getApplicationContext())
////                        .applicationKey(SinchService.APP_KEY)
////                        .userId(sharedPrefHelper.getString("sinch_user", ""))
////                        .environmentHost(SinchService.ENVIRONMENT)
////                        .build();
////                uc.unregisterPushToken();
////                getSinchServiceInterface().stopClient();
////            }
////        }
//        progressDialog = ProgressDialog.show(context, "Logout", "Please Wait...", true);
//        CountInput countInput = new CountInput();
//        countInput.setUser_id(sharedPrefHelper.getString("user_id", ""));
//
//        Gson mGson = new Gson();
//        String data = mGson.toJson(countInput);
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, data);
//        ProgressDialog finalProgressDialog = progressDialog;
//        APIClient.getClient().create(TELEMEDICINE_API.class).callLogoutApi(body).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response.body().toString());
//                    finalProgressDialog.dismiss();
//                    Log.e("jbfd", "nsh " + jsonObject.toString());
//
//                    if (jsonObject.optString("success").equalsIgnoreCase("1")) {
//                        Intent intentLogout = new Intent(context, Login.class);
//                        intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intentLogout);
//                        finish();
//                    } else {
//                        Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    finalProgressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                finalProgressDialog.dismiss();
//            }
//        });
//    }


}