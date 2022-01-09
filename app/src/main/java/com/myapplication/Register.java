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

public class Register extends AppCompatActivity {

    public Register() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button registerButton = findViewById(R.id.registerButton);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView confirmPassword = findViewById(R.id.confirmPassword);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
                    openAlertDialog(getResources().getString(R.string.notEnteredLoginOrPassword), getResources().getString(R.string.registerError));
                } else {

                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        Thread thread = new Thread(new Runnable() {

                            @SneakyThrows
                            @Override
                            public void run() {

                                Communication communication = new Communication();
                                String result = communication.SendAndReceiveMessage(Send.Registration(username.getText().toString(), password.getText().toString()));
                                JSONObject jsonResult = new JSONObject(result);

                                if ((boolean) jsonResult.get("result") == true) {
                                    openAlertDialog(getResources().getString(R.string.registrationWasSuccessful), getResources().getString(R.string.registrationSuccessful));
                                    openNewActivity(Login.class);
                                } else {
                                    openAlertDialog(getResources().getString(R.string.userAlreadyExist), getResources().getString(R.string.registerError));
                                }
                            }
                        });
                        thread.start();
                    } else {

                        openAlertDialog(getResources().getString(R.string.passwordsNotTheSame), getResources().getString(R.string.invalidPasswords));
                    }
                }
            }
        });
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }

    private void openNewActivity(Class activityClass) {

        startActivity(new Intent(this, activityClass));
    }
}
