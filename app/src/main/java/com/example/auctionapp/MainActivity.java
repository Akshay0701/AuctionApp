package com.example.auctionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.auctionapp.Admin.AdminLogin;
import com.example.auctionapp.Seller.SellerRegister;
import com.example.auctionapp.User.UserRegister;

public class MainActivity extends AppCompatActivity {
    Button user_login,vendor_login,admin_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_login=findViewById(R.id.userBtn);
        vendor_login=findViewById(R.id.sellerBtn);
        admin_login=findViewById(R.id.adminBtn);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserRegister.class));
            }
        });
        vendor_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SellerRegister.class));
            }
        });
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });
    }
}