package ca.group6.it.thedocky;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.group6.it.thedocky.ui.login.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest


public class EspressoTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

    @Test
    public void forgotpasswordIsDisplayedBelowPassword() {
        onView(withText("Forgot password")).check(matches(isDisplayed()));
        onView(withText("Forgot password")).check(isCompletelyBelow(withId(R.id.password_login)));

    }
}

