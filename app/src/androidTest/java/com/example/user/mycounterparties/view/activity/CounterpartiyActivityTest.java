package com.example.user.mycounterparties.view.activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.user.mycounterparties.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Created by User on 11.12.2017.
 */
public class CounterpartiyActivityTest {

    @Rule
    public ActivityTestRule<CounterpartiyActivity> mainActivityTestRule = new ActivityTestRule<>(CounterpartiyActivity.class);

    @Test
    public void menuDeleteItemClicked() throws Exception{
       onView(withId(R.id.menu_item_delete)).perform(click());
       onView(withId(R.id.btnYes)).check(matches(isDisplayed()));
    }

    @Test
    public void testFavoriteItemClicked() throws Exception{
        onView(withId(R.id.menu_item_no_favorite)).perform(click());
        onView(withId(R.id.menu_item_no_favorite)).check(matches(isDisplayed()));
    }

}