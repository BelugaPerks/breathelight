package com.belugaperks.breathelight_sleepassist.ui.light;

import android.animation.Animator;

import java.util.List;

public class AnimationsListAndRemainingRepeatsValue {

    public int numberOfRepeatsRemaining;
    public List<Animator> animations;

    public AnimationsListAndRemainingRepeatsValue(int numberOfRepeatsRemaining, List<Animator> animations) {
        this.numberOfRepeatsRemaining = numberOfRepeatsRemaining;
        this.animations = animations;
    }


    public int getNumberOfRepeatsRemaining() {
        return numberOfRepeatsRemaining;
    }

    public void setNumberOfRepeatsRemaining(int numberOfRepeatsRemaining) {
        this.numberOfRepeatsRemaining = numberOfRepeatsRemaining;
    }

    public List<Animator> getAnimations() {
        return animations;
    }

    public void setAnimations(List<Animator> animations) {
        this.animations = animations;
    }

    @Override
    public String toString() {
        return "AnimationsListAndRemainingRepeatsValue{" +
                "numberOfRepeatsRemaining=" + numberOfRepeatsRemaining +
                ", animations=" + animations +
                '}';
    }
}
