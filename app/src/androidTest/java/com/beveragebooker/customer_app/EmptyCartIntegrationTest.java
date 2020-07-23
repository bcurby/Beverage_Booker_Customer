package com.beveragebooker.customer_app;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.BrowseMenu;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EmptyCartIntegrationTest {

    @Rule public ActivityScenarioRule<BrowseMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * This test checks the functionality of EmptyCart use case and its
     * interaction with applicable use cases (BrowseMenu and CartActivity).
     * Checks that once a button to change screen is clicked that button no
     * longer exists. Checks that when emptyCartButton is clicked the
     * banana is no longer there - the tests to make sure banana can be
     * added to cart as easily as it was initially.
     */
    @Test
    public void EmptyCartIntegrationTest() {
        // BrowseMenu
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());

        // CartActivity
        onView(withId(R.id.viewCart))
                .check(doesNotExist());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.emptyCartButton))
                .perform(click());

        // BrowseMenu
        onView(withId(R.id.emptyCartButton))
                .check(doesNotExist());
        onView(withId(R.id.viewCart))
                .perform(click());

        // CartActivity
        onView(withId(R.id.viewCart))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .check(doesNotExist());
        pressBack();

        // BrowseMenu
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());

        // CartActivity
        onView(withId(R.id.viewCart))
                .check(doesNotExist());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
    }
}