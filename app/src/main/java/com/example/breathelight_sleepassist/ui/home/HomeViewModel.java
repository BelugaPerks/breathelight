package com.example.breathelight_sleepassist.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<Drawable> colourButtonBackground;
    private MutableLiveData<Integer> startingBPM;


    public HomeViewModel() {
        colourButtonBackground = new MutableLiveData<>();
        colourButtonBackground.setValue(new ColorDrawable(Color.parseColor("#ff0000")));

        startingBPM = new MutableLiveData<>();
        startingBPM.setValue(11);
    }

    public void setColourButtonBackground(Drawable colourButton){
        this.colourButtonBackground.setValue(colourButton);
    }

    public void setStartingBPM(Integer newValue){
        this.startingBPM.setValue(newValue);
    }

    public LiveData<Drawable> getColourButtonBackground() {
        return colourButtonBackground;
    }

    public LiveData<Integer> getStartingBPM() {
        return startingBPM;
    }
}