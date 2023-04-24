package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    Button log_out;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        log_out= findViewById(R.id.button_log_out);

        log_out.setOnClickListener(view -> goback());
    }

    private void goback(){
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}