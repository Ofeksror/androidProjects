package com.example.keyboard;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Keyboard extends LinearLayout {
    Context context;
    String s = "";
    TextView preview;

    public boolean isCaps = false;

    public char[][] keyLayoutEn = new char[][] {{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'},
                                                {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'},
                                                {'z', 'x', 'c', 'v', 'b', 'n', 'm'}};

    public char[][] keyLayoutEnCAPS = new char[][] {{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
                                                    {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'},
                                                    {'Z', 'X', 'C', 'V', 'B', 'N', 'M'}};

    public Keyboard(Context context)
    {
        super(context);

        this.context = context;
        this.preview = new TextView(context);

        this.preview.setBackgroundColor(Color.parseColor("#f0f0f0"));
        this.preview.setTextColor(Color.parseColor("#0f0f0f"));
        this.preview.setTextDirection(View.TEXT_DIRECTION_RTL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setOrientation(VERTICAL);
        this.addView(preview);

        LoadKeyboard();
    }

    public void LoadKeyboard()
    {
        Button space = new Button(context);
        Button delete = new Button(context);
        Button caps = new Button(context);

        space.setText(" - ");
        space.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                s += " ";
                preview.setText(s);
            }
        });

        delete.setText("<- Delete");
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.length() > 0)
                {
                    s = s.substring(0, s.length() - 1);
                    preview.setText(s);
                }
            }
        });

        caps.setText("CAPS");
        caps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isCaps = !isCaps;
                Log.d(TAG, "onClick: isCaps: " + isCaps);

                if (isCaps)
                {
                    Log.d(TAG, "loading caps");
                    LoadCaps();
                }
                else
                {
                    Log.d(TAG, "loading lowerCase");
                    LoadEnglish();
                }
            }
        });

        this.addView(space);
        this.addView(delete);
        this.addView(caps);
    }

    public void LoadCaps()
    {
        for (int i = 0; i < 3; i++)
        {
            LinearLayout layout = new LinearLayout(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 100, 0, 0);
            layout.setLayoutParams(layoutParams);

            for (int j = 0; j < keyLayoutEnCAPS[i].length; j++)
            {
                Button key = new Button(context);

                // Key Design
                key.setBackgroundColor(Color.parseColor("#dae7f0"));
                key.setTextColor(Color.parseColor("#12161a"));

                LayoutParams btnParams =new LayoutParams(100,100);
                key.setLayoutParams(btnParams);

                // Set key content
                key.setText(String.valueOf(keyLayoutEnCAPS[i][j]));

                // Add key listener
                key.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // s += keyLayoutEnCAPS[i][j];
                        Button b = (Button)view;

                        s += b.getText().toString();
                        preview.setText(s);
                    }
                });

                // Add button to linear layout
                layout.addView(key);
            }

            this.addView(layout);
        }
    }

    public void LoadEnglish()
    {
        for (int i = 0; i < 3; i++)
        {
            LinearLayout layout = new LinearLayout(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 100, 0, 0);
            layout.setLayoutParams(layoutParams);

            for (int j = 0; j < keyLayoutEn[i].length; j++)
            {
                Button key = new Button(context);

                // Key Design
                key.setBackgroundColor(Color.parseColor("#dae7f0"));
                key.setTextColor(Color.parseColor("#12161a"));

                LayoutParams btnParams = new LayoutParams(100,100);
                key.setLayoutParams(btnParams);

                // Set key content
                key.setText(String.valueOf(keyLayoutEn[i][j]));

                // Add key listener
                key.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // s += keyLayoutEnCAPS[i][j];
                        Button b = (Button)view;

                        s += b.getText().toString();
                        preview.setText(s);
                    }
                });

                // Add button to linear layout
                layout.addView(key);
            }

            this.addView(layout);
        }
    }
}
