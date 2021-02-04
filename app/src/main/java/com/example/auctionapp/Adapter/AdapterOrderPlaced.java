package com.example.auctionapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Models.OrderPlaced;
import com.example.auctionapp.Models.Product;
import com.example.auctionapp.R;
import com.example.auctionapp.Seller.SellerProductDetails;
import com.example.auctionapp.User.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOrderPlaced extends RecyclerView.Adapter<AdapterOrderPlaced.MyHolder>  {

    Context context;
    List<OrderPlaced> productsList;
    boolean isUser;
    int action;//0 user 1 seller 2 admin
    public AdapterOrderPlaced(Context context, List<OrderPlaced> productsList,int action) {
        this.context = context;
        this.productsList = productsList;
        this.action=action;
    }

    @NonNull
    @Override
    public AdapterOrderPlaced.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new AdapterOrderPlaced.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderPlaced.MyHolder holder, final int position) {
        final String sId= productsList.get(position).getsId();
        final String uName= productsList.get(position).getuName();
        final String pName= String.valueOf(productsList.get(position).getpName());
        final String price= productsList.get(position).getPrice();
        final String imageUrl= productsList.get(position).getpImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.pName.setText(""+pName);
        holder.totalPrice.setText("Max Price : $"+ price);
        holder.vendorId.setText("Seller Id : "+sId);
        holder.endTime.setText(""+uName);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(context, "Clicked "+pName, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return productsList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView pName,vendorId,totalPrice,endTime;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            pName=itemView.findViewById(R.id.pName);
            vendorId=itemView.findViewById(R.id.selllerId);
            totalPrice=itemView.findViewById(R.id.maxPrice);
            endTime=itemView.findViewById(R.id.endTime);
        }
    }

}