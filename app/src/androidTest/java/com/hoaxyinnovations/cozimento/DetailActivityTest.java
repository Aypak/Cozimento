package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by kapsa on 11/22/2018.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DetailActivityTest {
    private static final String RECIPE_INTRODUCTION_LABEL= "Recipe Introduction";
    private static final String RECIPE_VIDEO= "";

    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class) {
                @Override

                /*Open step detail activity and send intent extras for step description and video url with it*/
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, StepDetailActivity.class);
                    result.putExtra("step_description", RECIPE_INTRODUCTION_LABEL);
                    result.putExtra("step_videoURL", RECIPE_VIDEO);
                    return result;
                }
            };

    @Test
    public void shouldShowRecipeDescription() {
        /*check that the recipe description matches the one passed in with the intent*/
        onView(withId(R.id.stepDescription)).check(matches(withText(RECIPE_INTRODUCTION_LABEL)));
    }






}
