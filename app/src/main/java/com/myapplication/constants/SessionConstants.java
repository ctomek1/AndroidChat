package com.myapplication.constants;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.myapplication.models.Group;
import com.myapplication.models.User;

import java.util.ArrayList;

public class SessionConstants {

    public static Integer USER_ID;
    public static Integer CURRENT_RECEIVER_ID;
    public static String CURRENT_RECEIVER_NAME;
    public static Integer CURRENT_GROUP_ID;
    public static String CURRENT_GROUP_NAME;
    public static ArrayList<User> LIST_OF_USERS = new ArrayList<>();
    public static ArrayList<Group> LIST_OF_GROUPS = new ArrayList<>();
    public static Boolean IS_USER_CHAT;
    public final static String AES_KEY = "p3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C";
    public final static byte[] KEY_IN_BYTES = AES_KEY.getBytes();
}
