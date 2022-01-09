package com.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.Group;
import com.myapplication.R;

import java.util.ArrayList;

public class GroupsListAdapter extends RecyclerView.Adapter<GroupsListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Group> groupsList;

    public GroupsListAdapter(Context context, ArrayList<Group> groupsList) {
        this.context = context;
        this.groupsList = groupsList;
    }

    @NonNull
    @Override
    public GroupsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsListAdapter.MyViewHolder holder, int position) {

        Group group = groupsList.get(position);
        holder.groupsListElement.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupsListElement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupsListElement = itemView.findViewById(R.id.listElement);

            groupsListElement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = 1;
                }
            });
        }
    }
}
