package com.example.bornittah.icapp.util;

/**
 * Created by john on 12/30/17.
 */

public class Constants {
    public abstract class config {
        /****** URL DECLARATION ******************************/
        public static final String DB_NAME = "registration_db";
        public static final int DB_VERSION = 1;
        public static final int TOTAL_TABLES = 2;


        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String PASSWORD = "password";
        public static final String USER_PHOTO = "user_photo";
        public static final String USERNAME = "username";
        public static final String USER_ROLE = "user_role";
        public static final String ONLINE = "online";

        public static final String AMOUNT = "amount";
        public static final String AMOUNT_WORD = "amount_word";
        public static final String START_DATE = "start_date";
        public static final String RETURN_DATE = "return_date";
        public static final String GUARANTOR1 = "guarantor1";
        public static final String GUARANTOR2 = "guarantor2";

        public static final String LOANS = "loans";




        public static final String MESSAGES = "messages";
        public static final String USERS = "users";
        public static final String CONDUCTORS = "Conductor";
        public static final String REPLY = "reply";



        public static final String APP_FOLDER = "student_registration";
        public static final String IMAGE_SUB_FOLDER = "photos";
        public static final String STORAGE_PATH_UPLOADS = "photos/";
        public static final String DATABASE_PATH_UPLOADS = "uploads";

        //public static final String FIREBASE_URL = "https://foodscanner-466e0.firebaseio.com/";
        public static final String FIREBASE_URL = "https://investmentclub-dbc9f.firebaseio.com/";

        public static final String STORAGE_REFERENCE_CHATT = "gs://mobicarev3.appspot.com/chatts/";
        public static final String STORAGE_REFERENCE_PROFILE = "gs://mobicarev3.appspot.com/profiles/";

        // global topic to receive app wide push notifications
        public static final String TOPIC_GLOBAL = "global";

        // broadcast receiver intent filters
        public static final String REGISTRATION_COMPLETE = "registrationComplete";
        public static final String PUSH_NOTIFICATION = "pushNotification";

        // id to handle the notification in the notification tray
        public static final int NOTIFICATION_ID = 100;
        public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

        public static final String SHARED_PREF = "ah_firebase";
        public static final String KEY_TOKEN = "regId";
        public static final String IMEI = "imei";
        public static final String DEVICES = "devices";
        public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
        public static final String UID = "AAAA5mVkmFo:APA91bHKC0Dxm0wWHh5MzPvrywZngpZL4NnWkfoUulBvvWgT_zgnisn-jh_qM_QSwZMtZC_M9-SR99EswA0ZEni__ZIKgplCJGo_FE3XYrwZ0ZAO6MJA7dtzfmmTwMkWzVl33JcFkiE9";

    }
}
