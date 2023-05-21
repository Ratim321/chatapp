package com.example.chatapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MainActivity;
import com.example.chatapp.Model.Normalstatus;
import com.example.chatapp.Model.Users;
import com.example.chatapp.Model.Userstatus;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemStatusBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class Topstatusadapter extends RecyclerView.Adapter<Topstatusadapter.TopstatusViewHolder> {
 Context context;
 FirebaseDatabase database;
 FirebaseAuth auth;
    public Topstatusadapter(Context context, ArrayList<Users> userstatuses) {
        this.context = context;
        this.userstatuses = userstatuses;
    }

    ArrayList<Users> userstatuses;
    @NonNull
    @Override
    public TopstatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new TopstatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopstatusViewHolder holder, int position) {
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        Users users=userstatuses.get(position);
        database.getReference().child("Story").child(auth.getUid()).child("storyimg").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Picasso.get().load(users.getStoryimg()).placeholder(R.drawable.img).into(holder.statusimageview);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("Testingurl").push().setValue(users.getStoryimg());
                holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<MyStory> myStories = new ArrayList<>();

                                  myStories.add(new MyStory(users.getStoryimg()));


                        new StoryView.Builder(((MainActivity)context).getSupportFragmentManager())
                                .setStoriesList(myStories) // Required
                                .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                                .setTitleText("ratim") // Default is Hidden
                                .setSubtitleText(users.getTimestamp()) // Default is Hidden
                                .setTitleLogoUrl("NUll") // Default is Hidden
                                .setStoryClickListeners(new StoryClickListeners() {
                                    @Override
                                    public void onDescriptionClickListener(int position) {
                                        //your action
                                    }

                                    @Override
                                    public void onTitleIconClickListener(int position) {
                                        //your action
                                    }
                                }) // Optional Listeners
                                .build() // Must be called before calling show method
                                .show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return userstatuses.size();
    }

    public class TopstatusViewHolder extends RecyclerView.ViewHolder{
        ItemStatusBinding binding;
        CircleImageView statusimageview;

        public TopstatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemStatusBinding.bind(itemView);
            statusimageview=itemView.findViewById(R.id.statusimgviwer2);
        }
    }
}
