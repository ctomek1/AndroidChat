package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myapplication.constants.SessionConstants;
import com.myapplication.json_parser.JsonParse;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button contactsButton;
    Button groupsButton;


    ArrayList<User> contactsList = new ArrayList<>();
    ArrayList<Group> groupsList = new ArrayList<>();

    ContactsListAdapter contactsListAdapter;
    GroupsListAdapter groupsListAdapter;

    RecyclerView chatRecyclerView;
    String[] messages;
    ArrayList<Message> messagesList;
    ChatActivity chatActivity;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsButton = findViewById(R.id.contactsListButton);
        groupsButton = findViewById(R.id.groupsListButton);
        recyclerView = findViewById(R.id.contactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        Communication communication = new Communication();
/*
        contactsList.add(new User(1, "sieeeema"));
        groupsList.add(new Group(1, "ellooo"));*/

        getContactsData(communication);
        getGroupsData(communication);

        contactsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onContactsButton();
            }
        });

        groupsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onGroupButton();
            }
        });
    }

    private void getContactsData(Communication communication) {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                String result = communication.SendAndReceiveMessage(Send.GetAllUsers());
            }
        });
        thread.start();
    }

    private void getGroupsData(Communication communication) {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                String result = communication.SendAndReceiveMessage(Send.GetAllGroups(SessionConstants.USER_ID));
                JsonParse.toUsersGroupsList(result, groupsList);
            }
        });
        thread.start();
    }

   /* private void createChatData()
    {
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setHasFixedSize(true);

        messagesList = new ArrayList<Message>();

        messages = new String[]{
                "mess",
                "age",
        };
    }*/

    /*private void getContactsData() {

        for(int i=0; i<imageId.length;i++){
            Contacts contacts = new Contacts(nicknames[i], descriptions[i], imageId[i]);
            contactsList.add(contacts);
        }
        myAdapter.notifyDataSetChanged();
    }*/

    public void onContactsButton() {
        contactsListAdapter = new ContactsListAdapter(this, contactsList);
        recyclerView.setAdapter(contactsListAdapter);
    }

    public void onGroupButton() {
        groupsListAdapter = new GroupsListAdapter(this, groupsList);
        recyclerView.setAdapter(groupsListAdapter);
    }
}