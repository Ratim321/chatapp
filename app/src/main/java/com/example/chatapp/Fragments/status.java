package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.statusadapter;
import com.example.chatapp.Model.Users;
import com.example.chatapp.R;
import com.example.chatapp.databinding.FragmentStatusBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class status extends Fragment {

   public status(){

  }
    FragmentStatusBinding binding;
   ArrayList<Users> list=new ArrayList<>();
   FirebaseDatabase database;
   SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         binding=FragmentStatusBinding.inflate(inflater,container,false);
         database=FirebaseDatabase.getInstance();
        FirebaseAuth auth;
        auth=FirebaseAuth.getInstance();
        ArrayList<String> numofreceiverequest=new ArrayList<>();
        statusadapter adapter=new statusadapter(list,getContext());
        binding.ndfragstatusrecylerview.setAdapter(adapter);



        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.ndfragstatusrecylerview.setLayoutManager(layoutManager);


       binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {

               list.clear();
            binding.swiperefresh.setRefreshing(false);
           }
       });

        binding.swiperefresh.setRefreshing(false);

//        database.getReference().child("Users").child(auth.getUid()).child("receiver").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot snapshot1:snapshot.getChildren()){
//                    if(snapshot1.exists()){
//                        numofreceiverequest.add(snapshot1.child("receiverequest").getValue().toString());
//
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
//                database.getReference().child("addbio").push().setValue("Error");
//
//            }
//        });







        database.getReference().child("Users").child(auth.getUid()).child("receiver").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    if(snapshot1.exists()){
                        int flag=0;
                        String name=snapshot1.child("receiverequest").getValue().toString();
                        for(int i=0;i<numofreceiverequest.size();i++){
                             if(name.equals(numofreceiverequest.get(i))){
                                  flag=1;
                             }

                        }
                        if(flag==0) {
                            numofreceiverequest.add(snapshot1.child("receiverequest").getValue().toString());
                        }


                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                database.getReference().child("addbio").push().setValue("Error");

            }
        });

















        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserid(dataSnapshot.getKey());


                for(int i=0;i<numofreceiverequest.size();i++) {
                    if(numofreceiverequest.get(i).equals(users.getUserid())) {


                        list.add(users);
                    }

                    adapter.notifyDataSetChanged();
                }








                }
                   adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return binding.getRoot();
    }
}