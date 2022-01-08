package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Contacts> contactsList;
    MyAdapter myAdapter;
    String[] nicknames;
    String[] descriptions;
    int[] imageId;

    int userId;

    RecyclerView chatRecyclerView;
    String[] messages;
    ArrayList<Message> messagesList;
    ChatActivity chatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = Login.getUserId();
        createContactsData();
    }

    private void createContactsData()
    {
        recyclerView = findViewById(R.id.contactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactsList = new ArrayList<Contacts>();

        myAdapter = new MyAdapter(this, contactsList);
        recyclerView.setAdapter(myAdapter);

        nicknames = new String[]{
                "nickname1",
                "nickname2",
        };

        imageId = new int[]{
                R.drawable.a,
                R.drawable.download
        };

        descriptions = new String[]{
                "random description",
                "not random description",
        };

        getContactsData();
    }

    private void createChatData()
    {
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setHasFixedSize(true);

        messagesList = new ArrayList<Message>();

        messages = new String[]{
                "mess",
                "age",
        };
    }

    private void getContactsData() {

        for(int i=0; i<imageId.length;i++){
            Contacts contacts = new Contacts(nicknames[i], descriptions[i], imageId[i]);
            contactsList.add(contacts);
        }
        myAdapter.notifyDataSetChanged();
    }

    public void onContactsButton(View view){

    }

    public void onGroupButton(View view){

    }
}