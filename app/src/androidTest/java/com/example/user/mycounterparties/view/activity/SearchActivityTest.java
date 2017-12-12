package com.example.user.mycounterparties.view.activity;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.user.mycounterparties.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.action.ViewActions.typeText;

/**
 * Created by User on 11.12.2017.
 */
public class SearchActivityTest {


    @Rule
    public ActivityTestRule<SearchActivity> searchActivityTestRule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void testEmptyEditText() throws Exception {
        onView(withId(R.id.autocompletetextview)).check(matches(allOf(

                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),

                isFocusable(),

                isClickable(),

                withText("")

        )));

    }

    @Test
    public void testInputDisplayed() throws Exception{
        onView(withId(R.id.autocompletetextview)).perform(typeText("Тинькофф"));

        closeSoftKeyboard();

        onView(withId(R.id.autocompletetextview)).check(matches(withText("Тинькофф")));
    }

}