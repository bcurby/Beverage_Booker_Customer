package com.beveragebooker.customer_app;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.BrowseMenu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * These tests largely test that the information taken from the database to be displayed is valid and therefore
 * shows that the use-case is correctly implemented.
 * To run these tests effectively please use the Debug option.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestForFillCart {

    @Rule public ActivityScenarioRule<BrowseMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * Add banana to cart.
     * Passes if there were no errors getting to the button or clicking the button.
     * Passes no matter if banana exists already in the database or not (is only testing that addToCart button works).
     * The reason for the use of hasSibling is because there are potential many addToCart buttons thus it would failed as it did
     * not know the correct one to choose thus it must be given a sibling to act as the unique identifier
     */
    @Test
    public void ClickAddToCartButton_AddsToCart() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
    }

    /**
     * Verifies that when viewCart is clicked cart is displayed (shown through the displaying of cartRecyclerView in activity_cart).
     * Passes if cartRecyclerView exists in the activity_cart, else fails.
     */
    @Test
    public void clickViewCartButton_VerifiesCartOpenWithRecycler() {
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.cartRecyclerView))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies that when viewCart is clicked cart is displayed (shown through the displaying of cartTotal in activity_cart).
     * Passes if cartTotal exists in the activity_cart, else fails.
     */
    @Test
    public void clickViewCartButton_VerifiesCartOpenWithCartTotal() {
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.cartTotal))
                .check(matches(isDisplayed()));
    }

    /**
    // Verifies that when viewCart is clicked cart items are displayed (shown through the displaying of cartItemName along with the actual name of the item - banana)
    // Passes if the item is found in the database with name banana, else fails.
     */
    @Test
    public void clickViewCartButton_VerifiesItemInCartWithName() {
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
    }

    /**
     // Verifies that when viewCart is clicked cart item banana has a price of 12.5.
     // Passes if the item is found in the database with price 12.5, else fails.
     */
    @Test
    public void clickViewCartButton_VerifiesItemInCartWithPrice() {
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(allOf(withId(R.id.cartItemPrice), hasSibling(withText("$12.50"))))
                .check(matches(isDisplayed()));
    }
}
