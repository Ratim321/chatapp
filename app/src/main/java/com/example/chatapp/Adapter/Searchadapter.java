package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MainActivity;
import com.example.chatapp.Model.Users;
import com.example.chatapp.R;
import com.example.chatapp.Settringsactivity;
import com.example.chatapp.search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Searchadapter extends RecyclerView.Adapter<Searchadapter.ViewHolder> {
    ArrayList<Users> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
   FirebaseAuth auth;

    public Searchadapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.text,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users=list.get(position);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Users").child(users.getUserid()).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.getValue(String.class);
                users.setProfilepicture(name);
                Picasso.get().load(name).placeholder(R.drawable.img).into(holder.img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        holder.name.setText(users.getUsername());
        holder.id.setText(users.getUserid());
        firebaseDatabase=FirebaseDatabase.getInstance();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth=FirebaseAuth.getInstance();
                users.setFreind(users.getUserid());


                HashMap<String, Object> obj=new HashMap<>();

                obj.put("fr",users.getFreind());

                HashMap<String, Object> obj1=new HashMap<>();

                obj1.put("receiverequest",auth.getUid());

//            firebaseDatabase.getReference().child("Users").child(users.getUserid()).updateChildren(obj);
              firebaseDatabase.getReference().child("Users").child(auth.getUid()).child("request").push().updateChildren(obj);

                    Toast.makeText(v.getContext(),"Freind request sent",Toast.LENGTH_LONG).show();
                firebaseDatabase.getReference().child("Users").child(users.getUserid()).child("receiver").push().updateChildren(obj1);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


      public void filterlist(ArrayList<Users> filteredlist){
                 list=filteredlist;
                 notifyDataSetChanged();
      }
    public class ViewHolder extends RecyclerView.ViewHolder{

         TextView name,id;
         CircleImageView img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text_view);
            id=itemView.findViewById(R.id.text_id);
            img=itemView.findViewById(R.id.userimgsearch);
        }
    }
}
