package com.example.auctionapp.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.auctionapp.Admin.AdminDashBoard;
import com.example.auctionapp.Admin.AllUsers;
import com.example.auctionapp.MainActivity;
import com.example.auctionapp.R;
import com.example.auctionapp.User.UserAccount;
import com.example.auctionapp.User.UserRegister;

public class SellerDashBoard extends AppCompatActivity {
    Button addProduct,viewProducts,placedProducts,seller_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash_board);
        addProduct=findViewById(R.id.addProduct);
        viewProducts=findViewById(R.id.viewProducts);
        placedProducts=findViewById(R.id.placedProducts);
        seller_acc=findViewById(R.id.seller_acc);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerDashBoard.this, AddProducts.class));
            }
        });

        viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerDashBoard.this, ViewProducts.class));
            }
        });

        placedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerDashBoard.this, PlacedOrders.class));
            }
        });

        seller_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(SellerDashBoard.this, UserAccount.class);
                yourIntent.putExtra("refrenceId","Seller");
                startActivity(yourIntent);
            }
        });
    }
}