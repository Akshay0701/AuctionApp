package com.example.auctionapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Adapter.AdapterUser;
import com.example.auctionapp.Models.Seller;
import com.example.auctionapp.Models.User;
import com.example.auctionapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    String mUID;
    AdapterUser adapterUsers;
    List<User> usersList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    String refrenceId="Users";
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        Intent intent = getIntent();
        refrenceId = intent.getExtras().getString("refrenceId");
        usersList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        txt=findViewById(R.id.txt);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(AllUsers.this);
        recyclerView.setLayoutManager(layoutManager);
        loadSearch();
    }

    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference(refrenceId);
        //get all data from this ref
        usersList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User users;
                    if(refrenceId.equals("Users")) {
                        users = ds.getValue(User.class);
                        txt.setText("View All Users");
                    }
                    else {
                        txt.setText("View All Sellers");
                        Seller seller = ds.getValue(Seller.class);
                        users=new User(seller.getAddress(),seller.getCountry(),seller.getEmail(),seller.getPassword()
                                ,seller.getPhoneNo(),seller.getsId(),seller.getsName());
                    }
                    usersList.add(users);
                }

                //adapter
                adapterUsers= new AdapterUser(AllUsers.this, usersList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(AllUsers.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}