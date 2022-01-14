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
import com.myapplication.models.Group;
import com.myapplication.R;
import com.myapplication.constants.SessionConstants;

import java.util.ArrayList;

public class GroupsListAdapter extends RecyclerView.Adapter<GroupsListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Group> groupsList;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupsListElement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupsListElement = itemView.findViewById(R.id.listElement);

            groupsListElement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    SessionConstants.IS_USER_CHAT = false;
                    SessionConstants.CURRENT_GROUP_ID = getIdOfGroupFromName(textView.getText().toString());
                    SessionConstants.CURRENT_GROUP_NAME = textView.getText().toString();
                    context.startActivity(new Intent(context, ChatActivity.class));
                }
            });
        }
    }

    public int getIdOfGroupFromName(String groupname) {
        int groupId = 0;
        for (Group group : groupsList) {
            if (group.getName().equals(groupname)) {
                groupId = group.getId();
            }
        }
        return groupId;
    }
}
