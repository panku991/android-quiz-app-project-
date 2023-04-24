package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity {

    EditText inputemail, inputpass;
    Button btn1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        inputemail = findViewById(R.id.Email);
        inputpass = findViewById(R.id.Password);
        btn1 = findViewById(R.id.Sign_in);


        btn1.setOnClickListener(view -> getauth());


    }

    private void getauth() {
        String email = inputemail.getText().toString();
        String password = inputpass.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUser = mAuth.getCurrentUser();
                    gotonext();
                } else {
                    Toast.makeText(sign_in.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private  void gotonext(){


        SharedPreferences sharedPreferences = getSharedPreferences(loginbye.PREFS_NAME_1,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasLoggedIn",true);
        editor.apply();


        SharedPreferences sharedPreferences1 = getSharedPreferences(loginbye.PREFS_NAME_1,0);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putString("hasUserEmail",inputemail.getText().toString());
        editor1.apply();


        Intent intent = new Intent(sign_in.this,MainActivity.class);
        intent.putExtra("email_of_user",sharedPreferences1.getString("hasUserEmail",""));
        startActivity(intent);
        finish();


    }


}