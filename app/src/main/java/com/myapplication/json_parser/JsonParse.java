package com.myapplication.json_parser;

import com.myapplication.Group;
import com.myapplication.Message;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonParse {

    public static boolean toPrivateMessageList(String jsonMessageArray, List<Message> destinationList){
        List<Message> msgl = new ArrayList<>();
        JSONArray allMessage,singleMessage;
        try {
            allMessage = new JSONArray(jsonMessageArray);
            for(int i = 0; i<allMessage.length(); i++){
                singleMessage = new JSONArray(allMessage.getJSONArray(i).toString());
                Message msg = new Message();
                msg.setAuthorId(singleMessage.getJSONArray(0).getInt(1));
                msg.setMessage(singleMessage.getJSONArray(1).getString(1));
                msg.setDate(new Date(singleMessage.getJSONArray(2).getString(1)));
                msgl.add(msg);
            }
            destinationList.addAll(msgl);
        }catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean toUsersGroupsList(String jsonGroupsArray, List<Group> destinationList){
        List<Group> usersGroups = new ArrayList<>();
        JSONArray allGroups,eachGroup;
        try{
            allGroups = new JSONArray(jsonGroupsArray);
            for(int i = 0; i<allGroups.length();i++){
                eachGroup = new JSONArray(allGroups.getJSONArray(i).toString());
                Group group = new Group();
                group.setId(eachGroup.getJSONArray(0).getInt(1));
                group.setName(eachGroup.getJSONArray(1).getString(1));
                usersGroups.add(group);
            }
            destinationList.addAll(usersGroups);
        }catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }







}