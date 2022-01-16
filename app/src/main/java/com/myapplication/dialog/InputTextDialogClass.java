package com.myapplication.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.myapplication.R;
import com.myapplication.comunnication.Communication;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.constants.SessionConstants;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class InputTextDialogClass extends AppCompatDialogFragment {

    private String text = "";

    public InputTextDialogClass() {
    }

    public String getText() {
        return text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder inputTextDialog  = new AlertDialog.Builder(getActivity());
        inputTextDialog.setTitle(getResources().getString(R.string.enterNewGroupName));

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        inputTextDialog.setView(input);

        inputTextDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        inputTextDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                text = input.getText().toString();
                if (!text.equals("")) {
                    Thread thread = new Thread(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {

                            Communication communication = new Communication();
                            if (communication.getSocket().isConnected()) {
                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.CreateGroup(text));

                                JSONObject jsonResult = new JSONObject(result);
                                if (jsonResult.getBoolean("result")) {
                                    communication = new Communication();
                                    if (communication.getSocket().isConnected()) {
                                    result = communication.SendAndReceiveMessage(CreateJSONsWithData.AddUserToGroup(SessionConstants.USER_ID, jsonResult.getInt("groupId")));
                                    jsonResult = new JSONObject(result);
                                    if (jsonResult.getBoolean("result")) {

                                        openAlertDialog(getResources().getString(R.string.groupCreateSuccess), getResources().getString(R.string.success));
                                    }
                                } else {
                                    openAlertDialog(getResources().getString(R.string.groupCreateFailure), getResources().getString(R.string.failure));
                                }
                                }
                            } else {
                                openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
                            }
                        }
                    });
                    thread.start();
                }
            }
        });

        return inputTextDialog.create();
    }

    public void show(FragmentManager fragmentManager,String tag){
        super.show(fragmentManager,tag);

        while(this.text == null){
            try {
                TimeUnit.MILLISECONDS.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "AlertDialogCreator");
    }
}
