package com.example.basicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.appsearch.StorageInfo;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    //Intialize Variable

    ImageView imageView;
    Button btopen;
    Button btnupload;

    StorageReference storageReference;

    ProgressDialog prg;

    private static final int CAMERA_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Variable
        imageView = findViewById(R.id.image_view);
        btopen = findViewById(R.id.bt_open);
        //btnupload=findViewById(R.id.btn_upload);

        storageReference= FirebaseStorage.getInstance().getReference();
        prg=new ProgressDialog(this);
        //Request Camera Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, CAMERA_REQUEST_CODE);
        }

        btopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //String selectedImage = null;
        if (requestCode == CAMERA_REQUEST_CODE && resultCode==RESULT_OK) {
            //System.out.println(0);
            //Uri uri=data.getData();
            //Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(captureImage);
//                File f=new File(currentPhotoPath);
//                selectedImage.setImageURI(Uri.fromFile(f));
//                Log.d("tag","Absolute image is "+Uri.fromFile(f));
//
//                Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri=Uri.fromFile(f);
//                mediaScanIntent.setData(contentUri);
//                this.sendBroadcast(mediaScanIntent);
//
//                uploadImagetofirebase(f.getName(),contentUri);
                
                prg.setMessage("Uploading Image");
                prg.show();
//                Uri uri=data.getData();
//                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(captureImage);
//                File f=new File(String.valueOf(captureImage));
//                imageView.setImageURI(Uri.fromFile(f));
                Uri uri=data.getData();
                StorageReference filepath=storageReference.child("Images").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        prg.dismiss();
                        Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

//    protected void uploadImagetofirebase(String name,Uri contentUri){
//        StorageReference image=storageReference.child("images/" + name);
//        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Log.d("TAG","On Success : Uploaded Image Url is " + uri.toString());
//                        }
//                    });
//                Toast.makeText(MainActivity.this, "Image is Uploaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}