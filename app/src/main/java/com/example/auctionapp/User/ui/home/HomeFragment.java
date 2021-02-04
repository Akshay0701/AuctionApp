package com.example.auctionapp.User.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auctionapp.Adapter.AdapterProduct;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;

    List<Product> productList;
    AdapterProduct adapterProduct;
    ImageView searchbtn,vehicleBtn,buildingBtn,deviceBtn,otherBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        checkforuserlogin();
        productList = new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadBooks();
        return root;
    }
    private void loadBooks() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Products");
        //get all data from this ref
        productList.clear();
        long timestamp=System.currentTimeMillis();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product products = ds.getValue(Product.class);
//                    if (timestamp>products.getStartTime()&&timestamp<products.getEndTime()){
//                        Toast.makeText(getContext(), "Enter", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getContext(), "No Entry", Toast.LENGTH_SHORT).show();
//                    }
                    productList.add(products);
                }
                //adapter
                adapterProduct= new AdapterProduct(getActivity(), productList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
            String mUID = user.getUid();
        }
    }
}