package com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapplication.ChatActivity;
import com.myapplication.Communication;
import com.myapplication.Group;
import com.myapplication.Message;
import com.myapplication.R;
import com.myapplication.Send;
import com.myapplication.User;
import com.myapplication.adapters.ContactsListAdapter;
import com.myapplication.adapters.GroupsListAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.json_parser.JsonParse;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button contactsButton;
    Button groupsButton;
    TextView listName;

    int userId;
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
        listName = (TextView) findViewById(R.id.listName);
        recyclerView = findViewById(R.id.contactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        userId = SessionConstants.USER_ID;

        // Communication communication = new Communication();

        contactsList.add(new User(1, "Użytkownik"));
        groupsList.add(new Group(1, "Grupa"));

        // getContactsData(communication);
        // getGroupsData(communication);

        onContactsButton();

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactsButton();
            }
        });

        groupsButton.setOnClickListener(new View.OnClickListener() {
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
                String result = communication.SendAndReceiveMessage(Send.GetAllGroups(userId));
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
        listName.setText(getResources().getString(R.string.contactsList));
        contactsListAdapter = new ContactsListAdapter(this, contactsList);
        recyclerView.setAdapter(contactsListAdapter);
    }

    public void onGroupButton() {
        listName.setText(getResources().getString(R.string.groupsList));
        groupsListAdapter = new GroupsListAdapter(this, groupsList);
        recyclerView.setAdapter(groupsListAdapter);
    }
}