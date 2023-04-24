package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class give_test_ID extends AppCompatActivity {


    EditText etTestID;
    Button submitTestID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_test_id);


        etTestID=findViewById(R.id.getTestID);
        submitTestID=findViewById(R.id.SubmitTestID);

        submitTestID.setOnClickListener(view -> goToAhead());


    }


    public void goToAhead(){

        String testOwner_id,test_id;

        String text = etTestID.getText().toString();

        if(!(text.length() >=25) || !text.contains(":")){
            return;
        }

        String[] separated = text.split(":");

        test_id=separated[0];
        testOwner_id= separated[1];


        Toast.makeText(this, testOwner_id+"  OR  "+test_id, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),getTest.class);
        intent.putExtra("testOwner_id",testOwner_id);
        intent.putExtra("test_id",test_id);
        startActivity(intent);
        finish();


    }

}