/*
 * Copyright (c)  2020. Indev Consultancy Private Limited,
 * Auther : Varun Verma
 * Date : 2020/02/27
 * Class :
 * Modified Date :
 * Modifications :
 * Modified By :
 */

package com.indev.jubicare_premium.service.util;

/**
 * Created by Ravi Tamada on 28/09/16.
 * www.androidhive.info
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String TAG = "test_tag";
    public static final String KEY_DATA = "data";
}
