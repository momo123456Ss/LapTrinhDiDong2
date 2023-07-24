package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_player1;
    TextView tv_player2;
    TextView tv_player1_score;
    TextView tv_player2_score,tv_who_win;
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_reset;
    private int p1_count, p2_count, round_count;
    boolean activePlayer;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}
            , {0, 3, 6}, {1, 4, 7}, {2, 5, 8}
            , {0, 4, 8}, {2, 4, 6}
    };

    private Button[] buttons = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_player1 = (TextView) findViewById(R.id.tv_player1);
        tv_player2 = (TextView) findViewById(R.id.tv_player2);
        tv_player1_score = (TextView) findViewById(R.id.tv_player1_score);
        tv_player2_score = (TextView) findViewById(R.id.tv_player2_score);
        tv_who_win = findViewById(R.id.tv_who_win);
        btn_reset = findViewById(R.id.btn_reset);
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        p1_count = 0;
        p2_count = 0;
        round_count = 0;
        activePlayer = true;



    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        String buttonIDS = view.getResources().getResourceEntryName(view.getId());
        int gamestater = Integer.parseInt(buttonIDS.substring(buttonIDS.length() - 1, buttonIDS.length()));
        if (activePlayer) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gamestater] = 0;

        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gamestater] = 1;

        }
        round_count++;
        if (checkWhoWin()) {
            if (activePlayer) {
                p1_count++;
                updatePlayerScore();
                Toast.makeText(this, "Player 1 WON", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                p2_count++;
                updatePlayerScore();
                Toast.makeText(this, "Player 2 WON", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if (round_count == 9) {
            Toast.makeText(this, "Player 2 WON", Toast.LENGTH_SHORT).show();
            playAgain();
        }
        else {
            activePlayer = !activePlayer;
        }

        if(p1_count > p2_count){
            tv_who_win.setText("Player 1 Winning");
        }else if (p1_count < p2_count){
            tv_who_win.setText("Player 2 Winning");
        }else {
            tv_who_win.setText("");
        }
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                p1_count = 0;
                p2_count = 0;
                tv_who_win.setText("");
                updatePlayerScore();
                Toast.makeText(getApplicationContext(), "reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkWhoWin() {
        boolean winnerWho = false;
        for (int[] winningPosition : winningPosition) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                winnerWho = true;
            }
        }
        return winnerWho;
    }

    public void updatePlayerScore() {
        tv_player1_score.setText(Integer.toString(p1_count));
        tv_player2_score.setText(Integer.toString(p2_count));
    }

    public void playAgain() {
        round_count = 0;
        activePlayer = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}