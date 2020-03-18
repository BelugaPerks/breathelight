package com.belugaperks.breathelight_sleepassist.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Thank you for sharing Breathe Light!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}