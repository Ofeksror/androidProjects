package com.example.tictactoeai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button[][] brr = new Button[3][3];
    Button resetButton;
    TextView winnerText;

    CharSequence winner;
    boolean turn = false;

    /* TODO:
        if playRandom == false: => play against smart AI
        if playRandom == true: => computer generates random plays
     */
    boolean playRandom = false;

    int turnsCount = 0;

    long waitTime = 250; // in milliseconds

    String player;

    // =============================== //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGame();
        newGame();
    }

    public void getButtons()
    {
        Button b1 = findViewById(R.id.btn1);
        Button b2 = findViewById(R.id.btn2);
        Button b3 = findViewById(R.id.btn3);
        Button b4 = findViewById(R.id.btn4);
        Button b5 = findViewById(R.id.btn5);
        Button b6 = findViewById(R.id.btn6);
        Button b7 = findViewById(R.id.btn7);
        Button b8 = findViewById(R.id.btn8);
        Button b9 = findViewById(R.id.btn9);

        brr[0][0] = b1;
        brr[0][1] = b2;
        brr[0][2] = b3;
        brr[1][0] = b4;
        brr[1][1] = b5;
        brr[1][2] = b6;
        brr[2][0] = b7;
        brr[2][1] = b8;
        brr[2][2] = b9;

        resetButton = findViewById(R.id.resetButton);
        winnerText = findViewById(R.id.winnerDeclaration);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });
    }

    public void setupGame()
    {
        getButtons();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // brr[i][j].setBackgroundColor(Integer.parseInt("#edd955"));

                brr[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button btn = (Button) view;

                        // Handle choice
                        if (turn) {
                            btn.setText("O");
                        }
                        else {
                            btn.setText("X");
                        }

                        btn.setClickable(false);

                        // Prepare next choice
                        turn = !turn;
                        turnsCount++;

                        // Check for endgame scenario
                        if (checkForWin())
                        {
                            endGame();
                        }
                        if (turnsCount == 9)
                        {
                            drawGame();
                        }

                        // Play against the computer
                        if (turnsCount % 2 != 0)
                        {
                            // waitMilliseconds(waitTime);
                            // Log.d("Waited", "Waited");

                            if (playRandom) randomPick();
                            else {
                                playAI();
                            }
                        }
                    }
                });
            }
        }

        newGame();
    }

    public void newGame() {
        resetButton.setVisibility(View.INVISIBLE);
        resetButton.setClickable(false);

        winnerText.setVisibility(View.INVISIBLE);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                brr[i][j].setClickable(true);
                brr[i][j].setText("");
            }
        }

        player = (turn) ? "O" : "X";
    }

    public boolean checkForWin()
    {
        // Check for single rows wins
        for (int r = 0; r < 3; r++)
        {
            CharSequence middle = brr[r][1].getText();

            if (middle == "")
            {
                continue;
            }

            if (brr[r][0].getText() == middle && middle == brr[r][2].getText())
            {
                winner = middle;
                return true;
            }
        }

        // Check for single columns wins
        for (int c = 0; c < 3; c++)
        {
            CharSequence middle = brr[1][c].getText();

            if (middle == "")
            {
                continue;
            }

            if (brr[0][c].getText() == middle && middle == brr[2][c].getText())
            {
                winner = middle;
                return true;
            }
        }

        // Check for diagonal wins
        CharSequence middle = brr[1][1].getText();
        if (middle != "")
        {
            if (brr[0][0].getText() == middle && brr[2][2].getText() == middle) {
                winner = middle;
                return true;
            } else if (brr[0][2].getText() == middle && brr[2][0].getText() == middle) {
                winner = middle;
                return true;
            }
        }

        return false;
    }

    public void endGame()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                brr[i][j].setClickable(false);
            }
        }

        resetButton.setVisibility(View.VISIBLE);
        resetButton.setClickable(true);

        winnerText.setVisibility(View.VISIBLE);
        String text = "Player " + winner + " wins!";
        winnerText.setText(text);

        turnsCount = 0;
    }

    public void drawGame()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                brr[i][j].setClickable(false);
            }
        }

        resetButton.setVisibility(View.VISIBLE);
        resetButton.setClickable(true);

        winnerText.setVisibility(View.VISIBLE);
        String text = "Draw!";
        winnerText.setText(text);

        turnsCount = 0;
    }

    public void randomPick()
    {
        int r, c;
        do
        {
            r = (int) (Math.random() * 3);
            c = (int) (Math.random() * 3);
        } while (brr[r][c].getText() != "");

        brr[r][c].performClick();
    }

    public void playAI()
    {
        String self = (turn) ? "O" : "X";

        Log.d("AI", "AI PLAY / TURN ahead ::: " + self + " --- ");

        ArrayList<Integer> threatsRows = new ArrayList<Integer>(0);
        ArrayList<Integer> threatsColumns = new ArrayList<Integer>(0);
        int[] threatsDiagonal1 = new int[] {9, 9};
        int[] threatsDiagonal2 = new int[] {9, 9};

        // Check for threats\advantages in rows
        for (int r = 0; r < 3; r++)
        {
            int countThreats = 0;
            int countAdvantages = 0;

            for (int c = 0; c < 3; c++)
            {
                if (brr[r][c].getText().toString().equals(self))
                    countAdvantages++;
                else if (brr[r][c].getText().toString().equals("")) {}
                else
                    countThreats++;
            }

            if (countAdvantages == 2)
            {
                for (int c = 0; c < 3; c++)
                {
                    if (brr[r][c].getText() == "")
                    {
                        brr[r][c].performClick();
                        Log.d("Played on columns", "Played on: (r):" + r + ", (c): " + c + "       ");
                        return;
                    }
                }
            }
            else if (countThreats == 2)
            {
                threatsRows.add(r);
            }
        }

        // Check for threats\advantages in columns
        for (int c = 0; c < 3; c++)
        {
            int countThreats = 0;
            int countAdvantages = 0;

            for (int r = 0; r < 3; r++)
            {
                if (brr[r][c].getText().toString().equals(self))
                    countAdvantages++;
                else if (brr[r][c].getText().toString().equals("")) {}
                else
                    countThreats++;
            }

            if (countAdvantages == 2)
            {
                for (int r = 0; r < 3; r++)
                {
                    if (brr[r][c].getText() == "")
                    {
                        brr[r][c].performClick();
                        Log.d("Played on rows", "Played on: (r):" + r + ", (c): " + c + "       ");
                        return;
                    }
                }
            }
            else if (countThreats == 2)
            {
                threatsColumns.add(c);
            }
        }

        // Check for diagonal threats\advantages

        int countAdvantages1 = 0;
        int countThreats1 = 0;

        int countAdvantages2 = 0;
        int countThreats2 = 0;

        for (int r = 0, c = 2; r < 3; r++, c--)
        {
            if (brr[r][c].getText().toString().equals(self))
            {
                countAdvantages1++;
            }
            else if (brr[r][c].getText().toString().equals("")) {}
            else
                countThreats1++;

            if (brr[r][2-c].getText().toString().equals(self))
                countAdvantages2++;
            else if (brr[r][2-c].getText().toString().equals("")) {}
            else
                countThreats2++;
        }

        if (countAdvantages1 == 2)
        {
            for (int r = 0, c = 2; r < 3; r++, c--)
            {
                if (brr[r][c].getText().toString().equals(""))
                {
                    brr[r][c].performClick();
                    Log.d("Played on diagonal 1", "Played on: (r):" + r + ", (c): " + c + "       ");
                    return;
                }
            }
        }

        if (countAdvantages2 == 2)
        {
            for (int r = 0, c = 0; r < 3; r++, c++)
            {
                if (brr[r][c].getText().toString().equals(""))
                {
                    brr[r][c].performClick();
                    Log.d("Played on diagonal 2", "Played on: (r):" + r + ", (c): " + c + "       ");
                    return;
                }
            }
        }

        if (countThreats1 == 2)
        {
            for (int r = 0, c = 2; r < 3; r++, c--)
            {
                if (brr[r][c].getText().toString().equals(""))
                {
                    brr[r][c].performClick();
                    Log.d("Played on diagonal 1: ", "Played against threat on (r):" + r + ", (c): " + c + "     ");
                    return;
                }
            }
        }

        if (countThreats2 == 2)
        {
            for (int r = 0, c = 0; r < 3; r++, c++)
            {
                if (brr[r][c].getText().toString().equals(""))
                {
                    brr[r][c].performClick();
                    Log.d("Played on diagonal 2: ", "Played against threat on (r):" + r + ", (c): " + c + "     ");
                    return;
                }
            }
        }

        // Encounter threats
        if (threatsRows.size() != 0)
        {
            for (int i = 0; i < threatsRows.size(); i++)
            {
                int r = threatsRows.get(i);

                for (int c = 0; c < 3; c++)
                {
                    if (brr[r][c].getText().toString().equals("")) {
                        Log.d("AI Play", " --- against threat on rows --- ");
                        brr[r][c].performClick();
                        return;
                    }
                }
            }
        }

        if (threatsColumns.size() != 0)
        {
            for (int i = 0; i < threatsColumns.size(); i++)
            {
                int c = threatsColumns.get(i);

                for (int r = 0; r < 3; r++)
                {
                    if (brr[r][c].getText().toString().equals("")) {
                        Log.d("AI Play", " --- against threat on columns --- ");
                        brr[r][c].performClick();
                        return;
                    }
                }
            }
        }

        // Perform a random click:
        int r = (int)(Math.random()*3);
        int c = (int)(Math.random()*3);

        while (!brr[r][c].getText().toString().equals(""))
        {
            r = (int)(Math.random()*3);
            c = (int)(Math.random()*3);
        }

        brr[r][c].performClick();
        Log.d("Played on random", "Played on: (r):" + r + ", (c): " + c + "       ");
        return;

    }

    public void waitMilliseconds(long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Log.d("Wait interruption", " interrupted");
            // Thread.currentThread().interrupt();
        }
    }

    /*
            try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            Log.d("Interruption:" , e.toString());
        }
     */
}