package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class setQuestions extends AppCompatActivity {



    String user_id,test_name;
    int total_questions;

    String test_id_for_tester;

    String[] spinnerOptions = {"option1","option2","option3","option4"};

    FirebaseDatabase database;
    DatabaseReference myRef,myRef_init,myRef2;

    String  last_ques,last_op1,last_op2,last_op3,last_op4,last_right="option1";

    TextView thead;
    EditText etq,et1,et2,et3,et4;
    Spinner spin;
    Button next_btn;

    int count=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_questions);


        user_id=getIntent().getStringExtra("user_id");
        test_name=getIntent().getStringExtra("test_name");
        total_questions=Integer.parseInt(getIntent().getStringExtra("total_ques"));

        database = FirebaseDatabase.getInstance();


        init();



        thead=findViewById(R.id.Tv_test_name);
        etq=findViewById(R.id.question);
        et1=findViewById(R.id.first_op);
        et2=findViewById(R.id.second_op);
        et3=findViewById(R.id.third_op);
        et4=findViewById(R.id.fourth_op);
        next_btn=findViewById(R.id.next_question);


        thead.setText(test_name);


        spin = findViewById(R.id.spinner);

        ArrayAdapter adapter_spin = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spinnerOptions);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter_spin);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                last_right=spinnerOptions[i].toString();
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {
                last_right=spinnerOptions[0];
            }
        });


        next_btn.setOnClickListener(view -> getAll());




    }




    public void getAll(){

        Toast.makeText(this, test_id_for_tester, Toast.LENGTH_SHORT).show();

        count++;

        if(count>=total_questions){

            shareTest();


        }
        last_ques = etq.getText().toString();
        last_op1=et1.getText().toString();
        last_op2=et2.getText().toString();
        last_op3=et3.getText().toString();
        last_op4=et4.getText().toString();



        myRef2 = myRef_init.child("Questions").push();
        myRef2.child("Question").setValue(last_ques);
        myRef2.child("Option1").setValue(last_op1);
        myRef2.child("Option2").setValue(last_op2);
        myRef2.child("Option3").setValue(last_op3);
        myRef2.child("Option4").setValue(last_op4);
        myRef2.child("right").setValue(last_right);



        etq.getText().clear();
        et1.getText().clear();
        et2.getText().clear();
        et3.getText().clear();
        et4.getText().clear();



    }

    public void init(){

        myRef_init = database.getReference(user_id).child("Tests").push();
        myRef_init.child("Test Name").setValue(test_name);
        test_id_for_tester=myRef_init.getKey();


    }


    public void shareTest(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final TextView linkText = new TextView(this);
        linkText.setGravity(Gravity.LEFT);


        alert.setTitle("Share your test :- ");

        alert.setView(linkText);
        linkText.setText(new StringBuilder().append(test_id_for_tester).append(":").append(user_id).toString());
        linkText.setMaxLines(1);

        alert.setPositiveButton("copy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                setClipboard(getApplicationContext(),linkText.getText().toString());

            }
        });

        alert.setNegativeButton("share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                sharing(linkText.getText().toString());

            }
        });

        alert.show();



    }




    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }



        setQuestions.this.finish();
        count=0;
    }

    private void sharing(String msg){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

        setQuestions.this.finish();
        count=0;
    }



}