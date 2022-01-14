package com.myapplication.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.myapplication.R;

public class AlertDialogClass extends AppCompatDialogFragment {

    private String message;
    private String title;

    public AlertDialogClass(String message, String title) {
        this.message = message;
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), null);

        return alertDialog.create();
    }
}
