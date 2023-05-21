package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.chatapp.Model.Users;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class statusadapter extends RecyclerView.Adapter<statusadapter.ViewHolder> {
    ArrayList<Users> list;
    Context context;
    FirebaseDatabase database;
    FirebaseAuth auth;
    SwipeRefreshLayout swipeRefreshLayout;

    public statusadapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public statusadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragstatus_ok,parent,false);
        ViewHolder v=new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
               Users users=list.get(position);
                holder.statusname.setText(users.getUsername()+" has sent a friend request");
                holder.requestid.setText(users.getUserid());
                database=FirebaseDatabase.getInstance();
                auth=FirebaseAuth.getInstance();






        database.getReference().child("Users").child(users.getUserid()).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.getValue(String.class);
                users.setProfilepicture(name);
                Picasso.get().load(name).placeholder(R.drawable.img).into(holder.reqimg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        HashMap<String, Object> obj2=new HashMap<>();

        obj2.put("req",users.getUserid());

        HashMap<String, Object> obj3=new HashMap<>();

        obj3.put("req",auth.getUid());
                holder.positve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.getReference().child("statusid").child(auth.getUid()).push().setValue(obj2);
                        database.getReference().child("Users").child(auth.getUid()).child("grant").push().updateChildren(obj2);
                        database.getReference().child("Users").child(users.getUserid()).child("grant").push().updateChildren(obj3);
                        holder.constraintLayout.setVisibility(View.GONE);


                    }
                });

        holder.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Users").child(auth.getUid()).child("receiver").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.exists()){
                                String name;
                                name=(snapshot1.child("receiverequest").getValue().toString());

                               if(name.equals(users.getUserid())){

                                       snapshot1.getRef().removeValue();


                               }


                            }
                        }
                     notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        database.getReference().child("addbio").push().setValue("Error");

                    }
                });

                holder.constraintLayout.setVisibility(View.GONE);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView requestid;
        CircleImageView reqimg;
        TextView statusname;
        ConstraintLayout constraintLayout;
        AppCompatButton positve,negative;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            requestid=itemView.findViewById(R.id.statusrequestid);
            positve=itemView.findViewById(R.id.positive);
            negative=itemView.findViewById(R.id.negative);
            statusname=itemView.findViewById(R.id.statusname);
            reqimg=itemView.findViewById(R.id.statusimg1);
            constraintLayout=itemView.findViewById(R.id.statusmainbackground);

        }
    }
}
