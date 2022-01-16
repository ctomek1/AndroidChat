package com.myapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.adapters.GroupsListAdapter;
import com.myapplication.adapters.UsersListAdapter;
import com.myapplication.comunnication.Communication;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.constants.SessionConstants;
import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.dialog.InputTextDialogClass;
import com.myapplication.jsonparser.JsonParse;
import com.myapplication.models.Group;
import com.myapplication.models.User;

import org.json.JSONObject;

import java.io.IOException;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button contactsButton;
    private Button groupsButton;
    private Button createGroupButton;
    private TextView listName;

    private UsersListAdapter usersListAdapter;
    private GroupsListAdapter groupsListAdapter;

    private Context context;

    public MainActivity() {
        this.context = this;
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
                getTextFromInputTextDialog();
                getGroupsData();
                setGroupsListToAdapter();
                recyclerView.smoothScrollToPosition(SessionConstants.LIST_OF_GROUPS.size());
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
                else {
                    openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
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
                if (communication.getSocket().isConnected()) {
                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllGroups(SessionConstants.USER_ID));
                    if (!result.equals("null")) {
                        JsonParse.toGroupsList(result, SessionConstants.LIST_OF_GROUPS);
                    }
                }
                else {
                    openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
                }
            }
        });
        thread.start();
    }

    private void setUsersListToAdapter() {
        listName.setText(getResources().getString(R.string.contactsList));
        usersListAdapter = new UsersListAdapter(this, SessionConstants.LIST_OF_USERS);
        recyclerView.setAdapter(usersListAdapter);
    }

    private void setGroupsListToAdapter() {
        listName.setText(getResources().getString(R.string.groupsList));
        groupsListAdapter = new GroupsListAdapter(this, SessionConstants.LIST_OF_GROUPS);
        recyclerView.setAdapter(groupsListAdapter);
    }

    private void getTextFromInputTextDialog() {

        InputTextDialogClass inputTextDialogClass = new InputTextDialogClass();
        inputTextDialogClass.show(getSupportFragmentManager(), "InputTextDialogCreator");

    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }
}