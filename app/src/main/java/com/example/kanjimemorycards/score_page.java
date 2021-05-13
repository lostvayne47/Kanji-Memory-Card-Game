package com.example.kanjimemorycards;

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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class score_page extends AppCompatActivity {

    ListView leaderboard_listview;                              //Declaration of listview
    static ArrayList<String> scores;                            //Declaration of Arraylist to score name and score
    EditText editText;                                          //Declaration of test textview to see if the code is working or not
    ArrayAdapter<String> arrayAdapter;                          //array adapter for list view

    SQLiteDatabase myDatabase;
    Cursor c;


    public void getEditText() {
        String highscore= "";
        highscore=Integer.toString(getIntent().getIntExtra("timetaken",0));  //Intent to get data from different score levels
        if(highscore==null){                                  //checking if highscore returned is null
            Log.i("score page highscore","null");
        }
        else {
            Log.i("score page highscore", highscore);
        }
        String temp="";
        temp=editText.getText().toString()+"\t\t\t\t\t\t"+highscore+"s";                     //Combining name and highscore
        myDatabase.execSQL("INSERT INTO highscores(Name) VALUES('"+temp+"')");               // inserting value in sql data base
    }

    public void data_base_recall(){
        c=myDatabase.rawQuery("SELECT * FROM highscores",null);     //selecting all rows and columns from the sql table

        int nameindex=c.getColumnIndex("Name");       //Column from sql is indexed

        c.moveToFirst();                                          // cursor moves to the first element of the sql table
        scores.clear();                                           //Clearing all existing values in the array to refresh all values
        while(!c.isAfterLast()){                                  //Looping through sql using cursor
            scores.add(c.getString(nameindex));                   //Adding value from sql to arraylist to show in array adapter
            Log.i("Name: ",c.getString(nameindex));          // Logging values to check if content is right
            c.moveToNext();                                       //moving to next value in the sql table
        }
    }
    public void submit_score(View view){
        getEditText();                                               //function call to get text
        data_base_recall();                                          //Calling data base recall function
        arrayAdapter.notifyDataSetChanged();                         //notifying array adapter of data set change
    }
    public void onBackPressed() {
        Log.i("CHECK", "BACK ENDING");
        finish();                                                    //finishing activity after back button is pressed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        myDatabase=this.openOrCreateDatabase("highscores",MODE_PRIVATE,null);       //Creating SQL database

        scores=new ArrayList<String>();                                      //initialising array list
        leaderboard_listview=(ListView)findViewById(R.id.leaderboard);       //initialising leaderboard listview
        editText=(EditText)findViewById(R.id.editTextTextPersonName);        //initialising edit textview

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,scores);  //setting up array adapter
        leaderboard_listview.setAdapter(arrayAdapter);                                                  //setting array adapters

        //scores.add("Hello");                                                                           //trial value used in array
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS highscores(Name VARCHAR)");                      //Creating table in sql

        data_base_recall();                                                                             //Calling data base recall function
        arrayAdapter.notifyDataSetChanged();

        leaderboard_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //myDatabase.execSQL("DROP TABLE highscores");                                        //Dropping table after testing
        //myDatabase.execSQL("INSERT INTO highscores(Name) VALUES('Aayush')");                // inserting dummy values

    }

}