package com.hoaxyinnovations.cozimento;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by kapsa on 11/22/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    private static final String INGREDIENTS_LABEL= "Ingredients";
    private static final String STEPS_LABEL= "Steps";



    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItemOpensDetailActivity() {


        onView(withId(R.id.recipes_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the OrderActivity opens with the correct tea name displayed
        onView(withId(R.id.tv_ingredients_label)).check(matches(withText(INGREDIENTS_LABEL)));
        onView(withId(R.id.tv_steps_label)).check(matches(withText(STEPS_LABEL)));



    }
}
