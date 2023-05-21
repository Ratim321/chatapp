package com.example.chatapp.Adapter;



import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatDetailActivity;
import com.example.chatapp.Model.Users;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
   ArrayList<Users> list;
   Context context;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    public UserAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
               Users users=list.get(position);
               FirebaseDatabase database=FirebaseDatabase.getInstance();





        database.getReference().child("Users").child(users.getUserid()).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.getValue(String.class);
                users.setProfilepicture(name);
                Picasso.get().load(name).placeholder(R.drawable.img).into(holder.image);
             
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




































//i have changed last messege can be error because truned singlelistner to value listner
        holder.username.setText(users.getUsername());

        FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid()+users.getUserid())
                        .orderByChild("timestamp").limitToLast(1)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChildren()){
                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                                        holder.lastmessege.setText(snapshot1.child("messege").getValue().toString());
                                        holder.messegeTime.setText(snapshot1.child("timetable").getValue().toString());


                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userid",users.getUserid());
                intent.putExtra("profilePic",users.getProfilepicture());
                intent.putExtra("username",users.getUsername());

                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       ImageView image;
       TextView username,lastmessege,messegeTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          image=itemView.findViewById(R.id.profile_image);
          username=itemView.findViewById(R.id.usernamelist);
          lastmessege=itemView.findViewById(R.id.lastmessege);
          messegeTime=itemView.findViewById(R.id.messegetime);
        }
    }


}
