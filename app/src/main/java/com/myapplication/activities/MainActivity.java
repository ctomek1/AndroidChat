package com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapplication.comunnication.Communication;
import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.dialog.InputTextDialogClass;
import com.myapplication.models.Group;
import com.myapplication.R;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.models.User;
import com.myapplication.adapters.UsersListAdapter;
import com.myapplication.adapters.GroupsListAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.jsonparser.JsonParse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button contactsButton;
    Button groupsButton;
    Button createGroupButton;
    TextView listName;

    UsersListAdapter usersListAdapter;
    GroupsListAdapter groupsListAdapter;

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

        // TODO Usuń to
        SessionConstants.LIST_OF_USERS.add(new User(2, "User1"));
        SessionConstants.LIST_OF_USERS.add(new User(3, "User2"));

        SessionConstants.LIST_OF_GROUPS.add(new Group(1, "Group1"));
        SessionConstants.LIST_OF_GROUPS.add(new Group(2, "Group2"));

        getContactsData();
        getGroupsData();

        setUsersListToAdapter();
        createGroupButton.setVisibility(View.INVISIBLE);

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

                getTextFromInputTextDialog(); // TODO czekanie na wynik
                Thread thread = new Thread(new Runnable() {

                    @SneakyThrows
                    @Override
                    public void run() {

                        Communication communication = new Communication();
                        if (communication.getSocket().isConnected()) {
                            String result = communication.SendAndReceiveMessage(CreateJSONsWithData.CreateGroup("aaa"));


                            JSONObject jsonResult = new JSONObject(result);
                            if (jsonResult.getBoolean("result")) {

                                result = communication.SendAndReceiveMessage(CreateJSONsWithData.AddUserToGroup(SessionConstants.USER_ID, jsonResult.getInt("groupId")));
                                jsonResult = new JSONObject(result);
                                if (jsonResult.getBoolean("result")) {

                                    openAlertDialog(getResources().getString(R.string.groupCreateSuccess), getResources().getString(R.string.success));
                                }
                            }
                            else {
                                openAlertDialog(getResources().getString(R.string.groupCreateFailure), getResources().getString(R.string.failure));
                            }
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void getContactsData() {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {

                Communication communication = new Communication();
                if (communication.getSocket().isConnected()) {
                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllUsers());
                    JsonParse.toUsersList(result, SessionConstants.LIST_OF_USERS);
                }
            }
        });
        thread.start();
    }

    private void getGroupsData() {

        Thread thread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {

                Communication communication = new Communication();
                if (communication.getSocket() != null) {
                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllGroups(SessionConstants.USER_ID));
                    if (result != null) {
                        JsonParse.toGroupsList(result, SessionConstants.LIST_OF_GROUPS);

                    }
                }
            }
        });
        thread.start();
    }

    public void setUsersListToAdapter() {
        listName.setText(getResources().getString(R.string.contactsList));
        usersListAdapter = new UsersListAdapter(this, SessionConstants.LIST_OF_USERS);
        recyclerView.setAdapter(usersListAdapter);
    }

    public void setGroupsListToAdapter() {
        listName.setText(getResources().getString(R.string.groupsList));
        groupsListAdapter = new GroupsListAdapter(this, SessionConstants.LIST_OF_GROUPS);
        recyclerView.setAdapter(groupsListAdapter);
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }

    private void getTextFromInputTextDialog() {

        InputTextDialogClass inputTextDialogClass = new InputTextDialogClass();
        inputTextDialogClass.show(getSupportFragmentManager(), "InputTextDialogCreator");
    }
}