package com.example.user.mycounterparties.view.activity;

import static android.support.test.espresso.Espresso.onView;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.user.mycounterparties.R;

import org.junit.Rule;
import org.junit.Test;


import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.action.ViewActions.click;


/**
 * Created by User on 11.12.2017.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecycleViewOnClick() throws Exception {
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withId(R.id.share)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnBackPressedForCounterpartiesDerailsActivity() throws Exception{
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        pressBack();
        onView(withId(R.id.rv)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnBackPressedForSearchActivity() throws Exception {
        onView(withId(R.id.fab)).perform(click());
        pressBack();
        onView(withId(R.id.rv)).check(matches(isDisplayed()));
    }


}