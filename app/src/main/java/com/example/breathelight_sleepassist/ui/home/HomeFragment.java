package com.example.breathelight_sleepassist.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.breathelight_sleepassist.R;
import com.example.breathelight_sleepassist.ui.light.LightPulse;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //set up spinner with custom dropdown resource
        final Spinner spinner = root.findViewById(R.id.spinner_time_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //set up spinner listener to update homeViewModel when item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homeViewModel.setDurationSpinnerPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //set up start bpm seekbar and text
        final TextView startBPMTextView = root.findViewById(R.id.text_start_bpm);
        final String startBPMText = startBPMTextView.getText().toString() + ": ";
        final SeekBar startBPM = root.findViewById(R.id.seekBar_start_bpm);
        //set up listener to add seekbar value to end of text in textView when it is changed
        homeViewModel.getStartingBPM().observe(this.getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer bpm) {
                startBPMTextView.setText(startBPMText + bpm);
            }
        });

        //set up listener to update homeViewModel when seekbar progress changes
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

        //set up goal bpm seekbar and text
        final TextView goalBPMTextView = root.findViewById(R.id.text_end_bpm);
        final String goalBPMText = goalBPMTextView.getText().toString() + ": ";
        final SeekBar goalBPM = root.findViewById(R.id.seekBar_end_bpm);
        //set up listener to add seekbar value to end of text in textView when it is changed
        homeViewModel.getGoalBPM().observe(this.getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer bpm) {
                goalBPMTextView.setText(goalBPMText + bpm);
            }
        });

        //set up listener to update homeViewModel when seekbar progress changes
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



        //get buttons and views containing them
        final ImageButton colourPickerButton = root.findViewById(R.id.colour_picker_button);
        final View outsideView = root.findViewById(R.id.outside_view);
        final View colourButtonView = root.findViewById(R.id.colour_button_view);
        final ImageButton redButton = root.findViewById(R.id.image_button_red);
        final ImageButton greenButton = root.findViewById(R.id.image_button_green);
        final ImageButton purpleButton = root.findViewById(R.id.image_button_purple);
        final ImageButton yellowButton = root.findViewById(R.id.image_button_yellow);
        final ImageButton whiteButton = root.findViewById(R.id.image_button_white);
        final ImageButton blueButton = root.findViewById(R.id.image_button_blue);

        //get pop out animations
        final Animation animPopUpRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_up_right);
        final Animation animPopUpLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_up_left);
        final Animation animPopRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_right);
        final Animation animPopLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_left);
        final Animation animPopDownRight = AnimationUtils.loadAnimation(getContext(), R.anim.pop_down_right);
        final Animation animPopDownLeft = AnimationUtils.loadAnimation(getContext(), R.anim.pop_down_left);

        //Instantiate map between string and Drawable for colour button. This is so a simple string may be stored in homeViewModel (and by extension, sharedPreferences)
        final HashMap<String,Drawable> colourButtonMap = new HashMap<>();
        colourButtonMap.put("red", redButton.getDrawable());
        colourButtonMap.put("white", whiteButton.getDrawable());
        colourButtonMap.put("green", greenButton.getDrawable());
        colourButtonMap.put("purple", purpleButton.getDrawable());
        colourButtonMap.put("blue", blueButton.getDrawable());
        colourButtonMap.put("yellow", yellowButton.getDrawable());

        //set up listener on colourButtonBackground in homeViewModel, so when it is updated (by selecting a colour picker button), the main colour picker button has it's background updated.
        homeViewModel.getColourButtonBackground().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                colourPickerButton.setBackground(colourButtonMap.get(s));
            }
        });

        //set up on lick listener for main colour picker button, to have the other coloured buttons pop out and become visible when selected.
        colourPickerButton.setOnClickListener(new View.OnClickListener() {
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
        //set up listeners for each coloured button
        //when selected, the coloured buttons are set to invisible again, then the homeViewModel has two values updated:
        //colourButtonBackground - so that the page may display the correct drawable for the main colour picker button
        //colour - to pass the correct integer value to the LightPulse activity
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("red");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.red_light));
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("green");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.green_light));
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("blue");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.blue_light));
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("yellow");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.yellow_light));
            }
        });
        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("purple");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.purple_light));
            }
        });
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
                homeViewModel.setColourButtonBackground("white");
                homeViewModel.setColour(ContextCompat.getColor(getContext(), R.color.white_light));
            }
        });
        outsideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colourButtonView.setVisibility(View.GONE);
                outsideView.setVisibility(View.GONE);
            }
        });

        //set up start button
        final Button startButton = root.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            //when pressed, LightPulse activity is started, and is provided with the selected values for duration, start BPM, goal BPM, and light colour
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LightPulse.class);
                intent.putExtra("startBPM", homeViewModel.getStartingBPM().getValue());
                intent.putExtra("goalBPM", homeViewModel.getGoalBPM().getValue());
                intent.putExtra("colour", homeViewModel.getColour().getValue());
                intent.putExtra("duration", spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        //set values to be either default or what is saved in shared prefs
        homeViewModel.setSharedPrefsValues();

        //set startBPM, goalBPM, and spinner position to be the values in homeViewModel
        startBPM.setProgress(homeViewModel.getStartingBPM().getValue());
        goalBPM.setProgress(homeViewModel.getGoalBPM().getValue());
        spinner.setSelection(homeViewModel.getDurationSpinnerPositon().getValue());


        //setting default values for colour selector view
        colourButtonView.setVisibility(View.GONE);
        outsideView.setVisibility(View.GONE);




        return root;
    }

}