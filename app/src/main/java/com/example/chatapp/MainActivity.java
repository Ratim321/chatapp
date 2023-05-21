package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chatapp.Adapter.ChatAdpater;
import com.example.chatapp.Adapter.Fragmentadapter;
import com.example.chatapp.Adapter.Searchadapter;
import com.example.chatapp.Adapter.Topstatusadapter;
import com.example.chatapp.Model.Users;
import com.example.chatapp.Model.Userstatus;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    RecyclerView listView;
    FirebaseDatabase database;
    ArrayList<Users> list=new ArrayList<>();

    Topstatusadapter topstatusadapter;
    ArrayList<Users> userstatuseslist;
    String[] name={"ratim","shariar"};

    final ArrayList<Users> searches=new ArrayList<>();

    final Searchadapter searchadapter=new Searchadapter(searches,this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        userstatuseslist=new ArrayList<>();


        ArrayList<String> statususerId=new ArrayList<>();
        ArrayList<String> statuspictureurl=new ArrayList<>();


        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        topstatusadapter=new Topstatusadapter(this,userstatuseslist);
        binding.whatsappuserstatus.setAdapter(topstatusadapter);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        binding.whatsappuserstatus.setLayoutManager(layoutManager1);
        binding.viewpager.setAdapter(new Fragmentadapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);
        listView=findViewById(R.id.listview2);
           ArrayList<String> storyidlist=new ArrayList<>();



           database.getReference().child("Story").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   storyidlist.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()) {




                                    if (snapshot1.exists()) {
                                        int flag = 0;
                                        String name = snapshot1.child("id").getValue().toString();
                                        for (int i = 0; i < storyidlist.size(); i++) {
                                            if (name.equals(storyidlist.get(i))) {
                                                flag = 1;

                                            }

                                        }
                                        if (flag == 0) {

                                            storyidlist.add(snapshot1.child("id").getValue().toString());
                                        }


                                    }
                                }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });






















//        database.getReference().child("Story").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                   userstatuseslist.clear();
//                   if(snapshot.exists()) {
//                       for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                           if (snapshot1.exists()) {
//
//                               Users users = snapshot1.getValue(Users.class);
//
//
//                               users.setUserid(snapshot1.getKey());
//
//
//                            statususerId.add(snapshot1.child("id").getValue().toString());
//                            statuspictureurl.add(snapshot1.child("storyimg").getValue().toString());
//                   users.setStoryimgid(snapshot1.child("id").getValue().toString());
//                   users.setStoryimg(snapshot1.child("storyimg").getValue().toString());
//
//                   //users.setUsername(snapshot1.child("name").getValue().toString());
//                   users.setTimestamp(snapshot1.child("timestamp").getValue().toString());
////                   users.setMainidimage(snapshot1.child("mainidimage").getValue().toString());
//
//
//
//                               userstatuseslist.add(users);
//
//
//                           }
//                       }
//                   }
//
//                  topstatusadapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                database.getReference().child("addbio").push().setValue("Error");
//
//            }.................will be work laterstorylist.....................

//        });





        database.getReference().child("Story").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.exists()) {

                            Users users = snapshot1.getValue(Users.class);


                            users.setUserid(snapshot1.getKey());


                            statususerId.add(snapshot1.child("id").getValue().toString());
                            statuspictureurl.add(snapshot1.child("storyimg").getValue().toString());
                            users.setStoryimgid(snapshot1.child("id").getValue().toString());
                            users.setStoryimg(snapshot1.child("storyimg").getValue().toString());

//                            users.setUsername(snapshot1.child("name").getValue().toString());
                            users.setTimestamp(snapshot1.child("timestamp").getValue().toString());
//                   users.setMainidimage(snapshot1.child("mainidimage").getValue().toString());
                            final boolean[] go = {true};
                      database.getReference().child("Users").child(auth.getUid()).child("grant").addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot8) {
                              userstatuseslist.clear();
                                        if(snapshot8.exists()) {
                                            for(DataSnapshot snapshot2:snapshot8.getChildren()) {
                                               String name3=snapshot2.child("req").getValue().toString();
                                               Toast.makeText(MainActivity.this,name3+"name",Toast.LENGTH_LONG).show();
                                                int j=0;
                                                Toast.makeText(MainActivity.this,auth.getUid()+"selfid",Toast.LENGTH_LONG).show();
                                                Toast.makeText(MainActivity.this,storyidlist.get(0)+"storyid",Toast.LENGTH_LONG).show();


                                                for(int i=0;i<storyidlist.size();i++){
                                                             if(name3.equals(storyidlist.get(i))){
                                                                 Toast.makeText(MainActivity.this,"BAD CAlloc",Toast.LENGTH_LONG).show();
                                                                 userstatuseslist.add(users);


                                                             }

                                                    topstatusadapter.notifyDataSetChanged();

                                                }
                                            }
                                        }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {

                          }
                      });





                        }
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                database.getReference().child("addbio").push().setValue("Error");

            }
        });





















































        Users dmmy=new Users();
        list.add(dmmy);




        listView.setAdapter(searchadapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        listView.setLayoutManager(layoutManager);






        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searches.clear();
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserid(dataSnapshot.getKey());
                    if(auth.getUid()!=users.getUserid()) {
                        list.add(users);
                    }




                }
                searchadapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


      database.getReference().child("Users").child(auth.getUid()).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if(snapshot.exists()){
                         String name=snapshot.getValue(String.class);
                         Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
                     }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
























//        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
//
//                    Users users = dataSnapshot.getValue(Users.class);
//
//
//                    users.setUserid(dataSnapshot.getKey());
//
//
//
//
//
//                    list.add();
//
//
//
//
//
//                }
//
//            }

















    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                listView.setVisibility(View.GONE);
                return true;
            }
        });

        SearchView searchView= (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search here");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                listView.setVisibility(View.VISIBLE);

                filter(newText);
                return true;
            }

            private void filter(String newText) {

                ArrayList<Users> filterresult = new ArrayList<>();

                filterresult.clear();
                for(Users item:list){

               if(item.getUsername().toLowerCase().contains(newText.toLowerCase())) {

                   filterresult.add(item);
               }

                }
                searchadapter.filterlist(filterresult);
            }
        });
        return  super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case  R.id.settings:
                Intent i=new Intent(MainActivity.this,Settringsactivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,Sighninactivity.class);
                startActivity(intent);
                  break;
            case R.id.groupechat:

                Intent intent1=new Intent(MainActivity.this,Groupechatactivity.class);
                startActivity(intent1);
                break;



        }

        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        String currentid=FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentid).setValue("Online");
        String secondcurrentid=FirebaseAuth.getInstance().getUid();
        database.getReference().child("anotherpresence").child(secondcurrentid).setValue("Online");
    }
//
//    @Override
//    protected void onStop() {
//        String currentid=FirebaseAuth.getInstance().getUid();
//        database.getReference().child("presence").child(currentid).setValue("Offline");
//        super.onStop();
//
//    }

    @Override
    protected void onPause() {
        String currentid=FirebaseAuth.getInstance().getUid();
        if(currentid!=null) {
            database.getReference().child("presence").child(currentid).setValue("Offline");
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        listView.setVisibility(View.GONE);
    }
}