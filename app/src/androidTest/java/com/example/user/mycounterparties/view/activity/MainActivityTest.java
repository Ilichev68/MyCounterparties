package com.example.user.mycounterparties.view.activity;

import static android.support.test.espresso.Espresso.onView;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.user.mycounterparties.R;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 11.12.2017.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onClick() throws Exception {
        onView(ViewMatchers.withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
//        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());
    }

}