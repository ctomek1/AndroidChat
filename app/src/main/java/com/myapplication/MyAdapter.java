package com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Contacts> contactsList;

    public MyAdapter(Context context, ArrayList<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Contacts contacts = contactsList.get(position);
        holder.username.setText(contacts.username);
        holder.description.setText(contacts.description);
        holder.profilePicture.setImageResource(contacts.profilePicture);

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        TextView description;
        ShapeableImageView profilePicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            description = itemView.findViewById(R.id.description);
        }
    }
}
