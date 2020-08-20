package com.example.pijeonchat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewholder> {

    Context context ;
    List<User> mUsers ;

    public RecyclerViewAdapter(Context context ,List<User> mUsers) {
        this.context = context;
        this.mUsers= mUsers ;
        //this.isChat=isChat;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new RecyclerViewAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        final User user = mUsers.get(position);

        holder.textView.setText(user.getUsername());
        holder.imageView.setImageResource(R.drawable.user);
        if (user.getStatus().equals("online"))
        {
            holder.textViewstatus.setVisibility(View.VISIBLE);
        }
        else if  (user.getStatus().equals("offline"))
        {
            holder.textViewstatus.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MessageActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder{
        public TextView textView ;
        public ImageView imageView;
        public TextView textViewstatus;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewstatus = itemView.findViewById(R.id.activetext);


        }
    }
}
