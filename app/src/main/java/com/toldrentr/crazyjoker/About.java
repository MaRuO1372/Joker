package com.toldrentr.crazyjoker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btn = findViewById(R.id.button2);
        tv = findViewById(R.id.tv_next);

        tv.setOnClickListener(v -> onBackPressed());

        btn.setText("is an android quiz game based on Java. My first android application. The idea of it is to improve quick math skills, to train user's brain to solve math problems quickly and easily - by looking for patterns." +
                "Features!\n" +
                "User friendly colorful UI\n" +
                "Robust settings that lets user customize pretty much everything.\n" +
                "Randomly generated math problems with patterns - where user use their common sense to solve not math skills.\n" +
                "Fitting sound tracks to help user concentrate more.\n" +
                "Dark mode\n" +
                "3 different game modes\n" +
                "Ads" +
                "The homepage page has a nice animated background with a soothing soundtrack playing in the background to give user a very chill vibe, to calm their brain. There are three game modes.");

    }
}