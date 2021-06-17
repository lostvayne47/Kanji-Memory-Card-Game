package com.example.kanjimemorycards;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Medium_Level extends AppCompatActivity {

    TextView timer_textView;
    ImageView im1,im2,im3,im4,im5,im6,im7,im8;
    CountDownTimer countDownTimer;
    int time_taken =0; //to keep track of time left during the game
    int gamescore=0; //to keep track of the score of the game
    int cardCount=0; //to keep track of number of cards drawn at any time
    boolean gamestate=false; //to keep track of the game state whether the timer button is clicked

    Intent change_activity;                          //intent to switch between various activities

    int[] imagedatabase ={R.drawable.kanji1, R.drawable.tree,R.drawable.kanji2,R.drawable.hill,R.drawable.kanji3, R.drawable.river,
            R.drawable.kanji4,R.drawable.ricefield,R.drawable.kanji5, R.drawable.fire,R.drawable.kanji6,R.drawable.water,
            R.drawable.kanji7,R.drawable.gold,R.drawable.kanji8,R.drawable.soil,R.drawable.kanji9,R.drawable.study,
            R.drawable.kanji10,R.drawable.king,R.drawable.kanji11,R.drawable.heart,R.drawable.kanji12,R.drawable.man,
            R.drawable.kanji13,R.drawable.woman,R.drawable.kanji14,R.drawable.mouth,R.drawable.kanji15,R.drawable.person,
            R.drawable.kanji16,R.drawable.moon,R.drawable.kanji17,R.drawable.sun,R.drawable.kanji18,R.drawable.year,
            R.drawable.kanji19,R.drawable.yen,R.drawable.kanji20,R.drawable.one,R.drawable.kanji21,R.drawable.two,
            R.drawable.kanji22,R.drawable.three,R.drawable.kanji23,R.drawable.four,R.drawable.kanji24,R.drawable.five,
            R.drawable.kanji25,R.drawable.six,R.drawable.kanji26,R.drawable.seven,R.drawable.kanji27,R.drawable.eight,
            R.drawable.kanji28,R.drawable.nine,R.drawable.kanji29,R.drawable.ten,R.drawable.kanji30,R.drawable.hundred,
            R.drawable.kanji31,R.drawable.thousand,R.drawable.kanji32,R.drawable.ten_thousand};                              //Image database

    int[][] solutions={{R.drawable.kanji1, R.drawable.tree},{R.drawable.kanji2,R.drawable.hill},{R.drawable.kanji3, R.drawable.river},
            {R.drawable.kanji4,R.drawable.ricefield},{R.drawable.kanji5, R.drawable.fire},{R.drawable.kanji6,R.drawable.water},
            {R.drawable.kanji7,R.drawable.gold},{R.drawable.kanji8,R.drawable.soil},{R.drawable.kanji9,R.drawable.study},
            {R.drawable.kanji10,R.drawable.king},{R.drawable.kanji11,R.drawable.heart},{R.drawable.kanji12,R.drawable.man},
            {R.drawable.kanji13,R.drawable.woman},{R.drawable.kanji14,R.drawable.mouth},{R.drawable.kanji15,R.drawable.person},
            {R.drawable.kanji16,R.drawable.moon},{R.drawable.kanji17,R.drawable.sun},{R.drawable.kanji18,R.drawable.year},
            {R.drawable.kanji19,R.drawable.yen},{R.drawable.kanji20,R.drawable.one},{R.drawable.kanji21,R.drawable.two},
            {R.drawable.kanji22,R.drawable.three},{R.drawable.kanji23,R.drawable.four},{R.drawable.kanji24,R.drawable.five},
            {R.drawable.kanji25,R.drawable.six},{R.drawable.kanji26,R.drawable.seven},{R.drawable.kanji27,R.drawable.eight},
            {R.drawable.kanji28,R.drawable.nine},{R.drawable.kanji29,R.drawable.ten},{R.drawable.kanji30,R.drawable.hundred},
            {R.drawable.kanji31,R.drawable.thousand},{R.drawable.kanji32,R.drawable.ten_thousand}};                       //solution set manually created

    int[] mediumimageArray = {0,0,0,0,0,0,0,0};                                                                             //set of images to be shuffled randomly


    void shuffledatabasearray(int[] arr){
        Random rnd = new Random();
        int index;
        Log.i("Info", String.valueOf(imagedatabase.length-1));
        for(int i=0;i<4;i++){
            index=rnd.nextInt(imagedatabase.length-1);                          //generate random index  to get 4 image and solution sets from image database
            if (index % 2 != 0 ) {
                index--;                                                              //image index should be even so to check for that
            }
            Log.i("Info", String.valueOf(index));
            mediumimageArray[i*2]=arr[index];                                        //allot from database to image array to be used
            mediumimageArray[i*2+1]=arr[index+1];
        }
        Log.i("Info","Array Initilaised");
    }

    static void shuffleimageArray(int[] arr)   //Code to Shuffle image array
    {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);                         // Assign index
            int a = arr[index];
            arr[index] = arr[i];                                         //Swap elements
            arr[i] = a;
        }
    }

    public void timer(View view){
        gamestate=true;
        countDownTimer=new CountDownTimer(31000,1000){       //timer set in milliseconds with an accounted error of one second
            @Override                                                                                   //value of timer will change depending on the level
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000==5){
                    MainActivity.audio_file.timeup_sound();
                }                                  //timer to give alert if 5s remaining
                //timer_textView.setText(Integer.toString((int) (millisUntilFinished/1000)));
                @SuppressLint("DefaultLocale") String formatted_time=String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                timer_textView.setText(formatted_time);
                time_taken =(30-(int) Math.floor((int)(millisUntilFinished/1000)));   //updating time left for high score
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
        for (int[] solution : solutions) {    //looping through solution set
            int j = 0;
            if ((solution[j] == toCheck[0] && solution[j + 1] == toCheck[1]) || (solution[j] == toCheck[1] && solution[j + 1] == toCheck[0])) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) {                        //if flag is equal to 1 then the two cards selected match
            //Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
            MainActivity.audio_file.right_sound();               //playing audio if cards match
            gamescore++;
        } else {
            //Toast.makeText(getApplicationContext(), Integer.toString(gamescore), Toast.LENGTH_SHORT).show();
            MainActivity.audio_file.wrong_sound();              //playing audio if cards don't match
            gamescore = 0;
            setImages();
        }
        if(gamescore==4){                   //checking game score to see the winning point
            Toast.makeText(getApplicationContext(), "WINNER", Toast.LENGTH_SHORT).show();
            MainActivity.audio_file.winning_sound();            //playing audio if user wins
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
        timer_textView.setText("00:30"); //setting textview to 00:00
        countDownTimer.cancel();        //reseting timer
        gamescore = 0;                 //reseting game score
        gamestate=false;              //not allowing game to be played
        setImages();                 //reseting the question mark images
        shuffledatabasearray(imagedatabase);   //shuffle function called to shuffle and assign the database images randomly
        shuffleimageArray(mediumimageArray);   //shuffle function called to shuffle the images randomly
        updateleaderboard();       //function call to update scoreboard
    }
    public void setImages(){
        im1.setImageResource(R.drawable.questionmark);
        im2.setImageResource(R.drawable.questionmark);
        im3.setImageResource(R.drawable.questionmark);
        im4.setImageResource(R.drawable.questionmark);
        im5.setImageResource(R.drawable.questionmark);
        im6.setImageResource(R.drawable.questionmark);
        im7.setImageResource(R.drawable.questionmark);
        im8.setImageResource(R.drawable.questionmark);
    }//set question mark if the image chosen is wrong

    int[] sendToCheck ={mediumimageArray[0], mediumimageArray[0]}; //checking array inside ignore the values

    public void animate (View view){
        if(gamestate) {
            if (cardCount < 2) {
                switch (view.getId())     //code to get imageview id to flip the image using switch case
                {
                    case R.id.mediumimageView1: {
                        im1.setImageResource(mediumimageArray[0]);        //setting images according to the shuffled image array
                        sendToCheck[cardCount] = mediumimageArray[0];    //appending the array to pass to the checking function
                        break;
                    }
                    case R.id.mediumimageView2: {
                        im2.setImageResource(mediumimageArray[1]);
                        sendToCheck[cardCount] = mediumimageArray[1];
                        break;
                    }
                    case R.id.mediumimageView3: {
                        im3.setImageResource(mediumimageArray[2]);
                        sendToCheck[cardCount] = mediumimageArray[2];
                        break;
                    }
                    case R.id.mediumimageView4: {
                        im4.setImageResource(mediumimageArray[3]);
                        sendToCheck[cardCount] = mediumimageArray[3];
                        break;
                    }
                    case R.id.mediumimageView5: {
                        im5.setImageResource(mediumimageArray[4]);
                        sendToCheck[cardCount] = mediumimageArray[4];
                        break;
                    }
                    case R.id.mediumimageView6: {
                        im6.setImageResource(mediumimageArray[5]);
                        sendToCheck[cardCount] = mediumimageArray[5];
                        break;
                    }
                    case R.id.mediumimageView7: {
                        im7.setImageResource(mediumimageArray[6]);
                        sendToCheck[cardCount] = mediumimageArray[6];
                        break;
                    }
                    case R.id.mediumimageView8: {
                        im8.setImageResource(mediumimageArray[7]);
                        sendToCheck[cardCount] = mediumimageArray[7];
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
        setContentView(R.layout.activity_medium__level);

        timer_textView =findViewById(R.id.timertextView);
        Button timer_start_button=findViewById(R.id.timer_start);

        im1= findViewById(R.id.mediumimageView1);   //image view 1 to 8 initialized
        im2= findViewById(R.id.mediumimageView2);
        im3= findViewById(R.id.mediumimageView3);
        im4= findViewById(R.id.mediumimageView4);
        im5= findViewById(R.id.mediumimageView5);
        im6= findViewById(R.id.mediumimageView6);
        im7= findViewById(R.id.mediumimageView7);
        im8= findViewById(R.id.mediumimageView8);

        shuffledatabasearray(imagedatabase);     //shuffle function called to shuffle and assign the database images randomly
        shuffleimageArray(mediumimageArray);     //shuffle function called to shuffle the images randomly

    }
}