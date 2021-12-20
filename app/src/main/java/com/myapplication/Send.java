package com.myapplication;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.json.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Send
{
    private final static String AES_KEY = "p3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C";
    private final static byte[] KEY_IN_BYTES = AES_KEY.getBytes();

    public String Login(String login, String password) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherPassword = password.getBytes();

        JSONObject loginJSON = new JSONObject();
        loginJSON.put("id", 1);
        loginJSON.put("login", login);
        loginJSON.put("password", Encryptor(cypherPassword, KEY_IN_BYTES).toString());

        return loginJSON.toString();
    }

    public String Registration(String login, String password) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherPassword = password.getBytes();

        JSONObject registerJSON = new JSONObject();
        registerJSON.put("id", 2);
        registerJSON.put("login", login);
        registerJSON.put("password", Encryptor(cypherPassword, KEY_IN_BYTES).toString());

        return registerJSON.toString();
    }

    public String GetAllGroups() throws JSONException {

        JSONObject getGroupsJSON = new JSONObject();
        getGroupsJSON.put("id", 3);

        return getGroupsJSON.toString();
    }

    public String GetAllUsers() throws JSONException {

        JSONObject getUsersJSON = new JSONObject();
        getUsersJSON.put("id", 4);

        return getUsersJSON.toString();
    }

    public String CreateGroup(String groupName) throws JSONException {

        JSONObject createGroupJSON = new JSONObject();
        createGroupJSON.put("id", 5);
        createGroupJSON.put("groupName", groupName);

        return createGroupJSON.toString();
    }

    public String AddUserToGroup(int userId, int groupId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 6);
        addUserToGroupJSON.put("userId", userId);
        addUserToGroupJSON.put("groupId", groupId);

        return addUserToGroupJSON.toString();
    }

    public String SendPrivateMessage(int recevierId, String messageContent, Date dateOfSend) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherMessageContent = messageContent.getBytes();

        JSONObject sendPrivateMessageJSON = new JSONObject();
        sendPrivateMessageJSON.put("id", 7);
        sendPrivateMessageJSON.put("recevierId", recevierId);
        sendPrivateMessageJSON.put("messageContent", Encryptor(cypherMessageContent, KEY_IN_BYTES).toString());
        sendPrivateMessageJSON.put("dateOfSend", dateOfSend);

        return sendPrivateMessageJSON.toString();
    }

    public String SendGroupMessage(int groupId, String messageContent, Date dateOfSend) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] cypherMessageContent = messageContent.getBytes();

        JSONObject sendGroupMessageJSON = new JSONObject();
        sendGroupMessageJSON.put("id", 8);
        sendGroupMessageJSON.put("groupId", groupId);
        sendGroupMessageJSON.put("messageContent", Encryptor(cypherMessageContent, KEY_IN_BYTES).toString());
        sendGroupMessageJSON.put("dateOfSend", dateOfSend);

        return sendGroupMessageJSON.toString();
    }

    public String GetRecentPrivateMessage(int receiverId, int authorId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 9);
        addUserToGroupJSON.put("receiverId", receiverId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }

    public String GetAllPrivateMessages(int receiverId, int authorId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 10);
        addUserToGroupJSON.put("receiverId", receiverId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }

    public String GetRecentGroupMessage(int groupId, int authorId) throws JSONException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 11);
        addUserToGroupJSON.put("groupId", groupId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }

    public String GetAllGroupMessages(int groupId, int authorId) throws JSONException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        JSONObject addUserToGroupJSON = new JSONObject();
        addUserToGroupJSON.put("id", 12);
        addUserToGroupJSON.put("groupID", groupId);
        addUserToGroupJSON.put("authorId", authorId);

        return addUserToGroupJSON.toString();
    }


    public byte[] Encryptor(byte[] message, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

}