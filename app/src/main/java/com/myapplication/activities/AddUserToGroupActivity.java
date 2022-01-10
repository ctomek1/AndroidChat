package com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.myapplication.comunnication.Communication;
import com.myapplication.R;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.models.User;
import com.myapplication.adapters.AddUserToGroupAdapter;

import java.io.IOException;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class AddUserToGroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<User> usersList = new ArrayList<>();

    AddUserToGroupAdapter addUserToGroupAdapter;

    public AddUserToGroupActivity() throws IOException {
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_to_group);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        usersList.add(new User(2, "User1"));
        usersList.add(new User(3, "User2"));

        getContactsData();
        setUsersListToAdapter();
    }

    private void getContactsData() {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                Communication communication = new Communication();
                if (communication.getSocket().isConnected()) {
                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllUsers());
                }
            }
        });
        thread.start();
    }

    public void setUsersListToAdapter() {
        addUserToGroupAdapter = new AddUserToGroupAdapter(this, usersList);
        recyclerView.setAdapter(addUserToGroupAdapter);
    }

}
