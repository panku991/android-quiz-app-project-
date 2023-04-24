package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginbye extends AppCompatActivity {

    EditText inputEmail , inputPassword , inputConfirmPassword;
    Button btnLog ;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextView go_sing_in;

    public static String PREFS_NAME_1 = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginbye);

        inputEmail = findViewById(R.id.editTextTextEmailAddress2);
        inputPassword = findViewById(R.id.editTextTextPassword);
        inputConfirmPassword = findViewById(R.id.editTextTextPassword2);
        btnLog = findViewById(R.id.buttonLog);
        go_sing_in=findViewById(R.id.go_sing_in);

        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

        go_sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginbye.this,sign_in.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private void PerforAuth(){

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputConfirmPassword.getText().toString();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(!email.matches(emailPattern)) {
            inputEmail.setError("enter valid email");
//            Toast.makeText(this, "invalid email !", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty() | password.length()<6)
        {
            inputPassword.setError("enter valid password");
//            Toast.makeText(this, "invalid password  !", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmpassword))
        {
            inputConfirmPassword.setError("password does not match");
//            Toast.makeText(this, "password does not match  !", Toast.LENGTH_SHORT).show();
        }
        else{
                progressDialog.setMessage("wait .... ");
                progressDialog.setTitle("log in ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sendusertonextactivity();
                            Toast.makeText(loginbye.this, "done", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(loginbye.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }


    }

    private void sendusertonextactivity(){

        SharedPreferences sharedPreferences = getSharedPreferences(loginbye.PREFS_NAME_1,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasLoggedIn",true);
        editor.apply();


        SharedPreferences sharedPreferences1 = getSharedPreferences(loginbye.PREFS_NAME_1,0);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putString("hasUserEmail",inputEmail.getText().toString());
        editor1.apply();


        Intent intent = new Intent(loginbye.this,MainActivity.class);
        intent.putExtra("email_of_user",sharedPreferences1.getString("hasUserEmail",""));
        startActivity(intent);
        finish();
    }




}