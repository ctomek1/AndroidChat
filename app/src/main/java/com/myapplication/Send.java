package com.myapplication;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.json.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Send {
    private final static String AES_KEY = "p3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C";
    private final static byte[] KEY_IN_BYTES = AES_KEY.getBytes();

    public static String Login(String login, String password) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherPassword = password.getBytes();

        JSONObject loginJSON = new JSONObject();
        loginJSON.put("id", 1);
        loginJSON.put("login", login);
        loginJSON.put("password", Encryptor(cypherPassword, KEY_IN_BYTES).toString());

        return loginJSON.toString();
    }

    public static String Registration(String login, String password) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherPassword = password.getBytes();

        JSONObject registerJSON = new JSONObject();
        registerJSON.put("id", 2);
        registerJSON.put("login", login);
        registerJSON.put("password", Encryptor(cypherPassword, KEY_IN_BYTES).toString());

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

    public static String SendPrivateMessage(int authorId, String messageContent, int receiverId, Date date) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherMessageContent = messageContent.getBytes();

        JSONObject sendPrivateMessageJSON = new JSONObject();
        sendPrivateMessageJSON.put("id", 7);
        sendPrivateMessageJSON.put("authorId", authorId);
        sendPrivateMessageJSON.put("receiverId", receiverId);
        sendPrivateMessageJSON.put("messageContent", Encryptor(cypherMessageContent, KEY_IN_BYTES).toString());
        sendPrivateMessageJSON.put("date", date);

        return sendPrivateMessageJSON.toString();
    }

    public static String SendGroupMessage(int authorId, String messageContent, int groupId, Date date) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherMessageContent = messageContent.getBytes();

        JSONObject sendGroupMessageJSON = new JSONObject();
        sendGroupMessageJSON.put("id", 8);
        sendGroupMessageJSON.put("authorId", authorId);
        sendGroupMessageJSON.put("groupId", groupId);
        sendGroupMessageJSON.put("messageContent", Encryptor(cypherMessageContent, KEY_IN_BYTES).toString());
        sendGroupMessageJSON.put("dateOfSend", date);

        return sendGroupMessageJSON.toString();
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

    public static String GetAllGroupMessages(int groupId) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

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

}