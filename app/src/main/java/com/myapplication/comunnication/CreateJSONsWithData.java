package com.myapplication.comunnication;

import android.os.Build;

import com.myapplication.constants.SessionConstants;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.json.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class CreateJSONsWithData {
    //TODO Sprawdzić zgodnośc z serwerem
    public static String Login(String login, String password) throws JSONException, NoSuchAlgorithmException {

        byte[] bytesPassword = password.getBytes();

        JSONObject loginJSON = new JSONObject();
        loginJSON.put("id", 1);
        loginJSON.put("login", login);
        loginJSON.put("password", new String(PasswordCombiner(bytesPassword)));

        return loginJSON.toString();
    }

    public static String Registration(String login, String password) throws JSONException, NoSuchAlgorithmException {

        byte[] bytesPassword = password.getBytes();

        JSONObject registerJSON = new JSONObject();
        registerJSON.put("id", 2);
        registerJSON.put("login", login);
        registerJSON.put("password", new String(PasswordCombiner(bytesPassword)));

        return registerJSON.toString();
    }

    public static String GetAllGroups(int userId) throws JSONException {

        JSONObject getGroupsJSON = new JSONObject();
        getGroupsJSON.put("id", 3);
        getGroupsJSON.put("userId", userId);

        return getGroupsJSON.toString();
    }

    public static String GetAllUsers() throws JSONException {

        JSONObject getUsersJSON = new JSONObject();
        getUsersJSON.put("id", 4);

        return getUsersJSON.toString();
    }

    public static String CreateGroup(String groupName) throws JSONException {

        JSONObject createGroupJSON = new JSONObject();
        createGroupJSON.put("id", 5);
        createGroupJSON.put("groupName", groupName);

        return createGroupJSON.toString();
    }

    public static String AddUserToGroup(int newGroupMemberId, int groupId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 6);
        addUserToGroupJSON.put("userId", newGroupMemberId);
        addUserToGroupJSON.put("groupId", groupId);

        return addUserToGroupJSON.toString();
    }

    public static String SendPrivateMessage(int authorId, String messageContent, int receiverId, Date date) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            byte[] cypherMessageContent = messageContent.getBytes();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            JSONObject sendPrivateMessageJSON = new JSONObject();
            sendPrivateMessageJSON.put("id", 7);
            sendPrivateMessageJSON.put("authorId", authorId);
            sendPrivateMessageJSON.put("receiverId", receiverId);
            sendPrivateMessageJSON.put("messageContent", Base64.getEncoder().encodeToString(Encryptor(cypherMessageContent, SessionConstants.KEY_IN_BYTES)));
            sendPrivateMessageJSON.put("date", dateFormat.format(date));

            return sendPrivateMessageJSON.toString();
        }
        return "null";
    }

    public static String SendGroupMessage(int authorId, String messageContent, int groupId, Date date) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            byte[] cypherMessageContent = messageContent.getBytes();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            JSONObject sendGroupMessageJSON = new JSONObject();
            sendGroupMessageJSON.put("id", 8);
            sendGroupMessageJSON.put("authorId", authorId);
            sendGroupMessageJSON.put("groupId", groupId);
            sendGroupMessageJSON.put("messageContent", Base64.getEncoder().encodeToString(Encryptor(cypherMessageContent, SessionConstants.KEY_IN_BYTES)));
            sendGroupMessageJSON.put("dateOfSend", dateFormat.format(date));

            return sendGroupMessageJSON.toString();
        }
        return "null";
    }

    public static String GetRecentPrivateMessage(int receiverId, int authorId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 9);
        addUserToGroupJSON.put("receiverId", receiverId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }

    public static String GetAllPrivateMessages(int receiverId, int authorId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 10);
        addUserToGroupJSON.put("receiverId", receiverId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }

    public static String GetRecentGroupMessage(int groupId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 11);
        addUserToGroupJSON.put("groupId", groupId);

        return addUserToGroupJSON.toString();
    }

    public static String GetAllGroupMessages(int groupId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 12);
        addUserToGroupJSON.put("groupId", groupId);

        return addUserToGroupJSON.toString();
    }

    public static byte[] Encryptor(byte[] message, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    public static byte[] PasswordCombiner(byte[] password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password);
        return messageDigest.digest();
    }

}