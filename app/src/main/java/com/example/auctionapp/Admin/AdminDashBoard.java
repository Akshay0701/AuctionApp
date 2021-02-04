package com.example.auctionapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.auctionapp.MainActivity;
import com.example.auctionapp.R;
import com.example.auctionapp.User.ProductDetails;

public class AdminDashBoard extends AppCompatActivity {
    Button backBtn,placedBtn,productsBtn,sellersBtn,usersBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        backBtn=findViewById(R.id.backBtn);
        placedBtn=findViewById(R.id.placedBtn);
        productsBtn=findViewById(R.id.productsBtn);
        sellersBtn=findViewById(R.id.sellersBtn);
        usersBtn=findViewById(R.id.usersBtn);

        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(AdminDashBoard.this, AllUsers.class);
                yourIntent.putExtra("refrenceId","Users");
                startActivity(yourIntent);
            }
        });
        sellersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(AdminDashBoard.this, AllUsers.class);
                yourIntent.putExtra("refrenceId","Seller");
                startActivity(yourIntent);
            }
        });
        productsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(AdminDashBoard.this, AllProductsAdmin.class);
                startActivity(yourIntent);
            }
        });
        placedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(AdminDashBoard.this, AllPlacedProducts.class);
                startActivity(yourIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent(AdminDashBoard.this, MainActivity.class);
                startActivity(yourIntent);
            }
        });
    }
}