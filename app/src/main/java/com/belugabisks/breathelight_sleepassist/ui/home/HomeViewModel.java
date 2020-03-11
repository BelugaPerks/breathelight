package com.belugabisks.breathelight_sleepassist.ui.home;


import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> colourButtonBackground;
    private final MutableLiveData<Integer> colour;
    private final MutableLiveData<Integer> startingBPM;
    private final MutableLiveData<Integer> goalBPM;
    private final MutableLiveData<Integer> durationSpinnerPosition;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;


    public HomeViewModel(Application application) {
        super(application);

        colour = new MutableLiveData<>();
        colourButtonBackground = new MutableLiveData<>();

        startingBPM = new MutableLiveData<>();
        goalBPM = new MutableLiveData<>();
        durationSpinnerPosition = new MutableLiveData<>();


        pref = this.getApplication().getSharedPreferences("BreathLightValues", 0);
        editor = pref.edit();

    }


    public void setColour(Integer colourValue){
        this.colour.setValue(colourValue);
        editor.putInt("colour", colourValue);
        editor.commit();
    }

    public LiveData<Integer> getColour(){
        return colour;
    }

    public void setDurationSpinnerPosition(Integer spinnerPositonValue){
        this.durationSpinnerPosition.setValue(spinnerPositonValue);
        editor.putInt("spinnerPosition", spinnerPositonValue);
        editor.commit();
    }

    public LiveData<Integer> getDurationSpinnerPositon(){
        return durationSpinnerPosition;
    }

    public void setColourButtonBackground(String colourButtonName){
        this.colourButtonBackground.setValue(colourButtonName);
        editor.putString("colourButtonBackground", colourButtonName);
        editor.commit();
    }

    public LiveData<String> getColourButtonBackground(){
        return colourButtonBackground;
    }

    public void setStartingBPM(Integer newValue){
        this.startingBPM.setValue(newValue);
        editor.putInt("startBPM", newValue);
        editor.commit();
    }

    public LiveData<Integer> getStartingBPM() {
        return startingBPM;
    }

    public void setGoalBPM(Integer newValue){
        this.goalBPM.setValue(newValue);
        editor.putInt("goalBPM", newValue);
        editor.commit();
    }

    public LiveData<Integer> getGoalBPM() {
        return goalBPM;
    }



    public void setSharedPrefsValues(){
        this.setColour(pref.getInt("colour", -1));
        this.setStartingBPM(pref.getInt("startBPM", 11));
        this.setGoalBPM(pref.getInt("goalBPM", 6));
        this.setColourButtonBackground(pref.getString("colourButtonBackground", "red"));
        this.setDurationSpinnerPosition(pref.getInt("spinnerPosition", 0));
    }
}