package com.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.comunnication.Communication;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.models.User;
import com.myapplication.R;
import com.myapplication.constants.SessionConstants;

import org.json.JSONObject;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class AddUserToGroupAdapter extends RecyclerView.Adapter<AddUserToGroupAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> usersList;

    public AddUserToGroupAdapter(Context context, ArrayList<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public AddUserToGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new AddUserToGroupAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddUserToGroupAdapter.MyViewHolder holder, int position) {

        User user = usersList.get(position);
        holder.usersListElement.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView usersListElement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usersListElement = itemView.findViewById(R.id.listElement);

            usersListElement.setOnClickListener(new View.OnClickListener() {
                @SneakyThrows
                @Override
                public void onClick(View v) {

                    Thread thread = new Thread(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            TextView textView = (TextView) v;
                            Communication communication = new Communication();
                            if (communication.getSocket().isConnected()) {
                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.AddUserToGroup(getIdOfUserFromName(textView.getText().toString()), SessionConstants.CURRENT_GROUP_ID));
                                JSONObject jsonResult = new JSONObject(result);

                                if (jsonResult.getBoolean("result")) {
                                    openAlertDialog(v.getResources().getString(R.string.addUserToGroupSuccess), v.getResources().getString(R.string.success));
                                } else {
                                    openAlertDialog(v.getResources().getString(R.string.addUserToGroupFailure), v.getResources().getString(R.string.failure));
                                }
                            }
                            else {
                                Toast toast = Toast.makeText(v.getContext(), v.getResources().getString(R.string.connectionFailed), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                    });
                    thread.start();
                }
            });
        }
    }

    public int getIdOfUserFromName(String username) {
        int userId = 0;
        for (User user : usersList) {
            if (user.getName().equals(username)) {
                userId = user.getId();
            }
        }
        return userId;
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(((AppCompatActivity) context).getSupportFragmentManager(), "AlertDialogCreator");
    }
}
