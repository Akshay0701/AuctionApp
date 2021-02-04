package com.example.auctionapp.User.ui.slideshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionapp.MainActivity;
import com.example.auctionapp.Models.Seller;
import com.example.auctionapp.Models.User;
import com.example.auctionapp.R;
import com.example.auctionapp.User.UserRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
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
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        editText_name=root.findViewById(R.id.editText_name);
        editText_area=root.findViewById(R.id.editText_area);
        editText_phone=root.findViewById(R.id.editText_phone);
        editText_email=root.findViewById(R.id.editText_email);
        editText_password=root.findViewById(R.id.editText_password);
        button_update=root.findViewById(R.id.button_update);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        getData();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValid();
            }
        });
        return root;
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
            User user =new User(saddress,scountry,semail,spassword,sphoneNo,mUid,suName);
            FirebaseDatabase.getInstance().getReference("Users").child(mUid).setValue(user);
            Toast.makeText(getContext(), "Updated Account", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if(user.getuId().equals(mUid)){
                            editText_name.setText(user.getuName());
                            editText_phone.setText(user.getPhoneNo());
                            editText_password.setText(user.getPassword());
                            editText_email.setText(user.getEmail());
                            editText_area.setText(user.getAddress());
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