package com.example.auctionapp.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.auctionapp.MainActivity;
import com.example.auctionapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddProducts extends AppCompatActivity {
    EditText autionTime,addProduct_miniPrice,addProduct_desc,addProduct_pName;
    Spinner spinner;
    String SelectedType="";
    String vendorId,vendorPhone;
    String mUid,startTime,startDate,endTime,endDate;
    private FirebaseAuth mAuth;

    Button addBook_selectImageBtn,add_productBtn;
    ImageView selected_img;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //image picked will be saved in this
    Uri image_rui=null;

    //permission constants
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;


    //permission constants
    private static final int IMAGE_PICK_CAMERA_CODE =300;
    private static final int IMAGE_PICK_GALLERY_CODE=400;

    //permission array
    String[] cameraPermessions;
    String[] storagePermessions;

    //progresses bar
    ProgressDialog pd;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
//            getData();
        }else{
            startActivity(new Intent(AddProducts.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        autionTime=findViewById(R.id.autionTime);
        addProduct_miniPrice=findViewById(R.id.addProduct_miniPrice);
        addProduct_desc=findViewById(R.id.addProduct_desc);
        addProduct_pName=findViewById(R.id.addProduct_pName);
        selected_img=findViewById(R.id.selected_img);
        add_productBtn=findViewById(R.id.add_productBtn);
        pd= new ProgressDialog(this);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Products");

        //init permissions
        cameraPermessions=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermessions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //image
        selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        add_productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });


    }
    private void checkValidation() {
        String Name,Desc,MinPrice,Minute;
        Name=addProduct_pName.getText().toString();
        Desc=addProduct_desc.getText().toString();
        MinPrice=addProduct_miniPrice.getText().toString();
        Minute=autionTime.getText().toString();
        if(Name.isEmpty()){
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        }
        else if(Minute.isEmpty()){
            Toast.makeText(this, "time is empty", Toast.LENGTH_SHORT).show();
        }
        else if(Desc.isEmpty()){
            Toast.makeText(this, "Description is empty", Toast.LENGTH_SHORT).show();
        }else if(MinPrice.isEmpty()){
            Toast.makeText(this, "Min Price is empty", Toast.LENGTH_SHORT).show();
        }else if(image_rui==null){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        } else{
            startAddingBook(Name,Desc,MinPrice,Minute);
        }
    }


    private void startAddingBook(String name, String desc, String minPrice, String minute) {
        pd.setMessage("publishing post...");
        pd.setCancelable(false);
        pd.show();
        final String timestamp= String.valueOf(System.currentTimeMillis());
        String filePathName="Posts/"+"post_"+timestamp;


        Bitmap bitmap=((BitmapDrawable)selected_img.getDrawable()).getBitmap();

        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        //image compress
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bout);
        byte[] data=bout.toByteArray();

        StorageReference ref= FirebaseStorage.getInstance().getReference().child(filePathName);


        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());

                String downloadUri=uriTask.getResult().toString();

                if(uriTask.isSuccessful()){
                    //uri is received upload post to firebase database
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Products");

                    String bId=ref.push().getKey();
                    Map<String, Object> hashMap=new HashMap<>();
                    //put info
                    long add=Long.parseLong(minute)*60000;
                    long timeend=System.currentTimeMillis()+add;
                    hashMap.put("pName",name);
                    hashMap.put("startTime", ServerValue.TIMESTAMP);
                    hashMap.put("endTime",timeend);
                    hashMap.put("pId",bId);
                    hashMap.put("sId",mUid);
                    hashMap.put("vendorNo",vendorPhone);
                    hashMap.put("pDesc",desc);
                    hashMap.put("minPrice",minPrice);
                    hashMap.put("report","0");
                    hashMap.put("pImageUrl",downloadUri);
                    hashMap.put("maxPrice",minPrice);

                    ref.child(bId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            pd.dismiss();
                            Toast.makeText(AddProducts.this, "Product Uploaded", Toast.LENGTH_SHORT).show();

                            //reset view
                            selected_img.setImageURI(null);
                            image_rui=null;

                            startActivity(new Intent(AddProducts.this,SellerDashBoard.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(AddProducts.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProducts.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void showImageDialog() {


        String[] options={"Camera","Gallery"};

        //dialog box
        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(AddProducts.this);

        builder.setTitle("Choose Action");



        Toast.makeText(this, " reached", Toast.LENGTH_SHORT).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    //camera clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if(which==1){
                    //camera clicked

                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }


    private void pickFromCamera() {

        ContentValues cv=new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_rui=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermessions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result&&result1;
    }


    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermessions,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{

                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&&storageAccepted){

                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "camera  & gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>1){
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_CODE){
                image_rui=data.getData();

                selected_img.setImageURI(image_rui);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE){

                selected_img.setImageURI(image_rui);

            }
        }
    }

}