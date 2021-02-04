package com.example.auctionapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auctionapp.Models.Auction;
import com.example.auctionapp.Models.Product;
import com.example.auctionapp.R;
import com.example.auctionapp.User.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAuction extends RecyclerView.Adapter<AdapterAuction.MyHolder>  {

    Context context;
    List<Auction> auctionList;
    boolean isUser;
    int action;//0 view 1 update 2 remove
    public AdapterAuction(Context context, List<Auction> auctionList) {
        this.context = context;
        this.auctionList = auctionList;
    }

    @NonNull
    @Override
    public AdapterAuction.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_auction,parent,false);
        return new AdapterAuction.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String uId= auctionList.get(position).getuId();
        final String price= auctionList.get(position).getPrice();

        //setdata
        holder.uId.setText(""+ uId);
        holder.price.setText("Price : $"+price);
        //handle click
    }
    @Override
    public int getItemCount() {
        return auctionList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView uId,price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            uId=itemView.findViewById(R.id.uIdTxt);
            price=itemView.findViewById(R.id.userPrice);
        }
    }

}