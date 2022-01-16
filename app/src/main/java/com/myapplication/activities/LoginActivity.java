package com.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.comunnication.Communication;
import com.myapplication.R;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.constants.SessionConstants;

import org.json.JSONObject;

import lombok.SneakyThrows;

public class LoginActivity extends AppCompatActivity {

    private Context context;

    public LoginActivity() {
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    openAlertDialog(getResources().getString(R.string.notEnteredLoginOrPassword), getResources().getString(R.string.loginError));
                } else {
                    Thread thread = new Thread(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            Communication communication = new Communication();
                            if (communication.getSocket().isConnected()) {
                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.Login(username.getText().toString(), password.getText().toString()));
                                JSONObject jsonResult = new JSONObject(result);

                                if (jsonResult.getBoolean("result") == true) {

                                    SessionConstants.USER_ID = jsonResult.getInt("userId");
                                    startActivity(new Intent(context, MainActivity.class));
                                } else {
                                    openAlertDialog(getResources().getString(R.string.invalidLoginOrPassword), getResources().getString(R.string.loginError));
                                }
                            }
                            else {
                                openAlertDialog(v.getResources().getString(R.string.connectionFailed), v.getResources().getString(R.string.failure));
                            }
                        }
                    });
                    thread.start();
                }
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }
}
