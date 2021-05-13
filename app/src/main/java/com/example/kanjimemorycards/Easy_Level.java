package com.example.kanjimemorycards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Easy_Level extends AppCompatActivity {

    TextView timer_textView_1;            //timertextview declaration
    ImageView im1,im2,im3,im4,im5,im6; //imageview declaration
    CountDownTimer countDownTimer;    //countdown timer declaration
    int time_taken =0; //to keep track of time left during the game
    int gamescore=0; //to keep track of the score of the game
    int cardCount=0; //to keep track of number of cards drawn at any time
    boolean gamestate=false; //to keep track of the game state whether the timer button is clicked

    Intent change_activity;  //intent to switch between various activities
    int[] imageArray = {R.drawable.kanji1, R.drawable.tree, R.drawable.kanji3, R.drawable.river, R.drawable.kanji5, R.drawable.fire};      //set of images to be shuffled randomly
    int[][] solutions={{R.drawable.kanji1, R.drawable.tree},{R.drawable.kanji3, R.drawable.river},{R.drawable.kanji5, R.drawable.fire}};  //solution set manually created

    static void shuffleArray(int[] arr)   //Code to Shuffle image array
    {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    public void timer(View view){
        gamestate=true;
         countDownTimer=new CountDownTimer(16000,1000){       //timer set in milliseconds with an accounted error of one second
            @Override                                                                                   //value of timer will change depending on the level
            public void onTick(long millisUntilFinished) {
                //timer_textView.setText(Integer.toString((int) (millisUntilFinished/1000)));
                String formatted_time=String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                timer_textView.setText(formatted_time);
                time_taken =(15-(int) Math.floor((int)(millisUntilFinished/1000)));   //updating time left for high score
                //Log.i("Time Left",Integer.toString(time_left));            //Logging time left
            }  //each interval update

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"TIME OVER",Toast.LENGTH_SHORT).show();
                resetgame();
            }    //final result after time ends
        }.start();

    } //timer for game

    public void checkSolution(int[] toCheck){

        int flag=0;               //flag variable used to check if the condition was satisfied in the loop
        for(int i=0;i<3;i++){    //looping through solution set which is a matrix of 3x2 value of matrix will change with levels
            int j=0;
            if((solutions[i][j]==toCheck[0] && solutions[i][j+1]==toCheck[1]) || (solutions[i][j]==toCheck[1] && solutions[i][j+1]==toCheck[0])) {
                flag=1;
            }
        }
        if (flag == 1) {
            //Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
            gamescore++;
        } else {
            //Toast.makeText(getApplicationContext(), Integer.toString(gamescore), Toast.LENGTH_SHORT).show();
            gamescore = 0;
            setImages();
        }
        if(gamescore==3){
          Toast.makeText(getApplicationContext(), "WINNER", Toast.LENGTH_SHORT).show();
//          try{
//          TimeUnit.SECONDS.sleep(1);
//          }
//          catch(Exception e){
//              e.printStackTrace();
//          }
            resetgame();
        }
    }// code to compare the images clicked from the solution set
    public void updateleaderboard(){
        change_activity=new Intent(getApplicationContext(),score_page.class);
        Log.i("FINAL TIME TAKEN: ",Integer.toString(time_taken));  //Logging time in which quiz was solved
        change_activity.putExtra("timetaken", time_taken);       //Going to high score activity
        startActivity(change_activity);
    }

    public void resetgame(){
        timer_textView.setText("00:15"); //setting textview to 00:00
        countDownTimer.cancel();        //reseting timer
        gamescore = 0;                 //reseting game score
        gamestate=false;              //not allowing game to be played
        setImages();                 //reseting the question mark images
        shuffleArray(imageArray);   //shuffle function called to shuffle the images randomly
        updateleaderboard();       //function call to update scoreboard
    }
    public void setImages(){
        im1.setImageResource(R.drawable.questionmark);
        im2.setImageResource(R.drawable.questionmark);
        im3.setImageResource(R.drawable.questionmark);
        im4.setImageResource(R.drawable.questionmark);
        im5.setImageResource(R.drawable.questionmark);
        im6.setImageResource(R.drawable.questionmark);
    }//set question mark if the image chosen is wrong

    int[] sendToCheck ={imageArray[0],imageArray[0]}; //checking array inside ignore the values

    public void animate (View view){
        if(gamestate) {
            if (cardCount < 2) {
                switch (view.getId())     //code to get imageview id to flip the image using switch case
                {
                    case R.id.mediumimageView1: {
                        im1.setImageResource(imageArray[0]);        //setting images according to the shuffled image array
                        sendToCheck[cardCount] = imageArray[0];    //appending the array to pass to the checking function
                        break;
                    }
                    case R.id.mediumimageView2: {
                        im2.setImageResource(imageArray[1]);
                        sendToCheck[cardCount] = imageArray[1];
                        break;
                    }
                    case R.id.mediumimageView3: {
                        im3.setImageResource(imageArray[2]);
                        sendToCheck[cardCount] = imageArray[2];
                        break;
                    }
                    case R.id.mediumimageView4: {
                        im4.setImageResource(imageArray[3]);
                        sendToCheck[cardCount] = imageArray[3];
                        break;
                    }
                    case R.id.mediumimageView5: {
                        im5.setImageResource(imageArray[4]);
                        sendToCheck[cardCount] = imageArray[4];
                        break;
                    }
                    case R.id.mediumimageView6: {
                        im6.setImageResource(imageArray[5]);
                        sendToCheck[cardCount] = imageArray[5];
                        break;
                    }
                    default: {
                        Toast.makeText(getApplicationContext(), "DEFAULT", Toast.LENGTH_SHORT).show();
                    }
                }
                cardCount++;                         //updating card count each time card is drawn
                if (cardCount == 2) {                    // making sure only two cards are used at a time
                    checkSolution(sendToCheck);      //calling checking function to compare the cards
                    cardCount = 0;
                }
            }
        }
    }//Code To execute main game


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy__level);

        timer_textView =findViewById(R.id.timertextView);
        Button timer_start_button=findViewById(R.id.timer_start);

        im1=(ImageView)findViewById(R.id.mediumimageView1);   //image view 1 to 6 initialized
        im2=(ImageView)findViewById(R.id.mediumimageView2);
        im3=(ImageView)findViewById(R.id.mediumimageView3);
        im4=(ImageView)findViewById(R.id.mediumimageView4);
        im5=(ImageView)findViewById(R.id.mediumimageView5);
        im6=(ImageView)findViewById(R.id.mediumimageView6);

        shuffleArray(imageArray);     //shuffle function called to shuffle the images randomly

    }

}