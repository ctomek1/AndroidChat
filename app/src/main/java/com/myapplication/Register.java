package com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Register extends AppCompatActivity {

    List<Account> accounts;

    public Register(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button loginButton = findViewById(R.id.loginButton);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Account tmpAccount = new Account(username.getText().toString(), password.getText().toString());
                for(int i = 0; i < accounts.size(); i++)
                {
                    if(tmpAccount != accounts.get(i))
                    {
                        accounts.add(tmpAccount);
                    }
                }
            }
        });
    }
}
