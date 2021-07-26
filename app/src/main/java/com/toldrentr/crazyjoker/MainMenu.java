package com.toldrentr.crazyjoker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class MainMenu extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Button highScoreButton;
    private TextView introText;
    private MediaPlayer mediaPlayer;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Boolean mute;
    int totalCount, length;
    Boolean muteTemp = false;
    SharedPreferences sharedPref;
    ImageButton muteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button quickMathButton, timeTrialsButton, advancedMathButton;
        Animation fromLeftQuickMath, fromLeftTimeTrials, fromLeftAdvanced;
        Boolean darkModePref;
        boolean muteTemp = false;

        quickMathButton = findViewById(R.id.quickMathsButton);
        advancedMathButton = findViewById(R.id.advancedMathButton);
        muteButton = findViewById(R.id.muteButton);

        //Animation for the game mode buttons
        fromLeftQuickMath = AnimationUtils.loadAnimation(this,R.anim.from_left_quick_math);
        quickMathButton.setAnimation(fromLeftQuickMath);
        fromLeftTimeTrials = AnimationUtils.loadAnimation(this,R.anim.from_left_time_trials);
        fromLeftAdvanced = AnimationUtils.loadAnimation(this, R.anim.from_left_advanced);
        advancedMathButton.setAnimation(fromLeftAdvanced);
        PreferenceManager.setDefaultValues(this,R.xml.preference,false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        mute = sharedPref.getBoolean(SettingsActivity.KEY_MUTE_MUSIC,false);

        //Checks for build version - required for background animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(darkModePref) {
                backgroundAnimationDark();
                quickMathButton.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
                advancedMathButton.setBackground(getDrawable(R.drawable.main_menu_button_rd_dk));
            }else{
                backgroundAnimation();
            }
        }



        //Initializes music
        mediaPlayer = MediaPlayer.create(this,R.raw.up_your_street);
        mediaPlayer.setLooping(true);
        if(!mute) {
            mediaPlayer.start();
            muteButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }else{
            muteButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    sharedPref.edit().putBoolean(SettingsActivity.KEY_MUTE_MUSIC,true).apply();
                    muteButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }else{
                    mediaPlayer.start();
                    sharedPref.edit().putBoolean(SettingsActivity.KEY_MUTE_MUSIC,false).apply();
                    muteButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying() && !mute){
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!mediaPlayer.isPlaying() && !mute) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void openAdvanced(View view){
        startActivity(new Intent(this, SinterActLoadingScreen.class));
    }
    public void openQuickMaths(View view){
        startActivity(new Intent(this,QuickMathLoadingScreen.class));
    }
    public void openSettings(View view){
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void backgroundAnimation(){
        ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
        constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation));
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();
    }
    public void backgroundAnimationDark(){
        ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
        constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.dark));
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();
    }
    //Required to implement dark mode changes without restarting the app.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SettingsActivity.KEY_DARK_MODE_SWITCH) || key.equals(SettingsActivity.KEY_MUTE_MUSIC)){
            recreate();
        }
    }
}
