package com.myapplication.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.adapters.AddUserToGroupAdapter;
import com.myapplication.constants.SessionConstants;

import lombok.SneakyThrows;

public class AddUserToGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddUserToGroupAdapter addUserToGroupAdapter;

    public AddUserToGroupActivity() {
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_to_group_activity);
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
