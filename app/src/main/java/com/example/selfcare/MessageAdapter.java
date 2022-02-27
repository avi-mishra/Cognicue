package com.example.selfcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    ArrayList<Chat> chatList;
    Context context;
    public MessageAdapter(Context context,ArrayList<Chat> chatList){
        this.context = context;
        this.chatList=chatList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.message_layout, parent, false);
        return new MessageAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
Chat chat = chatList.get(position);
if(chat.getSender().equals(FirebaseAuth.getInstance().getUid()))
{
    holder.message.setText(chat.getMessage());
    holder.name.setText("You");
}
else
{
    holder.message.setText(chat.getMessage());
    holder.name.setText("Anonymous");
    holder.layout.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient_receive));
}
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,message;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
