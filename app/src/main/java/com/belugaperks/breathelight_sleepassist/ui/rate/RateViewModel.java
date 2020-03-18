package com.belugaperks.breathelight_sleepassist.ui.rate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Thank you for rating Breathe Light!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}