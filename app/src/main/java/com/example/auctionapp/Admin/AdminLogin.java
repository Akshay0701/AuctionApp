package com.example.auctionapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.auctionapp.R;
import com.example.auctionapp.Seller.SellerLogin;
import com.example.auctionapp.Seller.SellerRegister;

public class AdminLogin extends AppCompatActivity {

    Button admin_signIn,gotoRegister;
    TextView admin_password,admin_username;

    LinearLayout linearLayout;
    LottieAnimationView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin_signIn=findViewById(R.id.admin_signIn);
        admin_password=findViewById(R.id.admin_password);
        admin_username=findViewById(R.id.admin_username);

        admin_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign
                String email,password;
                email=admin_username.getText().toString();
                password=admin_password.getText().toString();
                if(email.equals("admin") && password.equals("admin")){
                    startActivity(new Intent(AdminLogin.this,AdminDashBoard.class));
                }else
                    Toast.makeText(AdminLogin.this, "Wrong Admin UserName And Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}