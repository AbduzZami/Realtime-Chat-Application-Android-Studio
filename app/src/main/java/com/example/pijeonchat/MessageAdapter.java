package com.example.pijeonchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewholder> {

    Context context ;
    List<Chat> mChat;
    public static final int MSG_LEFT_TYPE = 0 ;
    public static final int MSG_RIGHT_TYPE = 1 ;
    FirebaseUser firebaseUser ;

    public MessageAdapter(Context context , List<Chat> mChat) {
        this.context = context;
        this.mChat = mChat ;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_item_right,parent,false);
            return new MessageAdapter.MyViewholder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_item_left, parent, false);
            return new MessageAdapter.MyViewholder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewholder holder, int position) {

        Chat chat = mChat.get(position);
        holder.textView.setText(chat.getMessage());
            if (holder.getItemViewType() == MSG_RIGHT_TYPE) {
                if (chat.getSeenstatus().equals("seen")) {
                    holder.textView2.setVisibility(View.VISIBLE);
                } else
                    holder.textView2.setVisibility(View.GONE);
            }


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder{
        public TextView textView , textView2;
        //public ImageView imageView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.msgtxt);
            textView2 = itemView.findViewById(R.id.seenstatustextid);
            //imageView = itemView.findViewById(R.id.imageView);


        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MSG_RIGHT_TYPE;
        }
        else
            return MSG_LEFT_TYPE;
    }
}

