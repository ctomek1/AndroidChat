package com.chatproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String nicknames[], descriptions[];
    Context context;

    public MyAdapter(Context ct, String[] nicknames, String[] descriptions)
    {
            context = ct;
            nicknames = nicknames;
            descriptions = descriptions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nicknameText.setText(nicknames[position]);
        holder.descriptionText.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return nicknames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nicknameText, descriptionText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nicknameText = itemView.findViewById(R.id.nickname);
            descriptionText = itemView.findViewById(R.id.description);
        }
    }
}
