package com.example.auctionapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.auctionapp.Adapter.AdapterOrderPlaced;
import com.example.auctionapp.Adapter.AdapterProduct;
import com.example.auctionapp.Adapter.AdapterUser;
import com.example.auctionapp.Models.OrderPlaced;
import com.example.auctionapp.Models.Product;
import com.example.auctionapp.Models.Seller;
import com.example.auctionapp.Models.User;
import com.example.auctionapp.R;
import com.example.auctionapp.Seller.PlacedOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPlacedProducts extends AppCompatActivity {

    String mUID;
    AdapterOrderPlaced adapterProduct;
    List<OrderPlaced> productList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_placed_products);
        productList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(AllPlacedProducts.this);
        recyclerView.setLayoutManager(layoutManager);
        loadSearch();
    }

    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderPlaced");
        //get all data from this ref
        productList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderPlaced product= ds.getValue(OrderPlaced.class);
                    productList.add(product);
                }

                //adapter
                adapterProduct= new AdapterOrderPlaced(AllPlacedProducts.this,productList ,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(AllPlacedProducts.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}