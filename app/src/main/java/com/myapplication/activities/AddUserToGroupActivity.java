package com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapplication.Communication;
import com.myapplication.Group;
import com.myapplication.Message;
import com.myapplication.R;
import com.myapplication.Send;
import com.myapplication.User;
import com.myapplication.adapters.AddUserToGroupAdapter;
import com.myapplication.adapters.UsersListAdapter;
import com.myapplication.adapters.GroupsListAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.json_parser.JsonParse;

import java.io.IOException;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class AddUserToGroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<User> usersList = new ArrayList<>();

    AddUserToGroupAdapter addUserToGroupAdapter;

    Communication communication = new Communication();

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

        usersList.add(new User(1, "Użytkownik"));
        usersList.add(new User(2, "Użytkownik2"));

        // getContactsData();
        setUsersListToAdapter();
    }

    private void getContactsData() {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                String result = communication.SendAndReceiveMessage(Send.GetAllUsers());
            }
        });
        thread.start();
    }

    public void setUsersListToAdapter() {
        addUserToGroupAdapter = new AddUserToGroupAdapter(this, usersList);
        recyclerView.setAdapter(addUserToGroupAdapter);
    }

}
