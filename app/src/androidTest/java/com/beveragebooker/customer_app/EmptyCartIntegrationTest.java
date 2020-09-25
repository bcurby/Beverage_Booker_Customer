package com.beveragebooker.customer_app;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.BrowseMenu;
import com.beveragebooker.customer_app.activities.PrimaryMenu;

import org.hamcrest.core.AllOf;
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

    @Rule public ActivityScenarioRule<PrimaryMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * This test checks the functionality of EmptyCart use case and its
     * interaction with applicable use cases (BrowseMenu and CartActivity).
     * Checks that once a button to change screen is clicked that button no
     * longer exists. Checks that when emptyCartButton is clicked the
     * sausage roll is no longer there - then tests to make sure sausage roll can be
     * added to cart as easily as it was initially.
     */
    @Test
    public void isFillCartEmptyCartSuccessful() throws InterruptedException {

        //Primary Menu
        onView(withId(R.id.foodMenuButton))
                .perform(click());
        Thread.sleep(500);

        // BrowseMenu
        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Sausage Roll"))))
                .perform(click());

        //Add to cart
        onView(withId(R.id.addToCartButton))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(1000);
        onView(withId(R.id.cartButton))
                .perform(click());

        // CartActivity
        Thread.sleep(1000);
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("Sausage Roll"))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.emptyCartButton))
                .perform(click());

        // CartActivity
        Thread.sleep(1000);
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Sausage Roll"))))
                .check(doesNotExist());

        onView(withId(R.id.homeButton))
                .perform(click());
        Thread.sleep(500);

        // BrowseMenu
        Thread.sleep(500);
        onView(withId(R.id.foodMenuButton))
                .perform(click());
        Thread.sleep(500);

        // BrowseMenu
        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Sausage Roll"))))
                .perform(click());

        //Add to cart
        onView(withId(R.id.addToCartButton))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(1000);
        onView(withId(R.id.cartButton))
                .perform(click());

        // CartActivity
        Thread.sleep(1000);
        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("Sausage Roll"))))
                .check(matches(isDisplayed()));
    }
}