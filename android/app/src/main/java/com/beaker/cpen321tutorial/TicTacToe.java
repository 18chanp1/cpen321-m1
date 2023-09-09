package com.beaker.cpen321tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {
    TicTacToeGame game;
    private ImageButton[] buttonArr;
    private boolean inProgress;
    private TextView resultDisplay;
    private Button reset;
    public static final String TAG = "TicTacToe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        resultDisplay = findViewById(R.id.ttt_res);

        reset = findViewById(R.id.ttt_reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inProgress = true;
                game.reset();

                resultDisplay.setText("");

                for(int i = 0; i < buttonArr.length; i++)
                {
                    buttonArr[i].setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        game = new TicTacToeGame();
        inProgress = true;

        buttonArr = new ImageButton[9];
        buttonArr[0] = findViewById(R.id.tt1);
        buttonArr[1] = findViewById(R.id.tt2);
        buttonArr[2] = findViewById(R.id.tt3);
        buttonArr[3] = findViewById(R.id.tt4);
        buttonArr[4] = findViewById(R.id.tt5);
        buttonArr[5] = findViewById(R.id.tt6);
        buttonArr[6] = findViewById(R.id.tt7);
        buttonArr[7] = findViewById(R.id.tt8);
        buttonArr[8] = findViewById(R.id.tt9);

        for(int i = 0; i < buttonArr.length; i++)
        {
            int finalI = i;
            buttonArr[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!inProgress) return;

                    else if(game.playerMove(finalI)) // do nothing if invalid box
                    {
                        //update game board for user's move
                        buttonArr[finalI].setBackgroundColor(Color.BLUE);
                        Log.d(TAG, "TERMINATION");
                        if(game.terminate() == 1)
                        {
                            // display some fancy message and done
                            resultDisplay.setText("You win!");
                            inProgress = false;
                            Log.d(TAG, "User won");
                        } else if (game.terminate() == 3)
                        {
                            //display tie message
                            resultDisplay.setText("It is a tie!");
                            inProgress = false;
                            Log.d(TAG, "Tie condition");
                        }else
                        {
                            int computerMove = game.computerMove();
                            buttonArr[computerMove].setBackgroundColor(Color.RED);
                            //update game board for computer's move
                            if(game.terminate() == 2)
                            {
                                inProgress = false;
                                resultDisplay.setText("You lose, idiot. ");
                            } else if (game.terminate() == 3)
                            {
                                //display message for a tie
                                resultDisplay.setText("It is a tie!");
                            }
                            //no one wins, so just leave.
                        }
                    }
                }
            });
        }
    }
}