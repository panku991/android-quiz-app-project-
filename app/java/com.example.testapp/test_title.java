package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class test_title extends AppCompatActivity {

    String user_id;
    EditText etNameOfTest , noOfQues;
    Button subTestName;
    String test_name,total_ques;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_title);


        user_id=getIntent().getStringExtra("user_id");

        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();


        etNameOfTest = findViewById(R.id.etTestName);
        noOfQues=findViewById(R.id.noOfQuestions);
        subTestName=findViewById(R.id.SubmitTestName);

        subTestName.setOnClickListener(view -> goToFill());


    }


    public void goToFill() {

        test_name = etNameOfTest.getText().toString();
        total_ques = noOfQues.getText().toString();


        if(test_name.equals("")|| total_ques.equals("")){
            Toast.makeText(this, "enter valid data ", Toast.LENGTH_SHORT).show();
            return;

        }


        Intent intent = new Intent(this,setQuestions.class);
        intent.putExtra("test_name",test_name);
        intent.putExtra("total_ques",total_ques);
        intent.putExtra("user_id",user_id);
        startActivity(intent);
        finish();


    }




}