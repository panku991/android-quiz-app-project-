package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Boolean flag=false;


    RecyclerViewAdapter adapter;



    String email_of_user,user_specific_id;
    RecyclerView recyclerView;
    FloatingActionButton give_test_option;
    ExtendedFloatingActionButton create_test,give_test;

    FirebaseDatabase database;
    DatabaseReference myRef,myRefToAll;

    ArrayList<ModelForFetch> fetchList;


    //String[] options= {"create test ","take test"};

    RelativeLayout layout;


    ImageView profile_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchList = new ArrayList<>();
        fetchList.clear();

        email_of_user= getIntent().getStringExtra("email_of_user");

        Toast.makeText(this, email_of_user, Toast.LENGTH_SHORT).show();

        String tyr1=email_of_user.replace("@","_");
        String try2 = tyr1.replace(".","_");
        user_specific_id=try2;

        layout=findViewById(R.id.thisgo);
        recyclerView=findViewById(R.id.recycler_view);
        give_test_option=findViewById(R.id.float_Btn);
        create_test=findViewById(R.id.create_test);
        give_test=findViewById(R.id.give_test);
        profile_b = findViewById(R.id.profileBtn);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(user_specific_id).child("User Details");
        myRef.child("userEmail").setValue(email_of_user);

        give_test_option.setOnClickListener(view -> goToNext());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                give_test.setVisibility(View.INVISIBLE);
                create_test.setVisibility(View.INVISIBLE);
                flag=false;
            }
        });


        profile_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            }
        });



    }

    public void goToNext(){

        if(flag){
            give_test.setVisibility(View.INVISIBLE);
            create_test.setVisibility(View.INVISIBLE);
            flag=false;
        }
        else{
            create_test.setVisibility(View.VISIBLE);
            give_test.setVisibility(View.VISIBLE);
            flag=true;
        }


        give_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                give_test.setVisibility(View.INVISIBLE);
                create_test.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), user_specific_id, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),give_test_ID.class);
                intent.putExtra("user_id",user_specific_id);
                startActivity(intent);

            }
        });


        create_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                give_test.setVisibility(View.INVISIBLE);
                create_test.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), user_specific_id, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),test_title.class);
                intent.putExtra("user_id",user_specific_id);
                startActivity(intent);

            }
        });


    }


    public void fetchAllTests(){

        fetchList.clear();

        myRefToAll = database.getReference(user_specific_id).child("Tests");

        myRefToAll.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dts : snapshot.getChildren()){

                    String fetchTotalQuestions = String.valueOf(dts.child("Questions").getChildrenCount());
                    String fetchTestId = dts.getKey();
                    String fetchTestName = String.valueOf(dts.child("Test Name").getValue());

                    ModelForFetch createdModel = new ModelForFetch(fetchTestName,fetchTestId,fetchTotalQuestions);

                    fetchList.add(createdModel);


                }

                adapter = new RecyclerViewAdapter(MainActivity.this,fetchList);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchAllTests();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fetchList.clear();
    }
}













/*

{
  "tests": {
    "testId1": {
      "teacherUserId": "teacherId1",
      "testName": "Test 1",
      "questions": {
        "questionId1": {
          "questionText": "What is the capital of France?",
          "options": {
            "option1": "Paris",
            "option2": "London",
            "option3": "New York",
            "option4": "Tokyo"
          },
          "correctOption": "option1"
        },
        "questionId2": {
          "questionText": "What is the largest mammal?",
          "options": {
            "option1": "Elephant",
            "option2": "Blue whale",
            "option3": "Giraffe",
            "option4": "Hippopotamus"
          },
          "correctOption": "option2"
        },
        // Add more questions here
      }
    },
    "testId2": {
      // Another test
    }
  },
  "testResults": {
    "testId1": {
      "studentUserId1": {
        "score": 8,
        "timeTaken": 180 // Time taken to complete the test in seconds
      },
      "studentUserId2": {
        "score": 10,
        "timeTaken": 240
      },
      // Add more students and their results here
    },
    "testId2": {
      // Results for another test
    }
  }
}

 */


