package com.myapplication.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.myapplication.R;

import java.util.concurrent.TimeUnit;

public class InputTextDialogClass extends AppCompatDialogFragment {

    private String text = null;

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


}
