package com.example.auctionapp.User.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auctionapp.Adapter.AdapterOrderPlaced;
import com.example.auctionapp.Models.OrderPlaced;
import com.example.auctionapp.R;
import com.example.auctionapp.Seller.PlacedOrders;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
    String mUID;
    List<OrderPlaced> productList;
    AdapterOrderPlaced adapterProduct;
    ImageView searchbtn,vehicleBtn,buildingBtn,deviceBtn,otherBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        checkforuserlogin();
        productList = new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadBooks();
        return root;
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
                    if(products.getuId().equals(mUID));
                    productList.add(products);
                }
                //adapter
                adapterProduct= new AdapterOrderPlaced(getContext(), productList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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