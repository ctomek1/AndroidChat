package com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    ArrayList<Account> accounts;

    public Login(ArrayList<Account> accounts)
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
                    if(tmpAccount == accounts.get(i))
                    {
                        //TODO login success
                    }
                }
            }
        });
    }
}
