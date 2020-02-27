package com.example.breathelight_sleepassist.ui.home;


import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Drawable> colourButtonBackground;
    private final MutableLiveData<Integer> colour;
    private final MutableLiveData<Integer> startingBPM;
    private final MutableLiveData<Integer> goalBPM;


    public HomeViewModel() {
        colourButtonBackground = new MutableLiveData<>();

        colour = new MutableLiveData<>();

        startingBPM = new MutableLiveData<>();
        startingBPM.setValue(11);
        goalBPM = new MutableLiveData<>();
        goalBPM.setValue(6);
    }

    public void setColourButtonBackground(Drawable colourButton){
        this.colourButtonBackground.setValue(colourButton);
    }

    public LiveData<Drawable> getColourButtonBackground() {
        return colourButtonBackground;
    }

    public void setColour(Integer colourValue){
        this.colour.setValue(colourValue);
    }

    public LiveData<Integer> getColour(){
        return colour;
    }

    public void setStartingBPM(Integer newValue){
        this.startingBPM.setValue(newValue);
    }

    public LiveData<Integer> getStartingBPM() {
        return startingBPM;
    }

    public void setGoalBPM(Integer newValue){
        this.goalBPM.setValue(newValue);
    }

    public LiveData<Integer> getGoalBPM() {
        return goalBPM;
    }
}