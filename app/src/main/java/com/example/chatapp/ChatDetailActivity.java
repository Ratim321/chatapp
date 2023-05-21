package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatapp.Adapter.ChatAdpater;
import com.example.chatapp.Model.MessegeModel;
import com.example.chatapp.Model.Users;
import com.example.chatapp.databinding.ActivityChatdetailactivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
  ActivityChatdetailactivityBinding binding;
  FirebaseDatabase database;
  FirebaseAuth auth;
  String secondstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatdetailactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance("https://chatapp-b42a8-default-rtdb.asia-southeast1.firebasedatabase.app/");
        auth=FirebaseAuth.getInstance();

        final String senderid=auth.getUid();


        String recieveid=getIntent().getStringExtra("userid");
        String username=getIntent().getStringExtra("username");
        String profilePic=getIntent().getStringExtra("profilePic");

        database.getReference().child("anotherpresence").child(recieveid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     secondstatus=snapshot.getValue(String.class);
                     Toast.makeText(ChatDetailActivity.this,secondstatus,Toast.LENGTH_LONG).show();
                      if(secondstatus.equals("Online")){
                          binding.currentuserstatus.setText(secondstatus);
                      }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("presence").child(recieveid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(snapshot.exists()){
                          String status=snapshot.getValue(String.class);
//                          if(status.equals("Online")||secondstatus.equals("Online")) {
//                              binding.currentuserstatus.setText("Online");
//                              binding.currentuserstatus.setVisibility(View.VISIBLE);
//                          }
//                          else if(status.equals("typing..")||secondstatus.equals("typing..")) {
//                              binding.currentuserstatus.setText("typing..");
//                              binding.currentuserstatus.setVisibility(View.VISIBLE);
//                          }
//                          else{
//                              binding.currentuserstatus.setText("Offline");
//                              binding.currentuserstatus.setVisibility(View.VISIBLE);
//                          }
                          if(!status.isEmpty()){
                              if(status.equals("Offline")){
                                  binding.currentuserstatus.setText("Offline");
                                  binding.currentuserstatus.setVisibility(View.VISIBLE);
                              }else if(status.equals("typing..")){
                                  binding.currentuserstatus.setText("typing..");
                                  binding.currentuserstatus.setVisibility(View.VISIBLE);
                              }
                              else{
                                  binding.currentuserstatus.setText("Online");
                                  binding.currentuserstatus.setVisibility(View.VISIBLE);
                              }
                          }
                      }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        binding.usernamechatdetailactivity.setText(username);

        Picasso.get().load(profilePic).placeholder(R.drawable.img).into(binding.profileImagechatdetailactivty);

        binding.chatdetailacitivtybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        final Handler handler=new Handler();
       binding.etmessege.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               database.getReference().child("anotherpresence").child(senderid).setValue("typing..");
               database.getReference().child("presence").child(senderid).setValue("typing..");
               handler.removeCallbacksAndMessages(null);
                 handler.postDelayed(userstoptyping,1000);
           }
           Runnable userstoptyping=new Runnable() {
               @Override
               public void run() {
                   database.getReference().child("anotherpresence").child(senderid).setValue("Online");
                   database.getReference().child("presence").child(senderid).setValue("Online");
               }
           };
       });







        final ArrayList<MessegeModel> messegeModels=new ArrayList<>();
        final ChatAdpater chatAdpater=new ChatAdpater(messegeModels,this,recieveid);

        binding.chatrecylerview.setAdapter(chatAdpater);
        LinearLayoutManager layoutlanager=new LinearLayoutManager(this);
        binding.chatrecylerview.setLayoutManager(layoutlanager);
          final String senderRoom=senderid+recieveid;
          final  String receiverRoom=recieveid+senderid;

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messegeModels.clear();
                  for(DataSnapshot snapshot1:snapshot.getChildren()){

                       MessegeModel model=snapshot1.getValue(MessegeModel.class);
                        model.setMessegeid(snapshot1.getKey());
                      Toast.makeText(ChatDetailActivity.this,model.getTimestamp().toString(),Toast.LENGTH_LONG).show();

                       messegeModels.add(model);
                  }
                  chatAdpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
























         binding.sendbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               String messege=  binding.etmessege.getText().toString();

                            Date currentTime=new Date();
                 SimpleDateFormat timeformat=new SimpleDateFormat("h:mm a");


                 final MessegeModel model=new MessegeModel(senderid,messege,new Date().getTime(),timeformat.format(currentTime).toString(),senderRoom,receiverRoom);

                binding.etmessege.setText("");
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                    database.getReference().child("chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }) ;
                            }
                        });
             }
         });


    }

    @Override
    protected void onResume() {

        super.onResume();
        String currentid=FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentid).setValue("Online");

        String secondcurrentid=FirebaseAuth.getInstance().getUid();
        database.getReference().child("anotherpresence").child(secondcurrentid).setValue("Online");
    }

//    @Override
//    protected void onStop() {
//        String currentid1=FirebaseAuth.getInstance().getUid();
//        database.getReference().child("anotherpresence").child(currentid1).setValue("Offline");
//        super.onStop();
//
//    }
    @Override
    protected void onPause() {
        String currentid=FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentid).setValue("Offline");
        super.onPause();
    }


}