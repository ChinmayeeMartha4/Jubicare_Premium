/*
 * Copyright (c)  2020. Indev Consultancy Private Limited,
 * Auther : Varun Verma
 * Date : 2020/02/27
 * Class :
 * Modified Date :
 * Modifications :
 * Modified By :
 */

package com.example.jubicare_premium.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessagingService;
import com.example.jubicare_premium.service.util.Config;
import com.example.jubicare_premium.sqlitehelper.SharedPrefHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    SharedPrefHelper sharedPrefHelper;
    public void onTokenRefresh() {
        String refreshedToken = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
        sharedPrefHelper=new SharedPrefHelper(getApplicationContext());
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {

        sharedPrefHelper.setString("token",token);

        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }
}

