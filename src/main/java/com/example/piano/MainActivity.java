package com.example.piano;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/*
class Question {
    private String question;
    private String[] fakeAnswers;
    private int answerIndex;

    public Question(String q, String[] fake, int index) {
        this.question = q;
        this.fakeAnswers = fake;
        this.answerIndex = index;
    }

    public String getQuestion() { return this.question; }
    public String[] getFakeAnswers() { return this.fakeAnswers; }
    public int getAnswerIndex() { return this.answerIndex; }
}
 */


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Button btnB1,btnB2,btnB3,btnB4;
    SoundPool sp;
    int b1;
    int b2;
    int b3;
    int b4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //phase 1 - check which sdk the user has
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            sp=new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
        }
        else
        {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC,1);


        }
        //phase 2 -load files to sp
        b1 = sp.load(this,R.raw.b1,1);
        b2 = sp.load(this,R.raw.b2,1);
        b3 = sp.load(this,R.raw.b3,1);
        b4 = sp.load(this,R.raw.b4,1);


        //phase 3 - referance to buttons
        btnB1=(Button)findViewById(R.id.btnB1);
        btnB1.setOnTouchListener(this);
        btnB2=(Button)findViewById(R.id.btnB2);
        btnB2.setOnTouchListener(this);
        btnB3=(Button)findViewById(R.id.btnB3);
        btnB3.setOnTouchListener(this);
        btnB4=(Button)findViewById(R.id.btnB4);
        btnB4.setOnTouchListener(this);



    }
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        float f = (float) 0.8;


        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            if(v==btnB1)
            {
                sp.play(b1, 1, 1, 0, 0, 1);
            }
            else if(v==btnB2)
            {
                sp.play(b2, 1, 1, 0, 0, 1);
            }
            else if(v==btnB3)
            {
                sp.play(b3, 1, 1, 0, 0, 1);
            }
            else if(v==btnB4) {
                sp.play(b4, 1, 1, 0, 0, 1);
            }
            v.setAlpha(1);
        }
        else if(event.getAction()==MotionEvent.ACTION_DOWN) {
            v.setAlpha(f);
        }
        return true;
    }
}
