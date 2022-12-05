package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    // Global variables

    public AppCompatButton colorPreview; // = findViewById(R.id.btnPreviewColor);
    public String color;

    public int pointsCount = 0;
    TextView pointsText;

    public AppCompatButton button1; //  = findViewById(R.id.btnChoice1);
    public AppCompatButton button2; // = findViewById(R.id.btnChoice2);
    public AppCompatButton button3; // = findViewById(R.id.btnChoice3);

    public AppCompatButton buttonArr[];

    public AppCompatButton continueBtn; // = findViewById(R.id.continueButton);

    // ---------------

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsText = (TextView) findViewById(R.id.pointsText);
        colorPreview = (AppCompatButton) findViewById(R.id.btnPreviewColor);

        button1 = (AppCompatButton) findViewById(R.id.btnChoice1);
        button2 = (AppCompatButton) findViewById(R.id.btnChoice2);
        button3 = (AppCompatButton) findViewById(R.id.btnChoice3);
        buttonArr = new AppCompatButton[]{button1, button2, button3};

        continueBtn = (AppCompatButton) findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               SetupGame();
           }
        });

        SetupGame();
    }

    protected static String generateColor() {
        char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        char[] color = new char[6];
        for (int i=0; i < 6; i++)
        {
            int index = (int)(Math.random() * 16);
            color[i] = codes[index];
        }

        String ret = "#" + color[0] + color[1] + color[2] + color[3] + color[4] + color[5];
        // codes = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];

        Log.d("Random color: ", ret);

        return ret;
    }

    public void SetupGame()
    {
        color = generateColor();
        colorPreview.setBackgroundColor(Color.parseColor(color));

        continueBtn.setEnabled(false);
        continueBtn.setVisibility(View.GONE);

        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i].setText(generateColor());
            buttonArr[i].setBackgroundColor(Color.parseColor("#f0f0f0"));

            buttonArr[i].setEnabled(true);

            // Set onclick to default wrong answer onclick event.
            buttonArr[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < buttonArr.length; i++) {
                        String bg = buttonArr[i].getText().toString();
                        buttonArr[i].setBackgroundColor(Color.parseColor(bg));

                        buttonArr[i].setEnabled(false);
                    }

                    // Make all other buttons unclickable
                    continueBtn.setVisibility(View.VISIBLE);
                    continueBtn.setEnabled(true);
                }
            });
        }

        // Set a random correct button
        int correctBtnIndex = (int)(Math.random() * 3);
        buttonArr[correctBtnIndex].setText(color);

        // Set correct button onclick event to correctBtnOnClick
        buttonArr[correctBtnIndex].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                for (int i = 0; i < buttonArr.length; i++)
                {
                    String bg = buttonArr[i].getText().toString();
                    buttonArr[i].setBackgroundColor(Color.parseColor(bg));

                    buttonArr[i].setEnabled(false);
                }

                pointsCount++;
                pointsText.setText("POINTS: " + pointsCount);

                continueBtn.setVisibility(View.VISIBLE);
                continueBtn.setEnabled(true);
            }
        });
    }
}