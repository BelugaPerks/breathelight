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
import android.widget.Spinner;

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

        final ImageButton colourButton = root.findViewById(R.id.colourPickerButton);
        homeViewModel.getColourButtonBackground().observe(this.getViewLifecycleOwner(), new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable d) {
                colourButton.setBackground(d);
            }
        });
        final View outsideView = root.findViewById(R.id.outsideView);

        final View colourButtonView = root.findViewById(R.id.colourButtonView);
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
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.red_light)));

            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.green_light)));
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.blue_light)));
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.yellow_light)));
            }
        });
        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.purple_light)));
            }
        });
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground(new ColorDrawable(getResources().getColor(R.color.white_light)));
            }
        });
        outsideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
            }
        });
        colourButtonView.setVisibility(View.GONE);
        outsideView.setVisibility(View.GONE);
        return root;
    }

}