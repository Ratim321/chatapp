package com.example.chatapp.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.ChatDetailActivity;
import com.example.chatapp.MainActivity;
import com.example.chatapp.Model.Users;
import com.example.chatapp.R;
import com.example.chatapp.Settringsactivity;
import com.example.chatapp.Sighninactivity;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.example.chatapp.databinding.FragmentChatfragmentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;


public class chatfragment extends Fragment {



    public chatfragment() {
        // Required empty public constructor
    }
    FragmentChatfragmentBinding binding;




    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    Users users1 = new Users();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase=FirebaseDatabase.getInstance("https://chatapp-b42a8-default-rtdb.asia-southeast1.firebasedatabase.app/");
        binding=FragmentChatfragmentBinding.inflate(inflater, container, false);
        FirebaseAuth auth;
        auth=FirebaseAuth.getInstance();
        UserAdapter adapter=new UserAdapter(list,getContext());
        binding.charrecylerview.setAdapter(adapter);
       ArrayList<String> numberoffreind=new ArrayList<>();


        LinearLayoutManager LayoutManager=new LinearLayoutManager(getContext());
        binding.charrecylerview.setLayoutManager(LayoutManager);
        binding.charrecylerview.showShimmerAdapter();
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                HashMap<String ,Object> map=new HashMap<>();
                map.put("token",s);
                firebaseDatabase.getReference().child("Users").child(auth.getUid()).updateChildren(map);

            }
        });




//
//        firebaseDatabase.getReference().child("Users").child(auth.getUid()).child("request").addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                             for(DataSnapshot snapshot1:snapshot.getChildren()){
//                                  if(snapshot1.exists()){
//                                       numberoffreind.add(snapshot1.child("fr").getValue().toString());
//
//                                  }
//                             }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                firebaseDatabase.getReference().child("addbio").push().setValue("Error");
//
//            }
//        });






 //proper freind request


//        firebaseDatabase.getReference().child("Users").child(auth.getUid()).child("grant").addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot snapshot1:snapshot.getChildren()){
//                    if(snapshot1.exists()){
//
//
//
//                        numberoffreind.add(snapshot1.child("req").getValue().toString());
//
//                    }
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                firebaseDatabase.getReference().child("addbio").push().setValue("Error");
//
//            }
//        }); previous repeted value arraylist





        firebaseDatabase.getReference().child("Users").child(auth.getUid()).child("grant").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    if(snapshot1.exists()){





                        int flag=0;
                        String name=snapshot1.child("req").getValue().toString();
                        for(int i=0;i<numberoffreind.size();i++){
                            if(name.equals(numberoffreind.get(i))){
                                flag=1;
                            }

                        }
                        if(flag==0) {
                            numberoffreind.add(snapshot1.child("req").getValue().toString());
                        }

                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                firebaseDatabase.getReference().child("addbio").push().setValue("Error");

            }
        });


















          users1.setNumberoffreind(numberoffreind);













//        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 list.clear();
//                 for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
//
//                     Users users = dataSnapshot.getValue(Users.class);
//
//
//                     users.setUserid(dataSnapshot.getKey());
//
//                  if(users.getFreind()!=null) {
//
//                      if (users.getFreind().equals("freind")) {
//                          list.add(users);
//                      }
//                  }
//
//
//
//
//
//
//
//
//                 }
//                 adapter.notifyDataSetChanged();
//             }
//
//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//
//             }
//         });

        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);


                    users.setUserid(dataSnapshot.getKey());

                    if(users1.getNumberoffreind()!=null) {

                        for(int j=0;j<numberoffreind.size();j++){
                            if(users1.getNumberoffreind().get(j).equals(users.getUserid())){
                                list.add(users);
                            }
                        }


                    }







                }
                binding.charrecylerview.hideShimmerAdapter();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

























        return binding.getRoot();
    }


}