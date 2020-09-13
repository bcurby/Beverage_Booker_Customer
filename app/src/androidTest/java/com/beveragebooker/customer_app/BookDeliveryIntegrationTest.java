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
public class BookDeliveryIntegrationTest {

    @Rule public ActivityScenarioRule<BrowseMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * This test runs through the program to test the interaction between
     * BookDelivery use case and other applicable use cases. Each time the
     * screen is changed there is a check to make sure that the button that
     * was the cause of the change is no longer existent confirming that
     * the screen changed.
     */
    @Test
    public void BookDeliveryIntegrationTest() {
        // BrowseMenu
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());

        // CartActivity
        onView(withId(R.id.viewCart))
                .check(doesNotExist());
        onView(withId(R.id.checkoutButton))
                .perform(click());

        // SelectOrderTypeActivity
        onView(withId(R.id.checkoutButton))
                .check(doesNotExist());
        onView(withId(R.id.DeliveryButton))
                .perform(click());

        // BookDeliveryActivity
        onView(withId(R.id.DeliveryButton))
                .check(doesNotExist());
        onView(withId(R.id.editTextUnit))
                .perform(typeText("123"));
        onView(withId(R.id.editTextStreetName))
                .perform(typeText("StreetName"), closeSoftKeyboard());
        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());

        // PaymentActivity
        onView(withId(R.id.ProceedToPaymentButton))
                .check(doesNotExist());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("123"));
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("12"));
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("21"));
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());

        // PlaceOrderActivity
        onView(withId(R.id.openPlaceOrderButton))
                .check(doesNotExist());
        onView(withId(R.id.placeOrderButton))
                .perform(click());

        // OrderConfirmationActivity
        onView(withId(R.id.placeOrderButton))
                .check(doesNotExist());
        onView(withId(R.id.homeButton))
                .perform(click());

        // PrimaryMenu
        onView(withId(R.id.menuLink))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editText2))
                .check(matches(isDisplayed()));
    }
}
