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

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessagesViewHolder> {

    Context context;
    ArrayList<Message> messagesList;

    public ChatAdapter(Context context, ArrayList<Message> messagesList) {
        this.context = context;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public ChatAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new MessagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MessagesViewHolder holder, int position) {

        Message message = messagesList.get(position);
        holder.message.setText(message.getMessage());
        holder.author.setText(message.getAuthorName());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView author;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textMessage);
            author = itemView.findViewById(R.id.authorOfMessage);
        }
    }
}
