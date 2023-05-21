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

import com.example.chatapp.databinding.ActivitySighninactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sighninactivity extends AppCompatActivity {
  ActivitySighninactivityBinding binding;
  ProgressDialog progressDialog;
  FirebaseAuth  auth;

   int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighninactivity);

        binding=ActivitySighninactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(Sighninactivity.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Log in to your account");
        binding.passoward.getBackground().mutate().setColorFilter(getResources().getColor(R.color.bottomLine), PorterDuff.Mode.SRC_ATOP);
        binding.email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.bottomLine), PorterDuff.Mode.SRC_ATOP);
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


        binding.sighnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(binding.email.getText().toString().equals("")||binding.passoward.getText().toString().equals("")){
                       if(binding.email.getText().toString().equals("")){
                           binding.email.setError("enter your email");
                       }
                       else if(binding.passoward.getText().toString().equals("")){
                           binding.passoward.setError("enter your passoward");

                       }
                       Toast.makeText(Sighninactivity.this,"Please Fill up the text",Toast.LENGTH_LONG).show();
                   }

              else {
                       auth.signInWithEmailAndPassword(binding.email.getText().toString(), binding.passoward.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressDialog.dismiss();
                               if (task.isSuccessful()) {

                                   Intent intent = new Intent(Sighninactivity.this, MainActivity.class);


                                   startActivity(intent);

                               } else {
                                   Toast.makeText(Sighninactivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }
            }
        });

        binding.clicksighnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(Sighninactivity.this,sighnup.class);
                startActivity(intent);
            }
        });

        if(auth.getCurrentUser()!=null){
            Intent  intent=new Intent(Sighninactivity.this,MainActivity.class);
            startActivity(intent);
        }
    }



}