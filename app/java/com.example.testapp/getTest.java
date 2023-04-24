package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class getTest extends AppCompatActivity {




    String test_id, testOwner_id;


    String TestNameValue;

    int radioId;


    TextView QuestionTv,QuestionNumber;
    RadioButton rBtn1,rBtn2,rBtn3,rBtn4,checkedRadioBtn;
    int QuestionIndex=0;
    int RightAns=0, WrongAns=0, total=0;
    ExtendedFloatingActionButton testNameTVFBTN,goToNextTVFBTN ;
    RadioGroup radioGroup;




    FirebaseDatabase database,database1,databaseCheck;
    DatabaseReference myRef, myRef2,checkRef;

    ArrayList<GetQuestions_Model> arrayList =  new ArrayList<>(); ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_test);





        test_id = getIntent().getStringExtra("test_id");
        testOwner_id = getIntent().getStringExtra("testOwner_id");

        init();


        databaseCheck = FirebaseDatabase.getInstance();

        checkRef = databaseCheck.getReference(testOwner_id).child("Tests");


        checkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(test_id)) {

                    getTestName();
                    getAllQuestions();
                }
                else{
                    Toast.makeText(getTest.this, "Enter the valid Test ID ", Toast.LENGTH_SHORT).show();
                    getTest.this.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getTest.this, "please check your internet connection OR the test ID ", Toast.LENGTH_SHORT).show();
            }
        });

        goToNextTVFBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers();
            }
        });



    }




    public void init(){

        radioGroup = findViewById(R.id.radioGroup);

        testNameTVFBTN=findViewById(R.id.testNameFBTN);
        goToNextTVFBTN=findViewById(R.id.goToNextFBTN);

        QuestionTv=findViewById(R.id.testQuestion);
        QuestionNumber=findViewById(R.id.testQuestionNumber);

        rBtn1=findViewById(R.id.option1);
        rBtn2=findViewById(R.id.option2);
        rBtn3=findViewById(R.id.option3);
        rBtn4=findViewById(R.id.option4);


    }


    public void getTestName(){

        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference(testOwner_id).child("Tests").child(test_id);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TestNameValue = String.valueOf(snapshot.child("Test Name").getValue());

                testNameTVFBTN.setText(TestNameValue);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void getAllQuestions(){

        database1 = FirebaseDatabase.getInstance();
        myRef = database1.getReference(testOwner_id).child("Tests").child(test_id).child("Questions");



        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                try{
                    for(DataSnapshot AQ1 : snapshot.getChildren()){

                        GetQuestions_Model model = AQ1.getValue(GetQuestions_Model.class);

                        arrayList.add(model);

                        setData();

                    }
                } catch (Exception e) {
                    Toast.makeText(getTest.this, "please check your internet connection OR the test ID ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getTest.this, "please check your internet connection OR the test ID ", Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void setData(){

        int number= QuestionIndex+1;

        QuestionNumber.setText(String.valueOf(number));

        QuestionTv.setText(arrayList.get(QuestionIndex).getQuestion());

        rBtn1.setText(String.valueOf(arrayList.get(QuestionIndex).getOption1()));
        rBtn2.setText(String.valueOf(arrayList.get(QuestionIndex).getOption2()));
        rBtn3.setText(String.valueOf(arrayList.get(QuestionIndex).getOption3()));
        rBtn4.setText(String.valueOf(arrayList.get(QuestionIndex).getOption4()));



    }

    public void checkAnswers(){

        if(checkButton()){
            @SuppressLint("ResourceType") String name = getResources().getResourceEntryName(checkedRadioBtn.getId());
            if(name.equals(arrayList.get(QuestionIndex).getRight())){
                RightAns++;
                jumpToNext();
            }
            else{
                WrongAns++;
                jumpToNext();
            }
        }
        else{
            Toast.makeText(this, "please select the one of above options !", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public Boolean checkButton(){

        try {
            radioId = radioGroup.getCheckedRadioButtonId();
            checkedRadioBtn = findViewById(radioId);
            return true;

        } catch (Exception e) {
            Toast.makeText(this, "this is exception", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    public void jumpToNext(){
        if(QuestionIndex>=arrayList.size()-1){
            gotoResult();
        }else{
            QuestionIndex++;
            setData();
        }
    }


    public void gotoResult(){

        total = QuestionIndex+1;


        Intent intent = new Intent(this,TestResult.class);

        intent.putExtra("total",String.valueOf(total));
        intent.putExtra("rightAns",String.valueOf(RightAns));
        intent.putExtra("wrongAns",String.valueOf(WrongAns));
        startActivity(intent);
        finish();

    }


}


