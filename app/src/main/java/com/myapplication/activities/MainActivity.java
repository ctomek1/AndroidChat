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
import com.myapplication.R;
import com.myapplication.Send;
import com.myapplication.User;
import com.myapplication.adapters.UsersListAdapter;
import com.myapplication.adapters.GroupsListAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.dialog.InputTextDialogClass;
import com.myapplication.json_parser.JsonParse;

import java.io.IOException;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button contactsButton;
    Button groupsButton;
    Button createGroupButton;
    TextView listName;

    ArrayList<User> usersList = new ArrayList<>();
    ArrayList<Group> groupsList = new ArrayList<>();

    UsersListAdapter usersListAdapter;
    GroupsListAdapter groupsListAdapter;

    Communication communication = new Communication();

    private Boolean isCreateGroupButtonVisible = false;

    public MainActivity() throws IOException {
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsButton = findViewById(R.id.contactsListButton);
        groupsButton = findViewById(R.id.groupsListButton);
        createGroupButton = findViewById(R.id.createGroupButton);

        listName = findViewById(R.id.listName);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        usersList.add(new User(1, "Użytkownik"));
        usersList.add(new User(2, "Użytkownik2"));
        groupsList.add(new Group(1, "Grupa"));
        groupsList.add(new Group(2, "Grupa2"));


        // getContactsData(communication);
        // getGroupsData(communication);

        setUsersListToAdapter();

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroupButton.setVisibility(View.INVISIBLE);
                setUsersListToAdapter();
            }
        });

        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroupButton.setVisibility(View.VISIBLE);
                setGroupsListToAdapter();
            }
        });

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // dodanie nowej grupy
            }
        });
    }

    private String getTextFromInputTextDialog() {

        InputTextDialogClass inputTextDialogClass = new InputTextDialogClass();
        inputTextDialogClass.show(getSupportFragmentManager(), "InputTextDialogCreator");
        String newGroupName = inputTextDialogClass.getText();

        return newGroupName;
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

    private void getGroupsData() {

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

    public void setUsersListToAdapter() {
        listName.setText(getResources().getString(R.string.contactsList));
        usersListAdapter = new UsersListAdapter(this, usersList);
        recyclerView.setAdapter(usersListAdapter);
    }

    public void setGroupsListToAdapter() {
        listName.setText(getResources().getString(R.string.groupsList));
        groupsListAdapter = new GroupsListAdapter(this, groupsList);
        recyclerView.setAdapter(groupsListAdapter);
    }
}