package com.belugaperks.breathelight_sleepassist.tests.breatheLight;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.belugaperks.breathelight_sleepassist.MainActivity;
import com.belugaperks.breathelight_sleepassist.R;

import org.hamcrest.Matcher;
import org.junit.runner.RunWith;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.belugaperks.breathelight_sleepassist.tests.utils.TestingUtils.clickSeekBar;
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


    @When("I click the hamburger menu")
    public void iClickTheHamburgerMenu() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left drawer should be closed
                .perform(DrawerActions.open()); // Open the drawer
    }

    @And("I select the About menu option")
    public void iSelectTheAboutMenuOption() {
        onView(withId((R.id.nav_view))).perform(NavigationViewActions.navigateTo(R.id.nav_about));
    }

    @Then("I expect the about text to be visible")
    public void iExpectTheAboutTextToBeVisible() {
        // Check the scrollview is visible
        onView(withId(R.id.about_scroll_view)).check(matches(isDisplayed()));
        // CHeck the text items can be scrolled to to and viewed
        onView(withId(R.id.text_about_1)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.text_about_2)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.text_about_3)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.text_about_4)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.text_about_5)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.text_about_6)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Then("I expect the view source code button to be visible")
    public void iExpectTheViewSourceCodeButtonToBeVisible()  {
        onView(withId(R.id.button_view_source)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Then("I expect the view license button to be visible")
    public void iExpectTheViewLicenseButtonToBeVisible()  {
        onView(withId(R.id.button_license)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @And("I select the Share menu option")
    public void iSelectTheShareMenuOption() {
        onView(withId((R.id.nav_view))).perform(NavigationViewActions.navigateTo(R.id.nav_share));
    }



    @Then("I expect the share prompt to come up")
    public void iExpectTheSharePromptToComeUp() {
        // Sleep to allow system share menu to appear
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasAction(Intent.ACTION_SEND));
    }

    @Then("I expect to be viewing the Share screen")
    public void iExpectToBeViewingTheShareScreen() {
        onView(withId(R.id.share_layout)).check(matches(isDisplayed()));
    }

    @Then("I expect the thank you message to be displayed on the share screen")
    public void iExpectTheThankYouMessageToBeDisplayedOnTheShareScreen() {
        onView(withId(R.id.text_share)).check(matches(isDisplayed()));
    }

    @And("I click the Share button")
    public void iClickTheShareButton() {
        // Sleep to allow emulator time to load the button before clicking
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.button_share)).perform(click());
    }

    @And("I click the View Source Code button")
    public void iClickTheViewSourceCodeButton() {
        onView(withId(R.id.button_view_source)).perform(scrollTo(), click());
    }

    @And("I click the View License button")
    public void iClickTheViewLicenseButton() {
        onView(withId(R.id.button_license)).perform(scrollTo(), click());
    }
}
