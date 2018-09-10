package com.example.zyandeep.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private GridLayout gridLayout;

    private TextView result;
    private TextView timer;
    private TextView question;
    private TextView score;

    private Button[] buttons;
    Button startButton;

    private int attempQuestions;
    private int correct;
    private int answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.resultTextView);
        timer = (TextView) findViewById(R.id.timerTextView);
        question = findViewById(R.id.questionTextView);
        score = findViewById(R.id.scoreTextView);

        buttons = new Button[4];
        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);



        // hide views except the start button
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.INVISIBLE);

        result = findViewById(R.id.resultTextView);
        result.setVisibility(View.INVISIBLE);


        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("Button", "tapped");

                startButton.setVisibility(View.INVISIBLE);
                startGame();
            }
        });
    }

    private void startGame() {

        this.attempQuestions = this.correct = 0;
        result.setText("");

        // show game component view
        linearLayout.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);

        generateQuestion();

        // Set the timer for 30s
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                question.setText("Game Over!");

                // play the sound
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bomb);
                mp.start();


                gridLayout.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);

                // decleare result
                result.setText("Total Score: " + String.format("%d/%d", correct, attempQuestions));
            }
        }.start();
    }



    private void generateQuestion() {
        Random random = new Random();

        int op1 = random.nextInt(20);
        int op2 = random.nextInt(20);
        this.answer = op1 + op2;
        
        question.setText(String.format("%d + %d = ?", op1, op2));

        score.setText(String.format("%d/%d", this.correct, this.attempQuestions));

        // display options
        // Select a button at random that displays the correct result
        int index = random.nextInt(4);
        buttons[index].setText(String.valueOf(this.answer));

        // display random sums on the other buttons
        for (int k = 0; k < 4; k++) {

            if (k != index) {

                int temp = this.answer;

                while (temp == this.answer) {
                    temp = random.nextInt(30) + random.nextInt(20);
                }

                buttons[k].setText(String.valueOf(temp));

            }
        }

    }



    public void buttonTapped(View v) {
        //Log.i("Button ID", String.valueOf(v.getId()));

        // Which button has been tapped?
        Button b = findViewById(v.getId());

        int userAnser = Integer.parseInt((String)b.getText());

        this.attempQuestions++;
        
        // check if that was the correct answer
        if (userAnser == this.answer) {
            this.correct++;

            result.setText("Correct!");
        }
        else {
            result.setText("Wrong!");
        }

        generateQuestion();
    }
}