package com.example.auctionapp.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Adapter.AdapterAuction;
import com.example.auctionapp.Models.Auction;
import com.example.auctionapp.Models.OrderPlaced;
import com.example.auctionapp.Models.Product;
import com.example.auctionapp.R;
import com.example.auctionapp.User.ProductDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellerProductDetails extends AppCompatActivity {
    ImageView img;
    TextView pName,sIdTxt,maxPriceTxt,descriptionTxt,uEmail,uId;
    Button calUserBtn,acceptOrderBtn;
    String mUid,mEmail;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    AdapterAuction adapterAuction;
    List<Auction> auctionList;

    private FirebaseAuth mAuth;

    LinearLayout availableBox;

    Product products;
    Auction userDetail;
    String pId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_details);
        Intent intent = getIntent();
        pId = intent.getExtras().getString("pId");
        //set
        img=findViewById(R.id.img);
        pName=findViewById(R.id.pName);
        sIdTxt=findViewById(R.id.sIdTxt);
        maxPriceTxt=findViewById(R.id.maxPriceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        uEmail=findViewById(R.id.uEmail);
        uId=findViewById(R.id.uId);
        calUserBtn=findViewById(R.id.calUserBtn);
        acceptOrderBtn=findViewById(R.id.acceptOrderBtn);
        loadBooks();

    }
    private void loadBooks() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Products");
        long timestamp=System.currentTimeMillis();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);
                    if(p.getpId().equals(pId)) {
                        products = p;
                        pName.setText(products.getpName());
                        Picasso.get().load(products.getpImageUrl()).into(img);
                        sIdTxt.setText("SID : "+products.getsId());
                        maxPriceTxt.setText("Max Price : $"+products.getMaxPrice());
                        descriptionTxt.setText("Description : "+products.getpDesc());
                        if (timestamp>products.getStartTime()&&timestamp<products.getEndTime()){
                            //seller not dong anything
                            acceptOrderBtn.setText("Auction Is Still Going");
                        }else{
                           //seller can accept order
                            acceptOrderBtn.setText("Accept Order");
                            loadMaxUser();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMaxUser() {
        Query ref = FirebaseDatabase.getInstance().getReference("Auctions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Auction auction = ds.getValue(Auction.class);
                    if(pId.equals(auction.getpId()) && products.getMaxPrice().equals(auction.getPrice())){
                        userDetail=auction;
                        uEmail.setText(userDetail.getuName());
                        uId.setText(userDetail.getuId());
                        acceptOrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Placed Order
                                OrderPlaced orderPlaced=new OrderPlaced(pId,products.getsId(),userDetail.getuId(),products.getMaxPrice(),userDetail.getuName(),products.getStartTime()+"",products.getEndTime()+"",products.getpName(),products.getpDesc(),products.getpImageUrl());
                                Query fquery= FirebaseDatabase.getInstance().getReference("Products").orderByChild("pId").equalTo(products.getpId());
                                fquery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            ds.getRef().removeValue();
                                        }
                                        FirebaseDatabase.getInstance().getReference("OrderPlaced").child(products.getpId()).setValue(orderPlaced);
                                        Toast.makeText(SellerProductDetails.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SellerProductDetails.this,SellerDashBoard.class));
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}