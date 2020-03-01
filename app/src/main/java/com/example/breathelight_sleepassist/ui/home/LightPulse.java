package com.example.breathelight_sleepassist.ui.home;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.example.breathelight_sleepassist.R;

import java.util.ArrayList;
import java.util.List;

public class LightPulse extends Activity {
    public LightPulse(){

    }
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
        hideStatusBar();

        setContentView(R.layout.light_pulse);

        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        ImageView lightPulseImage = findViewById(R.id.image_pulsing_light);
        lightPulseImage.setBackgroundColor(intent.getIntExtra("colour", 0));
        final Animation fadeLoop = AnimationUtils.loadAnimation( this.getApplicationContext(), R.anim.fade_loop);
        //final ObjectAnimator fade = (ObjectAnimator) AnimatorInflater.loadAnimator(this.getApplicationContext(), R.animator.fade);
        Integer duration = Integer.valueOf(intent.getStringExtra("duration").substring(0,2));
        Integer startBPMDuration = 60000/intent.getIntExtra("startBPM", 0);
        Integer goalBPMDuration = 60000/intent.getIntExtra("goalBPM", 0);

        Integer BPMDelta = goalBPMDuration - startBPMDuration;
        Integer averageBPM = (startBPMDuration + goalBPMDuration)/2;

        Log.i("debug", "onCreate: Start BPM duration: " + startBPMDuration + " Goal BPM duration: " + goalBPMDuration + " DPM Delta: " + BPMDelta + " Average BPM: " + averageBPM);

        Integer breathsInFiveMinutesAverage = 300000/averageBPM;

        Log.i("debug", "onCreate: Average breaths in 5 minutes: " + breathsInFiveMinutesAverage);

        Integer breathDurationShift = BPMDelta/breathsInFiveMinutesAverage;

        Log.i("debug", "onCreate: Breath duration shift: " + breathDurationShift);

        AnimatorSet pulseSet = new AnimatorSet();
        List<Animator> animations = new ArrayList<>();

        Integer totaltime = 0;

        for(int i = 0; i<breathsInFiveMinutesAverage; i++){
            Log.i("debug", "onCreate: " + startBPMDuration);
            final ObjectAnimator fadeAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
                    .setDuration(startBPMDuration);
            animations.add(fadeAnimator);
            totaltime = totaltime + startBPMDuration;
            startBPMDuration = startBPMDuration + breathDurationShift;
        }
        Log.i("debug", "onCreate: Total time for first set of animations: " + totaltime);




//        Integer BPMDurationIncrease = (goalBPMDuration-startBPMDuration)/4;
//
//        //fade.setTarget(lightPulseImage);
//        for(int i = 0; i<5; i++){
//            Log.i("debug", "onCreate: " + startBPMDuration + " " + goalBPMDuration + " " + BPMDurationIncrease);
////            fadeLoop.setDuration(startBPMDuration);
////            fadeLoop.setRepeatCount(6000/BPMDurationIncrease);
////            lightPulseImage.startAnimation(fadeLoop);
////            fade.setDuration(startBPMDuration);
////            fade.setRepeatCount(6000/BPMDurationIncrease);
////            fade.setTarget(lightPulseImage);
////            pulseSet.playSequentially(fade);
////            pulseSet.start();
//            final ObjectAnimator fadeAnimator = ObjectAnimator
//                    .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
//                    .setDuration(startBPMDuration);
//            fadeAnimator.setRepeatCount(6000/BPMDurationIncrease);
//            fadeAnimator.setRepeatMode(ValueAnimator.REVERSE);
//            animations.add(fadeAnimator);
//
//            startBPMDuration = startBPMDuration + BPMDurationIncrease;
//        }
        pulseSet.playSequentially(animations);




    }
}
