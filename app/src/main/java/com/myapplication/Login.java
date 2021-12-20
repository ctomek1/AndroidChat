package com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Login extends AppCompatActivity {

    List<Account> accounts;

    public Login(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
