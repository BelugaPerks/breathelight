package com.belugaperks.breathelight_sleepassist.ui.rate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.belugaperks.breathelight_sleepassist.R;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

public class RateFragment extends Fragment {

    private com.belugaperks.breathelight_sleepassist.ui.rate.RateViewModel rateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rateViewModel =
                ViewModelProviders.of(this).get(com.belugaperks.breathelight_sleepassist.ui.rate.RateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rate, container, false);
        final TextView textView = root.findViewById(R.id.text_rate);
        rateViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //setup rate button
        final Button shareButton = root.findViewById(R.id.button_rate);
        final ReviewManager manager = ReviewManagerFactory.create(getContext());

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remove the rate screen and button and use in-app review flow

            }
        });



        return root;
    }


}