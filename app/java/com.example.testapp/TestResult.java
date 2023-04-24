package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class TestResult extends AppCompatActivity {


    String total , rightAns , wrongAns ;

    TextView totalValue,rightValue,wrongValue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);


        totalValue=findViewById(R.id.totalValue);
        rightValue=findViewById(R.id.rightValue);
        wrongValue=findViewById(R.id.wrongValue);



        total=getIntent().getStringExtra("total");
        rightAns=getIntent().getStringExtra("rightAns");
        wrongAns=getIntent().getStringExtra("wrongAns");


        setValues();





    }

    void setValues() {
        totalValue.setText(total);
        rightValue.setText(rightAns);
        wrongValue.setText(wrongAns);
    }

}