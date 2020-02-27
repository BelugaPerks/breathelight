package com.example.breathelight_sleepassist.ui.home;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.breathelight_sleepassist.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        Spinner spinner = root.findViewById(R.id.timeSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        final TextView startBPMTextView = root.findViewById(R.id.text_start_bpm);
        final String startBPMText = startBPMTextView.getText().toString() + ": ";
        final SeekBar startBPM = root.findViewById(R.id.seekBar_start_bpm);
        homeViewModel.getStartingBPM().observe(this.getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer bpm) {
                startBPMTextView.setText(startBPMText + bpm);
            }
        });
        startBPM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                homeViewModel.setStartingBPM(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final TextView goalBPMTextView = root.findViewById(R.id.text_end_bpm);
        final String goalBPMText = goalBPMTextView.getText().toString() + ": ";
        final SeekBar goalBPM = root.findViewById(R.id.seekBar_end_bpm);
        homeViewModel.getGoalBPM().observe(this.getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer bpm) {
                goalBPMTextView.setText(goalBPMText + bpm);
            }
        });
        goalBPM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                homeViewModel.setGoalBPM(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        final ImageButton colourButton = root.findViewById(R.id.colour_picker_button);
        homeViewModel.getColourButtonBackground().observe(this.getViewLifecycleOwner(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable d) {
                colourButton.setBackground(d);
            }
        });
        final View outsideView = root.findViewById(R.id.outside_view);

        final View colourButtonView = root.findViewById(R.id.colour_button_view);
        final ImageButton redButton = root.findViewById(R.id.image_button_red);
        final ImageButton greenButton = root.findViewById(R.id.image_button_green);
        final ImageButton purpleButton = root.findViewById(R.id.image_button_purple);
        final ImageButton yellowButton = root.findViewById(R.id.image_button_yellow);
        final ImageButton whiteButton = root.findViewById(R.id.image_button_white);
        final ImageButton blueButton = root.findViewById(R.id.image_button_blue);
        final Animation animPopUpRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_up_right);
        final Animation animPopUpLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_up_left);
        final Animation animPopRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_right);
        final Animation animPopLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_left);
        final Animation animPopDownRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_down_right);
        final Animation animPopDownLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_down_left);

        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colourButtonView.setVisibility(View.VISIBLE);
                redButton.startAnimation(animPopUpRight);
                whiteButton.startAnimation(animPopUpLeft);
                greenButton.startAnimation(animPopRight);
                purpleButton.startAnimation(animPopLeft);
                blueButton.startAnimation(animPopDownRight);
                yellowButton.startAnimation(animPopDownLeft);
                outsideView.setVisibility(View.VISIBLE);

            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(redButton.getDrawable());

            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(greenButton.getDrawable());
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(blueButton.getDrawable());
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(yellowButton.getDrawable());
            }
        });
        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(purpleButton.getDrawable());
            }
        });
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(whiteButton.getDrawable());
            }
        });
        outsideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
            }
        });

        final Button startButton = root.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //setting default values
        colourButtonView.setVisibility(View.GONE);
        outsideView.setVisibility(View.GONE);
        homeViewModel.setColourButtonBackground(redButton.getDrawable());
        startBPM.setProgress(11);
        goalBPM.setProgress(6);


        return root;
    }

}