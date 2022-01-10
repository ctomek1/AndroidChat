package com.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.activities.ChatActivity;
import com.myapplication.R;
import com.myapplication.User;
import com.myapplication.constants.SessionConstants;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> usersList;

    public UsersListAdapter(Context context, ArrayList<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersListAdapter.MyViewHolder holder, int position) {

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
                @Override
                public void onClick(View v) {

                    TextView textView = (TextView) v;
                    SessionConstants.IS_USER_CHAT = true;
                    SessionConstants.CURRENT_RECEIVER_ID = getIdOfUserFromName(textView.getText().toString());
                    context.startActivity(new Intent(context, ChatActivity.class));
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
}
