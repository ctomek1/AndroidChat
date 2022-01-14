package com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.myapplication.comunnication.Communication;
import com.myapplication.R;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.constants.SessionConstants;
import com.myapplication.models.User;
import com.myapplication.adapters.AddUserToGroupAdapter;

import java.io.IOException;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class AddUserToGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<User> usersList = new ArrayList<>();
    private AddUserToGroupAdapter addUserToGroupAdapter;

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

        setUsersListToAdapter();
    }

    private void setUsersListToAdapter() {
        addUserToGroupAdapter = new AddUserToGroupAdapter(this, SessionConstants.LIST_OF_USERS);
        recyclerView.setAdapter(addUserToGroupAdapter);
    }

}
