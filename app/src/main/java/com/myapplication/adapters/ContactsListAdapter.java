package com.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.User;

import java.util.ArrayList;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> contactsList;

    public ContactsListAdapter(Context context, ArrayList<User> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ContactsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsListAdapter.MyViewHolder holder, int position) {

        User user = contactsList.get(position);
        holder.contactsListElement.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contactsListElement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactsListElement = itemView.findViewById(R.id.listElement);

            contactsListElement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = 2;
                }
            });
        }
    }
}