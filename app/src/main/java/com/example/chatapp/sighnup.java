package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.Model.Users;
import com.example.chatapp.databinding.ActivitySighnupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class sighnup extends AppCompatActivity {
    private FirebaseAuth mAuth;
  ActivitySighnupBinding binding;

  FirebaseDatabase database;
  ProgressDialog progressDialog;
  int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySighnupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance("https://transaction-75f14-default-rtdb.firebaseio.com/");
        progressDialog=new ProgressDialog(sighnup.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");
        binding.passoward.getBackground().mutate().setColorFilter(getResources().getColor(R.color.bottomLine), PorterDuff.Mode.SRC_ATOP);
        binding.email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.bottomLine), PorterDuff.Mode.SRC_ATOP);
        binding.username.getBackground().mutate().setColorFilter(getResources().getColor(R.color.bottomLine), PorterDuff.Mode.SRC_ATOP);
        binding.hidepass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(i==0) {
                    binding.passoward.setTransformationMethod(null);
                    i++;
                }else if(i==1){
                    binding.passoward.setTransformationMethod(new PasswordTransformationMethod());
                    i=0;
                }


            }
        });






        binding.sighnup.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if(binding.email.getText().toString().equals("")||binding.passoward.getText().toString().equals("")||binding.username.getText().toString().equals("")||(binding.passoward.getText().toString().matches("[0-9]+"))||binding.passoward.getText().toString().length()<=7){
                    if(binding.email.getText().toString().equals("")){
                        binding.email.setBackgroundResource(android.R.color.holo_red_light);
                        binding.passoward.setBackgroundResource(R.color.white);
                        binding.username.setBackgroundResource(android.R.color.white);
                    }
                   if(binding.passoward.getText().toString().equals("")){
                        binding.passoward.setBackgroundResource(android.R.color.holo_red_light);
                        binding.email.setBackgroundResource(R.color.white);
                        binding.username.setBackgroundResource(android.R.color.white);
                    }
                    if(binding.passoward.getText().toString().length()<=7){
                        binding.passoward.setError("Please use Above 5 chacter");
                    }
                     if(binding.passoward.getText().toString().equals("")||(binding.passoward.getText().toString().matches("[0-9]+"))){
                         binding.passoward.setError("Please use character and numeric");
                    }
                    Toast.makeText(sighnup.this,"Please Fill up the text",Toast.LENGTH_LONG).show();
                }




               else {
                    progressDialog.show();
                    try {


                        mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(), binding.passoward.
                                        getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.dismiss();

                                        if (task.isSuccessful()) {
                                            Users users = new Users(binding.username.getText().toString(), binding.email.
                                            getText().toString(), binding.passoward.getText().toString());
                                            String id = task.getResult().getUser().getUid();

                                            database.getReference().child("Users").child(id).setValue(users);





                                            Toast.makeText(sighnup.this, "success", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(sighnup.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(sighnup.this, (CharSequence) task.getException(), Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }

            }
        });

        binding.alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sighnup.this,Sighninactivity.class);
                startActivity(intent);
            }
        });



    }
}