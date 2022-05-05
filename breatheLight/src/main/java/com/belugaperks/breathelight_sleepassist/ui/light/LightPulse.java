package com.belugaperks.breathelight_sleepassist.ui.light;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProvider;

import com.belugaperks.breathelight_sleepassist.R;
import java.util.List;

public class LightPulse extends AppCompatActivity {


    public LightPulse(){

    }

    // Hides the status bar to allow true black background
    public void hideStatusBar() {
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
    }


    LightPulseViewModel lightPulseViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){



        super.onCreate(savedInstanceState);


        // Make sure animator scale is correctly set
        // Get duration scale from the global settings.
        float durationScale = Settings.Global.getFloat(this.getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1f);
        // If global duration scale is not 1 (default), try to override it for this activity.
        if (durationScale != 1) {
            try {
                ValueAnimator.class.getMethod("setDurationScale", float.class).invoke(null, 1f);
                durationScale = 1f;
            } catch (Throwable t) {
                // Something went wrong, alert the user.
                AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                        .setTitle("Warning - Light Pulse will not work on your device")
                        //set message
                        .setMessage("The 'Animator Duration Scale' has been changed from the default value of 1. BreatheLight attempted to override this setting, but was not able to do so. For the app to work properly you will need to go to your phone's settings, then 'Developer Settings', and set 'Animator Duration Scale' to 1.")
                        //set return button
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                finish();
                            }
                        })
                        //set close warning button
                        .setNegativeButton("Close Warning", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when button is clicked
                                Toast.makeText(getApplicationContext(),"BreatheLight may not work properly until 'Animator Duration Scale' setting is updated!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        }


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

                //End the activity once the screen has been given time to lock
                final Handler screenOffHandler = new Handler();
                screenOffHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("debug", "Exiting light pulse activity");

                        // Create/update persistent count var
                        SharedPreferences prefs = getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE );
                        int count = prefs.getInt("activityCount", 0);
                        count++;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("activityCount", count );
                        editor.apply();
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
