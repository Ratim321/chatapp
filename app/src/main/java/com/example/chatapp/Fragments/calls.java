package com.example.chatapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chatapp.MainActivity;
import com.example.chatapp.R;
import com.example.chatapp.Settringsactivity;
import com.example.chatapp.Sighninactivity;
import com.example.chatapp.databinding.FragmentCallsBinding;
import com.example.chatapp.testactivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class calls extends Fragment {
  FirebaseAuth auth;
  FragmentCallsBinding callsBinding;
   FragmentCallsBinding binding;
  FloatingActionButton floatingActionButton;

    public calls() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return null;
    }

}