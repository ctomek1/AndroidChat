package com.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.constants.SessionConstants;
import com.myapplication.models.Message;
import com.myapplication.R;
import com.myapplication.models.User;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Message> messagesList;

    public ChatAdapter(Context context, ArrayList<Message> messagesList) {
        this.context = context;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new ChatAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messagesList.get(position);

        holder.author.setText(getAuthorName(message.getAuthorId()));
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textMessage);
            author = itemView.findViewById(R.id.authorOfMessage);
        }
    }

    private String getAuthorName(int authorId) {
        String authorName = "";
        if (SessionConstants.USER_ID == authorId) {
            authorName = "You";
        }
        else {
           for (User user : SessionConstants.LIST_OF_USERS) {
               if (user.getId() == authorId) {
                   authorName = user.getName();
                   break;
               }
           }
        }
        return authorName;
    }
}
