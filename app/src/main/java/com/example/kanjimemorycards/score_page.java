package com.example.kanjimemorycards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class score_page extends AppCompatActivity {

    ListView leaderboard_listview;                              //Declaration of listview
    static ArrayList<String> scores;                            //Declaration of Arraylist to score name and score
    EditText editText;                                          //Declaration of test textview to see if the code is working or not
    String highscore;                                           //Declaration of Highscore String
    ScoreDatabase scoreDatabase;                                //Declaration of Score Database Object
    ArrayAdapter<String> arrayAdapter;                          //array adapter for list view
    DatabaseReference firebase;                                 //Declaring database reference object
    //SQLiteDatabase myDatabase;                                  //Declaring SQllite database object
    //Cursor c;                                                   //Declaring Cursor to navigate through SQL


    public void getEditText() {
        highscore="";
        highscore=Integer.toString(getIntent().getIntExtra("timetaken",0));  //Intent to get data from different score levels
        if(highscore==null){                                  //checking if highscore returned is null
            Log.i("score page highscore","null");
        }
        else {
            Log.i("score page highscore", highscore);
        }
//        String temp="";
//        temp=editText.getText().toString()+"\t\t\t\t\t\t"+highscore+"s";                     //Combining name and highscore
//        myDatabase.execSQL("INSERT INTO highscores(Name) VALUES('"+temp+"')");               // inserting value in sql data base
    }

    public void data_base_recall(){
//        c=myDatabase.rawQuery("SELECT * FROM highscores",null);     //selecting all rows and columns from the sql table
//
//        int nameindex=c.getColumnIndex("Name");       //Column from sql is indexed
//
//        c.moveToFirst();                                          // cursor moves to the first element of the sql table
//        scores.clear();                                           //Clearing all existing values in the array to refresh all values
//        while(!c.isAfterLast()){                                  //Looping through sql using cursor
//            scores.add(c.getString(nameindex));                   //Adding value from sql to arraylist to show in array adapter
//            Log.i("Name: ",c.getString(nameindex));          // Logging values to check if content is right
//            c.moveToNext();                                       //moving to next value in the sql table
//        }//Sql Code
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scores.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    ScoreDatabase sd=snap.getValue(ScoreDatabase.class);
                    try {
                        String temp="";
                        temp=sd.getName()+"\t\t\t\t\t\t"+sd.getScore()+"s";                     //Combining name and highscore
                        scores.add(temp);                                                       //Adding value from firebase to arraylist to show in array adapter
                        Log.i("Name: ",sd.getName());                                       // Logging values to check if content is right
                        Log.i("Score: ",sd.getScore());                                     // Logging values to check if content is right

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                arrayAdapter.notifyDataSetChanged();
                Log.i("Array Info","Data set notified");
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    public void submit_score(View view){
        getEditText();                                                  //function call to get text
        //data_base_recall();                                          //Calling data base recall function for SQL. Not needed for firebase due to event listener
        uploadtofirebase();                                           //Calling function to upload data to firebase
        arrayAdapter.notifyDataSetChanged();                         //notifying array adapter of data set change
        Toast.makeText(this,"Score Submitted!",Toast.LENGTH_SHORT).show();  //Displaying to user that scores have been submitted
    }

    public void onBackPressed() {
        Log.i("CHECK", "BACK ENDING");
        finish();                                                    //finishing activity after back button is pressed
    }

    public void uploadtofirebase(){                                                 //Uploading to firebase database
        scoreDatabase.setName(editText.getText().toString());                       //Setting name variable of object
        scoreDatabase.setScore(highscore);                                          //Setting score variable of object
        if(editText.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter your Name",Toast.LENGTH_SHORT).show();
        }else {
            firebase.child(editText.getText().toString()).setValue(scoreDatabase);      //Setting/pushing scoredatabase object to firebase
        }
        Log.i("FirebaseInfo", "Data Pushed Successfully");                   //Logging for conformation
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        //myDatabase=this.openOrCreateDatabase("highscores",MODE_PRIVATE,null);                 //Creating SQL database
        firebase= FirebaseDatabase.getInstance().getReference().child("Leaderboard");           //initialising firebase database reference

        scoreDatabase=new ScoreDatabase();                                                       //initialising ScoreDatabase object
        scores=new ArrayList<String>();                                                         //initialising array list

        leaderboard_listview=(ListView)findViewById(R.id.leaderboard);                           //initialising leaderboard listview
        editText=(EditText)findViewById(R.id.editTextTextPersonName);                            //initialising edit textview

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,scores);  //setting up array adapter
        leaderboard_listview.setAdapter(arrayAdapter);                                                  //setting array adapters

        //scores.add("Hello");                                                                           //trial value used in array
        //myDatabase.execSQL("CREATE TABLE IF NOT EXISTS highscores(Name VARCHAR)");                      //Creating table in sql

        data_base_recall();                                                                             //Calling data base recall function


        //myDatabase.execSQL("DROP TABLE highscores");                                        //Dropping table after testing
        //myDatabase.execSQL("INSERT INTO highscores(Name) VALUES('Aayush')");                // inserting dummy values

    }

}