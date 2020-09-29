package com.beveragebooker.customer_app;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.CartActivity;
import com.beveragebooker.customer_app.activities.PrimaryMenu;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTestForPlaceOrder {

    @Rule
    public ActivityScenarioRule<PrimaryMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * Verifies that when checkoutButton is clicked everything on the next page is displayed showing that
     * the application works as intended.
     */

    @Test
    public void a_IsCheckoutButtonsDisplayed() throws InterruptedException {

        Thread.sleep(500);
        onView(withId(R.id.cartButton))
                .perform(click());

        //Check the Checkout button is displayed
        Thread.sleep(1000);
        onView(withId(R.id.checkoutButton))
                .check(matches(isDisplayed()));

        Thread.sleep(500);
        onView(withId(R.id.checkoutButton))
                .perform(click());

        //Check the Pick Up button is displayed
        Thread.sleep(1000);
        onView(withId(R.id.PickUpButton))
                .check(matches(isDisplayed()));

        Thread.sleep(500);
        onView(withId(R.id.PickUpButton))
                .perform(click());

        //Check the card input widget is displayed
        Thread.sleep(1000);
        onView(withId(R.id.cardInputWidget))
                .check(matches(isDisplayed()));

    }

    /**
     * This is checks that the fields and buttons for payment are displayed correctly.
     */
    @Test
    public void b_IsPaymentFieldsDisplayed() throws InterruptedException {

        Thread.sleep(500);
        onView(withId(R.id.cartButton))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.checkoutButton))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.PickUpButton))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.orderTotal))
                .check(matches(isDisplayed()));

        onView(withId(R.id.placeOrderButton))
                .check(matches(isDisplayed()));

        //Check the Pick Up button is displayed
        Thread.sleep(1000);
        onView(withId(R.id.cardInputWidget))
                .check(matches(isDisplayed()));
    }
}
