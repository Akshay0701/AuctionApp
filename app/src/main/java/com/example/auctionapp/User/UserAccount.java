package com.example.auctionapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.Models.Seller;
import com.example.auctionapp.Models.User;
import com.example.auctionapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity {

    EditText editText_name,editText_area,editText_phone,editText_email,editText_password;
    Button button_update;
    TextView txt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,refrenceId;
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
        }else{
            startActivity(new Intent(UserAccount.this, UserRegister.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Intent intent = getIntent();
        refrenceId = intent.getExtras().getString("refrenceId");
        txt=findViewById(R.id.txt);
        editText_name=findViewById(R.id.editText_name);
        editText_area=findViewById(R.id.editText_area);
        editText_phone=findViewById(R.id.editText_phone);
        editText_email=findViewById(R.id.editText_email);
        editText_password=findViewById(R.id.editText_password);
        button_update=findViewById(R.id.button_update);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(refrenceId);

        getData();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValid();
            }
        });

    }
    private void checkValid() {
        String saddress,scountry,semail,spassword,sphoneNo,suName;
        semail= editText_email.getText().toString();
        suName= editText_name.getText().toString();
        spassword= editText_password.getText().toString();
        sphoneNo= editText_phone.getText().toString();
        saddress= editText_area.getText().toString();
        scountry="India";
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            editText_email.setError("Invalided Email");
            editText_email.setFocusable(true);
        }
        else if(spassword.length()<6){
            editText_password.setError("Password length at least 6 characters");
            editText_password.setFocusable(true);
        }
        else if(suName.isEmpty()){
            editText_name.setError("Name is empty");
            editText_name.setFocusable(true);
        }
        else if(sphoneNo.length()<10){
            editText_phone.setError("PhoneNo length at least 10 characters");
            editText_phone.setFocusable(true);
        }
        else if(saddress.length()<4){
            editText_area.setError("RollNo length at least 4 characters");
            editText_area.setFocusable(true);
        }
        else {
            Seller user =new Seller(saddress,scountry,semail,spassword,sphoneNo,mUid,suName);
            FirebaseDatabase.getInstance().getReference("Seller").child(mUid).setValue(user);
            Toast.makeText(UserAccount.this, "Updated Account", Toast.LENGTH_SHORT).show();
        }
    }
    private void getData() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference(refrenceId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(refrenceId.equals("Users")){
                        User user = ds.getValue(User.class);
                        if(user.getuId().equals(mUid)){
                            editText_name.setText(user.getuName());
                            editText_phone.setText(user.getPhoneNo());
                            editText_password.setText(user.getPassword());
                            editText_email.setText(user.getEmail());
                            editText_area.setText(user.getAddress());
                            txt.setText("User Account");
                        }
                    }else{
                        Seller seller = ds.getValue(Seller.class);
                        if(seller.getsId().equals(mUid)){
                            txt.setText("Seller Account");
                            editText_name.setText(seller.getsName());
                            editText_phone.setText(seller.getPhoneNo());
                            editText_password.setText(seller.getPassword());
                            editText_email.setText(seller.getEmail());
                            editText_area.setText(seller.getAddress());
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
}