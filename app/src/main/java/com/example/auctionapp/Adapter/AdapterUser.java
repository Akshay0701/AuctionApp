package com.example.auctionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Models.User;
import com.example.auctionapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder>  {

    Context context;
    List<User> usersList;
    int action; //  0 view // 1 delete
    public AdapterUser(Context context, List<User> usersList, int action) {
        this.context = context;
        this.usersList = usersList;
        this.action=action;
    }

    @NonNull
    @Override
    public AdapterUser.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_user,parent,false);
        return new AdapterUser.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.MyHolder holder, final int position) {
        final String vId= usersList.get(position).getuId();
        final String name= usersList.get(position).getuName();
        final String address= usersList.get(position).getAddress();
        final String email= usersList.get(position).getEmail();

        //setdata
        holder.name.setText(""+name);
        holder.id.setText(""+ vId);
        holder.email.setText("Email : "+email);
        holder.address.setText("Address : "+address);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, email, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView name,id,email,address;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            email=itemView.findViewById(R.id.email);
            address=itemView.findViewById(R.id.address);
        }
    }

}