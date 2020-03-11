package com.example.breathelight_sleepassist.ui.light;

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
import com.example.breathelight_sleepassist.R;
import java.util.ArrayList;
import java.util.List;

public class LightPulse extends Activity {
    public LightPulse(){

    }

    //Hides the status bar to allow true black background
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.light_pulse);

        //prevent the screen from automatically locking before the light pulse duration is over
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        hideStatusBar();

        //Enable stop button
        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Get data passed to LightPulse activity from home UI
        Intent intent = getIntent();
        //Set background to selected colour
        final ImageView lightPulseImage = findViewById(R.id.image_pulsing_light);
        lightPulseImage.setBackgroundColor(intent.getIntExtra("colour", 0));


        //Get total duration, start breath duration, and goal breath duration in milliseconds.
        int duration = Integer.valueOf(intent.getStringExtra("duration").substring(0,2))*60000;
        Integer startBreathDuration = 60000/intent.getIntExtra("startBPM", 0);
        Integer goalBreathDuration = 60000/intent.getIntExtra("goalBPM", 0);

        //Calculate difference in milliseconds between start and goal duration.
        Integer BreathDelta = goalBreathDuration - startBreathDuration;
        //Calculate the average breath length when moving from start to goal. This is just the midpoint.
        int averageBreathDuration = (startBreathDuration + goalBreathDuration)/2;

        Log.i("debug", "onCreate: Start Breath duration: " + startBreathDuration + " Goal Breath duration: " + goalBreathDuration + " Breath Delta: " + BreathDelta + " Average Breath DUration: " + averageBreathDuration + " total duration: " + duration);

        //Transition period is 5 mins, need to calculate the number of breaths in 5 mins if all breaths were at average
        Integer breathsInFiveMinutesAverage = 300000/averageBreathDuration;

        Log.i("debug", "onCreate: Average breaths in 5 minutes: " + breathsInFiveMinutesAverage);

        //Calculate how much to decrease breath duration by each time, in order to be consistent and move to goal breath duration in 5 mins
        Integer breathDurationShift = BreathDelta/breathsInFiveMinutesAverage;

        Log.i("debug", "onCreate: Breath duration shift: " + breathDurationShift);

        //Instantiate animatorSet for playing sequential animators
        AnimatorSet pulseSet = new AnimatorSet();
        //Instantiate animator list to store pulsing light animators
        List<Animator> animations = new ArrayList<>();

        //Integer for tracking how long the transition set of animators takes in milliseconds
        Integer totalTimeForFirstAnimations = 0;

        //Mutable breath duration integer for transition from start to goal BPM in for loop
        int breathDuration = startBreathDuration;

        //For loop to create the initial set of animators for the transition between start and goal BPM, over 5 minutes
        for(int i = 0; i<breathsInFiveMinutesAverage; i++){
            Log.i("debug", "onCreate: " + breathDuration);
            Log.i("debug", "onCreate colour is: " + intent.getIntExtra("colour", 0));
            //Create the animator to fade in and out once and add it to the list
            final ObjectAnimator fadeAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
                    .setDuration(breathDuration);
            fadeAnimator.setRepeatCount(1);
            fadeAnimator.setRepeatMode(ValueAnimator.REVERSE);
            animations.add(fadeAnimator);
            //update the duration for the next animation by adding on the previously calculated shift
            breathDuration = breathDuration + breathDurationShift;
            //update total time for transition animations
            totalTimeForFirstAnimations = totalTimeForFirstAnimations + breathDuration;
        }
        Log.i("debug", "onCreate: Total time for first set of animations: " + totalTimeForFirstAnimations);
        //Calculate remaining time after transition is done, for debugging
        int remainingDuration = duration-totalTimeForFirstAnimations;
        //Calculate how many breaths can be done in the remaining time at the goal breath duration
        int numberOfRepeatsRemaining = remainingDuration/goalBreathDuration;

        Log.i("debug", "onCreate: remaining duration: " + remainingDuration + " Number of repeats: " + numberOfRepeatsRemaining);
        Log.i("debug", "onCreate: GoalBPM: " + goalBreathDuration);
        //Ensure the number of remaining repeats is odd, to prevent ending with light permanently on
        if(numberOfRepeatsRemaining%2 == 0){
            numberOfRepeatsRemaining=numberOfRepeatsRemaining+1;
        }
        Log.i("debug", "onCreate: remaining duration: " + remainingDuration + " Number of repeats: " + numberOfRepeatsRemaining);

        //Create animator which repeats for the remaining number of breaths, with 10 extra added to allow the screen to automatically lock before the process is finished
        final ObjectAnimator fadeAnimator = ObjectAnimator
                .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
                .setDuration(goalBreathDuration);
        fadeAnimator.setRepeatCount(numberOfRepeatsRemaining+10);
        fadeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animations.add(fadeAnimator);

        Log.i("debug", "onCreate: animations list size: " + animations.size());

        //Calculate the total time of animations in milliseconds, for debugging
        int totalAnimationTime = totalTimeForFirstAnimations + (numberOfRepeatsRemaining*goalBreathDuration);

        Log.i("debug", "onCreate: animations total time in milliseconds: " + totalAnimationTime);

        //After the chosen duration is over, allow the screen to lock.
        final Handler screenOffHandler = new Handler();
        screenOffHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("debug", "times up");
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
        }, 10);

        //Add the animator list to the animatorSet, and play them sequentially
        pulseSet.playSequentially(animations);
        pulseSet.start();




    }
}
