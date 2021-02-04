package com.example.auctionapp.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auctionapp.Adapter.AdapterOrderPlaced;
import com.example.auctionapp.Adapter.AdapterProduct;
import com.example.auctionapp.Models.OrderPlaced;
import com.example.auctionapp.Models.Product;
import com.example.auctionapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlacedOrders extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
    String mUID;
    List<OrderPlaced> productList;
    AdapterOrderPlaced adapterProduct;
    ImageView searchbtn,vehicleBtn,buildingBtn,deviceBtn,otherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_orders);
        checkforuserlogin();
        productList = new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(PlacedOrders.this);
        recyclerView.setLayoutManager(layoutManager);
        loadBooks();
    }
    private void loadBooks() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderPlaced");
        //get all data from this ref
        productList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderPlaced products = ds.getValue(OrderPlaced.class);
                    if(products.getsId().equals(mUID));
                    productList.add(products);
                }
                //adapter
                adapterProduct= new AdapterOrderPlaced(PlacedOrders.this, productList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(PlacedOrders.this, LinearLayoutManager.VERTICAL, false));
                //set adapter to recycle
                recyclerView.setAdapter(adapterProduct);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checkforuserlogin() {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //  token=FirebaseInstanceId.getInstance().getToken();
            mUID = user.getUid();
        }
    }
}