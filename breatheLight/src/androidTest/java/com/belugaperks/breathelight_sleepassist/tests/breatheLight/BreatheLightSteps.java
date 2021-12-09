package com.belugaperks.breathelight_sleepassist.tests.breatheLight;

import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.belugaperks.breathelight_sleepassist.MainActivity;
import com.belugaperks.breathelight_sleepassist.R;

import org.junit.runner.RunWith;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class BreatheLightSteps {

    private ActivityTestRule activityRule = new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void cleanup() {
        Intents.release();
    }

    @Given("I start the application")
    public void startTheApplication(){
        activityRule.launchActivity(new Intent());
    }

    @And("I click the start button")
    public void iClickTheStartButton() {
        onView(withId(R.id.start_button)).perform(click());
    }

    @Then("I expect the stop button to be visible")
    public void iExpectTheStopButtonToBeVisible()  {
        onView(withId(R.id.stop_button)).check(matches(isDisplayed()));
    }

    @When("I drag the start BPM slider to position {int}")
    public void iDragTheStartBPMSliderToPositionStartBPMSeekbarPosition(int position) {
        onView(withId(R.id.seekBar_start_bpm)).perform(clickSeekBar(position));
    }

    @And("I drag the target BPM slider to position {int}")
    public void iDragTheTargetBPMSliderToPositionTargetBPMSeekbarPosition(int position) {

        onView(withId(R.id.seekBar_end_bpm)).perform(clickSeekBar(position));
    }

    @Then("I expect the starting breaths per min display to show {int}")
    public void iExpectTheStartingBreathsMinDisplayToShow(int startBreathValue) {
        onView(withId(R.id.text_start_bpm)).check(matches(withText("Starting Breaths/Min: " + startBreathValue)));
    }

    @Then("I expect the target breaths per min display to show {int}")
    public void iExpectTheTargetBreathsMinDisplayToShow(int targetBreathValue) {
        onView(withId(R.id.text_end_bpm)).check(matches(withText("Target Breaths/Min: " + targetBreathValue)));
    }


    public static ViewAction clickSeekBar(final int pos){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {
                        SeekBar seekBar = (SeekBar) view;
                        final int[] screenPos = new int[2];
                        seekBar.getLocationOnScreen(screenPos);

                        // get the width of the actual bar area
                        // by removing padding
                        int trueWidth = seekBar.getWidth()
                                - seekBar.getPaddingLeft() - seekBar.getPaddingRight();

                        // what is the position on a 0-1 scale
                        //  add 0.3f to avoid roundoff to the next smaller position
                        float relativePos = (0.3f + pos)/((float) seekBar.getMax()-seekBar.getMin());
                        if ( relativePos > 1.0f )
                            relativePos = 1.0f;

                        // determine where to click
                        final float screenX = trueWidth*relativePos + screenPos[0]
                                + seekBar.getPaddingLeft();
                        final float screenY = seekBar.getHeight()/2f + screenPos[1];

                        return new float[]{screenX, screenY};
                    }
                },
                Press.FINGER);
    }


    @When("I tap on the duration dropdown picker")
    public void iTapOnTheDurationDropdownPicker() {
        onView(withId(R.id.spinner_time_selector)).perform(click());
    }

    @And("I tap on the {int} minutes option")
    public void iTapOnTheMinutesOption(int mins) {
        onView(withText(mins + " Minutes")).perform(click());
    }

    @Then("I expect the duration display to show {int} minutes")
    public void iExpectTheDurationDisplayToShowMinutes(int mins) {
        onView(withId(R.id.spinner_time_selector)).check(matches(withSpinnerText(mins + " Minutes")));
    }

    @When("I tap the colour picker button")
    public void iTapTheColourPickerButton() {
        onView(withId(R.id.colour_picker_button)).perform(click());
    }

    @And("I select {string} from the buttons that appear")
    public void iSelectAColourFromTheButtonsThatAppear(String colour) {
        switch(colour){
            case "red" :
                onView(withId(R.id.image_button_red)).perform(click());
                break;
            case "green" :
                onView(withId(R.id.image_button_green)).perform(click());
                break;
            case "white" :
                onView(withId(R.id.image_button_white)).perform(click());
                break;
            case "blue" :
                onView(withId(R.id.image_button_blue)).perform(click());
                break;
            case "purple" :
                onView(withId(R.id.image_button_purple)).perform(click());
                break;
            case "yellow" :
                onView(withId(R.id.image_button_yellow)).perform(click());
                break;
            default:
                onView(withId(R.id.colour_picker_button)).perform(click());
                break;

        }
    }

    @Then("I expect {string} to be selected")
    public void iExpectThatColourToBeSelected(String colour) {
        onView(withId(R.id.colour_picker_button)).check(matches(withTagValue(equalTo(colour))));
    }



}
