package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class login_activity extends AppCompatActivity {

    EditText etEmail;
    Button submitBtn;

    public static String PREFS_NAME = "MyPrefsFile";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail=findViewById(R.id.editTextTextEmailAddress);
        submitBtn=findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(view -> getData());


    }

    public void getData(){


        if(etEmail.getText().toString().isEmpty()){
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(login_activity.PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasLoggedIn",true);
        editor.apply();


        SharedPreferences sharedPreferences1 = getSharedPreferences(login_activity.PREFS_NAME,0);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putString("hasUserEmail",etEmail.getText().toString());
        editor1.apply();


        Intent intent = new Intent(login_activity.this,MainActivity.class);
        intent.putExtra("email_of_user",sharedPreferences1.getString("hasUserEmail",""));
        startActivity(intent);
        finish();

    }

}