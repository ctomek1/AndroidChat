package com.myapplication.jsonparser;

import com.myapplication.models.Group;
import com.myapplication.models.Message;
import com.myapplication.models.User;
import com.myapplication.constants.SessionConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.SneakyThrows;


public class JsonParse {

    @SneakyThrows
    public static boolean toPrivateMessageList(String jsonMessageArray, List<Message> destinationList) {
        List<Message> msgl = new ArrayList<>();
        JSONArray allMessage, singleMessage;
        try {
            allMessage = new JSONArray(jsonMessageArray);
            for (int i = 0; i < allMessage.length(); i++) {
                singleMessage = new JSONArray(allMessage.getJSONArray(i).toString());
                Message msg = new Message();
                msg.setAuthorId(singleMessage.getJSONArray(0).getInt(1));
                msg.setMessage(singleMessage.getJSONArray(1).getString(1));
                msg.setDate(new Date(singleMessage.getJSONArray(2).getString(1)));
                msgl.add(msg);
            }
            destinationList.addAll(msgl);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean toGroupsList(String jsonGroupsArray, List<Group> destinationList) throws JSONException {
        List<Group> usersGroups = new ArrayList<>();
        JSONArray allGroups, eachGroup;
        allGroups = new JSONArray(jsonGroupsArray);
        for (int i = 0; i < allGroups.length(); i++) {
            eachGroup = new JSONArray(allGroups.getJSONArray(i).toString());
            Group group = new Group();
            group.setId(eachGroup.getJSONArray(0).getInt(1));
            group.setName(eachGroup.getJSONArray(1).getString(1));
            usersGroups.add(group);
        }
        destinationList.addAll(usersGroups);

        return true;
    }

    public static boolean toUsersList(String jsonGroupsArray, List<User> destinationList) throws JSONException {
        List<User> usersContact = new ArrayList<>();
        JSONArray allGroups, eachGroup;
        allGroups = new JSONArray(jsonGroupsArray);
        for (int i = 0; i < allGroups.length(); i++) {
            eachGroup = new JSONArray(allGroups.getJSONArray(i).toString());
            User user = new User();
            user.setId(eachGroup.getJSONArray(0).getInt(1));
            user.setName(eachGroup.getJSONArray(1).getString(1));
            usersContact.add(user);
        }
        destinationList.addAll(usersContact);

        return true;
    }

    public static boolean toRecentPrivateMessage(String jsonMessage, Message message) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, JSONException {
        JSONObject jmessage;
        message = new Message();
        jmessage = new JSONObject(jsonMessage);
        message.setDate(new Date(jmessage.getString("date")));
        message.setReceiverId(jmessage.getInt("receiverId"));
        message.setMessage(new String(Decryptor(jmessage.getString("message").getBytes(), SessionConstants.KEY_IN_BYTES)));
        // message.setAuthorId(jmessage.getInt("authorId"));

        return true;
    }

    public static boolean toRecentGroupMessage(String jsonGroupMessage, Message message) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, JSONException {
        JSONObject jmessage;
        message = new Message();
        jmessage = new JSONObject(jsonGroupMessage);
        message.setAuthorId(jmessage.getInt("authorId"));
        message.setDate(new Date(jmessage.getString("date")));
        message.setReceiverId(jmessage.getInt("groupId"));
        message.setMessage(new String(Decryptor(jmessage.getString("message").getBytes(), SessionConstants.KEY_IN_BYTES)));

        return true;
    }


    public static byte[] Decryptor(byte[] encryptedMessage, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage);
    }
}