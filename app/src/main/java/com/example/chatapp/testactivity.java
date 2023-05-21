package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatapp.Model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class testactivity extends AppCompatActivity {
 Button button;
 FirebaseStorage storage;
 FirebaseDatabase database=FirebaseDatabase.getInstance();
 FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        button=findViewById(R.id.imbutton);
        storage=FirebaseStorage.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");// */*
                    startActivityForResult(intent, 233);
                }catch (Exception e){
                    Toast.makeText(testactivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() !=null) {

                Uri sfile = data.getData();




                final StorageReference reference3 = storage.getReference().child("brand.")
                        .child(FirebaseAuth.getInstance().getUid());





                  reference3.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                              public void onSuccess(Uri uri) {
                                  database.getReference().child("Node").child(FirebaseAuth.getInstance().getUid())
                                          .child("storyimg").setValue(uri.toString());
                                  database.getReference().child("Node").child(FirebaseAuth.getInstance().getUid())
                                          .child("id").setValue(auth.getUid());
                                  Date currentTime=new Date();
                                  SimpleDateFormat timeformat=new SimpleDateFormat("h:mm a");
                                  database.getReference().child("Node").child(FirebaseAuth.getInstance().getUid())
                                          .child("timestamp").setValue(timeformat.format(currentTime));


                                  database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {

                                          for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                              database.getReference().child("Node").child(FirebaseAuth.getInstance().getUid())
                                                      .child("name").setValue(snapshot.child("username").getValue().toString());

                                              database.getReference().child("Node").child(FirebaseAuth.getInstance().getUid())
                                                      .child("mainidimage").setValue(snapshot.child("profilepic").getValue().toString());

                                          }

                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {

                                      }
                                  });


                              }
                          });

                      }
                  });


            }
        }
    }


