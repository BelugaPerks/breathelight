package com.belugaperks.breathelight_sleepassist.ui.light;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.belugaperks.breathelight_sleepassist.R;
import java.util.ArrayList;
import java.util.List;

public class LightPulse extends AppCompatActivity {
    public LightPulse(){

    }

    // Hides the status bar to allow true black background
    public void hideStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
        }
    }


    LightPulseViewModel lightPulseViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.light_pulse);

        lightPulseViewModel = new ViewModelProvider(this).get(LightPulseViewModel.class);

        // Prevent the screen from automatically locking before the light pulse duration is over
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Hide the status bar to prevent light clashing
        hideStatusBar();

        // Enable stop button
        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get data passed to LightPulse activity from home UI
        Intent intent = getIntent();

        // Set background to selected colour
        final ImageView lightPulseImage = findViewById(R.id.image_pulsing_light);
        lightPulseImage.setBackgroundColor(intent.getIntExtra("colour", 0));

        // Allow the screen to lock after the selected duration is over.
        final Handler screenOffHandler = new Handler();
        int duration = lightPulseViewModel.getDurationInMilliseconds(intent.getStringExtra("duration").substring(0,2));
        screenOffHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                //End the activity once the screen has  been given time to lock
                final Handler screenOffHandler = new Handler();
                screenOffHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("debug", "Exiting light pulse activity");
                        finish();
                    }
                }, 30000);
            }
        }, duration);

        // Get list of animations to play for pulsing breathing lights.
        List<Animator> animations = lightPulseViewModel.generateAnimatorList(intent, lightPulseImage);

        // Instantiate animatorSet for playing sequential animators
        AnimatorSet pulseSet = new AnimatorSet();

        // Add the animator list to the animatorSet, and play them sequentially
        pulseSet.playSequentially(animations);
        pulseSet.start();




    }
}
