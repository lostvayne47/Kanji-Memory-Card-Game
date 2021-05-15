package com.example.kanjimemorycards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView header;            //variables declared globally
    Button view_score;          //view button declaration
    Button play_button;         //play button button declaration
    Button easy_difficulty;     //easy button declaration
    Button medium_difficulty;   //medium button declaration
    Button hard_difficulty;     //hard button declaration
    Button glossary;            //Glossary button declaration

    Intent change_activity;     //intent to switch between various activities

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }  //Connecting Menu to main activity

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_settings:
            {
                Toast.makeText(this,"NOT WORKING",Toast.LENGTH_SHORT).show();
                return true;
            }
            default:{
                return false;
            }
        }
        //return super.onOptionsItemSelected(item);
    }  //Deciding task upon menu option selected

    public void selectdifficulty(View view){
        //Toast.makeText(this,"WORKING",Toast.LENGTH_SHORT).show();
        play_button.setVisibility(View.INVISIBLE);                  //change visibility of the initial headings and buttons
        view_score.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);

        easy_difficulty.setVisibility(View.VISIBLE);              //change visibility of the buttons to display difficulty options
        medium_difficulty.setVisibility(View.VISIBLE);
        hard_difficulty.setVisibility(View.VISIBLE);

    }   //Button function to select difficulty

    public void startgame(View view){

        switch(view.getId())                 //GETTING BUTTON ID AND SWITCHING TO INTENT
        {
            case R.id.easy_difficulty:{
                //Toast.makeText(this,"WORKING",Toast.LENGTH_SHORT).show();
                change_activity=new Intent(getApplicationContext(),Easy_Level.class);
                startActivity(change_activity);
                break;
            }                                                                               //going to easy level
            case R.id.medium_difficulty:{
                //Toast.makeText(this,"WORKING",Toast.LENGTH_SHORT).show();
                change_activity=new Intent(getApplicationContext(),Medium_Level.class);
                startActivity(change_activity);
                break;
            }                                                                               //going to medium level
            case R.id.hard_difficulty:{
                //Toast.makeText(this,"WORKING",Toast.LENGTH_SHORT).show();
                change_activity=new Intent(getApplicationContext(),Hard_Level.class);
                startActivity(change_activity);
                break;
            }                                                                                //going to hard level
        }

    } //switch to respective difficulty level activity

    public void glossarywindow(View view){
        change_activity=new Intent(getApplicationContext(),Glossary.class);
        startActivity(change_activity);
    }                          //switch to glossary activity
    public void leaderboard(View view){
        //Toast.makeText(this,"WORKING",Toast.LENGTH_SHORT).show();
        change_activity=new Intent(getApplicationContext(),score_page.class);
        startActivity(change_activity);
    }                          //switch to leaderboard activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getActionBar().hide();                                             //To Hide the Action Bar

        header=(TextView)findViewById(R.id.title_textview);                //main heading of the program
        view_score=(Button)findViewById(R.id.viewscore_button);            //button for going into view score section
        play_button=(Button)findViewById(R.id.play_button);                 //button for going to play game section
        glossary=(Button)findViewById(R.id.glossary_button);                //buttton for going to glossary section

        easy_difficulty=(Button)findViewById(R.id.easy_difficulty);        //buttons for various difficulty levels
        medium_difficulty=(Button)findViewById(R.id.medium_difficulty);
        hard_difficulty=(Button)findViewById(R.id.hard_difficulty);

        easy_difficulty.setVisibility(View.INVISIBLE);              //change visibility of the buttons to display difficulty options
        medium_difficulty.setVisibility(View.INVISIBLE);
        hard_difficulty.setVisibility(View.INVISIBLE);
    }
}