package com.beveragebooker.customer_app.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ViewOrderIntegrationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * The integration test for View Order logs a user in and proceeds to the View Order confirmation screen.
     * It then checks that the estimated order time message isd displayed.
     * The test then moves back to the primary menu. From here the 'View Order' button is clicked again and the
     * test movies to the OrderConfirmationActivity.
     * Finally, it checks for the estimated order time displaying again after leaving and then coming back to the activity.
     **/

    @Test
    public void viewOrderIntegrationTest() {

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("benn@gmail.com"));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(withId(R.id.continueButton))
                .perform(click());

        onView(withId(R.id.orderButton))
                .perform(click());

        onView(withId(R.id.textViewOrderConfirm))
                .check(matches(isDisplayed()));

        onView(withId(R.id.homeButton))
                .perform(click());

        onView(withId(R.id.orderButton))
                .perform(click());

        onView(withId(R.id.textViewOrderConfirm))
                .check(matches(isDisplayed()));
    }
}
