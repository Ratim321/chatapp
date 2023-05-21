package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatapp.Adapter.ChatAdpater;
import com.example.chatapp.Model.MessegeModel;
import com.example.chatapp.databinding.ActivityGroupechatactivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Groupechatactivity extends AppCompatActivity {
    ActivityGroupechatactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupechatactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.chatdetailacitivtybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Groupechatactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessegeModel> messegeModels = new ArrayList<>();
        final String senderId = FirebaseAuth.getInstance().getUid();

        final ChatAdpater adpater = new ChatAdpater(messegeModels, this);
        binding.usernamechatdetailactivity.setText("Freinds Chat");
        binding.chatrecylerview.setAdapter(adpater);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecylerview.setLayoutManager(layoutManager);
                       database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {

                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               messegeModels.clear();
                               for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                   MessegeModel model=dataSnapshot.getValue(MessegeModel.class);
                                   messegeModels.add(model);
                               }
                               adpater.notifyDataSetChanged();
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
         binding.sendbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final String messege=binding.etmessege.getText().toString();

                 Date currentTime=new Date();
                 SimpleDateFormat timeformat=new SimpleDateFormat("h:mm a");



                 final MessegeModel model=new MessegeModel(senderId,messege,new Date().getTime(),timeformat.format(currentTime).toString());

                 binding.etmessege.setText("");
                   database.getReference().child("Group Chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {

                       }
                   });

             }
         });

    }
}