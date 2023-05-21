package com.example.chatapp.Adapter;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatDetailActivity;
import com.example.chatapp.Model.MessegeModel;
import com.example.chatapp.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdpater extends RecyclerView.Adapter{
    public ChatAdpater(ArrayList<MessegeModel> messegeModels, Context context) {
        this.messegeModels = messegeModels;
        this.context = context;
    }

    ArrayList<MessegeModel> messegeModels;
  FirebaseDatabase database;

    public ChatAdpater(ArrayList<MessegeModel> messegeModels, Context context, String recId) {
        this.messegeModels = messegeModels;
        this.context = context;
        this.recId = recId;
    }

    Context context;
  String recId;
  int SENDER_VIEW_TYPE=1;
  int RECEIVER_VIEW_TYPE=2;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new senderViewHolder(view);
        }
           else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewholder(view);
        }


    }

    @Override
    public int getItemViewType(int position) {
      if(messegeModels.get(position).getId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;

      }else{
          return RECEIVER_VIEW_TYPE;
      }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {




                     MessegeModel messegeModel=messegeModels.get(position);

                     holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                         @Override
                         public boolean onLongClick(View v) {
                             new AlertDialog.Builder(context).setTitle("Delete")
                                     .setMessage("Are you sure want to delete message?")
                                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                                 FirebaseDatabase database=FirebaseDatabase.getInstance();
                                                 String senderoom=FirebaseAuth.getInstance().getUid()+recId;
                                                 database.getReference().child("chats").child(senderoom).child(messegeModel.getMessegeid())
                                                         .setValue(null);
                                         }
                                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                              dialog.dismiss();
                                         }
                                     }).show();


                             return false;
                         }
                     });









                     if(holder.getClass()==senderViewHolder.class){
                         database=FirebaseDatabase.getInstance();
                         ((senderViewHolder)holder).sendmeesge.setText(messegeModel.getMessege());

                         ((senderViewHolder)holder).sendertime.setText(messegeModel.getTimetable().toString());


                     }else{
                         ((RecieverViewholder)holder).reviemesg.setText(messegeModel.getMessege());


                         ((RecieverViewholder)holder).recviertime.setText(messegeModel.getTimetable().toString());
                     }
    }

    @Override
    public int getItemCount() {
        return messegeModels.size();
    }

    public  class RecieverViewholder extends RecyclerView.ViewHolder{
            TextView reviemesg,recviertime;


        public RecieverViewholder(@NonNull View itemView) {
            super(itemView);
            reviemesg=itemView.findViewById(R.id.textViewreciever);
            recviertime=itemView.findViewById(R.id.textViewtimereviver);

        }
    }


    public  class senderViewHolder extends RecyclerView.ViewHolder{
        TextView sendmeesge,sendertime;
        ImageView imageView;
        Button detebutton;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendmeesge=itemView.findViewById(R.id.textViewsender);
            sendertime=itemView.findViewById(R.id.samplesnedettime);
            imageView=itemView.findViewById(R.id.fellings);
            detebutton=itemView.findViewById(R.id.deletebutton);
        }
    }

}
