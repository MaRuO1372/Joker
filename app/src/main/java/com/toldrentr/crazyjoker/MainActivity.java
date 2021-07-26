package com.toldrentr.crazyjoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.toldrentr.crazyjoker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void about(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void policy(View view) {
        Intent intentPriv = new Intent(this, PrivacyPolicyActivity.class);
        intentPriv.putExtra("privpo", "privpol");
        startActivity(intentPriv);
    }

    public void play(View view) {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
        finish();
    }
}