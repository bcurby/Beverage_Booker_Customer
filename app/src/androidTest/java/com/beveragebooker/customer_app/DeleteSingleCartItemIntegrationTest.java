package com.beveragebooker.customer_app;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.PrimaryMenu;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

    @LargeTest
    @RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
    public class DeleteSingleCartItemIntegrationTest {

        @Rule public androidx.test.ext.junit.rules.ActivityScenarioRule<PrimaryMenu> ActivityScenarioRule
                = new ActivityScenarioRule<>(PrimaryMenu.class);

        /**
         * This test runs tests the implementation of delete single item from cart
         */
        @Test
        public void isFillCartDeleteSingleCartItemSuccessful() throws InterruptedException {
            // Primary Menu
            onView(withId(R.id.foodMenuButton))
                    .perform(click());
            Thread.sleep(500);

            // Food Menu / Activity Browse Menu
            Thread.sleep(1000);
            onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Sausage Roll"))))
                    .perform(click());

            //AddToCartActivity
            Thread.sleep(500);
            onView(withId(R.id.addToCartButton))
                    .perform(click());

            Thread.sleep(500);
            onView(withId(R.id.cartButton))
                    .perform(click());

             //Cart Activity
            Thread.sleep(1000);
            onView(allOf(ViewMatchers.withId(R.id.deleteCartItem), hasSibling(withText("Sausage Roll"))))
                    .perform(click());

            //Cart Activity
            Thread.sleep(1000);
            onView(allOf(ViewMatchers.withId(R.id.deleteCartItem), hasSibling(withText("Sausage Roll"))))
                    .check(doesNotExist());

        }
    }
