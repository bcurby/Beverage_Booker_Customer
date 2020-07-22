package com.beveragebooker.customer_app.activities;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedEmptyCartTest {
    @Rule
    public ActivityScenarioRule<BrowseMenu> mActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * Verifies that when a banana is added to cart then the button empty cart in the
     * cart screen is clicked that item name with text apple is displayed showing that
     * the program is now back on the menu page.
     */
    @Test
    public void AddItemToCart_EmptyCart() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.emptyCartButton))
                .perform(click());
        onView(allOf(withId(R.id.itemName), hasSibling(withText("apple"))))
                .check(matches(isDisplayed()));
    }

    /**
     * Does the same as above however checks the cart again to make sure the item is no
     * longer in there.
     */
    @Test
    public void AddItemToCart_EmptyCart_CheckIfItemStillInCart() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.emptyCartButton))
                .perform(click());
        onView(allOf(withId(R.id.itemName), hasSibling(withText("apple"))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(doesNotExist());
    }
}