package com.belugaperks.breathelight_sleepassist.ui.light;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

public class LightPulseViewModel  extends AndroidViewModel {


    public LightPulseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     *
     * @param durationInMinutesAsString The chosen runtime duration in minutes, as a String
     * @return The duration runtime in milliseconds, as an int
     */
    public int getDurationInMilliseconds(String durationInMinutesAsString){
        return Integer.parseInt(durationInMinutesAsString)*60000;
    }

    /**
     *
     * @param breathsPerMinute The chosen number of Breaths Per Minute (BPM)
     * @return The duration of each of those breaths, in milliseconds
     */
    public int getBreathDurationInMillisecondsFromBPM(int breathsPerMinute){
        return 60000/breathsPerMinute;
    }

    /**
     *
     * @param startBreathDuration The duration of the first breath, in milliseconds
     * @param goalBreathDuration The duration of the goal breath, to be reached after 5 minutes and then maintained for the full runtime duration, in milliseconds.
     * @return The number of breaths in 5 minutes, if all breaths were at the average breath length. This is how many breaths we want in 5 minutes if we start at the startBreathDuration and move to the goalBreathDuration over time
     */
    public int getNumberOfBreathsInFiveMinutes(int startBreathDuration, int goalBreathDuration){
        //Calculate the average breath length when moving from start to goal. This is just the length of the breath taken at the halfway point.
        int averageBreathDuration = (startBreathDuration + goalBreathDuration)/2;
        //Transition period is 5 minutes, need to calculate the number of breaths in 5 mins if all breaths were at the average breath length
        return 300000/averageBreathDuration;
    }

    /**
     *
     * @param startBreathDuration The duration of the first breath, in milliseconds
     * @param goalBreathDuration The duration of the goal breath, to be reached after 5 minutes and then maintained for the full runtime duration, in milliseconds.
     * @param totalBreathsInFiveMinutes The number of breaths in 5 minutes, if all breaths were at the average breath length.
     * @return The length of time each breath should be incremented by, in order for the breath length to reach the goal breath length after 5 minutes.
     */
    public int getBreathDurationShiftForTransitionPeriod(int startBreathDuration, int goalBreathDuration, int totalBreathsInFiveMinutes){

        //Calculate difference in milliseconds between start and goal duration.
        int BreathDelta = goalBreathDuration - startBreathDuration;

        //Calculate how much to decrease breath duration by each time, in order to be consistent and move to goal breath duration in 5 minutes. Dividing the total delta by the number of breaths gives us this value.
        return BreathDelta/totalBreathsInFiveMinutes;
    }

    public List<Animator> concatAnimationListWithRemainingAnimations(AnimationsListAndRemainingRepeatsValue animationsAndRepeats, int goalBreathDuration, ImageView lightPulseImage){

        List<Animator> animations = animationsAndRepeats.getAnimations();
        int numberOfRepeatsRemaining = animationsAndRepeats.getNumberOfRepeatsRemaining();

        //Create breathe in and out animators which repeat for the remaining number of breaths, with 5 extra of each added to allow the screen to automatically lock before the process is finished
        for(int i = 0; i<(numberOfRepeatsRemaining)+5; i++){
            final ObjectAnimator fadeInAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
                    .setDuration(Math.round(goalBreathDuration*0.8));
            animations.add(fadeInAnimator);
            final ObjectAnimator fadeOutAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 1f, 0f)
                    .setDuration(Math.round(goalBreathDuration*1.2));
            animations.add(fadeOutAnimator);
        }
        return animations;

    }

    public AnimationsListAndRemainingRepeatsValue createInitialTransitionAnimationSet(int totalBreathsInFiveMinutes, ImageView lightPulseImage, int breathDurationShift, int totalDuration, int startBreathDuration, int goalBreathDuration){

        //Instantiate animator list to store pulsing light animators
        List<Animator> animations = new ArrayList<>();

        // Integer for tracking how long the transition set of animators takes in milliseconds
        int totalTimeForFirstAnimations = 0;

        int currentBreathDuration = startBreathDuration;

        // For loop to create the initial set of animators for the transition between start and goal BPM, over 5 minutes
        for(int i = 0; i<totalBreathsInFiveMinutes; i++){
            // Create the animators to fade in and out one time and add them to the list. Exhale/inhale duration is split 60/40
            final ObjectAnimator fadeInAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 0f, 1f)
                    // Fade in is set to duration*0.8, a 20% reduction to make it take up 40% of a single breath in/out cycle
                    .setDuration(Math.round(currentBreathDuration*0.8));
            animations.add(fadeInAnimator);
            final ObjectAnimator fadeOutAnimator = ObjectAnimator
                    .ofFloat(lightPulseImage, View.ALPHA, 1f, 0f)
                    // Fade in is set to duration*1.2, a 20% increase to make it take up 60% of a single breath in/out cycle
                    .setDuration(Math.round(currentBreathDuration*1.2));
            animations.add(fadeOutAnimator);
            // Update the duration for the next animation by adding on the previously calculated shift
            currentBreathDuration = currentBreathDuration + breathDurationShift;
            // Update total time for transition animations
            totalTimeForFirstAnimations = totalTimeForFirstAnimations + currentBreathDuration;
        }
        // Calculate remaining time after transition is done
        int remainingDuration = totalDuration-totalTimeForFirstAnimations;
        // Calculate how many breaths can be done in the remaining time at the goal breath duration
        int numberOfRepeatsRemaining = remainingDuration/goalBreathDuration;

        return new AnimationsListAndRemainingRepeatsValue(numberOfRepeatsRemaining, animations);
    }

    public List<Animator> generateAnimatorList(Intent intent, ImageView lightPulseImage){
        // Get total duration, start breath duration, and goal breath duration in milliseconds.
        int duration = this.getDurationInMilliseconds(intent.getStringExtra("duration").substring(0,2));
        int startBreathDuration = this.getBreathDurationInMillisecondsFromBPM(intent.getIntExtra("startBPM", 0));
        int goalBreathDuration = this.getBreathDurationInMillisecondsFromBPM(intent.getIntExtra("goalBPM", 0));

        // Get the number of breaths in the first Five Minutes
        int totalBreathsInFiveMinutes = this.getNumberOfBreathsInFiveMinutes(startBreathDuration, goalBreathDuration);

        // Get the amount of time (in milliseconds) to increment each breath by for the first 5 minutes, so that the goal BPM is achieved on time.
        int breathDurationShift = this.getBreathDurationShiftForTransitionPeriod(startBreathDuration, goalBreathDuration, totalBreathsInFiveMinutes);

        // Get the initial animations list for the transition period, as well as the number of breaths that can fit in the time left after these initial animations.
        AnimationsListAndRemainingRepeatsValue initialAnimationsAndTime = this.createInitialTransitionAnimationSet(totalBreathsInFiveMinutes, lightPulseImage, breathDurationShift, duration, startBreathDuration, goalBreathDuration);

        // Add on the rest of the animations, a set of breath 'pulses' of the same duration to fill up the remaining time, and return it.
        return this.concatAnimationListWithRemainingAnimations(initialAnimationsAndTime, goalBreathDuration, lightPulseImage);
    }



}
