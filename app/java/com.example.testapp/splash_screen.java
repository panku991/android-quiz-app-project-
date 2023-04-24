package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splash_screen extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(loginbye.PREFS_NAME_1,0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);

                SharedPreferences sharedPreferences1 = getSharedPreferences(loginbye.PREFS_NAME_1,0);
                String hasUserEmail = sharedPreferences1.getString("hasUserEmail","");


                if(hasLoggedIn && (!hasUserEmail.equals(""))){

                    Intent intent = new Intent(splash_screen.this, MainActivity.class);
                    intent.putExtra("email_of_user",sharedPreferences1.getString("hasUserEmail",""));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(splash_screen.this, loginbye.class);
                    startActivity(intent);

                }




            }
        },SPLASH_TIME_OUT);

    }
}