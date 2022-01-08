package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.SneakyThrows;

public class Login extends AppCompatActivity {

    private static int userId;

    public Login() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

               // userId = 1;
               // openNewActivity(MainActivity.class);

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    openAlertDialog(getResources().getString(R.string.notEnteredLoginOrPassword), getResources().getString(R.string.loginError));
                } else {
                    Thread thread = new Thread(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {

                            Communication communication = new Communication();
                            String result = communication.SendAndReceiveMessage(Send.Login(username.getText().toString(), password.getText().toString()));
                            JSONObject jsonResult = new JSONObject(result);

                            if ((boolean) jsonResult.get("result") == true) {

                                userId = (int) jsonResult.get("userId");
                                openNewActivity(MainActivity.class);
                            } else {
                                openAlertDialog(getResources().getString(R.string.invalidLoginOrPassword), getResources().getString(R.string.loginError));
                            }
                        }

                    });
                    thread.start();
                    thread.stop();
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openNewActivity(Register.class);
            }
        });
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass =  new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }

    private void openNewActivity(Class activityClass) {

        startActivity(new Intent(this, activityClass));
    }

    public static int getUserId() {
        return userId;
    }
}
