package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.Model.Users;
import com.example.chatapp.databinding.ActivitySettringsactivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Settringsactivity extends AppCompatActivity {
    ActivitySettringsactivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettringsactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String name=auth.getUid();

        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settringsactivity.this,"ok",Toast.LENGTH_LONG).show();
                String status=binding.etStatus.getText().toString();
                String username=binding.etUsername.getText().toString();

                HashMap<String, Object> obj=new HashMap<>();
                Toast.makeText(Settringsactivity.this,username,Toast.LENGTH_LONG).show();
                obj.put("username",username);
                obj.put("status",status);
                if(name!=null) {

                    database.getReference().child("Users").child(name).updateChildren(obj);
                }
            }
        });

        if(name!=null) {

            database.getReference().child("Users").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users=snapshot.getValue(Users.class);


                    database.getReference().child("Users").child(name).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name= snapshot.getValue(String.class);
                            users.setProfilepicture(name);
                            Picasso.get().load(users.getProfilepicture()).placeholder(R.drawable.img).into(binding.profileimage);
                            binding.etStatus.setText(users.getStatus());
                            binding.etUsername.setText(users.getUsername());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        binding.settingsbackarraow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settringsactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });




        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");// */*
                startActivityForResult(intent,33);

            }
        });




        binding.storyimgplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");// */*
                startActivityForResult(intent,63);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() !=null){
            if(requestCode==33) {
                Uri sfile = data.getData();
                binding.profileimage.setImageURI(sfile);
                final StorageReference reference = storage.getReference().child("profile_picture")
                        .child(FirebaseAuth.getInstance().getUid());

                reference.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profilepic").setValue(uri.toString());


                                database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                            Users users = dataSnapshot.getValue(Users.class);
                                            users.setUserid(dataSnapshot.getKey());
                                            users.setProfilepicture(uri.toString());




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
            }else{
                Uri rfile = data.getData();
                binding.storyimg.setImageURI(rfile);
                final StorageReference reference1 = storage.getReference().child("story_img")
                        .child(FirebaseAuth.getInstance().getUid());

                reference1.putFile(rfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                database.getReference().child("Story").child(FirebaseAuth.getInstance().getUid())
                                        .child("storyimg").setValue(uri.toString());
                                database.getReference().child("Story").child(FirebaseAuth.getInstance().getUid())
                                        .child("id").setValue(auth.getUid());
                                Date currentTime=new Date();
                                SimpleDateFormat timeformat=new SimpleDateFormat("h:mm a");
                                database.getReference().child("Story").child(FirebaseAuth.getInstance().getUid())
                                        .child("timestamp").setValue(timeformat.format(currentTime));

                                if(auth.getUid()!=null) {

                                    database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Toast.makeText(Settringsactivity.this, "Previous", Toast.LENGTH_LONG).show();

                                            database.getReference().child("Story").child(FirebaseAuth.getInstance().getUid())
                                                    .child("name").setValue(snapshot.child("username").getValue().toString());
//
//                                         database.getReference().child("Story").child(FirebaseAuth.getInstance().getUid())
//                                                 .child("mainidimage").setValue(snapshot.child("profilepic").getValue().toString());





                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }


                                database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                            Users users = dataSnapshot.getValue(Users.class);
                                            users.setUserid(dataSnapshot.getKey());


                                            users.setStoryimg(uri.toString());
                                            users.setStoryimgid(auth.getUid());




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

}