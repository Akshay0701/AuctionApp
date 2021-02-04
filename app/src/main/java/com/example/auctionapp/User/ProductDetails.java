package com.example.auctionapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Adapter.AdapterAuction;
import com.example.auctionapp.Adapter.AdapterProduct;
import com.example.auctionapp.MainActivity;
import com.example.auctionapp.Models.Auction;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductDetails extends AppCompatActivity {
    ImageView img;
    TextView pName,sIdTxt,maxPriceTxt,descriptionTxt,auctionTxt;
    EditText priceEdt;
    Button sendBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    AdapterAuction adapterAuction;
    List<Auction> auctionList;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    LinearLayout availableBox;

    Product products;
    String pId;

    //Time Remaining
    long time =0;
    TextView _tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        checkforuserlogin();
        Intent intent = getIntent();
        pId = intent.getExtras().getString("pId");
        //set
        _tv = (TextView) findViewById( R.id.timerTxt);
        pName=findViewById(R.id.pName);
        sIdTxt=findViewById(R.id.sIdTxt);
        maxPriceTxt=findViewById(R.id.maxPriceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        auctionTxt=findViewById(R.id.auctionTxt);
        priceEdt=findViewById(R.id.priceEdt);
        img=findViewById(R.id.img);
        sendBtn=findViewById(R.id.sendBtn);
        loadBooks();
        loadAuctions();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price=priceEdt.getText().toString();
                if(!price.isEmpty()){
                    Auction auction=new Auction(mUid,mEmail,price,products.getpId(),products.getsId());
                    FirebaseDatabase.getInstance().getReference("Auctions").child(mUid+products.getpId()).setValue(auction);
                    Toast.makeText(ProductDetails.this, "Price Updated", Toast.LENGTH_SHORT).show();
                    if(Integer.parseInt(products.getMaxPrice())<Integer.parseInt(price)){
                        products.setMaxPrice(price);
                        FirebaseDatabase.getInstance().getReference("Products").child(products.getpId()).setValue(products);
                        Toast.makeText(ProductDetails.this, "Your Price Is On Top", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void loadAuctions() {
        auctionList = new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Auctions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                auctionList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Auction auction = ds.getValue(Auction.class);
                    if(pId.equals(auction.getpId())){
                        auctionList.add(auction);
                    }
                }
                if(auctionList.size()>=10){
                    auctionTxt.setVisibility(View.VISIBLE);
                    auctionTxt.setText("List Is Full");
                    priceEdt.setVisibility(View.GONE);
                    sendBtn.setVisibility(View.GONE);
                }
                adapterAuction=new AdapterAuction(ProductDetails.this,auctionList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapterAuction);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                            time= Math.abs(products.getEndTime()-timestamp);
                            Toast.makeText(ProductDetails.this, ""+time, Toast.LENGTH_SHORT).show();
                            auctionTxt.setVisibility(View.GONE);
                            priceEdt.setVisibility(View.VISIBLE);
                            sendBtn.setVisibility(View.VISIBLE);
                            new CountDownTimer(time, 1000) { // adjust the milli seconds here

                                public void onTick(long millisUntilFinished) {
                                    _tv.setText("Time Remain : "+String.format("%d min, %d sec",
                                            TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                }
                                public void onFinish() {
                                    _tv.setText("Times Up!");
                                    loadBooks();
                                    loadAuctions();
                                }
                            }.start();
                        }else{
                            auctionTxt.setVisibility(View.VISIBLE);
                            priceEdt.setVisibility(View.GONE);
                            sendBtn.setVisibility(View.GONE);
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
    public void checkforuserlogin() {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //  token=FirebaseInstanceId.getInstance().getToken();
            mUid = user.getUid();
            mEmail=user.getEmail();
        }else{
            startActivity(new Intent(ProductDetails.this, MainActivity.class));
        }
    }

}