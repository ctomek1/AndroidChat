package com.myapplication.constants;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class SessionConstants {
    public static Integer USER_ID;
    public static Integer CURRENT_RECEIVER_ID;
    public static String CURRENT_RECEIVER_NAME;
    public static Integer CURRENT_GROUP_ID;
    public static String CURRENT_GROUP_NAME;
    public static Boolean IS_USER_CHAT;
    public static Context CONTEXT;
    public final static String AES_KEY = "p3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C";
    public final static byte[] KEY_IN_BYTES = AES_KEY.getBytes();
}
